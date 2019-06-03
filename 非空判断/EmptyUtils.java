package com.ibm.modules.pdm.common.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * 判断Bean对象、集合、Map是否为空工具类
 * 
 * @author LiuBaoWen
 *
 */
public class EmptyUtils {

	/**
	 * 判断对象为null, null-true, not null-false
	 * 
	 * @param obj
	 * @return boolean
	 */
	public static boolean isNull(Object obj) {
		return obj == null;
	}

	/**
	 * 判断对象不为null, not null-true, null-false
	 * 
	 * @param obj
	 * @return boolean
	 */
	public static boolean isNotNull(Object obj) {
		return !isNull(obj);
	}

	/**
	 * 判断对象、集合、Map、数组、序列是否为空
	 * 
	 * @param obj
	 * @return boolean
	 */
	public static boolean isEmpty(Object obj) {
		// 判断对象
		if (obj == null)
			return true;
		// 判断集合
		else if (obj instanceof Collection)
			return ((Collection<?>) obj).isEmpty();
		// 判断map
		else if (obj instanceof Map)
			return ((Map<?, ?>) obj).isEmpty();
		// 判断数据
		else if (obj.getClass().isArray())
			return Array.getLength(obj) == 0;
		// 判断序列
		else if (obj instanceof CharSequence)
			return ((CharSequence) obj).length() == 0;
		return false;
	}

	/**
	 * 判断对象不为空
	 * 
	 * @param obj
	 * @return boolean
	 */
	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}

}
