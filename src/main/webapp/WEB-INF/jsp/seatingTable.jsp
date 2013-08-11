<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
	<title>Seating Table Manager</title>
	
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
	<script type="text/javascript">
		function deleteSeatingTable(seatingTableId) {
			if (confirm('Do you really want to delete this Table')) {
				window.location.href = 'seatingTable/delete/' + seatingTableId;
			} 
			
		}
	</script>
</head>
<body>
<a href="menu/" style="float: right;">Return to Menus</a>
<hr/>
<div style="color:red;">${errorMsg}</div>
<h3>Add Seating Table</h3>

<form:form method="post" action="seatingTable/add.html" commandName="seatingTable" >

	<form:hidden path="seatingTableId"/>
	<form:hidden path="restaurantId"/>
	<form:hidden path="userId" value='<%=request.getSession().getAttribute("userId")%>'/>
	<table>
	
	<tr>
		<td><form:label path="name"><spring:message code="label.name"/></form:label></td>
		<td><form:input path="name" /></td> 
	</tr>
	
	<tr>
		<td><form:label path="seats">Number of seats</form:label></td>
		<td><form:input path="seats" /></td>
	</tr>
	<tr>
		<td><form:label path="description">Description</form:label></td>
		<td><form:input path="description" /></td>
	</tr>
	<tr>
		<td><form:label path="status">Status</form:label></td>
		<td>
		<select name="status">
			<c:forEach items="${statusTypes}" var="statusType">
				<c:choose>
					<c:when test="${statusType == seatingTable.status }">
						<option value="${statusType}" selected="selected">${statusType}</option>
					</c:when>
					<c:otherwise>
						<option value="${statusType}">${statusType}</option>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</select>
		</td>
	</tr>
	
	<tr>
		<td colspan="2">
			
				<c:choose>
					<c:when test="${!empty seatingTable.seatingTableId}"><input type="submit" value="Save Table"/><button type="button" onclick="document.location.href='seatingTable/'">Cancel</button></c:when>
					<c:otherwise><input type="submit" value="Add Table"/><input type="reset" value="Cancel"></c:otherwise>
				</c:choose>
			
		</td>
	</tr>
</table>	
</form:form>
<hr/>
	
<h3>Seating Tables</h3>
<c:if  test="${!empty seatingTableList}">
<table class="data">
<tr>
	<th>Name</th>
	<th>Seats</th>
	<th>Status</th>
	<th>&nbsp;</th>
	<th>&nbsp;</th>
</tr>
<c:forEach items="${seatingTableList}" var="seatingTab">
	<tr>
		<td>${seatingTab.name}</td>
		<td>${seatingTab.seats}</td>
		<td>${seatingTab.status}</td>
		<td><button type="button" onclick="deleteSeatingTable(${seatingTab.seatingTableId});">delete</button></td>
		<td><button type="button" onclick="window.location.href='seatingTable/edit/${seatingTab.seatingTableId}';">edit</button></td>
	</tr>
</c:forEach>
</table>
</c:if>


</body>
</html>
