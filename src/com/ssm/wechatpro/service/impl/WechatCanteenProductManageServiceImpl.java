package com.ssm.wechatpro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ssm.wechatpro.dao.WechatCanteenProductManageMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatCanteenProductManageService;
import com.ssm.wechatpro.util.JudgeUtil;

@Service("wechatCanteenProductManageServiceImpl")
public class WechatCanteenProductManageServiceImpl implements WechatCanteenProductManageService{

	@Resource
	WechatCanteenProductManageMapper wechatCanteenProductManageMapper;

	/**
	 * 显示所选产品的种类信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void selectProductTypeByChoose(InputObject inputObject, OutputObject outputObject) throws Exception {
		// 获得登录人的信息
		Map<String, Object> map = inputObject.getLogParams();
		if(JudgeUtil.isNull(map.get("id") + ""))
			return ;
		else{
			// 查询已经选择商品的种类 信息
			List<Map<String, Object>> productTypeList = wechatCanteenProductManageMapper.selectProductTypeByChoose(map);
			outputObject.setBeans(productTypeList);
			outputObject.settotal(productTypeList.size());
		}
	}
	/**
	 * 显示所选择的产品信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void selectProductByChoose(InputObject inputObject, OutputObject outputObject) throws Exception {
		// 获取登录人的信息
		Map<String, Object> map = inputObject.getLogParams();
		if(JudgeUtil.isNull(map.get("id") + "")){
			return ;
		}else{
			//获取分页信息所需参数
			Map<String, Object> mapPage = inputObject.getParams();
			int page = Integer.parseInt(mapPage.get("offset").toString()) / Integer.parseInt(mapPage.get("limit").toString());
			page ++;
			int limit = Integer.parseInt(mapPage.get("limit").toString());
			Map<String, Object> mapParam = new HashMap<>();
			// 登录人Id不为空过的话，将登录人id放入参数map中
			if(!JudgeUtil.isNull(map.get("id")+""))
				mapParam.put("id", map.get("id")+"");
			// 根据名称进行模糊查询
			if(!JudgeUtil.isNull(mapPage.get("productName")+"")){
				mapParam.put("productName", mapPage.get("productName")+"");
			}
			mapParam.put("productTypeId", mapPage.get("productTypeId"));
			// 查询获得已经选择的商品的信息
			List<Map<String, Object>> productList = wechatCanteenProductManageMapper.selectProductByChoose(mapParam, new PageBounds(page, limit));
			// 强制类型转化，获取当前的分页信息
			PageList<Map<String, Object>> pageList = (PageList<Map<String,Object>>)productList;
			// 获取当前的总页数
			int total = pageList.getPaginator().getTotalCount();
			outputObject.settotal(total);
			outputObject.setBeans(productList);
		}
	}
	/**
	 * 显示所选择的常规套餐的信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void selectPackageProductByChoose(InputObject inputObject, OutputObject outputObject) throws Exception {
		// 获取登录人的信息
		Map<String, Object> map = inputObject.getLogParams();
		if(JudgeUtil.isNull(map.get("id") + ""))
			return ;
		else{
			// 获取分页信息所需要的参数
			Map<String, Object> mapPage = inputObject.getParams();
			int page = Integer.parseInt(mapPage.get("offset").toString()) / Integer.parseInt(mapPage.get("limit").toString());
			page ++;
			int limit = Integer.parseInt(mapPage.get("limit").toString());
			//存放查询所需的参数
			Map<String, Object> mapParam = new HashMap<>();
			// 登录人Id不为空过的话，将登录人id放入参数map中
			if(!JudgeUtil.isNull(map.get("id")+""))
				mapParam.put("id", map.get("id")+"");
			if(!JudgeUtil.isNull(mapPage.get("packageName")+"")){
				mapParam.put("packageName", mapPage.get("packageName")+"");
			}
			// 查询获得已经选择的常规套餐的信息
			List<Map<String, Object>> packageProductList = wechatCanteenProductManageMapper.selectPackageProductByChoose(mapParam, new PageBounds(page, limit));
			// 强制类型转换，获取当前分页信息
			PageList<Map<String, Object>> pageList = (PageList<Map<String, Object>>)packageProductList;
			// 获取当前页的总数
			int total = pageList.getPaginator().getTotalCount();
			outputObject.settotal(total);
			outputObject.setBeans(packageProductList);
		}
	}

	/**
	 * 显示所选择的可选套餐的信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void selectChoosePackageByChoose(InputObject inputObject, OutputObject outputObject) throws Exception {
		// 获取登录人的信息
		Map<String, Object> map = inputObject.getLogParams();
		if(JudgeUtil.isNull(map.get("id") + "")){
			return ;
		}else{
			// 获取分页信息所需参数
			Map<String, Object> mapPage = inputObject.getParams();
			int page = Integer.parseInt(mapPage.get("offset").toString()) / Integer.parseInt(mapPage.get("limit").toString());
			page ++;
			int limit = Integer.parseInt(mapPage.get("limit").toString());
			//存放查询所需的参数
			Map<String, Object> mapParam = new HashMap<>();
			mapParam.put("id", map.get("id")+"");
			if(!JudgeUtil.isNull(mapPage.get("packageName")+"")){
				mapParam.put("packageName", mapPage.get("packageName")+"");
			}
			
			// 查询获得已经选择的常规套餐的信息
			List<Map<String, Object>> choosePackageList = wechatCanteenProductManageMapper.selectChoosePackageByChoose(mapParam, new PageBounds(page, limit));
			// 强制类型转换，获取 当前分页信息
			PageList<Map<String, Object>> pageList = (PageList<Map<String,Object>>)choosePackageList;
			// 获取当前页的总数
			int total = pageList.getPaginator().getTotalCount();
			outputObject.settotal(total);
			outputObject.setBeans(choosePackageList);
		}
	}

	/**
	 * 查询数量
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void selectNum(InputObject inputObject, OutputObject outputObject)throws Exception{
		Map<String, Object> map = inputObject.getLogParams();
		Map<String, Object> mapParam = inputObject.getParams();
		if(JudgeUtil.isNull(map.get("id") + "")){
			return ;
		}else{
			mapParam.put("adminRestaurantId", map.get("id") + "");
			Map<String, Object> returnInfo = wechatCanteenProductManageMapper.selectNum(mapParam);
			outputObject.setBean(returnInfo);
		}
	}
	/**
	 * 更新商品的总数量
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void updateProductNum(InputObject inputObject, OutputObject outputObject) throws Exception {
		// 获得登录人的信息
		Map<String, Object> map = inputObject.getLogParams();
		// 获得所需要的参数信息（商品的总数量、商品id、商品类型）
		Map<String, Object> mapParam = inputObject.getParams();
		if(JudgeUtil.isNull(map.get("id") + ""))
			return ;
		else{
			// 将获取到的数据按照逗号（,）进行分割得到一个字符串数组
			String [] productIds = (mapParam.get("productId") + "").split(",");
			// 将productId的值给替换成字符串数组
			mapParam.put("productId", productIds);
			mapParam.put("id", map.get("id")+""); // 将创建人id放入参数map
	
			wechatCanteenProductManageMapper.updateProductNum(mapParam);
		}
	}
	
	/**
	 * 重置卖出的数量
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void updateProductNowNum(InputObject inputObject,OutputObject outputObject) throws Exception {
		// 获得登录人的信息
		Map<String, Object> map = inputObject.getLogParams();
		// 获得所需要的参数信息（商品的总数量、商品id、商品类型）
		Map<String, Object> mapParam = inputObject.getParams();
		
		if(JudgeUtil.isNull(map.get("id") + ""))
			return ;
		else{
			// 将获取到的数据按照逗号（,）进行分割得到一个字符串数组
			String [] productIds = (mapParam.get("productId") + "").split(",");
			// 将productId的值给替换成字符串数组
			mapParam.put("productId", productIds);
			mapParam.put("id", map.get("id")+""); // 将创建人id放入参数map
			wechatCanteenProductManageMapper.updateProductNowNum(mapParam);
		}
	}
	
	/**
	 * 商品上下线操作
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void updateProductState(InputObject inputObject,	OutputObject outputObject) throws Exception {
		// 获取登录人的信息
		Map<String, Object> map = inputObject.getLogParams();
		// 获取所需参数的信息（商品状态、商品id、商品类型）
		Map<String, Object> mapParam = inputObject.getParams();
		if(JudgeUtil.isNull(map.get("id") + ""))
			return ;
		else{
			// 将获取到的数据按照逗号（,）进行分割得到一个字符串数组
			String [] productIds = (mapParam.get("productId") + "").split(",");
			String [] productStates = (mapParam.get("productState") + "").split(",");
			if (productStates[0].equals("")){
				outputObject.setreturnMessage("请选择需要修改的商品");
				return;
			}
			// 集合
			for(int i = 0 ; i < productStates.length; i ++){
				Map<String, Object> mapProductAndState = new HashMap<>();
				mapProductAndState.put("productId", Integer.parseInt(productIds[i]));
				mapProductAndState.put("productState", Integer.parseInt(productStates[i]));
				mapProductAndState.put("adminRestaurantId", Integer.parseInt(map.get("id")+"")); // 将创建人id放入参数map
				mapProductAndState.put("productType", Integer.parseInt(mapParam.get("productType") + ""));
				wechatCanteenProductManageMapper.updateProductState(mapProductAndState);
			}
		}
	}
	
	
	/**
	 * 查看商店的详细信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectForShopInfo(InputObject inputObject, OutputObject outputObject) throws Exception {
		// 获得登录人员的信息
		Map<String, Object> map = inputObject.getLogParams();
		Map<String, Object> returnInfo = wechatCanteenProductManageMapper.selectForShopInfo(map);
		if(returnInfo==null){
			outputObject.setreturnMessage("非餐厅管理人员！");
			return ;
		}
		outputObject.setBean(returnInfo);
	}
	
	/**
	 * 表示用现金支付的用户
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateCashProple(InputObject inputObject,OutputObject outputObject) throws Exception {
		// 获得登录人的id
		Map<String, Object> map = inputObject.getLogParams();
		// 获得相对应的参数
		Map<String, Object> mapParam = inputObject.getParams();
		if(JudgeUtil.isNull(map.get("id") + "")){
			return ;
		}else{
			mapParam.put("id", map.get("id") + "");
			// 更新状态
			wechatCanteenProductManageMapper.updateCashProple(mapParam);
		}
	}
	
	/**
	 * 各个门店修改地址，营业时间等
	 */
	@Override
	public void updateProductForShop(InputObject inputObject,OutputObject outputObject) throws Exception {
		// 获得登录人的id
		Map<String, Object> map = inputObject.getLogParams();
		// 获得相对应的参数
		Map<String, Object> mapParam = inputObject.getParams();
		if(JudgeUtil.isNull(map.get("id") + "")){
			return ;
		}else{
			mapParam.put("adminId", map.get("id") + "");
			// 更新状态
			wechatCanteenProductManageMapper.updateProductForShop(mapParam);
		}
	}
	
