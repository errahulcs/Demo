/**
 * 
 */
package com.cookedspecially.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cookedspecially.domain.Category;
import com.cookedspecially.domain.DeliveryArea;
import com.cookedspecially.domain.Dish;
import com.cookedspecially.domain.Restaurant;
import com.cookedspecially.domain.RestaurantInfo;
import com.cookedspecially.domain.User;
import com.cookedspecially.service.DeliveryAreaService;
import com.cookedspecially.service.RestaurantService;
import com.cookedspecially.service.UserService;
import com.cookedspecially.utility.StringUtility;

/**
 * @author shashank
 *
 */
@Component
@RequestMapping("/restaurant")
public class RestaurantController {

	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DeliveryAreaService deliveryAreaService;
	
	@RequestMapping("/")
	public String listRestaurants(Map<String, Object> map, HttpServletRequest request) {
		map.put("restaurant", new Restaurant());
		map.put("restaurantList", restaurantService.listRestaurantByUser((Integer) request.getSession().getAttribute("userId")));
		return "restaurant";
	}
	
	@RequestMapping("/edit/{restaurantId}")
	public String editRestaurant(Map<String, Object> map, HttpServletRequest request, @PathVariable("restaurantId") Integer restaurantId) {
		map.put("restaurant", restaurantService.getRestaurant(restaurantId));
		map.put("restaurantList", restaurantService.listRestaurantByUser((Integer) request.getSession().getAttribute("userId")));
		return "restaurant";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addRestaurant(@ModelAttribute("restaurant") Restaurant restaurant, BindingResult result) {
		
		restaurantService.addRestaurant(restaurant);
		return "redirect:/restaurant/";
	}

	@RequestMapping("/delete/{restaurantId}")
	public String deleteRestaurant(@PathVariable("restaurantId") Integer restaurantId) {
		restaurantService.removeRestaurant(restaurantId);
		return "redirect:/restaurant/";
	}

	@RequestMapping(value = "/resources/APK", method = RequestMethod.GET)
	public void getFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String restaurantName = request.getParameter("restaurantName");
		if (StringUtility.isNullOrEmpty(restaurantName)) {
			Integer userId = (Integer) request.getSession().getAttribute("userId");
			User user = userService.getUser(userId);
			if (user != null) {
				restaurantName = user.getBusinessName();
			}
		}
		restaurantName = restaurantName.replaceAll("[^a-zA-Z0-9_]", ""); 
		response.setContentType("application/force-download");
		File f = new File("webapps" + File.separator + "static" + File.separator + "clients" + File.separator + "com"  + File.separator + "cookedspecially" + File.separator + restaurantName + File.separator + restaurantName + ".apk");
		response.setContentLength(new Long(f.length()).intValue());
        response.setHeader("Content-Disposition", "attachment; filename=" + restaurantName + ".apk");
        FileCopyUtils.copy(new FileInputStream(f), response.getOutputStream());
	}
	
	@RequestMapping(value="/getrestaurantinfo", method=RequestMethod.GET)
	public @ResponseBody RestaurantInfo getReataurantInfo(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		String restaurantIdStr = request.getParameter("restaurantId");
		User user = null;
		if (!StringUtility.isNullOrEmpty(username)) {
			user = userService.getUserByUsername(username);
		} else if (!StringUtility.isNullOrEmpty(restaurantIdStr)) {
			user = userService.getUser(Integer.parseInt(restaurantIdStr));
		}
		return new RestaurantInfo(user);
	}
	
	@RequestMapping(value="/getDeliveryAreas", method=RequestMethod.GET)
	public @ResponseBody ArrayList<String> getDeliveryAreas(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		String restaurantIdStr = request.getParameter("restaurantId");
		User user = null;
		if (!StringUtility.isNullOrEmpty(username)) {
			user = userService.getUserByUsername(username);
		} else if (!StringUtility.isNullOrEmpty(restaurantIdStr)) {
			user = userService.getUser(Integer.parseInt(restaurantIdStr));
		}
		ArrayList<String> deliveryAreasList = new ArrayList<String>();
		if (user != null) {
			List<DeliveryArea> deliveryAreas = deliveryAreaService.listDeliveryAreasByUser(user.getUserId());
			for (DeliveryArea deliveryArea : deliveryAreas) {
				deliveryAreasList.add(deliveryArea.getName());
			}
		}
		deliveryAreasList.add(DeliveryAreaService.defaultDeliveryArea);
		return deliveryAreasList;
	}
	
	@RequestMapping("/deliveryAreas")
	public String listDeliveryAreas(Map<String, Object> map, HttpServletRequest request) {

		map.put("deliveryArea", new DeliveryArea());
		map.put("deliveryAreaList", deliveryAreaService.listDeliveryAreasByUser((Integer) request.getSession().getAttribute("userId")));
		return "deliveryArea";
	}

	@RequestMapping("/editDeliveryArea/{deliveryAreaId}")
	public String editDeliveryArea(Map<String, Object> map, HttpServletRequest request, @PathVariable("deliveryAreaId")
	Integer deliveryAreaId) {

		map.put("deliveryArea", deliveryAreaService.getDeliveryArea(deliveryAreaId));
		map.put("deliveryAreaList", deliveryAreaService.listDeliveryAreasByUser((Integer) request.getSession().getAttribute("userId")));
		return "deliveryArea";
	}
	
	@RequestMapping(value = "/addDeliveryArea", method = RequestMethod.POST)
	public String addDeliveryArea(@ModelAttribute("deliveryArea")
	DeliveryArea deliveryArea, BindingResult result) {
		deliveryAreaService.addDeliveryArea(deliveryArea);
		return "redirect:/restaurant/deliveryAreas";
	}

	@RequestMapping("/deleteDeliveryArea/{deliveryAreaId}")
	public String deleteDeliveryArea(@PathVariable("deliveryAreaId")
	Integer deliveryAreaId) {
		deliveryAreaService.removeDeliveryArea(deliveryAreaId);
		return "redirect:/restaurant/deliveryAreas/";
	}
	
	
}
