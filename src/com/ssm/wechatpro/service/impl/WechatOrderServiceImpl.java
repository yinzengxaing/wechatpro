package com.ssm.wechatpro.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssm.wechatpro.dao.WechatProductRestaurantMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatOrderService;
import com.ssm.wechatpro.util.Constants;
import com.wechat.service.GetJSSDKParameter;
import com.wechat.service.UnifiedorderService;

@Service
public class WechatOrderServiceImpl implements WechatOrderService {
	
	@Resource
    private WechatProductRestaurantMapper  wechatProductRestaurantMapper;
	

	@Override
	public void getOrderParameter(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		Map<String,Object> beanmessage = wechatProductRestaurantMapper.selectCardAndKey(params);
        String mch_id = beanmessage.get("adminShopCard").toString();
        String key = beanmessage.get("adminShopKey").toString();
		String out_trade_no = params.get("out_trade_no").toString();// 商户订单号
		String total_fee = params.get("total_fee").toString();// 标记金额
		String openid = params.get("openid").toString();
		String adminId = params.get("adminId").toString();
		
		// 预支付标识
		Map<String, Object> message = UnifiedorderService.getPrepayid(mch_id,key,out_trade_no, total_fee, openid, inputObject, outputObject);// 获取预支付标示
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
		outputObject.setBean(map);
		
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

}  
