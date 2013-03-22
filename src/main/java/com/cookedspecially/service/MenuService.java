/**
 * 
 */
package com.cookedspecially.service;

import java.util.List;

import com.cookedspecially.domain.Menu;
import com.cookedspecially.enums.Status;

/**
 * @author sagarwal
 *
 */
public interface MenuService {

	public void addMenu(Menu menu);
	public List<Menu> listMenu();
	public List<Menu> allMenusByStatus(Integer restaurantId, Status status);
	public List<Menu> listMenuByUser(Integer userId);
	public void removeMenu(Integer id);
	public Menu getMenu(Integer id);
}
