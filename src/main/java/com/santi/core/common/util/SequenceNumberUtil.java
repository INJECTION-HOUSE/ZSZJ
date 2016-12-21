package com.santi.core.common.util;

import java.util.UUID;

public class SequenceNumberUtil {
	
	// 维修
	private static final String WX_PREFIX ="WX";
	
	// 投标
	private static final String TB_PREFIX ="TB";
	
	// 提现
	private static final String TX_PREFIX ="TX";
	
	// 收入记录订单编号
	private static final String DD_PREFIX ="DD";
	
	// 充值
	private static final String CZ_PREFIX ="CZ";

	/**
	 * 生成系统维修订单号
	 * @param code
	 * @return
	 */
	public static String generatChongzhiNumber(String code) {
		long naroTime = System.nanoTime();
		if(code == null || code.length() != 4) {
			return null;
		}
		return CZ_PREFIX + code + String.valueOf(naroTime);
	}
	
	/**
	 * 生成系统维修订单号
	 * @param code
	 * @return
	 */
	public static String generateOrderNumber(String code) {
		long naroTime = System.nanoTime();
		if(code == null || code.length() != 4) {
			return null;
		}
		return DD_PREFIX + code + String.valueOf(naroTime);
	}
	
	/**
	 * 生成系统维修订单号
	 * @param code
	 * @return
	 */
	public static String generateRepairNumber(String code) {
		long naroTime = System.nanoTime();
		if(code == null || code.length() != 4) {
			return null;
		}
		return WX_PREFIX + code + String.valueOf(naroTime);
	}
	
	/**
	 * 生成提款时候的提款申请号
	 * @param code
	 * @return
	 */
	public static String generateWithDrawNumber(String code) {
		long naroTime = System.nanoTime();
		if(code == null || code.length() != 4) {
			return null;
		}
		return TX_PREFIX + code + String.valueOf(naroTime);
	}
	
	/**
	 * 生成系统指定的投标号
	 * @param code
	 * @return
	 */
	public static String generateBidNumber(String code) {
		long naroTime = System.nanoTime();
		if(code == null || code.length() != 4) {
			return null;
		}
		return TB_PREFIX + code + String.valueOf(naroTime);
	}
	
	public static void main(String[] args) {
		System.out.println(UUID.randomUUID().toString());
		
		String key = "b5cfd3a3a73b4f3ab85915dedca17f79";
		System.out.println(key.length());
	}
}
