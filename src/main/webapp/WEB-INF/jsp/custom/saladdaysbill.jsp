<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<base href="${pageContext.request.contextPath}/"/>
<script type="text/javascript">
function zeroFill( number, width )
{
  width -= number.toString().length;
  if ( width > 0 )
  {
    return new Array( width + (/\./.test( number ) ? 2 : 1) ).join( '0' ) + number;
  }
  return number + ""; // always return a string
}

if (document.getElementById) onload = function () {
	
	var now = new Date();
	document.getElementById ('date-time').innerHTML = zeroFill(now.getDate(), 2) + '/' + zeroFill((now.getMonth()+1),2) + '/' + now.getFullYear() + ' ' + zeroFill(now.getHours(),2) + ':' + zeroFill(now.getMinutes(),2);
}
</script>
<body>
<h1 align="center">SALAD DAYS</h1>
<h2 align="center">Amicus Natural Products Pvt. Ltd.</h2>
<div align="center"><a href="http://www.saladdays.co" align="center">www.saladdays.co</a></div>
<div align="center"><a href="http://www.facebook.com/Saladdays.co" align="center">www.facebook.com/Saladdays.co</a></div>
<div align="center">9643 800 901/02/03</div>
<div align="center">Date & Time: <span id='date-time'></span> </div><br/>
<c:if  test="${!empty customer}">
Name: ${customer.firstName} ${customer.lastName} <br />
Address: ${customer.address}<br />
Email Id: ${customer.email}<br />
Phone No.: ${customer.phone}<br />
</c:if>
<c:if test="${!empty tableId }">
Table ID: ${tableId} <br/>
</c:if>
<table align="center" width="100%">
<tr>
<th width="33%">Item</th>
<th width="33%">Quantity</th>
<th width="33%">Amount</th>
</tr>
<c:if test="${!empty itemsMap}">
<c:forEach items="${itemsMap}" var="item">
	<tr>
		<td width="33%" align="center">${item.value.name}</td>
		<td width="33%" align="center">X ${item.value.quantity}</td>
		<td width="33%" align="center">${item.value.price}</td>
	</tr>
</c:forEach>
</c:if>
</table>
<hr/>
<div style="float:left">Sub Total</div>  <div style="float:right">${checkRespone.amount}</div><br/>
<c:if test = "${!empty checkRespone.additionalChargeName1}">
<div style="float:left">${checkRespone.additionalChargeName1}</div> <div style="float:right">${checkRespone.additionalCharge1}</div><br/>
</c:if>
<c:if test = "${!empty checkResp.additionalChargeName2}">
<div style="float:left">${checkRespone.additionalChargeName2}</div> <div style="float:right">${checkRespone.additionalCharge2}</div><br/>
</c:if>
<c:if test = "${!empty checkResp.additionalChargeName3}">
<div style="float:left">${checkRespone.additionalChargeName3}</div> <div style="float:right">${checkRespone.additionalCharge3}</div><br/>
</c:if>

<div style="float:left">Grand Total</div> <div style="float:right">${checkRespone.total}</div><br/>
<hr/>

<div align="center">TIN No.: 06551835911</div>
<div align="center">Invoice No.: SD2014000001</div>
<br/>
<br/>
<div align="center">You're awesome!</div>
<div align="center">-------</div>
</body>
</html>