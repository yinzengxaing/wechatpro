package com.ssm.wechatpro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ssm.wechatpro.dao.WechatAdminMenuMapper;
import com.ssm.wechatpro.dao.WechatAdminRoleMenuMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatAdminMenuService;
import com.ssm.wechatpro.util.Constants;
import com.ssm.wechatpro.util.DateUtil;
import com.ssm.wechatpro.util.ToolUtil;

@Service
public class WechatAdminMenuServiceImpl implements WechatAdminMenuService{
	
	@Resource
	private WechatAdminMenuMapper wechatAdminMenuMapper;
	@Resource
	private WechatAdminRoleMenuMapper wechatAdminRoleMenuMapper;

	/**
	 * 添加菜单
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void insertAdminMenu(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.MENU_TEST, Constants.MENU_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		if(map.get("menuLevel").toString().equals("1")){
			map.put("belongto", Constants.FIRST_MENU);//菜单id为0，是父菜单
			map.put("jibie",map.get("menuLevel"));
		}else{
			map.put("belongto", map.get("fatherId"));//从页面获取父菜单id
			map.put("jibie",map.get("menuLevel"));
		}
		map.put("createTime", DateUtil.getTimeAndToString());
		map.put("createId", inputObject.getLogParams().get("id"));//此菜单创建人id
		wechatAdminMenuMapper.insertAdminMenu(map);	
	}
 
	
	/**
	 * 根据id删除一个菜单（角色管理）
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void deleteMenuById(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.ALL_MENU, Constants.ALL_MENU_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		Map<String,Object> menu = new HashMap<>();
		menu = wechatAdminMenuMapper.selectById(map);
		//判断菜单级别
		if(menu.get("jibie").toString().equals("1")){//如果是一级菜单，
			Map<String,Object> id = new HashMap<>();
			id.put("belongto", menu.get("id"));
			if(wechatAdminMenuMapper.selectBybelong(id).size()==0){//没有子菜单，直接删除
				wechatAdminRoleMenuMapper.deleteMenuByRoleId(map);
			}else{//如果有子菜单,先删除一级菜单及其权限
				wechatAdminRoleMenuMapper.deleteMenuByRoleId(map);
				//再删除其子菜单及其权限
				List<Map<String,Object>> list = wechatAdminMenuMapper.selectBybelong(id);
				for(int i=0;i<list.size();i++){
					Map<String,Object> mid = new HashMap<>();
					mid.put("id", list.get(i).get("id"));
					//删除子菜单和其权限
					wechatAdminRoleMenuMapper.deleteMenuByRoleId(mid);
				}			
			}
		}else{//如果是二级菜单
			wechatAdminRoleMenuMapper.deleteMenuByRoleId(map);
		}
	}

	/**
	 * 根据id删除一个菜单（没有角色管理）
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void deleteById(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.ALL_MENU, Constants.ALL_MENU_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		Map<String,Object> menu = new HashMap<>();
		menu = wechatAdminMenuMapper.selectById(map);
		//判断菜单级别
		if(menu.get("jibie").toString().equals("1")){//如果是一级菜单，
			Map<String,Object> id = new HashMap<>();
			id.put("belongto", menu.get("id"));
			if(wechatAdminMenuMapper.selectBybelong(id).size()==0){//没有子菜单，直接删除
				wechatAdminMenuMapper.deleteById(map);
			}else{
				wechatAdminMenuMapper.deleteById(map);
				//再删除其子菜单及其权限
				List<Map<String,Object>> list = wechatAdminMenuMapper.selectBybelong(id);
				for(int i=0;i<list.size();i++){
					Map<String,Object> mid = new HashMap<>();
					mid.put("id", list.get(i).get("id"));
					//删除子菜单和其权限
					wechatAdminMenuMapper.deleteById(mid);
				}	
			}
		}else{
			wechatAdminMenuMapper.deleteById(map);
		}
		
	}
	
	/**
	 * 修改菜单信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateById(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		//判断要修改的菜单是否为空
		if(!ToolUtil.contains(map, Constants.ALL_MENU, Constants.ALL_MENU_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		wechatAdminMenuMapper.updateById(map);
	}

	/**
	 * 查询全部菜单信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectAll(InputObject inputObject, OutputObject outputObject) throws Exception {		
		Map<String,Object> map = inputObject.getParams();
		//查询所有列表菜单（这里也可以根据菜单级别查询）
		List<Map<String,Object>> beans = wechatAdminMenuMapper.selectAll(map);
		for(Map<String,Object> bean : beans){
			if(bean.get("pId").equals("0")){
				bean.put("isParent", true);
			}
		}
		outputObject.setBeans(beans);
	}
	
	/**
	 * 查询全部菜单信息（用于菜单列表）
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectAllMenuList(InputObject inputObject, OutputObject outputObject) throws Exception {		
		Map<String,Object> map = inputObject.getParams();
		int page = Integer.parseInt(map.get("offset").toString())/Integer.parseInt(map.get("limit").toString());
		page++;
		int limit = Integer.parseInt(map.get("limit").toString());
		//查询所有列表菜单（这里也可以根据菜单级别查询）
		List<Map<String,Object>> beans = wechatAdminMenuMapper.selectAllMenuList(map,new PageBounds(page,limit));
		PageList<Map<String, Object>> abilityInfoPageList = (PageList<Map<String, Object>>)beans;
		for(Map<String,Object> bean : beans){
			if(bean.get("pId").equals("0")){
				bean.put("isParent", true);
			}
		}
		int total = abilityInfoPageList.getPaginator().getTotalCount();
		outputObject.setBeans(beans);
		outputObject.settotal(total);
	}
	
	

	/**
	 * 根据id查询一条菜单信息，用于数据回显
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectById(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		//判断要修改的菜单是否为空
		if(!ToolUtil.contains(map, Constants.ALL_MENU, Constants.ALL_MENU_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		Map<String,Object> bean = wechatAdminMenuMapper.selectById(map);
		outputObject.setBean(bean);
	}

	/**
	 * 查询一级菜单
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectFirst(InputObject inputObject, OutputObject outputObject) throws Exception {
		List<Map<String,Object>> beans = wechatAdminMenuMapper.selectFirst();
		outputObject.setBeans(beans);
	}


	/**
	 * 查询二级菜单
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectSecondMenu(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.ALL_MENU, Constants.ALL_MENU_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		List<Map<String,Object>> beans = wechatAdminMenuMapper.selectSecondMenu(map);
		outputObject.setBeans(beans);
	}


	/**
	 * 添加菜单所拥有的权限
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void insertPower(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();//获取创建人id，二级菜单id(icon)
		if(!ToolUtil.contains(map, Constants.ADD_POWER, Constants.ADD_POWER_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		map.put("belongto", map.get("id").toString());
		map.put("jibie", Constants.JIBIE);
		map.put("createTime", DateUtil.getTimeAndToString());
		map.put("createId",inputObject.getLogParams().get("id").toString());
		wechatAdminMenuMapper.insertPower(map);
	}


	/**
	 * 查询二级菜单所拥有的权限
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectPower(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.ALL_MENU, Constants.ALL_MENU_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		List<Map<String,Object>> beans = wechatAdminMenuMapper.selectPower(map);
		outputObject.setBeans(beans);
	}


	/**
	 * 修改二级菜单所拥有的权限
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updatePower(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.ALL_MENU, Constants.ALL_MENU_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		wechatAdminMenuMapper.updatePower(map);//修改权限的名称或者url
	}

	/**
	 * 查询权限的二级菜单id和一级菜单id
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectFirstAndSecond(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.ALL_MENU, Constants.ALL_MENU_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		Map<String,Object> bean = wechatAdminMenuMapper.selectFirstAndSecond(map);
		outputObject.setBean(bean);
	}
}
