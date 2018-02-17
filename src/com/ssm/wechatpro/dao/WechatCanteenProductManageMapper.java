package com.ssm.wechatpro.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

/**
 * 餐厅人员商品管理
* @author administrator
* @param 
* @throws 
 */
public interface WechatCanteenProductManageMapper {
	
	
	/**
	 * 通过登录人id获得对应的商店id
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectProductTypeByChoose (Map<String, Object> map)throws Exception;
	
	/**
	 * 根据商店与商品的绑定关系来获得商品的信息（通过类别显示）
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectProductByChoose(Map<String, Object>map) throws Exception;
	public List<Map<String, Object>> selectProductByChoose(Map<String, Object>map, PageBounds bounds) throws Exception;
	/**
	 * 根据商店与套餐的绑定关系来获得套餐的信息
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectPackageProductByChoose(Map<String, Object>map) throws Exception;
	public List<Map<String, Object>> selectPackageProductByChoose(Map<String, Object> map, PageBounds bounds) throws Exception;
	/**
	 * 根据商店与可选套餐的绑定关系来获得可选套餐的信息
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectChoosePackageByChoose(Map<String, Object>map)throws Exception;
	public List<Map<String, Object>> selectChoosePackageByChoose(Map<String, Object>map, PageBounds bounds)throws Exception;
	
	/**
	 * 查询数量
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectNum(Map<String, Object> map)throws Exception;
	/**
	 * 更新商品的总数量
	 * @param map
	 * @throws Exception
	 */
	public void updateProductNum(Map<String, Object> map) throws Exception;
	
	/**
	 * 重置商品现在的数量
	 * @param map
	 * @throws Exception
	 */
	public void updateProductNowNum(Map<String, Object> map)throws Exception;
	
	/**
	 * 更新商品的上下线
	 * @param map
	 * @throws Exception
	 */
	public void updateProductState(Map<String, Object> map) throws Exception;
	
	
	/**
	 * 表示使用现金支付的用户
	 * @param map
	 * @throws Exception
	 */
	public void updateCashProple(Map<String, Object> map )throws Exception;
	
	/**
	 * 查看已经付款但是没有送给用户的订单列表（查询一张表）
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>>selectPaidOneOrderForm(Map<String, Object> map) throws Exception;
	public List<Map<String, Object>>selectPaidOneOrderForm(Map<String, Object> map, PageBounds pageBounds) throws Exception;
	
	/**
	 * 查看已经付款但是没有送给用户的订单列表（查询两张表）
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectPaidTwoOrderForm(Map<String ,Object> map) throws Exception;
	public List<Map<String, Object>> selectPaidTwoOrderForm(Map<String ,Object> map, PageBounds pageBounds) throws Exception;
	/**
	 * 删除一周前未付款的订单
	 * @param map
	 * @throws Exception
	 */
	public void deletePreWeekInfo(Map<String, Object> map) throws Exception;

	/**
	 * 删除一周前的未付款的订单详情
	 * @param map
	 * @throws Exception
	 */
	public void deletePreWeekDetailInfo(Map<String, Object> map) throws Exception;
	/**
	 * 查看一周前未付款的订单
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectPreWeekInfo(Map<String, Object> map ) throws Exception;
	
	/**
	 * 查看订单中的基本信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectOrderFromBasic(Map<String, Object> map) throws Exception;
	
	/** 
	 * 查看订单中含有的商品
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectOrderFormDetail(Map<String, Object> map ) throws Exception;
	/**
	 * 表示已经做好给用户
	 * @param map
	 * @throws Exception
	 */
	public void updateMake(Map<String, Object> map) throws Exception;
	
	/**
	 * 表示付款完之卖出数量做出相应的变化
	 * @param map
	 * @throws Exception
	 */
	public void updateMakeAddNum(Map<String, Object> map )throws Exception;
	/**
	 * 查看商店的详细
	 * @param map
	 * @throws Exception
	 */
	public Map<String, Object> selectForShopInfo(Map<String, Object> map) throws Exception;

	/**
	 * 各个门店修改地址，营业时间等
	 * @param mapParam
	 * @throws Exception
	 */
	public void updateProductForShop(Map<String, Object> mapParam)throws Exception;
}
