var rowId = null;
var adminWorkXCoordinate = "";
var adminWorkYCoordinate = "";
var map = null;

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
	$('body').on('click','#selectLogin',function(e){
		$('#massage').bootstrapTable('refresh', {url: path+'/post/wechatAdminLoginController/selectShop'}); 
	});
}

var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#massage').bootstrapTable({
            url: path+'/post/wechatAdminLoginController/selectShop',             //请求后台的URL（*）
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
            	title: '门店号',
            	width: '100',
            	align: 'center',
            	formatter: function (value, row, index) {
            		return '<a style="word-wrap:break-word;">'+value+'</a>';
            	}
            }, {
                field: 'adminShopName',
                title: '门店名',
                align: 'center',
                width: '100',
                formatter: function (value, row, index) {
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                }
            },{
                field: 'adminWorkPlace',
                title: '门店地址',
                align: 'center',
                width: '100',
                formatter: function (value, row, index) {
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                }
            },{
                field: 'adminShopCard',
                title: '商户号',
                align: 'center',
                width: '100',
                formatter: function (value, row, index) {
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                }
            },{
                field: 'adminIdentity',
                title: '餐厅状态',
                align: 'center',
                width: '100',
                formatter: function (value, row, index) {
                	if(value==1){
                		return '<a style="word-wrap:break-word;">已认证</a>';
                	}else if(value==5){
                		return '<a style="word-wrap:break-word;">认证中</a>';
                	}
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
            adminShopName:$("#adminShopName").val(),
            adminWorkPlace:$("#adminWorkPlace").val(),
        };
        return temp;
    };
    
    return oTableInit;
}

window.EvenInit = {
	//显示门店信息
    'click .RoleOfA': function (e, value, row, index){
    	var rowId= row.adminNo;
    	var adminRestaurantId = row.id;
    	var params = {
    			adminNo:rowId,
    	};
    	AjaxPostUtil.request({url:path+"/post/wechatAdminLoginController/selectShopXQ",params:params,type:'json',callback:function(json){
    		if(json.returnCode==0){
    			$("#adminNoXQ").text(json.bean.adminNo);
    			$("#adminShopNameXQ").html(json.bean.adminShopName);
    			$("#adminWorkPlaceXQ").html(json.bean.adminWorkPlace);
    			$("#adminKfPhone").html(json.bean.adminKfPhone);
    			$("#adminCharacteristic").html(json.bean.adminCharacteristic);
    			$("#adminKfBusinessHours").html(json.bean.adminKfBusinessHours);
    			$("#adminRecommend").html(json.bean.adminRecommend);
    			$("#adminWorkXCoordinate").html(json.bean.adminWorkXCoordinate);
    			$("#adminWorkYCoordinate").html(json.bean.adminWorkYCoordinate);
    			$("#adminShopCard").html(json.bean.adminShopCard);
    			$("#adminShopKey").html(json.bean.adminShopKey);
    		}else{
    			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
    		}
    		//显示地图
    		map = new AMap.Map('container', {
				 resizeEnable: true,
		         zoom: 11,
		         center: [json.bean.adminWorkXCoordinate, json.bean.adminWorkYCoordinate]
		    });
			//加载插件
			AMap.service('AMap.Geocoder',function(){//回调函数
			    //实例化Geocoder
			    geocoder = new AMap.Geocoder({});
			    //TODO: 使用geocoder 对象完成相关功能
			});
			//在地图上进行标记
			var marker = new AMap.Marker({
	            map:map,
	            bubble:true
	        });
			eventInit();
    		//显示门店中的商品信息
	    	var myparams = {
	    			adminRestaurantId:adminRestaurantId,
	    	}
	    	AjaxPostUtil.request({url:path+"/post/WechatProductRestaurantController/getProduct",params:myparams,type:'json',callback:function(json){
	    		  $("#selectProDiv").html("");
	    		if (json.returnCode==0){
					//对商品logo路径进行修饰
					Handlebars.registerHelper("proLogo", function(v1,options){
						 return path + "/"+v1;
					});
					//对商品创建时间进行修饰
					Handlebars.registerHelper("proCreatTime", function(v1,options){
						return v1.substring(0,10);
					});	
					//商品的列表
					var source = $("#selectProductListBean").html();  
				    var template = Handlebars.compile(source);
				    $("#selectProDiv").html($("#selectProDiv").html() + template(json));
				    //若图片找不到  则显示max图标
					$(".img-thumbnail").each(function(){
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
				    });
	    		}else{
	    			  $("#selectProDiv").html("该门店还没有商品！");
	    		}
	    	}});
    		
    	}});
		//显示查看页面
    	$('#myModal2').modal('show');
    },
	'click .RoleOfB': function (e, value, row, index){
		location.href="storesEdit.html?adminNo="+row.adminNo;
	},
    'click .RoleOfC': function (e, value, row, index){
		location.href="addProduct.html?id="+row.id;
	}
};
function operateFormatter(value, row, index) {
	if(row.adminIdentity==1){
		 return [
		         '<button type="button" class="RoleOfA btn btn-default  btn-sm" style="margin-right:15px;">详情</button>',
		         '<button type="button" class="RoleOfB btn btn-default  btn-sm" style="margin-right:15px;">编辑</button>',
		         '<button type="button" class="RoleOfC btn btn-default  btn-sm" style="margin-right:15px;">分发产品</button>',
		     ].join('');
	}else if(row.adminIdentity==5){
		return [
		         '<button type="button" class="RoleOfA btn btn-default  btn-sm" style="margin-right:15px;">详情</button>',
		         '<button type="button" class="RoleOfB btn btn-default  btn-sm" style="margin-right:15px;">编辑</button>',
		     ].join('');
	}
   
}
