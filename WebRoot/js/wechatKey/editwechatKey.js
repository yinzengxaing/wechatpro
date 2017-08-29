var id=null;

$(function(e){
	receiveData();
	dataInit();
});

function dataInit(){
	id = $.req("id");
	var params = {
			id:id  
	};
	AjaxPostUtil.request({url:path+"/post/WechatKeysController/selectKey",params:params,type:'json',callback:function(json){
		if(json.returnCode==0){
				$('#wechatKey').val(json.bean.wechatKey);
				$('#context').val(json.bean.context);
				$('#wechatType').val(json.bean.wechatType);
				$('#wetherComplet').val(json.bean.wetherComplet);
			} else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}	
		}
	});
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
//		验证成功提交新添加的产品类别
		var params = {
				id:id,
				wechatKey:$("#wechatKey").val(),
				context:$("#context").val(),
				wechatKeyType:$("#wechatKeyType").val(),
				wetherComplet:$("#wetherComplet").val(),
				wetherClose:""
		};
		AjaxPostUtil.request({url:path+"/post/WechatKeysController/updateWechatKey",params:params,type:'json',callback:function(json){
			if(json.returnCode==0){
				location.href = 'wechatKey.html';
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
