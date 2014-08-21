<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.regex.*"%>
<html>
<head>
<meta charset="utf-8" />
<title>CookedSpecially.com</title>

  <!--[if lt IE 9]><script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
  
  <link rel="stylesheet" href="css/style.css" />
  
  <!-- scripts at bottom of page -->
</head>
<body class="homepage">
<div id="container">
<div class="item"><a href="menu/">Menus</a></div>
<div class="item"><a href="dish/">Dishes</a></div>
<div class="item"><a href="seatingTable/">Manage Tables</a></div>
<div class="item"><a href="restaurant/deliveryAreas">Manage Delivery Areas</a></div>
<div class="item"><a href="dishTypes/">Manage DishTypes</a></div>
<c:set var="sessionUserId" value='<%=request.getSession().getAttribute("userId")%>'/>
<div class="item">
<c:if test='${!empty sessionUserId}'>
Logged in as <%=request.getSession().getAttribute("username")%> <a href="user/edit">Edit</a> | <a href="user/logout">Logout</a>
</c:if>
<c:if test='${empty sessionUserId}'>
<a href="user/login">Login</a>
</c:if><br>
<c:if test='${empty sessionUserId}'>
<a href="user/signup">SignUp</a>
</c:if>
</div>
<div class="item">
<c:if test='${!empty sessionUserId}'>
<!--a href="restaurant/resources/APK">Download Android App</a-->
<script type="text/javascript">
	var username = "<%=request.getSession().getAttribute("username")%>";
	var usernameMatch = /(.+)\@(.+)\.(.+)/;
	var restaurantId = "<c:out value='${sessionUserId}'/>";

	if (usernameMatch.test(username)) {
		//alert("match");
		var matchArray = usernameMatch.exec(username).slice();
		document.write('<a target="csmobile" href="/static/mobile/index.html?{%22restaurants%22:[' + restaurantId + ']}">Visit your mobile website<\/a>');
		document.write('&nbsp;|&nbsp;<a target="cstable" href="/static/table/index.html?{%22restaurants%22:[' + restaurantId + ']}">Save your application using HTML5<\/a>');

<%-- http://stackoverflow.com/questions/2291085/how-to-check-if-external-url-content-loads-correctly-into-an-iframe-in-jsp-pag --%>
<c:set var="androidAppUrl" value=""/>
<%
	String androidAppUrl = "";
	Pattern pattern = 
	Pattern.compile("(.+)\\@(.+)\\.(.+)");

	Matcher matcher = 
	pattern.matcher(request.getSession().getAttribute("username").toString());

	while (matcher.find()) {
		pageContext.setAttribute("androidAppUrl", "/static/clients/" + matcher.group(3) + "/" + matcher.group(2) + "/" + matcher.group(1) + ".apk");
	}
%>
<c:catch var="e">
    <c:import url="${androidAppUrl}" context="/" varReader="ignore"/>
    <c:out value="// Android App URL: ${androidAppUrl}" />
</c:catch>
<c:choose>
<c:when test="${empty e}">
	document.write('&nbsp;|&nbsp;<a href="<c:out value="${androidAppUrl}" />">Download your Android App<\/a>');
</c:when>
<c:otherwise>
	document.write('&nbsp;|&nbsp;<a href="mailto:akshay@cookedspecially.com">Ask about custom Android App development<\/a>');
</c:otherwise>
</c:choose>
		document.write('&nbsp;|&nbsp;<a target="csorders" href="orders.jsp#menus">Manage orders from customers<\/a>');
		document.write('&nbsp;|&nbsp;<a target="cschecks" href="checks.jsp#menus">Manage table status and print checks<\/a>');
	}
	else {
		//alert("no match");
	}
</script>
<br/>
<div><a href="order/searchChecks">Search Check</a></div>
<hr/>
<div><b>Download Reports</b> </div>
<div><a href="reports/dailySalesSummary.xls?restaurantId=${sessionUserId}">Daily Sales Summary</a> | <a href="reports/dailyInvoice.xls?restaurantId=${sessionUserId}">Daily Invoice</a> | <a href="reports/customers.xls?restaurantId=${sessionUserId}">Customers</a>| <a href="reports/topDishes.xls?restaurantId=${sessionUserId}">Top Dishes</a> | <a href="reports/detailedInvoice.xls?restaurantId=${sessionUserId}">Detailed Invoice</a></div>
<hr/>
<a href="reports/">Reports with Date Range</a>
</c:if>
<c:if test='${empty sessionUserId}'>
<!--a href="restaurant/resources/APK?restaurantName=axis">Download Android App for Axis</a-->
</c:if>
</div>

</div>
<!-- 
hello this is symbol
<link rel="stylesheet" type="text/css" href="http://cdn.webrupee.com/font">
<span class="WebRupee">Rs</span>
 -->
</body>
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-44124437-1', 'bakedspecially.com');
  ga('send', 'pageview');

</script>
</html>
