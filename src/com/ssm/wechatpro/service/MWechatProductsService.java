package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

/**
 * 手机端获取商品信息的service
 * @author yinzengxiang
 *
 */
public interface MWechatProductsService {

	public void getTypeList(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void getProductList(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	public void getPackageList(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	public void getProductListForIndex(InputObject inputObject,OutputObject outputObject)throws Exception;
	
}
