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
	<link rel="stylesheet" href="css/style.css" />
	<script type="text/javascript" src="js/nicEdit.js"></script>
	<script type="text/javascript" src="js/jquery-1.9.0.js"></script>
	<script src="js/jquery.validationEngine-en.js" type="text/javascript" charset="utf-8"></script>
	<script src="js/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>
	<link rel="stylesheet" href="css/validationEngine.jquery.css" type="text/css"/>
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
		<td><form:label path="name"><spring:message code="label.name"/>* </form:label></td>
		<td><form:input path="name" class="validate[required]" /></td> 
	</tr>
	<tr>
		<td><form:label path="description"><spring:message code="label.description"/></form:label></td>
		<td><textarea id="description" name="description"  placeholder="Description" style="width:680px;" >${dish.description}</textarea></td>
	</tr>
	<tr>
		<td><form:label path="shortDescription">Short Description</form:label></td>
		<td><textarea id="shortDescription" name="shortDescription"  placeholder="Short Description" style="width:680px;">${dish.shortDescription}</textarea></td>
	</tr>
	
	<tr>
		<td><form:label path="price">Price* </form:label></td>
		<td><form:input path="price" class="validate[required]" /></td>
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
		<td><form:label path="disabled">Disabled</form:label></td>
		<td>
		<c:choose>
			<c:when test="${dish.disabled}"><input type="checkbox" id="disabled" name="disabled" checked /></c:when>
			<c:otherwise><input type="checkbox" id="disabled" name="disabled" /></c:otherwise>
		</c:choose>
		
		
		</td>
	</tr>
	<!-- Dish active days -->
	<tr>
		<td><form:label path="dishActiveDays">Dish Active days</form:label></td>
		<td>
		<c:forEach items="${weekdayFlags}" var="weekdayFlag">
			${weekdayFlag.weekdayCode}
				<c:choose>
					<c:when test="${dish.dishActiveDays[weekdayFlag.index]}">
						<input type="checkbox" id="dishActiveDays[${weekdayFlag.index}]" name="dishActiveDays[${weekdayFlag.index}]" checked />
					</c:when>
					<c:otherwise>
						<input type="checkbox" id="dishActiveDays[${weekdayFlag.index}]" name="dishActiveDays[${weekdayFlag.index}]" />
					</c:otherwise>
				</c:choose>
			
		</c:forEach>
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
	<td>Current Time : ${currTime}</td>
	</tr>
	
	<tr>
	<td>Happy hour</td>
	</tr>
	<tr>
	
		<td><form:label path="happyHourEnabled">Enabled</form:label></td>
		<td>
		<c:choose>
			<c:when test="${dish.happyHourEnabled}"><input type="checkbox" id="happyHourEnabled" name="happyHourEnabled" checked /></c:when>
			<c:otherwise><input type="checkbox" id="happyHourEnabled" name="happyHourEnabled" /></c:otherwise>
		</c:choose>
		</td>
	</tr>
	<!-- Happy Hour active days -->
	<tr>
		<td><form:label path="happyHourActiveDays">Happy hour Active days</form:label></td>
		<td>
		<c:forEach items="${weekdayFlags}" var="weekdayFlag">
			${weekdayFlag.weekdayCode}
			
				<c:choose>
					<c:when test="${dish.happyHourActiveDays[weekdayFlag.index]}">
						<input type="checkbox" id="happyHourActiveDays[${weekdayFlag.index}]" name="happyHourActiveDays[${weekdayFlag.index}]" checked />
					</c:when>
					<c:otherwise>
						<input type="checkbox" id="happyHourActiveDays[${weekdayFlag.index}]" name="happyHourActiveDays[${weekdayFlag.index}]" />
					</c:otherwise>
				</c:choose>
			
		</c:forEach>
		</td>
	</tr>
	
	<tr>
		<td><form:label path="happyHourStartHour">Happy Hour Start</form:label></td>
		<td>
		Hour
		<select name="happyHourStartHour">
			 
			<c:forEach items="${hours}" var="hour">
				<c:choose>
					<c:when test="${dish.happyHourStartHour == hour}">
						<option value="${hour}" selected>${hour}</option>
					</c:when>
					<c:otherwise>
						<option value="${hour}">${hour}</option>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</select>
		Min 
		<select name="happyHourStartMin">
			<c:forEach items="${mins}" var="min">
				<c:choose>
					<c:when test="${dish.happyHourStartMin == min}">
						<option value="${min}" selected>${min}</option>
					</c:when>
					<c:otherwise>
						<option value="${min}">${min}</option>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</select>
		</td>
	</tr>
	<tr>
		<td><form:label path="happyHourEndHour">Happy Hour End</form:label></td>
		<td>
		Hour 
		<select name="happyHourEndHour">
			<c:forEach items="${hours}" var="hour">
				<c:choose>
					<c:when test="${dish.happyHourEndHour == hour}">
						<option value="${hour}" selected>${hour}</option>
					</c:when>
					<c:otherwise>
						<option value="${hour}">${hour}</option>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</select>
		Min 
		<select name="happyHourEndMin">
			<c:forEach items="${mins}" var="min">
				<c:choose>
					<c:when test="${dish.happyHourEndMin == min}">
						<option value="${min}" selected>${min}</option>
					</c:when>
					<c:otherwise>
						<option value="${min}">${min}</option>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</select>
		</td>
	</tr>
	<tr>
		<td><form:label path="happyHourPrice">Happy hour Price </form:label></td>
		<td><form:input path="happyHourPrice" /></td>
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
		<td><img height="200" width="200" src="${dish.imageUrl}" /></td>
		<td>${dish.price}</td>
		<td><button type="button" onclick="deleteDish(${dish.dishId});">delete</button></td>
		<td><button type="button" onclick="window.location.href='dish/edit/${dish.dishId}';">edit</button></td>
	</tr>
</c:forEach>
</table>
</c:if>


</body>
<script>
	$(function() {
		$("#dish").validationEngine();
	});
</script>
</html>
