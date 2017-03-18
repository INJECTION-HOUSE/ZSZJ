package com.santi.core.datamodel.dto;

import com.santi.core.common.util.WeiXinPaymentUtil;

public class UnifiedorderDto implements WeiXinConstants {

	private String appid;
	private String body;
	private String device_info;
	private String mch_id;
	private String nonce_str;
	private String notify_url;
	private String openId;
	private String out_trade_no;
	private String spbill_create_ip;
	private int total_fee;
	private String trade_type;
	private String product_id;
	private String sign;
	
	public UnifiedorderDto() {
		this.appid = APPID;
		this.mch_id = WXPAYMENTACCOUNT;
		this.device_info = DEVICE_INFO_WEB;
		this.notify_url = CALLBACK_URL;
		this.trade_type = TRADE_TYPE_NATIVE;
	}
	
	public UnifiedorderDto(boolean app){
		this.appid = APPID;
		this.mch_id = WXPAYMENTACCOUNT;
		this.notify_url = APP_CALLBACK_URL;
		this.trade_type = TRADE_TYPE_JSAPI;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

	public int getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String generateXMLContent() {
		String xml = "<xml>" +
		   "<appid>" + this.appid + "</appid>" + 
		   "<body>" + this.body + "</body>" + 
		   "<device_info>WEB</device_info>" + 
		   "<mch_id>" + this.mch_id + "</mch_id>" + 
		   "<nonce_str>" + this.nonce_str + "</nonce_str>" +
		   "<notify_url>" + this.notify_url + "</notify_url>" + 
		   "<out_trade_no>" + this.out_trade_no + "</out_trade_no>" + 
		   "<product_id>" + this.product_id + "</product_id>" +
		   "<spbill_create_ip>" + this.spbill_create_ip+ "</spbill_create_ip>" +
		   "<total_fee>" + String.valueOf(this.total_fee) + "</total_fee>" + 
		   "<trade_type>" + this.trade_type + "</trade_type>" + 
		   "<sign>" + this.sign + "</sign>" + 
		"</xml>";
		return xml;
	}
	
	public String makeSign() {
		String content ="appid=" + this.appid + 
				   "&body=" + this.body + 
				   "&device_info=WEB" + 
				   "&mch_id=" + this.mch_id + 
				   "&nonce_str=" + this.nonce_str + 
				   "&notify_url=" + this.notify_url +
				   "&out_trade_no=" + this.out_trade_no + 
				   "&product_id=" + this.product_id + 
				   "&spbill_create_ip=" + this.spbill_create_ip+
				   "&total_fee=" + String.valueOf(this.total_fee) +
				   "&trade_type=" + this.trade_type;
		content = content + "&key=" + WeiXinConstants.MD5_API_KEY;
		String esignature = WeiXinPaymentUtil.MD5Encode(content, "utf-8");
		return esignature.toUpperCase();
	}
	
	public String generateAppXMLContent() {
		String xml = "<xml>" +
		   "<appid>" + this.appid + "</appid>" + 
		   "<body>" + this.body + "</body>" + 
		   "<device_info>WEB</device_info>" + 
		   "<mch_id>" + this.mch_id + "</mch_id>" + 
		   "<nonce_str>" + this.nonce_str + "</nonce_str>" +
		   "<notify_url>" + this.notify_url + "</notify_url>" + 
		   "<openid>" + this.openId + "</openid>" + 
		   "<out_trade_no>" + this.out_trade_no + "</out_trade_no>" + 
		   "<spbill_create_ip>" + this.spbill_create_ip+ "</spbill_create_ip>" +
		   "<total_fee>" + String.valueOf(this.total_fee) + "</total_fee>" + 
		   "<trade_type>" + this.trade_type + "</trade_type>" + 
		   "<sign>" + this.sign + "</sign>" + 
		"</xml>";
		return xml;
	}
	
	public String makeAppSign() {
		String content ="appid=" + this.appid + 
				   "&body=" + this.body + 
				   "&device_info=WEB" + 
				   "&mch_id=" + this.mch_id + 
				   "&nonce_str=" + this.nonce_str + 
				   "&notify_url=" + this.notify_url +
				   "&openid=" + this.openId + 
				   "&out_trade_no=" + this.out_trade_no + 
				   "&spbill_create_ip=" + this.spbill_create_ip+
				   "&total_fee=" + String.valueOf(this.total_fee) +
				   "&trade_type=" + this.trade_type;
		content = content + "&key=" + WeiXinConstants.MD5_API_KEY;
		String esignature = WeiXinPaymentUtil.MD5Encode(content, "utf-8");
		return esignature.toUpperCase();
	}
	
}
