var id = null;

var wait = 120;

var phoneNum = null;

$(function(e){
	dataInit();
});

function dataInit(){
	eventInit();
}

function eventInit(){
	
	$('body').on('click', '#returnBack', function(e){
		location.href = "login.html";
	});
	
	$('body').on('click', '#sendPhoneMessage', function(e){
		if(isNull($("#username").val())){
			qiao.bs.msg({msg:"手机号不能为空！",type:'danger'});
			return false;
		}else if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test($("#username").val()))){
			qiao.bs.msg({msg:"手机号不合格",type:'danger'});
			return false;
		}else{
			var params = {
					phoneNum:$("#username").val(),	
			};
			AjaxPostUtil.request({url:path+"/post/wechatAdminLoginController/selectAdminNo",params:params,type:'json',callback:function(json){
				if(json.returnCode==0){
					id=json.bean.id;
					AjaxPostUtil.request({url:path+"/post/WechatPhoneMessageLogController/insertPhoneMessage",params:params,type:'json',callback:function(json){
						if(json.returnCode==0){
							phoneNum = $("#username").val();
							qiao.bs.msg({msg:'发送成功',type:'success'});
							time($("#sendPhoneMessage"));
							$("#phoneMessageNet").val(json.bean.phoneMessage);//需要后台验证验证码是否正确
						}else{
							qiao.bs.msg({msg:json.returnMessage,type:'danger'});
						}
					}});
				}else{
					id=null;
					qiao.bs.msg({msg:json.returnMessage,type:'danger'});
				}
			}});
		}
	});
	
	$('body').on('click', '#nextRet', function(e){
		if(id==null){
			qiao.bs.msg({msg:"请先获取验证码",type:'danger'});
		}else{
			var params = {
					phoneNum:$("#username").val(),	
			};
			AjaxPostUtil.request({url:path+"/post/wechatAdminLoginController/selectAdminNo",params:params,type:'json',callback:function(json){
				if(json.returnCode==0){
					if(phoneNum == $("#username").val()){
						if($("#phoneMessageNet").val()==$("#phoneMessage").val()){
							location.href="resetPwd.html?id="+id;
						}else{
							qiao.bs.msg({msg:"验证码不正确,请重新输入...",type:'danger'});
						}
					}else{
						id=null;
						qiao.bs.msg({msg:"获取验证码手机号和当前手机号不一致...",type:'danger'});
					}
				}else{
					id=null;
					qiao.bs.msg({msg:json.returnMessage,type:'danger'});
				}
			}});
		}
	});
}

function time(o) {
	if (wait == 0) {
		o.attr("disabled",false);
		o.html("免费获取验证码");
		wait = 120;
		$("#phoneMessageNet").val("");
	} else {
		o.attr("disabled", true);
		o.html(wait + "秒后可重新发送");
		wait--;
		setTimeout(function() {
			time(o)
		}, 1000)
	}
}

