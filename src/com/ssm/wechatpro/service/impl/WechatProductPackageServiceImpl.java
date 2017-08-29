package com.ssm.wechatpro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ssm.wechatpro.dao.WechatProductPackageMapper;
import com.ssm.wechatpro.dao.WechatProductTypeMapper;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatProductPackageService;
import com.ssm.wechatpro.util.DateUtil;
import com.ssm.wechatpro.util.JudgeUtil;

@Service("wechatProductPackageService")
public class WechatProductPackageServiceImpl implements WechatProductPackageService {

	@Autowired
	WechatProductPackageMapper wechatProductPackageMapper;
	
	@Autowired
	WechatProductTypeMapper wechatProductTypeMapper;

	/**
	 * 显示所有的套餐(包括永久有效或时间段内有效（超过套餐结束时间）)
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectAllPackage(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		List<Map<String, Object>> list = wechatProductPackageMapper.selectAllPackage(map);
		outputObject.settotal(list.size());
		outputObject.setBeans(list);
	}

	/**
	 * 通过id查询套餐的信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectPackageById(InputObject inputObject,	OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();// 获取套餐的id
		// 根据id查询出来套餐的信息
		Map<String, Object> productPackages = wechatProductPackageMapper.selectPackageById(map);
		// 通过产品ID组合的字符串获得商品信息
		Map<String, Object> productIdList = new HashMap<>();
		productIdList.put("productId", productPackages.get("productId")); // 获得商品id的字符串
		// 根据查询出来套餐信息中的产品id的字符串来查询
		List<Map<String, Object>> productNameString = wechatProductPackageMapper.selectPackageDetailInfoById(productIdList);
		String productNameList = "";
		for(Map<String, Object> maps : productNameString){
			productNameList = productNameList + maps.get("productName") + ","; // 获得商品名称组合的字符串
		}
		productPackages.put("productNameList", productNameList);
		outputObject.setBean(productPackages);
	}
	
	/**
	 * 添加一个套餐
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void insertPackage(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		// 获得当前登录人的编号
		Map<String, Object> adminMap = new  HashMap<>();
		adminMap.put("adminNo", inputObject.getLogParams().get("adminNo"));
		Map<String, Object> adminNo = wechatProductPackageMapper.selectAdminIdByAdminNo(adminMap);
		map.put("createId",adminNo.get("id"));
		map.put("createTime", DateUtil.getTimeAndToString());
		wechatProductPackageMapper.insertPackage(map);// 添加一个新的套餐
	}

	/**
	 * 更新一个套餐
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updatePackageState(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		wechatProductPackageMapper.updatePackageState(map);
	}

	@Override
	public void updatePackageStatePass(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		wechatProductPackageMapper.updatePackageStatePass(map);
	}

	@Override
	public void updatePackageStateNotPass(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		wechatProductPackageMapper.updatePackageStateNotPass(map);
	}

	/**
	 * 通过id删除套餐
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void deletePackageById(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		wechatProductPackageMapper.deletePackageById(map);
	}

	/**
	 * 通过套餐ID查询套餐中所含有商品的信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectPackageDetailInfoById(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		List<Map<String, Object>> list = wechatProductPackageMapper.selectPackageDetailInfoById(map);
		outputObject.setBeans(list);
		outputObject.settotal(list.size());
	}

	/**
	 * 首先查询到所有的商品类型，然后根据商品类型来查询商品
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectProductByProductTypeId(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> productListName = inputObject.getParams(); // 接收查询到该种套餐中的商品id
		boolean flag = JudgeUtil.isNull(productListName.get("productNameList")+""); // 如果参数不为空;
		
		String productListNameString = null;
		if(!flag)
			productListNameString = productListName.get("productNameList").toString(); 
		// 先查询到所有的商品类型，然后根据商品
		List<Map<String, Object>> productType = wechatProductTypeMapper.selectProductType();
		int n = productType.size(); // 表示商品类型的数量
		for(int i  = n - 1 ; i >= 0 ; i --){
			// 根据商品类型的id查询该种类型中的全部商品
			List<Map<String,Object>> productList = wechatProductPackageMapper.selectProductByProductTypeId(productType.get(i));
			// 如果该种套餐类型中不还有商品，则将这种商品类别移除
			if(productList.size() == 0 ){
				productType.remove(i);
			}
			else{
				if(!flag){ // 如果为空的话不进行此操作
					String productNameList = "";
					// 如果该分类中含有套餐中的商品，则将名称拼接在一块
					for(int j = 0 ; j < productList.size(); j ++){
						if(productListNameString.indexOf("," + productList.get(j).get("productId").toString() + ",") >= 0 )
							productNameList += productList.get(j).get("productName") + ",";
					}
					productType.get(i).put("productNameList", productNameList);
				}
				productType.get(i).put("productList", productList);
			}
		}
		outputObject.setBeans(productType);
	}

	/**
	 * 更新套餐信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updatePackageInfo(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		Map<String, Object> adminMap = new  HashMap<>();
		adminMap.put("adminNo", inputObject.getLogParams().get("adminNo"));
		Map<String, Object> adminNo = wechatProductPackageMapper.selectAdminIdByAdminNo(adminMap);
		map.put("createId",adminNo.get("id"));
		map.put("createTime", DateUtil.getTimeAndToString()); // 创建时间
		wechatProductPackageMapper.updatePackageInfo(map);
	}

	/**
	 * 查询当前为提审状态的套餐
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectProductByState(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		// 分页信息
		int page = Integer.parseInt(map.get("offset").toString()) / Integer.parseInt(map.get("limit").toString());
		page ++;
		int limit = Integer.parseInt(map.get("limit").toString());
		List<Map<String, Object>> list = wechatProductPackageMapper.selectProductByState(map,new PageBounds(page, limit));
		// 类型强转，获取当前的分页信息
		PageList<Map<String, Object>> pageList = (PageList<Map<String,Object>>)list;
		// 获取当前页的总数
		int total = pageList.getPaginator().getTotalCount();
		outputObject.settotal(total);
		outputObject.setBeans(list);
	}
}
