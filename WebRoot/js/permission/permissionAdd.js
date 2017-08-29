var id = null;

$(function(e){
	receiveData();
	dataInit();
});

function dataInit(){
	if(id==null){
		id = $.req("id");
	}
	AjaxPostUtil.request({url:path+"/post/WechatAdminMenuController/selectFirst",params:{},type:'json',callback:function(json){
		if(json.returnCode==0){
			var source = $("#oneMobel").html();  
		    var template = Handlebars.compile(source);
		    $("#menuOne").html(template(json));
		    eventInit();
		}else{
			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
		}
	}});
}

function eventInit(){
	$('body').on('change','#menuOne',function(e){
		var id =$("#menuOne").val();
	    var params = {
	    		id:id,
	    };
	    AjaxPostUtil.request({url:path+"/post/WechatAdminMenuController/selectSecondMenu",params:params,type:'json',callback:function(json){
			if(json.returnCode==0){
				var source = $("#twoMobel").html();  
			    var template = Handlebars.compile(source);
			    $("#menuTwo").html(template(json));
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
	});
	
	$('body').on('click', '#returnBack', function(e){
		location.href="permissionList.html";
	});
	
	$('body').on('click', '#saveMenu', function(e){
		if(isNull($("#permissionName").val())){
			qiao.bs.msg({msg:"权限名称不能为空",type:'danger'});
			return;
		}else if($("#permissionUrl").val()==null){
			qiao.bs.msg({msg:"权限链接不能为空",type:'danger'});
			return;
		}
		var id = $("#menuTwo").val();
		var params = {
			id:id,
			name:$("#permissionName").val(),
			url:$("#permissionUrl").val(),
		};
		AjaxPostUtil.request({url:path+"/post/WechatAdminMenuController/insertPower",params:params,type:'json',callback:function(json){
			if(json.returnCode==0){
		        qiao.bs.msg({msg:"添加成功",type:'success'});
		        setTimeout(function(e){
		        	location.href="permissionList.html";
		        },500);
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
	});
}