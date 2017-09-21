package com.ssm.wechatpro.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssm.wechatpro.dao.WechatProductMapper;
import com.ssm.wechatpro.dao.WechatProductTypeMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatProductMenuService;

/** 
 *创建商店菜单的实现类
 * @author 殷曾祥
 * 2017-9-20  下午7:57:03
 */
@Service
public class WechatProductMenuServiceImpl implements WechatProductMenuService {
	
	@Resource
	private WechatProductTypeMapper wechatProductTypeMapper;
	@Resource
	private WechatProductMapper wechatProductMapper;
	
	
	@Override
	public void getTypeList(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		//获取类别的列表
		List<Map<String,Object>> productTypeList = wechatProductTypeMapper.getProductTypeList(params);
		outputObject.settotal(productTypeList.size());
		outputObject.setBeans(productTypeList);
	}

	@Override
	public void getProductListByTypeId(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		//根据类别获取商品的信息
		List<Map<String,Object>> productList = wechatProductMapper.getProductList(params);
		outputObject.setBeans(productList);
		outputObject.settotal(productList.size());
		
	}

}
