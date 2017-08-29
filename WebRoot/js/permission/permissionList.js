$(function(e){
	dataInit();
});

function dataInit(){
	//1.初始化Table
    var oTable = new TableInit();
    oTable.Init();
	eventInit();
}

function eventInit(){
	$('body').on('click','#selectMenu',function(e){
		$('#massage').bootstrapTable('refresh', {url: path+'/post/WechatAdminMenuController/selectAllMenuList'}); 
	});
	
	$('body').on('click', '#addMenu', function(e){
		location.href="addMenu.html";
	});	
	
	$('body').on('click','#addPermission',function(e){
		location.href="permissionAdd.html";
	});
}

var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#massage').bootstrapTable({
            url: path+'/post/WechatAdminMenuController/selectAllMenuList',             //请求后台的URL（*）
            method: 'post',                     //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: false,                    //是否启用排序
            sortOrder: "asc",                   //排序方式
            queryParams: oTableInit.queryParams,//传递参数（*）
            contentType: "application/x-www-form-urlencoded",         //发送到服务器的数据编码类型
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10],        //可供选择的每页的行数（*）
            height:700,
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
            showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                  //是否显示父子表
            columns: [{
            	field: 'Number',
            	title: '序号',
            	width: '50',
            	align: 'center',
            	formatter: function (value, row, index) {
            		return index+1;
            	}
            }, {
                field: 'name',
                title: '菜单名称',
                align: 'center',
                width: '150',
                formatter: function (value, row, index) {
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                }
            }, {
                field: 'jibie',
                title: '菜单级别',
                align: 'center',
                width: '150',
                formatter: function (value, row, index) {
                	if(value==1){
                		return '<a style="word-wrap:break-word;">一级菜单</a>';
                	}else if(value==2){
                		return '<a style="word-wrap:break-word;">二级菜单</a>';
                	}else if(value==3){
                		return '<a style="word-wrap:break-word;">权限</a>';
                	}                	
                }
            }, {
                field: 'url',
                title: '链接地址',
                align: 'center',
                width: '150',
                formatter: function (value, row, index) {
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                }
            }, {
                field: 'createTime',
                title: '创建时间',
                align: 'center',
                width: '150',
                formatter: function (value, row, index) {
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                }
            }, {
	            field: 'operate',
	            title: '操作',
	            width: '210',
	            align: 'center',
	            events: EvenInit,
	            formatter: operateFormatter
            }],
            onLoadSuccess: function(data){  //加载成功时执行
            	if(data.returnCode!=0){
            		qiao.bs.msg({msg:data.returnMessage,type:'danger'});
            	}
            },
            onLoadError: function(){  //加载失败时执行
            	console.log("加载数据失败", {time : 1500, icon : 2});
            }
        });
    };
    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {
        	limit: params.limit,   //页面大小
            offset: params.offset,  //页码
            menuName:$('#menuName').val(),
            jibie:$('#jibie').val(),
        };
        return temp;
    };
    
    return oTableInit;
}

window.EvenInit = {
	
    'click .RoleOfA': function (e, value, row, index) {
        location.href="permissionEdit.html?id="+row.id;
    },
		
    'click .RoleOfB': function (e, value, row, index) {
    	var params = {
        		id:row.id,	
        };
    	//判断是否有角色在使用
    	AjaxPostUtil.request({url:path+"/post/WechatAdminRoleMenuController/selectRoleByMenuId",params:params,type:'json',callback:function(jsonSelect){
    		if(jsonSelect.returnCode==0){//如果有
		    	qiao.bs.confirm("该权限有角色在使用，确定删除该权限吗？",function(){
		    		AjaxPostUtil.request({url:path+"/post/WechatAdminMenuController/deleteMenuById",params:params,type:'json',callback:function(json){
		    			if(json.returnCode==0){
		    				qiao.bs.msg({msg:"删除成功",type:'success'});
		    				$('#massage').bootstrapTable('refresh', {url: path+'/post/WechatAdminMenuController/selectAllMenuList'});  
		    			}else{
		    				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
		    			}
		    		}});
		    	},function(){});
		    }else{
		    	//删除菜单
		    	qiao.bs.confirm("确定删除该权限吗？",function(){
		    		AjaxPostUtil.request({url:path+"/post/WechatAdminMenuController/deleteById",params:params,type:'json',callback:function(json){
		    			if(json.returnCode==0){
		    				qiao.bs.msg({msg:"删除成功",type:'success'});
		    				$('#massage').bootstrapTable('refresh', {url: path+'/post/WechatAdminMenuController/selectAllMenuList'});  
		    			}else{
		    				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
		    			}
		    		}});
		    	},function(){
		    	});
		    }
		}});    
    },
 
    'click .RoleOfC': function (e, value, row, index) {
    	$('#myModal').modal('show');
    	var params = {
    			id:row.id,
    	};
    	//debugger;
    	AjaxPostUtil.request({url:path+"/post/WechatAdminMenuController/selectPower",params:params,type:'json',callback:function(json){
    		if(json.returnCode==0){
    			var source = $("#permissionListBean").html();  
    		    var template = Handlebars.compile(source);
    		    $("#permissionListDiv").html(template(json));
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
    },
    
    'click .RoleOfD': function (e, value, row, index) {
    	location.href="powerEdit.html?id="+row.id;
    }
};
function operateFormatter(value, row, index) {
	if(row.jibie==1){
		 return [
		         '<button type="button" class="RoleOfA btn btn-default  btn-sm" style="margin-right:15px;">编辑</button>',
		         '<button type="button" class="RoleOfB btn btn-default  btn-sm" style="margin-right:15px;">删除</button>',
		     ].join('');
	}else if(row.jibie==3){
		return [
		        '<button type="button" class="RoleOfD btn btn-default  btn-sm" style="margin-right:15px;">编辑</button>',
		        '<button type="button" class="RoleOfB btn btn-default  btn-sm" style="margin-right:15px;">删除</button>',
		    ].join('');
	}else{
		return [
		        '<button type="button" class="RoleOfA btn btn-default  btn-sm" style="margin-right:15px;">编辑</button>',
		        '<button type="button" class="RoleOfB btn btn-default  btn-sm" style="margin-right:15px;">删除</button>',
		        '<button type="button" class="RoleOfC btn btn-default  btn-sm" style="margin-right:15px;">查看</button>',
		    ].join('');
	}
}
