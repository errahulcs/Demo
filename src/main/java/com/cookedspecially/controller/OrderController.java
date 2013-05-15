/**
 * 
 */
package com.cookedspecially.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cookedspecially.domain.Order;
import com.cookedspecially.domain.SeatingTable;
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
		return "orders";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addOrder(Map<String, Object> map, @ModelAttribute("order") Order order ) {
		order.setRestaurantId(order.getUserId());
		orderService.addOrder(order);
		return "redirect:/orders/";
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
