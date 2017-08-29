package com.ssm.wechatpro.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public interface WechatProductPackageChooseMapper {
	/**
	 * 
	 * 添加可选套餐分组
	 */
	public void AddWechatProductChooseGroup(Map<String, Object> map) throws Exception;
	/**
	 * 添加可选套餐
	 * @param params
	 */
	public void AddWechatProductChoosePackage(Map<String, Object> params) throws Exception;
	
	/**
	 * 查找所以多选套餐
	 * @param params
	 */
	public List<Map<String, Object>> selectAllPackage(Map<String, Object> map) throws Exception;

	/**
	 * 查看wechat_product_choose_package表详情
	 * @param params
	 */
	public Map<String, Object> selectOneWechatProductChoosePackage(Map<String, Object> map) throws Exception;
	/**
	 * 更具多个id查看wechat_product_choose_group表
	 * @param params
	 */
	public List<Map<String, Object>> selectWechatProductChooseGroupByIds(Map<String, Object> map) throws Exception;

	/**
	 * 更具多个id查看wechat_product表
	 * @param params
	 */
	public List<Map<String, Object>> selectwechatProductByIds(Map<String, Object> map) throws Exception;
	/**
	 * 删除wechat_product_choose_group 
	 * @param params
	 */
	public void delectWechatProductChooseGroup(Map<String, Object> map) throws Exception;
	/**
	 * 删除wechat_product_choose_package
	 * @param params
	 */
	public void delectWechatProductChoosePackage(Map<String, Object> map) throws Exception;
	/**
	 * 修改状态
	 * @param params
	 */
	public void updatePackageState(Map<String, Object> map) throws Exception;
	/**
	 * 
	 * 修改可选套餐分组
	 */
	public void updateWechatProductChooseGroup(Map<String, Object> map) throws Exception;
	/**
	 * 修改可选套餐
	 * @param params
	 */
	public void updateWechatProductChoosePackage(Map<String, Object> params) throws Exception;
	/**
	 * 查询价格
	 * @param params
	 */
	public  List<Map<String, Object>> selectwechatProductWithProductPrice(Map<String, Object> params) throws Exception;
	
	/**
	 * 查询待审核状态的套餐信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getCheckPendingList (Map<String, Object> map, PageBounds bounds) throws Exception;
	public List<Map<String,Object>> getCheckPendingList (Map<String, Object> map) throws Exception;
	
	/**
	 * 审核通过的可选套餐
	 * @param map
	 * @throws Exception
	 */
	public void updatePackageStatePass(Map<String,Object> map)throws Exception;
	
	/**
	 * 审核不通过的可选套餐
	 * @param map
	 * @throws Exception
	 */
	public void updatePackageStateNotPass(Map<String,Object> map)throws Exception;
	
	/**
	 * 提交审核 
	 * @param map
	 * @throws Exception
	 */
	public void updateChoosePackageState(Map<String,Object> map)throws Exception;
}
