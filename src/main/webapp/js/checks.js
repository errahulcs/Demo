/**********
 * CONSTANTS
 */
var restaurantId = "1"; //Axis
var appVersion = '0.1';
var appLatestVersion = '0.1';
var comCookedSpeciallyImagePattern = /\/static\/\d+\//;
var comCookedSpeciallyImagePrefix = "http://www.bakedspecially.com";
var comCookedSpeciallyApiPrefix = "http://www.bakedspecially.com:8080/CookedSpecially";
var menuApi = comCookedSpeciallyApiPrefix + "/menu/getallmenusjson/" + restaurantId;
var checkApi = comCookedSpeciallyApiPrefix + "/order/getCheckWithOrders.json?tableId=1&restaurantId=" + restaurantId;
var tableApi = comCookedSpeciallyApiPrefix + "/seatingTable/getRestaurantTables.json?restaurantId=" + restaurantId;
var db;
var strConnectionAlert = "You appear to be online, but we have a connection problem. Please click Ok to try again.";

var changeCount = 0;
var orderCount = 0;

/* *********
 * GLOBALS
 */
var menuDataString = "";
var menuData = {};
var checkDataString = "";
var checkData = [];
var tableDataString = "";
var tableData = {};
var startTime;
var endTime;
var loadingTime;



/* **********
 * 1. BEGIN initHomePage
 * Iterates through the data to add orders to the home page
   Function Calls 
    - addCheckToHomePage() for each order
 */
function initHomePage() {
	console.log("DEBUG > BEGIN initHomePage");	
	console.log("About to iterate through the data");
	var orderIndex = 0;
		
	// iterate through data file and create orders in homepage
	$.each(tableData.tables, function() {
		var tableName = this.name;
		var tableId = this.id;
		var tableStatus = this.status;
		
		$("#topMenu #table" + tableId).remove();
		var orderList = '' +
			'<ul id="table' + tableId + '" data-role="listview" data-inset="true" class="ui-listview ui-listview-inset ui-shadow">' +
			'</ul>';
		$(orderList).appendTo("#topMenu");

		var checkApi = comCookedSpeciallyApiPrefix + "/order/getCheckWithOrders.json?tableId=" + tableId + "&restaurantId=" + restaurantId;
		$.ajax(checkApi, {
			isLocal: false
		})
		.done (function (data) {
			if( console && console.log ) {
				console.log("ajax success: " + checkApi);
				checkDataString = JSON.stringify(data);
				console.log(checkDataString);
			}
			addCheckToHomePage(tableId, "#table" + tableId, tableName, data);
			checkData.push(JSON.parse(checkDataString));
		})
		.fail(function() { 
			console.log("ajax error: " + checkApi); 
		})
		.always(function() { 
			console.log("ajax complete: " + checkApi); 
		});
	});

	$("#app-loader").css({ "display": "none" });
	$("#allPages").css({ "visibility": "visible" });
	$("#home").on( "pagebeforeshow", function(event, ui) {
		// do something before navigating to a new page
	});
	$("#home").on( "pageshow", function(event, ui) {
		// do something when the home screen is shown
	});
	$.mobile.changePage("#menus");
	console.log("orderCount: " + orderIndex);
		
	console.log("DEBUG > END initHomePage");	
}
/* 
 * END initHomePage
 **********/

 
 

/* 
 *******************************************************
                     UI FUNCTIONS
 ******************************************************* 
 */
 
/* **********
 * 2. BEGIN addCheckToHomePage
 * Creates the home page with all the checks
    Parameters: 
 */
