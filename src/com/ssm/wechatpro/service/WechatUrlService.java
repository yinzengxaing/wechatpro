package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

public interface WechatUrlService {

	public void getUrlList(InputObject inputObject,OutputObject outputObject) throws Exception;
}
