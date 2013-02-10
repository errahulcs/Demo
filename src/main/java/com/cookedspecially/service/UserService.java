/**
 * 
 */
package com.cookedspecially.service;

import com.cookedspecially.domain.User;

/**
 * @author sagarwal
 *
 */
public interface UserService {

	public void addUser(User user);
	
	public User getUserByUsername(String username);
	
	public boolean isValidUser(User user, String password);
	
	public String getHash(String password);
}
