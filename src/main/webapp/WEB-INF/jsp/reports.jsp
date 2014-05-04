<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
	<title>Reporting Manager</title>
	
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
			text-align:center;
			vertical-align:middle;
		}
		.data th {
			font-weight: bold;
			background-color: #5C82FF;
			color: white;
		}
	</style>
	<base href="${pageContext.request.contextPath}/"/>
	<link rel="stylesheet" href="css/style.css" />
	<script type="text/javascript" src="js/jquery-1.9.0.js"></script>
	<script type="text/javascript" src="js/jquery.simple-dtpicker.js"></script>
	<link rel="stylesheet" type="text/css" href="css/jquery.simple-dtpicker.css" />
	<c:set var="sessionUserId" value='<%=request.getSession().getAttribute("userId")%>'/>
	<script type="text/javascript">
		function generateReportUrlsWithDateRange(restaurantId) {
			//var formValues = $('input[type=text]');
			var startDate = $('input[name=startDate]').val();
			var endDate = $('input[name=endDate]').val();
			$("#dailySalesSummaryReport").attr("href", "reports/dailySalesSummary.xls?restaurantId=" + restaurantId +"&startDate=" + startDate + "&endDate=" + endDate);
			$("#dailyInvoiceReport").attr("href", "reports/dailyInvoice.xls?restaurantId=" + restaurantId +"&startDate=" + startDate + "&endDate=" + endDate);
			$("#customersReport").attr("href", "reports/customers.xls?restaurantId=" + restaurantId +"&startDate=" + startDate + "&endDate=" + endDate);
			$("#topDishesReport").attr("href", "reports/topDishes.xls?restaurantId=" + restaurantId +"&startDate=" + startDate + "&endDate=" + endDate);
			$("#detailedInvoiceReport").attr("href", "reports/detailedInvoice.xls?restaurantId=" + restaurantId +"&startDate=" + startDate + "&endDate=" + endDate);
			alert("Generated URLs");
		}
	</script>
	
</head>
<body>
<a href="menu/" style="float: right;">Return to Menus</a>
<form id="deliveryAddressForm" action="#" method="post">	
    <input type="text" name="startDate" class="date-picker" />
    <input type="text" name="endDate" class="date-picker" />
    <input type="button" id="generateUrls" value="Generate Report Urls" onclick="generateReportUrlsWithDateRange(${sessionUserId})" />
</form>
<div id="reportUrlContainer">
<a id="dailySalesSummaryReport" href="reports/dailySalesSummary.xls?restaurantId=${sessionUserId}">Daily Sales Summary</a> | <a id="dailyInvoiceReport" href="reports/dailyInvoice.xls?restaurantId=${sessionUserId}">Daily Invoice</a> | <a id="customersReport" href="reports/customers.xls?restaurantId=${sessionUserId}">Customers</a>| <a id="topDishesReport" href="reports/topDishes.xls?restaurantId=${sessionUserId}">Top Dishes</a>| <a id="detailedInvoiceReport" href="reports/detailedInvoice.xls?restaurantId=${sessionUserId}">Detailed Invoice</a>
</div>


</body>
<script>
$(function() {
    $('.date-picker').appendDtpicker({"dateOnly": true});
});
</script>

</html>
