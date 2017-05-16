package com.guanmu.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RootLogger {
	static Logger log = LoggerFactory.getLogger("root");

	public static Logger getLog() {
		return log;
	}

	public static Logger getLogEx() {
		 return LoggerFactory.getLogger(new
		 Throwable().getStackTrace()[1].getClassName());
	}

	public static Logger getLog(String className) {
		return LoggerFactory.getLogger(className);
	}

}
