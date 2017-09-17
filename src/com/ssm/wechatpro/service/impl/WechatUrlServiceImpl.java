package com.ssm.wechatpro.service.impl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatUrlService;
import com.ssm.wechatpro.util.Constants;

@Service
public class WechatUrlServiceImpl implements WechatUrlService {

	@Override
	public void getUrlList(InputObject inputObject, OutputObject outputObject) throws Exception {
		List<Map<String,Object>> beans = new ArrayList<Map<String,Object>>();
		beans.add(index());
		beans.add(myMation());
		beans.add(restaurantList());
		beans.add(shoppingCart());
		outputObject.setBeans(beans);
	}
	
	//主页
	public Map<String,Object> index() throws Exception{
		String uuid = UUID.randomUUID().toString();
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+Constants.APPID+"&redirect_uri="
				+ URLEncoder.encode(Constants.PATH+"/html/phoneModelOne/index.html?typeId=1","UTF-8")
				+"&response_type=code&scope=snsapi_userinfo&state="+uuid+"#wechat_redirect";
		Map<String,Object> bean = new HashMap<String, Object>();
		bean.put("web", "主页");	
		bean.put("url", url);
		return bean;
	}
	//我的信息
	public Map<String,Object> myMation() throws Exception{
		String uuid = UUID.randomUUID().toString();
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+Constants.APPID+"&redirect_uri="
				+ URLEncoder.encode(Constants.PATH+"/html/phoneModelOne/myMation.html?typeId=5","UTF-8")
				+"&response_type=code&scope=snsapi_userinfo&state="+uuid+"#wechat_redirect";
		Map<String,Object> bean = new HashMap<String, Object>();
		bean.put("web", "我的信息");
		bean.put("url", url);
		return bean;
	}
	//购买商品
	public Map<String,Object> restaurantList() throws Exception{
		String uuid = UUID.randomUUID().toString();
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+Constants.APPID+"&redirect_uri="
				+ URLEncoder.encode(Constants.PATH+"/html/phoneModelOne/restaurantList.html?typeId=3","UTF-8")
				+"&response_type=code&scope=snsapi_userinfo&state="+uuid+"#wechat_redirect";
		Map<String,Object> bean = new HashMap<String, Object>();
		bean.put("web", "购买商品");
		bean.put("url", url);
		return bean;
		}
	//购物车
	public Map<String,Object> shoppingCart() throws Exception{
		String uuid = UUID.randomUUID().toString();
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+Constants.APPID+"&redirect_uri="
				+ URLEncoder.encode(Constants.PATH+"/html/phoneModelOne/shoppingCart.html?typeId=4","UTF-8")
				+"&response_type=code&scope=snsapi_userinfo&state="+uuid+"#wechat_redirect";
		Map<String,Object> bean = new HashMap<String, Object>();
		bean.put("web", "购物车");
		bean.put("url", url);
		return bean;
		}
	

}
