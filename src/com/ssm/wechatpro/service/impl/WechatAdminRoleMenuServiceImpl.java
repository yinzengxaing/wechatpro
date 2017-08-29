package com.ssm.wechatpro.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssm.wechatpro.dao.WechatAdminRoleMenuMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatAdminRoleMenuService;
import com.ssm.wechatpro.util.Constants;
import com.ssm.wechatpro.util.ToolUtil;


@Service
public class WechatAdminRoleMenuServiceImpl implements WechatAdminRoleMenuService{
	
	@Resource
	private WechatAdminRoleMenuMapper wechatAdminRoleMenuMapper;

	@Override
	public void selectRoleByMenuId(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.ALL_MENU, Constants.ALL_MENU_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		map.put("wechatAdminMenuId", map.get("id").toString());
		List<Map<String,Object>> beans = wechatAdminRoleMenuMapper.selectRoleByMenuId(map);	
		if(beans.size()!=0){//有角色在使用此权限
		}else{//没有角色在使用此权限
			outputObject.setreturnMessage("没有用户在使用此菜单");
		}
	}
}
