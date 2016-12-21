package com.santi.core.common.messages;

import org.springframework.beans.factory.InitializingBean;

/**
 * 全局配置类
 * 
 * @author ThinkGem
 * @version 2014-06-25
 */
public class Global implements InitializingBean {

	/**
	 * 当前对象实例
	 */
	private static Global global;

	private static Message message;
	private String author;
	/**
	 * 显示/隐藏
	 */
	public static final String SHOW = "1";
	public static final String HIDE = "0";

	/**
	 * 是/否
	 */
	public static final String YES = "1";
	public static final String NO = "0";

	/**
	 * 对/错
	 */
	public static final String TRUE = "true";
	public static final String FALSE = "false";

	/**
	 * 获取当前对象实例
	 */
	public static Global getInstance() {
		return global;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * 页面获取常量
	 * 
	 * @see ${fns:getConst('YES')}
	 */
	public static Object getConst(String field) {
		try {
			return Global.class.getField(field).get(null);
		} catch (Exception e) {
			// 异常代表无配置，这里什么也不做
		}
		return null;
	}

	public static String getMessage(String key) {
		return message.getMessage(key);
	}

	public static String getMessage(String suffix, String... prefix) {
		return message.getMessage(suffix, prefix);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		global = SpringContextHolder.getBean(Global.class);
		message = SpringContextHolder.getBean(Message.class);
	}

}
