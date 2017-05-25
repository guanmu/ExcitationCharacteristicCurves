/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.utils;

import java.util.Comparator;

import com.guanmu.model.ExFunction;

/**
 * <p>
 * 类描述:
 * <p>
 * 
 * 所属插件:com.guanmu.utils
 * @author wangquan 2017-5-25
 * 
 */
public class SortMethod {
	
	public static class FunctionSortByAB implements Comparator<ExFunction> {

		@Override
		public int compare(ExFunction f1, ExFunction f2) {
			
			if (f1 == null) {
				return -1;
			}
			
			if (f2 == null) {
				return 1;
			}
			
			if (f1.getA() > f2.getA()) {
				return 1;
			}
			
			if (f1.getA() < f2.getA()) {
				return -1;
			}
			
			if (f1.getB() > f2.getB()) {
				return 1;
			}
			
			if (f1.getB() < f2.getB()) {
				return -1;
			}
			
			return 0;
		}
		
	}
	
}
