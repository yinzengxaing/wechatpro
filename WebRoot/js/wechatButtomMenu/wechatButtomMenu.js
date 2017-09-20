var menuVersion="";
var menuId="";
var rebackInt=null;
var rebackContext=null;

$(function(e){
	receiveData();
	menuVersion = $.req("menuVersion");
	eventInit();
});
function dataInit(){
	if(isNull(menuId)){
			$("#saveBean").hide();
			$("#deleteBean").hide();
	}
    //版本号为空获取版本号，否则直接查询版本下所有菜单
	if(menuVersion==null){
		AjaxPostUtil.request({url:path+"/post/WechatButtomMenuController/getmenuVersion",params:{},type:'json',callback:function(json){
			if(json.returnCode==0){
				menuVersion=json.bean.menuVersion;
				var params = { 
						menuVersion:menuVersion,
						menuLevel:1
				};
				AjaxPostUtil.request({url:path+"/post/WechatButtomMenuController/selectMenuByVersion",params:params,type:'json',callback:function(json){
					if(json.returnCode==0){
						var source = $("#menuListBean").html();  
					    var template = Handlebars.compile(source);
					    $("#menuNameDiv").html(template(json));
					    loadMenuMation();
					}
				}});
			}
		}});
		
	}else{
		var params = { 
				menuVersion:menuVersion,
				menuLevel:1
			};
		AjaxPostUtil.request({url:path+"/post/WechatButtomMenuController/selectMenuByVersion",params:params,type:'json',callback:function(json){
			if(json.returnCode==0){
				var source = $("#menuListBean").html();  
			    var template = Handlebars.compile(source);
			    $("#menuNameDiv").html(template(json));
			    loadMenuMation();
			}
		}});
	}
}
//菜单添加增加菜单建
function loadMenuMation(){
	var strAdd = '<div class="menu"><div class="bt-name firstAddMenu"><img src="../../assest/img/tianjia.png" class="addMenuPng"/></div></div>';
	var strBeanAdd = '<li class="rowMenu addSecondMenu"><a href="javascript:;"><img src="../../assest/img/tianjia.png" class="addMenuPng"/></a></li>';
	var menuFirst = $(".menu");
	//为以一级菜单添加
	if(menuFirst.length<3){
		$("#menuNameDiv").html($("#menuNameDiv").html() + strAdd);
		countMenuWidth();
	}
	//为二级菜单添加
	$(".menu").each(function(){
    	if($(this).children(".new-sub").children("ul").children("li") .length<5){
    		$(this).children(".new-sub").children("ul").html(strBeanAdd + $(this).children(".new-sub").children("ul").html());
    	}
    });
	countMenuWidth();//计算显示样式
}

