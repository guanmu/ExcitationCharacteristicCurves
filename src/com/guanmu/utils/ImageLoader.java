/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.utils;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * 类描述:
 * <p>
 * 
 * 所属插件:com.guanmu.utils
 * @author wangquan 2017-5-15
 * 
 */
public final class ImageLoader {
	
	private ImageLoader() {
		
	}
	
	public static Image getImage(Class<?> relativeClass, String filename) {
		Image returnValue = null;
		
		InputStream is = relativeClass.getResourceAsStream(filename);
		if (is != null) {
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			try {
				int ch = 0;
				while((ch = bis.read()) != -1) {
					baos.write(ch);
				}
				
				returnValue = Toolkit.getDefaultToolkit().createImage(baos.toByteArray());
			} catch (IOException exception) {
				System.err.println("Error loading:" + filename);
			}
				
		}
		
		return returnValue;
	}
	
	
}
