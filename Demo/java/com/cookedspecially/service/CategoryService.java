/**
 * 
 */
package com.cookedspecially.service;

import java.util.List;

import com.cookedspecially.domain.Category;

/**
 * @author sagarwal
 *
 */
public interface CategoryService {

	public void addCategory(Category category);
	public List<Category> listCategory();
	public List<Category> listCategoryByUser(Integer userId);
	public void removeCategory(Integer id);
	public Category getCategory(Integer id);
}
