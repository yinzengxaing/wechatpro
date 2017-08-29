package com.ssm.util;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ssm.wechatpro.dao.WechatAppMapper;
import com.ssm.wechatpro.util.WeixinUtil;

public class InitServlet extends HttpServlet {  
    private static final long serialVersionUID = 1L;  
    private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);  
    
    private WechatAppMapper wechatAppMapper;
  
    public void init() throws ServletException {
    	ServletContext servletContext = this.getServletContext();   
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);  
        wechatAppMapper = (WechatAppMapper)ctx.getBean("wechatAppMapper");  
        List<Map<String,Object>> beans = null;
    	try {
			beans = wechatAppMapper.getApp();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	if(beans.isEmpty()){
    		log.error("appid and appsecret configuration error, please check carefully.");  
    	}else{
    		TokenThread.appid = beans.get(0).get("appId").toString();
    		TokenThread.appsecret = beans.get(0).get("appSecret").toString();	
    		log.info("weixin api appid:{}", TokenThread.appid);  
    		log.info("weixin api appsecret:{}", TokenThread.appsecret);  
    		// 未配置appid、appsecret时给出提示  
    		if ("".equals(TokenThread.appid) || "".equals(TokenThread.appsecret)) {  
    			log.error("appid and appsecret configuration error, please check carefully.");  
    		} else {  
    			// 启动定时获取access_token的线程  
    			new Thread(new TokenThread()).start();  
    		}  
    	}
    }

}  
