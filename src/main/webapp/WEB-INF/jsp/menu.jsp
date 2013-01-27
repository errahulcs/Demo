<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<title>Menu Manager</title>
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
</head>
<body>
<hr/>
<h3>Add Menu</h3>

<form:form method="post" action="add.html" commandName="menu">

	<table>
	<tr>
		<td><form:label path="name"><spring:message code="label.name"/></form:label></td>
		<td><form:input path="name" /></td> 
	</tr>
	<tr>
		<td><form:label path="description"><spring:message code="label.description"/></form:label></td>
		<td><form:input path="description" /></td>
	</tr>
	
	<tr>
		<td><form:label path="dishes"><spring:message code="label.dishes"/></form:label></td>
		<td>
		
            
               
            
        	<select name="dishIds" multiple="multiple">
			<c:forEach items="${dishList}" var="dish">
				<option value="${dish.dishId}">${dish.name}</option>
			</c:forEach>
			</select>
		</td>
	</tr>
	
	<tr>
		<td colspan="2">
			<input type="submit" value="<spring:message code="label.addmenu"/>"/>
		</td>
	</tr>
</table>	
</form:form>

<hr/>
<h3>Menus</h3>
<c:if  test="${!empty menuList}">
<table class="data">
<tr>
	<th>Name</th>
	<th>Description</th>
	<th>&nbsp;</th>
</tr>
<c:forEach items="${menuList}" var="menu">
	<tr>
		<td>${menu.name}</td>
		<td>${menu.description}</td>
		<td><a href="delete/${menu.menuId}">delete</a></td>
	</tr>
</c:forEach>
</table>
</c:if>


</body>
</html>
