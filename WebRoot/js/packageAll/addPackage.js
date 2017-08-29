
var productTypePriceLists;// 表示选中商品的总价格
var productTypeIdList; // 表示选中商品的id组成的字符串
var productIdList;
var imgId = null;
$(function(e){
	dataInit();
	eventInit();
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
			qiao.bs.msg({msg:"显示产品信息失败",type:"danger"});
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
			//起始时间
			starttime: {
				validators: {
					notEmpty: {
						message: '起始时间不能为空！',
					}
				}
			},
			//结束时间
			endtime: {
				validators: {
					notEmpty: {
						message: '结束时间不能为空！',
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
		}else{ // 设置参数
			var param={
					packageName:$("#productPackageName").val(),
					packageLogo:imgId,
					packagePrice:productTypePriceLists,
					productId:productIdList,
					packageIntegral:$("#productPackageIntegral").val(),
					packageType:$("input[name='optionsRadios']:checked").val(),
					packageStartTime:$("#starttime").val(),
					packageEndTime:$("#endtime").val(),
					packageState:0,
					packageDesc:$("#productPackageDesc").val()
				};
				
				AjaxPostUtil.request({url:path+"/post/wechatProductPackageController/insertPackage",params:param,type:"json",callback:function(json){
					if(json.returnCode == 0){
						qiao.bs.msg({msg:"添加成功",type:'success'});
						location.href = 'packageList.html';
					}else{
						qiao.bs.msg({msg:"商品添加失败",type:"danger"});
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
		if($("[name = 'optionsRadios']:checked").val() == 1){
			$("[name = 'settime']").hide();
		}else{
			$("[name = 'settime']").show();
		}
	});
	
	$("[fromOff = '1']").click(function(){
		$('#addPackageForm').bootstrapValidator('disableSubmitButtons', false); 
	});
}