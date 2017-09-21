package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

/**
 * 创建菜单的service
 *
 * @author 殷曾祥
 * 2017-9-20  下午7:53:54
 */

public interface WechatProductMenuService {

	public void getTypeList(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void getProductListByTypeId(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	

}
