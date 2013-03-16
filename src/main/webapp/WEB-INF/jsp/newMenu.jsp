<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Cooked specially</title>
	<link rel="stylesheet" href="/CookedSpecially/themes/base/jquery.ui.all.css">
	<link rel="stylesheet" type="text/css" href="/CookedSpecially/themes/base/jquery.multiselect.css" />
	<link rel="stylesheet" type="text/css" href="/CookedSpecially/themes/base/jquery.multiselect.filter.css" />
	<script src="/CookedSpecially/js/jquery-1.9.0.js"></script>
	<script src="/CookedSpecially/js/ui/jquery-ui.js"></script>
	<script type="text/javascript" src="/CookedSpecially/js/jquery.multiselect.js"></script>
	<script type="text/javascript" src="/CookedSpecially/js/jquery.multiselect.filter.js"></script>	

	<!--  <link rel="stylesheet" href="../demos.css">  -->
	<style>
	#section { list-style-type: none; margin: 1 px; padding: 1 px; width: 60%; border-style:solid; border-width:1px; }
	#section li { margin: 0 3px 3px 3px; padding: 0.4em; padding-left: 1.5em; font-size: 1.4em;  border-style:solid; border-width:1px}
	.dish { list-style-type: none; margin: 1 px; padding: 1 px;  border-style:solid; border-width:1px;  }
	.dish li { margin: 0 3px 3px 3px; padding: 0.4em; padding-left: 1.5em; font-size: 1.4em; border-style:solid; border-width:1px;}
	
	</style>
	<script>
	function removeDish(dishEL) {
		//alert(dishEL);
		dishEL.parent().hide();
	} 
	function addDish(dishId) {
		var el = $('#' + dishId); 
		var countDishes = el.find("li").size(); 
			if(countDishes < 1) {
			 	el.sortable();	
				el.disableSelection();
			} 
			el.append('<li class="ui-state-default">Dish ' + (countDishes + 1) 
					+ '<button type="button" class="removeDish" onclick="removeDish($(this));" style="float:right">-</button></li>');
			//$(".removeDish").unbind("click");
			//$(".removeDish").bind("click", function(event ) {
			//	$(this).parent().hide();
			//});
	}
	$(function() {
		$("select").multiselect().multiselectfilter();
		$("#addSection").click(function( event ) {
			 var name = $( "#name" ).val(),
			 price = $( "#price" ).val(),
			 description = $( "#description" ).val(),
			 header = $("#header").val(),
			 footer = $("#footer").val();
			$('#sectionForm').dialog('close')
			var count = $("#section").children().size();			
			if(count < 1) {
        			$( "#section" ).sortable();
				$( "#section" ).disableSelection();
			}
			

			$("#section").append('<li id="section"' + count + ' "ui-state-default">' + name 
					+ '<input type="hidden" name="sections[' + count +'].name" value="' + name + '"/>'
					+ '<input type="hidden" name="sections[' + count +'].price" value="' + price + '"/>'
					+ '<input type="hidden" name="sections[' + count +'].description" value="' + description + '"/>'
					+ '<input type="hidden" name="sections[' + count +'].header" value="' + header + '"/>'
					+ '<input type="hidden" name="sections[' + count +'].footer" value="' + footer + '"/>'
					+ '<input type="hidden" name="sections[' + count +'].valid" class="validSection" value="true"/>'
					+ '<button type="button" class="removeSection" style="float:right">x</button>'
					+ '<button type="button" class="addDish" onclick="addDish(\'dish'+count + '\')" style="float:right">+</button>'
					+ '<ul id="dish' + count + '" class="dish" ></ul> </li>');
			
			
			
			$(".removeSection").unbind("click");
			$(".removeSection").bind("click", function(event ) {
				$(this).parent().find(".validSection")[0].value = false;
				$(this).parent().hide();
			});
			
      });		
		
	});
	</script>
</head>
<body>

<div id="menu">
Some Menu
<form id="menuForm" action="/CookedSpecially/menu/addNew" method="post">
<ul id="section">

</ul>
<input type="submit" value="Add Menu"/>

</form>
<button id="addSomeSection" onclick="$('#sectionForm').dialog();">Add Section</button> 
</div>


<form id="sectionForm" hidden="true">
<input type="text" id="name" name="name" placeholder="Name" /> <input type="text" id="price" name="price" value="0.0" style="float: right;"/><br/>
( <input type="text" id="description" name="description" placeholder="Description"/> ) <br/>
<input type="text" id="header" name="header" placeholder="Header"/> <br>
Dishes goes here.<br>
Dishes goes here.<br>
Dishes goes here.<br>
Dishes goes here.<br>
<input type="text" id="footer" name="footer" placeholder="Footer" /><br>
<input type="button" id="addSection" value="Add Section" />
</form>

<form id="dishSection">
<select id="dishIdList" name="dishIds" multiple="multiple" style="width:370px">
	<c:if test="${!empty dishList}">
		<c:forEach items="${dishList}" var="dishVar">
			<option value="${dishVar.dishId}">${dishVar.name}</option>
			
		</c:forEach>
	</c:if>
</select>
</form>
</body>
</html>
