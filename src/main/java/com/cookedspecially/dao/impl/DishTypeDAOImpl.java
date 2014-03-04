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

import com.cookedspecially.dao.DishTypeDAO;
import com.cookedspecially.domain.Category;
import com.cookedspecially.domain.DishType;

/**
 * @author shashank
 *
 */
@Repository
public class DishTypeDAOImpl implements DishTypeDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void addDishType(DishType dishType) {
		sessionFactory.getCurrentSession().saveOrUpdate(dishType);
	}

	@Override
	public List<DishType> listDishTypes() {
		return sessionFactory.getCurrentSession().createCriteria(DishType.class).list();
	}

	@Override
	public List<DishType> listDishTypesByRestaurantId(Integer restaurantId) {
		return sessionFactory.getCurrentSession().createCriteria(DishType.class).add(Restrictions.eq("restaurantId", restaurantId)).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
	}

	@Override
	public void removeDishType(Integer id) {
		DishType dishType = (DishType) sessionFactory.getCurrentSession().load(DishType.class, id);
		if (null != dishType) {
			sessionFactory.getCurrentSession().delete(dishType);
		}
	}

	@Override
	public DishType getDishType(Integer id) {
		return (DishType)sessionFactory.getCurrentSession().get(DishType.class, id);
	}

}
