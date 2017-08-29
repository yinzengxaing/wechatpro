package com.ssm.wechatpro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssm.wechatpro.dao.WechatAdminLoginMapper;
import com.ssm.wechatpro.dao.WechatAdminRoleMapper;
import com.ssm.wechatpro.dao.WechatAdminRoleMenuMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatAdminRoleService;
import com.ssm.wechatpro.util.Constants;
import com.ssm.wechatpro.util.DateUtil;
import com.ssm.wechatpro.util.ToolUtil;

@Service
public class WechatAdminRoleServiceImpl implements WechatAdminRoleService {

	@Resource 
	private WechatAdminRoleMapper wechatAdminRoleMapper;
	@Resource
	private WechatAdminRoleMenuMapper wechatAdminRoleMenuMapper;
	@Resource
	private WechatAdminLoginMapper WechatAdminLoginMapper;
	
	/**
	 * 添加角色
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void addRole(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.ROLE_TEST, Constants.ROLE_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		map.put("wechatRoleName", map.get("wechatRoleName").toString());
		map.put("wechatRoleDesc", map.get("wechatRoleDesc").toString());
		map.put("wechatRoleState", Constants.ROLE_STATE);
		map.put("createTime", DateUtil.getTimeAndToString());
		map.put("createId", inputObject.getLogParams().get("id"));
		wechatAdminRoleMapper.addRole(map);
		// 添加权限
		Map<String, Object> role1 = new HashMap<>();
		String sourceStr = map.get("shuzu").toString();// 从页面获取一个字符数组
		String[] str = sourceStr.split(",");
		for (int i = 0; i < str.length; i++) {// 遍历添加
			role1.put("wechatAdminRoleId", map.get("id").toString());// 根据从页面传来的名称查id
			role1.put("wechatAdminMenuId", str[i]);
			role1.put("createId", inputObject.getLogParams().get("id"));
			role1.put("createTime", DateUtil.getTimeAndToString());
			wechatAdminRoleMenuMapper.addUserMenu(role1);
		}
	}
	
	/**
	 * 删除角色
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void deleteById(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.ALL_ROLE, Constants.ALL_ROLE_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		wechatAdminRoleMapper.deleteById(map);
		//删除角色之前已经拥有的权限
		wechatAdminRoleMenuMapper.deleteMenu(map);
		
	}
	
	/**
	 * 查询所有角色
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectAll(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		List<Map<String,Object>> beans = wechatAdminRoleMapper.selectAll(map);
		outputObject.setBeans(beans);
	}

	/**
	 * 根据id查询一个角色
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectById(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.ALL_ROLE, Constants.ALL_ROLE_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		Map<String,Object> bean = wechatAdminRoleMapper.selectById(map);//查询角色
		//查询该角色所拥有的菜单权限
		List<Map<String,Object>> beans = wechatAdminRoleMenuMapper.selectMenuByRole(map);
		String userZ = ";";
		for(Map<String,Object> item : beans){
			userZ = userZ + item.get("id") + ";";
		}
		bean.put("userZ", userZ);
		//输出该角色下的用户
		int userCount = WechatAdminLoginMapper.selectByRole(map);
		bean.put("userCount", userCount);
		outputObject.setBean(bean);
	}

	/**
	 * 修改
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateById(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.ALL_ROLE, Constants.ALL_ROLE_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		//删除角色之前已经拥有的权限
		wechatAdminRoleMenuMapper.deleteMenu(map);
		map.put("wechatRoleState", Constants.WECHAT_ROLE_STATE_CREATE);
		wechatAdminRoleMapper.updateById(map);//修改角色
		//将修改后的权限添加到数据库
		Map<String, Object> role1 = new HashMap<>();
		String sourceStr = map.get("shuzu").toString();// 从页面获取一个字符数组
		String[] str = sourceStr.split(",");
		for (int i = 0; i < str.length; i++) {// 遍历添加
			role1.put("wechatAdminRoleId", map.get("id").toString());// 根据从页面传来的名称查id
			role1.put("wechatAdminMenuId", str[i]);
			role1.put("createId", inputObject.getLogParams().get("id"));
			role1.put("createTime", DateUtil.getTimeAndToString());
			wechatAdminRoleMenuMapper.addUserMenu(role1);
		}
	}

	/**
	 * 修改为审核中状态
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateState(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.ALL_ROLE, Constants.ALL_ROLE_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		wechatAdminRoleMapper.updateState(map);//修改提审状态
	}

	/**
	 * 查询角色上线审核状态
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectByState(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		List<Map<String,Object>> beans = wechatAdminRoleMapper.selectByState(map);
		outputObject.setBeans(beans);
	}

	/**
	 * 修改为审核通过状态
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateStatePass(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		wechatAdminRoleMapper.updateStatePass(map);//修改为审核通过状态
	}

	/**
	 * 修改为审核不通过状态
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateStateNoPass(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		wechatAdminRoleMapper.updateStateNoPass(map);//修改为审核不通过状态
	}

	/**
	 * 只查询上线的角色
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectAllSx(InputObject inputObject, OutputObject outputObject) throws Exception {
		List<Map<String,Object>> beans = wechatAdminRoleMapper.selectAllSx();
		outputObject.setBeans(beans);
	}

}
