package com.ssm.wechatpro.service.impl;

import io.goeasy.GoEasy;
import io.goeasy.publish.GoEasyError;
import io.goeasy.publish.PublishListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.ssm.wechatpro.dao.MWechatCustomerOrderMapper;
import com.ssm.wechatpro.dao.MWechatShoppingCartMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.MWechatCustomerOrderService;
import com.ssm.wechatpro.service.WechatOrderService;
import com.ssm.wechatpro.util.DateUtil;
import com.ssm.wechatpro.util.JudgeUtil;
/**
 * 关于订单的serviceImpl
 *
 * @author 殷曾祥
 * 2017-7-22 下午3:19:42
 */

@Service
public class MWechatCustomerOrderServiceImpl implements MWechatCustomerOrderService {
	
	@Resource
	private MWechatCustomerOrderMapper mWechatCustomerOrderMapper;
	
	@Resource
	private MWechatShoppingCartMapper mWechatShoppingCartMapper;
	
	@Resource
	private WechatOrderService wechatOrderService;
	
	private static String ORDER_NAME ="wechat_customer_order_log_"; 
	private static String SHOPPING_NAME = "wechat_customer_order_shopping_log_";
	String order_log = null; // 表示数据表名(订单表)
	String shopping_log = null;//表示产品信息表
	
	public MWechatCustomerOrderServiceImpl() {
		order_log = ORDER_NAME + DateUtil.getTimeSixAndToString();// 拼接数据表名
		shopping_log =SHOPPING_NAME + DateUtil.getTimeSixAndToString();//拼接数据表
	}
	
	//添加一个订单
	@SuppressWarnings("static-access")
	@Override
	public void addOrder(InputObject inputObject, OutputObject outputObject)throws Exception {
		Map<String, Object> wechatLogParams = inputObject.getWechatLogParams();
		Map<String, Object> params = inputObject.getParams();
		params.put("id", wechatLogParams.get("id").toString());
		//外卖
		if (params.get("orderType").equals("5") && params.get("eatTime").toString().equals("undefined")){
			outputObject.setreturnMessage("收货地址不能为空");
			return;
		}
		//订单号 订单总金额  openId 商家id
		String orderNumber = "";//订单编号
		double totalPrice =0.0;//总价格
		String openid = wechatLogParams.get("openid").toString();
		String adminId_ = "";//商店id，从购物车查出
		String orderId="";//订单id
		
		//查询购物车中所有产品(用户id)
		List<Map<String,Object>> beans = mWechatShoppingCartMapper.selectAllProduct(params);
				
		//生成订单编号
		String str1 = DateUtil.getUUID();//随机生成的6位数
		String str2 = String.format("%06d",Integer.parseInt(params.get("adminId").toString()));//餐厅id前导0
		String str3 = DateUtil.getTimeToString(); //当月时间 20170722
		String str4 = DateUtil.getUUID();;//随机生成的6位数
		orderNumber = str1+str2+str3+str4;//拼接订单号
		//计算订单总价格
		for (Map<String, Object> map2 : beans) {
			 int count = Integer.parseInt(map2.get("wechatCommodityCount").toString());
			 double price =Double.parseDouble(map2.get("productPrice").toString());
			 double thisPric = count*price;
			 totalPrice+=thisPric;
		}
		//添加订单
		Map<String, Object> orderParams = new HashMap<String,Object>();
		orderParams.put("order_log", order_log);
		orderParams.put("orderNumber", orderNumber);
		orderParams.put("createId", wechatLogParams.get("id"));
		orderParams.put("createTime", DateUtil.getTimeAndToString());
		/**
		 * 处理就餐时间
		 */
		String orderEatTime = params.get("eatTime") + "";// 获取就餐时间
		// 如果就餐时间不为空的话，将就餐时间重新组合成新的字符串拼接
		if(!JudgeUtil.isNull(orderEatTime)){
			orderParams.put("orderEatTime", DateUtil.getTime() + " " + orderEatTime.replace("点 ", ":").replace("分", ":00"));
		}
		orderParams.put("orderAdminId", params.get("adminId").toString());
		if (params.get("remark") != null || params.get("remark") != ""){
			orderParams.put("orderDesc", params.get("remark"));
		}
		//订单类型(js判断)
		orderParams.put("orderType", params.get("orderType"));
		//每次操作订单后显示的时间
		orderParams.put("lastUpdateTime", DateUtil.getTimeAndToString());
		//订单的总价格
		orderParams.put("orderPrice", totalPrice);
		//联系方式（js判断联系方式是否合格）
		orderParams.put("phoneNumber", params.get("phoneNumber"));
		mWechatCustomerOrderMapper.addOrder(orderParams);
		//添加订单成功后，获取订单id
		orderId = orderParams.get("id")+"";
		adminId_ = params.get("adminId").toString();
		//添加到订单详情表
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for (Map<String, Object> map3 : beans) { //遍历当前登录人购物车中的所有商品
			//放置添加单个订单项的数据 
			Map<String, Object> thisMap = new HashMap<String,Object>();
			 thisMap.put("orderId", orderId);//订单id
			 thisMap.put("productID", map3.get("wechatCommodity"));
			 thisMap.put("productName", map3.get("productName"));//产品名称
			 thisMap.put("wechatCommodityType",1);//商品类型默认为1：产品
			 thisMap.put("productPrice", map3.get("productPrice"));
			 thisMap.put("wetherDiscount",0);//折扣默认为未参与
			 thisMap.put("wetherActivity", 0);//是否参与活动折扣默认为0
			 thisMap.put("productNum", map3.get("wechatCommodityCount"));
			 list.add(thisMap);
		}
		//添加订单项
		Map <String,Object> itemsParams= new HashMap<String,Object>();
		itemsParams.put("shopping_log", shopping_log);
		itemsParams.put("ordetItems", list);
		mWechatCustomerOrderMapper.addOrderItem(itemsParams);

		int total_fee = (int) (totalPrice*100);
		//调用支付接口
		Map<String, Object> payParams = new HashMap<String,Object>();
		payParams.put("out_trade_no", orderNumber);
		payParams.put("total_fee", total_fee);
		payParams.put("openid", openid);
		
		payParams.put("orderId", orderId);
		payParams.put("adminId", adminId_);
		payParams.put("orderNumber",orderNumber);
		inputObject.setParams(payParams);
		wechatOrderService.getOrderParameter(inputObject, outputObject);
				 
	}

