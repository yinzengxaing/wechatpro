var id = null;
var imgId = null;
var packageId = null; // 套餐的id
var packageProductIdList = null;
var productTypePriceLists = 0;
var productIdList = ""; // 向后台传的产品id串
$(function(e){
	receiveData();
	dataInit();
	eventInit();
});

function dataInit(){
	// 查询所有商品信息,通过类别显示出来
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
		
		packageProductInfo();
	}});
}
function packageProductInfo(){
	 if(id==null){
		 id = $.req("id");
	 }
	 var param = {
			id:id,
	 };
	 AjaxPostUtil.request({url:path+"/post/wechatProductPackageController/selectPackageById",params:param,type:'json',callback:function(json){
		 if(json.returnCode == 0){
			 $("#productPackageName").val(json.bean.packageName);
		     $("#logo").attr("src",path + "/" + json.bean.optionPath);
		     $("#productPackageIntegral").val(json.bean.packageIntegral);
		     $("#productPackageDesc").val(json.bean.packageDesc);
		     $("#productPackagePrice").val(json.bean.packagePrice);
		     imgId=json.bean.Imageid; // 上传图片在数据中的id
		      // 套餐中存放商品id的字符串
		     packageProductIdList ="," +json.bean.productId +",";
		     
		     // 先将productIdList赋值为从数据库中查询出来的结果，然后点击响应事件后重新赋值
		     productIdList = json.bean.productId;
		     
		     
		     // 回显套餐开始结束时间
		     if(json.bean.packageType == 1){
		    	 $("#optionsRadios1").attr("checked", "true");
					$("[name='settime']").hide();
		     }else{
		         $("#optionsRadios2").attr("checked", "true");
		         $("#starttime").val(json.bean.packageStartTime);
		         $("#endtime").val(json.bean.packageEndTime);
		         $("[name='settime']").show();
		     }
		     
		     // 遍历需要勾选的套餐
		     var $packageProductTag = $("div[name=packageProductListToTag]");
		     for(var i = 0 ; i < $packageProductTag.length; i ++){
		    	 // 找到name值为productTag的div，然后遍历其中的商品
		    	var $productTag = $($packageProductTag[i]).find("div[name=productTag]");
		    	var productNameList = "";
		    	// 遍历name值为productTag的div中的商品
		    	for(var j = 0 ; j < $productTag.length; j ++){
		    		var $productValue = $($productTag[j]).find("input[name=productChecked]");
		    		// 如果含有这个商品则将他们的名称拼接称为一个字符串
		    		if(packageProductIdList.indexOf(","+$productValue.attr("productId")+",") > -1){
		    			productNameList += $productValue.val() + ",";
		    			$productValue.attr("checked", true);
		    		}
		    	}
		    	$($packageProductTag[i]).find("input[name=productNameList]").attr("value",productNameList);
		     }
		     
		}else{
			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
		}
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
		
		// 计算总价
		productTypePriceLists = 0;
		productIdList = "";
		$("input[name='productChecked']:checked").each(function(){
			productTypePriceLists += Math.ceil($(this).attr("productPrice") * 90)/100.0;
			productIdList += $(this).attr("productId") +",";
		});
		// 填充套餐总价格
		$("#productPackagePrice").attr("value",productTypePriceLists.toFixed(2));
	});
	
	$("#optionsRadios2").on("click",function(){
		$("#starttime").attr("value", "");
		$("#endtime").attr("value", "");
	});
	
	$("#cancel").on("click", function(){
		location.href = "packageList.html";
	});	
	
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
						message: '套餐积分不能为0'
					}
				}
			},
			//套餐价格
			productPackagePrice: {
				validators: {
					notEmpty: {
						message: '套餐价格不能为空！'
					},
					regexp: {
			              regexp: /^[0-9]+(\.[0-9]{1,2})?$/,
			              message: '套餐价格只能是数字'
			        }
				}
			},
			//商品选项
			productNameList: {
				validators: {
					notEmpty: {
						message: '商品选项不能为空！'
					},
				}
			},
			//起始时间
			starttime: {
				validators: {
					notEmpty: {
						message: '起始时间不能为空！'
					},
				}
			},
			//结束时间
			endtime: {
				validators: {
					notEmpty: {
						message: '结束时间不能为空！'
					}
				}
			},
			productPackageLogo:{
				validators:{
					notEmpty:{
						message:"套餐Logo不能为空！"
					}
				}
			}
			
		}
	}).on('success.form.bv', function(e) {
		if(imgId==null){
			qiao.bs.msg({msg:"请选择商品LOGO",type:'danger'});
		}else{ // 设置参数
			var param={
					id:id,
					packageName:$("#productPackageName").val(),
					packageLogo:imgId,
					packagePrice:$("#productPackagePrice").val(),
					productId:productIdList,
					packageIntegral:$("#productPackageIntegral").val(),
					packageType:$("input[name='optionsRadios']:checked").val(),
					packageStartTime:$("#starttime").val(),
					packageEndTime:$("#endtime").val(),
					packageState:0,
					packageDesc: $("#productPackageDesc").val()
				};

			AjaxPostUtil.request({url:path+"/post/wechatProductPackageController/updatePackageInfo",params:param,type:"json",callback:function(json){
				if(json.returnCode == 0){
					qiao.bs.msg({msg:"套餐更新成功",type:'success'});
					location.href = 'packageList.html';
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
	
	// 如果为永久有效则隐藏时间选择框，否则就显示
	$("[name = 'optionsRadios']").click(function(){
		if($("[name='optionsRadios']:checked").val() == 1){
			$("[name='settime']").hide();
		}else{
			$("[name='settime']").show();
		}
	});
	
	$("[fromOff = '1']").click(function(){
		$('#addPackageForm').bootstrapValidator('disableSubmitButtons', false); 
	});
}