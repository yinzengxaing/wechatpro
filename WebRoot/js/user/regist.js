
var wait = 120;

$(function(e){
	dataInit();
});

function dataInit(){
	eventInit();
}

function eventInit(){
	
	$('body').on('click', '#sendPhoneMessage', function(e){
		var params = {
				phoneNum:$("#username").val(),	
		};
		if(checkMobile($("#username").val()) == false){
			qiao.bs.msg({msg:"手机号码不正确!",type:'danger'});
		}else{
			AjaxPostUtil.request({url:path+"/post/WechatPhoneMessageLogController/insertPhoneMessage",params:params,type:'json',callback:function(json){
				if(json.returnCode==0){
					qiao.bs.msg({msg:'发送成功',type:'success'});
					time($("#sendPhoneMessage"));
					$("#phoneMessageNet").val(json.bean.phoneMessage);
				}else{
					qiao.bs.msg({msg:json.returnMessage,type:'danger'});
				}
			}});
		}
	});
	
	$('body').on('click', '#returnBack', function(e){
		location.href = "login.html";
	});
	
	
	$('#registerForm').bootstrapValidator({
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			username: {
				validators: {
					notEmpty: {
						message: '手机号不能为空'
					},
					stringLength: {
						min: 11,
						max: 11,
						message: '手机号长度为11位'
					},
					regexp:{ 
						regexp:/^[a-zA-Z0-9_\.]+$/,
						message: '手机号不合格'
					}
				}
			},
			passwordfirst: {
				validators: {
					notEmpty: {
						message: '密码不能为空'
					},
					stringLength: {
						min: 6,
						max: 18,
						message: '密码长度 6-18'
					},
					regexp: {
						regexp: /^[a-zA-Z0-9_\.]+$/,
						message: '只接受数字和字母 '
					}
				}
			},
			passwordSecond: {
				validators: {
					notEmpty: {
						message: '密码不能为空'
					},
					stringLength: {
						min: 6,
						max: 18,
						message: '密码长度 6-18'
					},
					regexp: {
						regexp: /^[a-zA-Z0-9_\.]+$/,
						message: '只接受数字和字母 '
					}
				}
			},
			phoneMessage: {
				validators:{
					notEmpty: {
						message: '验证码不能为空'
					}
				}
			}
		}
	}).on('success.form.bv', function(e) {
		if($("#passwordfirst").val()!=$("#passwordSecond").val()){
			qiao.bs.msg({msg:"两次密码不相同，请重新输入",type:'danger'});
			return false;
		}else{
			var params = {
					adminNo:$("#username").val(),
					password1:$("#passwordfirst").val(),
					password2:$("#passwordSecond").val(),
					sendPhoneMessage:$("#phoneMessage").val(),
					phoneMessage:$("#phoneMessageNet").val(),
			};
			AjaxPostUtil.request({url:path+"/post/wechatAdminLoginController/insertAdminLogin",params:params,type:'json',callback:function(json){
				if(json.returnCode==0){
					qiao.bs.msg({msg:'注册成功',type:'success'});
					location.href = 'login.html';
				}else{
					qiao.bs.msg({msg:json.returnMessage,type:'danger'});
				}
			}});
		}
		return false;
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
