package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

public interface WechatMapService {

	void queryAllCityByABC(InputObject inputObject, OutputObject outputObject)throws Exception;

}
