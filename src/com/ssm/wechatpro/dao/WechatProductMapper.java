package com.ssm.wechatpro.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public interface WechatProductMapper {

	public List<Map<String, Object>> getProductList(Map<String, Object> map) throws Exception;
	//获取分页的商品信息
	public List<Map<String, Object>> getProductList(Map<String, Object> map,PageBounds bounds ) throws Exception;
	
	public Map<String, Object> getProductById(Map<String, Object> map) throws Exception;
	
	public Map<String, Object> getProductByName(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> selectProductType(Map<String, Object> map) throws Exception;
	
	public void addProduct(Map<String, Object> map) throws Exception;
	
	public void updateProduct(Map<String, Object> map) throws Exception;
	
	public Map<String,Object> getPackageCountByProductId(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> getPackageByProductId(Map<String, Object> map) throws Exception;
	
	//查询所有的商品类别
	public List<Map<String, Object>> getProductTypeList() throws Exception;
	
}
