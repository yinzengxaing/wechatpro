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
			var source = $("#fatherMobel").html();  
		    var template = Handlebars.compile(source);
		    $("#fatherMenu").html(template(json));
		    var params = {
		    		id:id,
		    };
	    	AjaxPostUtil.request({url:path+"/post/WechatAdminMenuController/selectById",params:params,type:'json',callback:function(json){
	    		if(json.returnCode==0){
	    			if(json.bean.jibie==1){
	    				$("#menuName").val(json.bean.name);
	    				$("#menuIcon").val(json.bean.icon);
	    				$("#menuLevel").val(json.bean.jibie);
	    				$(".secondMenu").hide();
	    			}else if(json.bean.jibie==2){
	    				$("#menuName").val(json.bean.name);
	    				$("#menuIcon").val(json.bean.icon);
	    				$("#menuLevel").val(json.bean.jibie);
	    				$("#fatherMenu").val(json.bean.belongto);
	    				$("#menuUrl").val(json.bean.url);
	    			}
	    			$("#menuLevel").attr("disabled",true);
	    			eventInit();
	    		}else{
	    			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
	    		}
	    	}});
		}else{
			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
		}
	}});
}

function eventInit(){
	$('body').on('change', '#menuLevel', function(e){
		var menuLevel = $(this).val();
		if(menuLevel==1){
			$(".secondMenu").hide();
		}else if(menuLevel==2){
			$(".secondMenu").show();
		}
	});
	
	$('body').on('click', '#returnBack', function(e){
		location.href="permissionList.html";
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
			id:id,
			name:$("#menuName").val(),
			icon:$("#menuIcon").val(),
			belongto:$("#fatherMenu").val(),
			url:$("#menuUrl").val(),
		};
		AjaxPostUtil.request({url:path+"/post/WechatAdminMenuController/update",params:params,type:'json',callback:function(json){
			if(json.returnCode==0){
		        qiao.bs.msg({msg:"修改成功",type:'success'});
		        setTimeout(function(e){
		        	location.href="permissionList.html";
		        },500);
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
	});
}