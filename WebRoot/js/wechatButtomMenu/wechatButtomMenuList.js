var menuVersion="";

$(function(e){
	dataInit();
});

function dataInit(){
	//1.初始化Table
	var oTable = new TableInit();
	oTable.Init();
	eventInit();
}

function  eventInit(){
	$('body').on("click","#selectBean",function(e){
		menuVersion=$("#menuVersion").val(),
		$('#massage').bootstrapTable('refresh',{url: path+'/post/WechatButtomMenuController/selectAllMenus'});
	});
	$('body').on("click","#addMenu",function(e){
		location.href="wechatButtomMenu.html";
	});
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
		});
	}

var TableInit = function () {
	var oTableInit = new Object();
    //初始化Table
	oTableInit.Init = function () {
		$('#massage').bootstrapTable({
			url: path+'/post/WechatButtomMenuController/selectAllMenus',             //请求后台的URL（*）
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
            columns: [ {
			field: 'Number',
			title: '序号',
			width: '50',
			align: 'center',
			formatter: function (value, row, index) {
				return index+1;
				}
		},{
            	field: 'adminNo',
                title: '创建人',
                align: 'center',
                width: '60',
                formatter: function (value, row, index) {
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                }
            }, {
                field: 'createTime',
                title: '创建时间',
                align: 'center',
                width: '100',
                formatter: function (value, row, index) {
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                }
            },{
                field: 'wetherPublish',
                title: '是否发布',
                align: 'center',
                width: '60',
                formatter: function (value, row, index) {
                	if(value==0)
                		return '<a style="word-wrap:break-word;">'+"未发布"+'</a>';
                	else if(value==1)
                		return '<a style="word-wrap:break-word;">'+"已发布"+'</a>';
                	else if(value==2)
                		return '<a style="word-wrap:break-word;">'+"取消发布"+'</a>';	
                	}
            },{
                field: 'publishTime',
                title: '发布时间',
                align: 'center',
                width: '100',
                formatter: function (value, row, index) {
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                	}
            },{
                field: 'menuVersion',
                title: '菜单版本',
                align: 'center',
                width: '100',
                formatter: function (value, row, index) {
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                	}
            },{
                field: 'wetherUser',
                title: '是否正在使用',
                align: 'center',
                width: '100',
                formatter: function (value, row, index) {
                	if(value==0)
                		return '<a style="word-wrap:break-word;">'+"未使用"+'</a>';
                	else
                		return '<a style="word-wrap:break-word;">'+"使用中"+'</a>';
                	}
            },{
            	field: 'operate',
 	            title: '操作',
 	            width: '400',
 	            align: 'center',
 	            events: EvenInit,
 	            formatter: operateFormatter
 	            }],
            onLoadSuccess: function(){  //加载成功时执行
            	console.log("加载成功");
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
    			menuVersion: menuVersion
    			};
    	return temp;
    	};
    	return oTableInit;
}
window.EvenInit = {
		'click .RoleOfA': function (e, value, row, index) {
	    	location.href = "wechatButtomMenu.html?menuVersion="+row.menuVersion;
	    	},
	    	'click .RoleOfB': function (e, value, row, index) {
	    	menuVersion = row.menuVersion;
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
					$('#myModal2').modal('show');
					}
				}});
			},
			'click .RoleOfC': function (e, value, row, index) {
	    	var params={
					id:row.id,
					menuVersion:row.menuVersion,
					wetherUser:1,
					wetherPublish:1
			}
	    	AjaxPostUtil.request({url:path+"/post/WechatButtomMenuController/updateMenuPublish",params:params,type:'json',callback:function(json){
				if(json.returnCode == 0){
					qiao.bs.msg({msg:json.returnMessage,type:'success'});
				$('#massage').bootstrapTable('refresh',{url:path+'/post/WechatButtomMenuController/selectAllMenus'});
				}else{
					qiao.bs.msg({msg:json.returnMessage,type:'danger'});
				}
			}
			});
	    	},
};
function operateFormatter(value, row, index) {
	var str = "";
	if (row.wetherPublish == 1)
		str = [
				'<button type="button" class="RoleOfB btn btn-default  btn-sm" style="margin-right:15px;">详情</button>',
				'<button type="button" class="RoleOfA btn btn-default  btn-sm" style="margin-right:15px;">编辑</button>',
				'<button type="button" class="RoleOfC btn btn-default  btn-sm" style="margin-right:15px;">已发布</button>', 
				]
	else
		str = [
		        '<button type="button" class="RoleOfB btn btn-default  btn-sm" style="margin-right:15px;">详情</button>',
				'<button type="button" class="RoleOfA btn btn-default  btn-sm" style="margin-right:15px;">编辑</button>',
				'<button type="button" class="RoleOfC btn btn-default  btn-sm" style="margin-right:15px;">发布</button>', 
			  ]
	return str.join('');
};
function loadMenuMation(){
	countMenuWidth();
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

