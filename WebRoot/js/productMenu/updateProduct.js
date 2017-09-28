var imgId = null;
var id = null;
var productTypeId = "";
var startTime = "";
var endTime = "";
$(function(e){
	receiveData();
	dataInit();
});

function dataInit(){
	// 接收productTypeId 的值
	productTypeId= $.req("productTypeId");
	eventInit();
	getAllType();
}

function eventInit(){
	
	// 点击开始结束时间按钮
	$("input[name=optionsRadios]").on("click", function(){
		if($(this).val() == "Y"){
			$("#selectTime").show();
		}else{
			$("#selectTime").hide();
			startTime ="";
			endTime = "";
		}
	});
	
	//表单的验证
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
				id : id,
				productName : $('#productName').val(),
				productLogo : imgId,
				productIntegral :  $('#productIntegral').val(),
				productDesc : $('#productDesc').val(),
				productPrice:$('#productPrice').val(),
				productType : $("#typeMenu").val(),
				productBrandTag : $('#bandTagMenu').val(),
				startTime : startTime,
				endTime : endTime,
				flag : flag,
				}
			//进行商品的修改
			AjaxPostUtil.request({url:path+"/post/WechatProductController/updateProduct",params:params,type:'json',callback:function(json){
				if(json.returnCode == 0){
					location.href = "productTypeMenu.html?productTypeId=" + productTypeId;
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
		location.href = "productTypeMenu.html?productTypeId=" + productTypeId;
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
//获取所有的商品类别
function getAllType(){
	//获取所有已经上线的商品的类别
	AjaxPostUtil.request({url:path+"/post/WechatProductTypeController/getTypeOnline",params:{},type:'json',callback:function(json){
		if(json.returnCode==0){
			//填充数据
			var source = $("#typeListBean").html();
			var template = Handlebars.compile(source);
			$("#typeMenu").html(template(json));
		}else{
			qiao.bs.msg({msg:"现在还没有已经上线的商品类别",type:'danger'});
		}
		//获取商品品牌
		getAllBandTag();
	}});
}

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
			qiao.bs.msg({msg:"现在没有已经上线的商品品牌",type:'danger'});
		}
		//进行数据的回显
		setData();
	}});

}

//进行数据的回显
function setData(){
	if (isNull(id)){
		id = $.req("id");
	}
	var params = {
			id: id
	};
	AjaxPostUtil.request({url:path+"/post/WechatProductController/getPrductById",params:params,type:'json',callback:function(json){
			$('#productName').val(json.bean.productName);
			$("#logo").attr('src',path+"/"+json.bean.productLogo);
			imgId = json.bean.imgId;
			$('#productIntegral').val(json.bean.productIntegral);
			$('#productDesc').val(json.bean.productDesc);
			$('#productPrice').val(json.bean.productPrice);
			$("#typeMenu").val(json.bean.productType);
			$("#bandTagMenu").val(json.bean.productBrandTag);
			$("#startTime").val(json.bean.startTime);
			$("#endTime").val(json.bean.endTime);
			if(json.bean.startTime != "00:00"){
				$("#selectTime").show();
				$("#optionsRadios1").attr("checked", "true");
			}else{
				$("#selectTime").hide();
				$("#optionsRadios2").attr("checked", "true");
			}
		}
	});
}