package com.ssm.wechatpro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ssm.wechatpro.dao.WechatProductMapper;
import com.ssm.wechatpro.dao.WechatProductMenuMapper;
import com.ssm.wechatpro.dao.WechatProductTypeMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatProductTypeService;
import com.ssm.wechatpro.util.DateUtil;
import com.ssm.wechatpro.util.JudgeUtil;

@Service
public class WechatProductTypeServiceImpl implements WechatProductTypeService {

	@Resource
	private WechatProductTypeMapper wechatProductTypeMapper;
	@Resource
	private WechatProductMapper wechatProductMapper;
	@Resource
	private WechatProductMenuMapper wechatProductMenuMapper;
	
	/**
	 * 查询所有的商品类型列表
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void getProductTypeList(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		if (params == null ){
			outputObject.setreturnMessage("当前没有商品类型");
			return;
		}
		//获取分页信息
		int page = Integer.parseInt(params.get("offset").toString())/Integer.parseInt(params.get("limit").toString());
		page++;
		int limit = Integer.parseInt(params.get("limit").toString());
		List<Map<String,Object>> productTypeList = wechatProductTypeMapper.getProductTypeList(params ,new PageBounds(page,limit));
		PageList<Map<String, Object>> abilityInfoPageList = (PageList<Map<String, Object>>)productTypeList;
		//获取当前页数的总数
		int total = abilityInfoPageList.getPaginator().getTotalCount();
		outputObject.setBeans(productTypeList);
		outputObject.settotal(total);
	}
	
	/***
	 * 获取一个商品类型信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void getProductTypeById(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> id = inputObject.getParams();
		Map<String, Object> productType = wechatProductTypeMapper.getProductTypeById(id);
		outputObject.setBean(productType);
	}

	/**
	 * 添加一个商品类型
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception 
	 */
	@Override
	public void addProductType(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		Map<String, Object> user = inputObject.getLogParams();
		//判断商品类型名是否存在
		String typeName = params.get("typeName").toString();
		if (JudgeUtil.isNull(typeName)){
			outputObject.setreturnMessage("商品类型名不能为空！");
			return;
		}
		//判断商品类型名长度是否大于20
		if (typeName.length() > 20){
			outputObject.setreturnMessage("商品类型名称需要在20个字符以内");
			return;
		}
		//判断商品类型描述是否大于200
		String typeDesc = params.get("typeDesc").toString();
		if(typeDesc.length()>200){
			outputObject.setreturnMessage("产品类型名称需要在200个字符以内");
			return;
		}
		//判断商品类型名是否重复
		Map<String, Object> productType = wechatProductTypeMapper.getProductTypeByName(params);
		if (productType != null){
			outputObject.setreturnMessage("该商品类型名称已经存在");
			return;
		}
		
		int priority = 0;
		// 获取上线商品分类中优先级值最大的
		try{
		Map<String, Object> priorityMap = wechatProductTypeMapper.getMaxTypePriority();
		if(!JudgeUtil.isNull(priorityMap.get("priority") + "")){
			priority = Integer.parseInt(priorityMap.get("priority") + "");
		}
		}catch (Exception e) {
			priority = 0;
		}
		priority += 1;
		
		params.put("typePriority", priority);// 优先级值增加1
		params.put("createId",user.get("id"));
		params.put("createTime",DateUtil.getTimeAndToString());
		wechatProductTypeMapper.addProductType(params);
	}

	/**
	 * 	修改一个商品类型信息
	 *  @param inputObject
	 * 	@param outputObject
	 * 	@throws Exception 
	 */
	@Override
	public void updateProductType(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		//判断商品类型名是否存在
		String typeName = params.get("typeName").toString();
		if (JudgeUtil.isNull(typeName)){
			outputObject.setreturnMessage("商品类型名不能为空！");
			return;
		}
		//判断商品类型名长度是否大于20
		if (typeName.length() > 20){
			outputObject.setreturnMessage("商品类型名称需要在20个字符以内");
			return;
		}
		//判断商品类型描述是否大于200
		String typeDesc = null;
		try {
			typeDesc = params.get("typeDesc").toString();
		} catch (Exception e) {
		}
		if (typeDesc != null){
			if(typeDesc.length()>200){
			outputObject.setreturnMessage("产品类型名称需要在200个字符以内");
			return;
			}
		}
		String id = params.get("id").toString();
		Map<String, Object> productTypeByName = wechatProductTypeMapper.getProductTypeByName(params);
		String idByName = null;
		if (productTypeByName != null){
			idByName = productTypeByName.get("id").toString();
			if (!idByName.equals(id)){
				outputObject.setreturnMessage("该商品类型已经存在,请重新添加");
				return;
			}
		}
		//将上次产品审核意见清空
		params.put("typeOpinion", "");
		wechatProductTypeMapper.upateProductType(params);
	}

	/***
	 * 修改状态为提升状态
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateStateSubmit(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
			params.put("typeState",1);
			wechatProductTypeMapper.upateProductType(params);
	}

	/***
	 * 删除一个商品类型
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void deleteProductType(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		
		//获取该类别下的所有商品
		Map<String, Object> productParams = new HashMap<String, Object>();
		productParams.put("productType", params.get("id"));
		List<Map<String,Object>> productList = wechatProductMapper.getProductList(productParams);
		
		for (Map<String, Object> map : productList) {
			Map<String, Object>  thisMap = new HashMap<String, Object>();
			thisMap.put("id", map.get("id"));
			thisMap.put("productState", 4);
			wechatProductMapper.updateProduct(thisMap);
		}
		
		//wechatProductMapper.updateProduct(map);
		params.put("typeState", 4);
		wechatProductTypeMapper.upateProductType(params);
	}
	/***
	 * 获取上线的类别
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void getTypeOnline(InputObject inputObject, OutputObject outputObject) throws Exception {
			Map<String, Object> params = inputObject.getParams();
			params.put("typeState", 2);
			List<Map<String, Object>> typeList = wechatProductTypeMapper.getProductTypeList(params);
			if (typeList.size() <= 0){
				outputObject.setreturnMessage("目前还没有商品类别");
				return;
			}
			outputObject.setBeans(typeList);
	}
	
	/**
	 * 查询所有商品的种类，并按照id递增顺序排列
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectProductType(InputObject inputObject, OutputObject outputObject) throws Exception {
		List<Map<String, Object>> list = wechatProductTypeMapper.selectProductType(); 
		outputObject.setBeans(list);
	}
	/**
	 *获取所有待审核的商品类别
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void getCheckPendingList(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		params.put("typeState", 1);
		//获取分页信息
		int page = Integer.parseInt(params.get("offset").toString())/Integer.parseInt(params.get("limit").toString());
		page++;
		int limit = Integer.parseInt(params.get("limit").toString());
		List<Map<String,Object>> productTypeList = wechatProductTypeMapper.getProductTypeList(params ,new PageBounds(page,limit));
		PageList<Map<String, Object>> abilityInfoPageList = (PageList<Map<String, Object>>)productTypeList;
		//获取当前页数的总数
		int total = abilityInfoPageList.getPaginator().getTotalCount();
		outputObject.setBeans(productTypeList);
		outputObject.settotal(total);
		
	}
	
	/**
	 *审核通过一个商品类别
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateStatePass(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		params.put("typeState", 2);
		wechatProductTypeMapper.upateProductType(params);
	}
	/**
	 *审核不通过一个商品类别
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateStateNoPass(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		params.put("typeState", 3);
		wechatProductTypeMapper.upateProductType(params);
	}
	
}
