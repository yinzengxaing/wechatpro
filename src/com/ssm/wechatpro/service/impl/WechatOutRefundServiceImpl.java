package com.ssm.wechatpro.service.impl;

import io.goeasy.GoEasy;
import io.goeasy.publish.GoEasyError;
import io.goeasy.publish.PublishListener;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ssm.wechatpro.dao.WechatOutRefundMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatOrderService;
import com.ssm.wechatpro.service.WechatOutRefundService;
import com.ssm.wechatpro.util.DateUtil;

@Service
public class WechatOutRefundServiceImpl implements WechatOutRefundService {

	@Resource
	private WechatOutRefundMapper wechatOutRefundMapper;
	
	@Resource
	private WechatOrderService  wechatOrderService;
	
	/**
	 * 客户进行申请退款
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	@Transactional
	public void applyForRefund(InputObject inputObject, final OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		String orderNumber = params.get("orderNumber").toString();
		//获取退单的商店id 
		String adminId = String.format("%01d",Integer.parseInt(orderNumber.substring(6, 12)));
		//获取订单那表
		String order_log ="wechat_customer_order_log_"+ orderNumber.substring(12,18);
		//1 更改原始订单状态 获取退单的信息
		String nowTime = DateUtil.getTimeAndToString();
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("order_log", order_log);
		map1.put("wetherPayment", 2); //更改订单状态为申请退款
		map1.put("lastUpdateTime", nowTime);
		map1.put("orderNumber", orderNumber);
		wechatOutRefundMapper.updateOrderState(map1);
		
		//2 获取插入申请单中的参数
		Map<String, Object> map = new HashMap<>();
		map.put("orderNumber", orderNumber);
		map.put("order_log", order_log);
		Map<String, Object> orderParams = wechatOutRefundMapper.getOrderParams(map);
		
		//3 将申请的退单信息插入到退单表中
		Map<String, Object> map2 = new HashMap<>();
		map2.put("orderNumber", orderNumber);
		map2.put("refundTime", nowTime);
		map2.put("refundDesc", params.get("refundDesc"));
		map2.put("orderId", orderParams.get("id"));
		map2.put("adminId", adminId);
		map2.put("orderPrice", orderParams.get("orderPrice"));
		map2.put("wetherPaymentTime", orderParams.get("wetherPaymentTime"));
		map2.put("phoneNumber", orderParams.get("phoneNumber"));
		wechatOutRefundMapper.insertOutRefund(map2);
		
		//4 通知相关餐厅有新的退单
		GoEasy goEasy = new GoEasy("BC-c5e986fba5d14d38b2b2c5b4b072fc8c");
		goEasy.publish(adminId+"outRefond","您有新的退单请求，点击处理！", new PublishListener(){
			@Override
			public void onSuccess() {
				//System.out.println("成功");
			}
			@Override
			public void onFailed(GoEasyError error) {
				outputObject.setreturnMessage("消息发布失败, 错误编码：" + error.getCode() + " 错误信息： " + error.getContent());
			}
		});
	}

	
	/**
	 * 商家进行退款操作
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	@Override
	@Transactional
	public void dealWithRefund(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		//获取订单那表
		String orderNumber = params.get("orderNumber").toString();
		String order_log ="wechat_customer_order_log_"+ orderNumber.substring(12,18);
		params.put("order_log", order_log);
		String adminId = String.format("%01d",Integer.parseInt(orderNumber.substring(6, 12)));
		
		//1.修改退退单状态
		Map<String, Object> map = new HashMap<>();
		String processState = params.get("processState").toString();
		map.put("processState", processState);
		map.put("processorTime",  DateUtil.getTimeAndToString());
		map.put("processor", inputObject.getLogParams().get("id"));
		wechatOutRefundMapper.updateOutRefund(params);
		
		//2.修改订单那的状态
		Map<String, Object> map2 = new HashMap<>();
		if (processState.equals("1")){
			//调用退单接口进行退单操作
			Map<String, Object> orderPrice = wechatOutRefundMapper.getOrderParams(params);
//			Double totalPrice = Double.parseDouble(orderPrice.get("orderPrice").toString());
//			int total_fee = (int) (totalPrice*100);
			
			BigDecimal totalPrice = new BigDecimal(orderPrice.get("orderPrice").toString());
			BigDecimal mm = new BigDecimal(100);
			int total_fee = totalPrice.multiply(mm).intValue();
			
			params.put("orderPrice", total_fee);
			Map<String, Object>  refundParams = new HashMap<>();
			
			refundParams.put("orderPrice", total_fee);
			refundParams.put("adminId", adminId);
			refundParams.put("orderNumber",orderNumber);
			
			inputObject.setParams(refundParams);
			wechatOrderService.orderRefund(inputObject, outputObject);
			map2.put("wetherPayment", 3); //更改订单状态为退款成功
		}else if (processState.equals("2")){
			map2.put("wetherPayment", 4); //更改订单状态为退款失败
		}
		map2.put("order_log", order_log);
		map2.put("lastUpdateTime", DateUtil.getTimeAndToString());
		map2.put("orderNumber", orderNumber);
		wechatOutRefundMapper.updateOrderState(map2);
		
	}

	/**
	 * 获得退款的列表
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	@Transactional
	public void getRefundList(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		Map<String, Object> logParams = inputObject.getLogParams();
		//获取当前登录人店铺id
		String adminId = logParams.get("id").toString();
		
		//获取分页信息
		int page = Integer.parseInt(params.get("offset").toString())/Integer.parseInt(params.get("limit").toString());
		page++;
		int limit = Integer.parseInt(params.get("limit").toString());
		params.put("adminId", adminId);
		List<Map<String,Object>> outRefundList  = wechatOutRefundMapper.getOutRefundList(params ,new PageBounds(page,limit));
		PageList<Map<String, Object>> abilityInfoPageList = (PageList<Map<String, Object>>)outRefundList;
		//获取当前页数的总数
		int total = abilityInfoPageList.getPaginator().getTotalCount();
		outputObject.setBeans(outRefundList);
		outputObject.settotal(total);
	}

}
