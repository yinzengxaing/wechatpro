$(function(e){
	dataInit();
});

function dataInit(){
	eventInit();
}

function eventInit(){
//	对表单进行验证
	$('#addwechatKeyForm').bootstrapValidator({
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			wechatKey: {
				validators: {
					notEmpty: {
						message: '关键字不能为空!'
					}
				}
			},
			context:{
				validators: {
					notEmpty: {
						message: '回复内容不能为空!'
					}
				}				
			}
		}
	}).on('success.form.bv', function(e) {
		var params = {
				wechatKey:$("#wechatKey").val(),
				context:$("#context").val(),
				wechatKeyType:$("#wechatKeyType").val(),
				wetherComplet:$("#wetherComplet").val(),
		};
		AjaxPostUtil.request({url:path+"/post/WechatKeysController/addWechatKey",params:params,type:'json',callback:function(json){
			if(json.returnCode==0){
				qiao.bs.msg({msg:"添加成功",type:'success'});
				setTimeout(function(e){
					location.href = 'wechatKey.html';
				},500);
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
		return false;
	});
//	取消点击事件
	$('body').on('click', '#cancleBean', function(e){
		location.href = "wechatKey.html";
	});
}
