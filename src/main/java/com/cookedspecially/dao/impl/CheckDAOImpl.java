/**
 * 
 */
package com.cookedspecially.dao.impl;


import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cookedspecially.dao.CheckDAO;
import com.cookedspecially.domain.Check;
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
		List<Check> checksForTable = sessionFactory.getCurrentSession().createCriteria(Check.class).add(Restrictions.and(Restrictions.eq("tableId", tableId), Restrictions.eq("restaurantId", restaurantId), Restrictions.ne("status", Status.Paid), Restrictions.ne("status", Status.Cancel))).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
		if (checksForTable != null && checksForTable.size() > 0) {
			return checksForTable.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Check getCheckByCustId(Integer restaurantId, Integer custId) {
		List<Check> checksForTable = sessionFactory.getCurrentSession().createCriteria(Check.class).add(Restrictions.and(Restrictions.eq("customerId", custId), Restrictions.eq("restaurantId", restaurantId), Restrictions.ne("status", Status.Paid), Restrictions.ne("status", Status.Cancel))).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
		if (checksForTable != null && checksForTable.size() > 0) {
			return checksForTable.get(0);
		} else {
			return null;
		}
	}

	
	@Override
	public List<Check> getAllOpenChecks(Integer restaurantId) {
		return sessionFactory.getCurrentSession().createCriteria(Check.class).add(Restrictions.and(Restrictions.eq("restaurantId", restaurantId), Restrictions.ne("status", Status.Paid), Restrictions.ne("status", Status.Cancel))).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
	}
	
	@Override
	public List<Integer> getAllCheckIds() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Check.class);
		criteria.setProjection( Projections.projectionList().add( Projections.property("checkId")));

		List<Integer> ids=criteria.list();
		return ids;
	}
	
	@Override
	public List getClosedChecksByDate(Integer restaurantId, Date startDate, Date endDate) {
		//return sessionFactory.getCurrentSession().createCriteria(Check.class).add(Restrictions.and(Restrictions.eq("restaurantId", restaurantId), Restrictions.ne("status", Status.Unpaid), Restrictions.ne("status", Status.Cancel), Restrictions.between("closeTime", startDate, endDate))).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
		String sqlQuery = "SELECT sum(bill), checkType FROM Check WHERE restaurantId = :restaurantId AND closeTime > :startDate AND closeTime < :endDate group by checkType)";
		Query query = sessionFactory.getCurrentSession().createQuery(sqlQuery).setParameter("restaurantId", restaurantId).setParameter("startDate", startDate).setParameter("endDate", endDate);
		return query.list();

	}
}