function eventInit(){
	$('body').on("click","#backMenuList",function(e){
		location.href="wechatButtomMenuList.html";
	});
	$('body').on('click','#deleteBean',function(e){
		qiao.bs.confirm("是否删除该项菜单？",function(){
		var params = {
				id:$("#menuName").attr("menuId")
		}
		AjaxPostUtil.request({url:path+"/post/WechatButtomMenuController/deleteMenus",params:params,type:'json',callback:function(json){
			if(json.returnCode==0){
				qiao.bs.msg({msg:json.returnMessage,type:'success'});
				var publishparam={
						menuVersion:menuVersion,
						wetherUser:0,
						wetherPublish:0
			         }
		    AjaxPostUtil.request({url:path+"/post/WechatButtomMenuController/updateMenuPublish",params:publishparam,type:'json',callback:function(json){
				if(json.returnCode == 0){
					qiao.bs.msg({msg:json.returnMessage,type:'success'});
					dataInit();
				}else{
					qiao.bs.msg({msg:json.returnMessage,type:'danger'});
				}
			}
			});
				//dataInit();
				$("#myTab").children("li:eq("+0+")").addClass("active");
				$("#myTab").children("li:eq("+1+")").removeClass("active");
				$("#ios").attr("class","tab-pane fade margin-Top-Left-30");
				$("#home").attr("class","tab-pane fade  in active");
				$("#Tw").children("li").removeClass("active");
				$("#Tw").children("li:eq("+0+")").addClass("active");
				$("#context").children("div").attr("class","tab-pane fade");
				$("#context").children("div:eq("+0+")").attr("class","tab-pane fade  in active");
				$('#rolePrompt').val("");
				$('#menuName').val("");
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
		},function(){});
	});
	//保存按钮触发事件
	$('body').on('click','#saveBean',function(e){
	    if(isNull($("#menuName").val())){
			qiao.bs.msg({msg:"菜单名称不能为空",type:'danger'});
			return;
		} else if($("#menuName").attr("level")==1&&$("#menuName").val().length>4){
			qiao.bs.msg({msg:"一级菜单不能超过四个字",type:'danger'});
			return;
		}else if($("#menuName").attr("level")==2&&$("#menuName").val().length>6) {
			qiao.bs.msg({msg:"二级菜单不能超过六个字",type:'danger'});
			return;
		}else{
			//对回复内容的选择进行判断并存储
			for(var i = 0;i<5;i++){
				if($("#ios").hasClass("active")){
					if(IsURL($("#linkNet").val())){
						  rebackInt=6;
						  rebackContext=$("#linkNet").val();
						  break;
					  }else{
						  qiao.bs.msg({msg:"输入的网址不正确，请重新输入",type:'danger'});
					      return;
					  }
				  }
				if($("#Tw").children("li:eq("+i+")").hasClass("active")){
					rebackInt=i+1;
					if(i!=4){
					rebackContext= $("#context").children("div:eq("+i+")").text();
					}else{
						if(isNull($('#rolePrompt').val())){
							qiao.bs.msg({msg:"回复内容不能为空",type:'danger'});
							return;
						}else{
							rebackContext=$('#rolePrompt').val();  
							}
						}
					}
			}
				var params={
					id:$("#menuName").attr("menuId"),
					menuName:$("#menuName").val(),
					rebackContext:encodeURIComponent(rebackContext),
					rebackInt:rebackInt,
					}
			AjaxPostUtil.request({url:path+"/post/WechatButtomMenuController/updateMenuById",params:params,type:'json',callback:function(json){
				if(json.returnCode==0){
					qiao.bs.msg({msg:json.returnMessage,type:'success'});
					var publishparam={
							menuVersion:menuVersion,
							wetherUser:0,
							wetherPublish:0
				}
			    AjaxPostUtil.request({url:path+"/post/WechatButtomMenuController/updateMenuPublish",params:publishparam,type:'json',callback:function(json){
					if(json.returnCode == 0){
						qiao.bs.msg({msg:json.returnMessage,type:'success'});
						dataInit();
					}else{
						qiao.bs.msg({msg:json.returnMessage,type:'danger'});
					}
				}
				});
					//dataInit();
				}
			}},true);
			}
	}); 
	//一级菜单触发事件
	$('body').on('click', '.menu', function(e){
		if ($(this).hasClass("cura")) {
			$(this).children(".new-sub").hide(); //当前菜单下的二级菜单隐藏
			$(".menu").removeClass("cura"); //同一级的菜单项
			} else {
				$(".menu").removeClass("cura"); //移除所有的样式
				$(this).addClass("cura"); //给当前菜单添加特定样式
				$(".menu").children(".new-sub").slideUp("fast"); //隐藏所有的二级菜单
				$(this).children(".new-sub").slideDown("fast"); //展示当前的二级菜单
				}
		//触发添加键
		if($(this).children("div").hasClass("firstAddMenu")){
			$('#menuName').val("");
			$('#rolePrompt').val("");
			$("#linkNet").val("");
			var params={
				menuName:"一级菜单",
				menuVersion:menuVersion,
				menuLevel:1,
				rebackInt:0
			}
			AjaxPostUtil.request({url:path+"/post/WechatButtomMenuController/addMenu",params:params,type:'json',callback:function(json){
				if(json.returnCode==0){
					dataInit();
					}
				}},true);
			}else{
				$("#saveBean").show();
				$("#deleteBean").show();
				var	params={
					id:$(this).children(".bt-name").attr("rowId")
					};
				AjaxPostUtil.request({url:path+"/post/WechatButtomMenuController/selectMenuById",params:params,type:'json',callback:function(json){
					if(json.returnCode==0){
						$('#menuName').val(json.bean.menuName);
						$("#menuName").attr("menuId", json.bean.id);
						menuId=json.bean.id;
						$("#menuName").attr("level", json.bean.menuLevel);
						if(json.bean.hasChild==1){
							$("#myTabContent").hide();
							}else{
								$("#myTabContent").show();
								}
						if(json.bean.rebackInt!=6){
							if(json.bean.rebackInt==0){
								$("#myTab").children("li:eq("+0+")").addClass("active");
								$("#myTab").children("li:eq("+1+")").removeClass("active");
								$("#ios").attr("class","tab-pane fade margin-Top-Left-30");
								$("#home").attr("class","tab-pane fade  in active");
								$("#Tw").children("li").removeClass("active");
								$("#Tw").children("li:eq("+0+")").addClass("active");
								$("#context").children("div").attr("class","tab-pane fade");
								$("#context").children("div:eq("+0+")").attr("class","tab-pane fade  in active");
								
							}else{
								$("#myTab").children("li:eq("+0+")").addClass("active");
								$("#myTab").children("li:eq("+1+")").removeClass("active");
								$("#ios").attr("class","tab-pane fade margin-Top-Left-30");
								$("#home").attr("class","tab-pane fade  in active");
								$("#Tw").children("li").removeClass("active");
								$("#Tw").children("li:eq("+(json.bean.rebackInt-1)+")").addClass("active");
								$("#context").children("div").attr("class","tab-pane fade");
								$("#context").children("div:eq("+(json.bean.rebackInt-1)+")").attr("class","tab-pane fade  in active");
								if(json.bean.rebackInt==5){
									$('#rolePrompt').val(json.bean.rebackContext);
									}
								}
							
							}else {
								$("#myTab").children("li:eq("+0+")").removeClass("active");
								$("#myTab").children("li:eq("+1+")").addClass("active");
								$("#ios").attr("class","tab-pane fade margin-Top-Left-30 in active");
								$("#home").attr("class","tab-pane fade");
								$("#linkNet").val(json.bean.rebackContext);
								}
						}
					}});
				}
		});
	//二级菜单触发
	$('body').on('click', '.rowMenu', function(e){
		//触发二级菜单添加键
		if($(this).hasClass("addSecondMenu")){
			$('#menuName').val("");
			$('#rolePrompt').val("");
			$("#linkNet").val("");
			var beanParams={
				menuName:"二级菜单",
				menuVersion:menuVersion,
				menuLevel:2,
				menuBelong:$(this).parent().parent().parent().children(".bt-name").attr("rowId"),
				rebackInt:0
			}
			AjaxPostUtil.request({url:path+"/post/WechatButtomMenuController/addMenu",params:beanParams,type:'json',callback:function(json){
				if(json.returnCode==0){
					dataInit();
				}
				}});
			e.stopPropagation();
		}else{
			$("#myTabContent").show();
			$("#saveBean").show();
				var	params={
						id:$(this).attr("rowId")
				};
				AjaxPostUtil.request({url:path+"/post/WechatButtomMenuController/selectMenuById",params:params,type:'json',callback:function(json){
					if(json.returnCode==0){
						$('#menuName').val(json.bean.menuName);
						$("#menuName").attr("menuId", json.bean.id);
						menuId=json.bean.id;
						$("#menuName").attr("level", json.bean.menuLevel);
						if(json.bean.rebackInt!=6){
							if(json.bean.rebackInt==0){
								$("#myTab").children("li:eq("+0+")").addClass("active");
								$("#myTab").children("li:eq("+1+")").removeClass("active");
								$("#ios").attr("class","tab-pane fade margin-Top-Left-30");
								$("#home").attr("class","tab-pane fade  in active");
								$("#Tw").children("li").removeClass("active");
								$("#Tw").children("li:eq("+0+")").addClass("active");
								$("#context").children("div").attr("class","tab-pane fade");
								$("#context").children("div:eq("+0+")").attr("class","tab-pane fade  in active");
								
							}else{
								$("#myTab").children("li:eq("+0+")").addClass("active");
								$("#myTab").children("li:eq("+1+")").removeClass("active");
								$("#ios").attr("class","tab-pane fade margin-Top-Left-30");
								$("#home").attr("class","tab-pane fade  in active");
								$("#Tw").children("li").removeClass("active");
								$("#Tw").children("li:eq("+(json.bean.rebackInt-1)+")").addClass("active");
								$("#context").children("div").attr("class","tab-pane fade");
								$("#context").children("div:eq("+(json.bean.rebackInt-1)+")").attr("class","tab-pane fade  in active");
								if(json.bean.rebackInt==5){
									$('#rolePrompt').val(json.bean.rebackContext);
									}
								}
							
							}else {
								$("#myTab").children("li:eq("+0+")").removeClass("active");
								$("#myTab").children("li:eq("+1+")").addClass("active");
								$("#ios").attr("class","tab-pane fade margin-Top-Left-30 in active");
								$("#home").attr("class","tab-pane fade");
								$("#linkNet").val(json.bean.rebackContext);
								}
						}
					}},true);
				e.stopPropagation();
				}
		});
	dataInit();
}

function countMenuWidth(){
	var menu = $(".menu");
	if(menu.length==1){
		$(".menu").css("width","100%");
	}else if(menu.length==2){
		$(".menu").css("width","50%");
	}else if(menu.length==3){
		$(".menu").css("width","33%");
	}
}

function IsURL(str){
	return !!str.match(/^(http|https):\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/);
}

