/**
 * This library contains a function which checks wheter the user is still logged in. 
 * If it's not it will reload the page, redirecting the user to the login form.
 */
function refreshPage(){
	$("<div></div>").load("refresh.htm", function( response, status, xhr ) {
		if(xhr.status==401){
			alert("You are not logged in, you will be redirected to the login page now");
			location.reload();
		}
	});
	setTimeout(function(){refreshPage();}, 5000*60);
}