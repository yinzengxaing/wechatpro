package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

public interface WechatOutRefundService {
	
	/**
	 * 客户进行申请退款
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void applyForRefund(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	/**
	 * 用户处理退款
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void dealWithRefund(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	/**
	 * 获取申请退款的列表
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void getRefundList(InputObject inputObject ,OutputObject outputObject) throws Exception;
	
}
