/**
 * 
 */
package com.cookedspecially.interceptors;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.imageio.ImageIO;


import org.apache.commons.lang3.StringEscapeUtils;
import org.hamcrest.core.StringEndsWith;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import com.cookedspecially.utility.ImageUtility;

import junit.framework.TestCase;

/**
 * @author sagarwal
 *
 */
public class SecurityTest extends TestCase {

	public void testPassword() throws UnsupportedEncodingException {
		PasswordEncoder encoder = new ShaPasswordEncoder();
		//String hash = encoder.encodePassword("hello", "BITE MY SHINY METAL ASS!");
		String string = "&#2309;&#2344;&#2369;&#2330;&#2381;&#2331;&#2375;&#2342;";
		String strEscap = StringEscapeUtils.unescapeHtml4(string);
		System.out.println(strEscap);
		System.out.println(URLDecoder.decode(strEscap, "utf-8"));
		System.out.println(StringEscapeUtils.escapeHtml4(strEscap));
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
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("PST"));
		//System.out.println(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US));
		//cal.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
		System.out.println(cal.getTimeZone().getDisplayName());
		DateFormat formatter1;
		formatter1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		formatter1.setTimeZone(cal.getTimeZone());
		System.out.println(formatter1.format(cal.getTime()));
//		System.out.println(cal.get(Calendar.HOUR));
//		System.out.println(cal.get(Calendar.MINUTE));
//		System.out.println(cal.get(Calendar.DATE));
		
		cal.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
		
		System.out.println(cal.getTimeZone().getDisplayName());
		formatter1.setTimeZone(cal.getTimeZone());
		System.out.println(formatter1.format(cal.getTime()));
//		System.out.println(cal.get(Calendar.HOUR));
//		System.out.println(cal.get(Calendar.MINUTE));
//		System.out.println(cal.get(Calendar.DATE));
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
