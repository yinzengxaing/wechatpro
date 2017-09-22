var imgId = null;

var ue = null;

$(function(e){
	dataInit();
});

function dataInit(){
	eventInit();
}

function eventInit(){
	$('body').on('click','#saveScollor',function(e){
		if(isNull($('#scollorName').val())){
			qiao.bs.msg({msg:'广告名称不能为空',type:'danger'});
			return false;
		}else if(isNull(imgId)){
			qiao.bs.msg({msg:'请选择图片',type:'danger'});
			return false;
		}
		var params = {
				scollor_pic_name:$('#scollorName').val(),
				//scollor_pic_content:encodeURIComponent(UM.getEditor('editor').getContent()),
				//scollor_pic_introduce:$('#scollorIntroduce').val(),
				scollor_pic_path:imgId,
		};
		AjaxPostUtil.request({url:path+"/post/WechatScollorPicController/insertScoller",params:params,type:'json',callback:function(json){
			if(json.returnCode==0){
		        qiao.bs.msg({msg:"添加成功",type:'success'});
		        location.href = 'scollorList.html';
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
        acceptFileTypes: /(\.|\/)(jpe?g|png|svg)$/i
	}).on('fileuploadadd', function (e, data) {
		var uploadFile = data.files[0];
		fileName = uploadFile.name;
		var reg = ".(jpg|png|jpeg|svg)$";
		var re = new RegExp(reg);
		if (fileName.length > 50) {
			qiao.bs.msg({msg:"文件名长度不能大于50",type:'danger'});
			$.staticPic('picture','pictureDiv');
			return false;
		} else if (!re.test(fileName.toLowerCase())) {
			qiao.bs.msg({msg:"文件必须为jpg/jpeg/png格式",type:'danger'});
			$.staticPic('picture','pictureDiv');
			return false;
		} else if (uploadFile.size > 2000000) { // 2mb
			qiao.bs.msg({msg:"文件不能大于2M",type:'danger'})
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