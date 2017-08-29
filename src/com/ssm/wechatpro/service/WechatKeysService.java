package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

/**
 * 关键字用户的service接口
 * @author administrator
 *
 */
public interface WechatKeysService {
	public void addWechatKey(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void selectAllKeys(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void deleteWechatKey(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void updateWechatKey(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void selectKey(InputObject inputObject, OutputObject outputObject) throws Exception;
}