	/**
	 * 删除一周前未付款的订单
	 * @param map
	 * @throws Exception
	 *//*
	public void deletePreWeekNoPayInfo()throws Exception {
		
		// 获取一周前的时间
		String preWeekDate = DateUtil.getTimePreWeekToString();
		// 拼接的订单表的表名
		String tableName = ORDERlOGTABLENAME + preWeekDate.substring(0, 6);
		// 拼接订单详情表的表名
		String tableDetailName = ORDERSHOPPINGTABLENAMW + preWeekDate.substring(0, 6);
		// 查询所有未付款订单
		Map<String ,Object> map = new HashMap<>();
		map.put("tableName", tableName);
		map.put("preWeekDate", preWeekDate);
		
		// 获得未付款的订单的id
		List<Map<String, Object>> noPayOrderId = wechatCanteenProductManageMapper.selectPreWeekInfo(map);
		
		// 未付款订单的id
		Map<String , Object> deleteMap = new HashMap<>();
		
		deleteMap.put("tableName", tableName);
		deleteMap.put("noPayOrderId", noPayOrderId); // 需要删除的订单的id
		
		// 删除未付款的订单
		wechatCanteenProductManageMapper.deletePreWeekInfo(deleteMap);
		
		deleteMap.put("tableDetaileName", tableDetailName); // 详情表表名
		
		// 删除未付款的订单详
		wechatCanteenProductManageMapper.deletePreWeekDetailInfo(deleteMap);
	}*/
	/**
	 * 查看已经付款但是没有送给用户的订单列表（近七天的）
	 * @param map
	 * @return
	 * @throws Exception
	 */
/*	public void selectPaidOrderForm(InputObject inputObject, OutputObject outputObject) throws Exception {
		// 登录人的信息
		Map<String, Object> map = inputObject.getLogParams();
		Map<String, Object> mapgetParam = inputObject.getParams();
		if(JudgeUtil.isNull(map.get("id") + "")){
			return  ;
		}else{
			
			int page = Integer.parseInt(mapgetParam.get("offset").toString()) / Integer.parseInt(mapgetParam.get("limit").toString());
			page ++;
			int limit = Integer.parseInt(mapgetParam.get("limit").toString());
			
			String nowDate = DateUtil.getTimeToString(); // 获取当前的日期
			String preWeekDate = DateUtil.getTimePreWeekToString(); // 获取一周前的日期
			
			List<Map<String, Object>> returnInfo = new ArrayList<>(); // 需要返回的数据信息
			
			Map<String, Object> mapParam = new HashMap<>();
			// 判断两个时间是不是在一个月中
			// 表示是在一个月中
			if(nowDate.substring(4,6 ).equals(preWeekDate.substring(4,6))){
				// 拼接的表名
				String tableName = ORDERlOGTABLENAME + nowDate.substring(0, 6);
				mapParam.put("tableName", tableName);
				mapParam.put("id", map.get("id")); // 餐厅人员id
				mapParam.put("preWeekDate", preWeekDate); // 一周前时间
				mapParam.put("nowDate", nowDate); // 当前时间
				mapParam.put("orderType", mapgetParam.get("orderType"));
				returnInfo = wechatCanteenProductManageMapper.selectPaidOneOrderForm(mapParam, new PageBounds(page, limit));
			}else{ // 在两个月中
				String nowMonthtableName = ORDERlOGTABLENAME + nowDate.substring(0, 6); // 表示当月的表名
				String preMonthTableName = ORDERlOGTABLENAME + preWeekDate.substring(0, 6); // 表示上个月的数据表名
				mapParam.put("nowMonthTableName", nowMonthtableName); // 当前月的表名
				mapParam.put("nowDate", nowDate); // 当前时间
				mapParam.put("preMonthTableName", preMonthTableName); // 上个月的表名
				mapParam.put("id", map.get("id")); // 餐厅人员id
				mapParam.put("preWeekDate", preWeekDate);
				mapParam.put("orderType", mapgetParam.get("orderType"));
				returnInfo = wechatCanteenProductManageMapper.selectPaidTwoOrderForm(mapParam, new PageBounds(page, limit));
			}
			// 强制类型转换，获取当前的分页信息
			PageList<Map<String, Object>> pageList = (PageList<Map<String,Object>>)returnInfo;
			int total = pageList.getPaginator().getTotalCount();
			outputObject.setBeans(returnInfo);
			outputObject.settotal(total);
		}
	}*/
	/**
	 * 查看订单的详情
	 * @param inputObject 
	 * @param outputObject
	 * @throws Exception
	 */
/*	public void selectDetailInfoOrderForm(InputObject inputObject, OutputObject outputObject) throws Exception{
		// 获得登录人的id
		Map<String, Object> map = inputObject.getLogParams();
		// 需要获取（订单的id（orderId标识））
		Map<String, Object> mapParam = inputObject.getParams();
		if(JudgeUtil.isNull(map.get("id") + "")){
			return ;
		}else{
			// 通过订单获得日期信息
			String dateTime = (mapParam.get("orderNumber") + "").substring(12, 18);
			
			// 该订单的基本信息（orderAdminId表示登录人的id、orderid表示表示订单的id）
			mapParam.put("orderAdminId", map.get("id")+"");
			// 拼接表名
			mapParam.put("tableName", ORDERlOGTABLENAME + dateTime); // 通过
			// 查看订单的基本信息（表名、id（商店id）、orderAdminId（）、orderid（表示id））
			Map<String, Object> productBasicInfo = wechatCanteenProductManageMapper.selectOrderFromBasic(mapParam);
			// 通过订单基本信息获得订单的详细信息(订单id、)
			mapParam.put("tableName", ORDERSHOPPINGTABLENAMW + dateTime);
			List<Map<String, Object>> orderFormINfoList = wechatCanteenProductManageMapper.selectOrderFormDetail(mapParam);
			outputObject.setBean(productBasicInfo); // 订单的基本信息
			outputObject.setBeans(orderFormINfoList);  // 订单中含有的商品数量
			outputObject.settotal(orderFormINfoList.size());
		}
	}*/
	
