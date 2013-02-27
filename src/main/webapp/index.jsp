<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<div class="item"><a href="category/">Categories</a></div>
<c:set var="sessionUserId" value='<%=request.getSession().getAttribute("userId")%>'/>
<div class="item">
<c:if test='${!empty sessionUserId}'>
Logged in as <%=request.getSession().getAttribute("username")%> <a href="user/logout">Logout</a>
</c:if>
<c:if test='${empty sessionUserId}'>
<a href="user/login">Login</a>
</c:if>
</div>
</div>
</body>
</html>