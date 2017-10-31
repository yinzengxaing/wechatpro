package com.wechat.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import javax.net.ssl.SSLContext;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.util.Constants;
import com.ssm.wechatpro.util.OrderUtil;
import com.ssm.wechatpro.util.WeixinUtil;

/**
 * 退款
 *mch_id:微信支付分配的商户号
 *out_trade_no ：系统内部生成的订单号
 *total_fee：订单金额
 *refund_fee：退款金额
 *refund_desc：退款原因
 *key：商户的key
 * @author 殷曾祥
 * 2017-10-9  下午8:49:22
 */
public class RefundService {
	
	
	public static Map<String, Object> getRefundParams(String sub_mch_id,String out_trade_no,String total_fee,String refund_fee, InputObject inputObject,OutputObject outputObject)throws Exception{
	       Map<String,Object> parameters = new HashMap<String,Object>(); 
	       parameters.put("appid", Constants.APPID);//appid  
	       parameters.put("mch_id", Constants.MCH_ID);//商户号  
	       parameters.put("nonce_str", CreateNoncestr());  
	      //在notify_url中解析微信返回的信息获取到 transaction_id，此项不是必填，详细请看上图文档  
	      // parameters.put("transaction_id", "4200000016201710178680537928");  
	       parameters.put("out_trade_no", out_trade_no);//订单号  
	       parameters.put("out_refund_no", out_trade_no);//我们自己设定的退款申请号，约束为UK  
	       parameters.put("total_fee", total_fee) ;//单位为分  
	       parameters.put("refund_fee", refund_fee);//单位为分  
	       parameters.put("sub_mch_id", sub_mch_id);
	       parameters.put("op_user_id", Constants.MCH_ID);//操作人员,默认为商户账号  
	       String sign = OrderUtil.sign(parameters, Constants.KEY);  
	       parameters.put("sign", sign);  
	         
	      String reuqestXml = getRequestXml(parameters);  
	      KeyStore keyStore  = KeyStore.getInstance("PKCS12");  
	      FileInputStream instream = new FileInputStream(new File(Constants.SSL_PATH));//放退款证书的路径  
	      try {  
	          keyStore.load(instream, Constants.MCH_ID.toCharArray());//"商户号  
	      } finally {  
	          instream.close();  
	      }  
	  
	      SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, Constants.MCH_ID.toCharArray()).build();//"123456"指的是商户号  
	      SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(  
	              sslcontext,  
	              new String[] { "TLSv1" },  
	              null,  
	              SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);  
	      CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();  
          Map<String, Object> returnMap = new HashMap<>();
	      try {  
	  
	          HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/secapi/pay/refund");//退款接口  
	            
	          System.out.println("executing request" + httpPost.getRequestLine());  
	          StringEntity  reqEntity  = new StringEntity(reuqestXml);  
	          // 设置类型  
	          reqEntity.setContentType("application/x-www-form-urlencoded");  
	          httpPost.setEntity(reqEntity);  
	          CloseableHttpResponse response = httpclient.execute(httpPost);  
	          try {  
	              HttpEntity entity = response.getEntity();  
	  
	              System.out.println("----------------------------------------");  
	              System.out.println(response.getStatusLine());  
	              if (entity != null) {  
	                  System.out.println("Response content length: " + entity.getContentLength());  
	                  BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(),"UTF-8"));  
	                  String text;  
	                  while ((text = bufferedReader.readLine()) != null) { 
	                	  returnMap.put("returnText",text );
	                      System.out.println(text);  
	                  }  
	                   
	              }  
	              EntityUtils.consume(entity);  
	          } finally {  
	              response.close();  
	          }  
	      } finally {  
	          httpclient.close();  
	      }
		return returnMap;  
		
	}
    public static String CreateNoncestr() {  
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";  
        String res = "";  
        for (int i = 0; i < 16; i++) {  
            Random rd = new Random();  
            res += chars.charAt(rd.nextInt(chars.length() - 1));  
        }  
        return res;  
      } 
    
    public static String getRequestXml(Map<String, Object> parameters){  
        StringBuffer sb = new StringBuffer();  
        sb.append("<xml>");  
        Set es = parameters.entrySet();  
        Iterator it = es.iterator();  
        while(it.hasNext()) {  
          Map.Entry entry = (Map.Entry)it.next();  
          String k = (String)entry.getKey();  
          String v = (String)entry.getValue();  
          if ("attach".equalsIgnoreCase(k)||"body".equalsIgnoreCase(k)||"sign".equalsIgnoreCase(k)) {  
              sb.append("<"+k+">"+"<![CDATA["+v+"]]></"+k+">");  
          }else {  
              sb.append("<"+k+">"+v+"</"+k+">");  
          }  
        }  
        sb.append("</xml>");  
        return sb.toString();  
    } 
}
