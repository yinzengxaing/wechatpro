package com.ssm.wechatpro.dao;
import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

/**
 *封装退单时的所有dao操作
 *
 * @author 殷曾祥
 * 2017-10-14  下午3:08:53
 */
public interface WechatOutRefundMapper {

	/**
	 * 获取退单列表 分页查询
	 * @param map
	 */
	public List<Map<String, Object>> getOutRefundList(Map<String, Object> map ,PageBounds bounds) throws Exception;
	
	/**
	 * 根据订单 获取订单总价格
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getOrderParams(Map<String, Object> map) throws Exception;
	
	/**
	 * 修改退单的状态 （1：同意退款 2： 不同意退款）
	 * 修改时间
	 * 修改人
	 * @param map
	 * @throws Exception
	 */
	public void updateOutRefund(Map<String, Object> map) throws Exception;
	
	/**
	 * 插入一条新的退单信息
	 * @param map
	 * @throws Exception
	 */
	public void insertOutRefund(Map<String, Object> map) throws Exception;
	
	/**
	 * 退单信息的修改后 更改订单状态的修改
	 * @param map
	 * @throws Exception
	 */
	public void updateOrderState(Map<String, Object> map) throws Exception;
}
