<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<title>Menu Card</title>
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


<b>${menu.name}</b>
<br/>
<b>${menu.description}</b>
<hr/>
<table class="data">
<tr>
	<th>Name</th>
	<th>Description</th>
	<th>Category</th>
</tr>
<c:forEach items="${menu.dishes}" var="dish">
	<tr>
		<td>${dish.name}</td>
		<td>${dish.description}</td>
		<td>${dish.category.name} - ${dish.category.description}</td>
	</tr>
</c:forEach>
</table>



</body>
</html>