	/**
	 * 更改商品的支付状态
	 */
	@Override
	public void updatePayState(InputObject inputObject, final OutputObject outputObject) throws Exception {
		// 获取订单id
		// 获取登录人id
		// 创建流水号
		Map<String, Object> loginParam = inputObject.getWechatLogParams(); // 获取当前登录人的信息
		Map<String, Object> params = inputObject.getParams();   // 订单号、商店id（adminId orderNumber）
		
		// 获取不到当前登录人信息
		if(JudgeUtil.isNull(loginParam.get("id") + "")){
			return ;
		}
		GoEasy goEasy = new GoEasy("BC-c5e986fba5d14d38b2b2c5b4b072fc8c");
		goEasy.publish(params.get("adminId").toString(),"您有新订单!", new PublishListener(){
			@Override
			public void onSuccess() {
				//System.out.println("成功");
			}
			@Override
			public void onFailed(GoEasyError error) {
				outputObject.setreturnMessage("消息发布失败, 错误编码：" + error.getCode() + " 错误信息： " + error.getContent());
			}
		});
		
		String adminId =  "000000" + params.get("adminId")+""; // 表示商店id添加前导0
		// 查询当天该商店中的订单数量（tableName //订单表的名字   orderNumberChild // 商店id和当前日期拼接字符串）
		String shopIdAndNowDay = adminId.substring(adminId.length() -  6, adminId.length()) +  DateUtil.getTimeToString(); // 商店id和当前日期进行拼接
		Map<String, Object> maxDayNoParam = new HashMap<>();
		maxDayNoParam.put("tableName", order_log); // 订单表名
		maxDayNoParam.put("orderIdAndNowDay", shopIdAndNowDay); // 商店id和当前日期进行拼接
		
		Map<String, Object> dayNoMap = mWechatCustomerOrderMapper.selectMaxDayNo(maxDayNoParam);
		int dayNo = 0; // 该家商店当天的流水号
		// 判断当天当商店是否有订单卖出
		if(dayNoMap == null) {
			dayNo = 0;
		}else{
			dayNo = Integer.parseInt(dayNoMap.get("MaxDayNo")+""); // 获取当前最大日流水号
		}
		//提取订单日期 拼接订单的表
		String order_log= ORDER_NAME+DateUtil.getTimeSixAndToString();
		params.put("order_log", order_log); // 表名
		params.put("wetherPaymentTime", DateUtil.getTimeAndToString());
		params.put("lastUpdateTime", DateUtil.getTimeAndToString()); // 最后更新时间
		params.put("dayNo", dayNo + 1);// 日流水号增加1
		mWechatCustomerOrderMapper.updatePayState(params);
		Map<String, Object> deleteShopCartInfo = new HashMap<>();
		deleteShopCartInfo.put("id", loginParam.get("id") + ""); // 登录人id 
		deleteShopCartInfo.put("adminId", params.get("adminId") + "");  // 商店id
		// 删除当前用户在指定商店中购物车上中的商品
		mWechatCustomerOrderMapper.deleteShopCartProduct(deleteShopCartInfo);
	}

