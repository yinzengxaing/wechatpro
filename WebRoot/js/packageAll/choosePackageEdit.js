
var productTypePriceLists;// 表示选中商品的总价格
var productTypeIdList; // 表示选中商品的id组成的字符串
var productIdList;
//var imgId = null; // 套餐logo的id
var id = null;// 表示可选套餐的id
var ids = null;


$(function(e){
	receiveData();
	dataInit();
});

function dataInit(){
	// 查询所有的信息并显示在页面上
	AjaxPostUtil.request({url:path+"/post/wechatProductPackageController/selectProductByProductTypeId",params:{},type:"json",callback:function(json){
		if(json.returnCode == 0){
			
			Handlebars.registerHelper("productcreateTime",function(v1, options){
				if(v1.length>=10){
					return v1.substring(0,10);
				}else{
					return v1;
				}
			});
			
			Handlebars.registerHelper("picture",function(v1, options){
				if(v1!=null)
					return path + "/" + v1;
				else
					return path + "/assest/img/roleNoPic.png";
			});
		}else{
			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
		}
		var source = $("#productTypeBean").html();
		var template = Handlebars.compile(source);
		$("#productTypeList").html($("#productTypeList").html() + template(json));
/*		$(".img-thumbnail").each(function(){
	    	var myThis = $(this);
	    	var thisUrl = myThis.attr("src");
	    	$.ajax(thisUrl, {
	            type: 'get',
	            timeout: 1000,
	            success: function() {
	            },
	            error: function() {
	            	myThis.attr("src",path + "/assest/icon/maxLogo.jpg");
	            }
	        });
	    });*/
		reviewDate();
	}});
}

// 查询单个商品的信息
function reviewDate(){
	
	// 获得商品的信息
	if(id == null){
		id = $.req("id"); // 获得可选套餐的id
	}
	var params = {
			id : id,
	};
	// 通过可选套餐的id来查询相关信息
	AjaxPostUtil.request({url:path+"/post/WechatProductPackageChooseController/selectOne",params:params,type:'json',callback:function(json){
		var aaa =  json.returnCode;
		if(json.returnCode == 0){
			$("#productPackageName").val(json.bean.packageName); //套餐名称
			$("#logo").attr("src",path + "/" + json.bean.packageLogoPath); // logo
			$("#productPackageIntegral").val(json.bean.packageIntegral); // 套餐的积分
			$("#productPackageDesc").val(json.bean.packageDesc);// 套餐描述
			$("#productPackagePrice").val(json.bean.packagePrice); // 套餐价格
			$("#packagePrice").val(json.bean.packagePrice);// 套餐的价格
			var productIdList = "," + json.bean.productIdList + ","; // 可选套餐中所含有的商品
			imgId = json.bean.packageLogo; // 可选套餐中Logo的id
			// 判断该商品的有效时间（长期有效，或者时间段有效）
			if(json.bean.packageType == 1){
				$("#optionsRadios1").attr("checked", "true");
				$("[name = 'settime']").hide();
			}else{
				$("#optionsRadios2").attr("checked", "true");
				$("[name = 'settime']").show();
				$("#starttime").val(json.bean.packageStartTime);
				$("#endtime").val(json.bean.packageEndTime);
			}
			var chooseProductList = json.rows;// 表示返回的数据
			var $chooseProductListAll = $("div[name='chooseProductListOne']");
			// 对页面上的div[name='chooseProductListOne']进行遍历
			for(var i = 0 ; i < $chooseProductListAll.length; i ++){
				// 依次对返回的该套餐信息进行遍历
				for(var j = 0 ; j < chooseProductList.length; j ++){
					// 如果该套餐信息中的类型名称和页面上的相等，则将属性赋值
					if(chooseProductList[j].packageGroupProductTypeId == ($($chooseProductListAll[i]).find("span[name='chooseProductTypeId']").attr("value"))){
						$($chooseProductListAll[i]).find("input[name='productNameList']").attr("value", chooseProductList[j].productNameList);
						$($chooseProductListAll[i]).find("input[name=productNum]").attr("productIds", chooseProductList[j].id);
						// 给指定的单选框的name赋值
						var $checkName = $($chooseProductListAll[i]).find("input[name='optionsRadiosC"+chooseProductList[j].packageGroupProductTypeId+"']");
						// 各类商品的购买类型选择
						$($checkName).each(function(){
							if($(this).val() == chooseProductList[j].packageGroupProductType){
								$(this).attr("checked", true);
								if($(this).val() == 2){
									var $aaaa = $("label[name = 'labelProductNum"+chooseProductList[j].packageGroupProductTypeId+"']");
									$aaaa.show();
									$aaaa.find("input[name=productNum]").attr("value",chooseProductList[j].packageGroupProductNum);
								}
							}
						});
					}
				}
				
			}
			// 勾选商品
			var returnProductIdList = "," + json.bean.productIdList+ ",";
			var $productCheckedList = $("input[name=productChecked]");
			$($productCheckedList).each(function(){
				if(returnProductIdList.indexOf("," + $(this).attr("productId") + ",") != -1){
					$(this).attr("checked", true);
				}
			});
			
		}else{
			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
		}
		eventInit();
	}});
}

