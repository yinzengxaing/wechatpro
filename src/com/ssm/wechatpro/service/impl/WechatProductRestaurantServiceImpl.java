package com.ssm.wechatpro.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.ssm.wechatpro.dao.WechatProductRestaurantMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatProductRestaurantService;
import com.ssm.wechatpro.util.Constants;
import com.ssm.wechatpro.util.DateUtil;
import com.ssm.wechatpro.util.JudgeUtil;

@Service
public class WechatProductRestaurantServiceImpl implements WechatProductRestaurantService{

	@Resource
	private WechatProductRestaurantMapper wechatProductRestaurantMapper;
	
	/**
	 * 对每个餐厅分发产品
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void insertProduct(InputObject inputObject, OutputObject outputObject) throws Exception{
		Map<String,Object> map = inputObject.getParams();
		//添加选择的商品的id
		String selectProductListStr = map.get("selectProductList").toString();
		//添加选择的套餐的id
		String selectPackageListStr = map.get("selectPackageList").toString();
		//添加选择的可选套餐的id
		String selectChoosePackageListStr = map.get("selectChoosePackageList").toString();		
		//判断添加的商品是否为都为空
		if (selectChoosePackageListStr.equals("") && selectPackageListStr.equals("") && selectProductListStr.equals("")){
			//删除原来添加的商品
			wechatProductRestaurantMapper.deleteProductById(map);
			outputObject.setreturnCode(0);
			outputObject.setreturnMessage("保存成功");
			return;
		}
		
		//添加商品
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>(); 
		if(!JudgeUtil.isNull(selectProductListStr)){
			List<String>  selectProductList = Arrays.asList(selectProductListStr.split(","));
			//删除没有选择的的产品
			Map<String, Object> pMap = new HashMap<>();
			pMap.put("productType",Constants.SHOP);
			pMap.put("adminRestaurantId", map.get("adminRestaurantId").toString());
			List<Map<String,Object>> productByType = wechatProductRestaurantMapper.getProductByType(pMap);
			for (Map<String, Object> map2 : productByType) {
				//查询当前的商店是否包含此商品 若不包含 删除该商品
				if (!selectProductList.contains(map2.get("productId").toString())){
					pMap.put("productId", map2.get("productId"));
					wechatProductRestaurantMapper.deleteProductByPid(pMap);
				}
			}
			
			//添加商品
			for(int i=0;i<selectProductList.size();i++){
				Map<String,Object> myMap = new HashMap<>();
				myMap.put("adminRestaurantId", map.get("adminRestaurantId").toString());
				myMap.put("productType",Constants.SHOP);//商品
				myMap.put("productId",selectProductList.get(i));
				List<Map<String,Object>> productListById = wechatProductRestaurantMapper.getProductById(myMap);
				if (productListById.isEmpty()){
					myMap.put("productState", Constants.PRODUCT);//产品状态默认为0，下线状态。
					myMap.put("productNum",Constants.PRODUCT);//产品数量默认为0
					myMap.put("productNowNum",Constants.PRODUCT);//产品当前数量
					list.add(myMap);
				}
			}
        }
		
		//添加套餐
		if(!JudgeUtil.isNull(selectPackageListStr)){
			List<String>  selectPackageList = Arrays.asList(selectPackageListStr.split(","));
			//删除没有选择的的产品
			Map<String, Object> pMap = new HashMap<>();
			pMap.put("productType",Constants.PACKAGE);
			pMap.put("adminRestaurantId", map.get("adminRestaurantId").toString());
			List<Map<String,Object>> productByType = wechatProductRestaurantMapper.getProductByType(pMap);
			for (Map<String, Object> map2 : productByType) {
				//查询当前的商店是否包含此商品 若不包含 删除该商品
				if (!selectPackageList.contains(map2.get("productId").toString())){
					pMap.put("productId", map2.get("productId"));
					wechatProductRestaurantMapper.deleteProductByPid(pMap);
				}
			}
			//添加套餐
			for(int i=0;i<selectPackageList.size();i++){
				Map<String,Object> myMap = new HashMap<>();
				myMap.put("adminRestaurantId", map.get("adminRestaurantId").toString());
				myMap.put("productType", Constants.PACKAGE);//常规套餐
				myMap.put("productId",selectPackageList.get(i));
				List<Map<String,Object>> productById = wechatProductRestaurantMapper.getProductById(myMap);
				if (productById.isEmpty()){
				myMap.put("productState",Constants.PRODUCT);//产品状态默认为0，下线状态。
				myMap.put("productNum", Constants.PRODUCT);//产品数量默认为0
				myMap.put("productNowNum",Constants.PRODUCT);//产品当前数量
	            list.add(myMap);
				}
			}
        }
		
		//添加可选套餐
		if(!JudgeUtil.isNull(selectChoosePackageListStr)){
			List<String>  selectChoosePackageList = Arrays.asList(selectChoosePackageListStr.split(","));
			//删除没有选择的的产品
			Map<String, Object> pMap = new HashMap<>();
			pMap.put("productType",Constants.CHOSEPACKAGE);
			pMap.put("adminRestaurantId", map.get("adminRestaurantId").toString());
			List<Map<String,Object>> productByType = wechatProductRestaurantMapper.getProductByType(pMap);
			for (Map<String, Object> map2 : productByType) {
				//查询当前的商店是否包含此商品 若不包含 删除该商品
				if (!selectChoosePackageList.contains(map2.get("productId").toString())){
					pMap.put("productId", map2.get("productId"));
					wechatProductRestaurantMapper.deleteProductByPid(pMap);
				}
			}
			for(int i=0;i<selectChoosePackageList.size();i++){
				Map<String,Object> myMap = new HashMap<>();
				myMap.put("adminRestaurantId", map.get("adminRestaurantId").toString());
				myMap.put("productType", Constants.CHOSEPACKAGE);//可选套餐
				myMap.put("productId",selectChoosePackageList.get(i));
				List<Map<String,Object>> productById = wechatProductRestaurantMapper.getProductById(myMap);
				if (productById.isEmpty()){
					myMap.put("productState", Constants.PRODUCT);//产品状态默认为0，下线状态。
					myMap.put("productNum", Constants.PRODUCT);//产品数量默认为0
					myMap.put("productNowNum",Constants.PRODUCT);//产品当前数量
		            list.add(myMap);					
				}
			}
        }
		if (!list.isEmpty()){
			wechatProductRestaurantMapper.insertProduct(list);	
		}
	}

	@Override
	public void getProduct(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		//获取产品,套餐,可选套餐
		List<Map<String,Object>> productList = wechatProductRestaurantMapper.getProductListById(params);
		HashSet<Object> set = new HashSet<>();
		for (Map<String, Object> map : productList) {
			set.add(map.put("typeName", map.get("typeName")));
		}
		List<Map<String, Object>> retrunList = new ArrayList<>();
		for (Object object : set) {
			Map<String, Object> retrunMap = new HashMap<>();
			retrunMap.put("typeName", object);
			List<Map<String, Object>> list =  new ArrayList<>();
			
			for  (Map<String, Object> map : productList) {
				if (map.get("typeName").equals(object)){
					list.add(map);
				}
			}
			retrunMap.put("list", list);
			retrunList.add(retrunMap);
		}
		
		
		List<Map<String,Object>> packageList = wechatProductRestaurantMapper.getPackageListById(params);
		List<Map<String, Object>> chooPackList = wechatProductRestaurantMapper.getChooPackListById(params);
		//判断该商店里是否拥有商品
		if (productList.size()<=0 && packageList.size()<=0 && chooPackList.size()<=0){
			outputObject.setreturnMessage("该商店下还没有商品！");
			return ;
		}
		//添加商品到map中
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("productList", productList);
		map.put("packageList", packageList);
		map.put("chooPackList", chooPackList);
		outputObject.setBean(map);
		outputObject.setBeans(retrunList);
	}

	/**
	 * 每晚凌晨系统定时修改商品
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	*/
	@Override
	public void updateState() throws Exception {
		wechatProductRestaurantMapper.updateStoreState();
	}

