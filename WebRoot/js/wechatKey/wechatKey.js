var wechatKey="";
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
	$('body').on('click', '#addBean', function(e){
		location.href = "addwechatKey.html";
	});
	
	$('body').on('click', '#selectBean', function(e){
		wechatKey=$("#wechatKeys").val(),
		$('#massage').bootstrapTable('refresh',{url: path+'/post/WechatKeysController/selectAllKeys'});
	});
}
var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#massage').bootstrapTable({
            url: path+'/post/WechatKeysController/selectAllKeys',             //请求后台的URL（*）
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
            	field: 'wechatKey',
                title: '关键字',
                align: 'center',
                width: '60',
                formatter: function (value, row, index) {
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                }
            }, {
                field: 'context',
                title: '回复内容',
                align: 'center',
                width: '100',
                formatter: function (value, row, index) {
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                }
            },{
                field: 'wechatKeyType',
                title: '关键字类型',
                align: 'center',
                width: '60',
                formatter: function (value, row, index) {
                	if(value==1)
                	return '<a style="word-wrap:break-word;">'+"图文关键字"+'</a>';
                	else if(value==2)
                		return '<a style="word-wrap:break-word;">'+"文字关键字"+'</a>';
                	else if(value==3)
                		return '<a style="word-wrap:break-word;">'+"图片关键字"+'</a>';
                	else if(value==4)
                		return '<a style="word-wrap:break-word;">'+"系统关键字"+'</a>';
                	else if(value==5)
                		return '<a style="word-wrap:break-word;">'+"地址关键字"+'</a>';
                }
            },{
                field: 'adminNo',
                title: '创建人ID',
                align: 'center',
                width: '60',
                formatter: function (value, row, index) {
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                }
            },{
                field: 'createTime',
                title: '创建时间',
                align: 'center',
                width: '100',
                formatter: function (value, row, index) {
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                }
            },{
                field: 'wetherClose',
                title: '是否正在运作',
                align: 'center',
                width: '100',
                formatter: function (value, row, index) {
                	if(value==1)
                	return '<a style="word-wrap:break-word;">'+"是"+'</a>';
                	else
                		return '<a style="word-wrap:break-word;">'+"否"+'</a>';	
                }
            },{
                field: 'wetherComplet',
                title: '是否完全匹配',
                align: 'center',
                width: '100',
                formatter: function (value, row, index) {
                	if(value==1)
                	return '<a style="word-wrap:break-word;">'+"是"+'</a>';
                	else
                		return '<a style="word-wrap:break-word;">'+"否"+'</a>';
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
            wechatKey:wechatKey
        };
        return temp;
    };
    return oTableInit;
}

window.EvenInit = {
    'click .RoleOfB': function (e, value, row, index) {
    	qiao.bs.confirm("确定删除该关键字吗？",function(){
    		var params={
    				id:row.id
    		}
    		AjaxPostUtil.request({url:path+"/post/WechatKeysController/deleteWechatKey",params:params,type:'json',callback:function(json){
    			if(json.returnCode == 0){
    				qiao.bs.msg({msg:json.returnMessage,type:'success'});
    			$('#massage').bootstrapTable('refresh',{url:path+'/post/WechatKeysController/selectAllKeys'});
    			}else{
    				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
    			}
    		}
    		});
   });
    },
    'click .RoleOfA': function (e, value, row, index) {
    	location.href = "editwechatKey.html?id="+row.id;
    },
    'click .RoleOfC': function (e, value, row, index) {
    	var params={
				id:row.id,
				context:row.context,
				wechatKey:row.wechatKey,
				wetherClose:0,
		}
    	AjaxPostUtil.request({url:path+"/post/WechatKeysController/updateWechatKey",params:params,type:'json',callback:function(json){
			if(json.returnCode == 0){
				qiao.bs.msg({msg:json.returnMessage,type:'success'});
			$('#massage').bootstrapTable('refresh',{url:path+'/post/WechatKeysController/selectAllKeys'});
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}
		});
    },
    'click .RoleOfD': function (e, value, row, index) {
    	var params={
				id:row.id,
				context:row.context,
				wechatKey:row.wechatKey,
				wetherClose:1,
		}
    	AjaxPostUtil.request({url:path+"/post/WechatKeysController/updateWechatKey",params:params,type:'json',callback:function(json){
			if(json.returnCode == 0){
				qiao.bs.msg({msg:json.returnMessage,type:'success'});
			$('#massage').bootstrapTable('refresh',{url:path+'/post/WechatKeysController/selectAllKeys'});
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}
		});
    }
};

function operateFormatter(value, row, index) {
	      var str = "";
	if (row.wetherClose == 1)
		str = [
				'<button type="button" class="RoleOfA btn btn-default  btn-sm" style="margin-right:15px;">编辑</button>',
				'<button type="button" class="RoleOfB btn btn-default  btn-sm" style="margin-right:15px;">删除</button>',
				'<button type="button" class="RoleOfC btn btn-default  btn-sm" style="margin-right:15px;">停止运行</button>', ]
	else
		str = [
				'<button type="button" class="RoleOfA btn btn-default  btn-sm" style="margin-right:15px;">编辑</button>',
				'<button type="button" class="RoleOfB btn btn-default  btn-sm" style="margin-right:15px;">删除</button>',
				'<button type="button" class="RoleOfD btn btn-default  btn-sm" style="margin-right:15px;">正常运行</button>', ]

	return str.join('');
};

