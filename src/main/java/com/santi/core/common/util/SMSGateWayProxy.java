package com.santi.core.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class SMSGateWayProxy {
	// never try to change these settings
	private static final String SMS_USERID = "199234zhang";
	private static final String SMS_SECURITY_KEY = "6f7d69c2d590c86ec708";
	// end comments by ZHIGANG on 20160818
	
	public static String getRadomCode() {
		Random r = new Random(10000);
		r.setSeed(System.currentTimeMillis());
		String code = "";
		while(code.length() != 4) {
			int c = r.nextInt();
			if(c < 0) {
				continue;
			}
			code = String.valueOf(c);
		}
		return code;
	}
	
	public static int sendSWithDrawMSNotify(String cellphone, int payment) {
		List<String> myContacts = new ArrayList<String>();
		myContacts.add(cellphone);  
		int result = SMSGateWayProxy.sendMessage(myContacts, "您申请的提现金额:" + payment + " 已经打到你的账户，请注意查收!");
		return result;
	}
	
	public static int sendAuditProfileNotify(String cellphone) {
		List<String> myContacts = new ArrayList<String>();
		myContacts.add(cellphone);  
		int result = SMSGateWayProxy.sendMessage(myContacts, "您提交的企业/个人认证已经被管理员审核通过!");
		return result;
	}
	/**
	 * 
	 * @param contactList
	 *            - less than 100
	 * @param content
	 * @return
	 */
	public static int sendMessage(List<String> contactList, String content) {
		try {
			// build new HTTP Client
			HttpClient httpClient = HttpClientBuilder.create().build(); 
			HttpPost post = new HttpPost("http://utf8.sms.webchinese.cn");
			post.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf8");
			
			// Assemble form data
			StringBuffer contacts = new StringBuffer();
			int size = contactList.size();
			for(int i=0; i<size; i++) {
				contacts.append(contactList.get(i));
				if(i < (size - 1)) {
					contacts.append(",");
				}
			}
			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			urlParameters.add(new BasicNameValuePair("Uid", SMS_USERID));
			urlParameters.add(new BasicNameValuePair("Key", SMS_SECURITY_KEY));
			urlParameters.add(new BasicNameValuePair("smsMob", contacts.toString()));
			urlParameters.add(new BasicNameValuePair("smsText", content));
			// post.setEntity(new UrlEncodedFormEntity(urlParameters));
			post.setEntity(new UrlEncodedFormEntity(urlParameters, "UTF-8"));

			// submit form data
			HttpResponse httpResponse = httpClient.execute(post);
			String response = EntityUtils.toString(httpResponse.getEntity());
			System.out.println(response);
			post.releaseConnection();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	public static void main(String[] args) {

//		List<String> myContacts = new ArrayList<String>();
//		myContacts.add("13914067909"); 
//		myContacts.add("15312482037");
//		myContacts.add("13566630198");
//		SMSGateWayProxy.sendMessage(myContacts, "测试验证码:" + ":2233");
		System.out.println("code : " + getRadomCode());
	}

}
