package com.ssm.wechatpro.dao;

import java.util.List;
import java.util.Map;

public interface WechatProductRestaurantMapper {

	public int insertProduct(List<Map<String, Object>> list) throws Exception;
	//获得指定门店下的产品
	public List<Map<String, Object>> getProductListById(Map<String, Object> map) throws Exception;
	//获得指定门店下的套餐
	public List<Map<String, Object>> getPackageListById(Map<String, Object> map) throws Exception;
	//获得指定门店下的可选套餐
	public List<Map<String, Object>> getChooPackListById(Map<String, Object> map) throws Exception;
	//删除指定门店下的所有商品
	public void deleteProductById(Map<String, Object> map) throws Exception;
	//查询认证通过的门店card值和对应的key值
	public Map<String,Object> selectCardAndKey(Map<String,Object> map) throws Exception;
	//系统当时修改商品状态
	public int updateStoreState() throws Exception;
	//系统定时创建月表
	public int insertTable(Map<String,Object> map) throws Exception;
	//根据产品类别获取该门店下原有的商品的id （产品，套餐， 可选套餐）
	public List<Map<String, Object>> getProductByType(Map<String, Object> map) throws Exception;
	//根据类型和商品id 查询一个商品是否存在
	public List<Map<String, Object>> getProductById(Map<String, Object> map) throws Exception;
	//根据商店id 商品id 商品类别 删除一个商品
	public void deleteProductByPid(Map<String, Object> map) throws Exception;
	//系统定时清空购物车
	public int deleteAllCart(Map<String,Object> map) throws Exception;
	
}
