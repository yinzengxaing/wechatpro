package com.ssm.wechatpro.service.impl;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ssm.wechatpro.dao.WechatUserMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.EmpowerWebpageService;
import com.ssm.wechatpro.util.Constants;
import com.wechat.service.GetCity;
import com.wechat.service.GetOpenIdByCode;

@Service
public class EmpowerWebpageServiceImpl  implements EmpowerWebpageService {
	@Resource
	WechatUserMapper wechatUserMapper;
	private static Logger logger = LoggerFactory.getLogger(EmpowerWebpageServiceImpl.class);  
	
	/**
	 * 通过code获取openid
	 * 
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void getOpenidBycode(InputObject inputObject,OutputObject outputObject,ServletRequest request) throws Exception{
		//判断session 中是否已经存在用户 、不存在会抛出空指针异常
		Map<String, Object> user = inputObject.getWechatLogParams();//获取openid
		Map<String ,Object> params = inputObject.getParams();
		String code = params.get("code").toString();
		//获取用户的地理位置
		Map<String,Object> city = GetCity.getUserCity(params.get("latitude").toString(), params.get("longitude").toString());
		String Location = city.get("city").toString();
		Map<String,Object> bean = GetOpenIdByCode.getRequest1(Constants.APPID, Constants.APPSECRET, code);
		//用户请求不是从前台发出的
		if (bean == null){
			user.put("latitude", params.get("latitude").toString());
			user.put("longitude", params.get("longitude").toString());
			user.put("Location", Location);
			outputObject.setWechatLogParams(user);
			outputObject.setBean(user);
		}else{
			try {
				//当前session中的用户和发送消息获得的用户不想符合
				if (!user.isEmpty()){
					if (!user.get("openid").equals(bean.get("openid"))){ 
						user =  wechatUserMapper.selectWechatUserByOpenId(bean);
						user.put("latitude", params.get("latitude").toString());
						user.put("longitude", params.get("longitude").toString());
						user.put("Location", Location);
						outputObject.setWechatLogParams(user);
					}
				}
			//未获取到用户session中不存在用户	
			} catch (Exception e) {
				user =  wechatUserMapper.selectWechatUserByOpenId(bean);
			}finally{
				try {
					outputObject.setWechatLogParams(user);
				//session没有初始化
				} catch (Exception e2) {
					((HttpServletRequest) request).getSession().setAttribute("admTsyWechatUser", user);
					logger.error("login-error={}",e2);
				}
				user.put("latitude", params.get("latitude").toString());
				user.put("longitude", params.get("longitude").toString());
				user.put("Location", Location);
				outputObject.setBean(user);
				System.out.println(user);
			}
		}

	}
	
	/**
	 * 更新session
	 * 
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void updateSession(InputObject inputObject,OutputObject outputObject) throws Exception{
		Map<String,Object> map = inputObject.getParams();//获取新的城市
		Map<String,Object> bean = inputObject.getWechatLogParams();
		bean.put("Location", map.get("Location").toString());//更新
		outputObject.setWechatLogParams(bean);
	}

}
