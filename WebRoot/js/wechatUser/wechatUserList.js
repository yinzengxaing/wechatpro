
var base = null;

$(function(e){
	dataInit();
});

function dataInit(){
	base = new Base64();
	AjaxPostUtil.request({url:path+"/post/wechatAdminLoginController/selectSession",params:{},type:'json',callback:function(jsonSession){
		if(jsonSession.returnCode==0){
			var goEasy = new GoEasy({
			    appkey: 'BC-c5e986fba5d14d38b2b2c5b4b072fc8c'
			});
			goEasy.subscribe({
				channel: jsonSession.bean.adminNo,
				onMessage: function (message) {
					qiao.bs.msg({msg:message.content,type:'success'});
				},
				onSuccess: function () {
					qiao.bs.msg({msg:"消息接收机制已启动",type:'success'});
				},
				onFailed: function (error) {
					alert("Channel订阅失败, 错误编码：" + error.code + " 错误信息：" + error.content)
				}
			});
			//1.初始化Table
		    var oTable = new TableInit();
		    oTable.Init();
			eventInit();
		}else{
			location.href = 'login.html';
		}
	}});
}

function eventInit(){
	
	$('body').on('click', '#selectBean', function(e){
		$('#massage').bootstrapTable('refresh',{url: path+'/post/WechatUserController/selectAllWechatUser'});
	});
	
	$('body').on('click', '#updateBean', function(e){
		if(isNull($("#numUpdate").val())){
			qiao.bs.msg({msg:"请选择更新数量",type:'danger'});
			return;
		}
		AjaxPostUtil.request({url:path+"/post/WechatUserController/checkWechatUser",params:{numUpdate:$("#numUpdate").val()},type:'json',callback:function(json){
			if(json.returnCode==0){
				$('#massage').bootstrapTable('refresh',{url: path+'/post/WechatUserController/selectAllWechatUser'});
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
	});
	
}
var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#massage').bootstrapTable({
            url: path+'/post/WechatUserController/selectAllWechatUser',             //请求后台的URL（*）
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
            height:750,
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
            	field: 'openid',
                title: '用户标识',
                align: 'center',
                width: '60',
                formatter: function (value, row, index) {
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                }
            }, {
                field: 'nickname',
                title: '用户昵称',
                align: 'center',
                width: '100',
                formatter: function (value, row, index) {
                	return '<a style="word-wrap:break-word;">'+base.decode(value)+'</a>';
                }
            },{
                field: 'sex',
                title: '用户性别',
                align: 'center',
                width: '60',
                formatter: function (value, row, index) {
                	if(value==1)
                		return '<a style="word-wrap:break-word;color:green">男</a>';
                	else if(value==2)
                		return '<a style="word-wrap:break-word;color:red">女</a>';
                	else
                		return '<a style="word-wrap:break-word;color:blue">未知</a>';
                }
            },{
                field: 'subscribe',
                title: '用户是否关注订阅号',
                align: 'center',
                width: '100',
                formatter: function (value, row, index) {
                	if(value==1)
                		return '<a style="word-wrap:break-word;color:green">关注中</a>';
                	else
                		return '<a style="word-wrap:break-word;color:red">取消关注</a>';
                }
            },{
                field: 'user_id',
                title: '是否绑定用户',
                align: 'center',
                width: '100',
                formatter: function (value, row, index) {
                	if(value==1)
                	return '<a style="word-wrap:break-word;color:green">是</a>';
                	else
                		return '<a style="word-wrap:break-word;color:red">否</a>';	
                }
            },{
                field: 'wechatNowUser',
                title: '手机号',
                align: 'center',
                width: '100',
                formatter: function (value, row, index) {
                	if(isNull(value)){
                		value = "";
                	}
                	return '<a style="word-wrap:break-word;color:green">'+value+'</a>';
                		
                }
            },{
            	 field: 'headimgurl',
 	            title: '用户头像',
 	            width: '400',
 	            align: 'center',
 	           formatter: function (value, row, index) {
               	return '<img class="img-circle" style="width:40px;height:40px;" src="'+value+'"/>';
               }
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
            nickname:$("#nickname").val(),
            openid:''
        };
        return temp;
    };
    
    return oTableInit;
}