function addCheckToHomePage(tableId, htmlTableId, tableName, data) {
	console.info("DEBUG > BEGIN addCheckToHomePage: " + tableId); 
	
	var checkId = data.checkId;
	var htmlCheckId = "#check" + checkId;
	var checkStatus = data.status;
	var guests = data.guests;
	var additionalChargesName1 = data.additionalChargesName1;
	var additionalChargesName2 = data.additionalChargesName2;
	var additionalChargesName3 = data.additionalChargesName3;
	var total = data.bill + data.additionalChargesValue1 + data.additionalChargesValue2 + data.additionalChargesValue3;

	$(htmlTableId + " li#check" + checkId).remove();
	var theCheck = '' +
		'<li id="check' + checkId + '" class="ui-li ui-li-static ui-btn-up-c">' +
			'<p id="status" class="ui-li-desc">' +
			'	Check #' + checkId + ' (' + checkStatus + ')' + 
			//' | ' + guests + ' guest(s)' +
			'</p>' +
			'<h3 class="ui-li-heading">' +
			'	' + tableName +
			'</h3>' +
		'</li>';
	$(theCheck).appendTo(htmlTableId);
	
	if (total > 0) {
		var theLink = '' +
			'<p id="links" class="ui-li-aside ui-li-desc">' +
			'	Mark Table as <a href="javascript:void(0);" onclick="setTableStatusAvailable(\'' + tableId + '\',\'' + htmlTableId + '\',\'' + tableName +'\','  + checkId + ');return(false);">Available</a>' + 
			'	, <a href="javascript:void(0);" onclick="setTableStatusBusy(\'' + tableId + '\',\'' + htmlTableId + '\',\'' + tableName +'\','  + checkId + ');return(false);">Busy</a>' + 
			'	or <a href="javascript:void(0);" onclick="setTableStatusReserved(\'' + tableId + '\',\'' + htmlTableId + '\',\'' + tableName +'\','  + checkId + ');return(false);">Reserved</a>' + 
			'	&nbsp;|&nbsp;<a href="javascript:void(0);" onclick="$(\'#check' + checkId + '\').print();return(false);">Print Check</a>' + 
			'</p>'; 
		$(theLink).prependTo(htmlCheckId);
		if (checkStatus != "Paid" && checkStatus != "PAID") {
			theLink = '' +
				'&nbsp;|&nbsp;<a href="javascript:void(0);" onclick="setCheckStatusPaid(\'' + tableId + '\',\'' + htmlTableId + '\',\'' + tableName +'\','  + checkId + ');return(false);">Mark Check as Paid</a>';
			$(theLink).appendTo(htmlCheckId + " p#links");
		}
		$.each(data.orders, function() {
			$.each(this.orderDishes, function() {
				var name = this.name;
				var quantity = this.quantity;
				var price = this.price;
				var theItem = '' +
					'<p class="ui-li-desc">' +
					'	<strong>' + name + '</strong> ' + quantity + ' @ &#8377;' + price + 
					'</p>';
				$(theItem).appendTo(htmlCheckId);
			});
		});

		var theAdditions = '<br/>';
		if (additionalChargesName1 && additionalChargesName1.length > 0) {
			theAdditions = theAdditions + '<p id="additions" class="ui-li-desc"><strong>' + additionalChargesName1 + ': </strong> &#8377;' + data.additionalChargesValue1 + '</p>';
		}
		if (additionalChargesName2 && additionalChargesName2.length > 0) {
			theAdditions = theAdditions + '<p id="additions" class="ui-li-desc"><strong>' + additionalChargesName2 + ': </strong> &#8377;' + data.additionalChargesValue2 + '</p>';
		}
		if (additionalChargesName3 && additionalChargesName3.length > 0) {
			theAdditions = theAdditions + '<p id="additions" class="ui-li-desc"><strong>' + additionalChargesName3 + ': </strong> &#8377;' + data.additionalChargesValue3 + '</p>';
		}
		$(theAdditions).appendTo(htmlCheckId);
	
		var theSummary = '' +
			'<br/>' + 
			'<p id="summary" class="ui-li-desc">' +
			'	<strong>Total: </strong> &#8377;' + total + 
			'</p>';
		$(theSummary).appendTo(htmlCheckId);
	}
	
	console.log("DEBUG > END addCheckToHomePage");	
}
/* 
 * END addCheckToHomePage
 **********/





 
 /* 
 *******************************************************
                     DATA FUNCTIONS
 ******************************************************* 
 */

function setCheckStatusPaid(tableId, htmlTableId, tableName, checkId) {
	setCheckStatus(tableId, htmlTableId, tableName, checkId, "Paid");
}
function setCheckStatusReady(tableId, htmlTableId, tableName, checkId) {
	setCheckStatus(tableId, htmlTableId, tableName, checkId, "READYTOPAY");
}
function setCheckStatusUnpaid(tableId, htmlTableId, tableName, checkId) {
	setCheckStatus(tableId, htmlTableId, tableName, checkId, "Unpaid");
}
/***********
 * BEGIN setCheckStatus
 */
