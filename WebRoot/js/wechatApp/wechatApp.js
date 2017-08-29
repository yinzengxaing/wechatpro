var id = null;

$(function(e){
	dataInit();
});

function dataInit(){
	eventInit();
}

function initApp(){
	AjaxPostUtil.request({url:path+"/post/WechatAppController/selectApp",params:{},type:'json',callback:function(json){
		if(json.returnCode==0){
			if(json.bean!=null){
				id = json.bean.id; 
				$('#appId').val(json.bean.appId);
				$('#appSecret').val(json.bean.appSecret);
				$('#wechatType').val(json.bean.wechatType);
				defualtInit();
			} else{
				$('#editBean').css({"display":"none"});
				$('#cancleBean').css({"display":"none"});
			}	
		}
	}});
}
function eventInit(){
	$('body').on('click', '#saveBean', function(e){
		if(isNull($("#appId").val())){
			qiao.bs.msg({msg:"APPID不能为空",type:'danger'});
			return;
		} else if(isNull($("#appSecret").val())){
			qiao.bs.msg({msg:"APP-SECRET不能为空",type:'danger'});
			return;
		} else if(isChn($("#appId").val())){     
			qiao.bs.msg({msg:"APPID不能包含汉字",type:'danger'});
			return;
		} else if(isChn($("#appSecret").val())){     
			qiao.bs.msg({msg:"APP-SECRET不能包含汉字",type:'danger'});
			return;
		}
		if(!isNull(id)){
			var params = { 
				id:id,
				appId:$("#appId").val(),	
				appSecret:$("#appSecret").val(),
				wechatType:$("#wechatType").val(),
			};
			AjaxPostUtil.request({url:path+"/post/WechatAppController/updateApp",params:params,type:'json',callback:function(json){
				if(json.returnCode==0){
					qiao.bs.msg({msg:json.returnMessage,type:'success'});
					defualtInit();
				}else{
					qiao.bs.msg({msg:json.returnMessage,type:'danger'});
				}
			}});
		} else{
			var params = { 
				appId:$("#appId").val(),	
				appSecret:$("#appSecret").val(),
				wechatType:$("#wechatType").val(),
			};
			AjaxPostUtil.request({url:path+"/post/WechatAppController/addWechatApp",params:params,type:'json',callback:function(json){
				if(json.returnCode==0){
					id = json.bean.id;
					qiao.bs.msg({msg:json.returnMessage,type:'success'});
					defualtInit();
				}else{
					qiao.bs.msg({msg:json.returnMessage,type:'danger'});
				}
			}});
		}
	});
	
	$('body').on('click','#editBean',function(e){
		$('#editBean').css({"display":"none"});
		$('#saveBean').css({"display":"inline"});
		$('#cancleBean').css({"display":"inline"});
		$('#appId').attr("disabled",false);
		$('#appSecret').attr("disabled",false);
		$('#wechatType').attr("disabled",false);
	});
	
	$('body').on('click','#cancleBean',function(e){
		initApp();
	});
	initApp();
}

function defualtInit(){
	$('#editBean').css({"display":"inline"});
	$('#saveBean').css({"display":"none"});
	$('#cancleBean').css({"display":"none"});
	$('#appId').attr("disabled",true);
	$('#appSecret').attr("disabled",true);
	$('#wechatType').attr("disabled",true);
}

