
$(function(e){
	dataInit();
});

function dataInit(){
	//初始化table
	var oTable = new TableInit();
	oTable.Init();
	eventInit();		
}

function eventInit(){
	$('body').on('click','#addScollor',function(e){
		location.href = "addScollor.html";
	});
	
	$('body').on('click','#selectLogin',function(e){
		$('#massage').bootstrapTable('refresh', {url: path+'/post/WechatScollorPicController/selectAllScollor'}); 
	});
}

var TableInit = function(){
	var oTableInit = new Object();
    //初始化Table
	oTableInit.Init = function(){
		$("#massage").bootstrapTable({
			url:path+"/post/WechatScollorPicController/selectAllScollor",
			method: 'post',
			toolbar: '#toolbar',
			striped: true,
			cache: false,
			pagination: true,
			sortable: false,
			sotrOrder: 'asc',
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
            	field: 'scollor_pic_name',
            	title: '广告名称',
            	width: '50',
            	align: 'center',
            	formatter: function (value, row, index) {
            		if (value.length>10){
						//截取字符串前10个字
						return '<a style="word-wrap:break-word;">'+value.substring(0,10)+"..."+'</a>';	
					}else{
						return '<a style="word-wrap:break-word;">'+value+'</a>';	
					}
            	}
            }, {
                field: 'optionPath',
                title: '广告图片',
                align: 'center',
                width: '100',
                formatter: function (value, row, index) {
                	return '<img class="img-circle" style="width:40px;height:40px;" src="'+path+'/'+value+'"/>';
                }
            },{
                field: 'scollor_pic_data',
                title: '添加日期',
                align: 'center',
                width: '100',
                formatter: function (value, row, index) {
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                }
            },
            /*
            {
                field: 'url',
                title: 'url链接',
                align: 'center',
                width: '100',
                formatter: function (value, row, index) {
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                }
            },{
                field: 'adminNo',
                title: '创建人',
                align: 'center',
                width: '50',
                formatter: function (value, row, index) {
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                }
            },{
                field: 'scollor_pic_introduce',
                title: '介绍',
                align: 'center',
                width: '50',
                formatter: function (value, row, index) {
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                }
            },{
                field: 'scollor_pic_display',
                title: '上线状态',
                align: 'center',
                width: '50',
                formatter: function (value, row, index) {
                	if(value==0){
                		return '<a style="word-wrap:break-word;">下线</a>';
                	}else if(value==1){
                		return '<a style="word-wrap:break-word;">上线</a>';
                	}else if(value==2){
                		return '<a style="word-wrap:break-word;">正常</a>';
                	}
                	
                }
            },{
                field: 'scollor_pic_fb',
                title: '发布状态',
                align: 'center',
                width: '50',
                formatter: function (value, row, index) {
                	if(value==0){
                		return '<a style="word-wrap:break-word;">未发布</a>';
                	}else if(value==1){
                		return '<a style="word-wrap:break-word;">已发布</a>';
                	}
                }
            },{
                field: 'scollor_num',
                title: '展示顺序',
                align: 'center',
                width: '80',
                formatter: function (value, row, index) {
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                }
            },*/
            {
	            field: 'operate',
	            title: '操作',
	            width: '300',
	            align: 'center',
	            events: EvenInit,
	            formatter: operateFormatter
            }],
            onLoadSuccess: function(data){  //加载成功时执行
            	if(data.returnCode!=0){
            		qiao.bs.msg({msg:data.returnMessage,type:'danger'});
            	}
            },
            onLoadError:function(){
            	console.log("加载数据失败",{time: 1500, icon : 2});
            }
		});
	};
	
	//得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {
        	limit: params.limit,   //页面大小
            offset: params.offset,  //页码
            scollor_pic_name:$("#scollor_pic_name").val(),
        };
        return temp;
    };
    return oTableInit;
}

