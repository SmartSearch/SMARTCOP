/**
 * This library contains an array of initialization functions and 
 * event-triggered functions to be called and/or attached when a new window of a certain tipe is created
 * These functions are usually used to render the dialog and refresh its contents when a certain event is triggered
 * (Such as resize or minimiza the window).
 * This is needed because you can't just put the javascript in the pages that are loaded into the dialogs because
 * they won't be able to tell into which window they are
 * see the window-opener library for more details
 * @author Wladimir Totino
 */
var gMap = google.maps;
var initFunctions={};
//Nodes Map functions
initFunctions["nodeslistmain"]=initAlertsWindow;
initFunctions["nodeslistmain-resize"]=refreshMap;
initFunctions["nodeslistmain-maximize"]=initFunctions["nodeslistmain-resize"];
initFunctions["nodeslistmain-restore"]=restoreMap;
initFunctions["nodeslistmain-minimize"]=initFunctions["nodeslistmain-resize"];

initFunctions["nodedetailsvideo"]=initVideoWindow;
