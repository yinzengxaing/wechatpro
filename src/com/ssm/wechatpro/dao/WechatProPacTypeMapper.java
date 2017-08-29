package com.ssm.wechatpro.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

/**
 * 商品分类的mapper
 * @author yinzengxiang
 *
 */
public interface WechatProPacTypeMapper {
	
	public List<Map<String, Object>> getPacTypeList(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> getPacTypeList(Map<String, Object> map , PageBounds bounds) throws Exception;
	
	public Map<String, Object> getPacTypeById(Map<String, Object> map) throws Exception;
	
	public Map<String, Object> getPacTypeByName(Map<String,Object> map) throws Exception;
	
	public void addPacType(Map<String, Object> map)  throws Exception;
	
	public void deletePacType(Map<String, Object> map) throws Exception;
	
	public void updatePacType(Map<String, Object> map) throws Exception;
	
		
	
}
