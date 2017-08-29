package com.ssm.wechatpro.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssm.wechatpro.dao.MWechatDeliveryAddressMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.MWechatDeliveryAddressService;
import com.ssm.wechatpro.util.Constants;
import com.ssm.wechatpro.util.DateUtil;
import com.ssm.wechatpro.util.JudgeUtil;
import com.ssm.wechatpro.util.ToolUtil;

@Service
public class MWechatDeliveryAddressServiceImpl implements MWechatDeliveryAddressService{

	@Resource
	private MWechatDeliveryAddressMapper mWechatDeliveryAddressMapper;

	/**
	 * 添加收货地址
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void insertDeliveryAddress(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		//收件人、手机号不能为空判断
		if(!ToolUtil.contains(map, Constants.ADD_ADDRESS, Constants.ADD_ADDRESS_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		map.put("deliveryUserId", inputObject.getWechatLogParams().get("id"));
		//将之前的默认地址改变为未使用状态
		mWechatDeliveryAddressMapper.updateNoUse(map);
		//判断手机号是否合格
		if(!JudgeUtil.isPhoneNO(map.get("deliveryPhone").toString())){
			outputObject.setreturnMessage("手机号不合格");
			return;
		}else{
			map.put("createTime", DateUtil.getTimeAndToString());
			map.put("wetherUse", Constants.ADDRESS);
			mWechatDeliveryAddressMapper.insertDeliveryAddress(map);
		}
	}
	
	/**
	 * 查询当前登录用户的所有收货地址信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectByDeliveryUserId(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		map.put("deliveryUserId", inputObject.getWechatLogParams().get("id"));
		List<Map<String,Object>> beans = mWechatDeliveryAddressMapper.selectByDeliveryUserId(map);
		outputObject.setBeans(beans);
	}

	/**
	 * 根据id删除收货地址
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void deleteById(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.ALL_ADDRESS, Constants.ALL_ADDRESS_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		mWechatDeliveryAddressMapper.deleteById(map);
	}

	/**
	 * 编辑收货地址
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateAddress(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.ALL_ADDRESS, Constants.ALL_ADDRESS_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		mWechatDeliveryAddressMapper.updateAddress(map);
	}

	/**
	 * 数据回显
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectById(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.ALL_ADDRESS, Constants.ALL_ADDRESS_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		Map<String,Object> bean = mWechatDeliveryAddressMapper.selectById(map);
		outputObject.setBean(bean);
	}

	/**
	 * 设置为默认地址
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateUse(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.ALL_ADDRESS, Constants.ALL_ADDRESS_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		Map<String,Object> map1 = inputObject.getWechatLogParams();
		map1.put("deliveryUserId", map1.get("id").toString());
		mWechatDeliveryAddressMapper.updateNoUse(map1);
		mWechatDeliveryAddressMapper.updateUse(map);//将地址设置为默认使用地址
	}
}
