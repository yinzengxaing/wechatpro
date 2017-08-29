package com.ssm.wechatpro.service.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.ssm.wechatpro.dao.WechatAppMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatAppService;
import com.ssm.wechatpro.util.Constants;
import com.ssm.wechatpro.util.DateUtil;
import com.ssm.wechatpro.util.JudgeUtil;
import com.ssm.wechatpro.util.ToolUtil;

@Service
public class WechatAppServiceImpl implements WechatAppService {

	@Resource
	WechatAppMapper wechatAppMapper;
	

	/***
	 * 添加一个app用户信息
	 * @amparam inputObject
	 * @par outputObject
	 * @throws ExceptionS
	 */
	@Override
	public void addWechatApp(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		if(!ToolUtil.contains(params,Constants.WECHATAPP_KEY,Constants.WECHATAPP_RETURNMESSAGE, inputObject, outputObject)){
			return ;
		}
		// 判断该用户是否存在
		List<Map<String, Object>> app = wechatAppMapper.getApp();
		if (!app.isEmpty()) {
			outputObject.setreturnMessage("该公众号信息已经存在");
		} else {
			if (JudgeUtil.isNull(params.get("appId").toString()))
				outputObject.setreturnMessage("appId的值不能为空");
			else if (JudgeUtil.isNull(params.get("appSecret").toString()))
				outputObject.setreturnMessage("appSecret的值不能为空");
			else {
				params.put("createId", inputObject.getLogParams().get("id"));
				params.put("createTime", DateUtil.getTimeAndToString());
				wechatAppMapper.addApp(params);
				outputObject.setBean(params);
			}
		}
	}

	/***
	 * 修改app用户信息
	 * @amparam inputObject
	 * @par outputObject
	 * @throws Exception
	 */
	@Override
	public void updateApp(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		if(!ToolUtil.contains(params,Constants.WECHATAPP_KEY,Constants.WECHATAPP_RETURNMESSAGE, inputObject, outputObject)){
			return ;
		}
		if (JudgeUtil.isNull(params.get("appId").toString()))
			outputObject.setreturnMessage("appId的值不能为空");
		else if (JudgeUtil.isNull(params.get("appSecret").toString()))
			outputObject.setreturnMessage("appSecret的值不能为空");
		else
			wechatAppMapper.updateApp(params);
	}

	/***
	 * 查询app用户信息
	 * @amparam inputObject
	 * @par outputObject
	 * @throws Exception
	 */
	@Override
	public void selectApp(InputObject inputObject, OutputObject outputObject) throws Exception {
		List<Map<String, Object>> app = wechatAppMapper.getApp();
		if (!app.isEmpty()) {
			outputObject.setBean(app.get(0));
		} else {
			outputObject.setBean(null);
		}
	}
}
