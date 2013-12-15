/**
 * 
 */
package com.cookedspecially.controller;

import java.util.ArrayList;
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

import com.cookedspecially.domain.Check;
import com.cookedspecially.domain.Customer;
import com.cookedspecially.domain.Customers;
import com.cookedspecially.domain.JsonOrder;
import com.cookedspecially.service.CustomerService;
import com.cookedspecially.utility.StringUtility;

/**
 * @author shashank
 *
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@RequestMapping("/")
	public String index(Map<String, Object> map, HttpServletRequest request) {

		map.put("customer", new Customer());
		return "customer";
	}

	@RequestMapping("/addCustomer")
	public String addCustomerJSONPage(Map<String, Object> map, HttpServletRequest request) {
		return "addCustomer";
	}

	@RequestMapping("/edit/{customerId}")
	public String editCustomer(Map<String, Object> map, HttpServletRequest request, @PathVariable("customerId") Integer customerId) {

		map.put("customer", customerService.getCustomer(customerId));
		return "customer";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addCustomer(Map<String, Object> map, @ModelAttribute("customer") Customer customer) {
		customer.setRestaurantId(customer.getUserId());
		customerService.addCustomer(customer);
		return "redirect:/customer/";
	}

	@RequestMapping(value = "/setCustomerInfo.json", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody String setCustomerInfoJSON(@RequestBody Customer customer, Model model, HttpServletRequest request) {
		if (customer != null) {
			//customer.setUserId(customer.getRestaurantId());
			customerService.addCustomer(customer);
			
			return "Successfully set customer";
		}
		return "Improper customer info supplied";
	}
	
	@RequestMapping("/delete/{customerId}")
	public String deleteCustomer(Map<String, Object> map, HttpServletRequest request, @PathVariable("customerId") Integer customerId) {

		try {
			customerService.removeCustomer(customerId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return index(map, request);
	}

	@RequestMapping(value = "/getCustomerInfo.json", method = RequestMethod.GET)
	public @ResponseBody Customers getCustomerInfoJSON(HttpServletRequest request, HttpServletResponse response) {
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String custIdStr = request.getParameter("custId");
		Integer custId = -1;
		if (!StringUtility.isNullOrEmpty(custIdStr)) {
			custId = Integer.parseInt(custIdStr);
		}
		List<Customer> customerList = customerService.getCustomerByParams(custId, email, phone);
		Customers customers = new Customers();
		if (customerList != null && customerList.size() < 1) {
			customerList = new ArrayList<Customer>();
			Customer customer = new Customer();
			customer.setPhone(phone);
			customer.setEmail(email);
			customerService.addCustomer(customer);
			customerList.add(customer);
			customers.setExactMatch(false);
			customers.setNewCustomer(true);
		} else {
			customers.setNewCustomer(false);
			Customer exactCustomer = null;
			for (Customer customer : customerList) {
				boolean exactMatch = false;
				if (custId > 0) {
					if (custId != customer.getCustomerId()) {
						continue;
					}
					exactMatch = true;
				}
				if (!StringUtility.isNullOrEmpty(phone)) {
					if (!phone.equals(customer.getPhone())) {
						continue;
					}
					exactMatch = true;
				}
				if (!StringUtility.isNullOrEmpty(email)) {
					if (!email.equals(customer.getEmail())) {
						continue;
					}
					exactMatch = true;
				}
				if (exactMatch) {
					exactCustomer = customer;
					break;
				}
			}
			if (exactCustomer != null) {
				customerList = new ArrayList<Customer>();
				customerList.add(exactCustomer);
				customers.setExactMatch(true);
			} else {
				customers.setExactMatch(false);
			}
		}
		
		customers.setCustomers(customerList);
		return customers;
	}
}
