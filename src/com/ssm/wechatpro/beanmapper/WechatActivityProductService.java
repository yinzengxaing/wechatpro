package com.ssm.wechatpro.beanmapper;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

/**
 *特价商品活动相关的service 
 *
 * @author 殷曾祥
 * 2017-9-24  下午7:47:18
 */
public interface WechatActivityProductService {
	
	public void addProductActivity(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void getProductList(InputObject inputObject,OutputObject outputObject) throws Exception;
	
}
