var id1 = null;

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
	//查询按钮点击事件
	$('body').on('click', '#sub', function(e){
	    $('#massage').bootstrapTable('refresh', {url: path+'/post/WechatProductBrandTagController/getCheckPendingList'});  
    });
	//审核通过按钮点击事件
	$('body').on('click', '#button1', function(e){
		if(isNull($("#bandTagPass").val())){
			qiao.bs.msg({msg:"请填写审核意见",type:'danger'});
			return false;
		}
    	var params = {
    		id:id1,	
    		brandTagOpinion:$("#bandTagPass").val()
    	};
    	AjaxPostUtil.request({url:path+"/post/WechatProductBrandTagController/updateStatePass",params:params,type:'json',callback:function(json){
    		if(json.returnCode==0){
    			qiao.bs.msg({msg:"审核通过",type:'success'});
    			$('#myModal3').modal('hide');
    			$('#massage').bootstrapTable('refresh', {url: path+'/post/WechatProductBrandTagController/getCheckPendingList'});
    		}else{
    			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
    		}
    	}});
    });
	
	$('body').on('click', '#button2', function(e){
		if(isNull($("#bandTagNoPass").val())){
			qiao.bs.msg({msg:"请填写审核意见",type:'danger'});
			return false;
		}
    	var params = {
    		id:id1,	
    		brandTagOpinion:$("#bandTagNoPass").val()
    	};
    	AjaxPostUtil.request({url:path+"/post/WechatProductBrandTagController/updateStateNoPass",params:params,type:'json',callback:function(json){
    		if(json.returnCode==0){
    			qiao.bs.msg({msg:"审核不通过",type:'success'});
    			$('#myModal4').modal('hide');
    			$('#massage').bootstrapTable('refresh', {url: path+'/post/WechatProductBrandTagController/getCheckPendingList'});
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
            url: path+'/post/WechatProductBrandTagController/getCheckPendingList',             //请求后台的URL（*）
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
                field: 'brandTagName',
                title: '品牌名称',
                align: 'center',
                width: '150',
                formatter: function (value, row, index) {
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                }
            }, {
                field: 'brandTagState',
                title: '品牌状态',
                align: 'center',
                width: '100',
                formatter: function (value, row, index) {
                	if(value==0){
    					return "创建";
    				}else if(value==1){
    					return "待审核";
    				}else if(value==2){
    					return "上线";
    				}else if(value==3){
    					return "审核不通过";
    				}else{
    					return "";
    				}
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                }
            }, {
                field: 'brandTagDesc',
                title: '品牌描述',
                align: 'center',
                width: '100',
                formatter: function (value, row, index) {
                	if(value.length>20){
    					return value.substring(0,20)+"...";
    				}else{
    					return value;
    				}
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                }
            }, {
                field: 'adminNo',
                title: '创建人',
                align: 'center',
                width: '100',
                formatter: function (value, row, index) {
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                }
            }, {
                field: 'createTime',
                title: '创建时间',
                align: 'center',
                width: '100',
                formatter: function (value, row, index) {
                	return '<a style="word-wrap:break-word;">'+value.substring(0,10)+'</a>';
                }
            }, {
	            field: 'operate',
	            title: '操作',
	            width: '300',
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
            brandTagName: $('#search').val(),
        };
        return temp;
    };
    
    return oTableInit;
}

window.EvenInit = {
    'click .RoleOfA': function (e, value, row, index) {
    	var rowId = row.id;
    	$('#myModal2').modal('show');
		var params = {
			id:rowId
			}
		AjaxPostUtil.request({url:path+"/post/WechatProductBrandTagController/getProductBrandTagById",params:params,type:'json',callback:function(json){
			if (json.returnCode == 0){
				var str = "";
				if(json.bean.brandTagState==0){
					str = "创建";
				}else if(json.bean.brandTagState==1){
					str = "待审核";
				}else if(json.bean.brandTagState==2){
					str = "审核通过";
				}else if(json.bean.brandTagState==3){
					str = "审核不通过";
				}else if(json.bean.brandTagState==4){
					str = "删除";
				}
				$("#brandTagState").html(str);
				$("#brandTagName").html(json.bean.brandTagName);
				$("#brandTagDesc").html(json.bean.brandTagDesc);
				$("#createTime").html(json.bean.createTime);
				$("#adminNo").html(json.bean.adminNo);
				if(isNull(json.bean.brandTagOpinion)){
					$("#brandTagOpinion").html("无");
				}else{
					$("#brandTagOpinion").html(json.bean.brandTagOpinion);
				}
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
    },
    'click .RoleOfB': function (e, value, row, index) {
    	$('#myModal3').modal('show');
    	id1=row.id;
    },
    'click .RoleOfC': function (e, value, row, index) {
    	$('#myModal4').modal('show');
    	id1=row.id
    }
};
function operateFormatter(value, row, index) {
    return [
        '<button type="button" class="RoleOfA btn btn-default  btn-sm" style="margin-right:15px;">详情</button>',
        '<button type="button" class="RoleOfB btn btn-default  btn-sm" style="margin-right:15px;">审核通过</button>',
        '<button type="button" class="RoleOfC btn btn-default  btn-sm" style="margin-right:15px;">审核不通过</button>',
    ].join('');
}
