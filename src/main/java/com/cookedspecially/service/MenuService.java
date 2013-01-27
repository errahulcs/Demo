/**
 * 
 */
package com.cookedspecially.service;

import java.util.List;

import com.cookedspecially.domain.Menu;

/**
 * @author sagarwal
 *
 */
public interface MenuService {

	public void addMenu(Menu menu);
	public List<Menu> listMenu();
	public void removeMenu(Integer id);

}
