/**
 * This library contains all the functions related to the nodes maps dialog window
 * @author Wladimir Totino
 */

/**
 * Loads the data for the Events log window
 * TODO: all the urls should be put in variables
 */
function loadJsonAlerts(windowId){
	$("#"+windowId+"-alertscontainerajaxloading").show();
	var jsonAlerts = $.getJSON("nodes/alertslist.htm", function(data) {
		var self = this;
		var alerts=getResultsArray(data);
		var content = $($.parseHTML("<div></div>"));
		$.each(alerts, function(key, val) {
			var string='<div class="map-alerts-element">';
			string+="- <span class='map-text-container-link'>"+val.activity+" </span> ";
			string+=val.message;
			string+="</div>";
			var element = $($.parseHTML(string));
			element.attr('data-id', val.id_observation);
			element.attr('data-nodeid', val.id_location);
			element.attr('data-windowid', windowId);
			element.attr('data-latitude', val.latitude);
			element.attr('data-longitude', val.longitude);
			content.append(element);
		});
		$('#'+windowId+'-alertscontainer').html(content);
		$("#"+windowId+"-alertscontainerajaxloading").hide();
		//Scrolls the alerts box to the bottom
		var el=document.getElementById(windowId+"-alertscontainer");
		el.scrollTop=el.scrollHeight;
		setTimeout(function(){loadJsonAlerts(windowId);}, 30000);
	});
}

/**
 * Callback function when the window is being restored while minimized
 */
function restoreMap(windowId){
	refreshMap(windowId);
	$("#"+windowId).dialogExtend("maximize");
}

/**
 * refresh the gap when the window size is being changed (including minimize/maximize) 
 * @param windowId
 */
function refreshMap(windowId){
	$('#'+windowId+'-mapcontainer').gmap('refresh');
	if($('#'+windowId+'-detailscontainer').is(":visible")){
		$('#'+windowId+'-detailscontainer').height($('#'+windowId+'-socialcontainer').innerHeight()-$('#'+windowId+'-socialcontainerbar').outerHeight()-4);
	}
	$('#'+windowId+'-messagescontainer').css('max-height', $('#'+windowId+'-mapcontainer').innerHeight()-$("#"+windowId+"-messagescontainer-handle").outerHeight()-5);
	$('#'+windowId+'-maptabs').css('max-height', $('#'+windowId+'-mapcontainer').innerHeight());
	scrollLog(windowId);
}

/**
 * Init function for the nodes map window, gathers all the data needed for the window to work
 * loads the markers and renders the window
 * see the window-opener library for more details
 */
function initAlertsWindow(windowId){
	var jqxhr = $.getJSON( "nodes/getlocations.htm", function(data, textStatus) {
		var lastLat=0;
		var lastLong=0;
		$('#'+windowId+'-mapcontainer').gmap({'zoom': 2, "mapTypeId": gMap.MapTypeId.HYBRID, 'callback': function() {
			var self=this;
			var locations=getResultsArray(data);
			var first=true;
			var str="";
			$.each(locations, function(key, val) {
				lastLat=val.latitude;
				lastLong=val.longitude;
				addLocationMarker(self, val, windowId);
				//Adds the location to the list
				var selected="";
				if(first){
					selected="edgenode-click-selected";
					first=false;
				}
				str+='<span data-node-id="'+val.id+'" class="edgenode-click '+selected+'">- '+val.address+" - "+val.name+"</span><br/>";
			});
			$("#"+windowId+"-edgenodeslist").html(str);
		}}).bind('init', function(evt, map) { 
			$("#"+windowId).dialogExtend("maximize");
			$("#"+windowId).on("dialogfocus", function( event, ui ){
				//brings all the accessory dialogs to top
				$("."+windowId+"-children-dialog").dialog("moveToTop");
			});
			//Completes the loading phase with various loading
			completeLoading(windowId);
			google.maps.event.addListener(map, 'zoom_changed', function() {
				//Hides/Shows locations and sensors based on zoom
				var zoom=map.getZoom();
				var showLocation=true;
				if(zoom>=14){
					showLocation=false;
				}
				//Sets the sensors invisible 
				$('#'+windowId+'-mapcontainer').gmap('find', 'markers', { 'property': 'type', 'value': "location" }, function(marker, found) {
					if(marker.type=="sensor"){
						marker.setVisible(!showLocation);
					}
				});

			});
		});
	}).fail(function() {
		writeLog("There was an error trying to complete the request", windowId, "critical");
	});
}