	/**
	 * 更改一个订单的是否做完的状态
	 */
	@Override
	public void updateMakeState(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		//提起订单日期 拼接订单的表
		String order_log= ORDER_NAME+params.get("orderId").toString().substring(12, 18);
		params.put("order_log", order_log);
		mWechatCustomerOrderMapper.updateMakeState(params);
	}

	/**
	 * 获取近3三个月全部的订单
	 */
	@Override
	public void getAllOrder(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		params.put("order_log_1", ORDER_NAME + DateUtil.getTimeSixAndToString());
		params.put("order_log_2", ORDER_NAME + DateUtil.getTimePrevMonthAndToString());
		params.put("order_log_3", ORDER_NAME + DateUtil.getTimeVorMonthAndToString());
		params.put("createId", inputObject.getWechatLogParams().get("id"));
		List<Map<String,Object>> allOrder = mWechatCustomerOrderMapper.getAllOrder(params);
		if(allOrder.size() <=0){
			outputObject.setreturnMessage("当前没有该类订单~");
			return;
		}
		List<Map<String, Object>> returnList = new ArrayList<Map<String,Object>>();
		
		for (Map<String, Object> map : allOrder) {
			Map<String, Object> returnMap =new HashMap<String,Object>();
			//获得订单那中的商品的id,总价格,创建时间,订单号
			returnMap.put("orderId", map.get("id"));
			returnMap.put("totalPrice", map.get("orderPrice"));
			returnMap.put("createTime", map.get("createTime"));
			returnMap.put("orderNumber", map.get("orderNumber"));
			returnMap.put("wetherPayment", map.get("wetherPayment"));
			returnMap.put("wetherMake", map.get("wetherMake"));
			returnMap.put("orderEatTime", map.get("orderEatTime"));
			returnMap.put("orderType", map.get("orderType"));
			returnMap.put("dayNo", map.get("dayNo"));
			returnMap.put("phoneNumber", map.get("phoneNumber"));
			returnMap.put("wetherPaymentTime", map.get("wetherPaymentTime"));
			
			//获得订单中的商店信息
			Map<String, Object> params_1 = new HashMap<String,Object>();
			params_1.put("orderAdminId", map.get("orderAdminId"));
			Map<String, Object> restaurantInfo = mWechatCustomerOrderMapper.getRestaurantInfo(params_1);
			returnMap.put("adminId", restaurantInfo.get("adminId"));
			returnMap.put("adminShopName", restaurantInfo.get("adminShopName"));
			
			//获得商品的信息（提取第一个的商品类型 商品id 来查询）
			Map<String, Object> params_2 = new HashMap<String,Object>();
			params_2.put("id", map.get("id"));
			params_2.put("shopping_log", SHOPPING_NAME+map.get("orderNumber").toString().substring(12, 18));
			List<Map<String,Object>> productDetailByOrderId = mWechatCustomerOrderMapper.getProductDetailByOrderId(params_2);
			
			Map<String, Object> params_3 = new HashMap<String,Object>();
			params_3.put("productType",productDetailByOrderId.get(0).get("wechatCommodityType"));
			params_3.put("productId",productDetailByOrderId.get(0).get("productID"));
			Map<String, Object> productInfo = mWechatCustomerOrderMapper.getProductInfo(params_3);
			returnMap.put("productID", productInfo.get("productID"));
			returnMap.put("orderName", productInfo.get("productName"));
			returnMap.put("orderLogo", productInfo.get("productLogo"));
			returnList.add(returnMap);
		}	
		outputObject.setBeans(returnList);
	}

