package com.santi.core.datamodel.dto;

public interface WeiXinConstants {
	
	public static final int WHITE = 0xFFFFFFFF;
	public static final int BLACK = 0xFF000000;
	
	// 账号信息
//	public static final String APPID = "wx69c23dc789f7bc59";
//	public static final String APP_SECRT_KEY = "b3dad73bcb519095af72a3be7284f0e8";
	public static final String APPID = "wx6962b52069f38239";
	public static final String APP_SECRT_KEY = "628b7c62cc229b455778bb2e059b4710";
	public static final String APP_OAUTH_STATE = "ZHUSUZHIJIA-987ERIA12345";
	
	// 支付验证
	public static final String WXPAYMENTACCOUNT = "1406425802";
//	public static final String MD5_API_KEY = "804697500bad11e7b4a000ff434a7fc1";
	public static final String MD5_API_KEY = "b5cfd3a3a73b4f3ab85915dedca17f79";
	
	// Oauth request access_token url
	public static final String REQUEST_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + APPID + "&secret=" + APP_SECRT_KEY + "&code={CODE}&grant_type=authorization_code";
	
	// 请求 用户信息
	public static final String REQUEST_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token={ACCESS_TOKEN}&openid={OPENID}&lang=zh_CN";
		
	// 同一下单URL
	public static final String UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	// 回调URL
	public static final String CALLBACK_URL = "http://www.zhusuhome.com/zszj/payment/notifycallback";
	//微信app支付回调URL
	public static final String APP_CALLBACK_URL = "http://www.zhusuhome.com/zszj/payment/appnotifycallback";
	// public static final String CALLBACK_URL = "http://127.0.0.1:8080/zszj/payment/notifycallback";
	
	// 设备信息
	public static final String DEVICE_INFO_WEB = "WEB";
	
	// PC端网站支持扫码支付
	public static final String TRADE_TYPE_NATIVE = "NATIVE";  
	
	// 公众号内部支持JS支付
	public static final String TRADE_TYPE_JSAPI = "JSAPI";  
	
	// 微信登录默认密码
	public static final String DEFAULT_PASSWORD = "@#$ZSZJ123";
	
	
}
