/**
 * 
 */
package com.cookedspecially.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cookedspecially.dao.DeliveryAreaDAO;
import com.cookedspecially.domain.DeliveryArea;
import com.cookedspecially.service.DeliveryAreaService;

/**
 * @author shashank
 *
 */
@Service
public class DeliveryAreaServiceImpl implements DeliveryAreaService {

	@Autowired
	private DeliveryAreaDAO deliveryAreaDAO;
	
	/* (non-Javadoc)
	 * @see com.cookedspecially.service.DeliveryAreaService#addDeliveryArea(com.cookedspecially.domain.DeliveryArea)
	 */
	@Override
	@Transactional
	public void addDeliveryArea(DeliveryArea deliveryArea) {
		deliveryAreaDAO.addDeliveryArea(deliveryArea);
	}

	/* (non-Javadoc)
	 * @see com.cookedspecially.service.DeliveryAreaService#listDeliveryAreas()
	 */
	@Override
	@Transactional
	public List<DeliveryArea> listDeliveryAreas() {
		return deliveryAreaDAO.listDeliveryArea();
	}

	/* (non-Javadoc)
	 * @see com.cookedspecially.service.DeliveryAreaService#listDeliveryAreasByUser(java.lang.Integer)
	 */
	@Override
	@Transactional
	public List<DeliveryArea> listDeliveryAreasByUser(Integer userId) {
		return deliveryAreaDAO.listCategoryByUser(userId);
	}

	/* (non-Javadoc)
	 * @see com.cookedspecially.service.DeliveryAreaService#removeDeliveryArea(java.lang.Integer)
	 */
	@Override
	@Transactional
	public void removeDeliveryArea(Integer id) {
		deliveryAreaDAO.removeDeliveryArea(id);
	}

	/* (non-Javadoc)
	 * @see com.cookedspecially.service.DeliveryAreaService#getDeliveryArea(java.lang.Integer)
	 */
	@Override
	@Transactional
	public DeliveryArea getDeliveryArea(Integer id) {
		return deliveryAreaDAO.getDeliveryArea(id);
	}

}
