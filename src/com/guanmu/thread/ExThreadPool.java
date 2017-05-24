/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>
 * 类描述:
 * <p>
 * 
 * 所属插件:com.guanmu.thread
 * @author wangquan 2017-5-24
 * 
 */
public class ExThreadPool {
	
	private static ExThreadPool instance = new ExThreadPool();
	
	private static ExecutorService controllerExec = Executors.newCachedThreadPool();
	
	private static ExecutorService nearTryExec = Executors.newCachedThreadPool();
	
	private ExThreadPool() {
		
	}
	
	public static ExThreadPool getInstance() {
		return instance;
	}
	
	
	public ExecutorService getControllerExec() {
		return controllerExec;
	}
	
	public ExecutorService getNearTyExec() {
		return nearTryExec;
	}

	/**
	 * 
	 */
	public void stopThreadPools() {
		
		if (!controllerExec.isShutdown()) {
			controllerExec.shutdownNow();			
		}
		
		if (!nearTryExec.isShutdown()) {
			nearTryExec.shutdownNow();			
		}
		
	}

}
