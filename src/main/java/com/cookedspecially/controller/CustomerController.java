/**
 * 
 */
package com.cookedspecially.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cookedspecially.domain.Customer;
import com.cookedspecially.service.CustomerService;

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

	@RequestMapping("/delete/{customerId}")
	public String deleteCustomer(Map<String, Object> map, HttpServletRequest request, @PathVariable("customerId") Integer customerId) {

		try {
			customerService.removeCustomer(customerId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return index(map, request);
	}

	
}
