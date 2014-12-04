/**
 * This library contains all the events related to the Nodes Map dialog window
 * @author Wladimir Totino
 */
$(document).ready(function(){	
	
	//Event triggered when you change from the Details/Location tab, we must remove the "is hidden" from the minimize button
	$(document).on("click", ".ui-tabs-anchor", function (event) {
		var windowId=getWrappingDialogId($(this));
		$("#"+windowId+"-closemessagescontainer").data("hidden", 0);
	});
	
	//Event triggered when you click the minimize button in the sidebar
	$(document).on("click", ".close-socialcontainer", function (event) {
		var button=$(this);
		var windowId=getWrappingDialogId($(this));
		//If it's hidden
		if(button.data("hidden")==1){
			$("#"+button.data("current-tab")).show("blind");
			button.data("hidden", 0);
		} else { //If it's not hidden
			button.data("hidden", 1);
			$("#"+windowId+"-maptabs").children(".ui-tabs-panel").each(function(){
				if($(this).is(":visible")){
					$(this).hide("blind");
					button.data("current-tab", $(this).attr('id'));
				}
			});
			if($('#'+windowId+'-detailscontainer').is(":visible")){
				
			}
		}
	});
	
	//Event triggered when, in the Locations tab, you click on a node's name
	$(document).on("click", ".edgenode-click", function (event) {
		var windowId=getWrappingDialogId($(this));
		var show=true;
		var logText="";
		//Removes/Shows the selection
		if($(this).hasClass("edgenode-click-selected")){
			$(this).removeClass("edgenode-click-selected");
			show=false;
			logText="Removed Edge Node \""+$(this).html()+"\" From View";
		} else {
			$(this).addClass("edgenode-click-selected");
			logText="Added Edge Node \""+$(this).html()+"\" To View";
		}
		$('#'+windowId+'-mapcontainer').gmap('find', 'markers', { 'property': 'edgeNodeId', 'value': $(this).data("node-id") }, function(marker, found) {
			if(marker.type=="location"){
				marker.setVisible(show);
			}
		});
		writeLog(logText, windowId, "debug");
	});
	
	
	//Event triggered when you click on the ^ button next to a detail area
	$(document).on("click", ".show-details-area", function (event) {
		var id=$(this).attr('id');
		$("#"+id+"container").toggle("blind");
	});
	
	//Event triggered when you click on the + button next to a detail area, showing the current content in a new dialog
	$(document).on("click", ".map-details-open-new-window", function (event) {
		var id=$(this).data('window-id');
		var name=id+"-"+$(this).data("linked");
		$("#"+name+"ajaxloading").show();
		var html=$("#"+name).html();
		var detailsDialog=$($.parseHTML('<div class="'+id+'-children-dialog monitor-text">'+html+"</div>")).dialog({
			title: $(this).data("title"),
			width: 350,
			height: maxContainerHeight*0.50,
			close: function(event, ui) {
				$(this).dialog('destroy').remove();
			}
		});
		$("#"+name+"ajaxloading").hide();
	});
	
	//Event triggered when you click on an Alert
	$(document).on("click", ".map-alerts-element", function (event) {
		var windowId=$(this).data('windowid');
		var nodeId=$(this).data('nodeid');
		var id=$(this).data('id');
		var position=new gMap.LatLng($(this).data('latitude'), $(this).data('longitude'));
		//Loads the info for the node
		var string='';
		//Shows the loading circles
		$("#"+windowId+"-socialcontainerajaxloading").show();
		$("#"+windowId+"-mediacontainerajaxloading").show();
		$.getJSON("nodes/alertslist.htm", function(data) {
			var alerts=getResultsArray(data);
			var currentAlert={};
			//Finds the single alert with the current id
			$.each(alerts, function(key, val) {
				if(val.id_observation==id){
					currentAlert=val;
				}
			});
			//Centers to the marker
			$('#'+windowId+'-mapcontainer').gmap('option', 'zoom', 19);
			$('#'+windowId+'-mapcontainer').gmap("option", "center",  position);
			showTweets(windowId, position.lng(), position.lat(), 0.1);
			string+='<div style="text-align: center; margin-bottom: 5px; margin-top: 3px; ">Alert Details</div>';
			string+='<div style="clear: both; width: 100%">';
			string+=convertAlertDetails(currentAlert);
			string+="</div>";
			$("#"+windowId+"-detailscontainer").html($.parseHTML(string));
			$("#"+windowId+"-socialcontainerajaxloading").hide();
			$("#"+windowId+"-mediacontainerajaxloading").hide();
			//Opens the details container panel
			$("#"+windowId+"-detailscontainer").show("blind");
			//Opens the media container
			if(currentAlert.multimedia!=""){
				var mediaString='<div data-url="nodes/video.htm?url='+currentAlert.multimedia+'" data-title="Media" class="new-window map-text-container-link" title="Media">- Link</div>';
				$("#"+windowId+"-mediacontainer").html(mediaString);
			} else {
				$("#"+windowId+"-mediacontainer").html("No Media attached");
			}
			$("#"+windowId+"-mediacontainer").show("blind");
		});
		writeLog("Opened Alert details from Node "+nodeId, windowId, "debug");
	});
	
	//Event triggered when you click on a log in the logs area
	$(document).on("click", ".log-click", function (event) {
		var str="#"+$(this).attr('id')+"-open";
		$(str).toggle("blind");
	});
	
	
	$(document).on("click", ".show-tweets-details", function (event) {
		var windowId=$(this).data("windowid");
		var longitude=$(this).data('longitude');
		var lat=$(this).data('lat');
		var area=$(this).data('area');
		showTweets(windowId, longitude, lat, area);
		writeLog("Opened Tweets within area around Node "+$(this).data('node-id'), windowId, "debug");
	});
});