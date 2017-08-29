package com.ssm.wechatpro.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssm.wechatpro.dao.WechatShoppingCartMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatShoppingCartService;
import com.ssm.wechatpro.util.DateUtil;

@Service
public class WechatShoppingCartServiceImpl implements WechatShoppingCartService {

	@Resource
	private WechatShoppingCartMapper wechatShoppingCartMapper;

	/**
	 * 根据购物车所属人id显示该用户购物车中的信息
	 * 
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectAllShoppingCart(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> wechatCustomerLoginId = inputObject.getParams();// 获取参数
		// 获取当前用户的id，然后放入map中到数据库进行查询
		List<Map<String, Object>> info = wechatShoppingCartMapper.selectAllShoppingCart(wechatCustomerLoginId);
		outputObject.setBeans(info);
	}

	/**
	 * 向购物车中添加商品
	 * 
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void insertShoppingCart(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> product = inputObject.getParams();
		// 获取登录人ID
//		HttpSession session = inputObject.getRequest().getSession();
//		String wechatCustomerLoginId = session.getAttribute("createdId");
//		product.put("wechatCustomerLoginId", wechatCustomerLoginId);
		product.put("createTime", DateUtil.getTimeAndToString());
		// 获取当前用户的id，然后放入map中到数据库进行查询
		wechatShoppingCartMapper.insertShoppingCart(product); // 插入数据
	}

	/**
	 * 删除购物车中的信息(通过所属人id和商品id)
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void deleteShopptinCartByCustomerLoginIdAndCommodity(InputObject inputObject, OutputObject outputObject)throws Exception {
		// id 表示所属人id 和 商品id
		Map<String, Object> id = inputObject.getParams();
		wechatShoppingCartMapper.deleteShopptinCartByCustomerLoginIdAndCommodity(id);
	}

}
