/**
 * 
 */
package com.cookedspecially.dao;

import java.util.List;

import com.cookedspecially.domain.Menu;

/**
 * @author sagarwal
 *
 */
public interface MenuDAO {
	public void addMenu(Menu menu);
	public List<Menu> listMenu();
	public List<Menu> listMenuByUser(Integer userId);
	public void removeMenu(Integer id);
	public Menu getMenu(Integer id);
}
