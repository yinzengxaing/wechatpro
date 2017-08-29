package com.ssm.wechatpro.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public interface WechatProductBrandTagMapper {
	
	public List<Map<String, Object>> getProductBrandTagList(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> getProductBrandTagList(Map<String, Object> map,PageBounds bounds) throws Exception;
	
	public Map<String, Object> getProductBrandTagById(Map<String, Object> map)throws Exception ;
	
	public Map<String, Object> getProductBrandTagByName(Map<String, Object> map) throws Exception;
	
	public void addProductBrandTag(Map<String, Object> map) throws Exception ;
	
	public void updateProductBrandTag(Map<String, Object> map) throws Exception;
}
