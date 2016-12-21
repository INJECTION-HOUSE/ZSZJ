package com.santi.core.common.util;

public class StringUtil {
	
	public static boolean isEmpty(String content) {
		if(content == null || "".equals(content.trim())) {
			return true;
		}
		return false;
	}

}
