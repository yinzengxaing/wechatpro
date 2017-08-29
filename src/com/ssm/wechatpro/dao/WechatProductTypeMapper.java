package com.ssm.wechatpro.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
/**
 * 商品类型的dao操作
 * @author administrator
 *
 */
public interface WechatProductTypeMapper {

	//获取所有商品类型(分页使用)
	public List<Map<String, Object>> getProductTypeList(Map<String, Object> map, PageBounds pageBounds) throws Exception;
	
	//获取所有的商品类型(普通使用)
	public List<Map<String, Object>> getProductTypeList(Map<String, Object> map) throws Exception;
	
	//根据id获取一个商品类型
	public Map<String, Object> getProductTypeById(Map<String, Object> map) throws Exception;
	
	//根据typeName获取一个商品类型
	public Map<String, Object> getProductTypeByName(Map<String, Object> map) throws Exception;
	
	//添加一个商品类型
	public void addProductType(Map<String, Object> map) throws Exception;
	
	//修改一个商品类型
	public void upateProductType(Map<String, Object > map) throws Exception;
	
	//查询所有商品的种类，并按照id递增顺序排列 
	public List<Map<String, Object>> selectProductType() throws Exception;
 }
