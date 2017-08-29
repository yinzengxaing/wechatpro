package com.ssm.wechatpro.dao;

import java.util.Map;

public interface WechatPhoneMessageLogMapper {
	
	public int insertPhoneMessage(Map<String,Object> map) throws Exception;
	
	public int selectCount(Map<String,Object> map) throws Exception;

}
