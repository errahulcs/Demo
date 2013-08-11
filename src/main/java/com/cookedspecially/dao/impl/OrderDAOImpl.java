/**
 * 
 */
package com.cookedspecially.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cookedspecially.dao.OrderDAO;
import com.cookedspecially.domain.Dish;
import com.cookedspecially.domain.Order;

/**
 * @author shashank
 *
 */
@Repository
public class OrderDAOImpl implements OrderDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Order> listOrders(Map<String, Object> queryMap) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Order.class).add(Restrictions.allEq(queryMap));
		return criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
		
//		String hql = "from Order where";
//		int i = 0;
//		for (Entry<String, Object> entry : queryMap.entrySet()) {
//			if (i > 0) {
//				hql += " and ";
//			}
//			hql += " " + entry.getKey() + " = :" + entry.getKey();
//			i++;
//		}
//		Query query = sessionFactory.getCurrentSession().createQuery(hql);
//		for (Entry<String, Object> entry : queryMap.entrySet()) {
//			query.setParameter(entry.getKey(), entry.getValue());
//		}
//		return query.list();
	}
	
	@Override
	public void addOrder(Order order) {
		sessionFactory.getCurrentSession().saveOrUpdate(order);

	}

	@Override
	public void removeOrder(Integer id) throws Exception {
		Order order = (Order) sessionFactory.getCurrentSession().load(Order.class, id);
		if (order != null) {
			sessionFactory.getCurrentSession().delete(order);
		}
	}

	@Override
	public Order getOrder(Integer id) {
		return (Order) sessionFactory.getCurrentSession().get(Order.class, id);
	}

}
