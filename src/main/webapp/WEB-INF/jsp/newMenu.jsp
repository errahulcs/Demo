<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
	.ui-multiselect.dishDialog {font-size:1.0em;}
	.ui-multiselect-menu.dishDialog {font-size:1.0em;}
	</style>
	<script type="text/javascript" src="/CookedSpecially/js/nicEdit.js"></script>
	<script type="text/javascript">
		bkLib.onDomLoaded(function() { 
			//nicEditors.allTextAreas()
			var nicEditorInstance = new nicEditor({fullPanel : false, buttonList : ['bold','italic','underline','left','center','right', 'justify', 'ol', 'ul', 'subscript', 'superscript', 'strikethrough', 'removeformat', 'indent', 'outdent', 'hr', 'forecolor', 'bgcolor', 'fontSize', 'fontFamily', 'fontFormat']});
			nicEditorInstance.panelInstance('menuDescription');
		});
	</script>
	<script>
	function removeDish(dishEL) {
		//alert(dishEL);
		//var sectionEl = dishEL.parent().parent().parent();
		
		//var dishesEl = dishEL.parent().parent();
		dishEL.parent().remove();
		/*var dishELs = el.find("li");
		var addedDishIds = new Array();
		for (var i = 0; i < countDishes; i++) {
			addedDishIds[i] = dishELs[i].attributes["data-dishid"].value;
		}
		sectionEl.find(".addedDishIds")[0].value = addedDishIds.toString();
		*/
	}
	function editSection(sectionEditBtnEL) {
		var sectionEL = sectionEditBtnEL.parent();
		var sectionELId = sectionEL.attr('id');
		$('#addSection').hide();
		$('#saveSection').attr("data-sectionCount", sectionELId);
		$('#saveSection').show();
		$('#sectionId').val(sectionEL.find('.sectionSectionId')[0].value);
		$('#name').val(sectionEL.find('.sectionName')[0].value);
		$('#price').val(sectionEL.find('.sectionPrice')[0].value);
		$('#description').val(sectionEL.find('.sectionDescription')[0].value);
		$('#header').val(sectionEL.find('.sectionHeader')[0].value);
		$('#footer').val(sectionEL.find('.sectionFooter')[0].value);
		$('#sectionForm').dialog();
	}
	function removeSection(sectionEL) {
		
		$(sectionEL.parent()).find(".validSection")[0].value = false;
		$(sectionEL.parent()).hide();
		
	}
	function addDishes(dishesELId) {
		var el = $('#' + dishesELId); 
		
		var dishELs = el.find("li");
		var countDishes = dishELs.size();
		var dishes = {};
		var dishIdListOptions = $("#dishIdList").find("option");
		
		for (var i = 0; i < countDishes; i++) {
			var dishId = dishELs[i].attributes["data-dishid"].value;
			var dishName = dishELs[i].attributes["data-dishname"].value;
			dishes[dishId] = dishName;
		}
		for (var i =0; i < dishIdListOptions.size(); i++) {
			optionDishId = dishIdListOptions[i].value;
			if (optionDishId in dishes) {
				if(!(dishIdListOptions[i].selected)) {
					$("#ui-multiselect-dishIdList-option-" + i).click();
				}
			} else {
				if(dishIdListOptions[i].selected) {
					$("#ui-multiselect-dishIdList-option-" + i).click();
				}
			}
		}
		$("#addDish").attr("data-disheselid", dishesELId);
		//$("#dishSection").dialog("open");
		$("#dishSection").dialog();
	}
	
	function addSelectedDish() {
		var dishesELId = $("#addDish").attr("data-disheselid");
		var el = $('#' + dishesELId);
		
		var dishELs = el.find("li");
		var dishes = {};
		//var addedDishIds = new Array();
		//var j = 0;
		for (var i = 0; i < dishELs.size(); i++) {
			var dishId = dishELs[i].attributes["data-dishid"].value;
			var dishName = dishELs[i].attributes["data-dishname"].value;
			dishes[dishId] = dishName;
			//addedDishIds[j++] = dishId;
			
		}
		if (dishELs.size() < 1) {
			el.sortable();	
			el.disableSelection();
		}
		var dishIdListOptions = $("#dishIdList").find("option");
		for (var i =0; i < dishIdListOptions.size(); i++) {
			optionDishId = dishIdListOptions[i].value;
			if (!(optionDishId in dishes)) {
				if(dishIdListOptions[i].selected) {
					var dishId = dishIdListOptions[i].value;
					var dishName = dishIdListOptions[i].text;
					el.append('<li class="ui-state-default" style="font-size:0.8em;" data-dishid="'+ dishId + '" data-dishname="' + dishName + '">' + dishName 
							+ '<button type="button" class="removeDish" onclick="removeDish($(this));" style="float:right">-</button></li>');
					//addedDishIds[j++] = dishId;
				}
			}
		}
		//el.parent().find(".addedDishIds")[0].value = addedDishIds.toString();
		$("#dishSection").dialog("close");
	}
	function submitMenu() {
		var sectionELs = $("#section").find("li.section");
		
		for (var i = 0;  i < sectionELs.size(); i++) {
			$(sectionELs[i]).find(".position")[0].value = i;
			var dishELs = $($(sectionELs[i]).find(".dish")[0]).find("li");
			
			var addedDishIds = new Array();
			for (var j = 0 ; j < dishELs.size(); j++) {
				addedDishIds[j] = dishELs[j].attributes["data-dishid"].value;
			}
			
			$(sectionELs[i]).find(".addedDishIds")[0].value = addedDishIds.toString();
		}
		//$("#menuForm").find('[name="description"]')[0].value = $("menuDescription").val();
		$('#menuDescription').val($($('#menuDescription').prev()).find('div.nicEdit-main').html());
		$("#menuForm").submit();
	}
	function openSectionForm() {
		
		$('#sectionId').val("");
		$('#name').val("");
		$('#price').val("");
		$('#description').val("");
		$('#header').val("");
		$('#footer').val("");
		$('#addSection').show();
		$('#saveSection').hide();
		$('#sectionForm').dialog();
	}
	$(function() {
		
		$("select#dishIdList").multiselect({classes: "dishDialog"}).multiselectfilter();
		$( "#section" ).sortable();
		$( "#section" ).disableSelection();
		$( ".dish" ).sortable();
		$( ".dish" ).disableSelection();
		$("#saveSection").click(function( event ) {
			var name = $( "#name" ).val(),
			 price = $( "#price" ).val(),
			 description = $( "#description" ).val(),
			 header = $("#header").val(),
			 footer = $("#footer").val(),
			 sectionId = $("#sectionId").val();
			$('#sectionForm').dialog('close');
			var sectionELId = $(this).attr("data-sectionCount");
			var sectionEL = $('#' + sectionELId);
			$(sectionEL.find('.sectionNameText')[0]).text(name);
			sectionEL.find('.sectionName')[0].value = name;
			sectionEL.find('.sectionPrice')[0].value = price;
			sectionEL.find('.sectionDescription')[0].value = description;
			sectionEL.find('.sectionHeader')[0].value = header;
			sectionEL.find('.sectionFooter')[0].value = footer;
		});
		$("#addSection").click(function( event ) {
			 var name = $( "#name" ).val(),
			 price = $( "#price" ).val(),
			 description = $( "#description" ).val(),
			 header = $("#header").val(),
			 footer = $("#footer").val(),
			 sectionId = $("#sectionId").val();
			$('#sectionForm').dialog("close");
			var count = $("#section").children().size();			
			//if(count < 1) {
        		
			//}
			

			$("#section").append('<li id="section' + count + '" class="section ui-state-default"><span class="sectionNameText">' + name + '</span>'
					+ '<input type="hidden" class="addedDishIds" name="sections[' + count +'].dishIds" value=""/>'
					+ '<input type="hidden" class="sectionSectionId" name="sections[' + count +'].sectionId" value="' + sectionId + '"/>'
					+ '<input type="hidden" class="sectionName" name="sections[' + count +'].name" value="' + name + '"/>'
					+ '<input type="hidden" class="sectionPrice" name="sections[' + count +'].price" value="' + price + '"/>'
					+ '<input type="hidden" class="sectionDescription" name="sections[' + count +'].description" value="' + description + '"/>'
					+ '<input type="hidden" class="sectionHeader" name="sections[' + count +'].header" value="' + header + '"/>'
					+ '<input type="hidden" class="sectionFooter" name="sections[' + count +'].footer" value="' + footer + '"/>'
					+ '<input type="hidden" name="sections[' + count +'].valid" class="validSection" value="true"/>'
					+ '<input type="hidden" name="sections[' + count +'].position" class="position" value="'+count+'"/>'
					+ '<button type="button" onclick="removeSection($(this));" style="float:right">x</button>'
					+ '<button type="button" onclick="editSection($(this));" style="float:right">E</button>'
					+ '<button type="button" class="addDish" onclick="addDishes(\'dish'+count + '\')" style="float:right">+</button>'
					+ '<ul id="dish' + count + '" class="dish" ></ul> </li>');
			
			$( "#dish" + count ).sortable();
			$( "#dish" + count ).disableSelection();
			/*
			$(".removeSection").unbind("click");
			$(".removeSection").bind("click", function(event ) {
				$(this).parent().find(".validSection")[0].value = false;
				$(this).parent().hide();
			});
			*/
      });		
	  /*
	  $("#dishSection").dialog({
		autoOpen: false,
		width:450,
		modal:true,
		//dialogClass:"dishDialog",
		hide: "explode"
	  });
	  */
	});
	</script>
