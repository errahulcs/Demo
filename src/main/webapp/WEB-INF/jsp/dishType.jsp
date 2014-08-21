<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<title>DishType Manager</title>
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
	<link rel="stylesheet" href="css/style.css" />
	
</head>
<body>
<a href="index.jsp" style="float: right;">Return to Home</a>
<hr/>
<h3>Add DishType</h3>

<form:form method="post" action="dishTypes/add.html" commandName="dishType">
	
	<form:hidden path="dishTypeId" />
	<form:hidden path="restaurantId" value='<%=request.getSession().getAttribute("userId")%>'/>
	<table>
	
	<tr>
		<td><form:label path="name"><spring:message code="label.name"/></form:label></td>
		<td><form:input path="name"  /></td> 
	</tr>
	
	<tr>
		<td colspan="2">
			<input type="submit" value="Add DishType"/>
		</td>
	</tr>
</table>	
</form:form>

<hr/>	
<h3>DishTypes</h3>
<c:if  test="${!empty dishTypeList}">
<table class="data">
<tr>
	<th>Name</th>
	<th>&nbsp;</th>
	<th>&nbsp;</th>
</tr>
<c:forEach items="${dishTypeList}" var="dishType">
	<tr>
		<td>${dishType.name}</td>
		<td><a href="dishTypes/delete/${dishType.dishTypeId}">delete</a></td>
		<td><a href="dishTypes/edit/${dishType.dishTypeId}">edit</a></td>
	</tr>
</c:forEach>
</table>
</c:if>


</body>
</html>
