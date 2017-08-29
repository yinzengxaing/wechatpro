package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

/**
 * 餐厅人员管理商品
* @author administrator+
* @param 
* @throws 
 */
public interface WechatCanteenProductManageService {

	
	/**
	 * 显示所选产品的种类信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void selectProductTypeByChoose(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	/**
	 * 显示所选择的产品信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void selectProductByChoose(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	/**
	 * 显示所选择的常规套餐的信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void selectPackageProductByChoose(InputObject inputObject, OutputObject outputObject) throws Exception ;
	
	/**
	 * 显示所选择的可选套餐的信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void selectChoosePackageByChoose(InputObject inputObject, OutputObject outputObject) throws Exception ;

	/**
	 * 查询数量
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void selectNum (InputObject inputObject, OutputObject outputObject) throws Exception;
	/**
	 * 更新商品数量
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void updateProductNum(InputObject inputObject, OutputObject outputObject) throws Exception ;
	
	/**
	 * 更新当前的商品数量
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void updateProductNowNum(InputObject inputObject, OutputObject outputObject) throws Exception ;
	
	/**
	 * 商品上下线操作
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void updateProductState(InputObject inputObject, OutputObject outputObject) throws Exception ;
	
	/**
	 * 表示用现金支付的用户，在指定的商品上加一
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void updateCashProple(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	/**
	 * 查看商店的详细信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void selectForShopInfo(InputObject inputObject, OutputObject outputObject) throws Exception;	
}