function eventInit(){
	
	$("#productTypeList").on("click", ".packageDiv", function(){
			// 当前选中的div
			var productTypeNameLists = "";
			$(this).find("input[name='productChecked']:checked").each(function(){
				productTypeNameLists += $(this).val() +",";
			});
			$(this).parent().find("input[name='productNameList']").attr("value", productTypeNameLists);
	});

	$("#optionsRadios2").on("click",function(){
		$("#starttime").attr("value", "");
		$("#endtime").attr("value", "");
	});
	
	$("#cancel").on("click", function(){
		location.href = "packageList.html";
	});
	
	
	$("[fromOff = '1']").click(function(){
		$('#addPackageForm').bootstrapValidator('disableSubmitButtons', false); 
	});
	
	
	// 监听购买类型中的单选按钮
	$(".checkedOrNo").on("click",function(){
		var valu = $(this).attr("value");
		var valee = $(this).attr("typeId");
		if(valu % 2 == 1){
			$("label[name = 'labelProductNum"+valee+"']").hide();
		}else{
			$("label[name = 'labelProductNum"+valee+"']").show();
		}
	});
	
	var productPackagePrice = 0;
	//表单的验证
	$('#addPackageForm').bootstrapValidator({
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields:{
			//套餐名称
			productPackageName: {
				validators: {
					notEmpty: {
						message: '套餐名称不能为空！'
					},
					stringLength: {
						 max: 20,
						message: '套餐名称长度必须小于20字符！'
					}
				}
			},
			//套餐描述
			productPackageDesc:{
				validators: {
					notEmpty: {
						message: '套餐描述不能为空！'
					},
					stringLength: {
						 max: 20,
						message: '套餐描述信息长度必须小于20字符！'
					}
				}		
			},
			// 套餐积分
			productPackageIntegral:{
				validators: {
					regexp:{
						regexp: /^[1-9]$/,
						message: '套餐积分不能为0',
					}
				}
			},
			//套餐价格
			productPackagePrice: {
				validators: {
					notEmpty: {
						message: '套餐价格不能为空！',
					},
					regexp: {
			              regexp: /^[1-9]+(\.[0-9]{1,2})?$/,
			              message: '套餐价格只能是大于0的数字',
			        }
				}
			},
			//商品选项
			productNameList: {
				validators: {
					notEmpty: {
						message: '商品选项不能为空！',
					}
				}
			}
		}
	}).on('success.form.bv', function(e) {
		if(imgId==null){
			qiao.bs.msg({msg:"请选择商品LOGO",type:'danger'});
		}else{
			 if($("[name = 'optionsRadios']:checked").val() == 2){
				if(isNull($("#starttime").val()) || isNull($("#endtime").val())){
					qiao.bs.msg({msg:"请选择时间",type:'danger'});
					return false;
				}
				if($("#starttime").val() > $("#endtime").val()){
					qiao.bs.msg({msg:"请选择正确的时间段",type:'danger'});
					return false;
				}
			 }
			// 设置参数
			var $productNum = $("input[name='productNum']");
			//产品id
			var groupProducts = "";
			//分类id
			var groupIds = "";
			//多选或单选
			var groupType = "";
			//多选个数；
			var groupProductNum = "";
			var ids = "";
			for(var i = 0; i <$productNum.length; i ++){
				var typeId =$($productNum[i]).attr("typeId");
				var productids =$($productNum[i]).attr("productids");
				var productNum = $($productNum[i]).val();
				var optionsRadiosC = $("input[name='optionsRadiosC"+typeId+"']:checked").val();
				var $productChecked =$($("[name='rows']")[i]).find("input[name='productChecked']:checked");
				var productId = '';
				if($productChecked.length < productNum && optionsRadiosC == 2){
					qiao.bs.msg({msg:"第"+(i * 1 + 1)+"组商品多选数目超过规定数目",type:'danger'});
					return false;
				}
				//拼接id
				for(var j = 0; j <$productChecked.length; j ++){
					productId += $($productChecked[j]).attr("productId")+",";
				}
				if(productId != ''){
					//拼接食品分类
					if(!isNull(typeId)){
						groupIds += typeId +",";
						}
					else{
						groupIds += " ,";
						}
					groupType += optionsRadiosC +",";
					groupProductNum += productNum +",";
					groupProducts += productId +"-";
				}
			}
			var param={
					id:id,
					ids:ids,
					groupProducts:groupProducts,
					groupIds:groupIds,
					groupType:groupType,
					groupProductNum:groupProductNum,
					packageName:$("#productPackageName").val(),
					packageLogo:imgId,
					packagePrice:$("#packagePrice").val(),
					packageIntegral:$("#productPackageIntegral").val(),
					packageType:$("input[name='optionsRadios']:checked").val(),
					packageStartTime:$("#starttime").val(),
					packageEndTime:$("#endtime").val(),
					packageState:0,
					packageDesc:$("#productPackageDesc").val()
				};
			AjaxPostUtil.request({url:path+"/post/WechatProductPackageChooseController/updateOne",params:param,type:"json",callback:function(json){
				if(json.returnCode == 0){
					qiao.bs.msg({msg:"修改成功",type:'success'});
					setTimeout(location.href = 'choosePackageList.html',1000);// 表示一秒后;
				}else{
					qiao.bs.msg({msg:"修改失败",type:"danger"});
				}
			}});
		}
		return false;
	});
	
	$('#jupdate').on('click', function() {
		$(this).parent().parent().find("input").val( parseInt($(this).parent().parent().find("input").val(), 10) + 1);
	});
	$('#jiupdate').on('click', function() {
		if($(this).parent().parent().find("input").val()<=0){
			$(this).parent().parent().find("input").val("0");
		}else{
			$(this).parent().parent().find("input").val( parseInt($(this).parent().parent().find("input").val(), 10) - 1);
		}
	});
	
	$("#imgFiles").uploadPreview({ Img: "logo"});
	$("#imgFiles").fileupload({
		url : path + "/post/UploadController/insertImgFile?imgType=3",
		disableImageResize: /Android(?!.*Chrome)|Opera/.test(window.navigator.userAgent),
        autoUpload: true,//是否自动上传
        maxFileSize: 999000,
        imageMaxWidth: 1000,
        imageMaxHeight: 800,
        imageQuality:0.6,
        dataType:'json',
        acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i
	}).on('fileuploadadd', function (e, data) {
		//进行文件添加的操作
		var uploadFile = data.files[0];
		fileName = uploadFile.name;
		var reg = ".(jpg|png|jpeg|svg)$";
		var re = new RegExp(reg);
		if (fileName.length > 50) {
			qiao.bs.msg({msg:"文件名长度不能大于50",type:'danger'});
			$.staticPic('logo','logoDiv');
			return false;
		} else if (!re.test(fileName.toLowerCase())) {
			qiao.bs.msg({msg:"文件必须为jpg/jpeg/png格式",type:'danger'});
			$.staticPic('logo','logoDiv');
			return false;
		} else if (uploadFile.size > 2000000) { // 2mb
			qiao.bs.msg({msg:"文件不能大于2M",type:'danger'})
			$.staticPic('logo','logoDiv');
			return false;
		}
	}).on('fileuploaddone', function (e, data) {
		//进行文件上传的操作
		if(isNull(data.result.bean)){
			$.staticPic('logo','logoDiv');
			qiao.bs.msg({msg:data.result.returnMessage,type:'danger'});
		}else{
			qiao.bs.msg({msg:"上传成功",type:'success'});
			imgId = data.result.bean.id;
		}
	}).on('fileuploadfail', function (e, data) {
		//上传失败的
		$.staticPic('logo','logoDiv');
		qiao.bs.msg({msg:"上传失败，图片格式不正确",type:'danger'});
	});
	
	
	$("[name='optionsRadios']").click(function(){
		if($("[name = 'optionsRadios']:checked").val() == 1){
			$("[name = 'settime']").hide();
		}else{
			$("[name = 'settime']").show();
		}
		
	});
}