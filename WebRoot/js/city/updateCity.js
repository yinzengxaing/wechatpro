var rowId = "";
var rowUpdateId = "";
$(function(e){
	receiveData();
	rowId = $.req("id");
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
			
			AjaxPostUtil.request({url:path+"/post/WechatCityController/queryCityById",params:{id:rowId},type:'json',callback:function(jsonMation){
				if(jsonMation.returnCode==0){
					//填充数据
					console.log(jsonMation);
					rowUpdateId = jsonMation.bean.id;
					$("#cityName").val(jsonMation.bean.cityName);
					
					if(jsonMation.bean.cityType == 1){
						$("#cityType").val("字母");
					}else if(jsonMation.bean.cityType == 2){
						$("#cityType").val("城市");
						$("#cityLevel").val(jsonMation.bean.cityLevel);
						if(jsonMation.bean.cityLevel == 1){
							$("#cityLevel").attr("地级市");
						}else{
							$("#cityLevel").attr("县级市(县)");
						}
						$("#cityLetter").val(jsonMation.bean.parentId);
						$("#cityDiv").show();
					}
					
				}else{
					qiao.bs.msg({msg:jsonMation.returnMessage,type:'danger'});
				}
			}});
			
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
		alert(rowUpdateId);
		//验证成功提交新添加的城市信息
		if($("#cityType").val()=="字母"){//字母
			var params = {
					id : rowUpdateId,
					cityName:$("#cityName").val(),
					cityType:1,
			};
		}else if($("#cityType").val()=="城市"){//城市
			var params = {
					id : rowUpdateId,
					cityName:$("#cityName").val(),
					cityType:2,
					cityLevel:$("#cityLevel").val(),
					cityLetter:$("#cityLetter").find("option:selected").text(),
					parentId : $("#cityLetter").val()
			};
		}
		AjaxPostUtil.request({url:path+"/post/WechatCityController/updateCityById",params:params,type:'json',callback:function(json){
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
	//取消点击事件
	$('body').on('click', '#qvxiao', function(e){
		location.href = "cityList.html";
	});
	
}
