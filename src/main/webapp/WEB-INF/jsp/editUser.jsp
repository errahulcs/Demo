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
	<script type="text/javascript">
var currName=new Array();
currName[0]="Albania Lek";
currName[1]="Afghanistan Afghani";
currName[2]="Argentina Peso";
currName[3]="Aruba Guilder";
currName[4]="Australia Dollar";
currName[5]="Azerbaijan New Manat";
currName[6]="Bahamas Dollar";
currName[7]="Barbados Dollar";
currName[8]="Belarus Ruble";
currName[9]="Belize Dollar";
currName[10]="Bermuda Dollar";
currName[11]="Bolivia Boliviano";
currName[12]="Bosnia and Herzegovina Convertible Marka";
currName[13]="Botswana Pula";
currName[14]="Bulgaria Lev";
currName[15]="Brazil Real";
currName[16]="Brunei Darussalam Dollar";
currName[17]="Cambodia Riel";
currName[18]="Canada Dollar";
currName[19]="Cayman Islands Dollar";
currName[20]="Chile Peso";
currName[21]="China Yuan Renminbi";
currName[22]="Colombia Peso";
currName[23]="Costa Rica Colon";
currName[24]="Croatia Kuna";
currName[25]="Cuba Peso";
currName[26]="Czech Republic Koruna";
currName[27]="Denmark Krone";
currName[28]="Dominican Republic Peso";
currName[29]="East Caribbean Dollar";
currName[30]="Egypt Pound";
currName[31]="El Salvador Colon";
currName[32]="Estonia Kroon";
currName[33]="Euro Member Countries";
currName[34]="Falkland Islands (Malvinas) Pound";
currName[35]="Fiji Dollar";
currName[36]="Ghana Cedis";
currName[37]="Gibraltar Pound";
currName[38]="Guatemala Quetzal";
currName[39]="Guernsey Pound";
currName[40]="Guyana Dollar";
currName[41]="Honduras Lempira";
currName[42]="Hong Kong Dollar";
currName[43]="Hungary Forint";
currName[44]="Iceland Krona";
currName[45]="India Rupee";
currName[46]="Indonesia Rupiah";
currName[47]="Iran Rial";
currName[48]="Isle of Man Pound";
currName[49]="Israel Shekel";
currName[50]="Jamaica Dollar";
currName[51]="Japan Yen";
currName[52]="Jersey Pound";
currName[53]="Kazakhstan Tenge";
currName[54]="Korea (North) Won";
currName[55]="Korea (South) Won";
currName[56]="Kyrgyzstan Som";
currName[57]="Laos Kip";
currName[58]="Latvia Lat";
currName[59]="Lebanon Pound";
currName[60]="Liberia Dollar";
currName[61]="Lithuania Litas";
currName[62]="Macedonia Denar";
currName[63]="Malaysia Ringgit";
currName[64]="Mauritius Rupee";
currName[65]="Mexico Peso";
currName[66]="Mongolia Tughrik";
currName[67]="Mozambique Metical";
currName[68]="Namibia Dollar";
currName[69]="Nepal Rupee";
currName[70]="Netherlands Antilles Guilder";
currName[71]="New Zealand Dollar";
currName[72]="Nicaragua Cordoba";
currName[73]="Nigeria Naira";
currName[74]="Korea (North) Won";
currName[75]="Norway Krone";
currName[76]="Oman Rial";
currName[77]="Pakistan Rupee";
currName[78]="Panama Balboa";
currName[79]="Paraguay Guarani";
currName[80]="Peru Nuevo Sol";
currName[81]="Philippines Peso";
currName[82]="Poland Zloty";
currName[83]="Qatar Riyal";
currName[84]="Romania New Leu";
currName[85]="Russia Ruble";
currName[86]="Saint Helena Pound";
currName[87]="Saudi Arabia Riyal";
currName[88]="Serbia Dinar";
currName[89]="Seychelles Rupee";
currName[90]="Singapore Dollar";
currName[91]="Solomon Islands Dollar";
currName[92]="Somalia Shilling";
currName[93]="South Africa Rand";
currName[94]="Korea (South) Won";
currName[95]="Sri Lanka Rupee";
currName[96]="Sweden Krona";
currName[97]="Switzerland Franc";
currName[98]="Suriname Dollar";
currName[99]="Syria Pound";
currName[100]="Taiwan New Dollar";
currName[101]="Thailand Baht";
currName[102]="Trinidad and Tobago Dollar";
currName[103]="Turkey Lira";
currName[104]="Turkey Lira";
currName[105]="Tuvalu Dollar";
currName[106]="Ukraine Hryvna";
currName[107]="United Kingdom Pound";
currName[108]="United States Dollar";
currName[109]="Uruguay Peso";
currName[110]="Uzbekistan Som";
currName[111]="Venezuela Bolivar";
currName[112]="Viet Nam Dong";
currName[113]="Yemen Rial";
currName[114]="Zimbabwe Dollar";

