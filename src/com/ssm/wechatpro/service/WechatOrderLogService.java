package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

public interface WechatOrderLogService {

	public void insertOrderLog(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void selectByUserId(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void selectByWetherPayment(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void modifyOrderLog(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void deleteOrderLog(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void selectProduct(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void selectShopName(InputObject inputObject, OutputObject outputObject) throws Exception;

}
