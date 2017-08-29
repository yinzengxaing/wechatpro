package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
/**
 * 处理产品品牌的service
 * @author administrator
 *
 */
public interface WechatProductBrandTagService {

	public void getProductBrandTagList(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	public void getProductBrandTagById(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void addProductBrandTag(InputObject inputObject, OutputObject outputObject) throws Exception ;
	
	public void updateProductBrandTag(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	public void getBrandTagOnline(InputObject inputObject, OutputObject outputObject) throws Exception;
	//获取所有待审核的品牌
	public void getCheckPendingList(InputObject inputObject, OutputObject outputObject) throws Exception;
	//审核通过一个品牌
	public void updateStatePass(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	public void updateStateNoPass(InputObject inputObject ,OutputObject outputObject) throws Exception;
}