var currCode=new Array();
currCode[0]="ALL";
currCode[1]="AFN";
currCode[2]="ARS";
currCode[3]="AWG";
currCode[4]="AUD";
currCode[5]="AZN";
currCode[6]="BSD";
currCode[7]="BBD";
currCode[8]="BYR";
currCode[9]="BZD";
currCode[10]="BMD";
currCode[11]="BOB";
currCode[12]="BAM";
currCode[13]="BWP";
currCode[14]="BGN";
currCode[15]="BRL";
currCode[16]="BND";
currCode[17]="KHR";
currCode[18]="CAD";
currCode[19]="KYD";
currCode[20]="CLP";
currCode[21]="CNY";
currCode[22]="COP";
currCode[23]="CRC";
currCode[24]="HRK";
currCode[25]="CUP";
currCode[26]="CZK";
currCode[27]="DKK";
currCode[28]="DOP";
currCode[29]="XCD";
currCode[30]="EGP";
currCode[31]="SVC";
currCode[32]="EEK";
currCode[33]="EUR";
currCode[34]="FKP";
currCode[35]="FJD";
currCode[36]="GHC";
currCode[37]="GIP";
currCode[38]="GTQ";
currCode[39]="GGP";
currCode[40]="GYD";
currCode[41]="HNL";
currCode[42]="HKD";
currCode[43]="HUF";
currCode[44]="ISK";
currCode[45]="INR";
currCode[46]="IDR";
currCode[47]="IRR";
currCode[48]="IMP";
currCode[49]="ILS";
currCode[50]="JMD";
currCode[51]="JPY";
currCode[52]="JEP";
currCode[53]="KZT";
currCode[54]="KPW";
currCode[55]="KRW";
currCode[56]="KGS";
currCode[57]="LAK";
currCode[58]="LVL";
currCode[59]="LBP";
currCode[60]="LRD";
currCode[61]="LTL";
currCode[62]="MKD";
currCode[63]="MYR";
currCode[64]="MUR";
currCode[65]="MXN";
currCode[66]="MNT";
currCode[67]="MZN";
currCode[68]="NAD";
currCode[69]="NPR";
currCode[70]="ANG";
currCode[71]="NZD";
currCode[72]="NIO";
currCode[73]="NGN";
currCode[74]="KPW";
currCode[75]="NOK";
currCode[76]="OMR";
currCode[77]="PKR";
currCode[78]="PAB";
currCode[79]="PYG";
currCode[80]="PEN";
currCode[81]="PHP";
currCode[82]="PLN";
currCode[83]="QAR";
currCode[84]="RON";
currCode[85]="RUB";
currCode[86]="SHP";
currCode[87]="SAR";
currCode[88]="RSD";
currCode[89]="SCR";
currCode[90]="SGD";
currCode[91]="SBD";
currCode[92]="SOS";
currCode[93]="ZAR";
currCode[94]="KRW";
currCode[95]="LKR";
currCode[96]="SEK";
currCode[97]="CHF";
currCode[98]="SRD";
currCode[99]="SYP";
currCode[100]="TWD";
currCode[101]="THB";
currCode[102]="TTD";
currCode[103]="TRY";
currCode[104]="TRL";
currCode[105]="TVD";
currCode[106]="UAH";
currCode[107]="GBP";
currCode[108]="USD";
currCode[109]="UYU";
currCode[110]="UZS";
currCode[111]="VEF";
currCode[112]="VND";
currCode[113]="YER";
currCode[114]="ZWD";

