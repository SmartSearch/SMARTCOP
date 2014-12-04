<%@ include file="/WEB-INF/jsp/include/header.jsp" %>
<c:if test="${not empty error}">
		<div class="errorblock" style="position: absolute; bottom: 80px;">
			Your login attempt was not successful, try again.<br /> Cause :
			${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
		</div>
</c:if>
<div id="logindialog" title="Login" style="overflow: hidden">
<form name='f' action='<c:url value='j_spring_security_check' />' method='POST'>
 <table>
    <tr><td>User:</td><td><input type='text' name='j_username' value=''></td></tr>
    <tr><td>Password:</td><td><input type='password' name='j_password'/></td></tr>
    <tr><td colspan='2'><input name="submit" type="submit" value="Login"/></td></tr>
  </table>
</form>
</div>
<script>
$(document).ready(function(){
	$( window ).resize(function() {
		//Keeps the login dialog in the center
		$("#logindialog").dialog('option', 'position', ["center", "center"]);
	});
	//Creates the login dialog at the center of the page
	$("#logindialog").dialog({
		position: ["center", "center"],
		draggable: false,
		closeOnEscape: false,
		resizable: false,
	}).dialogExtend({"closable" : false});
});
</script>
<div style="position: absolute; bottom: 0; right: 0; text-align: right; background-color: #FFFFFF; width: 100%">
<div style="background: url('img/s3log-logo.png') no-repeat; height: 80px; width: 211px; float: right;"></div>
<div style="background: url('img/smart-logo.png') no-repeat; height: 67px; width: 265px; float: right; margin-right: 15px;"></div>
</div>
<%@ include file="/WEB-INF/jsp/include/footer.jsp" %>