package com.wechat.service;
import java.util.HashMap;
import java.util.Map;


import com.ssm.wechatpro.util.TransUtil;

import net.sf.json.JSONObject;

/**
 * 聚合数据发送短信----成功
 * @author 卫志强
 *
 */
public class PhoneMessageService {
	
    //配置您申请的KEY
    public static final String APPKEY = "50ff7fd7734aac010e873d7e04deb0f9";
 
    /**
     * 屏蔽词检查测
     */
    public static void getShielding(String word) throws Exception{
        String result =null;
        String url ="http://v.juhe.cn/sms/black";//请求接口地址
        Map<String,Object> params = new HashMap<>();//请求参数
        params.put("word",word);//需要检测的短信内容，需要UTF8 URLENCODE
        params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
        try {
            result = UtilService.net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if(object.getInt("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 发送短信
     * @throws Exception 
     */
    public static Map<String,Object> sendPhoneMessage(String phone, String word) throws Exception{
        String result =null;
        word = "#code#=" + word;
        Map<String,Object> returnMap = new HashMap<>();
        String url ="http://v.juhe.cn/sms/send";//请求接口地址
        Map<String,Object> params = new HashMap<>();//请求参数
        params.put("mobile",phone);//接收短信的手机号码
        params.put("tpl_id","34089");//短信模板ID，请参考个人中心短信模板设置
        params.put("tpl_value",TransUtil.getURLEncodeByString(word));//变量名和变量值对。如果你的变量名或者变量值中带有#&=中的任意一个特殊符号，请先分别进行urlencode编码后再传递，<a href="http://www.juhe.cn/news/index/id/50" target="_blank">详细说明></a>
        params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
        params.put("dtype","json");//返回数据的格式,xml或json，默认json
        try {
            result = UtilService.net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            returnMap.put("error_code", object.getInt("error_code"));
            returnMap.put("reason", object.getString("reason"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnMap;
    }
 
    public static void main(String[] args) throws Exception{
    	sendPhoneMessage("15090393060","526845");
    }
 
}