var currHexCode=new Array();
currHexCode[0]="&#76";
currHexCode[1]="&#1547";
currHexCode[2]="&#36";
currHexCode[3]="&#402";
currHexCode[4]="&#36";
currHexCode[5]="&#1084";
currHexCode[6]="&#36";
currHexCode[7]="&#36";
currHexCode[8]="&#112";
currHexCode[9]="&#66";
currHexCode[10]="&#36";
currHexCode[11]="&#36";
currHexCode[12]="&#75";
currHexCode[13]="&#80";
currHexCode[14]="&#1083";
currHexCode[15]="&#82";
currHexCode[16]="&#36";
currHexCode[17]="&#6107";
currHexCode[18]="&#36";
currHexCode[19]="&#36";
currHexCode[20]="&#36";
currHexCode[21]="&#165";
currHexCode[22]="&#36";
currHexCode[23]="&#8353";
currHexCode[24]="&#107";
currHexCode[25]="&#8369";
currHexCode[26]="&#75";
currHexCode[27]="&#107";
currHexCode[28]="&#82";
currHexCode[29]="&#36";
currHexCode[30]="&#163";
currHexCode[31]="&#36";
currHexCode[32]="&#107";
currHexCode[33]="&#8364";
currHexCode[34]="&#163";
currHexCode[35]="&#36";
currHexCode[36]="&#162";
currHexCode[37]="&#163";
currHexCode[38]="&#81";
currHexCode[39]="&#163";
currHexCode[40]="&#36";
currHexCode[41]="&#76";
currHexCode[42]="&#36";
currHexCode[43]="&#70";
currHexCode[44]="&#107";
currHexCode[45]="&#8377";
currHexCode[46]="&#82";
currHexCode[47]="&#65020";
currHexCode[48]="&#163";
currHexCode[49]="&#8362";
currHexCode[50]="&#74";
currHexCode[51]="&#165";
currHexCode[52]="&#163";
currHexCode[53]="&#1083";
currHexCode[54]="&#8361";
currHexCode[55]="&#8361";
currHexCode[56]="&#1083";
currHexCode[57]="&#8365";
currHexCode[58]="&#76";
currHexCode[59]="&#163";
currHexCode[60]="&#36";
currHexCode[61]="&#76";
currHexCode[62]="&#1076";
currHexCode[63]="&#82";
currHexCode[64]="&#8360";
currHexCode[65]="&#36";
currHexCode[66]="&#8366";
currHexCode[67]="&#77";
currHexCode[68]="&#36";
currHexCode[69]="&#8360";
currHexCode[70]="&#402";
currHexCode[71]="&#36";
currHexCode[72]="&#67";
currHexCode[73]="&#8358";
currHexCode[74]="&#8361";
currHexCode[75]="&#107";
currHexCode[76]="&#65020";
currHexCode[77]="&#8360";
currHexCode[78]="&#66";
currHexCode[79]="&#71";
currHexCode[80]="&#83";
currHexCode[81]="&#8369";
currHexCode[82]="&#122";
currHexCode[83]="&#65020";
currHexCode[84]="&#108";
currHexCode[85]="&#1088";
currHexCode[86]="&#163";
currHexCode[87]="&#65020";
currHexCode[88]="&#1044";
currHexCode[89]="&#8360";
currHexCode[90]="&#36";
currHexCode[91]="&#36";
currHexCode[92]="&#83";
currHexCode[93]="&#82";
currHexCode[94]="&#8361";
currHexCode[95]="&#8360";
currHexCode[96]="&#107";
currHexCode[97]="&#67";
currHexCode[98]="&#36";
currHexCode[99]="&#163";
currHexCode[100]="&#78";
currHexCode[101]="&#3647";
currHexCode[102]="&#84";
currHexCode[103]="&#8378";
currHexCode[104]="&#8356";
currHexCode[105]="&#36";
currHexCode[106]="&#8372";
currHexCode[107]="&#163";
currHexCode[108]="&#36";
currHexCode[109]="&#36";
currHexCode[110]="&#1083";
currHexCode[111]="&#66";
currHexCode[112]="&#8363";
currHexCode[113]="&#65020";
currHexCode[114]="&#90";

