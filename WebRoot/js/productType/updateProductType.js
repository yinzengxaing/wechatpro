var id = null ;

$(function(e){
	receiveData();
	dataInit();
});

function dataInit(){
	if (isNull(id)){
		id = $.req("id");
	}
		var params = {
				id: id,
		};
		//从后台查询到该id的数据进行回显
		AjaxPostUtil.request({url:path+"/post/WechatProductTypeController/getProductTypeById",params:params,type:'json',callback:function(json){
			if(json.returnCode==0){
				$("#typeName").val(json.bean.typeName);
				$("#typeDesc").val(json.bean.typeDesc);
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
	eventInit();
}
function eventInit(){
	//对表单进行验证
	$('#addProductTypeForm').bootstrapValidator({
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			typeName: {
				validators: {
					notEmpty: {
						message: '商品类型名称不能为空！'
					},
					stringLength: {
						 max: 20,
						message: '商品类型信息长度必须小于20字符。'
					}
				}
			},
			typeDesc:{
				validators: {
					stringLength: {
						 max: 200,
						message: '商品类型信息长度必须小于200字符。'
					}
				}				
			}
		}
	}).on('success.form.bv', function(e) {
		//验证成功提交新添加的产品类别
		var params = {
				id:id,
				typeName:$("#typeName").val(),
				typeDesc:$("#typeDesc").val(),
		};
		AjaxPostUtil.request({url:path+"/post/WechatProductTypeController/updateProductType",params:params,type:'json',callback:function(json){
			if(json.returnCode==0){
				location.href = 'productTypeList.html';
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
		return false;
	});
	//取消点击事件
	$('body').on('click', '#cancleBean', function(e){
		location.href = "productTypeList.html";
	});
	
}
