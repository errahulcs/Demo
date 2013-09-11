<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<title>My jQuery JSON Web Page</title>
<head>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<base href="${pageContext.request.contextPath}/"/>
<script type="text/javascript">
var order = {number:20, checkId:2, tableId:1, time:'',price:'20.3',items:[]};
var itemsCount = 0;
/*order.items[0]={id: "", name :"", price: ""};
order.items[1]={id: "", name :"", price: ""};
order.items[2]={id: "", name :"", price: ""};
order.items[0].id=1;
order.items[1].id=2;
order.items[2].id=3;
order.items[0].name='Shashank';
order.items[1].name='Shashank2';
order.items[2].name='Shashank 3';
order.items[0].price='10.1';
order.items[1].price='10.3';
order.items[2].price='10.5';
*/
function initializeOrder(){
    var formValues = $('input[type=text]');
    order = {};
    itemsCount = 0;
    $.map(formValues, function(n, i) {
        order[n.name] = $(n).val();
    });
	order['items'] = [];
    console.log(JSON.stringify(order));
    alert('order initialized');
}

function addItemToOrder(){
    var formValues = $('input[type=text]');
    order.items[itemsCount] = {};
    
    $.map(formValues, function(n, i) {
    	order.items[itemsCount][n.name] = $(n).val();
    });
	itemsCount = itemsCount + 1;
    console.log(JSON.stringify(order));
    alert('item added to order');
}

SubmitOrderJSON = function() {

    var resultDiv = $("#resultDivContainer");

    $.ajax({
        url: "order/addToCheck.json",
        type: "POST",
        data: JSON.stringify(order),
        contentType:"application/json; charset=utf-8",
        dataType: "json",
        success: function (result) {
            switch (result) {
                case true:
                    processResponse(result);
                    break;
                default:
                    resultDiv.html(result);
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
        alert(xhr.status);
        alert(thrownError);
        }
    });
};

</script>
</head>
<body>

<h1>My jQuery JSON Web Page</h1>
<form id="createOrderForm" action="#" method="post">
    <table>
        <tr><td> CheckId: </td><td><input type="text" name="checkId" id="checkId"/>  </td></tr>
        <tr><td> TableId </td><td><input type="text" name="tableId" id="tableId"/>  </td></tr>
        
        <tr><td>  </td><td>  <input type="button" id="createOrder" value="Initialize Order" onclick="initializeOrder()" /></td></tr>

    </table>
</form>

<form id="addItemToOrderForm" action="#" method="post">
    <table>
        <tr><td> ItemId: </td><td><input type="text" name="id" id="id"/>  </td></tr>
        <tr><td> Price: </td><td><input type="text" name="price" id="price"/>  </td></tr>
        
        <tr><td>  </td><td>  <input type="button" id="addItem" value="Add Item to Order" onclick="addItemToOrder()" /></td></tr>

    </table>
</form>

<div id="resultDivContainer"></div>

<button type="button" onclick="SubmitOrderJSON()">Submit Order JSON</button>

</body>
</html> 