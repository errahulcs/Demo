<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
			text-align:center;
			vertical-align:middle;
		}
		.data th {
			font-weight: bold;
			background-color: #5C82FF;
			color: white;
		}
	</style>
	<base href="${pageContext.request.contextPath}/"/>
	<link rel="stylesheet" href="css/style.css" />
	<script type="text/javascript" src="js/jquery-1.9.0.js"></script>
	<script src="js/jquery.validationEngine-en.js" type="text/javascript" charset="utf-8"></script>
	<script src="js/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>
	<link rel="stylesheet" href="css/validationEngine.jquery.css" type="text/css"/>
	<script type="text/javascript">
		function deleteDeliveryArea(deliveryAreaId) {
			if (confirm('Do you really want to delete this Delivery Area')) {
				window.location.href = 'restaurant/deleteDeliveryArea/' + deliveryAreaId;
			} 
			
		}
	</script>
	
</head>
<body>
<a href="menu/" style="float: right;">Return to Menus</a>
<hr/>
<div style="color:red;">${errorMsg}</div>
<h3>Add Delivery Area</h3>

<form:form method="post" action="restaurant/addDeliveryArea.html" commandName="deliveryArea">

	<form:hidden path="id"/>
	<form:hidden path="userId" value='<%=request.getSession().getAttribute("userId")%>'/>
	<table>
	
		<tr>
			<td><form:label path="name"><spring:message code="label.name"/>* </form:label></td>
			<td><form:input path="name" class="validate[required]" /></td> 
		</tr>
		
		<tr>
		<td colspan="2">
			
				<c:choose>
					<c:when test="${!empty deliveryArea.id}"><input type="submit" value="Save Delivery Area"/><button type="button" onclick="document.location.href='restaurant/deliveryAreas'">Cancel</button></c:when>
					<c:otherwise><input type="submit" value="Add DeliveryArea"/><input type="reset" value="Cancel"></c:otherwise>
				</c:choose>
			
		</td>
		</tr>
	</table>	
</form:form>
<hr/>
	
<h3>Delivery Areas</h3>
<c:if  test="${!empty deliveryAreaList}">
<table class="data">
<tr>
	<th>Name</th>
	<th>&nbsp;</th>
	<th>&nbsp;</th>
</tr>
<c:forEach items="${deliveryAreaList}" var="deliveryArea">
	<tr>
		<td style="width:30%;">${deliveryArea.name}</td>
		<td><button type="button" onclick="deleteDeliveryArea(${deliveryArea.id});">delete</button></td>
		<td><button type="button" onclick="window.location.href='restaurant/editDeliveryARea/${deliveryArea.id}';">edit</button></td>
	</tr>
</c:forEach>
</table>
</c:if>


</body>
<script>
	$(function() {
		$("#deliveryArea").validationEngine();
	});
</script>
</html>
