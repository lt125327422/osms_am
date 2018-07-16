package com.itecheasy.common.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BeanContrastValueUtils {

	public static <T, E> List<String> contrastProperties(T source, E target,String[] ignoreParams) {
		return contrast(source, target, ignoreParams);
	}
	
	public static <T, E> List<String> contrastProperties(T source, E target) {
		return contrast(source, target, null);
	}
	
	public static <T, E> List<String> contrastProperties(T source, Class<E> type) {
		if(source == null)
			return null;
		E target;
		try {
			target = type.newInstance();
			return contrastProperties(source,target,null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static <T, E> List<String> contrastProperties(T source, Class<E> type,String[] ignoreParams) {
		E target;
		try {
			target = type.newInstance();
			return contrastProperties(source,target,ignoreParams);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	private static <T, E> List<String> contrast(T source, E target, String[] ignoreParams) {
		if (source == null || target == null)
			return null;
		List<String> result = new ArrayList<String>();
 		try {
			Method[] sourceMethods = source.getClass().getDeclaredMethods();
			Method[] targetMethods = target.getClass().getDeclaredMethods();
			String sourceMethodName = null;
			String fieldName = null;
			for (Method sourceMethod : sourceMethods) {
				sourceMethodName = sourceMethod.getName();
				if (sourceMethodName.startsWith("get")) {
					fieldName = sourceMethodName.substring(3);
				} else if (sourceMethodName.startsWith("is")) {
					fieldName = sourceMethodName.substring(2);
				} else {
					continue;
				}
				boolean isIgnore = false;
				if(ignoreParams != null)
					for(String fn : ignoreParams){
						if(fn.equalsIgnoreCase(fieldName)){
							isIgnore = true;
							break;
						}
					}
				if(isIgnore){
					continue;
				}
				Object o = sourceMethod.invoke(source, new Object[] {}); 
				String getMethodName = "get" + fieldName;
				Object value = null;
				for (Method sm : targetMethods) {
					if (sm.getName().equalsIgnoreCase(getMethodName)) {
						value = sm.invoke(target, new Object[] {}); 
						break;
					}
				}
				if(o == null){
					if(value != null){
						result.add(fieldName);
					}
					continue;
				}
				if(!o.equals(value)){
					result.add(fieldName);
				}
			}
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	} 
}
