<%@ include file="/WEB-INF/jsp/include/header.jsp" %>
<script>
//<![CDATA[
//The current arrow showed
var arrow;
//Wheter on not the buttons page is being opened or closed
var opening=false;
$(document).ready(function(){
	//Sets the arrow url to the current one (in some cases it may not be the one expected)
	arrow=$("#controlpanelarrow").attr("src");
	
	/**
	 * Changes the background image of the button when mouseover or mouseleave
	 */
	function changeButtonBackground(item, action){
		background=item.css('background-image');
		background = background.replace(/^url\(["']?/, '').replace(/["']?\)$/, '');
		if(action=="leave"){
			item.css({'background-image' : "url("+background.substring(0, background.length-5)+"n.png)"});
		} else {
			item.css({'background-image' : "url("+background.substring(0, background.length-5)+"h.png)"});
		}
	}
	
	//function that checks every 5 minutes wheter the user is logged in or not
	refreshPage();
	
	//Event triggered when clicking on the arrow that shows or hides the buttons panel
	$(document).on("click", ".open-control-panel", function (event) {
		if(!opening){
			opening=true;
			if(arrow=="arrowup.png"){
				arrow="arrowdown.png";
			} else {
				arrow="arrowup.png";
			}
			$("#controlpanelarrow").attr("src", "img/"+arrow);
			//When it goes up, it must be showed
			if(arrow!="arrowup.png"){
				$("#button-bar").css("z-index", 20000);
			}
			$("#button-bar").toggle( "drop", {direction:"down"}, 500, function(){
				if(arrow=="arrowup.png"){
					$("#button-bar").css("z-index", 9997);
				}
				opening=false;
			});
		}
	});
	
	//Event triggered when the moves goes on a button
	$(document).on("mouseenter", ".main-bar-button", function (event) {
		changeButtonBackground($(this), "hover");
	});
	
	//Event triggered when the moves leaves a button
	$(document).on("mouseleave", ".main-bar-button", function (event) {
		changeButtonBackground($(this), "leave");
	});
	
	//Event triggered when clicking the logout button
	$("#main-logout").click(function(){location.href="logout.htm";});
	//Opens a nodes map dialog when entering the page
	createDialog("nodes/main.htm", "SMART COP", true);
});
//]]>
</script>
<div id="main-container" style="width: 99.5%; height: 89%; position: absolute; top:0; bottom:0;"> 
</div>
<div id="button-bar" style="clear: both; position: fixed; bottom: 0; left: 25px; width: 100%; height: 50px; display:block; background-color: #222222; z-index: 20000; overflow: hidden;">
<div data-url="nodes/main.htm" data-title="SMART COP" class="new-window main-bar-button" title="Nodes Map" style="background: url('img/buttons/nodesmapn.png') no-repeat; background-size: 100%;"></div>
<div id="main-logout" class="main-bar-button" title="Logout" style="background: url('img/buttons/logoutn.png') no-repeat; background-size: 100%; cursor: pointer;"></div>
</div>
<div id="main-bar" style="position: fixed; bottom:0; left: 0; width: 100%; height: 50px; background-color: #000000; z-index: 9998; border-top: 1px solid #FFFFFF">
<div style="float: left; width: 25px; height: 100%; clear:both;">
<div class="open-control-panel" style="margin-top: 10px; cursor: pointer;" title="Open/Close Menu"><img id="controlpanelarrow" style="width: 75%; height: auto" src="img/arrowdown.png" border="0" /></div>
</div>
</div>
<%@ include file="/WEB-INF/jsp/include/footer.jsp" %>
