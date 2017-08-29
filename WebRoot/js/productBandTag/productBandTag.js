var id = null;

$(function(e){
	dataInit();
});
function dataInit(){
	setData();
	eventInit();
}

function eventInit(){
	// 对表单进行验证
	$('#brandTagForm').bootstrapValidator({
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			brandTagName: {
				validators: {
					notEmpty: {
						message: '品牌名称不能为空！'
					},
					stringLength: {
						max: 30,
						message: '品牌名称必须小于30字符！'
					}
				}
			},
			brandTagDesc:{
				validators: {
					stringLength: {
						 max: 200,
						message: '品牌信息长度必须小于200字符！'
					}
				}				
			}
		}
	}).on('success.form.bv', function(e) {
		  if (isNull(id)){//添加
			   var params = {
					brandTagName:$("#brandTagName").val(),
					brandTagDesc:$("#brandTagDesc").val()
				}
				AjaxPostUtil.request({url:path+"/post/WechatProductBrandTagController/addProductBrandTag",params:params,type:'json',callback:function(json){
					if(json.returnCode == 0){	
						setData();
						qiao.bs.msg({msg:json.returnMessage,type:'success'});
					}else{
						qiao.bs.msg({msg:json.returnMessage,type:'danger'});
					}
				}});  
	   }else{  //编辑
			 var params = {
					 id:id,
					 brandTagName:$("#brandTagName").val(),
					 brandTagDesc:$("#brandTagDesc").val(),
					}
				AjaxPostUtil.request({url:path+"/post/WechatProductBrandTagController/updateProductBrandTag",params:params,type:'json',callback:function(json){
					if(json.returnCode == 0){	
						setData();
						qiao.bs.msg({msg:json.returnMessage,type:'success'}); //显示后台传入的信息
					}else{
						qiao.bs.msg({msg:json.returnMessage,type:'danger'});
					}
				}});
	   }
			return false; //一定要加上!
	});

	//编辑按钮点击事件
	$('body').on('click', '#editBean', function(e){
		$('#saveBean').css({ "display": "inline" }); //使保存按钮可见
		$('#editBean').css({ "display": "none" }); //使编辑不可见
		$('#cancleBean').css({ "display": "inline" }); //使取消按钮可见
		$('#brandTagName').attr("disabled",false); 
		$('#brandTagDesc').attr("disabled",false); 
	});
	//取消按钮点击事件
	$('body').on('click', '#cancleBean', function(e){
		setData();
	});
}

function setData(){
	//获取后台的品牌数据
	AjaxPostUtil.request({url:path+"/post/WechatProductBrandTagController/getProductBrandTagList",type:'json',callback:function(json){
		if (json.rows[0] != null){
			$('#saveBean').css({ "display": "none" }); //使保存按钮不可见
			$('#cancleBean').css({ "display": "none" }); //使取消按钮不可见
			if (json.rows[0].brandTagState == 1) //提审状态
				$('#editBean').css({ "display": "none" }); //使编辑按钮不可见
			else 
				$('#editBean').css({ "display": "inline" }); //使编辑按钮不可见
			$('#brandTagName').attr("disabled",true);  //设置为不可编辑状态
			$('#brandTagDesc').attr("disabled",true);
			//设置数据
			id = json.rows[0].id;
			$('#brandTagName').val(json.rows[0].brandTagName);
			$('#brandTagDesc').val(json.rows[0].brandTagDesc);
			$('#brandTagState').val(json.rows[0].brandTagState);
		}else{
			$('#cancleBean').css({ "display": "none" }); //使取消按钮不可见
			$('#editBean').css({ "display": "none" }); //使编辑按钮不可见
			$('#saveBean').css({ "display": "inline" }); //使保存按钮可见
			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
		}
	}});
}


