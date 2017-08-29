$(function(e){
	dataInit();
});

function dataInit(){
	eventInit();
}

function eventInit(){
//	对表单进行验证
	$('#addProductTypeForm').bootstrapValidator({
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			proPacTypeName: {
				validators: {
					notEmpty: {
						message: '分类名称不能为空！'
					},
					stringLength: {
						 max: 50,
						message: '分类名称长度必须小于50字符！'
					}
				}
			},
			proPacTypeDesc:{
				validators: {
					stringLength: {
						 max: 500,
						message: '分类描述必须小于500字符！'
					}
				}				
			}
		}
	}).on('success.form.bv', function(e) {
//		验证成功提交新添加的产品类别
		var params = {
				proPacTypeName:$("#proPacTypeName").val(),
				proPacTypeDesc:$("#proPacTypeDesc").val(),
		};
		AjaxPostUtil.request({url:path+"/post/WechatProPacTypeController/addPacType",params:params,type:'json',callback:function(json){
			if(json.returnCode==0){
				location.href = 'proPacTypeList.html';
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
		return false;
	});
//	取消点击事件
	$('body').on('click', '#cancleBean', function(e){
		location.href = "proPacTypeList.html";
	});
	
}
