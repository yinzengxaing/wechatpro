var id = null;

$(function(e){
	dataInit();
});

function dataInit(){
	receiveData();
	eventInit();
}

function eventInit(){
	$('body').on('click', '#returnBack', function(e){
		location.href = "forgetPwd.html";
	});
	$('body').on('click', '#reset', function(e){
		if(id==null){
			id = $.req("id");
		}
		if(isNull($("#passwordfirst").val()) || isNull($("#passwordSecond").val())){
			qiao.bs.msg({msg:"密码不能为空!",type:'danger'});
			return false;
		}else if($("#passwordfirst").val() != $("#passwordSecond").val()){
			qiao.bs.msg({msg:"两次输入密码不相同，请重新输入！",type:'danger'});
			return false;
		}else{else if($("#passwordfirst").val().length>5 && "#passwordfirst").val().length<19){
			qiao.bs.msg({msg:"密码长度必须在6-18位，请重新输入！",type:'danger'});
			return false;
		}
		var params = {
				id:id,
	    		newPassword1:$("#passwordfirst").val(),
	    		newPassword2:$("#passwordSecond").val(),	
		};
		AjaxPostUtil.request({url:path+"/post/wechatAdminLoginController/updatePassword",params:params,type:'json',callback:function(json){
			if(json.returnCode==0){
				qiao.bs.msg({msg:'修改密码成功',type:'success'});
			}else{
				qiao.bs.msg({msg:json.renturnMessage,type:'danger'});
			}
		}});
		}
		
	});
}