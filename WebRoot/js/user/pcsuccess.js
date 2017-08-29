$(function(e){
	dataCommonInit();
	eventCommonInit();
});

function dataCommonInit(){
	AjaxPostUtil.request({url:path+"/post/wechatAdminLoginController/selectSession",params:{},type:'json',callback:function(jsonSession){		
			if(jsonSession.returnCode==0){
				$("#login").text(jsonSession.bean.adminNo);
				AjaxPostUtil.request({url:path+"/post/wechatAdminLoginController/selectByUser",params:{},type:'json',callback:function(json){
					if(json.returnCode==0){
						Handlebars.registerHelper("compare1", function(v1,v2,options){
							if(v1==null||v1==""){
								return options.fn(this);
							}else{
								return options.inverse(this);
							}
						});
						var source = $("#selectMenu").html();  
					    var template = Handlebars.compile(source);
					    $("#sideNav").html($("#sideNav").html()+template(json));
					    $.sidebarMenu($('#sideNav'));
					}else{
						location.href = 'login.html';
					}
				}});
			}else{
				location.href = 'login.html';
			}
		}
	});
}

function eventCommonInit(){
	$('body').on('click', '.clickMenu', function(e){
	});
	
	$('body').on('click', '#logoOut', function(e){
		AjaxPostUtil.request({url:path+"/post/wechatAdminLoginController/clearSession",params:{},type:'json',callback:function(jsonSession){
			if(jsonSession.returnCode==0){
				location.href = 'login.html';
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}
	});
	});
	
}

