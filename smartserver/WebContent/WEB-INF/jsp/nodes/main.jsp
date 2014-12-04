<%@ include file="/WEB-INF/jsp/include/windowheader.jsp" %>
<div class="init-window">nodeslistmain</div>

<div id="mapcontainer" class="changeid map-window-container" style="overflow: hidden;">
</div>
<div id="logscontainer" class="changeid map-logs-container">
</div>
<div id="maptabs" class="changeid map-tabs-container black5bg ui-corner-all">
<div id="messagescontainer-handle" class="changeid map-messages-handle" style="cursor: move; height: 25px;">
<button id="closemessagescontainer" class="changeid close-socialcontainer ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only ui-dialog-titlebar-close" title="close" style="position: relative; float: right; top: auto; right: auto; margin: 5px;">
<span class="ui-button-icon-primary ui-icon ui-icon-minus"></span>
<span class="ui-button-text">minimize</span>
</button>
</div>
<div style="height: 30px;"></div>
<div id="edgenodeslistcontainer" class="changeid map-messages-container" style="display: none;">
<div id="edgenodeslist" class="changeid map-alerts-container map-nodes-container monitor-text">
Locations List<br/>
</div>
</div> 
<div id="messagescontainer" class="changeid map-messages-container">

<div id="messagescontainercontent" class="changeid">
<div style="clear: both; width: 100%">
<div id="alerts" class="details-area-button show-details-area changeid ui-corner-top">^</div>
<div id="alertsNewWindow" data-linked="alertscontainer" data-title="Alerts" title="Show current data in a new window" class="details-area-button map-details-open-new-window changeid ui-corner-top" style="float: left">+</div>
<div id="alertscontainerajaxloading" class="changeid ajax-loading" style="float: right; margin-top: 3px;"></div> 
<div class="map-details-title">Events Log</div>
</div>
<div class="map-border-text-container">
<div id="alertscontainer" class="changeid map-alerts-container map-text-container">
</div>
</div>

<div style="clear: both; width: 100%">
<div id="details" class="details-area-button show-details-area changeid ui-corner-top">^</div>
<div id="detailsNewWindow" data-linked="detailscontainer" data-title="Details" title="Show current data in a new window" class="details-area-button map-details-open-new-window changeid ui-corner-top" style="float: left">+</div>
<div id="socialcontainerajaxloading" class="changeid ajax-loading" style="float: right; margin-top: 3px;"></div> 
<div class="map-details-title">Details</div>
</div>

<div class="map-border-text-container">
<div id="detailscontainer" style="display: none;" class="changeid map-details-container map-text-container">
</div>
</div>

<div style="clear: both; width: 100%">
<div id="media" class="details-area-button show-details-area changeid ui-corner-top">^</div>
<div id="mediaNewWindow" data-linked="mediacontainer" data-title="Media" title="Show current data in a new window" class="details-area-button map-details-open-new-window changeid ui-corner-top" style="float: left">+</div>
<div id="mediacontainerajaxloading" class="changeid ajax-loading" style="float: right; margin-top: 3px;"></div> 
<div class="map-details-title">Media</div>
</div>


<div class="map-border-text-container">
<div id="mediacontainer" style="display: none;" class="changeid map-media-container map-text-container">
</div>
</div>

<div style="clear: both; width: 100%">
<div id="twitter" title="Open/Close details area" class="details-area-button show-details-area changeid ui-corner-top">^</div>
<div id="twitterNewWindow" data-linked="twittercontainer" data-title="Twitter Board" title="Show current data in a new window" class="details-area-button map-details-open-new-window changeid ui-corner-top" style="float: left">+</div>
<div id="twittercontainerajaxloading" class="changeid ajax-loading" style="float: right; margin-top: 3px;"></div> 
<div class="map-details-title">Twitter Board</div>
</div>

<div class="map-border-text-container">
<div id="twittercontainer" style="display: none; overflow-x: none" class="changeid map-twitter-container map-text-container">
</div>
</div>
<br/>
</div>
</div>
</div>