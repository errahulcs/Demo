/**
 * 
 */
package com.cookedspecially.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cookedspecially.dao.CustomerDAO;
import com.cookedspecially.domain.Customer;
import com.cookedspecially.domain.Dish;
import com.cookedspecially.utility.StringUtility;

/**
 * @author shashank
 *
 */
@Repository
public class CustomerDAOImpl implements CustomerDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addCustomer(Customer customer) {
		sessionFactory.getCurrentSession().saveOrUpdate(customer);

	}

	@Override
	public void removeCustomer(Integer id) throws Exception {
		Customer customer = (Customer) sessionFactory.getCurrentSession().load(Customer.class, id);
		if (null != customer) {
			sessionFactory.getCurrentSession().delete(customer);
		}
	}

	@Override
	public Customer getCustomer(Integer id) {
		return (Customer)sessionFactory.getCurrentSession().get(Customer.class, id);
	}
	
	@Override
	public List<Customer> getCustomerByParams(Integer custId, String email, String phone, Integer restaurantId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Customer.class);
		if (restaurantId != null && restaurantId > 0) {
			criteria = criteria.add(Restrictions.eq("restaurantId", restaurantId));
		}
		boolean disjunctionPresent = false;
		Disjunction disjunction = Restrictions.disjunction();
		if (custId != null && custId > 0) {
			disjunction.add(Restrictions.eq("customerId", custId));
			disjunctionPresent = true;
		}
//		if (restaurantId != null && restaurantId > 0) {
//			disjunction.add(Restrictions.eq("restaurantId", restaurantId));
//		}
		if (!StringUtility.isNullOrEmpty(email)) {
			disjunction.add(Restrictions.eq("email", email));
			disjunctionPresent = true;
		}
		if (!StringUtility.isNullOrEmpty(phone)) {
			disjunction.add(Restrictions.eq("phone", phone));
			disjunctionPresent = true;
		}
		if (disjunctionPresent) {
			criteria = criteria.add(disjunction);
		}
		return criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
	}

}