window.EvenInit = {
	'click .RoleOfA': function (e, value, row, index){
		location.href = "editScollor.html?id="+row.id;
	},

	//删除按钮
	'click .RoleOfB': function (e, value, row, index){
		var id = row.id;
		qiao.bs.confirm("确定删除该广告吗？",function(){
			var params = {
				id:id,
			};			
			AjaxPostUtil.request({url:path+"/post/WechatScollorPicController/deleteScollor",params:params,type:'json',callback:function(json){
				if(json.returnCode==0){
					qiao.bs.msg({msg:"删除成功",type:'success'});
					$('#massage').bootstrapTable('refresh', {url: path+'/post/WechatScollorPicController/selectAllScollor'});
				}else{
					qiao.bs.msg({msg:json.returnMessage,type:'danger'});
				}
			}});
		},function(){});
	},
	/*
	//上线
	'click .RoleOfC': function (e, value, row, index){
		var id = row.id;
		qiao.bs.confirm("确定上线该通知吗？",function(){
			var params = {
				id:id,
			};			
			AjaxPostUtil.request({url:path+"/post/WechatScollorPicController/updateSxScollor",params:params,type:'json',callback:function(json){
				if(json.returnCode==0){
					qiao.bs.msg({msg:"上线成功",type:'success'});
					$('#massage').bootstrapTable('refresh', {url: path+'/post/WechatScollorPicController/selectAllScollor'});
				}else{
					qiao.bs.msg({msg:json.returnMessage,type:'danger'});
				}
			}});
		},function(){});
	},
	//下线
	'click .RoleOfE': function (e, value, row, index){
		var id = row.id;
		qiao.bs.confirm("确定下线该通知吗？",function(){
			var params = {
				id:id,
			};			
			AjaxPostUtil.request({url:path+"/post/WechatScollorPicController/updateXxScollor",params:params,type:'json',callback:function(json){
				if(json.returnCode==0){
					qiao.bs.msg({msg:"下线成功",type:'success'});
					$('#massage').bootstrapTable('refresh', {url: path+'/post/WechatScollorPicController/selectAllScollor'});
				}else{
					qiao.bs.msg({msg:json.returnMessage,type:'danger'});
				}
			}});
		},function(){});
	},
	//发布
	'click .RoleOfD': function (e, value, row, index){
		$('#myModal').modal('show');
		$('body').on('click', '#queding', function(e){
			var params = {
					id:row.id,
					scollor_num:$("#scollorNum").val(),
				};			
				AjaxPostUtil.request({url:path+"/post/WechatScollorPicController/updateFbScollor",params:params,type:'json',callback:function(json){
					if(json.returnCode==0){
						qiao.bs.msg({msg:"发布成功",type:'success'});
						$('#myModal').modal('hide');
						$('#massage').bootstrapTable('refresh', {url: path+'/post/WechatScollorPicController/selectAllScollor'});
					}else{
						qiao.bs.msg({msg:json.returnMessage,type:'danger'});
					}
				}});
		});
	},
	//取消发布
	'click .RoleOfF': function (e, value, row, index){
		var id = row.id;
		qiao.bs.confirm("确定取消发布该通知吗？",function(){
			var params = {
				id:id,
			};			
			AjaxPostUtil.request({url:path+"/post/WechatScollorPicController/updateQxFbScollor",params:params,type:'json',callback:function(json){
				if(json.returnCode==0){
					qiao.bs.msg({msg:"取消发布成功",type:'success'});
					$('#massage').bootstrapTable('refresh', {url: path+'/post/WechatScollorPicController/selectAllScollor'});
				}else{
					qiao.bs.msg({msg:json.returnMessage,type:'danger'});
				}
			}});
		},function(){});
	},
	//编辑展示顺序
	'click .RoleOfM': function (e, value, row, index){
		$('#myModal1').modal('show');
		$('body').on('click', '#queding1', function(e){
			var params = {
					id:row.id,
					scollor_num:$("#scollorNum1").val(),
				};			
				AjaxPostUtil.request({url:path+"/post/WechatScollorPicController/updateScollorNum",params:params,type:'json',callback:function(json){
					if(json.returnCode==0){
						qiao.bs.msg({msg:"修改展示顺序成功",type:'success'});
						$('#myModal1').modal('hide');
						$('#massage').bootstrapTable('refresh', {url: path+'/post/WechatScollorPicController/selectAllScollor'});
					}else{
						qiao.bs.msg({msg:json.returnMessage,type:'danger'});
					}
				}});
		});
	},*/
	//查看详情
	'click .RoleOfG': function (e, value, row, index){
		var id = row.id;
		$('#myModal2').modal('show');
		var params = {
			id:id,
		};	
		AjaxPostUtil.request({url:path+"/post/WechatScollorPicController/selectById",params:params,type:'json',callback:function(json){
			if(json.returnCode==0){
				$("#scollorName").html(json.bean.scollor_pic_name);
				$("#createTime").html(json.bean.scollor_pic_data);
				//$("#scollorId").html(row.adminNo);
				/*
				if(json.bean.scollor_pic_display == 1){
					$("#scollorLine").html("上线");
				}else if(json.bean.scollor_pic_display == 0){
					$("#scollorLine").html("下线");
				}else if(json.bean.scollor_pic_display == 2){
					$("#scollorLine").html("正常");
				}
				if(json.bean.scollor_pic_fb == 1){
					$("#scollorFabu").html("已发布");
				}else if(json.bean.scollor_pic_fb == 0){
					$("#scollorFabu").html("未发布");
				}
				$("#scollorIndrouce").html(isUndefineReturnNull(json.bean.scollor_pic_introduce));
				$("#scollor_pic_content").html(json.bean.scollor_pic_content);
				$("#url").html(row.url);
				*/
				$("#scollorPicture").attr("src",path + "/" + isUndefineReturnNull(json.bean.optionPath));
				eventInit();
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
    }
	
};

function operateFormatter(value, row, index) {
	/*
	if((row.scollor_pic_display==0 || row.scollor_pic_display==2) && row.scollor_pic_fb==1 ){
		return [
		        '<button type="button" class="RoleOfA btn btn-default  btn-sm" style="margin-right:15px;">编辑</button>',
		        '<button type="button" class="RoleOfG btn btn-default  btn-sm" style="margin-right:15px;">详情</button>',
		        '<button type="button" class="RoleOfB btn btn-default  btn-sm" style="margin-right:15px;">删除</button>',
		         '<button type="button" class="RoleOfC btn btn-default  btn-sm" style="margin-right:15px;">上线</button>',
		         '<button type="button" class="RoleOfF btn btn-default  btn-sm" style="margin-right:15px;">取消发布</button>',
		         '<button type="button" class="RoleOfM btn btn-default  btn-sm" style="margin-right:15px;">编辑展示顺序</button>',
		     ].join('');
	}else if((row.scollor_pic_display==0 || row.scollor_pic_display==2) && row.scollor_pic_fb==0 ){
		return [
		        '<button type="button" class="RoleOfA btn btn-default  btn-sm" style="margin-right:15px;">编辑</button>',
		        '<button type="button" class="RoleOfG btn btn-default  btn-sm" style="margin-right:15px;">详情</button>',
		        '<button type="button" class="RoleOfB btn btn-default  btn-sm" style="margin-right:15px;">删除</button>',
		         '<button type="button" class="RoleOfC btn btn-default  btn-sm" style="margin-right:15px;">上线</button>',
		         '<button type="button" class="RoleOfD btn btn-default  btn-sm" style="margin-right:15px;">发布</button>',
		     ].join('');
	}else if(row.scollor_pic_display==1 && row.scollor_pic_fb==1 ){
		return [
		        '<button type="button" class="RoleOfA btn btn-default  btn-sm" style="margin-right:15px;">编辑</button>',
		        '<button type="button" class="RoleOfG btn btn-default  btn-sm" style="margin-right:15px;">详情</button>',
		        '<button type="button" class="RoleOfB btn btn-default  btn-sm" style="margin-right:15px;">删除</button>',
		         '<button type="button" class="RoleOfE btn btn-default  btn-sm" style="margin-right:15px;">下线</button>',
		         '<button type="button" class="RoleOfF btn btn-default  btn-sm" style="margin-right:15px;">取消发布</button>',
		         '<button type="button" class="RoleOfM btn btn-default  btn-sm" style="margin-right:15px;">编辑展示顺序</button>',
		     ].join('');
	}else if(row.scollor_pic_display==1 && row.scollor_pic_fb==0 ){
		return [
		        '<button type="button" class="RoleOfA btn btn-default  btn-sm" style="margin-right:15px;">编辑</button>',
		        '<button type="button" class="RoleOfG btn btn-default  btn-sm" style="margin-right:15px;">详情</button>',
		        '<button type="button" class="RoleOfB btn btn-default  btn-sm" style="margin-right:15px;">删除</button>',
		         '<button type="button" class="RoleOfE btn btn-default  btn-sm" style="margin-right:15px;">下线</button>',
		         '<button type="button" class="RoleOfD btn btn-default  btn-sm" style="margin-right:15px;">发布</button>',
		     ].join('');
	}*/
	return [
	        '<button type="button" class="RoleOfG btn btn-default  btn-sm" style="margin-right:15px;">阅览图片</button>',
	        '<button type="button" class="RoleOfB btn btn-default  btn-sm" style="margin-right:15px;">删除</button>',
	     ].join('');
   
}