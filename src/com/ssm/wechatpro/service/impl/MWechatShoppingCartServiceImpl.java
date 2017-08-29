package com.ssm.wechatpro.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.ssm.wechatpro.dao.MWechatShoppingCartMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.MWechatShoppingCartService;
import com.ssm.wechatpro.util.DateUtil;


@Service
public class MWechatShoppingCartServiceImpl implements MWechatShoppingCartService {
	
	@Resource
	private MWechatShoppingCartMapper mWechatShoppingCartMapper;
	
	/**
	 * 添加一个商品到购物车中
	 */
	@Override
	public void addProduct(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		Map<String, Object> wechatLogParams = inputObject.getWechatLogParams();
		Map<String, Object> map = new HashMap<String, Object>();
		//获取登录人的id
		map.put("wechatCustomerLoginId", wechatLogParams.get("id"));
		//获取商品的id
		map.put("wechatCommodity", params.get("wechatCommodity"));
		//获取商店的id
		map.put("wechatCommodityAdminId", params.get("wechatCommodityAdminId"));
		//获取商品的类型
		map.put("wechatCommodityType", params.get("wechatCommodityType"));
		//放置商品的数量（初始状况为1）
		map.put("wechatCommodityCount", 1);
		//该商品放入购物车时间
		map.put("createTime", DateUtil.getTimeAndToString());
		//当前放置的产品是否已经存在
		List<Map<String,Object>> cartProductInfo = mWechatShoppingCartMapper.getCartProductInfo(map);
		//当前添加的商品已经存在购物车中 只需要将count++
		if (cartProductInfo.size() >0){
			mWechatShoppingCartMapper.productCountUp(map);
		}
		//当前购物车还没有这种商品
		else{
			mWechatShoppingCartMapper.addProduct(map);
		}
		
	}
	
	/**
	 * 删除购物车中的一个商品
	 */
	@Override
	public void deleteProductCount(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		Map<String, Object> wechatLogParams = inputObject.getWechatLogParams();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("wechatCustomerLoginId", wechatLogParams.get("id"));
		map.put("wechatCommodity", params.get("wechatCommodity"));
		map.put("wechatCommodityAdminId", params.get("wechatCommodityAdminId"));
		map.put("wechatCommodityType", params.get("wechatCommodityType"));
		//获取当商品的信息
		List<Map<String,Object>> cartProductInfo = mWechatShoppingCartMapper.getCartProductInfo(map);
		if (!cartProductInfo.isEmpty()){
			Map<String, Object> map2 = cartProductInfo.get(0);
			//判读当前的商品数量是否为1
			if (map2.get("wechatCommodityCount").equals(1)){ //直接删除该商品
				mWechatShoppingCartMapper.deleteProduct(map2);
			}else{
				mWechatShoppingCartMapper.productCOuntDown(map);
			}
		}else{
			outputObject.setreturnMessage("删除失败");
			return;
		}
	}
	
	
	/**
	 * 删除一种商品到购物车中
	 */
	@Override
	public void deleteProduct(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("id", params.get("id"));
		mWechatShoppingCartMapper.deleteProduct(map);
	}
	
	/**
	 * 更改商品的数量
	 */
	@Override
	public void updateProductCount(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("wechatCommodityCount",params.get("wechatCommodityCount"));
		map.put("id", params.get("id"));
	}

	/**
	 * 查看购物车
	 */
	@Override
	public void checkCart(InputObject inputObject, OutputObject outputObject) throws Exception {

		Map<String, Object> wechatLogParams = inputObject.getWechatLogParams();
		Map<String, Object> map = new HashMap<String,Object>();
		//总的list
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		//获取登录人id
		map.put("wechatCustomerLoginId", wechatLogParams.get("id"));
		//查询该登录人购物车下的所有商店
		List<Map<String,Object>> shopIds = mWechatShoppingCartMapper.getShopIds(map);
		for (Map<String, Object> map2 : shopIds) {
			//创建一个map 放置每个商店查询出来的商店信息
			Map<String, Object>   returnMap = new 	HashMap<String, Object>();
			//获取每个商店下的商品的信息
			List<Object> list = new ArrayList<Object>();
			//获取每个商店的名称
			Map<String, Object> shopName = mWechatShoppingCartMapper.getShopName(map2);
			returnMap.put("adminId", shopName.get("adminId"));
			returnMap.put("adminShopName", shopName.get("adminShopName"));
			Map<String, Object> thisMap = new HashMap<String, Object>();
			thisMap.put("wechatCommodityAdminId", map2.get("wechatCommodityAdminId"));
			thisMap.put("wechatCustomerLoginId", wechatLogParams.get("id"));
			List<Map<String,Object>> cartProductInfo = mWechatShoppingCartMapper.getCartProductInfo(thisMap);
			//获取到每个商店中的商品信息
			for (Map<String, Object> map3 : cartProductInfo) {
				if (map3.get("wechatCommodityType").equals(1)){
					Map<String, Object> productByCartInfo = mWechatShoppingCartMapper.getProductByCartInfo(map3);
					productByCartInfo.put("productType",1);
					productByCartInfo.put("count", map3.get("wechatCommodityCount"));
					productByCartInfo.put("creatTime", map3.get("createTime"));
					productByCartInfo.put("cartId", map3.get("id"));
					list.add(productByCartInfo);
				}else if (map3.get("wechatCommodityType").equals(2)){
					Map<String, Object> packageByCartInfo = mWechatShoppingCartMapper.getPackageByCartInfo(map3);
					packageByCartInfo.put("productType", 2);
					packageByCartInfo.put("count", map3.get("wechatCommodityCount"));
					packageByCartInfo.put("creatTime", map3.get("createTime"));
					packageByCartInfo.put("cartId", map3.get("id"));
					list.add(packageByCartInfo);
				}else if (map3.get("wechatCommodityType").equals(3)){
					Map<String, Object> chooPackageByCartInfo = mWechatShoppingCartMapper.getChooPackageByCartInfo(map3);
					chooPackageByCartInfo.put("count", map3.get("wechatCommodityCount"));
					chooPackageByCartInfo.put("productType", 3);
					chooPackageByCartInfo.put("creatTime", map3.get("createTime"));
					chooPackageByCartInfo.put("cartId", map3.get("id"));
					list.add(chooPackageByCartInfo);
				}
			}
				returnMap.put("productList",list);
				returnList.add(returnMap);				
		}
		if (returnList.size()<=0){
			outputObject.setreturnMessage("您还没有吧商品添加到购物车中哦~");
			return;	
		}else{
			outputObject.setBeans(returnList);
		}
		
	}

	/**
	 * 删除购物车中的所有商品
	 */
	@Override
	public void deleteCart(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> wechatLogParams = inputObject.getWechatLogParams();
		mWechatShoppingCartMapper.deleteCartAllInfo(wechatLogParams);
	}
}
