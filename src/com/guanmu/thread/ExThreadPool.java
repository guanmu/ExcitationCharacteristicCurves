/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.thread;

import java.util.ArrayList;
import java.util.List;
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
	
	private static List<ExecutorService> controllerExecs = new ArrayList<ExecutorService>();
	
	private static List<ExecutorService> nearTryExecs = new ArrayList<ExecutorService>();
	
	private static List<ExecutorService> tryExecs = new ArrayList<ExecutorService>();
	
	private ExThreadPool() {
		
	}
	
	public static ExThreadPool getInstance() {
		return instance;
	}
	
	
	public ExecutorService getControllerExec() {

		
		ExecutorService controllerExec = Executors.newCachedThreadPool();
		controllerExecs.add(controllerExec);
		
		return controllerExec;
	}
	
	public ExecutorService getNearTryExec() {
		
		ExecutorService nearTryExec = Executors.newCachedThreadPool();
		nearTryExecs.add(nearTryExec);
		
		return nearTryExec;
	}
	
	public ExecutorService getTryExec() {
		
		ExecutorService tryExec = Executors.newCachedThreadPool();
		tryExecs.add(tryExec);
		
		return tryExec;
	}
	
	/**
	 * 
	 */
	public void stopThreadPools() {
		
		for(ExecutorService exec : controllerExecs) {
			exec.shutdownNow();
		}
		
		for(ExecutorService exec : nearTryExecs) {
			exec.shutdownNow();
		}

		for(ExecutorService exec : tryExecs) {
			exec.shutdownNow();
		}
		
		controllerExecs.clear();
		nearTryExecs.clear();
		tryExecs.clear();
	}

}