function list(index) {
//var con=document.getElementById('sym');
  //if(index==-1) {
    // con.innerHTML="";
    // return;
    //}
    //con.innerHTML=currHexCode[index];
    if (index == -1) {
    	index = 108;
    }
    $("#currency").val(currCode[index]);
}

	</script>
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
	<form:hidden path="appCacheIconUrl"/>
	<form:hidden path="buttonIconUrl"/>
	<form:hidden id="currency" path="currency"/>
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
		<td><form:label path="deliveryCharges">Delivery Charges</form:label></td>
		<td><form:input path="deliveryCharges" class="validate[maxSize[10]]"/></td>
	</tr>
	<tr>
		<td><form:label path="minInCircleDeliveyThreshold">Minimum In Circle Delivery Threshold</form:label></td>
		<td><form:input path="minInCircleDeliveyThreshold" class="validate[maxSize[10]]"/></td>
	</tr>
	<tr>
		<td><form:label path="minOutCircleDeliveyThreshold">Minimum Out Circle DeliveryThreshold</form:label></td>
		<td><form:input path="minOutCircleDeliveyThreshold" class="validate[maxSize[10]]"/></td>
	</tr>
	
	<tr>
	<td>Currency : </td>
	<td>
	<select name=cur id=cur onChange=list(this.value)>
		<script type="text/javascript">
		var actualCurrCode = "${user.currency}";
		document.write("<option value=-1>Select Currency</option>");
		count=currName.length;
		for(i=0;i<count;i++) {
			var finalStr = "<option value="+i;
			if (actualCurrCode == currCode[i]) {
				finalStr += " selected=\"selected\"";
			}
			finalStr += ">"+currName[i]+" - " + currCode[i]+ " - " + currHexCode[i]+ "</option>"
			document.write(finalStr);	
		}
			
		 
		</script>
	</select>
		
	</td> 
	</tr>
	<tr>
		<td><form:label path="timeZone">Time Zone : </form:label></td>
		<td>
			<select name="timeZone">
				<c:forEach items="${timeZones}" var="tz">
					<c:choose>
						<c:when test="${tz == user.timeZone}">
							<option value="${tz}" selected="selected">${tz}</option>
						</c:when>
						<c:otherwise>
							<option value="${tz}">${tz}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</td>
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
	<!-- Work Hours Start -->
	<hr/>
	Work Hours
	<table class="data">
	<tr>
		<th>Day of Week</th>
		<th>Open Time (HH:mm)</th>	
		<th>Close Time (HH:mm)</th>
	</tr>
	<tr>
		<td>Sunday</td>
		<td><form:input path="sundayOpenTime" class="validate[maxSize[5]]"/></td>	
		<td><form:input path="sundayCloseTime" class="validate[maxSize[5]]"/></td>
	</tr>
	<tr>
		<td>Monday</td>
		<td><form:input path="mondayOpenTime" class="validate[maxSize[5]]"/></td>	
		<td><form:input path="mondayCloseTime" class="validate[maxSize[5]]"/></td>
	</tr>
	<tr>
		<td>Tuesday</td>
		<td><form:input path="tuesdayOpenTime" class="validate[maxSize[5]]"/></td>	
		<td><form:input path="tuesdayCloseTime" class="validate[maxSize[5]]"/></td>
	</tr>
	<tr>
		<td>Wednesday</td>
		<td><form:input path="wednesdayOpenTime" class="validate[maxSize[5]]"/></td>	
		<td><form:input path="wednesdayCloseTime" class="validate[maxSize[5]]"/></td>
	</tr>
	<tr>
		<td>Thursday</td>
		<td><form:input path="thursdayOpenTime" class="validate[maxSize[5]]"/></td>	
		<td><form:input path="thursdayCloseTime" class="validate[maxSize[5]]"/></td>
	</tr>
	<tr>
		<td>Friday</td>
		<td><form:input path="fridayOpenTime" class="validate[maxSize[5]]"/></td>	
		<td><form:input path="fridayCloseTime" class="validate[maxSize[5]]"/></td>
	</tr>
	<tr>
		<td>Saturday</td>
		<td><form:input path="saturdayOpenTime" class="validate[maxSize[5]]"/></td>	
		<td><form:input path="saturdayCloseTime" class="validate[maxSize[5]]"/></td>
	</tr>

	</table>
	<hr/>
	<!-- Work Hours Ends -->
	
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
