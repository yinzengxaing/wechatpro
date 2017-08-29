package com.ssm.wechatpro.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssm.wechatpro.dao.WechatProPacTypeRestaurantMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatProPacTypeRestaurantService;
import com.ssm.wechatpro.util.DateUtil;
import com.ssm.wechatpro.util.JudgeUtil;
@Service
public class WechatProPacTypeRestaurantServiceImpl implements WechatProPacTypeRestaurantService {

	@Resource 
	private WechatProPacTypeRestaurantMapper wechatProPacTypeRestaurantMapper;
	
	/**
	 * 查询所有的分类列表
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void getRestaurantList(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		List<Map<String,Object>> restaurantList = wechatProPacTypeRestaurantMapper.getRestaurantList(params);
		if (restaurantList.size()>0){
			for (int i = 0 ;i<restaurantList.size();i++){
				Map<String, Object> map = new HashMap<String,Object>();
				Map<String, Object> thisMap = restaurantList.get(i);
				map.put("proPacTypeId",thisMap.get("id"));
				//获取各类产品的的数量
				 List<Map<String, Object>> productCountList = wechatProPacTypeRestaurantMapper.getProductCount(map);
				//将查询到的商品类型中的数量添加到thisMap中
				//放入商品的种类
				 thisMap.put("proCount", 0);
				 thisMap.put("packCount", 0);
				 thisMap.put("chooCount", 0);
				 for (int j = 0;j< productCountList.size();j++ ){
					 if (productCountList.get(j).get("proPacType").toString().equals("1")){
						 thisMap.put("proCount", productCountList.get(j).get("proCount"));
					 }else if (productCountList.get(j).get("proPacType").toString().equals("2")){
						 thisMap.put("packCount", productCountList.get(j).get("proCount"));
					 }else if(productCountList.get(j).get("proPacType").toString().equals("3")){
						 thisMap.put("chooCount", productCountList.get(j).get("proCount"));
					 }
				 }
			}
		}
		outputObject.setBeans(restaurantList);
	}
	/**
	 * 查询所有的商品列表
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void getProductList(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		List<Map<String,Object>> productList = wechatProPacTypeRestaurantMapper.getProductList();
		
		List<Map<String, Object> > returnList  =  new ArrayList<Map<String,Object>>();
		
		HashSet<Object> set = new HashSet<>();
		
		for (Map<String, Object> map : productList) {
			set.add(map.get("typeName"));
		}
		for (Object object : set) {
			Map<String, Object> retrunMap = new HashMap<>();
			retrunMap.put("typeName", object);
			List<Map<String, Object>> list = new ArrayList<>();
			for (Map<String, Object> map: productList) {
				if (map.get("typeName").equals(object)){
					list.add(map);
				}
			}
			retrunMap.put("lsit", list);
			returnList.add(retrunMap);
		}
		
		List<Map<String,Object>> packageList = wechatProPacTypeRestaurantMapper.getPackageList();
		List<Map<String,Object>> choosePackageList = wechatProPacTypeRestaurantMapper.getChoosePackageList();
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("productList", productList);
		map.put("packageList", packageList);
		map.put("choosePackageList", choosePackageList);
		List<Map<String,Object>> productSelect = wechatProPacTypeRestaurantMapper.getProductSelect(params);
		List<Map<String,Object>> packageListSelect = wechatProPacTypeRestaurantMapper.getPackageListSelect(params);
		List<Map<String,Object>> choosePackageSelect = wechatProPacTypeRestaurantMapper.getChoosePackageSelect(params);
		map.put("productSelect", productSelect);
		map.put("packageListSelect", packageListSelect);
		map.put("choosePackageSelect", choosePackageSelect);
		outputObject.setBean(map);
		outputObject.setBeans(returnList);
	}
	/**
	 * 修改一个商品分类信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateRestaurant(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		Map<String, Object> logParams = inputObject.getLogParams();
		//删除原有的类别下所包含的商品信息
		wechatProPacTypeRestaurantMapper.deleteRestaurantById(params);
		//添加选择的商品的id
		String selectProductListStr = params.get("selectProductList").toString();
		//添加选择的套餐的id
		String selectPackageListStr = params.get("selectPackageList").toString();
		//添加选择的可选套餐的id
		String selectChoosePackageListStr = params.get("selectChoosePackageList").toString();
		//初始化分类数量
		Map<String, Object> thisMap = new HashMap<String,Object>();
		thisMap.put("proCount", 0);
		thisMap.put("packCount", 0);
		thisMap.put("chooCount", 0);
		if(JudgeUtil.isNull(selectProductListStr) && JudgeUtil.isNull(selectPackageListStr) && JudgeUtil.isNull(selectChoosePackageListStr) ){
			outputObject.setBean(thisMap);
			outputObject.setreturnCode(0);
			outputObject.setreturnMessage("保存成功");
			return ;
		}
		//存入数据
		 List<Map<String, Object>> list = new ArrayList<Map<String,Object>>(); 
		if (!JudgeUtil.isNull(selectProductListStr)){
			//根据逗号截取字符串为list
			 List<String>  selectProductList = Arrays.asList(selectProductListStr.split(","));
			for (int i = 0 ;i <selectProductList.size() ; i++){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("proPacTypeId", params.get("proPacTypeId")); //添加的商品类别id
				map.put("proPacId",selectProductList.get(i)); //商品id
				map.put("proPacType", 1);//添加的为商品类型
				map.put("createId", logParams.get("id"));
				map.put("createTime", DateUtil.getTimeAndToString());
				list.add(map);
			}
		}
		if (!JudgeUtil.isNull(selectPackageListStr)){
			//根据逗号截取字符串为list
			 List<String>  selectPackageList = Arrays.asList(selectPackageListStr.split(","));
			for (int i = 0 ;i <selectPackageList.size() ; i++){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("proPacTypeId", params.get("proPacTypeId")); //添加的商品类别id
				map.put("proPacId",selectPackageList.get(i)); //商品id
				map.put("proPacType", 2);//添加的为商品类型
				map.put("createId", logParams.get("id"));
				map.put("createTime", DateUtil.getTimeAndToString());
				list.add(map);
			}
		}
		if (!JudgeUtil.isNull(selectChoosePackageListStr)){
			//根据逗号截取字符串为list
			 List<String>  selectChooseProductList = Arrays.asList(selectChoosePackageListStr.split(","));
			for (int i = 0 ;i <selectChooseProductList.size() ; i++){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("proPacTypeId", params.get("proPacTypeId")); //添加的商品类别id
				map.put("proPacId",selectChooseProductList.get(i)); //商品id
				map.put("proPacType", 3);//添加的为商品类型
				map.put("createId", logParams.get("id"));
				map.put("createTime", DateUtil.getTimeAndToString());
				list.add(map);
			}
		}
		wechatProPacTypeRestaurantMapper.updateRestaurant(list);
		//放入商品的种类
		List<Map<String,Object>> productCountList = wechatProPacTypeRestaurantMapper.getProductCount(params);
		if (productCountList.size()>0){
			 for (int j = 0;j< productCountList.size();j++ ){
				 if (productCountList.get(j).get("proPacType").toString().equals("1")){
					 thisMap.put("proCount", productCountList.get(j).get("proCount"));
				 }else if (productCountList.get(j).get("proPacType").toString().equals("2")){
					 thisMap.put("packCount", productCountList.get(j).get("proCount"));
				 }else if(productCountList.get(j).get("proPacType").toString().equals("3")){
					 thisMap.put("chooCount", productCountList.get(j).get("proCount"));
				 }
			 }
		}
		outputObject.setBean(thisMap);
	}
	/**
	 * 查看该商品分类下的所有商品 并显示其创建时间 
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void getRestaurantById(InputObject inputObject, OutputObject outputObject) throws Exception {
		//获取当前分类创建者的id和创建时间 
		Map<String, Object> params = inputObject.getParams();
		List<Map<String,Object>> list = wechatProPacTypeRestaurantMapper.selectRestaurantById(params);
		if (list.size() <= 0){
			outputObject.setreturnMessage("当前分类下还没有商品！");
			return ;
		}
		List<Map<String,Object>> productSelect = wechatProPacTypeRestaurantMapper.getProductSelect(params);
		List<Map<String,Object>> packageListSelect = wechatProPacTypeRestaurantMapper.getPackageListSelect(params);
		List<Map<String,Object>> choosePackageSelect = wechatProPacTypeRestaurantMapper.getChoosePackageSelect(params);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("productSelect", productSelect);
		map.put("packageListSelect", packageListSelect);
		map.put("choosePackageSelect", choosePackageSelect);
		map.put("createTime", list.get(0).get("createTime"));
		map.put("createId", list.get(0).get("createId"));
		outputObject.setBean(map);
	}
}
