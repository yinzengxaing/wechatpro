package com.ssm.wechatpro.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssm.wechatpro.dao.MWechatProductMapper;
import com.ssm.wechatpro.dao.MWechatShoppingCartMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.MWechatProductService;
import com.ssm.wechatpro.util.DateUtil;
import com.ssm.wechatpro.util.JudgeUtil;
/**
 * 手机端查看产品的service 实现类
 *
 * @author 殷曾祥
 * 2017-7-31  上午8:44:41
 */
@Service
public class MWechatProductServiceImpl implements MWechatProductService {
	
	@Resource
	private MWechatProductMapper mWechatProductMapper;
	
	@Resource
	private MWechatShoppingCartMapper mWechatShoppingCartMapper;
	
	private static String SHOPPING_NAME = "wechat_customer_order_shopping_log_";
	//获得该地区的所有的商店
	@Override
	public void getAllRestaurant(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> wechatLogParams = inputObject.getWechatLogParams();
		Map<String, Object> params = inputObject.getParams();
		Map<String, Object> resParams = new HashMap<String, Object>();
		if (!params.get("city").equals("undefined")){
			resParams.put("adminWorkPlace",params.get("city"));	
		}else{
			resParams.put("adminWorkPlace",wechatLogParams.get("Location"));
		}
		Map<String, Object> returnMap = new HashMap<String,Object>();
		List<Map<String,Object>> allRestaurant = mWechatProductMapper.getAllRestaurant(resParams);
		if (allRestaurant.size() <= 0){
			outputObject.setreturnMessage("您所在的地区没有餐厅，请更换地区后再试~");
			returnMap.put("city", resParams.get("adminWorkPlace"));
			outputObject.setBean(returnMap);
			return;
		}else{
			returnMap.put("allRestaurant", allRestaurant);
			returnMap.put("city", resParams.get("adminWorkPlace"));
			outputObject.setBean(returnMap);
		}
	}

	//获得该商店的所有的类别
	@Override
	public void getAllType(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		List<Map<String,Object>> allType = mWechatProductMapper.getAllType(params);
		List<Map<String, Object>> returnList = new ArrayList<>();
		for (Map<String, Object> map2 : allType) {
			Map<String, Object> map = new HashMap<>();
			map.put("adminId", params.get("adminId"));
			map.put("typeId", map2.get("typeId"));
			map.put("NowTime", DateUtil.getNowTime());
			List<Map<String,Object>> productListByType = mWechatProductMapper.getProductListByType(map);
			if (!productListByType.isEmpty()){
				returnList.add(map2);
			}
		}
		outputObject.setBeans(returnList);
	}

	//获得一个类别下的所有商品
	@Override
	public void getProductListByType(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> wechatLogParams = inputObject.getWechatLogParams();
		Map<String, Object> params = inputObject.getParams();
		params.put("NowTime", DateUtil.getNowTime());
		//查询该类的所有商品
		List<Map<String,Object>> productListByType = mWechatProductMapper.getProductListByType(params);
		for (Map<String, Object> map : productListByType) {
				Map<String, Object> cartParam = new HashMap<>();
				cartParam.put("wechatCustomerLoginId",wechatLogParams.get("id"));
				cartParam.put("wechatCommodity", map.get("productId"));
				cartParam.put("wechatCommodityAdminId", map.get("adminId"));
				cartParam.put("wechatCommodityType", "1");
				//查询当前商品是否在购物车中
				List<Map<String,Object>> cartProductInfo = mWechatShoppingCartMapper.getCartProductInfo(cartParam);
				if (!cartProductInfo.isEmpty()){
					map.put("productCount", cartProductInfo.get(0).get("wechatCommodityCount"));
				}else{
					map.put("productCount", 0);
				}
				map.put("productType", 1);
			}
	
			outputObject.setBeans(productListByType);
	}

	//获得该商店的所有的套餐
	@Override
	public void getAllPackageList(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		List<Map<String,Object>> allPackageList = mWechatProductMapper.getAllPackageList(params);
		if (allPackageList.size() <=0 ){
			outputObject.setreturnMessage("当前商店没有套餐");
			return ;
		}else{
			for (Map<String, Object> map : allPackageList) {
				map.put("productType", 2);
			}
			outputObject.setBeans(allPackageList);
		}
	}
	
	/**
	 * 获取所有的可选套餐
	 */
	@Override
	public void getAllChoosePackageList(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		List<Map<String,Object>> allChoosePackageList = mWechatProductMapper.getAllChoosePackageList(params);
		if (allChoosePackageList.size() <=0){
			outputObject.setreturnMessage("当前商店没有可选套餐");
			return;
		}else {
			for (Map<String, Object> map : allChoosePackageList) {
				map.put("productType", 3);
			}
			outputObject.setBeans(allChoosePackageList);
		}
	}

