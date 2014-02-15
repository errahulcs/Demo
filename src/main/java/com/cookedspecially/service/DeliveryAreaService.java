/**
 * 
 */
package com.cookedspecially.service;

import java.util.List;
import com.cookedspecially.domain.DeliveryArea;

/**
 * @author shashank
 *
 */
public interface DeliveryAreaService {
	
	public static String defaultDeliveryArea = "Others";
	
	public void addDeliveryArea(DeliveryArea deliveryArea);
	public List<DeliveryArea> listDeliveryAreas();
	public List<DeliveryArea> listDeliveryAreasByUser(Integer userId);
	public void removeDeliveryArea(Integer id);
	public DeliveryArea getDeliveryArea(Integer id);
}
