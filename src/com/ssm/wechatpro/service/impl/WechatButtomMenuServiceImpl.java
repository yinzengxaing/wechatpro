package com.ssm.wechatpro.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ssm.wechatpro.bean.wechat.AccessToken;
import com.ssm.wechatpro.bean.wechat.Button;
import com.ssm.wechatpro.bean.wechat.CommonButton;
import com.ssm.wechatpro.bean.wechat.ComplexButton;
import com.ssm.wechatpro.bean.wechat.Menu;
import com.ssm.wechatpro.bean.wechat.ViewButton;
import com.ssm.wechatpro.dao.WechatAppMapper;
import com.ssm.wechatpro.dao.WechatButtomMenuMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatButtomMenuService;
import com.ssm.wechatpro.util.Constants;
import com.ssm.wechatpro.util.DateUtil;
import com.ssm.wechatpro.util.ToolUtil;
import com.ssm.wechatpro.util.WeixinUtil;

@Service
public class WechatButtomMenuServiceImpl implements WechatButtomMenuService {

	@Resource
	private WechatButtomMenuMapper wechatButtomMenuMapper;
	
	@Resource
	private WechatAppMapper wechatAppMapper;
	
	
	/***
	 * 查找所有菜单项
	 * @amparam inputObject
	 * @par outputObject
	 * @throws Exception
	 */
	@Override
	public void selectAllMenus(InputObject inputObject, OutputObject outputObject) throws Exception {
                Map<String, Object> params = inputObject.getParams();
                params.put("menuVersion", "%" + params.get("menuVersion") + "%");
                int page = Integer.parseInt(params.get("offset").toString())
        				/ Integer.parseInt(params.get("limit").toString());
        		page++;
        		int limit = Integer.parseInt(params.get("limit").toString()); 
                List<Map<String, Object>> menus  = wechatButtomMenuMapper.selectAllMenuList(params,new PageBounds(page, limit));
                PageList<Map<String, Object>> abilityInfoPageList = (PageList<Map<String, Object>>) menus;
                int total = abilityInfoPageList.getPaginator().getTotalCount();
                outputObject.setBeans(menus);
                outputObject.settotal(total);
	}
	
	/***
	 * 查看Version相同的菜单项
	 * @amparam inputObject
	 * @par outputObject
	 * @throws Exception
	 */
	@Override
	public void selectMenuByVersion(InputObject inputObject, OutputObject outputObject) throws Exception {
           Map<String, Object> params = inputObject.getParams();
          List<Map<String, Object>> menus = wechatButtomMenuMapper.selectMenuByVersion(params);
          for(Map<String, Object> menu : menus){
        	  List<Map<String, Object>> menuItems = wechatButtomMenuMapper.selectMenuByBelong(menu);
        	  menu.put("menuItems", menuItems);
          }
          outputObject.setBeans(menus);
	}
	
	/***
	 *发布菜单
	 * @amparam inputObject
	 * @par outputObject
	 * @throws Exception
	 */
	@Override
	public void updateMenuPublish(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		List<Map<String, Object>> appBeans = wechatAppMapper.getApp();
		if (appBeans.isEmpty()) {
			outputObject.setreturnMessage("请确认APPID或者APPSECRET存在...");
		} else {
			AccessToken at = WeixinUtil.getAccessToken(appBeans.get(0).get("appId").toString(), appBeans.get(0).get("appSecret").toString());
			if (null != at) {
				// 调用接口创建菜单
				Map<String, Object> bean = new HashMap<>();
				bean.put("menuVersion", params.get("menuVersion"));
				Map<String,Object> result = WeixinUtil.createMenu(selectPublish(bean),at.getToken());
				// 判断菜单创建结果
				if (!result.isEmpty()) {
					if(result.get("returnCode").toString().equals(Constants.WECHAT_BUTTOM_MENU_SUCCESS)){
						wechatButtomMenuMapper.updatewetherPublish();
						params.put("wetherPublish", 1);
						params.put("wetherUser", 1);
						params.put("publishUser",inputObject.getLogParams().get("id"));
						params.put("publishTime", DateUtil.getTimeAndToString());
						wechatButtomMenuMapper.updatePublish(params);
					}else{
						outputObject.setreturnMessage("创建菜单失败。错误编码errcode：" + result.get("returnCode") + "错误信息errmsg:" + result.get("returnMsg"));
					}
				} else {
					outputObject.setreturnMessage("请求微信服务器失败...");
				}
			}
		}
	}
	
