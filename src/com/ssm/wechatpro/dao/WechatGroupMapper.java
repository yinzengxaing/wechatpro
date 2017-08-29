package com.ssm.wechatpro.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public interface WechatGroupMapper {

	public int insertGroup(Map<String,Object> map) throws Exception;
	
	public List<Map<String,Object>> selectAll(Map<String,Object> map, PageBounds pageBounds) throws Exception;
	
	public Map<String,Object> selectById(Map<String,Object> map) throws Exception;
	
	public int selectByName(Map<String,Object> map) throws Exception;
	
	public void deleteById(Map<String,Object> map) throws Exception;
	
	public int update(Map<String,Object> map) throws Exception;
}
