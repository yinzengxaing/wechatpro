var imgId = null;

$(function(e){
	dataInit();
});

function dataInit(){
	
	eventInit();
}

function eventInit(){
//	对表单进行验证
	$('#addProductTypeForm').bootstrapValidator({
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			typeName: {
				validators: {
					notEmpty: {
						message: '商品类型名称不能为空！'
					},
					stringLength: {
						 max: 20,
						message: '商品类型名称必须小于20字符！'
					}
				}
			},
			typeDesc:{
				validators: {
					stringLength: {
						 max: 200,
						message: '商品类型描述名称必须小于200字符！'
					}
				}
			}
		}
	}).on('success.form.bv', function(e) {
		// 进行验证是否上传图片
		if(imgId == null){
			$("#saveBean").removeAttr("disabled");
			qiao.bs.msg({msg:"请选择商品LOGO",type:'danger'});
		}
		//		验证成功提交新添加的产品类别
		var params = {
				typeName:$("#typeName").val(),
				typeDesc:$("#typeDesc").val(),
				typeLogoId:imgId,
		};
		AjaxPostUtil.request({url:path+"/post/WechatProductTypeController/addProductType",params:params,type:'json',callback:function(json){
			if(json.returnCode==0){
				location.href = 'productTypeList.html';
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
		return false;
	});
//	取消点击事件
	$('body').on('click', '#cancleBean', function(e){
		location.href = "productTypeList.html";
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
