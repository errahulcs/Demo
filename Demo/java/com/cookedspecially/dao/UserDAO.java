/**
 * 
 */
package com.cookedspecially.dao;

import com.cookedspecially.domain.User;

/**
 * @author sagarwal
 *
 */
public interface UserDAO {

	public void saveUser(User user);
	public User getUser(Integer userId);
	public User getUserByUsername(String username);
	
}
