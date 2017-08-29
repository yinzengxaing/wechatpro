package com.ssm.wechatpro.dao;

import java.util.Map;

public interface WechatAdminLoginLogMapper {

 	public int insertLoginLog(Map<String,Object> map) throws Exception;
 	
 	public int selectFailCount(Map<String,Object> map) throws Exception;
}
