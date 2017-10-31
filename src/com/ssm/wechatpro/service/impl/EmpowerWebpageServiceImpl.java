package com.ssm.wechatpro.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssm.wechatpro.dao.WechatUserMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.EmpowerWebpageService;
import com.ssm.wechatpro.util.Constants;
import com.wechat.service.GetOpenIdByCode;

@Service
public class EmpowerWebpageServiceImpl implements EmpowerWebpageService {

	@Resource
	WechatUserMapper wechatUserMapper;
	
	/**
	 * 通过code获取openid
	 * 
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void getOpenidBycode(InputObject inputObject,OutputObject outputObject) throws Exception{
		//判断session 中是否已经存在用户 、不存在会抛出空指针异常
		Map<String, Object> map = inputObject.getWechatLogParams();//获取openid
		try {
			int size = map.size();
			if (size >= 0  || !map.isEmpty()){
				outputObject.setBean(map);
			}else{
				Map<String ,Object> params = inputObject.getParams();
				String code = (String) params.get("code");
				Map<String,Object> bean = GetOpenIdByCode.getRequest1(Constants.APPID, Constants.APPSECRET, code);
				if(bean == null){
					outputObject.setreturnMessage("session为空");
					return;
				}
				Map<String,Object> user = wechatUserMapper.selectWechatUserByOpenId(bean);
				outputObject.setWechatLogParams(user);
				outputObject.setBean(user);
			}
		} catch (Exception e) {
			Map<String ,Object> params = inputObject.getParams();
			String code = (String) params.get("code");
			Map<String,Object> bean = GetOpenIdByCode.getRequest1(Constants.APPID, Constants.APPSECRET, code);
			if(bean == null){
				outputObject.setreturnMessage("session为空");
				return;
			}
			Map<String,Object> user = wechatUserMapper.selectWechatUserByOpenId(bean);
			outputObject.setWechatLogParams(user);
			outputObject.setBean(user);
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
