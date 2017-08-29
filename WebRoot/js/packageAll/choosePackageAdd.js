
var productTypePriceLists;// 表示选中商品的总价格
var productTypeIdList; // 表示选中商品的id组成的字符串
var productIdList;
var imgId = null;
$(function(e){
	dataInit();
});

function dataInit(){
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
			              regexp: /^(([1-9][0-9]*)|0)(\.[0-9]{1,2})?$/,
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
			},
			imgFiles:{
				validators:{
					notEmpty:{
						message:"套餐Logo不能为空！",
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
			for(var i = 0; i <$productNum.length; i ++){
				
				var typeId =$($productNum[i]).attr("typeId");
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
					if(typeId != '')
						groupIds += typeId +",";
					else
						groupIds += " ,";
					
					groupType += optionsRadiosC +",";
					groupProductNum += productNum +",";
					groupProducts += productId +"-";
				}
			}
			var param={
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
			AjaxPostUtil.request({url:path+"/post/WechatProductPackageChooseController/addPackage",params:param,type:"json",callback:function(json){
				if(json.returnCode == 0){
					qiao.bs.msg({msg:"添加成功",type:'success'});
					setTimeout(location.href = 'choosePackageList.html',1000);// 表示一秒后;
				}else{
					qiao.bs.msg({msg:json.returnMessage,type:'danger'});
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
	
	
	$("[name = 'optionsRadios']").click(function(){
		if($("[name = 'optionsRadios']:checked").val() == 1){
			$("[name = 'settime']").hide();
		}else{
			$("[name = 'settime']").show();
		}
		
	})
	
	$("[fromOff = '1']").click(function(){
		$('#addPackageForm').bootstrapValidator('disableSubmitButtons', false); 
	})
	
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
}
