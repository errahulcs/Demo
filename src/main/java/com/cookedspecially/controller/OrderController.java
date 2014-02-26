/**
 * 
 */
package com.cookedspecially.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring3.SpringTemplateEngine;

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
import com.cookedspecially.enums.check.CheckType;
import com.cookedspecially.enums.check.PaymentMode;
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

	@Autowired
	private JavaMailSenderImpl mailSender;
	
	@Autowired
	private SpringTemplateEngine templateEngine;
	
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
				orderDish.setDishType(StringUtility.isNullOrEmpty(jsonDish.getDishType())?"OTHERS":jsonDish.getDishType());
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

	@RequestMapping("/deleteCheck/{checkId}")
	public @ResponseBody String deleteCheck(Map<String, Object> map, HttpServletRequest request, @PathVariable("checkId") Integer checkId) {
		try {
			checkService.removeCheck(checkId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Success: Deleted the Check with id:" +checkId;
	}

	@RequestMapping(value = "/searchChecks")
	public String searchChecks(Map<String, Object> map, HttpServletRequest request) {
		String checkIdString = request.getParameter("checkId");
		String invoiceId = request.getParameter("invoiceId");
		if (!StringUtility.isNullOrEmpty(checkIdString)) {
			Integer checkId  = Integer.parseInt(checkIdString);
			try {
				Check check = checkService.getCheck(checkId);
				map.put("checkList", Arrays.asList(new CheckResponse(check)));
			} catch (Exception e) {
				e.printStackTrace();
			}	
		} else if (!StringUtility.isNullOrEmpty(invoiceId)) {
			try {
				List<Check> checks = checkService.getCheckByInvoiceId(invoiceId);
				List<CheckResponse> checkResponses = new ArrayList<CheckResponse>();
				for (Check check : checks) {
					checkResponses.add(new CheckResponse(check));
				}
				map.put("checkList", checkResponses);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return "searchChecks";
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
			// Need to add logic to decide on what checktype to set.
			if (tableId > 0) {
				check.setCheckType(CheckType.Table);
			} else /*if ( custId > 0 )*/ { 
				// Marking all others as TakeAway for now.
				check.setCheckType(CheckType.TakeAway);
			}
			
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
						if (order.getStatus() != Status.CANCELLED) {
							checkBill += order.getBill();
						}
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
	
	@RequestMapping(value = "/generateCheckForPrint", method = RequestMethod.GET)
	public String generateCheckForPrint(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) {
		String billTemplateName = request.getParameter("templateName");
		Integer checkId = Integer.parseInt(request.getParameter("checkId"));
		Check check = checkService.getCheck(checkId);
		if (check != null) {
			CheckResponse checkResponse = new CheckResponse(check);
			map.put("checkRespone", checkResponse);
			if (check.getCustomerId() > 0) {
				Customer customer = customerService.getCustomer(check.getCustomerId());
				map.put("customer", customer);
			} else if (check.getTableId() > 0) {
				map.put("tableId", check.getTableId());
			}
			Map<String, JsonDish> itemsMap = new TreeMap<String, JsonDish>();
			List<CheckDishResponse> items = checkResponse.getItems();
			for (CheckDishResponse item : items) {
				if (itemsMap.containsKey(item.getName())) {
					JsonDish jsonDish = itemsMap.get(item.getName());
					jsonDish.setPrice(jsonDish.getPrice() + item.getPrice());
					jsonDish.setQuantity(jsonDish.getQuantity() + 1);
				} else {
					JsonDish jsonDish = new JsonDish();
					jsonDish.setQuantity(1);
					jsonDish.setName(item.getName());
					jsonDish.setPrice(item.getPrice());
					itemsMap.put(item.getName(), jsonDish);
				}
			}
			map.put("itemsMap", itemsMap);
		}
		
		return "custom/" + billTemplateName;
	}
	
	@RequestMapping(value = "/emailNewCheck", method = RequestMethod.GET)
	public @ResponseBody String emailNewCheck(HttpServletRequest request, HttpServletResponse response) throws MessagingException {
		String emailAddr = request.getParameter("email");
	    // Prepare the evaluation context
	    final Context ctx = new Context(request.getLocale());
	    ctx.setVariable("name", "Shashank");
	    ctx.setVariable("subscriptionDate", new Date());
	    ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));
	    //ctx.setVariable("imageResourceName", imageResourceName); // so that we can reference it from HTML
	 
	    // Prepare message using a Spring helper
	    final MimeMessage mimeMessage = mailSender.createMimeMessage();
	    final MimeMessageHelper message =
	        new MimeMessageHelper(mimeMessage, false, "UTF-8"); // true = multipart

	    message.setSubject("Example HTML email with inline image");
	    message.setFrom("thymeleaf@example.com");
	    message.setTo(emailAddr);
	 
	    // Create the HTML body using Thymeleaf
	    final String htmlContent = templateEngine.process("email-inlineimage", ctx);
	    message.setText(htmlContent, true); // true = isHtml
	 
	    // Add the inline image, referenced from the HTML code as "cid:${imageResourceName}"
	    //final InputStreamSource imageSource = new ByteArrayResource(imageBytes);
	    //message.addInline(imageResourceName, imageSource, imageContentType);
	 
	    // Send mail
	    mailSender.send(mimeMessage);

		return "sucess";
	}

	@RequestMapping(value = "/emailTemplatedCheck", method = RequestMethod.GET)
	public @ResponseBody String emailTemplatedCheck(HttpServletRequest request, HttpServletResponse response) throws MessagingException {
		String emailAddr = request.getParameter("email");
		String templateName =request.getParameter("templateName");
		Integer checkId = Integer.parseInt(request.getParameter("checkId"));
		
		// Prepare the evaluation context
	    final Context ctx = new Context(request.getLocale());
	    
		Check check = checkService.getCheck(checkId);
		if (check != null) {
			CheckResponse checkResponse = new CheckResponse(check);
			ctx.setVariable("checkRespone", checkResponse);
			if (check.getCustomerId() > 0) {
				Customer customer = customerService.getCustomer(check.getCustomerId());
				ctx.setVariable("customer", customer);
			} else if (check.getTableId() > 0) {
				ctx.setVariable("tableId", check.getTableId());
			}
			
			Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
			cal.setTime(check.getOpenTime());
			DateFormat formatter1;
			formatter1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			formatter1.setTimeZone(cal.getTimeZone());
			ctx.setVariable("checkDate", formatter1.format(cal.getTime()));
			Map<String, JsonDish> itemsMap = new TreeMap<String, JsonDish>();
			List<CheckDishResponse> items = checkResponse.getItems();
			for (CheckDishResponse item : items) {
				if (itemsMap.containsKey(item.getName())) {
					JsonDish jsonDish = itemsMap.get(item.getName());
					jsonDish.setPrice(jsonDish.getPrice() + item.getPrice());
					jsonDish.setQuantity(jsonDish.getQuantity() + 1);
				} else {
					JsonDish jsonDish = new JsonDish();
					jsonDish.setQuantity(1);
					jsonDish.setName(item.getName());
					jsonDish.setPrice(item.getPrice());
					itemsMap.put(item.getName(), jsonDish);
				}
			}
			
			ctx.setVariable("itemsMap", itemsMap);
		} else {
			return "No check found";
		}
		
	    // Prepare message using a Spring helper
	    final MimeMessage mimeMessage = mailSender.createMimeMessage();
	    final MimeMessageHelper message =
	        new MimeMessageHelper(mimeMessage, false, "UTF-8"); // true = multipart

	    message.setSubject("Your Receipt");
	    message.setFrom("thymeleaf@example.com");
	    message.setTo(emailAddr);
	 
	    // Create the HTML body using Thymeleaf
	    final String htmlContent = templateEngine.process(templateName, ctx);
	    message.setText(htmlContent, true); // true = isHtml
	 	 
	    // Send mail
	    mailSender.send(mimeMessage);

	    return "Email Sent Successfully";
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
			//strBuilder.append("Check Id : " + checkResponse.getId() + "<br />");
//			if (checkResponse.getTableId() != null && checkResponse.getTableId() > 0) {
//				strBuilder.append("Table Id : " + checkResponse.getTableId() + "<br />");
//			}
//			if (checkResponse.getCustomerId() != null) {
//				strBuilder.append("Customer Id : " + checkResponse.getCustomerId() + "<br /><br /> ");
//			}
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
	
	@RequestMapping(value = "/allChecksWithOpenOrders.json", method = RequestMethod.GET)
	public @ResponseBody List<Check> getAllChecksWithOpenOrdersJSON(HttpServletRequest request, HttpServletResponse response) {
		Integer restaurantId = Integer.parseInt(request.getParameter("restaurantId"));
		List<Integer> checkIds = orderService.getAllOpenOrderCheckIds(restaurantId);
		return checkService.getAllChecks(checkIds);
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
		if (status == Status.CANCELLED) {
			updateCheckForCancelOrder(order);
		}
		order.setStatus(status);
		order.setModifiedTime(new Date());
		orderService.addOrder(order);
		return order;
	}
	
	//Admin Function
	@RequestMapping(value = "/syncChecksAndOrders")
	public @ResponseBody String syncChecksAndOrders(HttpServletRequest request, HttpServletResponse response) {
		List<Integer> checkIds = checkService.getAllCheckIds();
		for (Integer checkId: checkIds) {
			Check check = checkService.getCheck(checkId);
			for (Order order : check.getOrders()) {
				order.setCheckId(checkId);
				orderService.addOrder(order);
			}
		}
		return "Succesfully updated all orders with checkId";
	}
	
	private void updateCheckForCancelOrder(Order order) {
		// Fetch the check from order
		// Update check with calculations of removal of bill values etc.
		User user = userService.getUser(order.getRestaurantId());
		Check check = checkService.getCheck(order.getCheckId());
		float checkBill = check.getBill() - order.getBill();
		check.setBill(checkBill);
		check.setAdditionalChargesValue1(user.getAdditionalCharge(checkBill, user.getAdditionalChargesType1(), user.getAdditionalChargesValue1()));
		check.setAdditionalChargesValue2(user.getAdditionalCharge(checkBill, user.getAdditionalChargesType2(), user.getAdditionalChargesValue2()));
		check.setAdditionalChargesValue3(user.getAdditionalCharge(checkBill, user.getAdditionalChargesType3(), user.getAdditionalChargesValue3()));
		checkService.addCheck(check);
	}
	
	@RequestMapping(value = "/setCheckStatus")
	public @ResponseBody Check setCheckStatus(HttpServletRequest request, HttpServletResponse response) {
		Integer checkId = Integer.parseInt(request.getParameter("checkId"));
		String statusStr = request.getParameter("status");
		com.cookedspecially.enums.check.Status status = com.cookedspecially.enums.check.Status.valueOf(com.cookedspecially.enums.check.Status.class, statusStr);
		Check check = checkService.getCheck(checkId);
		if (status == com.cookedspecially.enums.check.Status.Cancel) {
			// cancel all orders within
			List<Order> orders = check.getOrders();
			for (Order order: orders) {
				order.setStatus(Status.CANCELLED);
			}
		} 
		check.setStatus(status);
		check.setModifiedTime(new Date());
		checkService.addCheck(check);
		return check;
	}

	@RequestMapping(value = "/markAllOrdersAsPaid")
	public @ResponseBody Check markAllOrdersAsPaid(HttpServletRequest request, HttpServletResponse response) {
		Integer checkId = Integer.parseInt(request.getParameter("checkId"));
		Check check = checkService.getCheck(checkId);
		if (check != null) {
			// cancel all orders within
			List<Order> orders = check.getOrders();
			for (Order order: orders) {
				order.setStatus(Status.PAID);
			}
			checkService.addCheck(check);
		} 
		return check;
	}
	
	@RequestMapping(value = "/setCheckType")
	public @ResponseBody Check setCheckType(HttpServletRequest request, HttpServletResponse response) {
		Integer checkId = Integer.parseInt(request.getParameter("checkId"));
		String typeStr = request.getParameter("type");
		CheckType checkType = CheckType.valueOf(CheckType.class, typeStr);
		Check check = checkService.getCheck(checkId);
		check.setCheckType(checkType);
		check.setModifiedTime(new Date());
		checkService.addCheck(check);
		return check;
	}
	@RequestMapping(value = "/setCheckPaymentType")
	public @ResponseBody Check setCheckPaymentType(HttpServletRequest request, HttpServletResponse response) {
		Integer checkId = Integer.parseInt(request.getParameter("checkId"));
		String paymentModeStr = request.getParameter("type");
		PaymentMode payment = PaymentMode.valueOf(PaymentMode.class, paymentModeStr);
		Check check = checkService.getCheck(checkId);
		check.setPayment(payment);
		check.setModifiedTime(new Date());
		checkService.addCheck(check);
		return check;
	}
	
	@RequestMapping(value = "/setDeliveryTime")
	public @ResponseBody String setCheckDeliveryTime(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		Integer checkId  = Integer.parseInt(request.getParameter("checkId"));
		String deliveryTimeStr = request.getParameter("deliveryTime");
		Check check = checkService.getCheck(checkId);
		if (check != null) {
			if (StringUtility.isNullOrEmpty(deliveryTimeStr)) {
				return "wrong delivery time";
			}
			
			Date deliveryTime = DateUtils.parseDate(deliveryTimeStr, "yyyy-MM-dd HH:mm");
			check.setDeliveryTime(deliveryTime);
			checkService.addCheck(check);
			return "recieved";
		}
		return "check not found";
	}

	@RequestMapping(value = "/setDeliveryArea")
	public @ResponseBody String setCheckDeliveryArea(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		Integer checkId  = Integer.parseInt(request.getParameter("checkId"));
		String deliveryArea = request.getParameter("deliveryArea");
		Check check = checkService.getCheck(checkId);
		if (check != null) {
			check.setDeliveryArea(deliveryArea);
			checkService.addCheck(check);
			return "recieved";
		}
		return "check not found";
	}
	
	@RequestMapping(value = "/setDeliveryAddress")
	public @ResponseBody String setCheckDeliveryAddress(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		Integer checkId  = Integer.parseInt(request.getParameter("checkId"));
		String deliveryAddress = request.getParameter("deliveryAddress");
		Check check = checkService.getCheck(checkId);
		if (check != null) {
			check.setDeliveryAddress(deliveryAddress);
			checkService.addCheck(check);
			return "recieved";
		}
		return "check not found";
	}
	
	@RequestMapping(value = "/setDeliveryDetails")
	public @ResponseBody String setCheckDeliveryDetails(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		Integer checkId  = Integer.parseInt(request.getParameter("checkId"));
		String deliveryAddress = request.getParameter("deliveryAddress");
		String deliveryArea = request.getParameter("deliveryArea");
		String deliveryTimeStr = request.getParameter("deliveryTime");
		
		Check check = checkService.getCheck(checkId);
		if (check != null) {
			check.setDeliveryAddress(deliveryAddress);
			check.setDeliveryArea(deliveryArea);
			if (!StringUtility.isNullOrEmpty(deliveryTimeStr)) {
				Date deliveryTime = DateUtils.parseDate(deliveryTimeStr, "yyyy-MM-dd HH:mm");
				check.setDeliveryTime(deliveryTime);
			}
			checkService.addCheck(check);
			return "recieved";
		}
		return "check not found";
	}
	
	@RequestMapping(value = "/addToCheck.json", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody OrderResponse addToCheckJSON(@RequestBody JsonOrder order, Model model, HttpServletRequest request) {
		Check check = null;
		String error = "";
		Integer restaurantId = null;
		boolean bFirstOrder = false;
		if (order.getCheckId() > 0) {
			check = checkService.getCheck(order.getCheckId());
		}
		SeatingTable table = null;
		Customer customer = null;
		if (check == null) {
			if (order.getTableId() > 0) {
				table = seatingTableService.getSeatingTable(order.getTableId());
				if (table != null) {
					restaurantId = table.getRestaurantId();
					check = checkService.getCheckByTableId(table.getRestaurantId(), table.getId());
				}
			} else if (order.getCustId() > 0) {
				customer = customerService.getCustomer(order.getCustId());
				if (customer != null) {
					restaurantId = customer.getRestaurantId();
					check = checkService.getCheckByCustId(restaurantId, customer.getCustomerId());
				}
			}
		}
		if (check != null) {
			restaurantId = check.getRestaurantId();
		} /*else if (table == null) {
			error = "No table found";
		}*/ else {
			if (order.getTableId() > 0 && table == null) {
				error = "No table found";
			} else if (order.getCustId() > 0 && customer == null) {
				error = "No customer was found";
			} else {
				check = new Check();
				bFirstOrder = true;
				check.setOpenTime(new Date());
				check.setRestaurantId(restaurantId);
				check.setStatus(com.cookedspecially.enums.check.Status.Unpaid);
				if (table != null) {
					check.setTableId(table.getId());
					check.setCheckType(CheckType.Table);
				} else if (customer != null) {
					check.setCustomerId(customer.getCustomerId());
					check.setCheckType(CheckType.TakeAway);
				}
				check.setUserId(restaurantId);
				// check's delivery area will be set only when first order is being placed and is for non-table orders
				// as non table orders can either be delivery.
				check.setDeliveryArea((order.getDeliveryArea()!= null && table == null)?order.getDeliveryArea():"");
				check.setDeliveryAddress((order.getDeliveryAddress()!= null && table == null)?order.getDeliveryAddress():"");
			}
		}
		
		OrderResponse orderResp = new OrderResponse();
		if (StringUtility.isNullOrEmpty(error))
		{
			Order targetOrder = new Order();
			targetOrder.setUserId(check.getUserId());
			targetOrder.setRestaurantId(restaurantId);
			targetOrder.setCheckId(check.getCheckId());
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
					orderDish.setPrice(jsonDish.getPrice());
					orderDish.setDishType(StringUtility.isNullOrEmpty(jsonDish.getDishType())?"OTHERS":jsonDish.getDishType());
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
				if (bFirstOrder) {
					Order orderToUpdate = check.getOrders().get(0);
					orderToUpdate.setCheckId(check.getCheckId());
					orderService.addOrder(orderToUpdate);
				}
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
