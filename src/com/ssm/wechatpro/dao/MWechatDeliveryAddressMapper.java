package com.ssm.wechatpro.dao;

import java.util.List;
import java.util.Map;

public interface MWechatDeliveryAddressMapper {

	public int insertDeliveryAddress(Map<String,Object> map) throws Exception;
	
	public List<Map<String,Object>> selectByDeliveryUserId(Map<String,Object> map) throws Exception;
	
	public int deleteById(Map<String,Object> map) throws Exception;
	
	public int updateAddress(Map<String,Object> map) throws Exception;
	
	public Map<String,Object> selectById(Map<String,Object> map) throws Exception;
	
	public int updateUse(Map<String,Object> map) throws Exception;
	
	public int updateNoUse(Map<String,Object> map) throws Exception;
}
