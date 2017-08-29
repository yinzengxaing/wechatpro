package com.ssm.wechatpro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ssm.wechatpro.dao.UploadMapper;
import com.ssm.wechatpro.dao.WechatAdminLoginMapper;
import com.ssm.wechatpro.dao.WechatScollorPicMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatScollorPicService;
import com.ssm.wechatpro.util.Constants;
import com.ssm.wechatpro.util.DateUtil;
import com.ssm.wechatpro.util.JudgeUtil;
import com.ssm.wechatpro.util.ToolUtil;

@Service
public class WechatScollorPicServiceImpl implements WechatScollorPicService{

	@Resource
	private WechatScollorPicMapper wechatScollorPicMapper;
	@Resource
	private UploadMapper uploadMapper;
	@Resource
	private WechatAdminLoginMapper wechatAdminLoginMapper;
	
	/**
	 * 添加通知
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void insertScoller(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.SCOLLOR_PIC_TEST, Constants.SCOLLOR_PIC_TEST_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		map.put("scollor_pic_data", DateUtil.getTime());//添加日期
		map.put("scollor_pic_userid",inputObject.getLogParams().get("id") );//用户id
		wechatScollorPicMapper.insertScoller(map);
	}

	/**
	 * 查询所有通知
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectAllScollor(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		int page = Integer.parseInt(map.get("offset").toString())/Integer.parseInt(map.get("limit").toString());
		page++;
		int limit = Integer.parseInt(map.get("limit").toString());
		List<Map<String,Object>> beans = wechatScollorPicMapper.selectAllScollor(map,new PageBounds(page,limit));
		PageList<Map<String, Object>> abilityInfoPageList = (PageList<Map<String, Object>>)beans;
		for (Map<String, Object> bean : beans) {//遍历每一条通知，获取每一条图片的路径
			if(bean.containsKey("scollor_pic_path")){
				if(!JudgeUtil.isNull(bean.get("scollor_pic_path").toString())){
					Map<String,Object> upload = new HashMap<>();
					upload.put("id", bean.get("scollor_pic_path").toString());
					Map<String,Object> tupian = uploadMapper.selectById(upload);
					if(tupian!=null){
						bean.put("optionPath", tupian.get("optionPath").toString());
					}
				}
			}
			Map<String,Object> param = new HashMap<>();
			param.put("id", bean.get("scollor_pic_userid").toString());
			Map<String,Object> map2 = wechatAdminLoginMapper.selectById(param);
			bean.put("adminNo", map2.get("adminNo").toString());//创建人手机号
			//拼接url
			bean.put("url", "http://"+Constants.YUMING+"/wechatpro/html/phoneModelOne/slideShow.html?id="+bean.get("id").toString());
		}
		int total = abilityInfoPageList.getPaginator().getTotalCount();
		outputObject.setBeans(beans);
		outputObject.settotal(total);
	}

	/**
	 * 删除一条通知
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void deleteScollor(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.SCOLLOR_PIC, Constants.SCOLLOR_PIC_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		wechatScollorPicMapper.deleteScollor(map);
	}

	/**
	 * 回显
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectById(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.SCOLLOR_PIC, Constants.SCOLLOR_PIC_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		Map<String,Object> bean = wechatScollorPicMapper.selectById(map);
		//回显图片
		if(bean.containsKey("scollor_pic_path")){
			if(!JudgeUtil.isNull(bean.get("scollor_pic_path").toString())){
				Map<String,Object> upload = new HashMap<>();
				upload.put("id", bean.get("scollor_pic_path").toString());
				Map<String,Object> tupian = uploadMapper.selectById(upload);
				if(tupian!=null){
					bean.put("optionPath", tupian.get("optionPath").toString());
				}
			}
		}
		outputObject.setBean(bean);
	}

	/**
	 * 编辑通知
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateScoller(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.SCOLLOR_PIC, Constants.SCOLLOR_PIC_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		wechatScollorPicMapper.updateScoller(map);
	}

	/**
	 * 发布通知
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateFbScollor(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.SCOLLOR_PIC, Constants.SCOLLOR_PIC_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		Map<String,Object> bean = new HashMap<>();
		bean.put("scollor_num", map.get("scollor_num").toString());
		if(wechatScollorPicMapper.selectScollorNum(bean) != null){
			outputObject.setreturnMessage("已发布的通知中该展示顺序已存在");
			return;
		}
		wechatScollorPicMapper.updateFbScollor(map);
	}

	/**
	 * 上线
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateSxScollor(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.SCOLLOR_PIC, Constants.SCOLLOR_PIC_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		wechatScollorPicMapper.updateSxScollor(map);
	}

	/**
	 * 下线
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateXxScollor(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.SCOLLOR_PIC, Constants.SCOLLOR_PIC_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		wechatScollorPicMapper.updateXxScollor(map);
	}

	/**
	 * 取消发布
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateQxFbScollor(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.SCOLLOR_PIC, Constants.SCOLLOR_PIC_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		wechatScollorPicMapper.updateQxFbScollor(map);
	}

	/**
	 * 查询前五条已发布并已上线的通知
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectFiveScollor(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		List<Map<String,Object>> beans = wechatScollorPicMapper.selectFiveScollor(map);
		for (Map<String, Object> bean : beans) {//遍历每一条通知，获取每一条图片的路径
			if(bean.containsKey("scollor_pic_path")){
				if(!JudgeUtil.isNull(bean.get("scollor_pic_path").toString())){
					Map<String,Object> upload = new HashMap<>();
					upload.put("id", bean.get("scollor_pic_path").toString());
					Map<String,Object> tupian = uploadMapper.selectById(upload);
					if(tupian!=null){
						bean.put("optionPath", tupian.get("optionPath").toString());
					}
				}
			}
		}
		outputObject.setBeans(beans);
	}

	/**
	 * 修改展示顺序
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateScollorNum(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.SCOLLOR_PIC, Constants.SCOLLOR_PIC_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		Map<String,Object> num = wechatScollorPicMapper.selectScollorNum(map);
		if(num == null){
			wechatScollorPicMapper.updateScollorNum(map);
		}else{
			wechatScollorPicMapper.updateScollorNum(map);
			num.put("scollor_num", Constants.SCOLLOR_NUM);//将其排列顺序变为0
			wechatScollorPicMapper.updateScollorNum(num);
		}
	}
}
