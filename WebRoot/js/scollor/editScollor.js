var id = null;
var ue = null;
var imgId = null;

$(function(e){
	receiveData();
	dataInit();
});

function dataInit(){
	if(id==null){
		id = $.req("id");
	}
	var params = {
			id:id,
	};
	AjaxPostUtil.request({url:path+"/post/WechatScollorPicController/selectById",params:params,type:'json',callback:function(json){
		if(json.returnCode==0){
			$("#scollorName").val(json.bean.scollor_pic_name);
			$("#picture").attr("src",path + "/" + json.bean.optionPath);
			imgId = json.bean.scollor_pic_path;
			encodeURIComponent(UM.getEditor('editor').setContent(json.bean.scollor_pic_content));
			$("#scollorIntroduce").val(json.bean.scollor_pic_introduce);
			eventInit();
		}else{
			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
		}
	}});
}

function eventInit(){
	$('body').on('click','#saveScollor',function(e){
		if(isNull($('#scollorName').val())){
			qiao.bs.msg({msg:'通知名称不能为空',type:'danger'});
			return false;
		}else if(isNull(UM.getEditor('editor').getContent())){
			qiao.bs.msg({msg:'通知内容不能为空',type:'danger'});
			return false;
		}else if(isNull($('#scollorIntroduce').val())){
			qiao.bs.msg({msg:'介绍不能为空',type:'danger'});
			return false;
		}else if(isNull(imgId)){
			qiao.bs.msg({msg:'请选择图片',type:'danger'});
			return false;
		}
		var params = {
			id:id,
			scollor_pic_name:$('#scollorName').val(),
			scollor_pic_content:encodeURIComponent(UM.getEditor('editor').getContent()),
			scollor_pic_introduce:$('#scollorIntroduce').val(),
			scollor_pic_path:imgId,
		};
		AjaxPostUtil.request({url:path+"/post/WechatScollorPicController/updateScoller",params:params,type:'json',callback:function(json){
			if(json.returnCode==0){
		        qiao.bs.msg({msg:"修改成功",type:'success'});
		        location.href="scollorList.html";
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
	});
	
	$('body').on('click', '#returnBack', function(e){
		location.href="scollorList.html";
	});
	
	$("#imgFiles").uploadPreview({ Img: "picture"});
	$("#imgFiles").fileupload({
		url : path + "/post/UploadController/insertImgFile?imgType=2",
		disableImageResize: /Android(?!.*Chrome)|Opera/.test(window.navigator.userAgent),
        autoUpload: true,//是否自动上传
        maxFileSize: 999000,
        imageMaxWidth: 1000,
        imageMaxHeight: 800,
        imageQuality:0.6,
        dataType:'json',
        acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i
	}).on('fileuploadadd', function (e, data) {
		var uploadFile = data.files[0];
		fileName = uploadFile.name;
		var reg = ".(jpg|png|jpeg)$";
		var re = new RegExp(reg);
		if (fileName.length > 50) {
			Util.dialog.tips("文件名长度不能大于50");
			$.staticPic('picture','pictureDiv');
			return false;
		} else if (!re.test(fileName.toLowerCase())) {
			Util.dialog.tips("文件必须为jpg/jpeg/png格式");
			$.staticPic('picture','pictureDiv');
			return false;
		} else if (uploadFile.size > 2000000) { // 2mb
			Util.dialog.tips("文件不能大于2M");
			$.staticPic('picture','pictureDiv');
			return false;
		}
	}).on('fileuploaddone', function (e, data) {
		if(isNull(data.result.bean)){
			$.staticPic('picture','pictureDiv');
			qiao.bs.msg({msg:data.result.returnMessage,type:'danger'});
		}else{
			qiao.bs.msg({msg:"上传成功",type:'success'});
			imgId = data.result.bean.id;
		}
	}).on('fileuploadfail', function (e, data) {
		$.staticPic('picture','pictureDiv');
		qiao.bs.msg({msg:"上传失败，图片格式不正确",type:'danger'});
	});
	ue = UM.getEditor('editor');
}