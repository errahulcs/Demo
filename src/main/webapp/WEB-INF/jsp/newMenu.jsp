
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Cooked specially</title>
	<link rel="stylesheet" href="/CookedSpecially/themes/base/jquery.ui.all.css">
	<script src="/CookedSpecially/js/jquery-1.9.0.js"></script>
	<script src="/CookedSpecially/js/ui/jquery-ui.js"></script>	

	<!--  <link rel="stylesheet" href="../demos.css">  -->
	<style>
	#section { list-style-type: none; margin: 1 px; padding: 1 px; width: 60%; border-style:solid; border-width:1px; }
	#section li { margin: 0 3px 3px 3px; padding: 0.4em; padding-left: 1.5em; font-size: 1.4em;  border-style:solid; border-width:1px}
	.dish { list-style-type: none; margin: 1 px; padding: 1 px;  border-style:solid; border-width:1px;  }
	.dish li { margin: 0 3px 3px 3px; padding: 0.4em; padding-left: 1.5em; font-size: 1.4em; border-style:solid; border-width:1px;}
	
	</style>
	<script>
	$(function() {
		$("#addSection").click(function( event ) {
			var count = $("#section").children().size();			
			if(count < 1) {
        			$( "#section" ).sortable();
				$( "#section" ).disableSelection();
			}
			

			$("#section").append('<li "ui-state-default">Section ' + (count+1) + '<button  class="addDish" style="float:right">+</button> <button class="removeSection" style="float:right">x</button><ul id="dish' + (count+1) + '" class="dish" ></ul> </li>');
			$(".addDish").unbind("click");
			$(".addDish").bind("click", function(event ) {
				var el = $(this).parent().find(".dish"); 
				var countDishes = el.find("li").size(); 
				if(countDishes < 1) {
				 	el.sortable();	
					el.disableSelection();
				} 
				el.append('<li class="ui-state-default">Dish ' + (countDishes + 1) + '<button class="removeDish" style="float:right">-</button></li>');
				$(".removeDish").unbind("click");
				$(".removeDish").bind("click", function(event ) {
					$(this).parent().remove();
				});
			});

			$(".removeSection").unbind("click");
			$(".removeSection").bind("click", function(event ) {
				$(this).parent().remove();
			});
			
      });		
		
	});
	</script>
</head>
<body>

<div id="menu">
Some Menu
<ul id="section">

</ul>
<button id="addSection">Add Section</button>
</div>



</body>
</html>
