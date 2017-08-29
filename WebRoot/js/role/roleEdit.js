var tree = null;
var nodes = null;
var v = null;
var id = null;

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
	AjaxPostUtil.request({url:path+"/post/WechatAdminMenuController/selectAll",params:{},type:'json',callback:function(json){
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
		AjaxPostUtil.request({url:path+"/post/WechatAdminRoleController/selectById",params:params,type:'json',callback:function(json){
			if(json.returnCode==0){
				$("#roleName").val(json.bean.wechatRoleName);
				$("#logo").attr("src",path + "/" + json.bean.optionPath);
				$("#editPrompt").val(json.bean.wechatRoleDesc);
				imgId = json.bean.wechatRoleHeadPortrait;
				var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo")  
			    var zTree = zTreeObj.getCheckedNodes(false);  
			    var pid= json.bean.userZ; /**此处数据前后必须拼接;*/  
			    for (var i = 0; i < zTree.length; i++) {
			        if (pid.indexOf(";" + zTree[i].id + ";") != -1) {  
	                    zTreeObj.checkNode(zTree[i], true);                   
			        }  
			    }  
			    $("#wechatRoleOpinion").val(json.bean.wechatRoleOpinion);
				eventInit();
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
	    }});
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
		if(isNull($('#roleName').val())){
			qiao.bs.msg({msg:'角色名称不能为空',type:'danger'});
			return false;
		}else if(isNull($('#editPrompt').val())){
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
			id:id,
			wechatRoleName:$('#roleName').val(),
		    wechatRoleDesc:$('#editPrompt').val(),
			wechatRoleHeadPortrait:imgId,
		    wechatRoleOpinion:$('#wechatRoleOpinion').val(),
			shuzu:v,
		}
		AjaxPostUtil.request({url:path+"/post/WechatAdminRoleController/updateById",params:params,type:'json',callback:function(json){
			if(json.returnCode==0){
		        qiao.bs.msg({msg:"修改成功",type:'success'});
		        location.href="roleList.html";
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
        acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i
	}).on('fileuploadadd', function (e, data) {
		var uploadFile = data.files[0];
		fileName = uploadFile.name;
		var reg = ".(jpg|png|jpeg)$";
		var re = new RegExp(reg);
		if (fileName.length > 50) {
			Util.dialog.tips("文件名长度不能大于50");
			$.staticPic('logo','logoDiv');
			return false;
		} else if (!re.test(fileName.toLowerCase())) {
			Util.dialog.tips("文件必须为jpg/jpeg/png格式");
			$.staticPic('logo','logoDiv');
			return false;
		} else if (uploadFile.size > 2000000) { // 2mb
			Util.dialog.tips("文件不能大于2M");
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

