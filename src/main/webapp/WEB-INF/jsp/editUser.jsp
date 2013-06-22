<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
	<title>Edit User</title>
	
	<style type="text/css">
		body {
			font-family: sans-serif;
		}
		.data, .data td {
			border-collapse: collapse;
			width: 100%;
			border: 1px solid #aaa;
			margin: 2px;
			padding: 2px;
		}
		.data th {
			font-weight: bold;
			background-color: #5C82FF;
			color: white;
		}
	</style>
	<base href="${pageContext.request.contextPath}/"/>
	
</head>
<body>
<a href="menu/" style="float: right;">Return to Menus</a>
<hr/>
<div style="color:red;">${errorMsg}</div>
<h3>Edit User</h3>

<form:form method="post" action="user/update.html" commandName="user" enctype="multipart/form-data">

	<form:hidden path="userId"/>
	<form:hidden path="username"/>
	<form:hidden path="passwordHash"/>
	<form:hidden path="businessPortraitImageUrl"/>
	<form:hidden path="businessLandscapeImageUrl"/>
	<table>
	
	<tr>
		<td><form:label path="firstName">First Name</form:label></td>
		<td><form:input path="firstName" /></td> 
	</tr>
	<tr>
		<td><form:label path="lastName">Last Name</form:label></td>
		<td><form:input path="lastName" /></td>
	</tr>
	<tr>
		<td><form:label path="businessName">Business Name</form:label></td>
		<td><form:input path="businessName" /></td>
	</tr>
	
	
	<tr>
		<td><form:label path="businessPortraitImageUrl">
		Business Portrait Image
		<c:choose>
			<c:when test="${fn:startsWith(user.businessPortraitImageUrl, 'http://')}">(${user.businessPortraitImageUrl})</c:when>
			<c:when test="${fn:startsWith(user.businessPortraitImageUrl, '/')}">(${user.businessPortraitImageUrl})</c:when>
		</c:choose> 
		</form:label></td>
		<td><input type="file" name="files[0]"/>
		<form:errors path="businessPortraitImageUrl" style="color:red;"/> </td>
	</tr>
	<tr>
		<td><form:label path="businessLandscapeImageUrl">
		Business Landscape image
		<c:choose>
			<c:when test="${fn:startsWith(user.businessLandscapeImageUrl, 'http://')}">(${user.businessLandscapeImageUrl})</c:when>
			<c:when test="${fn:startsWith(user.businessLandscapeImageUrl, '/')}">(${user.businessLandscapeImageUrl})</c:when>
		</c:choose> 
		</form:label></td>
		<td><input type="file" name="files[1]"/>
		<form:errors path="businessLandscapeImageUrl" style="color:red;"/> </td>
	</tr>
	<tr>
		<td colspan="2">
			<input type="submit" value="Save User"/>
			<button type="button" onclick="document.location.href='/'">Cancel</button>
		</td>
	</tr>
</table>	
</form:form>
<hr/>


</body>
</html>
