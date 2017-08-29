package com.wechat.service;

import java.io.BufferedInputStream;
import org.apache.commons.httpclient.HttpStatus;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import com.ssm.wechatpro.dao.WechatProductRestaurantMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.util.Constants;
import com.ssm.wechatpro.util.DateUtil;
import com.ssm.wechatpro.util.OrderUtil;
import com.sun.org.apache.bcel.internal.generic.NEW;



public class UnifiedorderService {
	
	@Resource
    private static WechatProductRestaurantMapper  wechatProductRestaurantMapper;
	/** 
     * 微信统一下单接口,获取预支付标示prepay_id 和签名
     * @param out_trade_no1 商户订单号 
     * @param total_fee1 订单金额(单位:分) 
     * @param openid1 网页授权取到的openid 
     * @return 
	 * @throws Exception 
     */  
    @SuppressWarnings("static-access")
	public static Map<String,Object> getPrepayid(String mch_id,String key,String out_trade_no,String total_fee,String openid, InputObject inputObject,OutputObject outputObject) throws Exception{  
    	String result = null;
        //封装h5页面调用参数
        Map<String ,Object> signMap = new HashMap<>();
        String url="https://api.mch.weixin.qq.com/pay/unifiedorder";
        Map<String, Object> params = new HashMap<String, Object>();
        String nonce_str = UUID.randomUUID().toString().substring(0, 32);//生成随机数，可直接用系统提供的方法  
        String body = "maxBurger";  
        params.put("appid", Constants.APPID);  
        params.put("mch_id", mch_id);  
        params.put("nonce_str", nonce_str);  
        params.put("body", body);  
        params.put("out_trade_no", DateUtil.getToString());  
        params.put("total_fee", total_fee);  
        params.put("spbill_create_ip", inputObject.getRequest().getRemoteAddr());  
        params.put("notify_url", "http://z1714z2699.imwork.net/wechatpro/html/phoneModelOne/index.html");  
        params.put("trade_type", "JSAPI"); 
        params.put("openid", openid); 
        String sign = OrderUtil.sign(params, key);  
        params.put("sign", sign);  
        String xmlResult = OrderUtil.ArrayToXml(params);
        try{
			result = post(url,xmlResult);
			Map<String,Object> bean = readStringXmlOut(result);
			String prepayId = bean.get("prepay_id").toString();
	        
	        signMap.put("appId", Constants.APPID);
	        signMap.put("timeStamp", System.currentTimeMillis() / 1000 + "");
	        signMap.put("package", "prepay_id="+prepayId);
	        signMap.put("signType", "MD5");
	        signMap.put("nonceStr", nonce_str);
	        String paySign2 = OrderUtil.sign(signMap, Constants.KEY);
	        signMap.put("paySign", paySign2);
		}catch (Exception e) {
			e.printStackTrace();
		}
        return signMap;
    }  
    
    @SuppressWarnings("deprecation")
	public static String post(String url,String xmlFileName){    
        //关闭   
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");     
        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");     
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "stdout");    
        //创建httpclient工具对象   
        HttpClient client = new HttpClient();    
        //创建post请求方法   
        PostMethod myPost = new PostMethod(url);    
        //设置请求超时时间   
        client.setConnectionTimeout(300*1000);  
        String responseString = null;    
        try{    
            //设置请求头部类型   
            myPost.setRequestHeader("Content-Type","text/xml");  
            myPost.setRequestHeader("charset","utf-8");  
            myPost.setRequestBody(xmlFileName);  
            int statusCode = client.executeMethod(myPost);    
            if(statusCode == HttpStatus.SC_OK){    
                BufferedInputStream bis = new BufferedInputStream(myPost.getResponseBodyAsStream());    
                byte[] bytes = new byte[1024];    
                ByteArrayOutputStream bos = new ByteArrayOutputStream();    
                int count = 0;    
                while((count = bis.read(bytes))!= -1){    
                    bos.write(bytes, 0, count);    
                }    
                byte[] strByte = bos.toByteArray();    
                responseString = new String(strByte,0,strByte.length,"utf-8");    
                bos.close();    
                bis.close();    
            }    
        }catch (Exception e) {    
            e.printStackTrace();    
        }    
        myPost.releaseConnection();    
        return responseString;    
    }  
    
    public static Map<String, Object> readStringXmlOut(String xml) {
        Map<String, Object> map = new HashMap<String, Object>();
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml); // 将字符串转为XML
            Element rootElt = doc.getRootElement(); // 获取根节点
            @SuppressWarnings("unchecked")
            List<Element> list = rootElt.elements();// 获取根节点下所有节点
            for (Element element : list) { // 遍历节点
                map.put(element.getName(), element.getText()); // 节点的name为map的key，text为map的value
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
	
	

}
