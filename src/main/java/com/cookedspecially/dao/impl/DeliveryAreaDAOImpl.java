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

import com.cookedspecially.dao.DeliveryAreaDAO;
import com.cookedspecially.domain.Category;
import com.cookedspecially.domain.DeliveryArea;

/**
 * @author shashank
 *
 */
@Repository
public class DeliveryAreaDAOImpl implements DeliveryAreaDAO {

	@Autowired
	private SessionFactory sessionFactory;

	/* (non-Javadoc)
	 * @see com.cookedspecially.dao.DeliveryAreaDAO#addDeliveryArea(com.cookedspecially.domain.DeliveryArea)
	 */
	@Override
	public void addDeliveryArea(DeliveryArea deliveryArea) {
		sessionFactory.getCurrentSession().saveOrUpdate(deliveryArea);
	}

	/* (non-Javadoc)
	 * @see com.cookedspecially.dao.DeliveryAreaDAO#listDeliveryArea()
	 */
	@Override
	public List<DeliveryArea> listDeliveryArea() {
		return sessionFactory.getCurrentSession().createQuery("from DeliveryArea").list();
	}

	/* (non-Javadoc)
	 * @see com.cookedspecially.dao.DeliveryAreaDAO#listCategoryByUser(java.lang.Integer)
	 */
	@Override
	public List<DeliveryArea> listCategoryByUser(Integer userId) {
		return sessionFactory.getCurrentSession().createCriteria(DeliveryArea.class).add(Restrictions.eq("userId", userId)).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
	}

	/* (non-Javadoc)
	 * @see com.cookedspecially.dao.DeliveryAreaDAO#removeDeliveryArea(java.lang.Integer)
	 */
	@Override
	public void removeDeliveryArea(Integer id) {
		DeliveryArea deliveryArea = (DeliveryArea) sessionFactory.getCurrentSession().load(DeliveryArea.class, id);
		if (null != deliveryArea) {
			sessionFactory.getCurrentSession().delete(deliveryArea);
		}
	}

	/* (non-Javadoc)
	 * @see com.cookedspecially.dao.DeliveryAreaDAO#getDeliveryArea(java.lang.Integer)
	 */
	@Override
	public DeliveryArea getDeliveryArea(Integer id) {
		return (DeliveryArea) sessionFactory.getCurrentSession().load(DeliveryArea.class, id);
	}

}
