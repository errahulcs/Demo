/**
 * 
 */
package com.cookedspecially.utility;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author shashank
 *
 */
public class ImageUtility {

	public static boolean resizeImage(String srcPath, String targetPath, String formatType, int resolutionX, int resolutionY) {
		if (resolutionX < 1 || resolutionY < 1) {
			return false;
		}
		try {
			BufferedImage srcImage = ImageIO.read(new File(srcPath));
	    	int type = srcImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : srcImage.getType();
	    	int existingHeight = srcImage.getHeight();
	    	int existingWidth = srcImage.getWidth();
	    	float ratioX = (float)existingWidth/resolutionX;
	    	float ratioY = (float)existingHeight/resolutionY;
	    	float finalDivider = Math.min(ratioX, ratioY);
			BufferedImage resizeImageJpg = resizeImage(srcImage, (int)(existingWidth/finalDivider), (int)(existingHeight/finalDivider), type);
			ImageIO.write(resizeImageJpg, formatType, new File(targetPath));
			return true;
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return false;
	}
	
	public static BufferedImage resizeImage(BufferedImage srcImage, int resolutionX, int resolutionY, int type) {
		BufferedImage targetImage = new BufferedImage(resolutionX, resolutionY, type);
		Graphics2D g = targetImage.createGraphics();
		g.drawImage(srcImage, 0, 0, resolutionX, resolutionY, null);
		g.dispose();
	 
		return targetImage;
	}
}
