var id1 = null;

$(function(e) {
	dataInit();
});

function dataInit(){
	var oTable = new TableInit();
	oTable.Init();
	eventTini();
}

function eventTini(){
	// 进行查询
	$("body").on("click", '#sub', function(e){
		$('#massage').bootstrapTable('refresh', {url: path+'/post/WechatProductPackageChooseController/getCheckPendingList'});
	});
	//审核通过按钮点击事件
	$('body').on('click', '#button1', function(e){
		if(isNull($("#packagePass").val())){
			qiao.bs.msg({msg:"请填写审核意见",type:'danger'});
			return false;
		}
		var params = {
    		id:id1,	
    		packageOpinion:$("#packagePass").val(),
    	};
    	AjaxPostUtil.request({url:path+"/post/WechatProductPackageChooseController/updatePackageStatePass",params:params,type:'json',callback:function(json){
    		if(json.returnCode==0){
    			qiao.bs.msg({msg:"审核通过",type:'success'});
    			$('#myModal3').modal('hide');
    			$('#massage').bootstrapTable('refresh', {url:path+'/post/WechatProductPackageChooseController/getCheckPendingList'});
    		}else{
    			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
    		}
    	}});
    });
	// 审核不通过按钮点击事件
	$('body').on('click', '#button2', function(e){
		if(isNull($("#packageNoPass").val())){
			qiao.bs.msg({msg:"请填写审核意见",type:'danger'});
			return false;
		}
		
    	var param = {
    		id:id1,	
    		productOpinion:$("#packageNoPass").val(),
    	};
    	AjaxPostUtil.request({url:path+"/post/WechatProductPackageChooseController/updatePackageStateNotPass",params:param,type:'json',callback:function(json){
    		if(json.returnCode==0){
    			qiao.bs.msg({msg:"审核通过",type:'success'});
    			$('#myModal4').modal('hide');
    			$('#massage').bootstrapTable('refresh', {url: path+'/post/WechatProductPackageChooseController/getCheckPendingList'});
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
            url: path+'/post/WechatProductPackageChooseController/getCheckPendingList',             //请求后台的URL（*）
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
                field: 'packageName',
                title: '套餐名称',
                align: 'center',
                width: '150',
                formatter: function (value, row, index) {
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                }
            }, {
                field: 'packageState',
                title: '类别状态',
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
                field: 'packageDesc',
                title: '套餐描述',
                align: 'center',
                width: '100',
                formatter: function (value, row, index) {
                	if (isNull(value)){
                		return "无";
                	}else if(value.length > 20){
    					return value.substring(0,20)+"...";
    				}else{
    					return value;
    				}
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                }
            },{
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
            packageName: $('#search').val(),
        };
        return temp;
    };
    
    return oTableInit;
}

window.EvenInit = {
		
	// 查看套餐 详情
	'click .RoleOfA': function (e, value, row, index) {
    	var rowId = row.id;
    	$('#myModal2').modal('show');
		var params = {
			id:rowId,
		};
		AjaxPostUtil.request({url:path+"/post/WechatProductPackageChooseController/selectOne",params:params,type:'json',callback:function(json){
			if(json.returnCode == 0){
				var states = json.bean.packageState;
				var packageType = json.bean.packageType;
				var productId = json.bean.productId;
				$("#packageName").html(json.bean.packageName);
				$("#createName").html(json.bean.adminNo);
				$("#packageCreateTime").html(json.bean.createTime);
				$("#choosePackagePrice").html(json.bean.packageGroupProductMinPrice + "元");
				// 套餐是否打折
				var count = json.bean.packagePrice;
				var disCounnt = new Array("一折", "二折", "三折", "四折", "五折", "六折", "七折", "八折", "九折", "原价");
				$("#choosePackageDiscount").html(disCounnt[count-1] + "出售");
				if(!isNull(json.bean.packageLogoPath)){
					$("#packageLogo").attr('src',path+"/"+json.bean.packageLogoPath);
				}
				if(json.bean.packageDesc != null && json.bean.packageDesc != '' && typeof(json.bean.packageDesc) != "undefined"){
					$("#packageDesc").html(json.bean.packageDesc);
				}else{
					$("#packageDesc").html("无");
				}
				$("#packagePrice").html(json.bean.packagePrice);
				$("#packageIntegral").html(json.bean.packageIntegral);
				if(json.bean.packageWetherDiscount == "N"){
					$("#packageWetherDiscount").html("不优惠");
				}else{
					$("#packageWetherDiscount").html("优惠");
				}
					
				if(json.bean.packageOpinion != null && json.bean.packageOpinion != '' && typeof(json.bean.packageOpinion) != "undefined"){
					$("#packageOpinion").html(json.bean.packageOpinion);
				}else{
					$("#packageOpinion").html("无");
				}
				
				// 套餐状态的判断
				if(states == 0){
					$("#packageState").html("创建");
				}else if(states == 1){
					$("#packageState").html("正在审核");
				}else if(states == 2){
					$("#packageState").html("审核通过");
				}else if(states == 3){
					$("#packageState").html("审核不通过");
				}else{
					
				}
				// 套餐类型的判断(是否优惠)
				if(packageType == 1){
					$("#packageType").html("永久有效");
				}else if(packageType == 2){
					$("#packageType").html(json.bean.packageStartTime + "~" +json.bean.packageEndTime+"有效");
				}else{
					
				}
				Handlebars.registerHelper("onePackageGroupProductMinPrice", function(v1, v2, option){
					if(v1 == 1)
						return '多选一';
					if(v1 == 2)
						return '多选	可选数量：'+ v2;
					if(v1 == 3)
						return '全选';
				});
				
				Handlebars.registerHelper("productStatus", function(v1, option){
					if(v1 == 4 || v1 == 3)
						return "下线";
					else if (v1 == 1)
						return "正在审核";
					else
						return "上线 ";
				});
				
				Handlebars.registerHelper("oneCreateTime", function(v1, option){
					if(v1.length>10)
						return v1.substring(0,10);
					else
						return v1;
				});
				Handlebars.registerHelper("oneOptionPath", function(v1, option){
					if(!isNull(v1))
						return path + "/" + v1;
					else
						return path + "/assest/img/roleNoPic.png";
				});
				var source = $("#productsBean").html();
				var template = Handlebars.compile(source);
				$("#productsList").html(template(json));
				
/*				$(".img-thumbnail").each(function(){
			    	var myThis = $(this);
			    	var thisUrl = myThis.attr("src");
			    	$.ajax(thisUrl, {
			            type: 'get',
			            timeout: 1000,
			            success: function() {
			            },
			            error: function() {
			            	myThis.attr("src",path + "/assest/icon/maxLogo.jpg");
			            }
			        });
			    });*/
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
	},
	
	"click .RoleOfB":function(e, value, row, index){
		$("#packagePass").val("");// 清空上一次的提审意见
		$("#myModal3").modal('show');
		id1=row.id;
	},
	"click .RoleOfC":function(e, value, ros, index){
		$("#packageNoPass").val("");
		$('#myModal4').modal('show');
		id1 = ros.id;
	}
};

function operateFormatter(value, row, index) {
	return [
	        '<button type="button" class="RoleOfA btn btn-default  btn-sm" style="margin-right:15px;">详情</button>',
	        '<button type="button" class="RoleOfB btn btn-default  btn-sm" style="margin-right:15px;">审核通过</button>',
	        '<button type="button" class="RoleOfC btn btn-default  btn-sm" style="margin-right:15px;">审核不通过</button>',
	    ].join('');
}
