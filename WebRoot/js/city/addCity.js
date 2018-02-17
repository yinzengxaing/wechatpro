$(function(e){
	dataInit();
});

function dataInit(){
	eventInit();
	//查询城市首字母
	AjaxPostUtil.request({url:path+"/post/WechatCityController/queryCityOneBySelect",params:{},type:'json',callback:function(json){
		if(json.returnCode==0){
			//填充数据
			var source = $("#fatherMobel").html();
			var template = Handlebars.compile(source);
			$("#cityLetter").html(template(json));
		}else{
			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
		}
	}});
	
	//对表单进行验证
	$('#addCityForm').bootstrapValidator({
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			cityName: {
				validators: {
					notEmpty: {
						message: '城市名称不能为空！'
					},
					stringLength: {
						 max: 50,
						message: '城市名称长度必须小于50字符！'
					}
				}
			}
		}
	}).on('success.form.bv', function(e) {
		//验证成功提交新添加的城市信息
		if($("#cityType").val()==1){//字母
			var params = {
					cityName:$("#cityName").val(),
					cityType:$("#cityType").val(),
			};
		}else{//城市
			var params = {
					cityName:$("#cityName").val(),
					cityType:$("#cityType").val(),
					cityLevel:$("#cityLevel").val(),
					cityLetter:$("#cityLetter").find("option:selected").text(),
					parentId : $("#cityLetter").val()
			};
		}
		AjaxPostUtil.request({url:path+"/post/WechatCityController/addCity",params:params,type:'json',callback:function(json){
			if(json.returnCode==0){
				location.href = 'cityList.html';
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
		return false;
	});
}

function eventInit(){
	$('body').on('change', "#cityType", function(e){
		if($(this).val()==1){
			$("#cityDiv").hide();
		}
		if($(this).val()==2){
			$("#cityDiv").show();
		}
	});
	//取消点击事件
	$('body').on('click', '#qvxiao', function(e){
		location.href = "cityList.html";
	});
	
}
