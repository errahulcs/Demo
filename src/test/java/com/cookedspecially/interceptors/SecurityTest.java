/**
 * 
 */
package com.cookedspecially.interceptors;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Locale;

import javax.imageio.ImageIO;


import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import com.cookedspecially.utility.ImageUtility;

import junit.framework.TestCase;

/**
 * @author sagarwal
 *
 */
public class SecurityTest extends TestCase {

	public void testPassword() {
		PasswordEncoder encoder = new ShaPasswordEncoder();
		String hash = encoder.encodePassword("hello", "BITE MY SHINY METAL ASS!");
		//System.out.println(hash);
		//System.out.println(encoder.isPasswordValid(hash, "hello", "BITE MY SHINY METAL ASS!"));
	}
//	
//	
	public void testEncoding() throws IOException {
    	String fileUrl = "/home/shashank/Desktop/70_ballentiine_17.png";
    	String targetPath = ImageUtility.getSmallImageUrl(fileUrl, 200, 200);//"/home/shashank/Desktop/70_ballentiine_17200x200.png";
    	System.out.println(targetPath);
    	//ImageUtility.resizeImage(fileUrl, targetPath, ImageUtility.getFileFormat(fileUrl), 200, 200);
    	System.out.println(ImageUtility.getFileFormat(fileUrl));
	}
	
	public void testCalendar() {
		Calendar cal = Calendar.getInstance();
		System.out.println(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US));
	}
//	private static BufferedImage resizeImage(BufferedImage originalImage, int type){
//		BufferedImage resizedImage = new BufferedImage(100, 100, type);
//		Graphics2D g = resizedImage.createGraphics();
//		g.drawImage(originalImage, 0, 0, 100, 100, null);
//		g.dispose();
//	 
//		return resizedImage;
//	}
}
