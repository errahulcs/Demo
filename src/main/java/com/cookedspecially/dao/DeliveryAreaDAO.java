/**
 * 
 */
package com.cookedspecially.dao;

import java.util.List;
import com.cookedspecially.domain.DeliveryArea;

/**
 * @author shashank
 *
 */
public interface DeliveryAreaDAO {
	public void addDeliveryArea(DeliveryArea deliveryArea);
	public List<DeliveryArea> listDeliveryArea();
	public List<DeliveryArea> listCategoryByUser(Integer userId);
	public void removeDeliveryArea(Integer id);
	public DeliveryArea getDeliveryArea(Integer id);
}
