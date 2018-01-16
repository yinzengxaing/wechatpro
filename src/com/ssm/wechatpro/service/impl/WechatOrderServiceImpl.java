package com.ssm.wechatpro.service.impl;

import io.goeasy.GoEasy;
import io.goeasy.publish.GoEasyError;
import io.goeasy.publish.PublishListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ssm.wechatpro.dao.MWechatCustomerOrderMapper;
import com.ssm.wechatpro.dao.WechatProductRestaurantMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatOrderService;
import com.ssm.wechatpro.util.Constants;
import com.ssm.wechatpro.util.DateUtil;
import com.ssm.wechatpro.util.JudgeUtil;
import com.ssm.wechatpro.util.OrderUtil;
import com.wechat.service.GetJSSDKParameter;
import com.wechat.service.RefundService;
import com.wechat.service.UnifiedorderService;

@Service
public class WechatOrderServiceImpl implements WechatOrderService {
	
	@Resource
    private WechatProductRestaurantMapper  wechatProductRestaurantMapper;
	
	@Resource
	private MWechatCustomerOrderMapper mWechatCustomerOrderMapper;

	private static String ORDER_NAME ="wechat_customer_order_log_"; 
	String order_log = ORDER_NAME + DateUtil.getTimeSixAndToString();// 拼接数据表名 表示数据表名(订单表)
	
	@Override
	public void getOrderParameter(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		Map<String,Object> beanmessage = wechatProductRestaurantMapper.selectCardAndKey(params);
		String sub_mch_id = beanmessage.get("adminShopCard").toString();
     	String key = beanmessage.get("adminShopKey").toString();
		String out_trade_no = params.get("out_trade_no").toString();// 商户订单号
		String total_fee = params.get("total_fee").toString();// 标记金额
		String openid = params.get("openid").toString();
		String adminId = params.get("adminId").toString();
		
		// 预支付标识
		Map<String, Object> message = UnifiedorderService.getPrepayid(sub_mch_id,key,out_trade_no, total_fee, openid, inputObject, outputObject);// 获取预支付标示
		Map<String, Object> map = new HashMap<>();
		map.put("adminId", adminId);
		map.put("noncestr", message.get("nonceStr"));
		map.put("timestamp", message.get("timeStamp"));
		map.put("signType", "MD5");
		map.put("appid", Constants.APPID);
		map.put("paypackage", message.get("package"));
		map.put("paySign", message.get("paySign"));
		map.put("signature", GetJSSDKParameter.getWechatJsParameter(map).get("signature"));
		
		//返回订单编号 和商家id 
		map.put("orderNumber", params.get("orderNumber").toString());
		map.put("orderId", params.get("orderId"));
		map.put("adminId",params.get("adminId").toString());
		
		System.out.println("map="+map);
		
		outputObject.setBean(map);
		
	}
	
	/**
	 * 退款
	 *mch_id:微信支付分配的商户号
	 *out_trade_no ：系统内部生成的订单号
	 *total_fee：订单金额
	 *refund_fee：退款金额
	 *refund_desc：退款原因
	 *key：商户的key
	 */
	@Override
	public void orderRefund(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		Map<String,Object> beanmessage = wechatProductRestaurantMapper.selectCardAndKey(params);
		String sub_mch_id = beanmessage.get("adminShopCard").toString();
		String out_trade_no =params.get("orderNumber").toString();
		String refund_fee = params.get("orderPrice").toString(); //订单金额
		String total_fee = params.get("orderPrice").toString(); //
		
        Map<String, Object> returnMap = RefundService.getRefundParams(sub_mch_id, out_trade_no, total_fee, refund_fee, inputObject, outputObject);
		outputObject.setBean(returnMap);
	}
	
	
	
