/**
 * 
 */
package com.cookedspecially.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cookedspecially.dao.MenuDAO;
import com.cookedspecially.domain.Menu;
import com.cookedspecially.service.MenuService;

/**
 * @author sagarwal
 *
 */
@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDAO menuDAO;
	
	@Override
	@Transactional
	public void addMenu(Menu menu) {
		menuDAO.addMenu(menu);
	}


	@Override
	@Transactional
	public List<Menu> listMenu() {
		return menuDAO.listMenu();
	}


	@Override
	@Transactional
	public void removeMenu(Integer id) {
		menuDAO.removeMenu(id);
	}
	
	@Override
	@Transactional
	public Menu getMenu(Integer id) {
		return menuDAO.getMenu(id);
	}

}
