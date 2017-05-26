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
	
	private static ExecutorService controllerExec = null;
	
	private static ExecutorService nearTryExec = null;
	
	private static ExecutorService tryExec = null;
	
	private ExThreadPool() {
		
	}
	
	public static ExThreadPool getInstance() {
		return instance;
	}
	
	
	public ExecutorService getControllerExec() {
		if (controllerExec != null) {
			controllerExec.shutdownNow();
		}
		
		controllerExec = Executors.newCachedThreadPool();
		
		return controllerExec;
	}
	
	public ExecutorService getNearTryExec() {
		
		if (nearTryExec != null) {
			nearTryExec.shutdownNow();
		}
		
		nearTryExec = Executors.newCachedThreadPool();
		
		return nearTryExec;
	}
	
	public ExecutorService getTryExec() {
		
		if (tryExec != null) {
			tryExec.shutdownNow();
		}
		
		tryExec = Executors.newCachedThreadPool();
		
		return tryExec;
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
		
		if (!tryExec.isShutdown()) {
			tryExec.shutdownNow();
		}
	}

}
