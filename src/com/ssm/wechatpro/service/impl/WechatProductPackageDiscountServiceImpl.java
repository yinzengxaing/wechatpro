package com.ssm.wechatpro.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.wechatpro.dao.WechatProductPackageDiscountMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatProductPackageDiscountService;
import com.ssm.wechatpro.util.DateUtil;

@Service("wechatProductPackageDiscountService")
public class WechatProductPackageDiscountServiceImpl implements WechatProductPackageDiscountService{
	
	@Autowired
	WechatProductPackageDiscountMapper wechatProductPackageDiscountMapper;
	
	/**
	 * 显示所有的打折套餐
	 */
	@Override	
	public void selectAllPackageDiscount(InputObject inputObject,OutputObject outputObject) throws Exception {
		List<Map<String, Object>> list = wechatProductPackageDiscountMapper.selsctAllPackageDiscount();
		outputObject.setBeans(list);
	}
	
	/**
	 * 添加新的打折表
	 */
	@Override
	public void insertPackageDiscount(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		map.put("createTime", DateUtil.getTimeAndToString()); // 创建时间
		wechatProductPackageDiscountMapper.insertPackageDiscount(map);
	}

	/**
	 * 修改打折表
	 * */
	@Override
	public void modifyPackageDiscount(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		wechatProductPackageDiscountMapper.modifyPackageDiscount(map);
	}

	/**
	 * 删除打折套餐（设置为不优惠）
	 * */
	@Override
	public void deletePackageDiscount(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		wechatProductPackageDiscountMapper.deletePackageDiscount(map); // 逻辑删除
				
	}

}