	/**
	 * 
	 *	获取未支付的订单
	 */
	@Override
	public void getAllOrderNotPaid(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		params.put("order_log_1", ORDER_NAME + DateUtil.getTimeSixAndToString());
		params.put("order_log_2", ORDER_NAME + DateUtil.getTimePrevMonthAndToString());
		params.put("order_log_3", ORDER_NAME + DateUtil.getTimeVorMonthAndToString());
		params.put("createId", inputObject.getWechatLogParams().get("id"));
		params.put("wetherPayment", "0");
		
		List<Map<String,Object>> allOrder = mWechatCustomerOrderMapper.getAllOrder(params);
		outputObject.setBeans(allOrder);
	}
	
	//根据订单获取一个订单中的所有详细信息 数据格式     商品类型-商品id-商品数量,商品类型-商品id-商品数量
	@Override
	public void getProductDetailByOrderId(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		//提取订单日期 拼接订单的表
		String shopping_log= SHOPPING_NAME+params.get("orderId").toString().substring(12, 18);
		params.put("shopping_log", shopping_log);
		List<Map<String,Object>> productDetailByOrderId = mWechatCustomerOrderMapper.getProductDetailByOrderId(params);
		outputObject.setBeans(productDetailByOrderId);
		
	}

	//查询一组商品的信息
	@Override
	public void getProductInfo(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		String[] split1 = params.get("orderItems").toString().split(",");
		//获取商店的信息
		HashSet<Object> hasHSet = new HashSet<Object>();
		for (String string : split1) {
			String[] split = string.split("-");
			hasHSet.add(split[0]);
		}	
		//返回的数据集合
		List<Map<String, Object>> returnList = new ArrayList<Map<String,Object>>();
		//遍历选取的所有商店
		for (Object object : hasHSet) {
			Map<String, Object> returnMap = new HashMap<String,Object>();
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("orderAdminId", object);
			Map<String, Object> restaurantInfo = mWechatCustomerOrderMapper.getRestaurantInfo(map);
			returnMap.put("adminId", restaurantInfo.get("adminId"));
			returnMap.put("adminShopName", restaurantInfo.get("adminShopName"));
			//放置当前商店的所有的桑普
			List<Map<String, Object>> thisList = new ArrayList<Map<String,Object>>();
			for (String string : split1) { //遍历所有得商品
				String[] split = string.split("-");
				if (split[0].equals(object)){ //此商品为该商店的商品
					Map<String, Object> thisMap = new HashMap<String,Object>();
					Map<String, Object> pMap = new HashMap<String,Object>();
					pMap.put("productType",split[1]);
					pMap.put("productId", split[2]);
					Map<String, Object> productInfo = mWechatCustomerOrderMapper.getProductInfo(pMap);
					thisMap.put("adminId", split[0]);
					thisMap.put("cardId", split[4]);
					thisMap.put("productID", productInfo.get("productID"));
					thisMap.put("productName", productInfo.get("productName"));
					thisMap.put("wechatCommodityType",split[1] );
					thisMap.put("productPrice", productInfo.get("productPrice"));
					thisMap.put("count", split[3]);
					thisList.add(thisMap);
				}
			}
			returnMap.put("productInfo", thisList);
			returnList.add(returnMap);
		}
		outputObject.setBeans(returnList);
	}
	 /**
	  * 获取当前登录用户的默认地址
	  */
	@Override
	public void getAddress(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> wechatLogParams = inputObject.getWechatLogParams();
		Map<String, Object> map = new HashMap<String,Object>();
		Map<String, Object> returnMap = new HashMap<String,Object>();
		map.put("deliveryUserId", wechatLogParams.get("id"));
		Map<String, Object> address = mWechatCustomerOrderMapper.getAddress(map);
		//若默认地址为nul
		if (address == null){
			outputObject.setreturnMessage("您还没有填写默认收货地址,请添加默认收货地址~");
			return;
		}else{
			  String province = address.get("province").toString(); //省
			  String city = address.get("city").toString(); //市
			  String count = address.get("count").toString(); //县,区
			  String specific = address.get("specific_").toString(); //详细信息
			  //拼接 地址信息
			  String addressInfo = province+city+count+specific;
			  //收货人姓名
			  String username = address.get("username").toString();
			  //收货人联系方式
			  String phone = address.get("phone").toString();
			  //填充数据
			  returnMap.put("addressId", address.get("addressId"));
			  returnMap.put("addressInfo", addressInfo);
			  returnMap.put("username", username);
			  returnMap.put("phone", phone);
			  outputObject.setBean(returnMap);
		}
	}

