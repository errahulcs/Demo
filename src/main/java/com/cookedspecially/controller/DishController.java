/**
 * 
 */
package com.cookedspecially.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.cookedspecially.domain.Dish;
import com.cookedspecially.domain.User;
import com.cookedspecially.enums.WeekDayFlags;
import com.cookedspecially.service.DishService;
import com.cookedspecially.service.UserService;
import com.cookedspecially.utility.ImageUtility;
import com.cookedspecially.utility.StringUtility;

/**
 * @author sagarwal
 *
 */
@Controller
@RequestMapping("/dish")
public class DishController {

	@Autowired
	private DishService dishService;
	
	@Autowired
	private UserService userService;
	
	private static int MAXFILESIZE=5; //in MB
	//@Autowired
	//private CategoryService categoryService;
	
	private static int HOURS[] = new int[24];
	private static int MINS[] = new int[4];
	private static boolean ALLWEEKDAYS[] = new boolean[7];
	static {
		for (int i = 0; i < 24; i++) {
			HOURS[i] = i;
		}
		for (int i = 0; i < 4; i++) {
			MINS[i] = i*15;
		}
		for (int i = 0; i < 7; i++) {
			ALLWEEKDAYS[i] = true;
		}
	}
	
	@RequestMapping("/")
	public String listDishes(Map<String, Object> map, HttpServletRequest request) {

		Dish dish = new Dish();
		//dish.setActiveDays(WeekDayFlags.getWeekDayVal(ALLWEEKDAYS));
		//dish.setHappyHourDays(WeekDayFlags.getWeekDayVal(ALLWEEKDAYS));
		dish.setDishActiveDays(ALLWEEKDAYS);
		dish.setHappyHourActiveDays(ALLWEEKDAYS);
		map.put("dish", dish);
		map.put("dishList", dishService.listDishByUser((Integer) request.getSession().getAttribute("userId")));
		map.put("hours", HOURS);
		map.put("mins", MINS);
		map.put("currTime", new Date().toLocaleString());
		map.put("weekdayFlags", WeekDayFlags.values());
		//map.put("categoryList", categoryService.listCategoryByUser((Integer) request.getSession().getAttribute("userId")));
		return "dish";
	}

