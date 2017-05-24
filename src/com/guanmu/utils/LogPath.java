/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.utils;

import ch.qos.logback.core.PropertyDefinerBase;


/**
 * <p>
 * 类描
 * <p>
 * @author wangquan 2016-3-24
 * 
 */
public class LogPath extends PropertyDefinerBase {
	
	public static final String OS_NAME = System.getProperty("os.name").toLowerCase();	
	
	public static final String WINDOWS_SUFFIX = "C:/";
	public static final String LINUX_SUFFIX = "/";	
	
	private static final String SUB_LOG_PATH = "ecc/logs";
	
	@Override
	public String getPropertyValue() {
		
		if (isWindows()) {
			return WINDOWS_SUFFIX + SUB_LOG_PATH;			
		}

		if (isLinux()) {
			return LINUX_SUFFIX + SUB_LOG_PATH;	
		}
		
		return "ecc/logs";
	}


	
	/**
	 * @return
	 */
	public static boolean isLinux() {
		return OS_NAME.indexOf("linux") >= 0;
	}

	/**
	 * @return
	 */
	public static boolean isWindows() {
		return OS_NAME.indexOf("windows") >= 0;
	}
	
}
