package com.ssm.wechatpro.dao;

import java.util.List;
import java.util.Map;

public interface MWechatLoginUserMapper {

	public int insertPhoneLogin(Map<String,Object> map) throws Exception;
	
	public Map<String,Object> selectByLoginPhone(Map<String,Object> map) throws Exception;
	
	public Map<String,Object> selectLoginPassword(Map<String,Object> map) throws Exception;
	
	public int updatePasswordAndType(Map<String,Object> map) throws Exception;
	
	public int updatePassword(Map<String,Object> map) throws Exception;
	
	public List<Map<String,Object>> selectAll(Map<String,Object> map) throws Exception;
	
	public Map<String,Object> selectPhoneAndScore(Map<String,Object> map) throws Exception;
}
