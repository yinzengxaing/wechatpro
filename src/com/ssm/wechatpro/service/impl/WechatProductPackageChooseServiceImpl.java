package com.ssm.wechatpro.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ssm.wechatpro.dao.WechatProductPackageChooseMapper;
import com.ssm.wechatpro.dao.WechatProductPackageMapper;
import com.ssm.wechatpro.dao.WechatProductTypeMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatProductPackageChooseService;
import com.ssm.wechatpro.util.JudgeUtil;

@Service("WechatProductPackageChooseService")
public class WechatProductPackageChooseServiceImpl implements WechatProductPackageChooseService {

	@Autowired
	WechatProductPackageChooseMapper wechatProductPackageChooseMapper;
	
	@Autowired
	WechatProductPackageMapper wechatProductPackageMapper;
	
	@Autowired
	WechatProductTypeMapper wechatProductTypeMapper;
	/**
	 * 添加可选套餐
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
		http://127.0.0.1:8080/wechatpro//post/WechatProductPackageChooseController/AddPackage?type=1&packageGroupProductId1=1,2,3&packageGroupProductType1=1&packageGroupProductMinPrice1=10&packageName=lilili&packageLogo=3&packagePrice=4&packageIntegral=3&packageType=1
	 */
	
	@Override
	public void addPackage(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> params = inputObject.getParams();
		String ids = "";
		String[] groupIds = params.get("groupIds").toString().split(",");
		String[] groupProducts = params.get("groupProducts").toString().split("-");
		String[] groupType = params.get("groupType").toString().split(",");
		String[] groupProductNum = params.get("groupProductNum").toString().split(",");
		//添加组
		if(groupIds.length != groupProducts.length  && groupIds.length != groupType.length && groupIds.length != groupProductNum.length)
			outputObject.setreturnMessage("参数有误");
		else{
			for(int i = 0; i < groupIds.length; i ++){
				Map<String,Object> map = new HashMap<>();
				map.put("packageGroupProductTypeId",groupIds[i]);
				map.put("packageGroupProductId",groupProducts[i]);
				if(!JudgeUtil.IsIntNumber(groupType[i]))
					continue;
				map.put("packageGroupProductType",groupType[i]);
				if(JudgeUtil.IsIntNumber(groupProductNum[i])){
					map.put("packageGroupProductNum",groupProductNum[i]);
				}else{
					map.put("packageGroupProductNum",0);
				}
				map.put("packageGroupProductMinPrice",selectAllPackage(groupProducts[i],Integer.parseInt(groupType[i]),Integer.parseInt(map.get("packageGroupProductNum").toString())));
				wechatProductPackageChooseMapper.AddWechatProductChooseGroup(map);
				ids += map.get("id")+",";
			}	
		}

		if(ids.equals("")){
			outputObject.setreturnCode("-1");
			outputObject.setreturnMessage("无商品信息");
		}else{
			params.put("productId", ids);
			params.put("packageState", 0);
			params.put("createTime", new Date());
			params.put("createId", inputObject.getLogParams().get("id"));
			params.put("packageWetherDiscount", "N");
			wechatProductPackageChooseMapper.AddWechatProductChoosePackage(params);
		}
	}
	
	/**
	 * 计算最少价钱 productId产品ids，type 选择种类，num选择个数
	 * @param productId
	 * @param type
	 * @param num
	 * @return
	 * @throws Exception
	 */
	private double selectAllPackage(String productId,int type,int num) throws Exception {
		String[] groupIds = productId.split(",");
		if(type == 1)
			num = 1;
		if(type == 3)
			num = groupIds.length;
		if(num != 0){
			Map<String,Object> map = new HashMap<>();
			map.put("ids", productId);
			map.put("num", num);
			List<Map<String, Object>> moneyList = wechatProductPackageChooseMapper.selectwechatProductWithProductPrice(map);
			double money = 0;
			for(Map<String, Object> one:moneyList)		
				money += Double.parseDouble(one.get("productPrice").toString());		
			return money;
		}else
			return 0;
	}

