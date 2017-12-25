$(function(e){
	dataInit();
	eventInit();
});

function dataInit(){
	AjaxPostUtil.request({url:path+"/post/wechatAdminLoginController/selectSession",params:{},type:'json',callback:function(jsonSession){		
		if(jsonSession.returnCode==0){
			if(!isNull(jsonSession.bean)){
				location.href = 'pcsuccess.html';
			}
		}else{
			
		}
	}});
}

function eventInit(){
	$('#loginForm').bootstrapValidator({
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			username: {
				message: 'The username is not valid',
				validators: {
					notEmpty: {
						message: '账户不能为空'
					},
					stringLength: {
						min: 11,
						max: 11,
						message: '手机号长度为11位'
					},
					regexp: {
						regexp: /^[a-zA-Z0-9_\.]+$/,
						message: '只接受数字和字母 '
					}
				}
			},
			password:{
				message: 'The password is not valid',
				validators: {
					notEmpty: {
						message: '密码不能为空'
					}
				}
			},
		}
	}).on('success.form.bv', function(e) {
		var params = {
				adminNo:$("#username").val(),
				passwordbefore:$("#password").val(),
		};
		AjaxPostUtil.request({url:path+"/post/wechatAdminLoginController/insertLogin",params:params,type:'json',callback:function(json){
			if(json.returnCode==0){
				location.href = 'pcsuccess.html';
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
		return false;
	});
	
	$('body').on('click', '#register', function(e){
		location.href = "regist.html";
	});
}

