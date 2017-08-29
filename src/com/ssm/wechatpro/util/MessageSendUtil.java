package com.ssm.wechatpro.util;

import javax.servlet.http.HttpServletRequest;

import com.wechat.service.WendaJiqirenService;


public class MessageSendUtil {
	
    /**
     * 获取验证码
     */
	public static String getWord() throws Exception{
		String word  = (int)((Math.random()*9+1)*100000)+"";
	    return word;
	}
	
	/**
	 * 不是回复关键字的回复----------图灵机器人
	 * 
	 * @param fromUserName
	 * @param toUserName
	 * @return
	 */
	public static String Reback(String fromUserName, String toUserName, String content, HttpServletRequest request) throws Exception {
		// 回复文本消息
		String result = null;
		result = WendaJiqirenService.getRequest1(content);
		if (result == null) {
			return MessageUtil.textMessageToXml(WeChatPublicUtil.getTextMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_TEXT, "该机器人不支持该文字" + content + "的回复"));
		} else {
			return MessageUtil.textMessageToXml(WeChatPublicUtil.getTextMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_TEXT,"麦克思机器人小麦：" + result));
		}
	}
}
