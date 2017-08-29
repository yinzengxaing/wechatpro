package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

public interface WechatPhoneMessageLogService {

	public void insertPhoneMessage(InputObject inputObject, OutputObject outputObject) throws Exception;
}
