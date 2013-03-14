/**
 * 
 */
package com.cookedspecially.interceptors;

import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import junit.framework.TestCase;

/**
 * @author sagarwal
 *
 */
public class SecurityTest extends TestCase {

	public void testPassword() {
		PasswordEncoder encoder = new ShaPasswordEncoder();
		String hash = encoder.encodePassword("hello", "BITE MY SHINY METAL ASS!");
		System.out.println(hash);
		System.out.println(encoder.isPasswordValid(hash, "hello", "BITE MY SHINY METAL ASS!"));
	}
	
	public void testTrim() {
		String completeUrl = "http://localhost:28080/CookedSpecially/user/forgotPassword.html";
		System.out.println(completeUrl.substring(0, completeUrl.lastIndexOf("/forgotPassword")));
	}
}
