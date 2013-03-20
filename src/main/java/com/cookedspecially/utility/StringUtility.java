/**
 * 
 */
package com.cookedspecially.utility;

/**
 * @author sagarwal
 *
 */
public class StringUtility {

	public static boolean isNullOrEmpty(String str) {
		if (str == null || str.length() == 0) {
			return true;
		}
		return false;
	}
	
}

