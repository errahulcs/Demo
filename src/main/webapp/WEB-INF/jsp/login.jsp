<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<title>Login Manager</title>
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
<h3>Login </h3>

<form:form method="post" action="login.html">

	<table>
	<tr>
		<td>UserName: </td>
		<td><input type="text" name="username"/>  </td> 
	</tr>
	<tr>
		<td>Password:</td>
		<td>  <input type="password" name="password" /> </td>
	</tr>
	<!-- 
	<tr>
		
		<td>  <input type="checkbox" name="signup" value="true"> SignUp</input> </td>
		<td></td>
	</tr>
	 -->
	<tr>
		<td colspan="2">
			<input type="submit" value="Login"/>
		</td>
	</tr>
</table>	
</form:form>
<a href="/CookedSpecially/user/signup"> Signup</a>