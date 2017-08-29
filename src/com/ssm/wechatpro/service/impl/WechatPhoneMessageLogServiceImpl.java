package com.ssm.wechatpro.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssm.wechatpro.dao.WechatPhoneMessageLogMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatPhoneMessageLogService;
import com.ssm.wechatpro.util.Constants;
import com.ssm.wechatpro.util.DateUtil;
import com.ssm.wechatpro.util.JudgeUtil;
import com.ssm.wechatpro.util.MessageSendUtil;
import com.wechat.service.PhoneMessageService;

@Service
public class WechatPhoneMessageLogServiceImpl implements WechatPhoneMessageLogService{

	@Resource
	WechatPhoneMessageLogMapper wechatPhoneMessageLogMapper;
	

	/**
     * 添加手机验证信息
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
	@Override
	public void insertPhoneMessage(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		String word = MessageSendUtil.getWord();// 获取验证码
		map.put("monthTable", Constants.MONTH_TABLE+DateUtil.getTimeSixAndToString());
		map.put("wetherSend", 0);//发送短信失败（0）
		String phone = map.get("phoneNum").toString();// 获取手机号
		if (JudgeUtil.isPhoneNO(phone)) {// 手机号合格
			Map<String, Object> bean = PhoneMessageService.sendPhoneMessage(phone, word);// 发送短信
			if (bean.get("error_code").toString().equals("0")) {
				map.put("wetherSend", 1);// 发送短息成功（1）
			}else{
				outputObject.setreturnMessage(bean.get("reason").toString());
			}
		} else if (phone.isEmpty()) {
			outputObject.setreturnMessage("手机号不能为空,请输入手机号！");
		} else {
			outputObject.setreturnMessage("手机号不合格");
		}
		map.put("phoneMessage", word);
		map.put("createTime", DateUtil.getTimeAndToString());
		wechatPhoneMessageLogMapper.insertPhoneMessage(map);//将手机验证等信息添加到数据库
		
		Map<String,Object> bean = new HashMap<>();
		bean.put("phoneMessage", word);
		outputObject.setBean(bean);//将验证码显示在页面
	}

}
