package com.ssm.wechatpro.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public interface WechatProductPackageMapper {

	/**
	 * 查询所有套餐
	 * @param map 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectAllPackage(Map<String, Object> map) throws Exception;

	/**
	 *  根据套餐id查看套餐信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectPackageById(Map<String, Object> map) throws Exception;

	/**
	 * 添加一个套餐
	 * @param map
	 * @throws Exception
	 */
	public void insertPackage(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询当前为提审状态的套餐
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> selectProductByState(Map<String, Object> map, PageBounds bounds) throws Exception;
	public List<Map<String,Object>> selectProductByState(Map<String, Object> map) throws Exception;
	
	/**
	 * 更新套餐状态信息
	 * @param map
	 * @throws Exception
	 */
	public void updatePackageState(Map<String,Object> map)throws Exception;
	
	public void updatePackageStatePass(Map<String,Object> map)throws Exception;
	
	public void updatePackageStateNotPass(Map<String,Object> map)throws Exception;
	
	/**
	 * 修改套餐的信息
	 * @param map
	 * @throws Exception
	 */
	public void updatePackageInfo(Map<String, Object> map)throws Exception;
	/**
	 * 通过Id删除套餐(进行逻辑删除)
	 * @param map
	 * @throws Exception
	 */
	public void deletePackageById(Map<String, Object> map) throws Exception;
	/**
	 * 通过套餐中查询出套餐绑定产品ID,查看套餐的详细信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>>selectPackageDetailInfoById(Map<String, Object> map) throws Exception;
	
	
	/**
	 * 根据商品的类别id查询商品信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectProductByProductTypeId(Map<String, Object> map) throws Exception;
	
	/**
	 * 通过登录人账号查询登录人id
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectAdminIdByAdminNo(Map<String, Object> map) throws Exception;

}
