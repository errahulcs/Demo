/**********
 * CONSTANTS
 */
var restaurantId = "1"; //Axis
var appVersion = '0.1';
var appLatestVersion = '0.1';
//http://www.bakedspecially.com/api/getOrders.php?restaurantId=1
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
    - addOrderToHomePage() for each order
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
		var guests = this.guests;

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
			addOrdersToHomePage("#table" + tableId, tableName, data.orders);
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
 * 2. BEGIN addOrdersToHomePage
 * Creates the home page with all the orders
    Parameters: 
 */
function addOrdersToHomePage(tableId, tableName, data) {
	console.info("DEBUG > BEGIN addOrdersToHomePage: " + tableId); 
	
	$.each(data, function() {
		var orderId = this.orderId;
		var htmlOrderId = "#order" + orderId;
		var orderTime = new Date(this.createdTime).toLocaleTimeString();
		var orderStatus = this.status;

		var theOrder = '' +
			'<li id="order' + orderId + '" class="ui-li ui-li-static ui-btn-up-c">' +
				'<p id="links" class="ui-li-aside ui-li-desc">' +
				'	<a href="javascript:void(0);" onclick="$(\'#order' + orderId + '\').print();setOrderStatusPending(' + orderId + ');return(false);">Print</a>' + 
				'</p>' + 
				'<p class="ui-li-desc">' +
				'	(<span id="orderStatus">' + orderStatus + '</span>) Ordered @ ' + orderTime + 
				'</p>' +
				'<h3 class="ui-li-heading">' +
				'	' + tableName +
				'</h3>' +
			'</li>';
		$(theOrder).appendTo(tableId);
		if (orderStatus == "Pending" || orderStatus == "PENDING") {
			var theLink = '' +
				'&nbsp;|&nbsp;<strong><a href="javascript:void(0);" onclick="setOrderStatusReady(' + orderId + ');return(false);">Mark as Ready for Pickup</a></strong>';
		$(theLink).appendTo(htmlOrderId + " p#links");
		}
		if (orderStatus == "Ready" || orderStatus == "READY") {
			var theLink = '' +
				'&nbsp;|&nbsp;<strong><a href="javascript:void(0);" onclick="setOrderStatusDelivered(' + orderId + ');return(false);">Mark as Delivered</a></strong>';
		$(theLink).appendTo(htmlOrderId + " p#links");
		}
		$.each(this.orderDishes, function() {
			var name = this.name;
			var quantity = this.quantity;
			var price = this.price;
			var theItem = '' +
				'<p class="ui-li-desc">' +
				'	<strong>' + name + '</strong> ' + quantity + ' @ &#8377;' + price + 
				'</p>';
			$(theItem).appendTo(htmlOrderId);
		});
 	});
	console.log("DEBUG > END addOrdersToHomePage");	
}
/* 
 * END addOrderToHomePage
 **********/





 
 /* 
 *******************************************************
                     DATA FUNCTIONS
 ******************************************************* 
 */

function setOrderStatusPending(orderId) {
	setOrderStatus(orderId, "Pending");
}
function setOrderStatusReady(orderId) {
	setOrderStatus(orderId, "Ready");
}
function setOrderStatusDelivered(orderId) {
	setOrderStatus(orderId, "Delivered");
}
/***********
 * BEGIN setOrderStatus
 */
function setOrderStatus(orderId, status) {
console.log("DEBUG > BEGIN setOrderStatus");	
//status=<NEW|Pending|Ready|Delivered>
var statusApi = comCookedSpeciallyApiPrefix + "/order/setOrderStatus?orderId=" + orderId + "&status=" + status;

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
 * END setOrderStatus
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
 
 
 
 
 	