</head>
<body>

<div id="menu">

<form id="menuForm" action="/CookedSpecially/menu/addNew" method="post"  enctype="multipart/form-data">
<input type="hidden" name="userId" id="userId" value='<%=request.getSession().getAttribute("userId")%>'/>
<input type="hidden" name="menuId" id="menuId" value="${menu.menuId}"/>
<input type="hidden" name="imageUrl" id="imageUrl" value="${menu.imageUrl}"/>
Name: <input type="text" name="name"  placeholder="Name" value="${menu.name}"/><br/>
Description: <textarea id="menuDescription" name="description" placeholder="Description" style="width: 640px">${menu.description}</textarea><br/>
Menu Image (${menu.imageUrl}): <input type="file" name="file"/> <br/>
Status: 
<select name="status">
<c:forEach items="${statusTypes}" var="statusType">
	<c:choose>
		<c:when test="${statusType == menu.status }">
			<option value="${statusType}" selected="selected">${statusType}</option>
		</c:when>
		<c:otherwise>
			<option value="${statusType}">${statusType}</option>
		</c:otherwise>
	</c:choose>
</c:forEach>
</select>
<ul id="section">
<c:if test="${!empty menu.sections}">
<c:set var="sectionCount" value='0'/>
<c:forEach items="${menu.sections}" var="section">
<li id="section${sectionCount}" class="section ui-state-default"> <span class="sectionNameText">${section.name}</span> 
	<input type="hidden" class="addedDishIds" name="sections[${sectionCount}].dishIds" value=""/>
	<input type="hidden" class="sectionSectionId" name="sections[${sectionCount}].sectionId" value="${section.sectionId}"/>
	<input type="hidden" class="sectionName" name="sections[${sectionCount}].name" value="${section.name}"/>
	<input type="hidden" class="sectionPrice" name="sections[${sectionCount}].price" value="${section.price}"/>
	<input type="hidden" class="sectionDescription" name="sections[${sectionCount}].description" value="${section.description}"/>
	<input type="hidden" class="sectionHeader" name="sections[${sectionCount}].header" value="${section.header}"/>
	<input type="hidden" class="sectionFooter" name="sections[${sectionCount}].footer" value="${section.footer}"/>
	<input type="hidden" name="sections[${sectionCount}].valid" class="validSection" value="true"/>
	<input type="hidden" name="sections[${sectionCount}].position" class="position" value="${sectionCount}"/>
	<button type="button" onclick="removeSection($(this));" style="float:right">x</button>
	<button type="button" onclick="editSection($(this));" style="float:right">E</button>
	<button type="button" class="addDish" onclick="addDishes('dish${sectionCount}')" style="float:right">+</button>
	<ul id="dish${sectionCount}" class="dish" >
		<c:if test="${!empty section.dishes}">
			<c:forEach items="${section.dishes}" var="dish">
				<li class="ui-state-default" style="font-size:0.8em;" data-dishid="${dish.dishId}" data-dishname="${dish.name}">${dish.name} 
					<button type="button" class="removeDish" onclick="removeDish($(this));" style="float:right">-</button>
				</li>	
			</c:forEach>
		</c:if>
		
	</ul>