/**
 * Generates a text representation of an alert in JSON format 
 * by adding labels in place of the values names and removing the unneded fields
 * @param alert the JSON parsed alert
 * @returns {String} parsed HTML text of an alert
 */
function convertAlertDetails(alert){
	var string="";
	var arr=["class", "createdAt", "id", "priority"];
	$.each(alert, function(key, val) {
		if($.inArray(key, arr)==-1){
			if(key=="createdAtString"){
				key="date";
			}
			if(key=="address"){
				key="Address";
				val=alert.address+" - "+alert.name;
			}
			if($.inArray(key, alertExclude)==-1){
				string=string+getLabel(key, alertLabels) + ": " + val+" <br/> ";
			}
		}
	});
	return string;
}

/**
 * Adds an edge node marker
 * @param self
 * @param val
 * @param windowId
 */
function addLocationMarker(self, val, windowId){
	self.addMarker({
		'nodeId': val.id, 
		'address': val.address,
		'name': val.name,
		'position': new gMap.LatLng(val.latitude, val.longitude),
		'icon': 'img/markers/m2.png',
		'type': 'location'
	}).click(function() {
		var marker=this;
		//Loads the info for the node
		var string='';
		$("#"+windowId+"-socialcontainerajaxloading").show();
		$.getJSON("nodes/getlastalert/"+val.id+".htm", function(data) {
			var info=getResultsArray(data);
			//There is only one alert though it's turned into an array
			info=info[0];
			string+='<div style="min-width: 300px; height: 150px; color: #000000; overflow: auto;">';
			string+='<div>';
			string+='<div style="text-align: center; margin-bottom: 2px;">Location Details</div>';
			string=string+"id: " + marker.nodeId+" <br/> ";
			string=string+"address: " + marker.address+" - "+marker.name+" <br/> ";
			string+='<div style="text-align: center; margin-top: 2px; margin-bottom: 2px;">Last Alert Details</div>';
			string+=convertAlertDetails(info);
			string+='</div>';
			string+='</div>';
			$("#"+windowId+"-socialcontainerajaxloading").hide();
			self.openInfoWindow({ 'content': string }, marker);
		});
		writeLog("Opened Node details of Node "+val.id, windowId, "debug");
	});
}

/**
 * loads the sensors markers and puts them on the map
 */
function addSensorMarkers(windowId){
	var jqxhr = $.getJSON( "nodes/nodeslistjson.htm", function(data) {
		$.each(data, function(key, val) {
			$('#'+windowId+'-mapcontainer').gmap('addMarker', {
				'nodeId': val.nodeId, 
				'position': new gMap.LatLng(val.latitude, val.longitude),
				'icon': 'img/markers/'+val.type+".png",
				'edgeNodeId': val.edgeNodeId,
				'type': "sensor",
				'visible': false
			});
		});
	}).fail(function() {
		writeLog("There was an error trying to complete the request", windowId, "critical");
	});
}

/**
 * Executes all the operations needed to complete the loading of a Nodes map window
 */
function completeLoading(windowId){
	loadJsonAlerts(windowId);
	loadJsonNodes(windowId);
	loadJsonLogs(windowId);
	addSensorMarkers(windowId);
	//Creates the draggable
	$( "#"+windowId+"-maptabs" ).draggable({ 
		containment: $("#"+windowId+"-mapcontainer"), 
		handle: $( "#"+windowId+"-messagescontainer-handle" ), 
		axis: "x",
		scroll: false
	});
	//Creates the tabs
	createTabs(windowId);
}

