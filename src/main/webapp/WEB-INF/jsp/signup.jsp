<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<title>User Signup</title>
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
<h3>User Signup</h3>

<form:form method="post" action="signup.html" commandName="user">

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
		<td><form:label path="username">Username</form:label></td>
		<td><form:input path="username" /></td> 
	</tr>
	<tr>
		<td>Password</td>
		<td><input type="password" name="password"/> </td> 
	</tr>
	
	<tr>
		<td colspan="2">
			<input type="submit" value="Sign up!"/>
		</td>
	</tr>
</table>	
</form:form>
