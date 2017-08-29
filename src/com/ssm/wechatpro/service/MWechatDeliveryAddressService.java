package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

public interface MWechatDeliveryAddressService {

	public void insertDeliveryAddress(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void selectByDeliveryUserId(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void deleteById(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void updateAddress(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void selectById(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void updateUse(InputObject inputObject, OutputObject outputObject) throws Exception;
}
