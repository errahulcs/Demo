<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>

CookedSpecially.com</head>
<body>
<a href="menu/">Menus</a>
<a href="dish/">Dishes</a>
<a href="category/">Categories</a>
<c:set var="sessionUserId" value='<%=request.getSession().getAttribute("userId")%>'/>
<c:if test='${!empty sessionUserId}'>
Logged in as <%=request.getSession().getAttribute("username")%> <a href="user/logout">Logout</a>
</c:if>
<c:if test='${empty sessionUserId}'>
<a href="user/login">Login</a>
</c:if>

</body>
</html>