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
		}
		.data th {
			font-weight: bold;
			background-color: #5C82FF;
			color: white;
		}
	</style>
	<base href="${pageContext.request.contextPath}/"/>
	<script type="text/javascript" src="js/nicEdit.js"></script>
	<script type="text/javascript">
		bkLib.onDomLoaded(function() { 
			//nicEditors.allTextAreas()
			var nicEditorInstance = new nicEditor({fullPanel : false, iconsPath: 'images/nicEditorIcons.gif', buttonList : ['bold','italic','underline','left','center','right', 'justify', 'ol', 'ul', 'subscript', 'superscript', 'strikethrough', 'removeformat', 'indent', 'outdent', 'hr', 'forecolor', 'bgcolor', 'fontSize', 'fontFamily', 'fontFormat']});
			nicEditorInstance.panelInstance('shortDescription');
			nicEditorInstance.panelInstance('description');
		});
		function deleteDish(dishId) {
			if (confirm('Do you really want to delete this dish')) {
				window.location.href = 'dish/delete/' + dishId;
			} 
			
		}
	</script>
	
</head>
<body>
<a href="menu/" style="float: right;">Return to Menus</a>
<hr/>
<div style="color:red;">${errorMsg}</div>
<h3>Add Dish</h3>

<form:form method="post" action="dish/add.html" commandName="dish" enctype="multipart/form-data">

	<form:hidden path="dishId"/>
	<form:hidden path="imageUrl"/>
	<form:hidden path="userId" value='<%=request.getSession().getAttribute("userId")%>'/>
	<table>
	
	<tr>
		<td><form:label path="name"><spring:message code="label.name"/></form:label></td>
		<td><form:input path="name" /></td> 
	</tr>
	<tr>
		<td><form:label path="description"><spring:message code="label.description"/></form:label></td>
		<td><textarea id="description" name="description"  placeholder="Description" style="width:680px;">${dish.description}</textarea></td>
	</tr>
	<tr>
		<td><form:label path="shortDescription">Short Description</form:label></td>
		<td><textarea id="shortDescription" name="shortDescription"  placeholder="Short Description" style="width:680px;">${dish.shortDescription}</textarea></td>
	</tr>
	
	<tr>
		<td><form:label path="price">Price</form:label></td>
		<td><form:input path="price" /></td>
	</tr>
	
	<tr>
		<td><form:label path="dishType">Dish Type</form:label></td>
		<td><form:input path="dishType" /></td>
	</tr>
	
	<tr>
		<td><form:label path="vegetarian">Vegetarian</form:label></td>
		<td>
		<c:choose>
			<c:when test="${dish.vegetarian}"><input type="checkbox" id="vegetarian" name="vegetarian" checked/></c:when>
			<c:otherwise><input type="checkbox" id="vegetarian" name="vegetarian" /></c:otherwise>
		</c:choose>
		
		</td>
	</tr>
	
	<tr>
		<td><form:label path="alcoholic">Alcoholic</form:label></td>
		<td>
		<c:choose>
			<c:when test="${dish.alcoholic}"><input type="checkbox" id="alcoholic" name="alcoholic" checked /></c:when>
			<c:otherwise><input type="checkbox" id="alcoholic" name="alcoholic" /></c:otherwise>
		</c:choose>
		
		
		</td>
	</tr>
	
	<tr>
		<td><form:label path="imageUrl">
		<spring:message code="label.imageUrl"/>
		<c:choose>
			<c:when test="${fn:startsWith(dish.imageUrl, 'http://')}">(${dish.imageUrl})</c:when>
			<c:when test="${fn:startsWith(dish.imageUrl, '/')}">(${dish.imageUrl})</c:when>
		</c:choose> 
		</form:label></td>
		<td><input type="file" name="file"/>
		<form:errors path="imageUrl" style="color:red;"/> </td>
	</tr>
	
	<tr>
		<td colspan="2">
			
				<c:choose>
					<c:when test="${!empty dish.dishId}"><input type="submit" value="Save Dish"/><button type="button" onclick="document.location.href='dish/'">Cancel</button></c:when>
					<c:otherwise><input type="submit" value="Add Dish"/><input type="reset" value="Cancel"></c:otherwise>
				</c:choose>
			
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
	<th>Image</th>
	<th>Price</th>
	<th>&nbsp;</th>
	<th>&nbsp;</th>
</tr>
<c:forEach items="${dishList}" var="dish">
	<tr>
		<td>${dish.name}</td>
		<td>${dish.description}</td>
		<td><img src="${dish.imageUrl}" /></td>
		<td>${dish.price}</td>
		<td><button type="button" onclick="deleteDish(${dish.dishId});">delete</button></td>
		<td><a href="dish/edit/${dish.dishId}">edit</a></td>
	</tr>
</c:forEach>
</table>
</c:if>


</body>
</html>
