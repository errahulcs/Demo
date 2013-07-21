<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<title>My jQuery JSON Web Page</title>
<head>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript">
var order = {number:20, tableId:1, time:'',price:'20.3',items:[]};
order.items[0]={id: "", name :"", price: ""};
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


JSONTest = function() {

    var resultDiv = $("#resultDivContainer");

    $.ajax({
        url: "http://localhost:8080/CookedSpecially/order/submitTableOrder",
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

<div id="resultDivContainer"></div>

<button type="button" onclick="JSONTest()">JSON</button>

</body>
</html> 