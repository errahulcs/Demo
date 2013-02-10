<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<title>Dish Manager</title>
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
<h3>Add Dish</h3>

<form:form method="post" action="add.html" commandName="dish">

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
		<td><form:label path="price">Price</form:label></td>
		<td><form:input path="price" /></td>
	</tr>
	<tr>
		<td><form:label path="category"><spring:message code="label.category"/></form:label></td>
		<td>
			<select name="categoryId" >
            <c:forEach items="${categoryList}" var="category">
               <option value="${category.categoryId}">${category.name} - ${category.description}</option>
            </c:forEach>
        	</select>
		</td>
	</tr>
	<tr>
		<td><form:label path="imageUrl"><spring:message code="label.imageUrl"/></form:label></td>
		<td><form:input path="imageUrl" /></td>
	</tr>
	
	<tr>
		<td colspan="2">
			<input type="submit" value="<spring:message code="label.adddish"/>"/>
		</td>
	</tr>
</table>	
</form:form>
<hr/>
	
<h3>Dishes</h3>
<c:if  test="${!empty dishList}">
<table class="data">
<tr>
	<th>Name</th>
	<th>Description</th>
	<th>Category</th>
	<th>Image</th>
	<th>Price</th>
	<th>&nbsp;</th>
</tr>
<c:forEach items="${dishList}" var="dish">
	<tr>
		<td>${dish.name}</td>
		<td>${dish.description}</td>
		<td>${dish.category.name} - ${dish.category.description}</td>
		<td><img src="${dish.imageUrl}" /></td>
		<td>${dish.price}</td>
		<td><a href="delete/${dish.dishId}">delete</a></td>
	</tr>
</c:forEach>
</table>
</c:if>


</body>
</html>
