var imgId = null;
var productTypeId = null;
var startTime = "";
var endTime = "";

$(function(e){
	receiveData();
	dataInit();
});

function dataInit(){
	 
	if (isNull(productTypeId)){
		productTypeId = $.req("productTypeId");
	}
	eventInit();
	getAllBandTag();
}

function eventInit(){
	
	$('#member_week').multiselect({
		 buttonClass: 'btn-over',
		 enableClickableOptGroups: true,
		 inheritClass: true,
		 enableCollapsibleOptGroups: true,
		 includeSelectAllOption: false,
		 enableFiltering: true,
		 buttonWidth:'49%',
		 maxHeight: '200',
		 nonSelectedText: '请选择',
		 includeSelectAllOption: true,//全选  
		 selectAllText: '全选',//全选的checkbox名称  
		 selectAllJustVisible: true,//选择所有的。true为全选可见的  
	});
	$('#member_month').multiselect({
		 buttonClass: 'btn-over',
		 enableClickableOptGroups: true,
		 inheritClass: true,
		 enableCollapsibleOptGroups: true,
		 includeSelectAllOption: false,
		 enableFiltering: true,
		 buttonWidth:'49%',
		 maxHeight: '300',
		 nonSelectedText: '请选择',
		 includeSelectAllOption: true,//全选  
		 selectAllText: '全选',//全选的checkbox名称  
		 selectAllJustVisible: true,//选择所有的。true为全选可见的  
	});
	
	$("#productNature").on('change', function(){
		if($("#productNature").val() == 0){
			$("#productDiv").removeClass("show");
			$("#productDiv").addClass("hide");
		}
		if($("#productNature").val() == 1){
			$("#productDiv").removeClass("hide");
			$("#productDiv").addClass("show");
		}
	});
	
	// 点击开始结束时间按钮
	$("input[name=optionsRadios]").on("click", function(){
		if($(this).val() == "Y"){
			$("#selectTime").show();
		}else{
			$("#selectTime").hide();
			startTime = endTime = "";
		}
	});
	
	$('#addProductForm').bootstrapValidator({
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			//产品名称
			menuName: {
				validators: {
					notEmpty: {
						message: '产品名称不能为空！'
					},
					stringLength: {
						 max: 20,
						message: '产品名称长度必须小于20字符！'
					}
				}
			},
			//产品描述
			productDesc:{
				validators: {
					stringLength: {
						 max: 200,
						message: '产品描述信息长度必须小于200字符！'
					}
				}		
			},
			//产品价格
			menuPrice: {
				validators: {
					notEmpty: {
						message: '产品价格不能为空！'
					},
					regexp: {
						regexp:/^(([1-9]+)|([0-9]+\.[0-9]{1,2}))$/,
			              message: '产品价格只能是两位小数！'
			        },
					stringLength: {
						 max: 6,
						message: '产品价格长度必须小于6位！'
					}
				}
			},productInteger : {
				validators: {
					notEmpty: {
						message: '产品积分不能为空！'
					},
					regexp: {
						regexp:/^[0-9]+$/,
			             message: '产品只能是数字！'
			        }
				}
			}
		}
	}).on('success.form.bv', function(e) {
		var flag = $("input[name=optionsRadios]:checked").val();
		if(flag == "Y"){
			startTime = $("#startTime").val();
			endTime = $("#endTime").val();
			if(isNull(startTime) || isNull(endTime)){
				qiao.bs.msg({msg:"开始结束时间不能为空",type:'danger'});
				return false;
			}
		} 
		if(imgId==null){
			$("#saveMenu").removeAttr("disabled");
			qiao.bs.msg({msg:"请选择商品LOGO",type:'danger'});
		}else{
			//设置参数
			var params  = {
				productName : $('#productName').val(),
				productLogo : imgId,
				productIntegral :  $('#productIntegral').val(),
				productDesc : $('#productDesc').val(),
				productPrice:$('#productPrice').val(),
				productType : productTypeId,
				productBrandTag : $('#bandTagMenu').val(),
				startTime : startTime,
				endTime : endTime,
				flag : flag,
				productNature : $("#productNature").val(),
				productTime : $("#productTime").val(),
				showStartTime1 : $("#showStartTime1").val(),
				showEndTime1 : $("#showEndTime1").val(),
				showStartTime2 : $("#showStartTime2").val(),
				showEndTime2 : $("#showEndTime2").val(),
				showStartTime3 : $("#showStartTime3").val(),
				showEndTime3 : $("#showEndTime3").val(),
				memberWeek : returnSelStr($("#member_week").val()),
				memberMonth : returnSelStr($("#member_month").val())
			};
			//进行商品的添加
			AjaxPostUtil.request({url:path+"/post/WechatProductController/addProduct",params:params,type:'json',callback:function(json){
				if(json.returnCode == 0){
					location.href = "productTypeMenu.html?productTypeId="+productTypeId;
				}else{
					$("#saveMenu").removeAttr("disabled");
					qiao.bs.msg({msg:json.returnMessage,type:'danger'});
				}
			}
			});
		}
		return false; //一点要加!!!!
	});
	//取消按钮相应事件
	$('body').on('click', '#cancleBean', function(e){
		location.href = "productTypeMenu.html?productTypeId="+productTypeId;
	});

	//上传图片
	$("#imgFiles").uploadPreview({ Img: "logo"});
	$("#imgFiles").fileupload({
		url : path + "/post/UploadController/insertImgFile?imgType=1",
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
}
/*//获取所有的商品类别
function getAllType(){
	//获取所有已经上线的商品的类别
	AjaxPostUtil.request({url:path+"/post/WechatProductTypeController/getTypeOnline",params:{},type:'json',callback:function(json){
		if(json.returnCode==0){
			//填充数据
			var source = $("#typeListBean").html();
			var template = Handlebars.compile(source);
			$("#typeMenu").html(template(json));
		}else{
			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
		}
		//获取商品品牌
		getAllBandTag();
	}});
}*/

//获取所有的品牌
function getAllBandTag(){
	//获取所有已经上线的品牌
	AjaxPostUtil.request({url:path+"/post/WechatProductBrandTagController/getBrandTagOnline",params:{},type:'json',callback:function(json){
		if(json.returnCode==0){
			//填充数据
			var source = $("#brandTagListBean").html();
			var template = Handlebars.compile(source);
			$("#bandTagMenu").html(template(json));
		}else{
			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
		}
	}});
}

//multiselect转换字符串
function returnSelStr(str){
	if(isNull(str)){
		return null;
	}else{
		return str.toString(",");
	}
}