	/**
	 * 获取首页显示的商品信息
	 */
	@Override
	public void getIndexProductList(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> logParams = inputObject.getWechatLogParams();
		Map<String, Object> map = new HashMap<>();
		String adminId = "";
		map.put("adminWorkPlace", logParams.get("Location"));
		//获取该地区中的所有的餐厅
		List<Map<String,Object>> allRestaurant = mWechatProductMapper.getAllRestaurant(map);
		if (allRestaurant.isEmpty()) { // 当前地区没有餐厅 获取郑州的第一家餐厅
			map.put("adminWorkPlace", "郑州市");
			List<Map<String, Object>> allRestaurant2 = mWechatProductMapper.getAllRestaurant(map);
			if (!allRestaurant2.isEmpty()) {
				Map<String, Object> map2 = allRestaurant2.get(0);
				adminId = map2.get("adminId").toString();
			} else {
				adminId = "";
			}
		} else {
			Map<String, Object> map2 = allRestaurant.get(0);
			adminId = map2.get("adminId").toString();
		}
		Map<String, Object> returnMap = new HashMap<>();
		
		if (!JudgeUtil.isNull(adminId)){
			Map<String, Object> params = new HashMap<>();
			params.put("adminId", adminId);
			//按照价格选取三个
			List<Map<String,Object>> packageByPrice = mWechatProductMapper.getPackageByPrice(params);
			//选取套餐 和 可选套餐
			//List<Map<String,Object>> allChoosePackageList = mWechatProductMapper.getAllChoosePackageList(params);
			List<Map<String,Object>> allPackageList = mWechatProductMapper.getAllPackageList(params);
			if(!packageByPrice.isEmpty() && !allPackageList.isEmpty() && packageByPrice.size()>=4 && allPackageList.size()>=8 ){
				
				List<Map<String, Object>> list = new ArrayList<Map<String,Object>>(); 
				for(int i=0;i<8;i=i+2){
					Map<String, Object> thisMap = new HashMap<String,Object>();	
					//放入套餐的信息 
					thisMap.put("adminId",allPackageList.get(i).get("adminId"));
					thisMap.put("packId", allPackageList.get(i).get("productId"));
					thisMap.put("packLogo", allPackageList.get(i).get("productLogo"));
					thisMap.put("packName", allPackageList.get(i).get("productName"));
					thisMap.put("packPrice", allPackageList.get(i).get("productPrice"));
					//放入可选套餐的信息	
					thisMap.put("adminId",allPackageList.get(i+1).get("adminId"));
					thisMap.put("chookId", allPackageList.get(i+1).get("productId"));
					thisMap.put("chooLogo", allPackageList.get(i+1).get("productLogo"));
					thisMap.put("chooName", allPackageList.get(i+1).get("productName"));
					thisMap.put("chooPrice", allPackageList.get(i+1).get("productPrice"));
					list.add(thisMap);
				}
				returnMap.put("allPackageList", packageByPrice);
				returnMap.put("newPackage",list);
			}
		}
		outputObject.setBean(returnMap);
		
		
	}

	/**
	 * 获取商品详情
	 */
	@Override
	public void getProductDetail(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		String shopping_log =SHOPPING_NAME + DateUtil.getTimeSixAndToString();//拼接数据表
		
		params.put("shopping_log", shopping_log);
		Map<String, Object> productDetail = mWechatProductMapper.getProductDetail(params);
		Map<String, Object> countByMonth = mWechatProductMapper.getCountByMonth(params);
		if (countByMonth == null){
			productDetail.put("monthNumber",0);
		}else{
			
			productDetail.put("monthNumber",countByMonth.get("monthNumber"));
		}
		outputObject.setBean(productDetail);
	}
	
	/**
	 * 获取当前购物车 中的详情
	 */
	public void getCartDetail(InputObject inputObject, OutputObject outputObject )throws Exception{
		Map<String, Object> params = inputObject.getParams();
		Map<String, Object> cartParmas = new HashMap<>();
		Map<String, Object> returnMap = new HashMap<>();
		cartParmas.put("wechatCustomerLoginId", inputObject.getWechatLogParams().get("id"));
		cartParmas.put("wechatCommodityAdminId",params.get("adminId"));
		cartParmas.put("wechatCommodityType", "1");
		List<Map<String,Object>> cartProductInfo = mWechatShoppingCartMapper.getCartProductInfo(cartParmas);
		if (cartProductInfo.isEmpty()){
			returnMap.put("totalCount", "0");
		}else{
			int totalCount = 0;
			double totalPrice = 0.0;
			for (Map<String, Object> map : cartProductInfo) {
				int count = Integer.parseInt(map.get("wechatCommodityCount").toString());
				totalCount = totalCount + count;
				//查询当前商品的价格
				Map<String, Object> thisMap = new HashMap<>();
				thisMap.put("productType", 1);
				thisMap.put("productId", map.get("wechatCommodity"));
				Map<String, Object> productDetail = mWechatProductMapper.getProductDetail(thisMap);
				  BigDecimal price = new BigDecimal(productDetail.get("productPrice").toString());
				  BigDecimal thiscount = new BigDecimal(map.get("wechatCommodityCount").toString());
				  double doubleValue = price.multiply(thiscount).doubleValue();
				  BigDecimal thisPirce = new BigDecimal(doubleValue+"");
				  BigDecimal thistotalPrice = new BigDecimal(totalPrice+"");
				  
				  totalPrice = thisPirce.add(thistotalPrice).doubleValue();

				map.put("productName", productDetail.get("productName"));
				map.put("productPrice", productDetail.get("productPrice"));
			}
			returnMap.put("totalCount", totalCount);
			returnMap.put("totalPrice", totalPrice);
			returnMap.put("productList",cartProductInfo);
		}
		outputObject.setBean(returnMap);
		
	}
}
