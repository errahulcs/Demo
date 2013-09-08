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

import com.cookedspecially.dao.CheckDAO;
import com.cookedspecially.domain.Check;
import com.cookedspecially.domain.Menu;
import com.cookedspecially.enums.check.Status;

/**
 * @author shashank
 *
 */
@Repository
public class CheckDAOImpl implements CheckDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void addCheck(Check check) {
		sessionFactory.getCurrentSession().saveOrUpdate(check);
	}

	@Override
	public void removeCheck(Integer id) {
		Check check = (Check) sessionFactory.getCurrentSession().load(Check.class, id);
		if (null != check) {
			sessionFactory.getCurrentSession().delete(check);
		}		
	}

	@Override
	public Check getCheck(Integer id) {
		return (Check) sessionFactory.getCurrentSession().get(Check.class, id);
	}

	@Override
	public Check getCheckByTableId(Integer restaurantId, Integer tableId) {
		List<Check> checksForTable = sessionFactory.getCurrentSession().createCriteria(Check.class).add(Restrictions.and(Restrictions.eq("tableId", tableId), Restrictions.eq("restaurantId", restaurantId), Restrictions.ne("status", Status.PAID))).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
		if (checksForTable != null && checksForTable.size() > 0) {
			return checksForTable.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Check getCheckByCustId(Integer restaurantId, Integer custId) {
		List<Check> checksForTable = sessionFactory.getCurrentSession().createCriteria(Check.class).add(Restrictions.and(Restrictions.eq("customerId", custId), Restrictions.eq("restaurantId", restaurantId), Restrictions.ne("status", Status.PAID))).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
		if (checksForTable != null && checksForTable.size() > 0) {
			return checksForTable.get(0);
		} else {
			return null;
		}
	}

}
