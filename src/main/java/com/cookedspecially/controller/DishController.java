/**
 * 
 */
package com.cookedspecially.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cookedspecially.domain.Dish;
import com.cookedspecially.service.CategoryService;
import com.cookedspecially.service.DishService;

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
	private CategoryService categoryService;
	
	@RequestMapping("/")
	public String listDishes(Map<String, Object> map, HttpServletRequest request) {

		map.put("dish", new Dish());
		map.put("dishList", dishService.listDishByUser((Integer) request.getSession().getAttribute("userId")));
		//map.put("categoryList", categoryService.listCategoryByUser((Integer) request.getSession().getAttribute("userId")));
		return "dish";
	}

	@RequestMapping("/edit/{dishId}")
	public String editDish(Map<String, Object> map, HttpServletRequest request, @PathVariable("dishId") Integer dishId) {

		map.put("dish", dishService.getDish(dishId));
		map.put("dishList", dishService.listDishByUser((Integer) request.getSession().getAttribute("userId")));
		//map.put("categoryList", categoryService.listCategoryByUser((Integer) request.getSession().getAttribute("userId")));
		return "dish";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addDish(@ModelAttribute("dish")
	Dish dish, BindingResult result, @RequestParam("file") MultipartFile file) {
		FileOutputStream fos = null;
		String fileUrl = dish.getImageUrl();
		if (!file.isEmpty()) {
            try {
				byte[] bytes = file.getBytes();
				//System.out.println(file.getOriginalFilename());
				//System.out.println(file.getContentType());
				String fileDir = File.separator + "static" + File.separator + dish.getUserId() + File.separator ;
				fileUrl = fileDir + dish.getDishId() + "_" + file.getOriginalFilename();
				File dir = new File("webapps" + fileDir);
				if (!dir.exists()) { 
					dir.mkdirs();
				}
				File outfile = new File("webapps" + fileUrl); 
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
            // store the bytes somewhere
           //return "uploadSuccess";
       } else {
           return "uploadFailure";
       }
		if (!fileUrl.equals(dish.getImageUrl()) && dish.getImageUrl().startsWith("/")) {
			File oldFile = new File("webapps" + dish.getImageUrl());
			if (oldFile.exists()) {
				oldFile.delete();
			}
		}

		dish.setImageUrl(fileUrl);
		dishService.addDish(dish);

		return "redirect:/dish/";
	}

	@RequestMapping("/delete/{dishId}")
	public String deleteDish(@PathVariable("dishId")
	Integer dishId) {

		dishService.removeDish(dishId);

		return "redirect:/dish/";
	}
	

}