	@RequestMapping("/edit/{dishId}")
	public String editDish(Map<String, Object> map, HttpServletRequest request, @PathVariable("dishId") Integer dishId) {
		Dish dish = dishService.getDish(dishId);
		dish.setDishActiveDays(WeekDayFlags.getWeekDayFlagsArr(dish.getActiveDays()));
		dish.setHappyHourActiveDays(WeekDayFlags.getWeekDayFlagsArr(dish.getHappyHourDays()));
		map.put("dish", dish);
		map.put("dishList", dishService.listDishByUser((Integer) request.getSession().getAttribute("userId")));
		map.put("hours", HOURS);
		map.put("mins", MINS);
		map.put("weekdayFlags", WeekDayFlags.values());
		map.put("currTime", new Date().toLocaleString());
		//map.put("categoryList", categoryService.listCategoryByUser((Integer) request.getSession().getAttribute("userId")));
		return "dish";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addDish(Map<String, Object> map, @ModelAttribute("dish")
	Dish dish, BindingResult result, @RequestParam("file") MultipartFile file) {
		FileOutputStream fos = null;
		String fileUrl = dish.getImageUrl();
		String outFileUrl = null;
		if (!file.isEmpty()) {
			if (file.getSize() > MAXFILESIZE*1000*1000) {
				result.rejectValue("imageUrl", "error.upload.sizeExceeded", "You cannot upload the file of more than " + MAXFILESIZE + " MB");
				map.put("dish", dish);
				map.put("dishList", dishService.listDishByUser(dish.getUserId()));
				return "dish";
			}
            try {
				byte[] bytes = file.getBytes();
				
				//System.out.println(file.getOriginalFilename());
				//System.out.println(file.getContentType());
				String fileDir = File.separator + "static" + File.separator + dish.getUserId() + File.separator ;
				fileUrl = fileDir + dish.getDishId() + "_" + file.getOriginalFilename().replaceAll("[^a-zA-Z0-9_.]", "_");
				File dir = new File("webapps" + fileDir);
				if (!dir.exists()) { 
					dir.mkdirs();
				}
				outFileUrl = "webapps" + fileUrl;
				File outfile = new File(outFileUrl); 
				//System.out.println(outfile.getAbsolutePath());
				fos = new FileOutputStream(outfile);
				fos.write(bytes);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
            if (!StringUtility.isNullOrEmpty(outFileUrl)) {
            	ImageUtility.resizeImage(outFileUrl, ImageUtility.getSmallImageUrl(outFileUrl, 200, 200), "jpg", 200, 200);
            }
			
            // store the bytes somewhere
           //return "uploadSuccess";
       } else {
           //return "uploadFailure";
       }
		if (!fileUrl.equals(dish.getImageUrl()) && dish.getImageUrl().startsWith("/")) {
			File oldFile = new File("webapps" + dish.getImageUrl());
			if (oldFile.exists()) {
				oldFile.delete();
			}
			File oldSmallFile = new File("webapps" + ImageUtility.getSmallImageUrl(dish.getImageUrl(), 200, 200) );
			if (oldSmallFile.exists()) {
				oldSmallFile.delete();
			}
		}
		
		dish.setImageUrl(fileUrl);
		Integer dishId = dish.getDishId();  
		dish.setActiveDays(WeekDayFlags.getWeekDayVal(dish.getDishActiveDays()));
		dish.setHappyHourDays(WeekDayFlags.getWeekDayVal(dish.getHappyHourActiveDays()));
		dishService.addDish(dish);
		if (dishId != null && dishId > 0) {
			dishService.updateMenuModificationTime(dishId);
		} else {
			if (fileUrl.contains("null_")) {
				String newFileUrl = renameFileToHaveDishId(fileUrl, dish.getDishId());
				dish.setImageUrl(newFileUrl);
				dishService.addDish(dish);
				String smallFileOldUrl = ImageUtility.getSmallImageUrl(fileUrl, 200, 200);
				renameFileToHaveDishId(smallFileOldUrl, dish.getDishId());
			}
		}
		return "redirect:/dish/";
	}


	@RequestMapping("/delete/{dishId}")
	public String deleteDish(Map<String, Object> map, HttpServletRequest request, @PathVariable("dishId") Integer dishId) {

		try {
			Dish dish = dishService.getDish(dishId);
			if (dish != null) {
				String dishImageUrl = dish.getImageUrl();
				dishService.removeDish(dishId);
				if (!StringUtility.isNullOrEmpty(dishImageUrl) && dishImageUrl.startsWith("/")) {
					File image = new File("webapps" + dishImageUrl);
					if (image.exists()) {
						image.delete();
					}
					File smallImage = new File("webapps" + ImageUtility.getSmallImageUrl(dishImageUrl, 200, 200));
					if (smallImage.exists()) {
						smallImage.delete();
					}
				}
			}
		} catch (DataIntegrityViolationException exp) {
			map.put("errorMsg", "Sorry, this dish is associated with some id and could not be deleted");
		} catch (Exception e) {
			map.put("errorMsg", "Sorry, something went wrong and we could not delete this dish.");
		}
		return listDishes(map, request);
	}
	
	@RequestMapping("/resizeDishes/{userId}")
	public String resizeDishes(Map<String, Object> map, HttpServletRequest request, @PathVariable("userId") Integer userId) {
		Integer adminUserId = (Integer) request.getSession().getAttribute("userId");
		User adminUser = userService.getUser(adminUserId);
		if ("shashankagarwal1706@gmail.com".equals(adminUser.getUsername())) {

			List<Dish> dishes = dishService.listDishByUser(userId);
			for (Dish dish : dishes) {
				String dishImageUrl = dish.getImageUrl();
				if (!StringUtility.isNullOrEmpty(dishImageUrl)) {
					String localUrl = "webapps" + dishImageUrl;
					File dishImage = new File(localUrl);
					if (dishImage.exists()) {
						ImageUtility.resizeImage(localUrl, ImageUtility.getSmallImageUrl(localUrl, 200, 200), "jpg", 200, 200);
					}
				}
			}
		}
		return "redirect:/dish/";
	}
	
	private String renameFileToHaveDishId(String fileUrl, Integer dishId) {
		File oldFile = new File("webapps" + fileUrl);
		String newFileUrl = fileUrl.replace("null_", dishId + "_");
		File newFile = new File("webapps" + newFileUrl);
		oldFile.renameTo(newFile);
		return newFileUrl;
	}
}