	/**
	 * 根据id获取一个用户的地址
	 */
	@Override
	public void getAddressById(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		Map<String, Object> address = mWechatCustomerOrderMapper.getAddressById(params);
		if(address == null){
			outputObject.setreturnMessage("您当前还没有收货地址");
			return ;
		}
		Map<String, Object> returnMap = new HashMap<String,Object>();
		
		String province = address.get("province").toString(); //省
		String city = address.get("city").toString(); //市
		String count = address.get("count").toString(); //县,区
		String specific = address.get("specific_").toString(); //详细信息
		//拼接 地址信息
		String addressInfo = province+city+count+specific;
		//收货人姓名
		String username = address.get("username").toString();
		//收货人联系方式
		String phone = address.get("phone").toString();
		//填充数据
		returnMap.put("addressId", address.get("addressId"));
		returnMap.put("addressInfo", addressInfo);
		returnMap.put("username", username);
		returnMap.put("phone", phone);
		outputObject.setBean(returnMap);
	}

	/**
	 * 删除一个订单
	 */
	public void deleteOrder(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		String shopping_log= SHOPPING_NAME+params.get("orderNumber").toString().substring(12, 18);
		String order_log = ORDER_NAME+params.get("orderNumber").toString().substring(12, 18);
		//删除
		params.put("shopping_log", shopping_log);
		params.put("order_log", order_log);
		mWechatCustomerOrderMapper.deleteOrder(params);
		mWechatCustomerOrderMapper.deleteOrderItems(params);
	}