function setCheckStatus(tableId, htmlTableId, tableName, checkId, status) {
console.log("DEBUG > BEGIN setCheckStatus");	
///order/setCheckStatus?checkId=<checkId>&status=<Paid|Unpaid|READYTOPAY>
var statusApi = comCookedSpeciallyApiPrefix + "/order/setCheckStatus?checkId=" + checkId + "&status=" + status;

	$.ajax(statusApi, {
		isLocal: false
	})
	.done (function (data) {
		if( console && console.log ) {
			console.log("ajax success: " + statusApi);
			console.log(JSON.stringify(data));
		}
		//initHomePage();
		addCheckToHomePage(tableId, htmlTableId, tableName, data);
	})
	.fail(function() { 
		console.log("ajax error: " + statusApi); 
	})
	.always(function() { 
		console.log("ajax complete: " + statusApi); 
	});
}
/* 
 * END setCheckStatus
 **********/


function setTableStatusAvailable(tableId) {
	setTableStatus(tableId, "Available");
}
function setTableStatusBusy(tableId) {
	setTableStatus(tableId, "Busy");
}
function setTableStatusReserved(tableId) {
	setTableStatus(tableId, "Reserved");
}
/***********
 * BEGIN setTableStatus
 */
function setTableStatus(tableId, status) {
console.log("DEBUG > BEGIN setTableStatus");	
//	http://www.bakedspecially.com:8080/CookedSpecially/seatingTable/setStatus?tableId=1&status=Available
var statusApi = comCookedSpeciallyApiPrefix + "/seatingTable/setStatus?tableId=" + tableId + "&status=" + status;

	$.ajax(statusApi, {
		isLocal: false
	})
	.done (function (data) {
		if( console && console.log ) {
			console.log("ajax success: " + statusApi);
			console.log(JSON.stringify(data));
		}
		initHomePage();
	})
	.fail(function() { 
		console.log("ajax error: " + statusApi); 
	})
	.always(function() { 
		console.log("ajax complete: " + statusApi); 
	});
}
/* 
 * END setTableStatus
 **********/


/***********
 * BEGIN updateAjaxDataRemote
 */
function updateAjaxDataRemote() {
console.log("DEBUG > BEGIN updateAjaxDataRemote");	

	$.ajax(tableApi, {
		isLocal: false
	})
	.done (function (data) {
		if( console && console.log ) {
			console.log("ajax success: " + tableApi);
			tableDataString = JSON.stringify(data);
			console.log(tableDataString);
		}

		tableData = JSON.parse(tableDataString);

		initHomePage();
		
	})
	.fail(function() { 
		console.log("ajax error: " + tableApi); 
	})
	.always(function() { 
		console.log("ajax complete: " + tableApi); 
	});
	
console.log("DEBUG > END updateAjaxDataRemote");	
}
/* 
 * END updateAjaxDataRemote
 **********/

 /* 
 *******************************************************
                     MAIN PROGRAM
 ******************************************************* 
 */

try {
	var startTime = new Date().getTime();
	
	//http://stackoverflow.com/questions/13986182/how-can-i-improve-the-page-transitions-for-my-jquery-mobile-app/13986390#13986390
	$(document).one("mobileinit", function () {
		$.mobile.ajaxEnabled = true;
		$.mobile.allowCrossDomainPages = true;
		$.mobile.autoInitializePage = true;
		$.mobile.defaultPageTransition = "none";
		$.mobile.loader.prototype.options.text = "loading";
		$.mobile.loader.prototype.options.textVisible = true;
		$.mobile.mobile.touchOverflowEnabled = true;
		$.mobile.pageContainer = $("#allPages");
		$.mobile.phonegapNavigationEnabled = true;
	});

	$(document).ready(function(){
		// When the document is ready, Cordova isn't
		updateAjaxDataRemote();
	});

} catch (error) {
	console.error("Your javascript has an error: " + error);
}


/**********
 * BEGIN showAppVersion
 */
function showAppVersion() {
    var appInfo = 'About Axis Tablet App\n\n' +
                  'Restaurant: Axis Cafe' + '\n' +
                  'AppVersion: Beta ' + appVersion + '\n\n' +
                  'Loading Time: ' + loadingTime + ' miliseconds\n' +
                  ''; //'Latest Version: Beta ' + appLatestVersion;
    alert(appInfo);


}
/*
 * END showAppVersion
 ***********/
 
 
 
 
 	