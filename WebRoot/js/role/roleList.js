var tree = null;
var nodes = null;
var v = null;

$(function(e){
	dataInit();
});

function dataInit(){
	roleDivInit();
	eventInit();
}
function roleDivInit(){
	var params = {
		wechatRoleName:$('#roleName').val(),
		wechatRoleState:$('#wechatRoleState').val(),
	};
	AjaxPostUtil.request({url:path+"/post/WechatAdminRoleController/selectAll",params:params,type:'json',callback:function(json){
		if(json.returnCode==0){
			Handlebars.registerHelper("compare1", function(v1,options){
				if(v1==0){
					return "创建";
				}else if(v1==1){
					return "审核中";
				}else if(v1==2){
					return "上线";
				}else if(v1==3){
					return "审核不通过";
				}else{
					return "";
				}
			});
			Handlebars.registerHelper("compare2", function(v1,v2,options){
				
				if(v2==0){
					return '<button type="button" class="btn btn-primary selectMaton">详情</button>';
				}
				
				if(v1==0){
					return '<button type="button" class="btn btn-primary selectMaton">详情</button>'
						+'<button type="button" class="btn btn-default editRole">编辑</button>'
						+'<button type="button" class="btn btn-info arraignment">提审</button>'
						+'<button type="button" class="btn btn-danger deleteMation">删除</button>';
				}else if(v1==1){
					return '<button type="button" class="btn btn-primary selectMaton">详情</button>';
				}else if(v1==2){
					return '<button type="button" class="btn btn-primary selectMaton">详情</button>'
						+'<button type="button" class="btn btn-default editRole">编辑</button>'
						+'<button type="button" class="btn btn-danger deleteMation">删除</button>';
				}else if(v1==3){
					return '<button type="button" class="btn btn-primary selectMaton">详情</button>'
					+'<button type="button" class="btn btn-default editRole">编辑</button>'
					+'<button type="button" class="btn btn-info arraignment">提审</button>'
					+'<button type="button" class="btn btn-danger deleteMation">删除</button>';
				}else{
				}
			});
			
			Handlebars.registerHelper("compare3", function(v1,options){
				if(v1.length>90){
					return v1.substring(0,90)+"...";
				}else{
					return v1;
				}
			});
			Handlebars.registerHelper("compare4", function(v1,options){
				if(v1 != null ){
					return path + "/" + v1;
				}else{
					return 
				}
			});
			var source = $("#roleListBean").html();  
		    var template = Handlebars.compile(source);
		    $("#roleListDiv").html(template(json));
		    $(".roleLogoPicZs").each(function(){
		    	var myThis = $(this);
		    	var thisUrl = myThis.attr("src");
		    	$.ajax(thisUrl, {
		            type: 'get',
		            timeout: 1000,
		            success: function() {
		            },
		            error: function() {
		            	myThis.attr("src",path + "/assest/img/roleNoPic.png");
		            }
		        });
		    });
		}else{
			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
		}
	}});
}

