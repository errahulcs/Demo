<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<title>User Signup</title>
	<link rel="stylesheet" href="/CookedSpecially/css/style.css" />
	
	<link rel="stylesheet" href="/CookedSpecially/themes/base/jquery.ui.all.css" />	
	<script type="text/javascript" src="/CookedSpecially/js/jquery-1.9.0.js"></script>
	<script type="text/javascript" src="/CookedSpecially/js/ui/jquery-ui.js"></script>
	<script src="/CookedSpecially/js/jquery.validationEngine-en.js" type="text/javascript" charset="utf-8"></script>
	<script src="/CookedSpecially/js/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>
	<link rel="stylesheet" href="/CookedSpecially/css/validationEngine.jquery.css" type="text/css"/>
	<style type="text/css">
		
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
		<td><form:label path="firstName">First Name *</form:label></td>
		<td>
			<form:input path="firstName" class="validate[required]" />
		</td> 
	</tr>
	<tr>
		<td><form:label path="lastName">Last Name *</form:label></td>
		<td><form:input path="lastName" class="validate[required]" /></td> 
	</tr>
	<tr>
		<td><form:label path="businessName">Business Name</form:label></td>
		<td><form:input path="businessName" /></td> 
	</tr>
	<tr>
		<td><form:label path="username">Email *</form:label></td>
		<td><form:input path="username" class="validate[required]" />
		<form:errors path="username" style="color:red"/>		
		</td>
		 
	</tr>
	<tr>
		<td>Password *</td>
		<td><input id="password" type="password" name="password" class="validate[required]"/> </td> 
	</tr>
	<tr>
		<td><label> Password check * </label></td>
		<td><input type="password" name="check" class="validate[required,equals[password]]" /></td> 
	</tr>
	<tr>
		<td colspan="2">
			<input type="submit" value="Sign up!"/>
		</td>
	</tr>
</table>	
</form:form>
</body>
<script>
	$(function() {
		$("#user").validationEngine();
		//$("#user").validator();
		/*
		jQuery.tools.validator.fn("[data-equals]", "Value not equal with the $1 field", function(input) {
			    var name = input.attr("data-equals"), field = this.getInputs().rfilter("[name=" + name + "]");
			    alert(name);
			    alert(input.val());
			    alert(field.val());
			    return input.val() == field.val() ? true : [name];
		});
		*/
	});
</script>

</html>