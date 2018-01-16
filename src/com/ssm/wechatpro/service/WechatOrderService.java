package com.ssm.wechatpro.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

public interface WechatOrderService {
	
	public void getOrderParameter(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void orderRefund(InputObject inputObject,OutputObject outputObject) throws Exception;

	public void notifyPay(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	
	
}