	/**
	 * 显示所有套餐
	 * @param inputObject
	 * @param outputObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public void selectAllPackage(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> params = inputObject.getParams();
		List<Map<String, Object>> beans = wechatProductPackageChooseMapper.selectAllPackage(params);
		outputObject.setBeans(beans);
		outputObject.settotal(beans.size());
	}
	
	/**
	 * 查看详情
	 * @param inputObject
	 * @param outputObject
	 * @return
	 * @throws Exception
	 */
	public void selectOne(InputObject inputObject, OutputObject outputObject) throws Exception{
		Map<String,Object> params = inputObject.getParams();
		if(!JudgeUtil.IsIntNumber(params.get("id").toString()))
			outputObject.setreturnMessage("参数有误");
		else{
			Map<String, Object> map = wechatProductPackageChooseMapper.selectOneWechatProductChoosePackage(params);
			if(map != null){
				Map<String, Object> productId = new HashMap<>();
				String productIdList = ""; // 商品的id组合的字符串
				productId.put("ids", map.get("productId"));
				List<Map<String, Object>> productIds = wechatProductPackageChooseMapper.selectWechatProductChooseGroupByIds(productId);
				for(Map<String, Object> one :productIds){
					Map<String, Object> packageGroupProductId = new HashMap<>();
					packageGroupProductId.put("ids", one.get("packageGroupProductId"));
					List<Map<String, Object>> product = wechatProductPackageChooseMapper.selectwechatProductByIds(packageGroupProductId);
					// 获得该组中商品名称的字符串
					String productNameList = "";
					for(Map<String, Object> two: product){
						productNameList += two.get("productName") + ",";
						productIdList += two.get("productId") + ",";
					}
					one.put("productNameList", productNameList); // 将名称组合的字符串放入到集合中
					one.put("rows", product);
				}
				outputObject.setBeans(productIds);
				map.put("productIdList", productIdList); // 将该套餐中的商品的id组合的字符串放入到集合中
				outputObject.setBean(map);
			}else{
				outputObject.setreturnMessage("获取套餐失败");
			}
		}
	}
	/**
	 * 删除活动套餐
	 * @param inputObject
	 * @param outputObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public void deleteOne(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> params = inputObject.getParams();
		if(!JudgeUtil.IsIntNumber(params.get("id").toString()))
			outputObject.setreturnMessage("参数有误");
		else{
			params.put("packageState", 4);
			wechatProductPackageChooseMapper.updatePackageState(params);
		}
	}
	
	/**
	 * 修改活动套餐
	 * @param inputObject
	 * @param outputObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public void updateOne(InputObject inputObject, OutputObject outputObject)throws Exception {
		Map<String,Object> params = inputObject.getParams();
		Map<String, Object> choosePackage = wechatProductPackageChooseMapper.selectOneWechatProductChoosePackage(params);
		
		if(choosePackage != null){
			
			List<String> ids = new ArrayList<String>();
			String[] groupIds = params.get("groupIds").toString().split(",");
			String[] groupProducts = params.get("groupProducts").toString().split("-");
			String[] groupType = params.get("groupType").toString().split(",");
			String[] groupProductNum = params.get("groupProductNum").toString().split(",");
			if( groupIds.length != groupProducts.length  && groupIds.length != groupType.length && groupIds.length != groupProductNum.length)
				outputObject.setreturnMessage("参数有误");
			
			//删除旧数据
			Map<String,Object> map = new HashMap<>();
			map.put("ids", choosePackage.get("productId").toString());
			wechatProductPackageChooseMapper.delectWechatProductChooseGroup(map);
			//添加新数据
			for(int i = 0; i < groupIds.length; i ++){
				if(groupIds[i].equals(" "))
					continue;
				map = new HashMap<>();
				map.put("packageGroupProductTypeId",groupIds[i]);
				map.put("packageGroupProductId",groupProducts[i]);
				if(!JudgeUtil.IsIntNumber(groupType[i]))
					continue;
				map.put("packageGroupProductType",groupType[i]);
				if(JudgeUtil.IsIntNumber(groupProductNum[i])){
					map.put("packageGroupProductNum",groupProductNum[i]);
				}else{
					map.put("packageGroupProductNum",0);
				}
				map.put("packageGroupProductMinPrice",selectAllPackage(groupProducts[i],Integer.parseInt(groupType[i]),Integer.parseInt(map.get("packageGroupProductNum").toString())));
				wechatProductPackageChooseMapper.AddWechatProductChooseGroup(map);
				ids.add(map.get("id").toString());
			}
			
			
			
			//修改套餐
			if(ids.size() == 0){
				outputObject.setreturnMessage("无商品信息");
			}else{
				String productId = "";
				for(String one :ids){
					productId += one+",";
				}
				params.put("productId", productId);
				params.put("packageState", 0);
				wechatProductPackageChooseMapper.updateWechatProductChoosePackage(params);
			}
		}else
			outputObject.setreturnMessage("查无此套餐");
	}
	
	/**
	 * 获取待审核状态的套餐信息
	 */
	public void getCheckPendingListupdateOne(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		map.put("key", map.get("packageName"));
		// 分页信息
		int page = Integer.parseInt(map.get("offset").toString()) / Integer.parseInt(map.get("limit").toString());
		page ++;
		int limit = Integer.parseInt(map.get("limit").toString());
		List<Map<String, Object>> list = wechatProductPackageChooseMapper.getCheckPendingList(map,new PageBounds(page, limit));
		// 类型强转
		PageList<Map<String, Object>> pageList = (PageList<Map<String,Object>>)list;
		// 获取当前页的总数
		int total = pageList.getPaginator().getTotalCount();
		outputObject.setBeans(list);
		outputObject.settotal(total);
	}

	@Override
	public void updatePackageStatePass(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		wechatProductPackageChooseMapper.updatePackageStatePass(map);
	}

	@Override
	public void updatePackageStateNotPass(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		wechatProductPackageChooseMapper.updatePackageStateNotPass(map);
	}
	
	@Override
	public void updateChoosePackageState(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		wechatProductPackageChooseMapper.updateChoosePackageState(map);
	}
}
