package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

public interface WechatOrderPrintService {
	
	/**
	 * 返回订单中包含的信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public void selectOrder(InputObject inputObject, OutputObject outputObject)throws Exception;
	
	/**
	 * 查询指定时间到当前时间之间的信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void selectTimeQuantumOrderInfo(InputObject inputObject, OutputObject outputObject)throws Exception;
}
