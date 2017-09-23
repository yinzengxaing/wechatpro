package com.ssm.wechatpro.dao;

import java.util.Map;

/**
 * 排序菜单的mapper
 *
 * @author 殷曾祥
 * 2017-9-23  上午10:14:42
 */
public interface WechatProductMenuMapper {
	
	//类别的排序
	public void updateTypeMenu(Map<String, Object> map) throws Exception;

	//商品的排序
	public void updateProductMenu(Map<String, Object> map) throws Exception;
}
