package com.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.util.StringUtils;

/**
 * Map辅助类
 */
public final class MapUtil {
	/**
	 * 判断Map中是否缺少必传参数.
	 * 
	 * @param param
	 * @param key
	 * @return
	 */
	public static boolean isContains(final Map<String, Object> param, String... keys) {
		boolean flag = true;
		if (param == null){
			flag = false;
		} else {
			for (String str : keys) {
				if (!param.containsKey(str) || param.get(str) == null || String.valueOf(param.get(str)).equals("") || String.valueOf(param.get(str)).equalsIgnoreCase("null")) {
					flag = false;
					break;
				}
			}
		}
		return flag;
	}

	/**
	 * 判断Map中是否缺少必传参数.
	 * 
	 * @param param
	 * @param key
	 * @return
	 */
	public static boolean isContainsStr(final Map<String, String> param, String... keys) {
		boolean flag = true;
		if (param == null){
			flag = false;
		} else {
			for (String str : keys) {
				if (!param.containsKey(str) || param.get(str) == null || "".equals(param.get(str))) {
					flag = false;
					break;
				}
			}
		}
		return flag;
	}

}
