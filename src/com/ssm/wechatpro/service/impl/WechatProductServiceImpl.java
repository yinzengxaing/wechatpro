package com.ssm.wechatpro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ssm.wechatpro.dao.UploadMapper;
import com.ssm.wechatpro.dao.WechatProductMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatProductService;
import com.ssm.wechatpro.util.DateUtil;
import com.ssm.wechatpro.util.JudgeUtil;
@Service
public class WechatProductServiceImpl implements WechatProductService {

	@Resource
	private WechatProductMapper wechatProductMapper;
	@Resource
	private UploadMapper uploadMapper;
	/**
	 * 查询所有商品信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void getProductList(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		//添加产品类型的数量
		List<Map<String,Object>> productList = wechatProductMapper.getProductList(params);
		List<Map<String,Object>> productTypeList = wechatProductMapper.getProductTypeList();
		
		for (Map<String, Object> map : productList) {
			//获取图片的路径的id
			Map<String, Object> selectLogo = new HashMap<String, Object>();
			//查询图片路径
			selectLogo.put("id",  map.get("productLogo"));
			Map<String, Object> logo = uploadMapper.selectById(selectLogo);
			//将路径放入map中
			map.put("productLogo", logo.get("optionPath"));
			//将套餐数量放入map
			Map<String, Object> packageCount  = wechatProductMapper.getPackageCountByProductId(map);
			map.put("count",packageCount.get("count") );
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productTypeList",productTypeList);
		outputObject.setBean(map);
		outputObject.setBeans(productList);
		outputObject.settotal(productList.size());			
	}

	/**
	 * 根据id查询一个商品信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void getProductById(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		Map<String, Object> product = wechatProductMapper.getProductById(map);	
		//获取图片的路径的id
		Map<String, Object> selectLogo = new HashMap<String, Object>();
		//将图片的id 放入product中并命名为imgId
		product.put("imgId", product.get("productLogo"));
		//查询图片路径
		selectLogo.put("id",  product.get("productLogo"));
		Map<String, Object> logo = uploadMapper.selectById(selectLogo);
		//将真实路径放 入product中
		product.put("productLogo", logo.get("optionPath"));
		outputObject.setBean(product);
	}

	/**
	 * 添加一个商品信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void addProduct(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		Map<String,Object> user = inputObject.getLogParams(); //获取登录的用户信息
		//判断商品的名称
		String productName = map.get("productName").toString();
		if(JudgeUtil.isNull(productName)){
			outputObject.setreturnMessage("商品名称不能为空！");
			return;
		}
		//判断商品名称长度
		if (productName.length() >20){
			outputObject.setreturnMessage("商品名称不能大于20个字符！");
			return;
		}
		//判断价格不能为null
		String productPriceStr = map.get("productPrice").toString();
		if (JudgeUtil.isNull(productPriceStr)){
			outputObject.setreturnMessage("商品价格不能为空！");
			return;
		}
		// 判断开始时间是否大于结束时间
		String startTime = map.get("startTime") + "";
		String endTime = map.get("endTime") + "";
		String flag = map.get("flag") + "";
		if(flag.endsWith("Y") && startTime.compareTo(endTime) > 0){
			outputObject.setreturnMessage("开始时间不能大于结束时间");
			return ;
		}
		//价格进行转型
		try {
			Double.parseDouble(productPriceStr);
		} catch (Exception e) {
			outputObject.setreturnMessage("请输入合法数字！");
			return;
		}
		//根据产品名查询检查产品名是否存在
		Map<String, Object> product = wechatProductMapper.getProductByName(map);
		if (product != null){
			outputObject.setreturnMessage("该产品名称已经存在，请重新输入！");
			return;
		}
		int maxCount = 0; // 表示商品的优先级
		// 查询优先级
		Map<String, Object> maxPriorityCount = wechatProductMapper.selectMaxPriority();
		if(Integer.parseInt((maxPriorityCount.get("productPriority")+"")) >= 0){
			maxCount = Integer.parseInt((maxPriorityCount.get("productPriority")+""));
		}
		maxCount += 1;
		//加入创建人id即为当前用户
		map.put("createId", user.get("id"));
		//加入创建时间
		map.put("createTime", DateUtil.getTimeAndToString());
		//加入产品条码这里使用时间来代替
		map.put("productKeyStr", DateUtil.getToString());
		//添加时产品的审核意见为空
		map.put("productOpinion", "");
		// 商品优先级
		map.put("productPriority", maxCount);
		wechatProductMapper.addProduct(map);
	}
	/**
	 * 修改一个商品信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateProduct(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		//判断商品的名称
		String productName = map.get("productName").toString();
		if(JudgeUtil.isNull(productName)){
			outputObject.setreturnMessage("商品名称不能为空！");
			return;
		}
		//判断商品名称长度
		if (productName.length() >20){
			outputObject.setreturnMessage("商品名称不能大于20个字符！");
			return;
		}
		//判断价格不能为null
		String productPriceStr = map.get("productPrice").toString();
		if (JudgeUtil.isNull(productPriceStr)){
			outputObject.setreturnMessage("商品价格不能为空！");
			return;
		}
		//价格进行转型
		try {
			Double.parseDouble(productPriceStr);
		} catch (Exception e) {
			outputObject.setreturnMessage("请输入合法数字！");
			return;
		}
		String id = map.get("id").toString();
		//根据参数中的产品名获取产品
		Map<String, Object> productByName = wechatProductMapper.getProductByName(map);
		//检查所改的商品名是否已经存在
		String idByName = null;
		if (productByName != null){
			idByName = productByName.get("id").toString();
			if (!idByName.equals(id)){
				outputObject.setreturnMessage("该产品名称已经存在，请重新输入！");
				return;
			}
		}
		//将审核意见设置为空
		map.put("productOpinion", "");
		wechatProductMapper.updateProduct(map);
	}

	/**
	 * 删除一个商品信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void deleteProduct(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		params.put("productState", 4);
		wechatProductMapper.updateProduct(params);
	}

	/**
	 * 查看一个商品所在套餐的信息详情
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void getPackageByProductId(InputObject inputObject, OutputObject outputObject)throws Exception {
		Map<String, Object> params = inputObject.getParams();
		 List<Map<String,Object>> packageList = wechatProductMapper.getPackageByProductId(params);
		 for (Map<String, Object> map : packageList) {
			 HashMap<String, Object> logId = new HashMap<String ,Object>();
			 logId.put("id",  map.get("packageLogo"));
			 Map<String, Object> packageLogo  = uploadMapper.selectById(logId);
			 map.put("packageLogo", packageLogo.get("optionPath"));
		}
		outputObject.setBeans(packageList);
		outputObject.settotal(packageList.size());
	}

	/**
	 * 提审一个商品
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateStateSubmit(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		params.put("productState", 1);
		wechatProductMapper.updateProduct(params);
	}
	
	/**
	 * 按照类别查询产品
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void selectProductType(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		List<Map<String, Object>> list = wechatProductMapper.selectProductType(map);
		outputObject.setBeans(list);
	}
	/**
	 * 获取所有待审核的商品
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void getCheckPendingList(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		params.put("productState", 1);
		//获取分页信息
		int  page = Integer.parseInt(params.get("offset").toString()) / Integer.parseInt(params.get("limit").toString());
		page++;
		int limit = Integer.parseInt(params.get("limit").toString());
		List<Map<String, Object>> productList = wechatProductMapper.getProductList(params, new PageBounds(page,limit));
		//类型强转 获取当前页的 信息
		PageList<Map<String, Object>> abilityInfoPageList = (PageList<Map<String, Object>>) productList;
		//获取当前页的总数
		int total = abilityInfoPageList.getPaginator().getTotalCount();
		outputObject.setBeans(productList);
		outputObject.settotal(total);
	}

	/**
	 * 审核通过一个商品
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateStatePass(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		params.put("productState", 2);
		wechatProductMapper.updateProduct(params);
	}

	/**
	 * 审核不通过一个商品
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateStateNoPass(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		params.put("productState", 3);
		wechatProductMapper.updateProduct(params);
	}
	
	/**
	 * 获取所有的商品种类列表
	 */
	@Override
	public void getProductTypeList(InputObject inputObject, OutputObject outputObject) throws Exception {
		List<Map<String,Object>> productTypeList = wechatProductMapper.getProductTypeList();
		outputObject.setBeans(productTypeList);
	}
	

	
}
