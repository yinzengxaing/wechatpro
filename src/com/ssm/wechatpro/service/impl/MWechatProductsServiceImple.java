package com.ssm.wechatpro.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssm.wechatpro.dao.MWechatProductsMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.MWechatProductsService;

@Service
public class MWechatProductsServiceImple implements MWechatProductsService {
	@Resource
	private MWechatProductsMapper mwechatProductsMapper;
	
	/**
	 * 查询该城市商品的类别信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void getTypeList(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		Map<String, Object> wechatLogParams = inputObject.getWechatLogParams();
		
		//用户还未登录
		if (wechatLogParams== null){
			List<Map<String,Object>> allType = mwechatProductsMapper.getAllType(params);
			outputObject.setBeans(allType);
		}
		//用户已经登录
		else{
			//获取用户登录地址
			params.put("adminWorkPlace", wechatLogParams.get("Location"));
			//获取该地区所有的的商店信息（商店名称和商店id）
			List<Map<String,Object>> restaurantInfo = mwechatProductsMapper.getRestaurantInfo(params);
			//若是该地区没有商店 则获取所有的商品类型
			if (restaurantInfo.size() <=0 ){
				//查询所有的商品商品类型
				List<Map<String,Object>> allType = mwechatProductsMapper.getAllType(params);
				outputObject.setBeans(allType);
			}
			//若是该地区拥有商店  则获取该商店拥有的类型
			else {
				List<Map<String,Object>> typeList = mwechatProductsMapper.getTypeList(restaurantInfo);
				outputObject.setBeans(typeList);
			}
		}
		
	}
	
	/**
	 * 查询该类型下的所有商品信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void getProductList(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		Map<String, Object> wechatLogParams = inputObject.getWechatLogParams();
		//用户还未登录
		if (wechatLogParams == null){
			List<Map<String,Object>> allProductByType = mwechatProductsMapper.getAllProductByType(params);
			outputObject.setBeans(allProductByType);
		}
		//用户已经登录
		else {
			//模拟从session中获取用户所在地址
			params.put("adminWorkPlace", wechatLogParams.get("Location"));
			//获取该地区所有的的商店信息（商店名称和商店id）
			List<Map<String,Object>> restaurantInfo = mwechatProductsMapper.getRestaurantInfo(params);
			//若是该地区没有商店 则获取所有的商品类型
			if (restaurantInfo.size() <=0 ){
				List<Map<String,Object>> allProductByType = mwechatProductsMapper.getAllProductByType(params);
				outputObject.setBeans(allProductByType);
			}
			//若是该地区拥有商店  则获取该商店拥有的类型
			else {
				//将所有的商品的商店id 通过list封装起来
				List<Object> resIdList = new ArrayList<Object>();
				for (Map<String, Object> map : restaurantInfo) {
					resIdList.add(map.get("adminId"));
				}
				params.put("adminRestaurantId", resIdList);
				List<Map<String,Object>> productByResId = mwechatProductsMapper.getProductByResId(params);
				outputObject.setBeans(productByResId);
			}		
		}
	}
	
	/**
	 * 查询所有的套餐信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void getPackageList(InputObject inputObject, OutputObject outputObject) throws Exception{
		Map<String, Object> params = inputObject.getParams();
		Map<String, Object> wechatLogParams = inputObject.getWechatLogParams();
		//用户还未登录
		if (wechatLogParams == null){
			List<Map<String,Object>> allPackage = mwechatProductsMapper.getAllPackage(params);
			outputObject.setBeans(allPackage);
		}
		//用户已经登录
		else{
			//模拟从session中获取用户所在地址
			params.put("adminWorkPlace", wechatLogParams.get("Location"));
			//获取该地区所有的的商店信息（商店名称和商店id）
			List<Map<String,Object>> restaurantInfo = mwechatProductsMapper.getRestaurantInfo(params);
			//若当前的地区没有商店时 获取全部的套餐
			if(restaurantInfo.size() <=0){
				List<Map<String,Object>> allPackage = mwechatProductsMapper.getAllPackage(params);
				outputObject.setBeans(allPackage);
			}
			//若当前地区有商店时
			else{
				//获取当前地区的这些商店的可选套餐
				List<Map<String,Object>> packByResId = mwechatProductsMapper.getPackByResId(restaurantInfo);
				outputObject.setBeans(packByResId);
			}
		}
	}

	/**
	 * 首页商品展示
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void getProductListForIndex(InputObject inputObject, OutputObject outputObject) throws Exception {
		//获取用户的登录信息 以及参数
		Map<String, Object> wechatLogParams = inputObject.getWechatLogParams();
		Map<String, Object> params = inputObject.getParams();
		//返回给页面的map
		Map<String, Object> map = new HashMap<String,Object>();
		
		//用户还没登录
		if (wechatLogParams == null){
			params.put("adminWorkPlace", "郑州");
			List<Map<String,Object>> restaurantInfo = mwechatProductsMapper.getRestaurantInfo(params);
			//获取当前城市下的4个价格最低的可选套餐
			List<Map<String,Object>> chooListByPrice = mwechatProductsMapper.getChooListByPrice(restaurantInfo);
			map.put("chooByPrice", chooListByPrice);
			//获取十个最新的套餐和可选套餐
			List<Map<String,Object>> chooByResId = mwechatProductsMapper.getChooByResId(restaurantInfo);
			List<Map<String,Object>> packByResId = mwechatProductsMapper.getPackByResId(restaurantInfo);
			
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>(); 
			for(int i=0;i<4;i++){
				Map<String, Object> thisMap = new HashMap<String,Object>();	
			//放入套餐的信息 
				thisMap.put("packId", packByResId.get(i).get("packageId"));
				thisMap.put("packLogo", packByResId.get(i).get("pacLogo"));
				thisMap.put("packName", packByResId.get(i).get("packageName"));
				thisMap.put("packPrice", packByResId.get(i).get("packagePrice"));
			//放入可选套餐的信息	
				thisMap.put("chookId", chooByResId.get(i).get("packageId"));
				thisMap.put("chooLogo", chooByResId.get(i).get("chooLogo"));
				thisMap.put("chooName", chooByResId.get(i).get("chooName"));
				thisMap.put("chooPrice", chooByResId.get(i).get("chooPrice"));
				list.add(thisMap);
			}
			map.put("newPackage", list);
			outputObject.setBean(map);
		}
		//用户已经登录
		else{
			//查询用户登录城市的信息
			params.put("adminWorkPlace", wechatLogParams.get("Location"));
			List<Map<String,Object>> restaurantInfo = mwechatProductsMapper.getRestaurantInfo(params);
			//当前城市没有商店
			if(restaurantInfo.size() <= 0){
				//获取所有的可选套餐4个价格最低的
				List<Map<String,Object>> allChooByPrice = mwechatProductsMapper.getAllChooByPrice(params);
				map.put("chooByPrice", allChooByPrice);
				//获取十个最新的套餐和可选套餐
				List<Map<String,Object>> allChooPackage = mwechatProductsMapper.getAllChooPackage(params);
				List<Map<String,Object>> allPackage = mwechatProductsMapper.getAllPackage(params);
				
				map.put("allChooPackage", allChooPackage);
				map.put("allPackage", allPackage);
				outputObject.setBean(map);
			}
			//当前城市有商品
			else {
				//获取当前城市下的4个价格最低的可选套餐
				List<Map<String,Object>> chooListByPrice = mwechatProductsMapper.getChooListByPrice(restaurantInfo);
				map.put("chooByPrice", chooListByPrice);
				//获取十个最新的套餐和可选套餐
				List<Map<String,Object>> chooByResId = mwechatProductsMapper.getChooByResId(restaurantInfo);
				List<Map<String,Object>> packByResId = mwechatProductsMapper.getPackByResId(restaurantInfo);
				
				List<Map<String, Object>> list = new ArrayList<Map<String,Object>>(); 
				for(int i=0;i<4;i++){
					Map<String, Object> thisMap = new HashMap<String,Object>();	
				//放入套餐的信息 
					thisMap.put("packId", packByResId.get(i).get("packageId"));
					thisMap.put("packLogo", packByResId.get(i).get("pacLogo"));
					thisMap.put("packName", packByResId.get(i).get("packageName"));
					thisMap.put("packPrice", packByResId.get(i).get("packagePrice"));
				//放入可选套餐的信息	
					thisMap.put("chookId", chooByResId.get(i).get("packageId"));
					thisMap.put("chooLogo", chooByResId.get(i).get("chooLogo"));
					thisMap.put("chooName", chooByResId.get(i).get("chooName"));
					thisMap.put("chooPrice", chooByResId.get(i).get("chooPrice"));
					list.add(thisMap);
				}
				map.put("newPackage", list);
				outputObject.setBean(map);
			}
		}
	}

}
