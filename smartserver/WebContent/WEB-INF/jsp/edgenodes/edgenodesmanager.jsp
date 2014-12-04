<%@ include file="/WEB-INF/jsp/include/windowheader.jsp" %>    
<h3 style="text-align: center;">Edge Nodes</h3>
<form:form method="post" action="edgenodes/addedgenode.htm?id=${edgeNode.id}" commandName="edgeNode" cssClass="ajform">
<table style="width: 95%; padding: 3px;" align="center">
<tr>
	<th>Id</th>
    <th>Name</th>
    <th>URL</th>
    <th style="min-width: 150px">&nbsp;</th>
</tr>
<c:forEach items="${edgeNodesList}" var="element">
<c:choose>
	<c:when test="${(edit && edgeNode.id!=element.id) || !edit}">
    <tr>
        <td>${element.id}</td>
        <td>${element.name}</td>
        <td>${element.url}</td>
        <td>
        <a class="ajlink" href="edgenodes/delete/${element.id}.htm">Delete</a> 
        <a class="ajlink" href="edgenodes/edit/${element.id}.htm">Edit</a>
        </td>
    </tr>
    </c:when>
    <c:otherwise>
    <tr>
    	<td>${element.id}</td>
        <td><form:input path="name" lenght="255" style="width: 90%" /></td>
        <td><form:input path="url" style="width: 90%" /></td>
        <td>
        <input type="submit" value="Edit"/> 
        <a href="edgenodes/edgenodesmanager.htm" class="ajlink"><button>Back</button></a>
        </td>
    </tr>
    </c:otherwise>
</c:choose>
</c:forEach>
<c:if test="${!edit}">
    <tr>
    	<td>&nbsp;</td>
        <td><form:input path="name" lenght="255" style="width: 90%" /></td>
        <td><form:input path="url" style="width: 90%" /></td>
        <td><input type="submit" value="Add"/></td>
    </tr>
</c:if>
</table>
</form:form>