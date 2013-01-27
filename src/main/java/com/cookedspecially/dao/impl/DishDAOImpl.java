/**
 * 
 */
package com.cookedspecially.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cookedspecially.dao.DishDAO;
import com.cookedspecially.domain.Dish;

/**
 * @author sagarwal
 *
 */
@Repository
public class DishDAOImpl implements DishDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void addDish(Dish dish) {
		sessionFactory.getCurrentSession().save(dish);

	}

	@Override
	public List<Dish> listDish() {
		return sessionFactory.getCurrentSession().createQuery("from Dish").list();
	}

	@Override
	public void removeDish(Integer id) {
		Dish dish = (Dish) sessionFactory.getCurrentSession().load(Dish.class, id);
		if (null != dish) {
			sessionFactory.getCurrentSession().delete(dish);
		}

	}

	@Override
	public List<Dish> getDishes(Integer[] ids) {
		return (List<Dish>) sessionFactory.getCurrentSession().createCriteria(Dish.class).add(Restrictions.in("dishId", ids)).list();

	}

}