	/**
	 * 查看 一个订单
	 */
	@Override
	public void getOrderDetails(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		String shopping_log= SHOPPING_NAME+params.get("orderNumber").toString().substring(12, 18);
		String order_log = ORDER_NAME+params.get("orderNumber").toString().substring(12, 18);
		Map<String, Object> returnMap = new HashMap<String,Object>();
		
		//获取订单信息
		Map<String, Object> orderParams = new HashMap<String,Object>();
		orderParams.put("id",params.get("orderId"));
		orderParams.put("order_log", order_log);
		Map<String, Object> orderDetailByOrderId = mWechatCustomerOrderMapper.getOrderDetailByOrderId(orderParams);
		returnMap.put("orderId",orderDetailByOrderId.get("id") );
		returnMap.put("orderNumber",orderDetailByOrderId.get("orderNumber") );
		returnMap.put("orderType",orderDetailByOrderId.get("orderType") );
		returnMap.put("createTime",orderDetailByOrderId.get("createTime") );
		returnMap.put("wetherPaymentTime",orderDetailByOrderId.get("wetherPaymentTime") );
		returnMap.put("orderAdminId",orderDetailByOrderId.get("orderAdminId") );
		returnMap.put("orderPrice",orderDetailByOrderId.get("orderPrice"));
		returnMap.put("dayNo",orderDetailByOrderId.get("dayNo") );
		returnMap.put("phoneNumber",orderDetailByOrderId.get("phoneNumber"));
		returnMap.put("orderEatTime",orderDetailByOrderId.get("orderEatTime"));
		if (!orderDetailByOrderId.get("orderDesc").equals("")){
			returnMap.put("orderDesc",orderDetailByOrderId.get("orderDesc"));
		}else{
			returnMap.put("orderDesc","无");
		}
		
		
		//获取餐厅的信息
		Map<String, Object> resParams = new HashMap<String,Object>();
		resParams.put("orderAdminId", orderDetailByOrderId.get("orderAdminId"));
		Map<String, Object> restaurantInfo = mWechatCustomerOrderMapper.getRestaurantInfo(resParams);
		returnMap.put("adminId", restaurantInfo.get("adminId"));
		returnMap.put("adminShopName", restaurantInfo.get("adminShopName"));
		
		//若订单的类型为5 获取地址的id 查询地址的信息
		if (orderDetailByOrderId.get("orderType").equals(5)){
			Map<String, Object> addressParams = new HashMap<String,Object>();
			addressParams.put("id", orderDetailByOrderId.get("orderEatTime"));
			Map<String, Object> address = mWechatCustomerOrderMapper.getAddressById(addressParams);
			if (address == null){
				returnMap.put("addressId", "");
				returnMap.put("addressInfo", "");
				returnMap.put("username", "");
				returnMap.put("phone", "");
			}else{
			String province = address.get("province").toString(); //省
			String city = address.get("city").toString(); //市
			String count = address.get("count").toString(); //县,区
			String specific = address.get("specific_").toString(); //详细信息
			//拼接 地址信息
			String addressInfo = province+city+count+specific;
			//收货人姓名
			String username = address.get("username").toString();
			//收货人联系方式
			String phone = address.get("phone").toString();
			//填充数据
			returnMap.put("addressId", address.get("addressId"));
			returnMap.put("addressInfo", addressInfo);
			returnMap.put("username", username);
			returnMap.put("phone", phone);
			}
		}else{
			returnMap.put("addressId", "");
			returnMap.put("addressInfo", "");
			returnMap.put("username", "");
			returnMap.put("phone", "");
		}
		
		//获取该订单下的所有的商品id 和商品类型
		Map<String, Object> itemsParams = new HashMap<String,Object>();
		itemsParams.put("shopping_log", shopping_log);
		itemsParams.put("id", params.get("orderId"));
		List<Map<String, Object>> returnList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> productDetailByOrderId = mWechatCustomerOrderMapper.getProductDetailByOrderId(itemsParams);
		for (Map<String, Object> map : productDetailByOrderId) {
			Map<String, Object> productParmas = new HashMap<String,Object>();
			productParmas.put("productType", map.get("wechatCommodityType"));
			productParmas.put("productId", map.get("productID"));
			Map<String, Object> productInfo = mWechatCustomerOrderMapper.getProductInfo(productParmas);
			productInfo.put("productNum", map.get("productNum"));
			returnList.add(productInfo);
		}
		//获取商品信息
		returnMap.put("producntInof", returnList);
		outputObject.setBean(returnMap);
	}

	/**
	 * 未支付订单进行支付
	 */
	@SuppressWarnings("static-access")
	@Override
	public void payOrder(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		Map<String, Object> wechatLogParams = inputObject.getWechatLogParams();
		String order_log = ORDER_NAME+params.get("orderNumber").toString().substring(12, 18);
		params.put("order_log", order_log);
		
		String orderId = params.get("orderNumber").toString()+DateUtil.getUUID();
		String openid = wechatLogParams.get("openid").toString(); 
		params.put("id", params.get("orderId"));
		
		Map<String, Object> orderDetailByOrderId = mWechatCustomerOrderMapper.getOrderDetailByOrderId(params);
		double price = Double.parseDouble(orderDetailByOrderId.get("orderPrice").toString());
		int total_fee = (int)(price * 100);
		
		
		//调用支付接口
		Map<String, Object> payParams = new HashMap<String,Object>();
		payParams.put("out_trade_no", orderId);
		payParams.put("total_fee", total_fee);
		payParams.put("openid", openid);
		
		payParams.put("adminId",orderDetailByOrderId.get("orderAdminId"));
		payParams.put("orderNumber", params.get("orderNumber"));
		payParams.put("orderId", params.get("orderId"));
		inputObject.setParams(payParams);
		wechatOrderService.getOrderParameter(inputObject, outputObject);
	}
	

}