	/**
	 * 系统定时创建月表
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	*/
	@Override
	public void insertTable() throws Exception {
		Map<String,Object> map = new HashMap<>();
		
		//wechat_admin_login_log_（登录日志表）
		map.put("tableName", Constants.LOGIN_TABLE+DateUtil.getNextTime());
		map.put("table", "wechat_admin_login_log");
		wechatProductRestaurantMapper.insertTable(map);
		
		//wechat_phone_message_log_（验证码发送短信表）
		map.put("tableName", Constants.MONTH_TABLE+DateUtil.getNextTime());
		map.put("table", "wechat_phone_message_log");
		wechatProductRestaurantMapper.insertTable(map);
		
		//m_wechat_login_score_log(积分表)
		map.put("tableName", Constants.SCORE_TABLE+DateUtil.getNextTime());
		map.put("table", "m_wechat_login_score_log");
		wechatProductRestaurantMapper.insertTable(map);
		
		//wechat_customer_order_log(订单表)
		map.put("tableName", Constants.ORDER_TABLE+DateUtil.getNextTime());
		map.put("table", "wechat_customer_order_log");
		wechatProductRestaurantMapper.insertTable(map);
		
		//wechat_customer_order_shopping_log(订单中商品的表)
		map.put("tableName", Constants.SHOP_TABLE+DateUtil.getNextTime());
		map.put("table", "wechat_customer_order_shopping_log");
		wechatProductRestaurantMapper.insertTable(map);	
	}

	/**
	 * 系统定时清空购物车
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	*/
	@Override
	public void deleteAllCart() throws Exception {
		Map<String,Object> map = new HashMap<>();
		wechatProductRestaurantMapper.deleteAllCart(map);//清空数据库
	}
	
}
