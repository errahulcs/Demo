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

import com.cookedspecially.dao.RestaurantDAO;
import com.cookedspecially.domain.Menu;
import com.cookedspecially.domain.Restaurant;

/**
 * @author shashank
 *
 */
@Repository
public class RestaurantDAOImpl implements RestaurantDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void addRestaurant(Restaurant restaurant) {
		sessionFactory.getCurrentSession().saveOrUpdate(restaurant);
	}

	@Override
	public List<Restaurant> listRestaurant() {
		return sessionFactory.getCurrentSession().createQuery("from Restaurant").list();
	}

	@Override
	public List<Restaurant> listRestaurantByUser(Integer userId) {
		return sessionFactory.getCurrentSession().createCriteria(Restaurant.class).add(Restrictions.eq("userId", userId)).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
	}

	@Override
	public void removeRestaurant(Integer id) {
		Restaurant restaurant = (Restaurant) sessionFactory.getCurrentSession().load(Restaurant.class, id);
		if (null != restaurant) {
			sessionFactory.getCurrentSession().delete(restaurant);
		}
	}

	@Override
	public Restaurant getRestaurant(Integer id) {
		return (Restaurant) sessionFactory.getCurrentSession().get(Restaurant.class, id);
	}

}
