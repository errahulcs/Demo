<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
	<title>Edit User</title>
	<base href="${pageContext.request.contextPath}/"/>
	<link rel="stylesheet" href="css/style.css" />
	
	<link rel="stylesheet" href="themes/base/jquery.ui.all.css" />	
	<script type="text/javascript" src="js/jquery-1.9.0.js"></script>
	<script type="text/javascript" src="js/ui/jquery-ui.js"></script>
	<script src="js/jquery.validationEngine-en.js" type="text/javascript" charset="utf-8"></script>
	<script src="js/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>
	<link rel="stylesheet" href="css/validationEngine.jquery.css" type="text/css"/>
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
		<td><form:label path="address1">Address</form:label></td>
		<td><form:input path="address1" class="validate[maxSize[100]]"/></td> 
	</tr>
	<tr>
		<td><form:label path="address2">Address</form:label></td>
		<td><form:input path="address2" class="validate[maxSize[100]]"/></td>
	</tr>
	<tr>
		<td><form:label path="city">City </form:label></td>
		<td><form:input path="city" class="validate[maxSize[100]]"/></td>
	</tr>
	<tr>
		<td><form:label path="country">Country</form:label></td>
		<td><form:input path="country" class="validate[maxSize[100]]"/></td>
	</tr>
	<tr>
		<td><form:label path="zip">Zip/Pincode</form:label></td>
		<td><form:input path="zip" class="validate[maxSize[10]]"/></td>
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
		<td><form:label path="appCacheIconUrl">
		Application Cache Icon
		<c:choose>
			<c:when test="${fn:startsWith(user.appCacheIconUrl, 'http://')}">(${user.appCacheIconUrl})</c:when>
			<c:when test="${fn:startsWith(user.appCacheIconUrl, '/')}">(${user.appCacheIconUrl})</c:when>
		</c:choose> 
		</form:label></td>
		<td><input type="file" name="files[2]"/>
		<form:errors path="appCacheIconUrl" style="color:red;"/> </td>
	</tr>
	<tr>
		<td><form:label path="buttonIconUrl">
		Button Icon
		<c:choose>
			<c:when test="${fn:startsWith(user.buttonIconUrl, 'http://')}">(${user.buttonIconUrl})</c:when>
			<c:when test="${fn:startsWith(user.buttonIconUrl, '/')}">(${user.buttonIconUrl})</c:when>
		</c:choose> 
		</form:label></td>
		<td><input type="file" name="files[3]"/>
		<form:errors path="buttonIconUrl" style="color:red;"/> </td>
	</tr>
	
	<tr><td></td></tr>
	<tr> <td><h3>Additional Charges:</h3></td></tr>
	
	<table class="data">
	<tr>
		<th>Name</th>
		<th>Charge Type</th>	
		<th>Value</th>
	</tr>
	<tr>
		<td><form:input path="additionalChargesName1" /></td>
		<td>
			<select name="additionalChargesType1">
				<c:forEach items="${chargeTypes}" var="chargeType">
					<c:choose>
						<c:when test="${chargeType == user.additionalChargesType1}">
							<option value="${chargeType}" selected="selected">${chargeType}</option>
						</c:when>
						<c:otherwise>
							<option value="${chargeType}">${chargeType}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</td>
		<td><form:input path="additionalChargesValue1" /></td>
	</tr>
	<tr>
		<td><form:input path="additionalChargesName2" /></td>
		<td>
			<select name="additionalChargesType2">
				<c:forEach items="${chargeTypes}" var="chargeType">
					<c:choose>
						<c:when test="${chargeType == user.additionalChargesType2}">
							<option value="${chargeType}" selected="selected">${chargeType}</option>
						</c:when>
						<c:otherwise>
							<option value="${chargeType}">${chargeType}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</td>
		<td><form:input path="additionalChargesValue2" /></td>
	</tr>
	<tr>
		<td><form:input path="additionalChargesName3" /></td>
		<td>
			<select name="additionalChargesType3">
				<c:forEach items="${chargeTypes}" var="chargeType">
					<c:choose>
						<c:when test="${chargeType == user.additionalChargesType3}">
							<option value="${chargeType}" selected="selected">${chargeType}</option>
						</c:when>
						<c:otherwise>
							<option value="${chargeType}">${chargeType}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</td>
		<td><form:input path="additionalChargesValue3" /></td>
	</tr>
	
	</table>
	
	<tr>
		<td colspan="2">
			<input type="submit" value="Save User"/>
			<button type="button" onclick="document.location.href=''">Cancel</button>
		</td>
	</tr>
</table>	
</form:form>
<hr/>


</body>
<script>
	$(function() {
		$("#user").validationEngine();
	});
</script>

</html>
