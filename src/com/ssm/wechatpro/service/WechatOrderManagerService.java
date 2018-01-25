package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

public interface WechatOrderManagerService {

	/**
	 * 查询该商店当天所有已经付款的但是没有给顾客的订单（按照流水号排序）
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public void selectPaiedOrderForm(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	/**
	 * 根据订单的id和订单的单号查询订单详情
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public void selectOrderFormInfo (InputObject inputObject, OutputObject outputObject) throws Exception;
	
	/**
	 * 表示做好了给顾客
	 * @param map
	 * @throws Exception
	 */
	public void updateMake(InputObject inputObject, OutputObject outputObject) throws Exception;

	/**
	 * 根据搜索的日期进行查询
	 * @param inputObject
	 * @param outputObject
	 */
	public void selectAllOrderByDate(InputObject inputObject,OutputObject outputObject)throws Exception;
}
