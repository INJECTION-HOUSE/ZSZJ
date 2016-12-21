package com.santi.core.common.messages;

import javax.annotation.Resource;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * 消息处理类
 * @author hl
 */
@Service
public class Message {

//	private static final Logger log = LoggerFactory.getLogger(Message.class);
	
	@Resource
	MessageSource messageSource;
	
	/**
	 * 消息类，比如suffix为"{aa}" prefix为["{bb}","{cc}"]
	 * aa={0}不能为{1}
	 * bb=日期
	 * cc=空
	 * 返回  "日期不能为空"
	 * @param suffix
	 * @param prefix
	 * @return
	 */
	public String getMessage(String suffix,String... prefix){
		Assert.hasText(suffix);
		Assert.notEmpty(prefix);
		String[] prefixStr = new String[prefix.length];
		for(int i=0;i<prefix.length;i++){
			prefixStr[i] = messageSource.getMessage(prefix[i], null, "", null);
		}
		return messageSource.getMessage(suffix, prefixStr, null);
	}
	
	public String getMessage(String msg){
		Assert.hasText(msg);
		return messageSource.getMessage(msg, null, null);
	}
	
}
