package com.ssm.wechatpro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ssm.wechatpro.dao.UploadMapper;
import com.ssm.wechatpro.dao.WechatAdminLoginLogMapper;
import com.ssm.wechatpro.dao.WechatAdminLoginMapper;
import com.ssm.wechatpro.dao.WechatAdminLoginMationMapper;
import com.ssm.wechatpro.dao.WechatAdminMenuMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatAdminLoginService;
import com.ssm.wechatpro.util.Constants;
import com.ssm.wechatpro.util.DateUtil;
import com.ssm.wechatpro.util.JudgeUtil;
import com.ssm.wechatpro.util.ToolUtil;
import com.wechat.service.IPService;

@Service
public class WechatAdminLoginServiceImpl implements WechatAdminLoginService {

	@Resource
	private WechatAdminLoginMapper wechatAdminLoginMapper;
	@Resource
	private WechatAdminLoginLogMapper wechatAdminLoginLogMapper;
    @Resource
    private WechatAdminLoginMationMapper wechatAdminLoginMationMapper;
    @Resource
    private UploadMapper uploadMapper;
    @Resource
    private WechatAdminMenuMapper wechatAdminMenuMapper;
    
	/**
	 * 注册
	 * 
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void insertAdminLogin(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.REGIST, Constants.REGIST_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		map.put("creatTime",DateUtil.getTimeAndToString());
		if (map.get("password1").equals(map.get("password2"))) {
			if ( map.get("password1").toString().length()>5 && map.get("password1").toString().length()<19) {
				map.put("adminPassword",ToolUtil.MD5(map.get("password1").toString()));
			}else{
				outputObject.setreturnMessage("密码必须是字符与数据同时出现，且是6-18位，请重新输入！");
				return;
			}
		} else {
			outputObject.setreturnMessage("两次密码不相同，请重新输入！");
			return;
		}
		if(map.get("phoneMessage").toString().equals(map.get("sendPhoneMessage").toString())){
		}else{
			outputObject.setreturnMessage("验证码不正确！");
			return;
		}
		/* 登录账号（手机号验证） */
		if (JudgeUtil.isPhoneNO(map.get("adminNo").toString())) {// 手机号合格
			/* 判断手机号是否被注册过 */
			int count = wechatAdminLoginMapper.selectByNo(map);// 检验手机号是否被注册过
			if (count > 0) {
				outputObject.setreturnMessage("该手机号已经被注册过");
			} else {
				map.put("adminIdentity", Constants.USER_IDENTITY);
				map.put("adminRole", Constants.USER_IDENTITY);
				wechatAdminLoginMapper.insertAdminLogin(map);// 添加用户
				//添加用户时将主键返回，并添加到角色表中的adminId，作为关联
				Map<String,Object> bean = new HashMap<>();
				bean.put("adminId",map.get("id").toString());
				wechatAdminLoginMationMapper.insertLoginMation(bean);
			}
		} else {
			outputObject.setreturnMessage("手机号不合格");
		}
	}
	
	/**
	 * 添加用户
	 * 
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void insertUser(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.INSERT_USER, Constants.INSERT_USER_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		map.put("creatTime",DateUtil.getTimeAndToString());
		if ( map.get("password").toString().length()>5 && map.get("password").toString().length()<19) {
			map.put("adminPassword",ToolUtil.MD5(map.get("password").toString()));
		}else {
			outputObject.setreturnMessage("密码必须是6-18位，请重新输入！");
			return;
		}	
		/* 登录账号（手机号验证） */
		if (JudgeUtil.isPhoneNO(map.get("adminNo").toString())) {// 手机号合格
			/* 判断手机号是否被注册过 */
			int count = wechatAdminLoginMapper.selectByNo(map);// 检验手机号是否被注册过
			if (count > 0) {
				outputObject.setreturnMessage("该手机号已经被注册过");
			} else {
				map.put("adminIdentity", map.get("adminIdentity").toString());
				map.put("adminRole", Constants.USER_IDENTITY);
				Map<String,Object> bean = new HashMap<>();
				if(map.get("adminIdentity").toString().equals("6")){
					wechatAdminLoginMapper.insertAdminLogin(map);// 添加用户
					//添加用户时将主键返回，并添加到角色表中的adminId，作为关联
					bean.put("adminId",map.get("id").toString());
					bean.put("adminWorkPlace",map.get("adminWorkPlace").toString());//地址信息
					wechatAdminLoginMationMapper.insertLoginMation(bean);
				}else if(map.get("adminIdentity").toString().equals("5")){
					wechatAdminLoginMapper.insertAdminLogin(map);// 添加用户
					//添加用户时将主键返回，并添加到角色表中的adminId，作为关联
					bean.put("adminId",map.get("id").toString());
					bean.put("adminWorkPlace",map.get("adminWorkPlace").toString());//地址信息
					bean.put("adminShopName",map.get("adminShopName").toString());//添加商店名称
					bean.put("adminShopCard",map.get("adminShopCard").toString());//添加商户号
					bean.put("adminShopKey",map.get("adminShopKey").toString());//商户号对应的key
					bean.put("adminKfPhone",map.get("adminKfPhone").toString());
					bean.put("adminKfBusinessHours",map.get("adminKfBusinessHours").toString());
					bean.put("adminRecommend",map.get("adminRecommend").toString());
					bean.put("adminCharacteristic",map.get("adminCharacteristic").toString());
					bean.put("adminWorkXCoordinate",map.get("adminWorkXCoordinate").toString());
					bean.put("adminWorkYCoordinate",map.get("adminWorkYCoordinate").toString());
					bean.put("id",map.get("id").toString());
					wechatAdminLoginMationMapper.insertLoginMation(bean);
				}
			}
		} else {
			outputObject.setreturnMessage("手机号不合格");
		}
	}

	/**
	 * 删除用户
	 * 
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void deleteById(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.USER, Constants.USER_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		wechatAdminLoginMapper.deleteById(map);
	}

	/**
	 * 更新用户密码
	 * 
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updatePassword(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.PASSWORD, Constants.PASSWORD_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		if(map.get("newPassword1").toString().equals(map.get("newPassword2").toString())){
			if(map.get("newPassword1").toString().length() > 5 && map.get("newPassword1").toString().length() < 19){
				map.put("adminPassword", ToolUtil.MD5(map.get("newPassword1").toString()));
				wechatAdminLoginMapper.updatePassword(map);
			}else{
				outputObject.setreturnMessage("密码长度必须为6-18位");
			}
		}else{
			outputObject.setreturnMessage("两次密码输入不相同");
		}
	}

	/**
	 * 查询所有用户信息
	 * 
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectAll(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		int page = Integer.parseInt(map.get("offset").toString())/Integer.parseInt(map.get("limit").toString());
		page++;
		int limit = Integer.parseInt(map.get("limit").toString());
		List<Map<String, Object>> beans = wechatAdminLoginMapper.selectAll(map,new PageBounds(page,limit));
		PageList<Map<String, Object>> abilityInfoPageList = (PageList<Map<String, Object>>)beans;
		for(int i=0;i<beans.size();i++){
			Map<String,Object> role = new HashMap<>();
			role = beans.get(i);
			if(role.get("adminIdentity").toString().equals("0")){
				role.put("adminIdentityShow", "未认证");
			}else if(role.get("adminIdentity").toString().equals("1")){
				role.put("adminIdentityShow", "餐厅人员");
			}else if(role.get("adminIdentity").toString().equals("2")){
				role.put("adminIdentityShow", "管理人员");
			}else if(role.get("adminIdentity").toString().equals("3")){
				role.put("adminIdentityShow", "超级管理人员");
			}else if(role.get("adminIdentity").toString().equals("5")){
				role.put("adminIdentityShow", "餐厅人员认证");
			}else if(role.get("adminIdentity").toString().equals("6")){
				role.put("adminIdentityShow", "管理人员认证");
			}else if(role.get("adminIdentity").toString().equals("4")){
				role.put("adminIdentityShow", "认证未通过");
			}
		}
		int total = abilityInfoPageList.getPaginator().getTotalCount();
		outputObject.setBeans(beans);
		outputObject.settotal(total);
	}

	/**
	 * 用于数据回显
	 * 
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectById(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.USER, Constants.USER_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		Map<String, Object> bean = wechatAdminLoginMapper.selectById(map);
		if(bean.get("adminIdentity").toString().equals("0")){
			bean.put("adminIdentityShow", "未认证");
		}else if(bean.get("adminIdentity").toString().equals("1")){
			bean.put("adminIdentityShow", "餐厅人员");
		}else if(bean.get("adminIdentity").toString().equals("2")){
			bean.put("adminIdentityShow", "管理人员");
		}else if(bean.get("adminIdentity").toString().equals("3")){
			bean.put("adminIdentityShow", "超级管理人员");
		}else if(bean.get("adminIdentity").toString().equals("5")){
			bean.put("adminIdentityShow", "餐厅人员认证");
		}else if(bean.get("adminIdentity").toString().equals("6")){
			bean.put("adminIdentityShow", "管理人员认证");
		}else if(bean.get("adminIdentity").toString().equals("4")){
			bean.put("adminIdentityShow", "认证未通过");
		}
		if(bean.containsKey("adminIDCardPositive")){
			if(!JudgeUtil.isNull(bean.get("adminIDCardPositive").toString())){
				Map<String,Object> upload = new HashMap<>();
				upload.put("id", bean.get("adminIDCardPositive").toString());
				Map<String,Object> tupian = uploadMapper.selectById(upload);
				if(tupian!=null){
					bean.put("optionPath", tupian.get("optionPath").toString());
				}	
			}
		}
		if(bean.containsKey("adminIDCardOtherPositive")){
			if(!JudgeUtil.isNull(bean.get("adminIDCardOtherPositive").toString())){
				Map<String,Object> upload2 = new HashMap<>();
				upload2.put("id", bean.get("adminIDCardOtherPositive").toString());
				Map<String,Object> tupian2 = uploadMapper.selectById(upload2);
				if(tupian2!=null){
					bean.put("optionPath2", tupian2.get("optionPath").toString());
				}
			}
		}
		if(bean.containsKey("adminIDCardPeoAndPosi")){
			if(!JudgeUtil.isNull(bean.get("adminIDCardPeoAndPosi").toString())){
				Map<String,Object> upload3 = new HashMap<>();
				upload3.put("id", bean.get("adminIDCardPeoAndPosi").toString());
				Map<String,Object> tupian3 = uploadMapper.selectById(upload3);
				if(tupian3!=null){
					bean.put("optionPath3", tupian3.get("optionPath").toString());
				}
			}
		}
		outputObject.setBean(bean);
	}

	/**
	 * 用于身份认证回显
	 * 
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectRz(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		map.put("id",inputObject.getLogParams().get("id").toString());
		Map<String, Object> bean = wechatAdminLoginMapper.selectById(map);
		if(bean.get("adminIdentity").toString().equals("0")){
			bean.put("adminIdentityShow", "未认证");
		}else if(bean.get("adminIdentity").toString().equals("1")){
			bean.put("adminIdentityShow", "餐厅人员");
		}else if(bean.get("adminIdentity").toString().equals("2")){
			bean.put("adminIdentityShow", "管理人员");
		}else if(bean.get("adminIdentity").toString().equals("3")){
			bean.put("adminIdentityShow", "超级管理人员");
		}else if(bean.get("adminIdentity").toString().equals("5")||bean.get("adminIdentity").toString().equals("6")){
			bean.put("adminIdentityShow", "认证中");
		}else if(bean.get("adminIdentity").toString().equals("4")){
			bean.put("adminIdentityShow", "认证未通过");
		}
		if(bean.containsKey("adminIDCardPositive")){
			if(!JudgeUtil.isNull(bean.get("adminIDCardPositive").toString())){
				Map<String,Object> upload = new HashMap<>();
				upload.put("id", bean.get("adminIDCardPositive").toString());
				Map<String,Object> tupian = uploadMapper.selectById(upload);
				if(tupian!=null){
					bean.put("optionPath", tupian.get("optionPath").toString());
				}	
			}
		}
		if(bean.containsKey("adminIDCardOtherPositive")){
			if(!JudgeUtil.isNull(bean.get("adminIDCardOtherPositive").toString())){
				Map<String,Object> upload2 = new HashMap<>();
				upload2.put("id", bean.get("adminIDCardOtherPositive").toString());
				Map<String,Object> tupian2 = uploadMapper.selectById(upload2);
				if(tupian2!=null){
					bean.put("optionPath2", tupian2.get("optionPath").toString());
				}
			}
		}
		if(bean.containsKey("adminIDCardPeoAndPosi")){
			if(!JudgeUtil.isNull(bean.get("adminIDCardPeoAndPosi").toString())){
				Map<String,Object> upload3 = new HashMap<>();
				upload3.put("id", bean.get("adminIDCardPeoAndPosi").toString());
				Map<String,Object> tupian3 = uploadMapper.selectById(upload3);
				if(tupian3!=null){
					bean.put("optionPath3", tupian3.get("optionPath").toString());
				}
			}
		}
		outputObject.setBean(bean);
	}

	
	/**
	 * 登录
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	@Override
	public void insertLogin(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();//获取页面传入的账号（手机号）
		if(!ToolUtil.contains(map, Constants.LOGIN, Constants.LOGIN_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		Map<String, Object> bean = wechatAdminLoginMapper.selectUserPassword(map);//获取相对应的密码
		int amount = 0;
		Map<String,Object> date = new HashMap<>();
		date.put("loginTable", Constants.LOGIN_TABLE+DateUtil.getTimeSixAndToString());
		date.put("loginData", DateUtil.getTime());
		date.put("no", map.get("adminNo"));
		//查询登录失败次数，如果当天登录失败三次则将今天冻结，不能再登录
		if(wechatAdminLoginLogMapper.selectFailCount(date)>=3){
			outputObject.setreturnMessage("您已登录失败三次，今天被冻结，请明天登录！");
			return;
		}
		if(bean == null){//没有查询到密码
			outputObject.setreturnMessage("该账号不存在！");
			return;
		} else if(!ToolUtil.MD5(map.get("passwordbefore").toString()).equals(bean.get("adminPassword"))){//如果从页面获取的密码与相对应的密码不相同
		    outputObject.setreturnMessage("密码错误！");
		} else{
			amount = 1;
			//获取该用户所拥有的菜单
			map.put("id", bean.get("id"));
			List<Map<String,Object>> beans = wechatAdminLoginMapper.selectByUser(map);//查询所有的一级菜单
			
			if(beans.get(0).get("wechatRoleState").toString().equals(Constants.USER_STATEBYPASSWORD)){//查看角色是否为上线状态
				for(Map<String,Object> b : beans){//遍历每一条数据
					b.put("userId", map.get("id"));
					List<Map<String,Object>> items = wechatAdminMenuMapper.selectByBelongto(b);
					b.put("menuList", items);
					b.put("size", items.size());
				}
				outputObject.setBeans(beans);
				//查询该角色下的所有权限
				List<Map<String,Object>> permission = wechatAdminLoginMapper.selectPowerByRole(map);
				outputObject.setLogParams(bean);
				outputObject.setLogMenuParams(beans);
				outputObject.setLogPermissionParams(permission);
			}else{
				outputObject.setreturnMessage("角色不在上线状态，请先审核！");
			}
		}
		//登录日志
		String no = map.get("adminNo").toString();
		Map<String, Object> beanLog = new HashMap<>();
		beanLog.put("loginTable", Constants.LOGIN_TABLE+DateUtil.getTimeSixAndToString());
		beanLog.put("no", no);
		try{
			beanLog.put("loginPlace", IPService.getAddressByLoginIp(inputObject.getRequest().getRemoteHost()));//获取登录用户的地址
			beanLog.put("loginIp", inputObject.getRequest().getRemoteHost());//获取登录用户的IP
		}catch (Exception e) {
			beanLog.put("loginPlace", "未获取");//获取登录用户的地址
			beanLog.put("loginIp", "127.0.0.1");//获取登录用户的IP
		}
		beanLog.put("loginData", DateUtil.getTimeAndToString());
		beanLog.put("loginNumSuccess", amount);//查询登录成功次数再+1
		wechatAdminLoginLogMapper.insertLoginLog(beanLog);
	}

	/**
	 * 获取登录信息
	 */
	@Override
	public void selectSession(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getLogParams();
		if(map==null){
			outputObject.setreturnMessage("Session为空,获取信息失败...");
		}else{
			outputObject.setBean(map);
		}
	}
	
	/**
	 * 清空session
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	@Override 
	public void clearSession(InputObject inputObject, OutputObject outputObject) throws Exception {
		outputObject.getRequest().getSession().invalidate();
	}
	
	/**
	 * 身份认证
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateById(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		map.put("id",inputObject.getLogParams().get("id").toString());
		if(map.get("adminIdentity").toString().equals("5")){//餐厅人员
			wechatAdminLoginMapper.updateById(map);
			wechatAdminLoginMationMapper.updateByAdminId(map);
		}else if(map.get("adminIdentity").toString().equals("6")){
			wechatAdminLoginMapper.updateById(map);
			wechatAdminLoginMationMapper.updateByAdminId(map);
		}
	}

	/**
	 * 查看手机号是否被注册过
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectAdminNo(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		Map<String,Object> bean = wechatAdminLoginMapper.selectAdminNo(map);
		if(bean==null){
			outputObject.setreturnMessage("改手机号未注册，请先注册...");
		}else{
			outputObject.setBean(bean);
		}
	}

	/**
	 * 所有正在认证中的用户
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectByAdminIdentity(InputObject inputObject,	OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		int page = Integer.parseInt(map.get("offset").toString())/Integer.parseInt(map.get("limit").toString());
		page++;
		int limit = Integer.parseInt(map.get("limit").toString());
		List<Map<String, Object>> beans = wechatAdminLoginMapper.selectByAdminIdentity(map,new PageBounds(page,limit));
		PageList<Map<String, Object>> abilityInfoPageList = (PageList<Map<String, Object>>)beans;
		for(int i=0;i<beans.size();i++){
			Map<String,Object> role = new HashMap<>();
			role = beans.get(i);
			if(role.get("adminIdentity").toString().equals("0")){
				role.put("adminIdentityShow", "未认证");
			}else if(role.get("adminIdentity").toString().equals("1")){
				role.put("adminIdentityShow", "餐厅人员");
			}else if(role.get("adminIdentity").toString().equals("2")){
				role.put("adminIdentityShow", "管理人员");
			}else if(role.get("adminIdentity").toString().equals("3")){
				role.put("adminIdentityShow", "超级管理人员");
			}else if(role.get("adminIdentity").toString().equals("5")){
				role.put("adminIdentityShow", "餐厅人员认证");
			}else if(role.get("adminIdentity").toString().equals("6")){
				role.put("adminIdentityShow", "管理人员认证");
			}else if(role.get("adminIdentity").toString().equals("4")){
				role.put("adminIdentityShow", "认证未通过");
			}
		}
		int total = abilityInfoPageList.getPaginator().getTotalCount();
		outputObject.setBeans(beans);
		outputObject.settotal(total);
	}

	/**
	 * 认证未通过
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateUserNoPass(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.USERONLINE, Constants.USERONLINE_RETURNMESSAGE,inputObject, outputObject)){
			return;
		}
		wechatAdminLoginMapper.updateUserNoPass(map);//认证未通过
	}

	/**
	 * 认证通过(餐厅人员认证)
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateCtUserPass(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.USERONLINE, Constants.USERONLINE_RETURNMESSAGE,inputObject, outputObject)){
			return;
		}
		wechatAdminLoginMapper.updateCtUserPass(map);//认证通过
	}
	
	/**
	 * 认证通过(管理人员认证)
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateGlUserPass(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.USERONLINE, Constants.USERONLINE_RETURNMESSAGE,inputObject, outputObject)){
			return;
		}
		wechatAdminLoginMapper.updateGlUserPass(map);//认证通过
	}

	/**
	 * 查询用户所属角色下的所有菜单
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectByUser(InputObject inputObject, OutputObject outputObject) throws Exception {
		List<Map<String, Object>> map = inputObject.getLogMenuParams();
		if(map==null){
			outputObject.setreturnMessage("用户所拥有的菜单为空,获取信息失败...");
		}else{
			outputObject.setBeans(map);
		}
	}

	/**
	 * 显示餐厅和餐厅人员信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectShop(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		int page = Integer.parseInt(map.get("offset").toString())/Integer.parseInt(map.get("limit").toString());
		page++;
		int limit = Integer.parseInt(map.get("limit").toString());
		List<Map<String,Object>> beans = wechatAdminLoginMapper.selectShop(map,new PageBounds(page,limit));
		PageList<Map<String, Object>> abilityInfoPageList = (PageList<Map<String, Object>>)beans;
		int total = abilityInfoPageList.getPaginator().getTotalCount();
		outputObject.setBeans(beans);
		outputObject.settotal(total);
	}

	/**
	 * 显示餐厅详情
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectShopXQ(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.STORE, Constants.STORE_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		Map<String,Object> bean = wechatAdminLoginMapper.selectShopXQ(map);
		outputObject.setBean(bean);
	}

	/**
	 * 编辑餐厅详情信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateShopXQ(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();//获取餐厅人员账号
		if(!ToolUtil.contains(map, Constants.STORE, Constants.STORE_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		wechatAdminLoginMapper.updateShopXQ(map);
	}
}
