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

import javax.imageio.ImageIO;


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
		//System.out.println(hash);
		//System.out.println(encoder.isPasswordValid(hash, "hello", "BITE MY SHINY METAL ASS!"));
	}
	
	
	public void testEncoding() throws IOException {
		//String finalStr = "Hello sample file.jpg".replaceAll("[^a-zA-Z0-9_.]", "_");
		//System.out.println(finalStr);
    	String fileUrl = "/home/shashank/Downloads/bhelpuri2.jpeg";
    	BufferedImage originalImage = ImageIO.read(new File(fileUrl));
    	int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
    	 
		BufferedImage resizeImageJpg = resizeImage(originalImage, type);
		ImageIO.write(resizeImageJpg, "jpg", new File("/home/shashank/Downloads/bhelpuri3.jpg"));
		
	}
	private static BufferedImage resizeImage(BufferedImage originalImage, int type){
		BufferedImage resizedImage = new BufferedImage(100, 100, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, 100, 100, null);
		g.dispose();
	 
		return resizedImage;
	}
}
