/**
 * 
 */
package com.cookedspecially.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cookedspecially.dao.UserDAO;
import com.cookedspecially.domain.User;
import com.cookedspecially.service.UserService;

/**
 * @author sagarwal
 *
 */
@Service
public class UserServiceImpl implements UserService {

	private static String salt = "BITE MY SHINY METAL ASS!!";
	private static PasswordEncoder encoder = new ShaPasswordEncoder();
	
	@Autowired
	private UserDAO userDao;

	@Override
	@Transactional
	public void addUser(User user) {
		userDao.saveUser(user);
	}

	@Override
	@Transactional
	public User getUser(Integer userId) {
		return userDao.getUser(userId);
	}
	
	@Override
	@Transactional
	public User getUserByUsername(String username) {
		return userDao.getUserByUsername(username);
	}

	@Override
	@Transactional
	public boolean isValidUser(User user, String password) {
		if (encoder.isPasswordValid(user.getPasswordHash(), password, salt)) {
			return true;
		}
		return false;
	}

	public String getHash(String password) {
		return encoder.encodePassword(password, salt);
	}
}