/**
 * Generates the Details/Edge Nodes tabs switcher for the sidebar
 */
function createTabs(windowId){
	var tabsUl="<ul>";
	tabsUl+='<li><a href="#'+windowId+'-messagescontainer">Details</a></li>';
	tabsUl+='<li><a href="#'+windowId+'-edgenodeslistcontainer">Locations</a></li>';
	tabsUl+='</ul>';
	$( "#"+windowId+"-maptabs" ).prepend(tabsUl);
	$( "#"+windowId+"-maptabs" ).tabs({
        hide: {
            effect: "blind",
        },
        show: {
            effect: "blind",
        }
    }).addClass( "ui-tabs-vertical ui-helper-clearfix" );
	$( "#"+windowId+"-maptabs li" ).removeClass( "ui-corner-top" ).addClass( "ui-corner-left" );
	$( "#"+windowId+"-maptabs" ).addClass('map-tabs-container').removeClass("ui-widget-content");
}

/**
 * Loads the logs
 * @param windowId
 */
function loadJsonLogs(windowId){
	var jsonAlerts = $.getJSON("nodes/getlogs.htm", function(data) {
		var logs=getResultsArray(data);
		var str="";
		var i=0;
		$.each(logs, function(key, val) {
			str+='<div id="log-'+i+'" style="cursor:pointer" class="log-click logs-'+val.severity.toLowerCase()+'"> '+val.datetime+" ["+val.severity+"] "+val.message+'</div>';
			str+='<div id="log-'+i+'-open" style="display: none; background-color: #666666">Details: '+val.description+'</div>';
			i++;
		});
		$("#"+windowId+"-logscontainer").html(str);
		scrollLog(windowId);
		setTimeout(function(){loadJsonLogs(windowId);}, 30000);
	});
}

//TODO: Remove these functions after making sure they are unusued 

function openDetailsWindow(windowId){
	//Triggers only of the height is 0
	if($('#'+windowId+'-socialcontainer').height()<=0){
		$('#'+windowId+'-socialcontainer').show();
		$('#'+windowId+'-alertscontainer').animate({
			height: '49%'
		});
		$('#'+windowId+'-socialcontainer').animate({
			height: '50%'
		}, function(){
			$('#'+windowId+'-detailscontainer').height($('#'+windowId+'-socialcontainer').innerHeight()-$('#'+windowId+'-socialcontainerbar').outerHeight()-4);
		});
	}
}

function restoreDetailsWindow(windowId){
	//Triggers only of the height is not 0
	if($('#'+windowId+'-socialcontainer').height()!=0){
		$('#'+windowId+'-alertscontainer').animate({
			height: '99%'
		});
		$('#'+windowId+'-socialcontainer').animate({
			height: '0'
		}, function(){
			$('#'+windowId+'-socialcontainer').hide();
		});
	}
	$("#"+windowId+"-detailscontainer").html("");
}

function showTweets(windowId, longitude, lat, area){
	$('#'+windowId+'-twittercontainerajaxloading').show();
	$.getJSON("twitter/tweetsfromlocation/"+longitude+"/"+lat+"/"+area+"/.htm", function(data) {
		var results=findInJson("Result", data);
		var string='';
		$.each(results, function(key, val) {
			string+='<div style="border: 2px solid #000000; background-color: #999999; margin: 2px; padding: 1px; color: #000000">';
			string+="User: "+val.UserName+"<br/>";
			string+=val.CreationDate+"<br/>";
			string+="<b>"+val.Message+"</b>"+"<br/>";
			string+="</div>";
		});
		string+="";
		$("#"+windowId+"-twittercontainer").html($.parseHTML(string));
		$('#'+windowId+'-twittercontainerajaxloading').hide();
		$("#"+windowId+"-twittercontainer").show("blind");
	});
}