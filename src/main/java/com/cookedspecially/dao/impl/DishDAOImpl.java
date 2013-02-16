/**
 * 
 */
package com.cookedspecially.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
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
		sessionFactory.getCurrentSession().saveOrUpdate(dish);

	}

	@Override
	public List<Dish> listDish() {
		return sessionFactory.getCurrentSession().createQuery("from Dish").list();
	}

	@Override
	public List<Dish> listDishByUser(Integer userId) {
		return sessionFactory.getCurrentSession().createCriteria(Dish.class).add(Restrictions.eq("userId", userId)).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
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

	@Override
	public Dish getDish(Integer id) {
		return (Dish) sessionFactory.getCurrentSession().get(Dish.class, id);
	}
}