	public Menu selectPublish(Map<String,Object> map) throws Exception{
		// 查询所有的一级菜单
		map.put("menuLevel", "1");
		List<Map<String,Object>> firstBeans = wechatButtomMenuMapper.SelectAllByLevel(map);
		// 查询所有的二级菜单
		map.put("menuLevel", "2");
		List<Map<String,Object>> secondBeans = wechatButtomMenuMapper.SelectAllByLevel(map);
		Menu menu = new Menu();
		// 用来存储一级菜单
		List<ComplexButton> btn_0 = new ArrayList<ComplexButton>();
		List<ViewButton> btn_1 = new ArrayList<ViewButton>();
		List<CommonButton> btn_2 = new ArrayList<CommonButton>();
		for (Map<String,Object> bean : firstBeans) {
			ComplexButton mainBtn = new ComplexButton();
			if (Integer.parseInt(bean.get("hasChild").toString()) == 1) {
				// 一级菜单下面有二级菜单
				mainBtn.setName(bean.get("menuName").toString());
				// 用来存储二级菜单
				List<CommonButton> btn1 = new ArrayList<CommonButton>();
				List<ViewButton> btn2 = new ArrayList<ViewButton>();
				for (Map<String,Object> bean1 : secondBeans) {
					if (bean1.get("menuBelong").equals(bean.get("id")) && bean1.get("menuType").equals("click")) {
						// 回复内容的菜单
						CommonButton btnbe = new CommonButton();
						btnbe.setName(bean1.get("menuName").toString());
						btnbe.setType(bean1.get("menuType").toString());
						btnbe.setKey(bean1.get("menuKey").toString());
						btn1.add(btnbe);
					} else if (bean1.get("menuBelong").equals(bean.get("id")) && bean1.get("menuType").equals("view")) {
						// 跳转网页的菜单
						ViewButton btnbe = new ViewButton();
						btnbe.setName(bean1.get("menuName").toString());
						btnbe.setType(bean1.get("menuType").toString());
						btnbe.setUrl(bean1.get("rebackContext").toString());
						btn2.add(btnbe);
					}
				}
				switch (btn1.size() + btn2.size()) {
				case 1:
					switch (btn1.size()) {
					case 0:mainBtn.setSub_button(new Button[] { btn2.get(0) });break;
					case 1:mainBtn.setSub_button(new Button[] { btn1.get(0) });break;
					default:break;
					}break;
				case 2:
					switch (btn1.size()) {
					case 0:mainBtn.setSub_button(new Button[] { btn2.get(0),btn2.get(1) });break;
					case 1:mainBtn.setSub_button(new Button[] { btn1.get(0),btn2.get(0) });break;
					case 2:mainBtn.setSub_button(new Button[] { btn1.get(0),btn1.get(1) });break;
					default:break;
					}break;
				case 3:
					switch (btn1.size()) {
					case 0:mainBtn.setSub_button(new Button[] { btn2.get(0),btn2.get(1), btn2.get(2) });break;
					case 1:mainBtn.setSub_button(new Button[] { btn1.get(0),btn2.get(0), btn2.get(1) });break;
					case 2:mainBtn.setSub_button(new Button[] { btn1.get(0),btn1.get(1), btn2.get(0) });break;
					case 3:mainBtn.setSub_button(new Button[] { btn1.get(0),btn1.get(1), btn1.get(2) });break;
					default:break;
					}
					break;
				case 4:
					switch (btn1.size()) {
					case 0:mainBtn.setSub_button(new Button[] { btn2.get(0),btn2.get(1), btn2.get(2), btn2.get(3) });break;
					case 1:mainBtn.setSub_button(new Button[] { btn1.get(0),btn2.get(0), btn2.get(1), btn2.get(2) });break;
					case 2:mainBtn.setSub_button(new Button[] { btn1.get(0),btn1.get(1), btn2.get(0), btn2.get(1) });break;
					case 3:mainBtn.setSub_button(new Button[] { btn1.get(0),btn1.get(1), btn1.get(2), btn2.get(0) });break;
					case 4:mainBtn.setSub_button(new Button[] { btn1.get(0),btn1.get(1), btn1.get(2), btn1.get(3) });break;
					default:break;
					}break;
				case 5:
					switch (btn1.size()) {
					case 0:mainBtn.setSub_button(new Button[] { btn2.get(0),btn2.get(1), btn2.get(2), btn2.get(3),btn2.get(4) });break;
					case 1:mainBtn.setSub_button(new Button[] { btn1.get(0),btn2.get(0), btn2.get(1), btn2.get(2),btn2.get(3) });break;
					case 2:mainBtn.setSub_button(new Button[] { btn1.get(0),btn1.get(1), btn2.get(0), btn2.get(1),btn2.get(2) });break;
					case 3:mainBtn.setSub_button(new Button[] { btn1.get(0),btn1.get(1), btn1.get(2), btn2.get(0),btn2.get(1) });break;
					case 4:mainBtn.setSub_button(new Button[] { btn1.get(0),btn1.get(1), btn1.get(2), btn1.get(3),btn2.get(0) });break;
					case 5:mainBtn.setSub_button(new Button[] { btn1.get(0),btn1.get(1), btn1.get(2), btn1.get(3),btn1.get(4) });break;
					default:break;
					}
					mainBtn.setSub_button(new Button[] { btn1.get(0),btn1.get(1), btn1.get(2), btn1.get(3), btn1.get(4) });break;
				default:break;
				}
				btn_0.add(mainBtn);
			} else {
				// 一级菜单下面没有二级菜单
				if (bean.get("menuType").equals("click")) {
					CommonButton btnbe = new CommonButton();
					btnbe.setName(bean.get("menuName").toString());
					btnbe.setType(bean.get("menuType").toString());
					btnbe.setKey(bean.get("menuKey").toString());
					btn_2.add(btnbe);
				} else if (bean.get("menuType").equals("view")) {
					ViewButton btnbe = new ViewButton();
					btnbe.setName(bean.get("menuName").toString());
					btnbe.setType(bean.get("menuType").toString());
					btnbe.setUrl(bean.get("rebackContext").toString());
					btn_1.add(btnbe);
				}
			}
		}
		switch (btn_0.size() + btn_1.size() + btn_2.size()) {
		case 1:
			switch (btn_0.size()) {
			case 0:
				switch (btn_1.size()) {
				case 0: menu.setButton(new Button[] { btn_2.get(0) }); break;
				case 1: menu.setButton(new Button[] { btn_1.get(0) }); break;
				default: break;
				}break;
			case 1: menu.setButton(new Button[] { btn_0.get(0) }); break;
			default: break;
			}break;
		case 2:
			switch (btn_0.size()) {
			case 0:
				switch (btn_1.size()) {
				case 0:menu.setButton(new Button[] { btn_2.get(0), btn_2.get(1) });break;
				case 1:menu.setButton(new Button[] { btn_1.get(0), btn_2.get(0) });break;
				case 2:menu.setButton(new Button[] { btn_1.get(0), btn_1.get(1) });break;
				default:break;
				}break;
			case 1:
				switch (btn_1.size()) {
				case 0:menu.setButton(new Button[] { btn_0.get(0), btn_2.get(0) });break;
				case 1:menu.setButton(new Button[] { btn_0.get(0), btn_1.get(0) });break;
				default:break;
				}break;
			case 2:menu.setButton(new Button[] { btn_0.get(0), btn_0.get(1) });break;
			default:break;
			} break;
		case 3:
			switch (btn_0.size()) {
			case 0:
				switch (btn_1.size()) {
				case 0: menu.setButton(new Button[] { btn_2.get(0), btn_2.get(1), btn_2.get(2) }); break;
				case 1: menu.setButton(new Button[] { btn_1.get(0), btn_2.get(0), btn_2.get(1) }); break;
				case 2: menu.setButton(new Button[] { btn_1.get(0), btn_1.get(1), btn_2.get(0) }); break;
				case 3: menu.setButton(new Button[] { btn_1.get(0), btn_1.get(1), btn_1.get(2) }); break;
				default: break;
				} break;
			case 1:
				switch (btn_1.size()) {
				case 0: menu.setButton(new Button[] { btn_0.get(0), btn_2.get(0), btn_2.get(1) }); break;
				case 1: menu.setButton(new Button[] { btn_0.get(0), btn_1.get(0), btn_2.get(0) }); break;
				case 2: menu.setButton(new Button[] { btn_0.get(0), btn_1.get(0), btn_1.get(1) }); break;
				default: break;
				} break;
			case 2:
				switch (btn_1.size()) {
				case 0: menu.setButton(new Button[] { btn_0.get(0), btn_2.get(0) }); break;
				case 1: menu.setButton(new Button[] { btn_0.get(0), btn_1.get(0) }); break;
				default: break;
				} break;
			case 3: menu.setButton(new Button[] { btn_0.get(0), btn_0.get(1), btn_0.get(2) }); break;
			default: break;
			} break;
		default: break;
		}
		return menu;
	}
	
