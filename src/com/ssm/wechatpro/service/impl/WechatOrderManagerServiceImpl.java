package com.ssm.wechatpro.service.impl;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ssm.wechatpro.dao.WechatOrderManagerMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatOrderManagerService;
import com.ssm.wechatpro.util.DateUtil;
import com.ssm.wechatpro.util.JudgeUtil;

@Service("wechatOrderManagerServiceImpl")
public class WechatOrderManagerServiceImpl implements WechatOrderManagerService {
	
	@Autowired
	private WechatOrderManagerMapper  wechatOrderManagerMapper;

	// 需要查询订单的表名
	private final static  String ORDERlOGTABLENAME = "wechat_customer_order_log_";
	private final static  String ORDERSHOPPINGTABLENAMW = "wechat_customer_order_shopping_log_";
	
	/**
	 * 查询该商店当天所有已经付款的但是没有给顾客的订单（按照流水号排序）
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public void selectPaiedOrderForm(InputObject inputObject, OutputObject outputObject) throws Exception{
		// tableName
		// orderAdminId  餐厅id
		// shopIdAndDay 六位餐厅id和当前日期拼接
		// 获取该商店登录id
		Map<String, Object> map = inputObject.getLogParams();
		Map<String, Object> mapParam = inputObject.getParams();
		if(JudgeUtil.isNull(map.get("id") + "")){
			return ;
		}
		// 尽心分页处理
		int page = Integer.parseInt(mapParam.get("offset").toString())/Integer.parseInt(mapParam.get("limit").toString());
		page++;
		int limit = Integer.parseInt(mapParam.get("limit").toString());
		
		mapParam.put("tableName", ORDERlOGTABLENAME + DateUtil.getTimeSixAndToString()); // 添加表名
		String shopIdAndDay = "000000" + map.get("id") + "";
		// 拼接商店id和当前日期字符串
		mapParam.put("shopIdAndDay", shopIdAndDay.substring(shopIdAndDay.length()-6, shopIdAndDay.length()) + DateUtil.getTimeToString());
		mapParam.put("orderAdminId", map.get("id") + "");
		List<Map<String,Object>> returnInfo = wechatOrderManagerMapper.selectPaiedOrderForm(mapParam, new PageBounds(page, limit));
		PageList<Map<String, Object>> pageList = (PageList<Map<String,Object>>)returnInfo;
		
		// 获取当前页数
		int total = pageList.getPaginator().getTotalCount();
		outputObject.setBeans(returnInfo);
		outputObject.settotal(total);
	}
	
	/**
	 * 根据订单的id和订单的单号查询订单详情
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public void selectOrderFormInfo (InputObject inputObject, OutputObject outputObject) throws Exception{
		Map<String, Object> mapParam = inputObject.getParams();
		String string = mapParam.get("orderNumber").toString();
		String data = string.substring(12,18);
		String tableName = ORDERlOGTABLENAME + data; // 拼接表名
		mapParam.put("tableName", tableName);
		// 查询商品的基本信息
		Map<String, Object> productBasic =  wechatOrderManagerMapper.selectOrderFormBasic(mapParam);
		
		// 拼接商品详情表的表名
		mapParam.put("detailTableName", ORDERSHOPPINGTABLENAMW +data);
		// 返回该订单中包含的商品信息
		List<Map<String, Object>> productList = wechatOrderManagerMapper.selectOrderFormDetail(mapParam);
		outputObject.setBean(productBasic);
		outputObject.setBeans(productList);
	}
	
	/**
	 * 表示做好了给顾客
	 * @param map
	 * @throws Exception
	 */
	public void updateMake(InputObject inputObject, OutputObject outputObject) throws Exception{
		// 获取当前餐厅的id
		Map<String, Object> map = inputObject.getLogParams();
		if(JudgeUtil.isNull(map.get("id") + "")){
			return ;
		}
		// 获取单号
		// 获取该订单的id
		Map<String, Object> mapParam = inputObject.getParams();
		// 拼接表名
		mapParam.put("tableName", ORDERlOGTABLENAME + DateUtil.getTimeSixAndToString());
		mapParam.put("nowTime", DateUtil.getTimeAndToString() + "");
		wechatOrderManagerMapper.updateMake(mapParam);
		// 同时将该订单中包含商品的数量在分配表中进行更新
		mapParam.put("detailTableName", ORDERSHOPPINGTABLENAMW + DateUtil.getTimeSixAndToString());
		// 传入订单id即可
		List<Map<String, Object>> productList = wechatOrderManagerMapper.selectProductTotal(mapParam);
		for(Map<String, Object> productInfo : productList){
			productInfo.put("id", map.get("id") + "");
			wechatOrderManagerMapper.updateMakeAddNum(productInfo);
		}
	}
	
}
