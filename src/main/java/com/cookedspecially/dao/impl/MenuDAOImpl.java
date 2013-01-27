/**
 * 
 */
package com.cookedspecially.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cookedspecially.dao.MenuDAO;
import com.cookedspecially.domain.Menu;

/**
 * @author sagarwal
 *
 */
@Repository
public class MenuDAOImpl implements MenuDAO {


	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void addMenu(Menu menu) {
		sessionFactory.getCurrentSession().save(menu);

	}

	@Override
	public List<Menu> listMenu() {
		return sessionFactory.getCurrentSession().createQuery("from Menu").list();
	}

	@Override
	public void removeMenu(Integer id) {
		Menu menu = (Menu) sessionFactory.getCurrentSession().load(Menu.class, id);
		if (null != menu) {
			sessionFactory.getCurrentSession().delete(menu);
		}

	}

}