	/**
	 * 更新送给顾客订单进行更新
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
/*	@Override
	public void updateMake(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getLogParams();
		Map<String, Object> mapParam = inputObject.getParams();
		if(JudgeUtil.isNull(map.get("id") +"")){
			return ;
		}else{
			String dateTime = (mapParam.get("orderNumber")+"").substring(12,18);
			mapParam.put("orderId", mapParam.get("id") + "");
			mapParam.put("tableName", ORDERlOGTABLENAME + dateTime);
			// 修改订单的状态
			wechatCanteenProductManageMapper.updateMake(mapParam);
			mapParam.put("tableName", ORDERSHOPPINGTABLENAMW + dateTime);
			
			// 查询其中含有的所有商品及种类
			List<Map<String, Object>> productList = wechatCanteenProductManageMapper.selectOrderFormDetail(mapParam);
			for(int i = 0 ; i < productList.size(); i ++){
				Map<String, Object> updateNumMap = new HashMap<>();
				updateNumMap.put("id", map.get("id") + "");
				updateNumMap.put("productId", productList.get(i).get("productID"));
				updateNumMap.put("productType", productList.get(i).get("wechatCommodityType"));
				updateNumMap.put("productNum", productList.get(i).get("productNum"));
				// 更新每个商品的卖出数量
				wechatCanteenProductManageMapper.updateMakeAddNum(updateNumMap);
			}
		}
	}*/

}