	/***
	 *根据id查询菜单项
	 * @amparam inputObject
	 * @par outputObject
	 * @throws Exception
	 */
	@Override
	public void selectMenuById(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		Map<String, Object> menu = wechatButtomMenuMapper.selectMenuById(params);
		outputObject.setBean(menu);
	}
	
	/***
	 *修改菜单项
	 * @amparam inputObject
	 * @par outputObject
	 * @throws Exception
	 */
	@Override
	public void updatemenuById(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> params = inputObject.getParams();
		if(!ToolUtil.contains(params,Constants.BUTTOMMENU_KEY,Constants.BUTTOMMENU_RETURNMESSAGE, inputObject, outputObject)){
			return ;
		}
		if(params.get("rebackInt").equals("6"))
			params.put("menuType", "view");
		else
			params.put("menuType", "click");
		wechatButtomMenuMapper.updateMenu(params);
	}
	
	/***
	 *添加菜单项
	 * @amparam inputObject
	 * @par outputObject
	 * @throws Exception
	 */
	@Override
	public void addMenu(InputObject inputObject, OutputObject outputObject) throws Exception {
             Map<String,Object> params = inputObject.getParams();
            
             params.put("menuType","click");
             params.put("menuKey", DateUtil.getToString());
             if(params.get("menuLevel").toString().equals("1")){
            	 params.put("menuBelong", 0);
             }else{
            	 Map<String,Object> map=new HashMap<String, Object>();
            	 map.put("id", params.get("menuBelong"));
            	 map.put("hasChild", 1);
            	 wechatButtomMenuMapper.updateMenu(map);
             }
             params.put("hasChild", 0);
             params.put("publishTime",  "-");
             params.put("createId", inputObject.getLogParams().get("id"));
             params.put("createTime", DateUtil.getTimeAndToString());
             params.put("wetherPublish", 0);
             params.put("wetherUser", 0);
             wechatButtomMenuMapper.addMenu(params);
	}
	
