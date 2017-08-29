package com.ssm.wechatpro.service;

import javax.servlet.http.HttpServletRequest;

public interface WeChatService {

	public String WechatReply(HttpServletRequest request) throws Exception;

}
