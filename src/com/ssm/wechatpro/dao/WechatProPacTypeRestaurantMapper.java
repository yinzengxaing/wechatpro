package com.ssm.wechatpro.dao;

import java.util.List;
import java.util.Map;

/**
 * 更改商品分类
 * @author yinzengxiang
 *
 */
public interface WechatProPacTypeRestaurantMapper {
	//获取所有的分类
	public List<Map<String, Object>> getRestaurantList(Map<String, Object> map) throws Exception;
	//获取所有的商品
	public List<Map<String , Object>> getProductList() throws Exception;
	//获取所有的套餐
	public List<Map<String, Object>> getPackageList()throws Exception;
	//获取所有的可选套餐
	public List<Map<String, Object>> getChoosePackageList()throws Exception;
	//获取该分类下所有已经被选择的商品
	public List<Map<String, Object>> getProductSelect(Map<String, Object> map)throws Exception;
	//获得该分类下已经被选择的套餐
	public List<Map<String,Object>> getPackageListSelect(Map<String, Object> map) throws Exception;
	//获得该分类下所有已经被选择的可选套餐
	public List<Map<String,Object>> getChoosePackageSelect(Map<String, Object> map) throws Exception;
	//更新一个分类
	public void updateRestaurant(List<Map<String, Object>> list) throws Exception;
	//查看一个商品分类创建人和创建时间
	public List<Map<String, Object>> selectRestaurantById(Map<String, Object> map)throws Exception;
	//删除一个分类选项下的商品
	public void deleteRestaurantById(Map<String, Object> map) throws Exception;
	//查询分类先各类商品的数量
	public List<Map<String, Object>> getProductCount(Map<String, Object> map) throws Exception;
	
}