	public static String SHA1(String decript) {  
	    try {  
	        MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");  
	        digest.update(decript.getBytes());  
	        byte messageDigest[] = digest.digest();  
	        // Create Hex String  
	        StringBuffer hexString = new StringBuffer();  
	        // 字节数组转换为 十六进制 数  
	            for (int i = 0; i < messageDigest.length; i++) {  
	                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);  
	                if (shaHex.length() < 2) {  
	                    hexString.append(0);  
	                }  
	                hexString.append(shaHex);  
	            }  
	            return hexString.toString();  
	   
	        } catch (NoSuchAlgorithmException e) {  
	            e.printStackTrace();  
	        }  
	        return "";  
	}

	/**
	 * 支付成功异步回调
	 */
	@SuppressWarnings("unused")
	@Override
	public void notifyPay(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		String out_trade_no=null;
	    String return_code =null;
	    String result_code = null;
	    String resXml = null;//反馈给服务器的通知xml
	    Map<String, Object> resultMap = new HashMap<String, Object>();//回调传的xml转map
	    Map<String, Object> returnXml = new HashMap<String, Object>();//响应微信服务器要生成xml前传的参数
	    try {
	    	//获取HTTP请求的输入流
	        InputStream inStream = request.getInputStream();
	        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
	        byte[] buffer = new byte[1024];
	        int len = 0;
	        while ((len = inStream.read(buffer)) != -1) {
	            outSteam.write(buffer, 0, len);
	        }
	        outSteam.close();
	        inStream.close();
	        String resultStr  = new String(outSteam.toByteArray(),"utf-8");// 获取微信调用我们notify_url的返回信息
	        System.out.println("支付成功的回调："+resultStr);
	        
	        resultMap = UnifiedorderService.readStringXmlOut(resultStr);
 	        System.out.println("回调接口的resultMap="+resultMap);
 	        
 	        String total_fee = (String)resultMap.get("total_fee");//1分显示的是1
 	        Double fee = Double.valueOf(total_fee);
 	        String sign = (String) resultMap.get("sign");
 	        result_code = (String) resultMap.get("result_code");
 	        out_trade_no = (String) resultMap.get("out_trade_no");//订单编号
 	        return_code = (String) resultMap.get("return_code");
 	        resultMap.put("order_log",order_log);
 	        if(return_code.equals("SUCCESS") && result_code.equals("SUCCESS")){
	        	//支付成功的业务逻辑
 	        	 Map<String, Object> orderMap = mWechatCustomerOrderMapper.checkOrderByNo(resultMap);
 	        	 String orderPrice = orderMap.get("orderPrice").toString();
 	        	 Double price = Double.valueOf(orderPrice);
 	        	 price =price*100;
 	        	//根据订单编号查询当前订单是否已经处理
	 	        if(orderMap.get("wetherPayment").toString().equals("1")){//已支付
	 	        	System.out.println("已支付，给微信服务器反馈信息");
	 	        	// 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.  
                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"  
                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";  
    			    response.getWriter().write(resXml);
	 	        }else{//未支付
	 	        	System.out.println("fee="+fee+",price="+price);
	 	        	if(fee.compareTo(price) == 0){//Double比较，金额相同
        				//更新订单
        				System.out.println("开始更新订单");
        				updatePayStateByBack(resultMap,request,response);
        				System.out.println("更新订单成功");
        				//响应微信服务器
        				// 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.  
                        resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"  
                                + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";  
        	  	        response.getWriter().write(resXml);
        				
        			}else{
        				System.out.println("金额异常");
        				//响应微信服务器
        	  	        // 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.  
                        resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"  
                                + "<return_msg><![CDATA[金额异常]]></return_msg>" + "</xml> ";  
        	  	        response.getWriter().write(resXml);
        			}
	 	        }
	         }else{
	        	System.out.println("支付失败或有问题");
	        	//响应微信服务器
	        	// 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.  
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"  
                        + "<return_msg><![CDATA[支付失败]]></return_msg>" + "</xml> ";  
	  	        response.getWriter().write(resXml);
	         }
	    }  catch (Exception e) {
	    	System.out.printf("微信回调接口出现错误：",e);
	    	//响应微信服务器
	    	// 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.  
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"  
                    + "<return_msg><![CDATA[回调接口出现错误]]></return_msg>" + "</xml> ";  
  	        response.getWriter().write(resXml);
	    } 
		
	}

	/**
	 * 更新订单状态
	 * @param resultMap
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void updatePayStateByBack(Map<String, Object> resultMap, HttpServletRequest request, final HttpServletResponse response)throws Exception {
		
		
		String attach = resultMap.get("attach").toString();//自定义的参数
		
		// 获取订单id
		// 获取登录人id
		String loginId = StringUtils.substringBeforeLast(attach, ",");
		//管理员AdminID
		String adminId = StringUtils.substringBefore(attach, ",");
		// 创建流水号
		// 获取不到当前登录人信息
		if(JudgeUtil.isNull(loginId)){//loginParam.get("id").toString()
			return ;
		}
		GoEasy goEasy = new GoEasy("BC-c5e986fba5d14d38b2b2c5b4b072fc8c");
		goEasy.publish(adminId,"您有新订单!", new PublishListener(){//params.get("adminId").toString()
			@Override
			public void onSuccess() { //
				//System.out.println("成功");
			}
			@Override
			public void onFailed(GoEasyError error) {
//				outputObject.setreturnMessage("消息发布失败, 错误编码：" + error.getCode() + " 错误信息： " + error.getContent());
				try {
					response.getWriter().write("消息发布失败, 错误编码：" + error.getCode() + " 错误信息： " + error.getContent());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		adminId =  "000000" + adminId+""; // 表示商店id添加前导0
		// 查询当天该商店中的订单数量（tableName //订单表的名字   orderNumberChild // 商店id和当前日期拼接字符串）
		String shopIdAndNowDay = adminId.substring(adminId.length()-6, adminId.length()) +  DateUtil.getTimeToString(); // 商店id和当前日期进行拼接
		Map<String, Object> maxDayNoParam = new HashMap<>();
		maxDayNoParam.put("tableName", order_log); // 订单表名
		maxDayNoParam.put("orderIdAndNowDay", shopIdAndNowDay); // 商店id和当前日期进行拼接
		System.out.println("回调接口商店拼接：maxDayNoParam="+maxDayNoParam);
		Map<String, Object> dayNoMap = mWechatCustomerOrderMapper.selectMaxDayNo(maxDayNoParam);
		System.out.println("流水号：dayNoMap="+dayNoMap);
		int dayNo = 0; // 该家商店当天的流水号
		// 判断当天当商店是否有订单卖出
		if(dayNoMap == null) {
			dayNo = 0;
		}else{
			dayNo = Integer.parseInt(dayNoMap.get("MaxDayNo")+""); // 获取当前最大日流水号
		}
		Map<String, Object> params = new HashMap<String, Object>();
		//提取订单日期 拼接订单的表
		params.put("order_log", order_log); // 表名
		params.put("wetherPaymentTime", DateUtil.getTimeAndToString());
		params.put("lastUpdateTime", DateUtil.getTimeAndToString()); // 最后更新时间
		params.put("dayNo", dayNo + 1);// 日流水号增加1
		params.put("orderNumber",resultMap.get("out_trade_no"));
		System.out.println("回调成功修改状态：params="+params);
		mWechatCustomerOrderMapper.updatePayState(params);
		Map<String, Object> deleteShopCartInfo = new HashMap<>();
		deleteShopCartInfo.put("id",StringUtils.substringBeforeLast(attach, ",") + ""); // 登录人id 
		deleteShopCartInfo.put("adminId", StringUtils.substringBefore(attach, ",") + "");  // 商店id
		System.out.println("回调成功删除订单：deleteShopCartInfo="+deleteShopCartInfo);
		// 删除当前用户在指定商店中购物车上中的商品
		mWechatCustomerOrderMapper.deleteShopCartProduct(deleteShopCartInfo);
	}


}  
