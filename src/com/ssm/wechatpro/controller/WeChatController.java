package com.ssm.wechatpro.controller;

import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ssm.wechatpro.service.WeChatService;
import com.ssm.wechatpro.util.SignUtil;

@Controller
public class WeChatController {

	@Resource
	private WeChatService weChatService;

	/**
	 * 微信提交接收
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("wechat/WechatPost")
	public void WechatPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/**
		 * 1.确认请求来自微信服务器
		 */
		String signature = request.getParameter("signature");// 微信加密签名
		String timestamp = request.getParameter("timestamp");// 时间戳
		String nonce = request.getParameter("nonce");// 随机数
		String echostr = request.getParameter("echostr");// 随机字符串
		PrintWriter out = response.getWriter();
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if (SignUtil.checkSignature(signature, timestamp, nonce))
			out.print(echostr);
		/**
		 * 2.处理微信服务器发来的消息
		 */
		request.setCharacterEncoding("UTF-8");// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		response.setCharacterEncoding("UTF-8");
		String respMessage = weChatService.WechatReply(request);// 调用核心业务类接收消息、处理消息
		// 响应消息
		PrintWriter out1 = response.getWriter();
		out1.print(respMessage);
		out1.close();
	}

}
