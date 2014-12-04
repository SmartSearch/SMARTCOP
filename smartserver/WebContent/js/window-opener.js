/**
 * This library contains all the functions and variables to Open, organize and handle the multiple dialogs system
 * of the SMART application and the minimized dialogs bar at the bottom of the application
 * @author Wladimir Totino 
 */

var maxContainerHeight;
var maxContainerWidth;
var minimizeHeight;
var minimizeSingleHeight;
var minimizePage=0;
var minimizePosition=-1;
var windowCount=1;
$(document).ready(function(){
	/*Event triggered when you click on something that will open a new window
	The HTML tag must contain 2 "data" attributes: data-url and data-title 
	which are, respectively, the url from where you get the data to put inside the dialog
	and the window title
	*/
	$(document).on("click", ".new-window", function (event) {
		createDialog($(this).data('url'), $(this).data('title'), true); //"nodes/main.htm"
	});
	
	//Event triggered whenever the browser windows is being resized
	$( window ).resize(function() {
		$("#main-container").height($(window).height()-$("#main-bar").height());
		$("#main-container").width($(window).width()-4);
		maxContainerHeight = $("#main-container").height();
		maxContainerWidth = $("#main-container").width();
		$(".ui-dialog-maximized").each(function(i) { 
			$(this).dialogExtend("maximize");
		});
		moveMinimizedBar();
	});
	//Sets height and width variables
	$("#main-container").height($(window).height()-$("#main-bar").height());
	$("#main-container").width($(window).width()-4);
	maxContainerHeight = $("#main-container").height();
	maxContainerWidth = $("#main-container").width();
	minimizeHeight = $("#dialog-extend-fixed-container").height();
	minimizeSingleHeight=minimizeHeight;
	
	/*
	 * Event triggered wheter you click on a link that you want to be loaded into a dialog window rather than as a 
	 * normal link (the link must have the ajlink class)
	 * 
	 */ 
	$(document).on("click", ".ajlink", function (event) {
		var link=$(this);
		var id=getWrappingDialogId($(this));
		loadContent(id, link.attr("href"));
        event.preventDefault();
    });
	
	/*
	 * Event triggered wheter you submit a form and you want the returning page to be loaded into a dialog window rather
	 * than a classical page reload (the form tag must have the ajform class) 
	 */
	$(document).on("submit", ".ajform", function (event) {
		var frm=$(this);
		var id=getWrappingDialogId($(this));
		var action=frm.attr('action');
		$("#"+id+"-loading").show();
        $.ajax({
            type: frm.attr('method'),
            url: action,
            data: frm.serialize(),
        }).always(function( data ) {
        	$("#"+id+"-content").html(data);
        	postLoad(id, action);
        	$("#"+id+"-loading").hide();
        });
        event.preventDefault();
    });
});

/**
 * returns the dialog element of the current window
 * @param element
 * @returns
 */
function getWrappingDialog(element){
	return element.closest('.ui-dialog-content');
}

/**
 * returns the dialog element's id attribute of the current window
 * @param element
 * @returns
 */
function getWrappingDialogId(element){
	return getWrappingDialog(element).attr('id');
}

/**
 * This function generates the dialog, attaching all the events and the data from the page spe
 * @param url
 * @param title
 * @param loadUrl
 */
