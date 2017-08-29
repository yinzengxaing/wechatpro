$(function(e){
	dataInit();
});

function dataInit(){
	AjaxPostUtil.request({url:path+"/post/WechatAdminRoleController/selectAll",params:{},type:'json',callback:function(json){
		if(json.returnCode==0){
			var source = $("#roleTypeModel").html();  
		    var template = Handlebars.compile(source);
		    $("#roleType").html(template(json));
		  //1.初始化Table
		    var oTable = new TableInit();
		    oTable.Init();
			eventInit();
		}else{
			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
		}
	}});
}

function eventInit(){
	$('body').on('click','#selectLogin',function(e){
		$('#massage').bootstrapTable('refresh', {url: path+'/post/wechatAdminLoginController/selectAll'}); 
	});
	
	$('body').on('click', '#addUser', function(e){
		location.href="addUser.html";
	});
}

var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#massage').bootstrapTable({
            url: path+'/post/wechatAdminLoginController/selectAll',             //请求后台的URL（*）
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
            },{
            	field: 'adminNo',
            	title: '用户账号',
            	width: '100',
            	align: 'center',
            	formatter: function (value, row, index) {
            		return '<a style="word-wrap:break-word;">'+value+'</a>';
            	}
            }, {
                field: 'adminIdentityShow',
                title: '用户身份',
                align: 'center',
                width: '100',
                formatter: function (value, row, index) {
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                }
            },{
                field: 'creatTime',
                title: '注册时间',
                align: 'center',
                width: '100',
                formatter: function (value, row, index) {
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                }
            },{
                field: 'adminRole',
                title: '用户角色',
                align: 'center',
                width: '100',
                formatter: function (value, row, index) {
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                }
            },{
	            field: 'operate',
	            title: '操作',
	            width: '160',
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
            adminNo:$("#adminNo").val(),
            adminIdentity:$("#loginState").val(),
            adminRole:$("#roleType").val(),
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
			id:rowId,
		};
		AjaxPostUtil.request({url:path+"/post/wechatAdminLoginController/selectById",params:params,type:'json',callback:function(json){
			if(json.returnCode==0){
				$("#userName").html(json.bean.adminNo);
				$("#userIdentitiy").html(json.bean.adminIdentityShow);
				$("#createTime").html(json.bean.creatTime);
				$("#userRole").html(json.bean.wechatRoleName);
				$("#userHeadPoint").html(json.bean.adminHeadPoint);
				if(json.bean.adminSex == 1){
					json.bean.adminSex = "男";
				}else if(json.bean.adminSex == 2){
					json.bean.adminSex = "女";
				}else{
					json.bean.adminSex = "-";
				}
				$("#realName").html(isUndefineReturnNull(json.bean.realName));
				$("#adminSex").html(isUndefineReturnNull(json.bean.adminSex));
				$("#adminAge").html(isUndefineReturnNull(json.bean.adminAge));
				$("#adminEducation").html(isUndefineReturnNull(json.bean.adminEducation));
				$("#adminNation").html(isUndefineReturnNull(json.bean.adminNation));
				$("#adminWorkPlace").html(isUndefineReturnNull(json.bean.adminWorkPlace));
				$("#adminHomePlace").html(isUndefineReturnNull(json.bean.adminHomePlace));
				$("#adminPoliticalLandscape").html(isUndefineReturnNull(json.bean.adminPoliticalLandscape));
				$("#adminQq").html(isUndefineReturnNull(json.bean.adminQq));
				
				if(json.bean.adminIdentityShow == "餐厅人员" || json.bean.adminIdentityShow == "餐厅人员认证"){
					$("#shopName").show();
					$("#adminShopName").html(json.bean.adminShopName);
				}else{
					$("#shopName").hide();
				}
				$("#userIDCardPositive").attr("src",path + "/" + isUndefineReturnNull(json.bean.optionPath));
				$("#userIDCardOtherPositive").attr("src",path + "/" + isUndefineReturnNull(json.bean.optionPath2));
				$("#userIDCardPeoAndPosi").attr("src",path + "/" + isUndefineReturnNull(json.bean.optionPath3));
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
    },
    'click .RoleOfB': function (e, value, row, index) {
    	$('#myModal5').modal('show');
    	$('body').on('click', '#button3', function(e){
    		if(isNull($("#newPassword1").val()) || isNull($("#newPassword2").val())){
    			qiao.bs.msg({msg:"密码不能为空,请输入!",type:'danger'});
    			return false;
    		}else{
    			var params = {
    		    		id:row.id,
    		    		newPassword1:$("#newPassword1").val(),
    		    		newPassword2:$("#newPassword2").val(),
    		    	};
    		    	AjaxPostUtil.request({url:path+"/post/wechatAdminLoginController/updatePassword",params:params,type:'json',callback:function(json){
    					if(json.returnCode==0){
    						qiao.bs.msg({msg:"修改密码成功",type:'success'});
    						$('#myModal5').modal('hide');
    						$('#massage').bootstrapTable('refresh', {url: path+'/post/wechatAdminLoginController/selectAll'}); 
    					}else{
    						qiao.bs.msg({msg:json.returnMessage,type:'danger'});
    					}
    				}});
    		}
    	});
    }
};
function operateFormatter(value, row, index) {
    return [
        '<button type="button" class="RoleOfA btn btn-default  btn-sm" style="margin-right:15px;">详情</button>',
        '<button type="button" class="RoleOfB btn btn-default  btn-sm" style="margin-right:15px;">修改密码</button>',
    ].join('');
}