</li>
<c:set var="sectionCount" value='${sectionCount + 1}'/>
</c:forEach>
</c:if>
</ul>
<button type="button" id="addSomeSection" onclick="openSectionForm();">Add Section</button> <br/>
<c:choose>
	<c:when test="${!empty menu.menuId}"><input type="button" onclick="submitMenu();" value="Save Menu"/></c:when>
	<c:otherwise><input type="button" onclick="submitMenu();" value="Add Menu"/></c:otherwise>
</c:choose>


</form>
 
</div>


<form id="sectionForm" hidden="true" title="Create Section ">
<input type="hidden" id="sectionId" name="sectionId" />
<input type="text" id="name" name="name" placeholder="Name" /> <input type="text" id="price" name="price" value="0.0" style="float: right;"/><br/>
( <input type="text" id="description" name="description" placeholder="Description"/> ) <br/>
<input type="text" id="header" name="header" placeholder="Header"/> <br>
Dishes go here.<br>
Dishes go here.<br>
Dishes go here.<br>
Dishes go here.<br>
<input type="text" id="footer" name="footer" placeholder="Footer" /><br>
<input type="button" id="addSection" value="Add Section" />
<input type="button" id="saveSection" value="Save Section" data-sectionCount="" hidden="true"/>
</form>

<form id="dishSection" hidden="true" title="Select Dishes">
<select id="dishIdList" name="selectedDishIds" multiple="multiple" style="width: 400px">
	<c:if test="${!empty dishList}">
		<c:forEach items="${dishList}" var="dishVar">
			<option value="${dishVar.dishId}">${dishVar.name}</option>
			
		</c:forEach>
	</c:if>
</select>
<input type="button" id="addDish" onclick="addSelectedDish();" value="Add Selected Dishes" />
</form>
</body>
</html>