function createDialog(url, title, loadUrl){
	var id=getTime()+"-"+getRandom(10000, 99999);
	//dialog body
	var myString = "<div id='"+id+"' class='custom-dialog-wrapper'>"+
		"<div id='"+id+"-content' class='inner-dialog-content'>Loading..."+
		"</div>"+
		"<div>"+
		"<div id='"+id+"-status-bar-footer' class='footerbar ui-corner-all ui-corner-top ui-corner-right ui-corner-tr'>"+
		"<div class='leftresize'></div>"+
		"<div style='margin-left: 20px; display: none' id='"+id+"-loading'>Loading...</div>"+
		"<div class='rightresize'></div>"+
		"</div>"+
		"</div>"+
		"</div>";
	var $jQueryObject = $($.parseHTML(myString));
	//Generates the dialog
	var dialog=$jQueryObject.dialog({
		position: {my: "center", at: "center", of: "#main-container"},
		minWidth: 560,
		minHeight: 405,
		resize: function(event, ui) { 
			resizeContent(event, $(this), id);
			var initFuncName=getInitFuncName(id);
			executeInitFunction(initFuncName+"-resize", id);
		},
		close: function(event, ui) {
			var initFuncName=getInitFuncName(id);
			executeInitFunction(initFuncName+"-close", id);
			//Destroys all the children dialogs
			$("."+id+"-children-dialog").dialog("destroy").remove();
			$(this).dialog('destroy').remove();
		}
	});
	//Adds the close, maximize, minimize and restore functions to the dialog, and relative events
	dialog.dialogExtend({
		"closable" : true,
		"maximizable" : true,
		"minimizable" : true,
		"collapsable":false,
		"dblclick" : "minimize",
		"titlebar" : "transparent",
		"beforeMinimize" : function(evt, dlg) {
			$('#'+id).dialog("option", "minWidth", 100);
		},
		"maximize" : function(evt, dlg){
			$("#"+id+"-status-bar-footer").height($("#main-bar").height()-15);
			resizeContent(evt, $(this), id);
			var initFuncName=getInitFuncName(id);
			executeInitFunction(initFuncName+"-maximize", id);
			//Shows all the children dialogs
			$("."+id+"-children-dialog").parent().show();
			$("."+id+"-children-dialog").dialog("moveToTop");
		},
		"restore" : function(evt, dlg){
			$('#'+id).dialog("option", "minWidth", 560);
			$("#"+id+"-status-bar-footer").height(25);
			resizeContent(evt, $(this), id);
			constrainDialog(id);
			var initFuncName=getInitFuncName(id);
			executeInitFunction(initFuncName+"-restore", id);
			//Shows all the children dialogs
			$("."+id+"-children-dialog").parent().show();
			$("."+id+"-children-dialog").dialog("moveToTop");
			moveMinimizedBar();
		},
		"minimize" : function(evt, dlg){			
			if(minimizePage==0){
				minimizePage=1;
				minimizeSingleHeight=$("#dialog-extend-fixed-container").height();
				minimizeHeight=minimizeSingleHeight;
			}
			//Hides all the children dialogs when the dialog is minimized
			$("."+id+"-children-dialog").parent().hide();
			moveMinimizedBar();
			var initFuncName=getInitFuncName(id);
			executeInitFunction(initFuncName+"-minimize", id);
		}
    });
	dialog.parent().attr('id', id+"-parent");
	//Adds constrains to the dialog for resizing and moving
	constrainDialog(id);
	dialog.dialog( "option", "width", maxContainerWidth*0.56);
	dialog.dialog( "option", "height", maxContainerHeight*0.86);
	dialog.dialog('option', 'title', title);
	
	windowCount++;
	//Slightly moves the dialog so they aren't all opened in the same place
	dialog.closest('.ui-dialog').animate({
		left: "+="+getRandom(-maxContainerWidth*0.1, maxContainerWidth*0.1),
		top:  "+="+getRandom(-maxContainerHeight*0.1, maxContainerHeight*0.1)
	}, 0);
	resizeContent(null, dialog, id);
	loadContent(id, url, loadUrl);
}

/**
 * Puts some content inside a dialog with a certain id 
 * @param id
 * @param url
 * @param loadUrl
 */
function loadContent(id, url, loadUrl){
	//If loadUrl is true, then it will load a remote page, else url is a string containing the content of the page.
	$("#"+id+"-loading").show();
	if(loadUrl){
		$("#"+id+"-content").load(url, function( response, status, xhr ) {
			//if you are trying to load a page while unauthorized
			if(xhr.status==401){
				alert("You are not logged in, you will be redirected to the login page now");
				location.reload();
			}
			postLoad(id, url);
			$("#"+id+"-loading").hide();
		});
	} else {
		$("#"+id+"-content").html(url);
		postLoad(id, url);
		$("#"+id+"-loading").hide();
	}
}

/**
 * Actions to do after content is being loaded
 * @param id
 * @param url
 */
function postLoad(id, url){
	//Inserts a unquie id for the current window to all the objects with class "changeid"
	$("#"+id+"-content").find(".changeid").each(function(i) { 
	    $(this).attr('id', id+"-"+$(this).attr('id'));
	    $(this).data("window-id", id);
	    $(this).removeClass('changeid');
	});
	//Finds the function that's used to initialize the dialog's content and then executes it
	var initFuncName=getInitFuncName(id);
	executeInitFunction(initFuncName, id);
}

/**
 * executes the function that's used to initialize a dialog's content, eventually returning data from the function itself
 * @param initFuncName
 * @param id
 * @returns
 */
function executeInitFunction(initFuncName, id){
	if(initFunctions.hasOwnProperty(initFuncName)){
		return initFunctions[initFuncName](id);
	}
}

/**
 * returns the initialization function name that's used for the dialog currently opened
 * TODO: the function name should be put inside a data attribute and not in a hidden div, this was a very wrong choice
 * @param id
 * @returns
 */
function getInitFuncName(id){
	return $("#"+id+"-content").children(".init-window").html();
}

/**
 * resizes the content of a dialog
 * @param event
 * @param element
 * @param id
 */
function resizeContent(event, element, id){
	$("#"+id+"-content").height(element.outerHeight()-$("#"+id+"-status-bar-footer").height()-12);
}

/** 
 * Adds contrasining options for resizing and dragging a dialog
 * @param id
 */
function constrainDialog(id){
	$('#'+id+"-parent").resizable( "option", "containment", $("#main-container") );
	$('#'+id+"-parent").draggable( "option", "containment", $("#main-container") );
}

/**
 * Since the extenddialog plugin minimized bar works by moving up wheter a row is full, we edit the behavior by 
 * setting the size and position whenever it gets modified 
 */
function moveMinimizedBar(){
	var minimizedDialogsContainer=$("#dialog-extend-fixed-container");
	minimizedDialogsContainer.animate({
		top: $(window).height()-minimizeSingleHeight
	}, 0);
}