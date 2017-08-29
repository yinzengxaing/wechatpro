package com.ssm.wechatpro.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public interface WechatKeysMapper {
	//查询所有关键字
	public List<Map<String, Object>> selectAllKeys(Map<String,Object> map, PageBounds pageBounds) throws Exception;

	//按id查询关键字
    public Map<String, Object> selectKeybyId(Map<String, Object> map) throws Exception;
    
    //按关键字查询
    public Map<String, Object> selectKeybyWechatKey(Map<String, Object> map) throws Exception;
    
    //添加关键字
	public void addKey(Map<String, Object> map) throws Exception;
	
	//修改关键字
	public void updateKey(Map<String, Object> map) throws Exception;
	
	//删除关键字
	public void deleteKey(Map<String, Object> map) throws Exception;
}
