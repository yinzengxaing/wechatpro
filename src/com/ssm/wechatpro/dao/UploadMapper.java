package com.ssm.wechatpro.dao;

import java.util.Map;

public interface UploadMapper {
	
	public int insertOption(Map<String,Object> map) throws Exception;
	
	public Map<String,Object> selectById(Map<String,Object> map) throws Exception;
}
