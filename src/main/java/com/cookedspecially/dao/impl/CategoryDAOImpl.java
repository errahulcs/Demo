/**
 * 
 */
package com.cookedspecially.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cookedspecially.dao.CategoryDAO;
import com.cookedspecially.domain.Category;

/**
 * @author sagarwal
 *
 */
@Repository
public class CategoryDAOImpl implements CategoryDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void addCategory(Category category) {
		sessionFactory.getCurrentSession().save(category);

	}

	@Override
	public List<Category> listCategory() {
		return sessionFactory.getCurrentSession().createQuery("from Category").list();
	}

	@Override
	public void removeCategory(Integer id) {
		Category category = (Category) sessionFactory.getCurrentSession().load(Category.class, id);
		if (null != category) {
			sessionFactory.getCurrentSession().delete(category);
		}
	}

	@Override
	public Category getCategory(Integer id) {
		return (Category) sessionFactory.getCurrentSession().load(Category.class, id);

	}

}
