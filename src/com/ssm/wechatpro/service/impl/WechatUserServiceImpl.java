package com.ssm.wechatpro.service.impl;

import io.goeasy.GoEasy;
import io.goeasy.publish.GoEasyError;
import io.goeasy.publish.PublishListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ssm.wechatpro.dao.WechatUserMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatUserService;
import com.ssm.wechatpro.util.DateUtil;
import com.wechat.service.GetUserList;
import com.wechat.service.GetUserMationService;

@Service
public class WechatUserServiceImpl implements WechatUserService {
	
	@Resource
	WechatUserMapper wechatUserMapper;

	/***
	 * 查询所有用户信息
	 * @amparam inputObject
	 * @par outputObject
	 * @throws Exception
	 */
	@Override
	public void selectAllWechatUser(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		if(params.get("nickname")!=null){
			String nickname = Base64.encodeBase64String(params.get("nickname").toString().getBytes("utf-8"));;
			params.put("nickname", "%"+nickname+"%");
		}
		int page = Integer.parseInt(params.get("offset").toString()) / Integer.parseInt(params.get("limit").toString());
		page++;
		int limit = Integer.parseInt(params.get("limit").toString()); 
		List<Map<String, Object>> beans = wechatUserMapper.selectWechatUser(params, new PageBounds(page, limit));
		PageList<Map<String, Object>> abilityInfoPageList = (PageList<Map<String, Object>>) beans;
		int total = abilityInfoPageList.getPaginator().getTotalCount();
		outputObject.setBeans(beans);
		outputObject.settotal(total);
	}
	
	/***
	 * 插入用户信息
	 * @amparam inputObject
	 * @par outputObject
	 * @throws Exception
	 */
	@Override
	public void insertWechatUser(InputObject inputObject, final OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getLogParams();
		List<String> beans = GetUserList.getRequest();
		List<Map<String, Object>> beansed = wechatUserMapper.selectWechatOpenid();
		List<Map<String,Object>> params = new ArrayList<Map<String,Object>>();
		int numUpdate = Integer.parseInt(inputObject.getParams().get("numUpdate").toString());
		GoEasy goEasy = new GoEasy("BC-c5e986fba5d14d38b2b2c5b4b072fc8c");
		goEasy.publish(map.get("adminNo").toString(),"系统开始检测，请等待", new PublishListener(){
			@Override
			public void onSuccess() {
			}
			@Override
			public void onFailed(GoEasyError error) {
				outputObject.setreturnMessage("消息发布失败, 错误编码：" + error.getCode() + " 错误信息： " + error.getContent());
			}
		});
		for(int i=0;i<beansed.size();i++)
		{
			if(beans.contains(beansed.get(i).get("openid").toString())){
 				beans.remove(beansed.get(i).get("openid").toString());
			}
		}
		List<Map<String, Object>> users = new ArrayList<>();
		int beansSize = beans.size();
		while(!beans.isEmpty()){
			for(int j = 0;j < 100 && !beans.isEmpty();j++){
				Map<String,Object> param = new HashMap<String, Object>();
				param.put("openid", beans.get(0));
				param.put("lang", "zh_CN");
				params.add(param);
				beans.remove(0);
			}
 			users.addAll(GetUserMationService.getRequest(params));
			goEasy.publish(map.get("adminNo").toString(),"检测成功100条用户信息，剩余"+beans.size()+"条用户信息未检测", new PublishListener(){
				@Override
				public void onSuccess() {
				}
				@Override
				public void onFailed(GoEasyError error) {
					outputObject.setreturnMessage("消息发布失败, 错误编码：" + error.getCode() + " 错误信息： " + error.getContent());
				}
			});
			params.clear();
			if(beans.size()<(beansSize-numUpdate)){
				break;
			}else{
				if(!users.isEmpty()){
					wechatUserMapper.insertAllWechatUser(users);
					users.clear();
				}
			}
		}
		
		if(beansSize<100&&!users.isEmpty()){
			wechatUserMapper.insertAllWechatUser(users);
		}
		
		goEasy.publish(map.get("adminNo").toString(),"数据库插入完成", new PublishListener(){
			@Override
			public void onSuccess() {
			}
			@Override
			public void onFailed(GoEasyError error) {
				outputObject.setreturnMessage("消息发布失败, 错误编码：" + error.getCode() + " 错误信息： " + error.getContent());
			}
		});
	}
  
	/***
	 * 取消订阅
	 * @amparam inputObject
	 * @par outputObject
	 * @throws Exception
	 */
	@Override
	public void unsubscribe(String openid) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("openid", openid);
		map.put("subscribe", 0);
		System.out.println(map);
		wechatUserMapper.updateWechatSubscribe(map);
	}

	/***
	 * 订阅
	 * @amparam inputObject
	 * @par outputObject
	 * @throws Exception
	 */
	@Override
	public void subscribe(String openid) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("openid", openid);
		Map<String, Object> bean = wechatUserMapper.selectWechatSubscribe(map);
		if(bean!=null){
			if(bean.get("subscribe").equals("0")){
				map.put("subscribe", "1");
				wechatUserMapper.updateWechatSubscribe(map);
			}
		}else{
			map = GetUserMationService.getRequest1(openid);
			wechatUserMapper.insertWechatUser(map);
			
		}
	}

	/***
	 * 修改WechatUser的位置信息
	 * @amparam inputObject
	 * @par outputObject
	 * @throws Exception
	 */
	@Override
	public void updateWechatUserLocation(Map<String, Object> map) throws Exception {
		wechatUserMapper.updateWechatUserLoaction(map);
	}

	/***
	 * 获取微信用户信息
	 * @amparam inputObject
	 * @par outputObject
	 * @throws Exception
	 */
	@Override
	public void selectLatitudeAndLongtitude(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getWechatLogParams();//获取openid
		outputObject.setBean(map);
	}
	
	/***
	 * 更新用户信息
	 * @amparam inputObject
	 * @par outputObject
	 * @throws Exception
	 */
	@Override
	public void updateWechatUserMassage(Map<String, Object> map)throws Exception {
		Map<String,Object> user = GetUserMationService.getRequest1(map.get("openid").toString());
		user.remove("wechatIntegral");
		map.putAll(user);
		Map<String,Object> loginUser = new HashMap<>();
		loginUser.put("loginPhone", map.get("wechatNowUser"));
		loginUser.put("createTime", DateUtil.getTimeAndToString());
		loginUser.put("loginIdentity", 0);
		loginUser.put("loginState", 1);
		loginUser.put("loginType", 1);
		loginUser.put("loginScore", map.get("wechatIntegral"));
		loginUser.put("membershipCardId", map.get("membershipCardId"));
		wechatUserMapper.insertMWechatLoginUser(loginUser);
		map.put("wechatLoginId", loginUser.get("id"));
		wechatUserMapper.updateWechatUser(map);
	}
}
