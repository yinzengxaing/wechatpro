var tree = null;
var nodes = null;
var v = null;

var imgId = null;

$(function(e){
	dataInit();
});

function dataInit(){
	//显示所有菜单
	AjaxPostUtil.request({url:path+"/post/WechatAdminMenuController/selectAll",params:{},type:'json',callback:function(json){
		if(json.returnCode==0){
			var setting = {
			    check: {
			        enable: true
			    },
			    data:{
			    	 key:{
			    		 title:"title"
			    	 },
			    	 simpleData: {
			             enable: true
			         }
			     }
			};
			$.fn.zTree.init($("#treeDemo"), setting, json.rows);
			eventInit();
		}else{
			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
		}
	}});
}

function eventInit(){
	$('body').on('click','#saveRole',function(e){
		tree = $.fn.zTree.getZTreeObj("treeDemo");
	    nodes = tree.getCheckedNodes(true);
        v = "";
		for(var i=0;i < nodes.length;i++){
			if(i==nodes.length-1)
				v+=nodes[i].id;
			else
				v+=nodes[i].id+",";
		}
		if(isNull($('#menuName').val())){
			qiao.bs.msg({msg:'角色名称不能为空',type:'danger'});
			return false;
		}else if(isNull($('#rolePrompt').val())){
			qiao.bs.msg({msg:'角色描述不能为空',type:'danger'});
			return false;
		}else if(isNull(v)){
			qiao.bs.msg({msg:'请选择权限',type:'danger'});
			return false;
		}else if(isNull(imgId)){
			qiao.bs.msg({msg:'请选择角色LOGO',type:'danger'});
			return false;
		}
		var params = {
				wechatRoleName:$('#menuName').val(),
			    wechatRoleDesc:$('#rolePrompt').val(),
				wechatRoleHeadPortrait:imgId,
				shuzu:v,
		};
		AjaxPostUtil.request({url:path+"/post/WechatAdminRoleController/addRole",params:params,type:'json',callback:function(json){
			if(json.returnCode==0){
		        qiao.bs.msg({msg:"添加成功",type:'success'});
		        location.href = 'roleList.html';
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});		
	});
	
	$('body').on('click', '#returnBack', function(e){
		location.href="roleList.html";
	});
	
	$("#imgFiles").uploadPreview({ Img: "logo"});
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
		if(isNull(data.result.bean)){
			$.staticPic('logo','logoDiv');
			qiao.bs.msg({msg:data.result.returnMessage,type:'danger'});
		}else{
			qiao.bs.msg({msg:"上传成功",type:'success'});
			imgId = data.result.bean.id;
		}
	}).on('fileuploadfail', function (e, data) {
		$.staticPic('logo','logoDiv');
		qiao.bs.msg({msg:"上传失败，图片格式不正确",type:'danger'});
	});
	
}