function eventInit(){
	
	$('body').on('click','#selectRole',function(e){
		roleDivInit();
	});
	
	$('body').on('click', '#addRoleMation', function(e){
		location.href="addRole.html";
	});
	
	$('body').on('click', '.arraignment', function(e){
		var rowId = $(this).parent().parent().parent().find('div[class="panel-heading"]').attr("rowId");
		qiao.bs.confirm("确定要进行提审吗？",function(){
			var params = {
				id:rowId,
			};			
			AjaxPostUtil.request({url:path+"/post/WechatAdminRoleController/updateState",params:params,type:'json',callback:function(json){
				if(json.returnCode==0){
					qiao.bs.msg({msg:"正在进行提审，请耐心等待···",type:'success'});
					setTimeout(roleDivInit,1000);//一秒后刷新页面
				}else{
					qiao.bs.msg({msg:json.returnMessage,type:'danger'});
				}
			}});
		},function(){});
	});
	
	$('body').on('click', '.deleteMation', function(e){
		var rowId = $(this).parent().parent().parent().find('div[class="panel-heading"]').attr("rowId");
		qiao.bs.confirm("确定删除该角色吗？",function(){
			var params = {
				id:rowId,
			};			
			AjaxPostUtil.request({url:path+"/post/WechatAdminRoleController/deleteById",params:params,type:'json',callback:function(json){
				if(json.returnCode==0){
					qiao.bs.msg({msg:"删除成功",type:'success'});
					setTimeout(roleDivInit,1000);//一秒后刷新页面
				}else{
					qiao.bs.msg({msg:json.returnMessage,type:'danger'});
				}
			}});
		},function(){});
	});
	
	$('body').on('click', '.selectMaton', function(e){
		var rowId = $(this).parent().parent().parent().find('div[class="panel-heading"]').attr("rowId");
		$('#myModal2').modal('show');
		var params = {
			id:rowId,
		};
		AjaxPostUtil.request({url:path+"/post/WechatAdminMenuController/selectAll",params:{},type:'json',callback:function(jsonTree){
			if(jsonTree.returnCode==0){
				var setting = {
				    check: {
				        enable: true
				    },
				    data:{
				    	 key:{
				    		 title:"title"
				    	 },
				    	 simpleData: {
				             enable: true
				         }
				     }
				};
				$.fn.zTree.init($("#treeDemo"), setting, jsonTree.rows);
				AjaxPostUtil.request({url:path+"/post/WechatAdminRoleController/selectById",params:params,type:'json',callback:function(json){
					if(json.returnCode==0){
						$("#roleNameFont").html(json.bean.wechatRoleName);
						var str = "";
						if(json.bean.wechatRoleState==0){
							str = "创建";
						}else if(json.bean.wechatRoleState==1){
							str = "提交审核";
						}else if(json.bean.wechatRoleState==2){
							str = "审核通过";
						}else if(json.bean.wechatRoleState==3){
							str = "审核不通过";
						}else if(json.bean.wechatRoleState==4){
							str = "删除";
						}
						$("#roleState").html(str);
						$("#wechatRoleOpinion").html(json.bean.wechatRoleOpinion);
						$("#roleImg").attr("src",path + "/" + json.bean.optionPath);
						$("#createPeople").html(json.bean.createId);
						$("#createTime").html(json.bean.createTime);
						$("#userCount").html(json.bean.userCount);
						$("#roleDesc").html(json.bean.wechatRoleDesc);
						var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
					    var zTree = zTreeObj.getCheckedNodes(false);  
					    var pid= json.bean.userZ; /**此处数据前后必须拼接;*/  
					    for (var i = 0; i < zTree.length; i++) {
					        if (pid.indexOf(";" + zTree[i].id + ";") != -1) {  
			                    zTreeObj.checkNode(zTree[i], true);                   
					        }  
					    }  
					    var nodes = zTreeObj.getNodes(),
						disabled = true, inheritParent = true, inheritChildren = true;
						for (var i = 0, l = nodes.length; i < l; i++) {
							zTreeObj.setChkDisabled(nodes[i], disabled, inheritParent, inheritChildren);
						}
						$(".roleLogoPicZs").each(function(){
					    	var myThis = $(this);
					    	var thisUrl = myThis.attr("src");
					    	$.ajax(thisUrl, {
					            type: 'get',
					            timeout: 1000,
					            success: function() {
					            },
					            error: function() {
					            	myThis.attr("src",path + "/assest/img/roleNoPic.png");
					            }
					        });
					    });
					}else{
						qiao.bs.msg({msg:json.returnMessage,type:'danger'});
					}
				}});
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
	});
	
	$('body').on('click','.editRole',function(e){
		var rowId = $(this).parent().parent().parent().find('div[class="panel-heading"]').attr("rowId");
		location.href="roleEdit.html?id="+rowId;
	});
	
}

