package com.ssm.wechatpro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ssm.wechatpro.dao.WechatProPacTypeMapper;
import com.ssm.wechatpro.dao.WechatProPacTypeRestaurantMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatProPacTypeService;
import com.ssm.wechatpro.util.DateUtil;
import com.ssm.wechatpro.util.JudgeUtil;
@Service
public class WechatProPacTypeServiceImpl implements WechatProPacTypeService {
	@Resource
	private  WechatProPacTypeMapper wechatProPacTypeMapper;
	@Resource
	private WechatProPacTypeRestaurantMapper wechatProPacTypeRestaurantMapper;
	/**
	 * 查询所有的分类信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void getPacTypeList(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		//获取分页信息
		int page = Integer.parseInt(params.get("offset").toString())/Integer.parseInt(params.get("limit").toString());
		page++;
		int limit = Integer.parseInt(params.get("limit").toString());
		List<Map<String,Object>> pacTypeList = wechatProPacTypeMapper.getPacTypeList(params, new PageBounds(page, limit));
		PageList<Map<String, Object>> abilityInfoPageList = (PageList<Map<String, Object>>) pacTypeList;
		int total = abilityInfoPageList.getPaginator().getTotalCount();
		outputObject.setBeans(pacTypeList);
		outputObject.settotal(total); 
	}
	/**
	 * 根据id查询一个分类信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void getPacTypeById(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		Map<String, Object> pacTypeById = wechatProPacTypeMapper.getPacTypeById(params);
		outputObject.setBean(pacTypeById);
	}

	/**
	 * 添加一个分类
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void addPacType(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		Map<String, Object> user = inputObject.getLogParams();
		//判断分类名称是否为空
		 String proPacTypeName = params.get("proPacTypeName").toString();
		if (JudgeUtil.isNull(proPacTypeName)){
			outputObject.setreturnMessage("分类名称不能为空！");
			return ;
		}
		//限制分类名称
		if (proPacTypeName.length() >=50){
			outputObject.setreturnMessage("分类名称不能大于50个字符！");
			return;
		}
		//判断分类描述长度
		if (params.get("proPacTypeDesc") != null){
			if (params.get("proPacTypeDesc").toString().length() >=500){
				outputObject.setreturnMessage("分类描述不能大于500字符");
			return;
			}
		}
		//查询添加的名称是否已经存在
		Map<String, Object> pacTypeByName = wechatProPacTypeMapper.getPacTypeByName(params);
		if (pacTypeByName != null){
			outputObject.setreturnMessage("您添加的分类名称已经存在请重新输入!");
			return;
		}
		//放入创建者信息
		params.put("createId",user.get("id"));
		params.put("createTime",DateUtil.getTimeAndToString());
		wechatProPacTypeMapper.addPacType(params);
	}
	
	/**
	 * 删除一个分类
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void deletePacType(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		wechatProPacTypeMapper.deletePacType(params);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("proPacTypeId", params.get("id"));
		List<Map<String,Object>> productSelect = wechatProPacTypeRestaurantMapper.getProductSelect(map);
		List<Map<String,Object>> packageListSelect = wechatProPacTypeRestaurantMapper.getPackageListSelect(map);
		List<Map<String,Object>> choosePackageSelect = wechatProPacTypeRestaurantMapper.getChoosePackageSelect(map);
		
		if (productSelect.size() >0 || packageListSelect.size()>0 || choosePackageSelect.size() >0 ){
			wechatProPacTypeRestaurantMapper.deleteRestaurantById(map);
		}
	}

	/**
	 * 修改一个分类
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updatePacType(InputObject inputObject, OutputObject outputObject)throws Exception {
		Map<String, Object> params = inputObject.getParams();
		//判断分类名称是否为空
		 String proPacTypeName = params.get("proPacTypeName").toString();
		if (JudgeUtil.isNull(proPacTypeName)){
			outputObject.setreturnMessage("分类名称不能为空！");
			return ;
		}
		//限制分类名称
		if (proPacTypeName.length() >=50){
			outputObject.setreturnMessage("分类名称不能大于50个字符！");
			return;
		}
		//判断分类描述长度
		if (params.get("proPacTypeDesc") != null){
			if (params.get("proPacTypeDesc").toString().length() >=500){
				outputObject.setreturnMessage("分类描述不能大于500字符");
			return;
			}
		}
		//查询修改后的名称是否重复
		String id = params.get("id").toString();
		Map<String, Object> pacTypeByName = wechatProPacTypeMapper.getPacTypeByName(params);
		if (pacTypeByName != null){
			String idByName = pacTypeByName.get("id").toString();
			if (!id.equals(idByName)){
				outputObject.setreturnMessage("分类名称已经存在，请重新输入");
				return;
			}
		}
		wechatProPacTypeMapper.updatePacType(params);
	}

}
