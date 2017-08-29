$(function(e){
	dataInit();
});

function dataInit(){
	AjaxPostUtil.request({url:path+"/post/WechatAdminMenuController/selectFirst",params:{},type:'json',callback:function(json){
		if(json.returnCode==0){
			var source = $("#fatherMobel").html();  
		    var template = Handlebars.compile(source);
		    $("#fatherMenu").html(template(json));
		    eventInit();
		}else{
			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
		}
	}});
}

function eventInit(){
	$(".secondMenu").hide();
	$('body').on('change', '#menuLevel', function(e){
		var menuLevel = $(this).val();
		if(menuLevel==1){
			$(".secondMenu").hide();
		}else if(menuLevel==2){
			$(".secondMenu").show();
		}
	});
	
	$('body').on('click', '#saveMenu', function(e){
		if(isNull($("#menuName").val())){
			qiao.bs.msg({msg:"菜单名称不能为空",type:'danger'});
			return;
		}else if($("#menuIcon").val()==null || $("#menuIcon").val()==""){
			qiao.bs.msg({msg:"菜单图标不能为空",type:'danger'});
			return;
		}else if($("#menuLevel").val()==2){
			if($("#menuUrl").val()==null || $("#menuUrl").val()==""){
				qiao.bs.msg({msg:"二级菜单url不能为空",type:'danger'});
				return;
			}
		}
		var params = {
			name:$("#menuName").val(),
			icon:$("#menuIcon").val(),
			menuLevel:$("#menuLevel").val(),
			url:$("#menuUrl").val(),
			fatherId:$("#fatherMenu").val(),	
		};
		AjaxPostUtil.request({url:path+"/post/WechatAdminMenuController/insertAdminMenu",params:params,type:'json',callback:function(json){
			if(json.returnCode==0){
		        qiao.bs.msg({msg:"添加成功",type:'success'});
		        setTimeout(function(e){
		        	location.href = 'permissionList.html';
		        },500);
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
	});
	
	$('body').on('click', '#qvxiao', function(e){
		location.href="permissionList.html";
	});
}