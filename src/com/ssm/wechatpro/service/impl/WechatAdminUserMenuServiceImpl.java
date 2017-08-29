package com.ssm.wechatpro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssm.wechatpro.dao.WechatAdminLoginMapper;
import com.ssm.wechatpro.dao.WechatAdminMenuMapper;
import com.ssm.wechatpro.dao.WechatAdminUserMenuMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatAdminUserMenuService;
import com.ssm.wechatpro.util.DateUtil;

@Service
public class WechatAdminUserMenuServiceImpl implements WechatAdminUserMenuService{
	
	@Resource
	private WechatAdminUserMenuMapper wechatAdminUserMenuMapper;//用户菜单
	@Resource
    private WechatAdminLoginMapper wechatAdminLoginMapper;//用户
	@Resource
	private WechatAdminMenuMapper wechatAdminMenuMapper;//菜单
	
	/**
	 * 添加用户&菜单
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void addUserMenu(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		Map<String,Object> user = new HashMap<>();
		Map<String,Object> menu = new HashMap<>();
		user.put("id", map.get("wechatAdminUserId"));//获取用户id
		menu.put("id", map.get("wechatAdminMenuId"));//获取菜单id
		//判断添加的用户id，和菜单id是否存在
		if(wechatAdminLoginMapper.selectById(user)==null){
			outputObject.setreturnMessage("该用户ID不存在");
			return;
		}else if(wechatAdminMenuMapper.selectById(menu)==null){
			outputObject.setreturnMessage("该菜单ID不存在");
			return;
		}
		map.put("createId", inputObject.getLogParams().get("id"));
		map.put("createTime", DateUtil.getTimeAndToString());
		wechatAdminUserMenuMapper.addUserMenu(map);
	}

	/**
	 * 根据用户id查询他所使用的菜单
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectMenuByUserId(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();//获取
		List<Map<String,Object>> beans = wechatAdminUserMenuMapper.selectMenuByUserId(map);
		outputObject.setBeans(beans);
	}

	/**
	 * 删除用户使用的菜单
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void deleteMenuByUserId(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		//删除之前判断要删除的菜单是否为一级菜单
		Map<String,Object> menu = new HashMap<>();
		menu.put("id", map.get("wechatAdminMenuId"));
		if(wechatAdminMenuMapper.selectById(menu).get("jibie").toString().equals("1")){//如果是一级菜单，判断是否有子菜单
			Map<String,Object> id = new HashMap<>();
			id.put("belongto", wechatAdminMenuMapper.selectById(menu).get("id"));
			if(wechatAdminMenuMapper.selectByBelongto(id).size()==0){//没有子菜单，直接删除
				wechatAdminUserMenuMapper.deleteMenuByUserId(map);
				outputObject.setreturnMessage("删除成功!");
			}else{//如果有子菜单，判断子菜单是否被用户使用
				List<Map<String,Object>> list = wechatAdminMenuMapper.selectByBelongto(id);
				for(int i=0;i<list.size();i++){
					Map<String,Object> menuId = new HashMap<>();
					menuId.put("wechatAdminMenuId", list.get(i).get("id"));
					Map<String,Object> mid = new HashMap<>();
					mid.put("id", list.get(i).get("id"));
					if(wechatAdminUserMenuMapper.selectUserByMenuId(menuId)==0){//如果没有对应的用户在使用，则删除此一级菜单	
						wechatAdminUserMenuMapper.deleteMenuByUserId(map);//删除一级菜单
						wechatAdminMenuMapper.deleteById(mid);//同时删除子菜单
						outputObject.setreturnMessage("删除成功!");
					}
					else{//如果有用户使用，则不能删除，
						outputObject.setreturnMessage("有其他用户在使用此菜单的子菜单，所以删除失败");
					}
				}			
			}
		}else{//如果是二级菜单，直接删除
			wechatAdminUserMenuMapper.deleteMenuByUserId(map);
			outputObject.setreturnMessage("删除成功!");
		}		
	}
	
}
