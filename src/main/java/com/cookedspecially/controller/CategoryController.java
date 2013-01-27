/**
 * 
 */
package com.cookedspecially.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cookedspecially.domain.Category;
import com.cookedspecially.service.CategoryService;

/**
 * @author sagarwal
 *
 */
@Controller
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	
	@RequestMapping("/")
	public String listContacts(Map<String, Object> map) {

		map.put("category", new Category());
		map.put("categoryList", categoryService.listCategory());
		return "category";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addContact(@ModelAttribute("category")
	Category category, BindingResult result) {
		categoryService.addCategory(category);

		return "redirect:/category/";
	}

	@RequestMapping("/delete/{categoryId}")
	public String deleteContact(@PathVariable("categoryId")
	Integer categoryId) {

		categoryService.removeCategory(categoryId);

		return "redirect:/category/";
	}	
}
