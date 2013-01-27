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
	public void removeMenu(Integer id);
	public Menu getMenu(Integer id);
}
