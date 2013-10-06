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
import com.cookedspecially.domain.CheckDishResponse;
import com.cookedspecially.domain.CheckResponse;
import com.cookedspecially.domain.Customer;
import com.cookedspecially.domain.JsonDish;
import com.cookedspecially.domain.JsonOrder;
import com.cookedspecially.domain.Menus;
import com.cookedspecially.domain.Order;
import com.cookedspecially.domain.OrderDish;
import com.cookedspecially.domain.OrderResponse;
import com.cookedspecially.domain.SeatingTable;
import com.cookedspecially.domain.User;
import com.cookedspecially.enums.order.DestinationType;
import com.cookedspecially.enums.order.SourceType;
import com.cookedspecially.enums.order.Status;
import com.cookedspecially.service.CheckService;
import com.cookedspecially.service.CustomerService;
import com.cookedspecially.service.MenuService;
import com.cookedspecially.service.OrderDishService;
import com.cookedspecially.service.OrderService;
import com.cookedspecially.service.SeatingTableService;
import com.cookedspecially.service.UserService;
import com.cookedspecially.utility.MailerUtility;
import com.cookedspecially.utility.StringUtility;

/**
 * @author shashank
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private UserService userService;
	
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
		order.setStatus(Status.NEW);
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
			seatingTable.setStatus(com.cookedspecially.enums.table.Status.Available);
			
			if (check != null) {
				check.setStatus(com.cookedspecially.enums.check.Status.Paid);
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
			check.setStatus(com.cookedspecially.enums.check.Status.Readytopay);
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
		queryMap.put("status", Status.NEW);
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
		queryMap.put("status", Status.NEW);
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
		targetOrder.setStatus(Status.NEW);
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

	private Check getCheck(String tableIdStr, String custIdStr, Integer restaurantId) {
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
			// We use restaurant Id as userId currently. will have to fix this in future.
			Integer userId = restaurantId;
			User user = userService.getUser(userId);
			if (user != null) {
				check.setAdditionalChargesName1(user.getAdditionalChargesName1());
				check.setAdditionalChargesName2(user.getAdditionalChargesName2());
				check.setAdditionalChargesName3(user.getAdditionalChargesName3());
			}
			check.setStatus(com.cookedspecially.enums.check.Status.Unpaid);
			SeatingTable table = null;
			if (tableId > 0) {
				table = seatingTableService.getSeatingTable(tableId);
				if (table != null) {
					check.setTableId(tableId);
					table.setStatus(com.cookedspecially.enums.table.Status.Busy);
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
		} else {
			List<Order> orders = check.getOrders();
			if (orders != null && orders.size() > 0 && check.getBill() == 0) {
				// This is a case where migration is not performed.
				// We use restaurant Id as userId currently. will have to fix this in future.
				Integer userId = check.getRestaurantId();
				User user = userService.getUser(userId);
				if (user != null) {
					check.setAdditionalChargesName1(user.getAdditionalChargesName1());
					check.setAdditionalChargesName2(user.getAdditionalChargesName2());
					check.setAdditionalChargesName3(user.getAdditionalChargesName3());
					float checkBill = 0;
					for (Order order : orders) {
						checkBill += order.getBill();
					}
					check.setBill(checkBill);
					check.setAdditionalChargesValue1(user.getAdditionalCharge(checkBill, user.getAdditionalChargesType1(), user.getAdditionalChargesValue1()));
					check.setAdditionalChargesValue2(user.getAdditionalCharge(checkBill, user.getAdditionalChargesType2(), user.getAdditionalChargesValue2()));
					check.setAdditionalChargesValue3(user.getAdditionalCharge(checkBill, user.getAdditionalChargesType3(), user.getAdditionalChargesValue3()));
					checkService.addCheck(check);
				}
			}
		}
		
		return check;
	}
	
	private CheckResponse getCheckResponse(String tableIdStr, String custIdStr, Integer restaurantId) {
		Check check = getCheck(tableIdStr, custIdStr, restaurantId);
		if (check != null) {
			return new CheckResponse(check);
		} else {
			return null;
		}
	}
	
	private CheckResponse getCheckResponseByCheckId(String checkIdStr) {
		if (!StringUtility.isNullOrEmpty(checkIdStr)) {
			Integer checkId = Integer.parseInt(checkIdStr);
			Check check = checkService.getCheck(checkId);
			if (check != null) {
				return new CheckResponse(check);
			}
		}
		return null;
	}
	
	@RequestMapping(value = "/getCheckWithOrders.json", method = RequestMethod.GET)
	public @ResponseBody Check getCheckWithOrderJSON(HttpServletRequest request, HttpServletResponse response) {
		String tableIdStr = request.getParameter("tableId");
		String custIdStr = request.getParameter("custId");
		Integer restaurantId = Integer.parseInt(request.getParameter("restaurantId"));
		return getCheck(tableIdStr, custIdStr, restaurantId);
	}
	
	@RequestMapping(value = "/getCheck.json", method = RequestMethod.GET)
	public @ResponseBody CheckResponse getCheckJSON(HttpServletRequest request, HttpServletResponse response) {
		String tableIdStr = request.getParameter("tableId");
		String custIdStr = request.getParameter("custId");
		Integer restaurantId = Integer.parseInt(request.getParameter("restaurantId"));
		return getCheckResponse(tableIdStr, custIdStr, restaurantId);
	}
	
	@RequestMapping(value = "/emailCheck", method = RequestMethod.GET)
	public @ResponseBody String emailCheck(HttpServletRequest request, HttpServletResponse response) {
		String tableIdStr = request.getParameter("tableId");
		String custIdStr = request.getParameter("custId");
		String checkIdStr = request.getParameter("checkId");
		String emailAddr = request.getParameter("email");
		Integer restaurantId = Integer.parseInt(request.getParameter("restaurantId"));
		CheckResponse checkResponse = null;
		
		if (StringUtility.isNullOrEmpty(checkIdStr)){
			checkResponse = getCheckResponse(tableIdStr, custIdStr, restaurantId);
		} else {
			checkResponse = getCheckResponseByCheckId(checkIdStr);
		}
		if (checkResponse != null) {
			User user = userService.getUser(restaurantId);
			
			StringBuilder strBuilder = new StringBuilder();
			strBuilder.append("<html><body>");
			if (user != null) {
				strBuilder.append("Restaurant Name : " + user.getBusinessName() + "<br />");
			}
			strBuilder.append("Check Id : " + checkResponse.getId() + "<br />");
			if (checkResponse.getTableId() != null) {
				strBuilder.append("Table Id : " + checkResponse.getTableId() + "<br />");
			}
			if (checkResponse.getCustomerId() != null) {
				strBuilder.append("Customer Id : " + checkResponse.getCustomerId() + "<br /><br /> ");
			}
			strBuilder.append("<b><i>Items</i></b> <br />");
			strBuilder.append("<table><tr><th>Name</th><th>Price</th></tr>");
			List<CheckDishResponse> dishes = checkResponse.getItems();
			for (CheckDishResponse dish : dishes) {
				strBuilder.append("<tr>");
				strBuilder.append("<td>" + dish.getName() + "</td>");
				strBuilder.append("<td>" + dish.getPrice() + "</td>");
				strBuilder.append("</tr>");
			}
			strBuilder.append("</table>");
			strBuilder.append("<br/>");
			strBuilder.append("<table>");
			
			strBuilder.append("<tr>");
			strBuilder.append("<td>");
			strBuilder.append("Total: ");
			strBuilder.append("</td>");
			strBuilder.append("<td>");
			strBuilder.append(checkResponse.getAmount());
			strBuilder.append("</td>");
			strBuilder.append("</tr>");
			if (checkResponse.getAdditionalCharge1() > 0) {
				strBuilder.append("<tr>");
				strBuilder.append("<td>");
				strBuilder.append(checkResponse.getAdditionalChargeName1());
				strBuilder.append("</td>");
				strBuilder.append("<td>");
				strBuilder.append(checkResponse.getAdditionalCharge1());
				strBuilder.append("</td>");
				strBuilder.append("</tr>");
			}
			if (checkResponse.getAdditionalCharge2() > 0) {
				strBuilder.append("<tr>");
				strBuilder.append("<td>");
				strBuilder.append(checkResponse.getAdditionalChargeName2());
				strBuilder.append("</td>");
				strBuilder.append("<td>");
				strBuilder.append(checkResponse.getAdditionalCharge2());
				strBuilder.append("</td>");
				strBuilder.append("</tr>");
			}
			if (checkResponse.getAdditionalCharge3() > 0) {
				strBuilder.append("<tr>");
				strBuilder.append("<td>");
				strBuilder.append(checkResponse.getAdditionalChargeName3());
				strBuilder.append("</td>");
				strBuilder.append("<td>");
				strBuilder.append(checkResponse.getAdditionalCharge3());
				strBuilder.append("</td>");
				strBuilder.append("</tr>");
			}
			strBuilder.append("<tr>");
			strBuilder.append("<td>");
			strBuilder.append("Grand Total: ");
			strBuilder.append("</td>");
			strBuilder.append("<td>");
			strBuilder.append(checkResponse.getTotal());
			strBuilder.append("</td>");
			strBuilder.append("</tr>");
			strBuilder.append("</table>");
			strBuilder.append("</body></html>");
			MailerUtility.sendHTMLMail(emailAddr, "Order Reciept", strBuilder.toString());
		} else {
			return "Error: No check found";
		}
		
		return "Email Sent Successfully";
	}
	
	@RequestMapping(value = "/allOpenChecks.json", method = RequestMethod.GET)
	public @ResponseBody List<Check> getAllOpenChecksJSON(HttpServletRequest request, HttpServletResponse response) {
		Integer restaurantId = Integer.parseInt(request.getParameter("restaurantId"));
		return checkService.getAllOpenChecks(restaurantId);
	}
	
	@RequestMapping(value = "/getReceipt", method = RequestMethod.GET)
	public String getReceipt(Map<String, Object> map, HttpServletRequest request) {
		String tableIdStr = request.getParameter("tableId");
		String custIdStr = request.getParameter("custId");
		Integer restaurantId = Integer.parseInt(request.getParameter("restaurantId"));
		CheckResponse checkResponse = getCheckResponse(tableIdStr, custIdStr, restaurantId);
		map.put("checkResp", checkResponse);
		return "receipt";
	}
	
	@RequestMapping(value = "/setOrderStatus")
	public @ResponseBody Order setOrderStatus(HttpServletRequest request, HttpServletResponse response) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		String statusStr = request.getParameter("status");
		statusStr = statusStr.toUpperCase();
		Status status = Status.valueOf(Status.class, statusStr);
		Order order = orderService.getOrder(orderId);
		order.setStatus(status);
		orderService.addOrder(order);
		return order;
	}
	
	@RequestMapping(value = "/setCheckStatus")
	public @ResponseBody Check setCheckStatus(HttpServletRequest request, HttpServletResponse response) {
		Integer checkId = Integer.parseInt(request.getParameter("checkId"));
		String statusStr = request.getParameter("status");
		com.cookedspecially.enums.check.Status status = com.cookedspecially.enums.check.Status.valueOf(com.cookedspecially.enums.check.Status.class, statusStr);
		Check check = checkService.getCheck(checkId);
		check.setStatus(status);
		checkService.addCheck(check);
		return check;
	}
	
	@RequestMapping(value = "/addToCheck.json", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody OrderResponse addToCheckJSON(@RequestBody JsonOrder order, Model model, HttpServletRequest request) {
		Check check = null;
		String error = "";
		Integer restaurantId = null;
		if (order.getCheckId() > 0) {
			check = checkService.getCheck(order.getCheckId());
		}
		SeatingTable table = null;
		if (check == null && order.getTableId() > 0) {
			table = seatingTableService.getSeatingTable(order.getTableId());
			if (table != null) {
				restaurantId = table.getRestaurantId();
				check = checkService.getCheckByTableId(table.getRestaurantId(), table.getId());
			}
		}
		if (check != null) {
			restaurantId = check.getRestaurantId();
		} else if (table == null) {
			error = "No table found";
		} else {
			check = new Check();
			check.setOpenTime(new Date());
			check.setRestaurantId(restaurantId);
			check.setStatus(com.cookedspecially.enums.check.Status.Unpaid);
			check.setTableId(table.getId());
			check.setUserId(restaurantId);
		}
		
		OrderResponse orderResp = new OrderResponse();
		if (StringUtility.isNullOrEmpty(error))
		{
			Order targetOrder = new Order();
			targetOrder.setUserId(check.getUserId());
			targetOrder.setRestaurantId(restaurantId);
			targetOrder.setCreatedTime(new Date());
			if (check.getTableId() > 0) {
				targetOrder.setSourceType(SourceType.TABLE);
				targetOrder.setSourceId(order.getTableId());
				targetOrder.setDestinationType(DestinationType.TABLE);
				targetOrder.setDestinationId(order.getTableId());	
			} else if (check.getCustomerId() > 0) {
				targetOrder.setSourceType(SourceType.COUNTER);
				targetOrder.setSourceId(order.getCustId());
				targetOrder.setDestinationType(DestinationType.COUNTER);
				targetOrder.setDestinationId(order.getCustId());
			}
			
			targetOrder.setStatus(Status.NEW);
			List<JsonDish> jsonDishes = order.getItems();
			Float bill = 0.0f;
			HashMap<Integer, OrderDish> orderDishMap = new HashMap<Integer, OrderDish>();
			for (JsonDish jsonDish  : jsonDishes) {
				if (orderDishMap.get(jsonDish.getId()) != null) {
					orderDishMap.get(jsonDish.getId()).addMore(jsonDish.getQuantity());
				} else {
					OrderDish orderDish = new OrderDish();
					orderDish.setDishId(jsonDish.getId());
					orderDish.setQuantity(jsonDish.getQuantity());
					orderDish.setName(jsonDish.getName());
					orderDish.setPrice(jsonDish.getPrice() * jsonDish.getQuantity());
					orderDishMap.put(orderDish.getDishId(), orderDish);
				}
				bill += (jsonDish.getPrice()*jsonDish.getQuantity());
			}
			targetOrder.setBill(bill);
			if (orderDishMap.size() > 0) {
				targetOrder.setOrderDishes(new ArrayList<OrderDish>(orderDishMap.values()));
			}
			//orderService.addOrder(targetOrder);
			
			if (check != null && check.getStatus() == com.cookedspecially.enums.check.Status.Unpaid) {
				List<Order> orders = check.getOrders();
				orders.add(targetOrder);
				Float checkBill = check.getBill() + targetOrder.getBill();
				check.setBill(checkBill);
				User user = userService.getUser(check.getRestaurantId());
				if (user != null) {
					check.setAdditionalChargesName1(user.getAdditionalChargesName1());
					check.setAdditionalChargesName2(user.getAdditionalChargesName2());
					check.setAdditionalChargesName3(user.getAdditionalChargesName3());
					check.setAdditionalChargesValue1(user.getAdditionalCharge(checkBill, user.getAdditionalChargesType1(), user.getAdditionalChargesValue1()));
					check.setAdditionalChargesValue2(user.getAdditionalCharge(checkBill, user.getAdditionalChargesType2(), user.getAdditionalChargesValue2()));
					check.setAdditionalChargesValue3(user.getAdditionalCharge(checkBill, user.getAdditionalChargesType3(), user.getAdditionalChargesValue3()));
				}
				check.setModifiedTime(new Date());
				
				checkService.addCheck(check);
			}
			
			orderResp.setOrderId(targetOrder.getOrderId());
			orderResp.setTableId(check.getTableId());
			orderResp.setRestaurantId(restaurantId);
			orderResp.setCheckId(check.getCheckId());
			orderResp.setStatus("Success");
		} else {
			orderResp.setStatus("Failed");
			orderResp.setError(error);
		}

		return orderResp;
	}
}
