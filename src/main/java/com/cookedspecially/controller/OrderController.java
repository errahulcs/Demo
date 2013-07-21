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
import com.cookedspecially.service.MenuService;
import com.cookedspecially.service.OrderDishService;
import com.cookedspecially.service.OrderService;
import com.cookedspecially.service.SeatingTableService;

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
		orderService.addOrder(order);
		return "redirect:/orders/";
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

}