	/***
	 *生成版本号
	 * @amparam inputObject
	 * @par outputObject
	 * @throws Exception
	 */
	@Override
	public void getmenuVersion(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("menuVersion", DateUtil.getToString());
		outputObject.setBean(map);
		
	}

	/***
	 *删除菜单项
	 * @amparam inputObject
	 * @par outputObject
	 * @throws Exception
	 */
	@Override
	public void deleteMenus(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> params = inputObject.getParams();
		Map<String,Object> map = wechatButtomMenuMapper.selectMenuById(params);
		if(map.get("menuLevel").toString().equals("2")){//如果是二级菜单
			//直接根据id删除该项
			wechatButtomMenuMapper.deleteButtomById(params);
			map.put("id", map.get("menuBelong"));
			List<Map<String,Object>> buttoms = wechatButtomMenuMapper.selectMenuByBelong(map);
			if(buttoms.isEmpty()){
				Map<String,Object> updatebuttom = new HashMap<>();
				updatebuttom.put("id", map.get("menuBelong"));
				updatebuttom.put("hasChild",'0');
				wechatButtomMenuMapper.updateMenu(updatebuttom);
			}
		}else{//一级菜单删除该项，及父菜单为该一级菜单的子菜单
			wechatButtomMenuMapper.deleteButtomById(params);
			map.put("menuBelong", map.get("id"));
			wechatButtomMenuMapper.deleteButtomBymenuBelong(map);
		}
	}

}
