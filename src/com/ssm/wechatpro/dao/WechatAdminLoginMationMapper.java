package com.ssm.wechatpro.dao;

import java.util.Map;

public interface WechatAdminLoginMationMapper {
   
	public int insertLoginMation(Map<String,Object> map) throws Exception;
	
	public int updateByAdminId(Map<String,Object> map) throws Exception;
	
	public Map<String,Object> selectById(Map<String,Object> map) throws Exception;
}
