package com.ssm.wechatpro.service;

import java.util.Map;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

/**
 * 公众号关注用户的service接口
 * @author administrator
 *
 */
public interface WechatUserService {
	public void selectAllWechatUser(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void insertWechatUser(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void subscribe(String openid) throws Exception;
	
	public void unsubscribe(String openid) throws Exception;
	
	public void updateWechatUserLocation(Map<String,Object> map) throws Exception;
	
	public void selectLatitudeAndLongtitude(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void updateWechatUserMassage(Map<String, Object> map) throws Exception;

	public Map<String, Object> selectLation(Map<String, Object> map)throws Exception;
}
