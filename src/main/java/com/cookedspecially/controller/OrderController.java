/**
 * 
 */
package com.cookedspecially.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cookedspecially.dao.CheckDAO;
import com.cookedspecially.domain.Check;
import com.cookedspecially.domain.Customer;
import com.cookedspecially.domain.JsonDish;
import com.cookedspecially.domain.JsonOrder;
import com.cookedspecially.domain.Menus;
import com.cookedspecially.domain.Order;
import com.cookedspecially.domain.OrderDish;
import com.cookedspecially.domain.OrderResponse;
import com.cookedspecially.domain.SeatingTable;
import com.cookedspecially.enums.order.DestinationType;
import com.cookedspecially.enums.order.SourceType;
import com.cookedspecially.enums.order.Status;
import com.cookedspecially.service.CheckService;
import com.cookedspecially.service.CustomerService;
import com.cookedspecially.service.MenuService;
import com.cookedspecially.service.OrderDishService;
import com.cookedspecially.service.OrderService;
import com.cookedspecially.service.SeatingTableService;
import com.cookedspecially.utility.StringUtility;

/**
 * @author shashank
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private SeatingTableService seatingTableService;
	
	@Autowired
	private OrderDishService orderDishService;

	@Autowired
	private MenuService menuService;
	
	@Autowired
	private CheckService checkService;
	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping("/")
	public String listOrders(Map<String, Object> map, HttpServletRequest request) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		Integer userId = (Integer) request.getSession().getAttribute("userId");
		queryMap.put("userId", userId);
		map.put("orders", orderService.listOrders(queryMap));
		return "orders";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addOrder(Map<String, Object> map, @ModelAttribute("order") Order order ) {
		order.setRestaurantId(order.getUserId());
		order.setStatus(Status.PLACED);
		orderService.addOrder(order);
		
		return "redirect:/orders/";
	}

	@RequestMapping("/closeCheck")
	public @ResponseBody String closeCheck(HttpServletRequest request, HttpServletResponse response) {
		int tableId = Integer.parseInt(request.getParameter("tableId"));
		int restaurantId = Integer.parseInt(request.getParameter("restaurantId"));
		SeatingTable seatingTable = seatingTableService.getSeatingTable(tableId);
		Check check = checkService.getCheckByTableId(restaurantId, tableId);
		if (seatingTable != null) {
			seatingTable.setStatus(com.cookedspecially.enums.table.Status.AVAILABLE);
			
			if (check != null) {
				check.setStatus(com.cookedspecially.enums.check.Status.PAID);
				check.setCloseTime(new Date());
				check.setModifiedTime(new Date());
				checkService.addCheck(check);
			}
			seatingTableService.addSeatingTable(seatingTable);
		}
		String result = "";
		if (seatingTable != null && check != null) {
			result = "Checked out table: " + tableId + " check Id : " + check.getCheckId();
		} else {
			result = "Nothing to checkout";
		}
		return result;
	}
	
	@RequestMapping("/checkout")
	public @ResponseBody String checkout(HttpServletRequest request, HttpServletResponse response) {
		int tableId = Integer.parseInt(request.getParameter("tableId"));
		int restaurantId = Integer.parseInt(request.getParameter("restaurantId"));
		Check check = checkService.getCheckByTableId(restaurantId, tableId);
			
		if (check != null) {
			check.setStatus(com.cookedspecially.enums.check.Status.READYTOPAY);
			check.setModifiedTime(new Date());
			checkService.addCheck(check);
		}
		
		String result = "";
		if (check != null) {
			result = "Checked out table: " + tableId + " check Id : " + check.getCheckId();
		} else {
			result = "Nothing to checkout";
		}
		return result;
	}
	
	@RequestMapping("/checkoutTable")
	public @ResponseBody String checkoutTable(HttpServletRequest request, HttpServletResponse response){
		int tableId = Integer.parseInt(request.getParameter("tableId"));
		int restaurantId = Integer.parseInt(request.getParameter("restaurantId"));
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("restaurantId", restaurantId);
		queryMap.put("sourceType", SourceType.TABLE);
		queryMap.put("sourceId", tableId);
		queryMap.put("status", Status.PLACED);
		List<Order> orders = orderService.listOrders(queryMap);
		String paidOrders = "";
		for(Order order : orders) {
			order.setStatus(Status.PAID);
			orderService.addOrder(order);
			if (!StringUtility.isNullOrEmpty(paidOrders)) {
				paidOrders += ',';
			}
			paidOrders += order.getOrderId();
		}
		return "Checked out table: " + tableId +" Orders:" + paidOrders;
	}
	
	@RequestMapping("/getactiveorders")
	public @ResponseBody List<Order> getActiveOrdersJsonByTable(HttpServletRequest request, HttpServletResponse response){
		int tableId = Integer.parseInt(request.getParameter("tableId"));
		Map<String, Object> queryMap = new HashMap<String, Object>();
		Integer userId = (Integer) request.getSession().getAttribute("userId");
		queryMap.put("userId", userId);
		queryMap.put("sourceType", SourceType.TABLE);
		queryMap.put("sourceId", tableId);
		queryMap.put("status", Status.PLACED);
		return orderService.listOrders(queryMap);		
	}
	
	@RequestMapping(value = "/submitTableOrder", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody OrderResponse submitOrder(@RequestBody JsonOrder order, Model model, HttpServletRequest request) {
		Order targetOrder = new Order();
		Integer userId = (Integer) request.getSession().getAttribute("userId");
		targetOrder.setUserId(userId);
		targetOrder.setRestaurantId(userId);
		targetOrder.setCreatedTime(new Date());
		targetOrder.setSourceType(SourceType.TABLE);
		targetOrder.setSourceId(order.getTableId());
		targetOrder.setDestinationType(DestinationType.TABLE);
		targetOrder.setDestinationId(order.getTableId());
		targetOrder.setStatus(Status.PLACED);
		List<JsonDish> jsonDishes = order.getItems();
		Float bill = 0.0f;
		HashMap<Integer, OrderDish> orderDishMap = new HashMap<Integer, OrderDish>();
		for (JsonDish jsonDish  : jsonDishes) {
			if (orderDishMap.get(jsonDish.getId()) != null) {
				orderDishMap.get(jsonDish.getId()).addMore(1);
			} else {
				OrderDish orderDish = new OrderDish();
				orderDish.setDishId(jsonDish.getId());
				orderDish.setQuantity(1);
				orderDish.setPrice(jsonDish.getPrice());
				orderDishMap.put(orderDish.getDishId(), orderDish);
			}
			bill += jsonDish.getPrice();
		}
		targetOrder.setBill(bill);
		if (orderDishMap.size() > 0) {
			targetOrder.setOrderDishes(new ArrayList<OrderDish>(orderDishMap.values()));
		}
		orderService.addOrder(targetOrder);
		OrderResponse orderResp = new OrderResponse();
		orderResp.setOrderId(targetOrder.getOrderId());
		return orderResp;
	}
	
	@RequestMapping(value = "/tableOrder")
	public String tableOrder(Map<String, Object> map, HttpServletRequest request) {
		Integer menuId = Integer.getInteger(request.getParameter("menuId"));
		map.put("menu", menuService.getMenu(menuId));
		Integer userId = (Integer) request.getSession().getAttribute("userId");
		List<SeatingTable> tables = seatingTableService.listSeatingTableByUser(userId);
		map.put("tables", tables);
		
		return "orders";
	}

	
	@RequestMapping("/cancel/{orderId}")
	public String cancelOrder(Map<String, Object> map, HttpServletRequest request, @PathVariable("orderId") Integer orderId) {
		try {
			Order order = orderService.getOrder(orderId);
			order.setStatus(Status.CANCELLED);
			orderService.addOrder(order);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOrders(map, request);
	}
	
	@RequestMapping("/delete/{orderId}")
	public String deleteOrder(Map<String, Object> map, HttpServletRequest request, @PathVariable("orderId") Integer orderId) {

		try {
			orderService.removeOrder(orderId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOrders(map, request);
	}

	@RequestMapping(value = "/getCheck.json", method = RequestMethod.GET)
	public @ResponseBody Check getCheckJSON(HttpServletRequest request, HttpServletResponse response) {
		String tableIdStr = request.getParameter("tableId");
		String custIdStr = request.getParameter("custId");
		Integer restaurantId = Integer.parseInt(request.getParameter("restaurantId"));
		Check check = null;
		Integer tableId = -1;
		Integer custId = -1;
		if (!StringUtility.isNullOrEmpty(tableIdStr)) {
			tableId = Integer.parseInt(tableIdStr);
			check = checkService.getCheckByTableId(restaurantId, tableId);
		} else if (!StringUtility.isNullOrEmpty(custIdStr)) {
			custId = Integer.parseInt(custIdStr);
			check = checkService.getCheckByCustId(restaurantId, custId);
		} 
		
		if (check == null) {
			check = new Check();
			check.setRestaurantId(restaurantId);
			check.setOpenTime(new Date());
			check.setStatus(com.cookedspecially.enums.check.Status.UNPAID);
			SeatingTable table = null;
			if (tableId > 0) {
				table = seatingTableService.getSeatingTable(tableId);
				if (table != null) {
					check.setTableId(tableId);
					table.setStatus(com.cookedspecially.enums.table.Status.BUSY);
					seatingTableService.addSeatingTable(table);
				}
			}
			Customer customer = null;
			if (custId > 0) {
				customer = customerService.getCustomer(custId);
				if (customer != null) {
					check.setCustomerId(custId);
				}
			}
			if (table != null || customer != null) {
				checkService.addCheck(check);
			}
		}
		
		return check;
	}
	
	@RequestMapping(value = "/setCheckStatus", method = RequestMethod.POST)
	public @ResponseBody Check setCheckStatus(HttpServletRequest request, HttpServletResponse response) {
		Integer checkId = Integer.parseInt(request.getParameter("checkId"));
		String statusStr = request.getParameter("status");
		com.cookedspecially.enums.check.Status status = com.cookedspecially.enums.check.Status.valueOf(com.cookedspecially.enums.check.Status.class, statusStr);
		Check check = checkService.getCheck(checkId);
		check.setStatus(status);
		return check;
	}
	
	@RequestMapping(value = "/addToCheck.json", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody OrderResponse addToCheckJSON(@RequestBody JsonOrder order, Model model, HttpServletRequest request) {
		
		Order targetOrder = new Order();
		Integer userId = (Integer) request.getSession().getAttribute("userId");
		targetOrder.setUserId(userId);
		targetOrder.setRestaurantId(userId);
		targetOrder.setCreatedTime(new Date());
		if (order.getTableId() > 0) {
			targetOrder.setSourceType(SourceType.TABLE);
			targetOrder.setSourceId(order.getTableId());
			targetOrder.setDestinationType(DestinationType.TABLE);
			targetOrder.setDestinationId(order.getTableId());	
		} else if (order.getCustId() > 0) {
			targetOrder.setSourceType(SourceType.COUNTER);
			targetOrder.setSourceId(order.getCustId());
			targetOrder.setDestinationType(DestinationType.COUNTER);
			targetOrder.setDestinationId(order.getCustId());
		}
		
		targetOrder.setStatus(Status.PLACED);
		List<JsonDish> jsonDishes = order.getItems();
		Float bill = 0.0f;
		HashMap<Integer, OrderDish> orderDishMap = new HashMap<Integer, OrderDish>();
		for (JsonDish jsonDish  : jsonDishes) {
			if (orderDishMap.get(jsonDish.getId()) != null) {
				orderDishMap.get(jsonDish.getId()).addMore(1);
			} else {
				OrderDish orderDish = new OrderDish();
				orderDish.setDishId(jsonDish.getId());
				orderDish.setQuantity(1);
				orderDish.setPrice(jsonDish.getPrice());
				orderDishMap.put(orderDish.getDishId(), orderDish);
			}
			bill += jsonDish.getPrice();
		}
		targetOrder.setBill(bill);
		if (orderDishMap.size() > 0) {
			targetOrder.setOrderDishes(new ArrayList<OrderDish>(orderDishMap.values()));
		}
		//orderService.addOrder(targetOrder);
		Check check = null;
		if (order.getCheckId() > 0) {
			check = checkService.getCheck(order.getCheckId());
			if (check != null && check.getStatus() == com.cookedspecially.enums.check.Status.UNPAID) {
				List<Order> orders = check.getOrders();
				orders.add(targetOrder);
				check.setModifiedTime(new Date());
				checkService.addCheck(check);
			}
		}
		
		OrderResponse orderResp = new OrderResponse();
		orderResp.setOrderId(targetOrder.getOrderId());
		return orderResp;
	}
}
