var productTypeId = "";

$(function(e){
	reviewSort();
});

function dataInit(){
	var oTable = new TableInit();
	oTable.Init();
	eventInit();
}

//获取所有的商品类别
function reviewSort(){
	// 显示种类信息
	AjaxPostUtil.request({url:path+"/post/WechatProductMenuController/getTypeList",params:{},type:'json',callback:function(json){
		if(json.returnCode == 0){
			if(json.total != 0){
				// 将标签显示
				$("#myTabContent").attr("style", "display:block");
				$("#myTabList").attr("style", "display:block");

				var source = $("#myTabBean").html();
				var template = Handlebars.compile(source);
				$("#myTabList").html(template(json));
				
				// 将第一个li标签上加上Class属性
				$('ul li:first-child').attr("class", "active");
				
				if (productTypeId == ""){
					productTypeId = json.rows[0].id;
				}
				// 获得第一种类型的商品
				dataInit();
			}else{
				$("#whenUnInvable").append("<img src='../../assest/img/not-available.png'>");
			}
		}else{
			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
		}
	}});
}
//事件
function eventInit(){
	// 进行查询
	$("body").on("click", '#seachBtn', function(e){
		$('#message').bootstrapTable('refresh', {url: path+'/post/WechatProductMenuController/getProductListByTypeId'});
	});
	
	// 点击切换商品种类
	$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
        // 获取已激活的标签页的名称
		productTypeId = $(e.target).attr("productTypeId");
		$("#message").bootstrapTable('refresh', {url: path+'/post/WechatProductMenuController/getProductListByTypeId'});
	});
	
	//添加分类事件
	$('body').on('click','#addProductType',function(e){
		location.href="addProductType.html";
	});
	
	//添加商品事件
	$('body').on('click','#addProduct',function(e){
		location.href="addProduct.html?productTypeId="+productTypeId;
	});
	
	
}


var TableInit =  function (){
	var oTableInit = new Object();
	// 初始化Table
	oTableInit.Init = function(){
		$("#message").bootstrapTable({
			url: path+'/post/WechatProductMenuController/getProductListByTypeId', // 请求后套的URL
			method:"post", 						// 请求方式（*）
			toolbar: '#toolbar',                //工具按钮用哪个容器
			striped: true,                      //是否显示行间隔色
			cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination: false,                   //是否显示分页（*）
			sortable: false,                    //是否启用排序
			sortOrder: "asc",                   //排序方式
            queryParams: oTableInit.queryParams,//传递参数（*）
            contentType: "application/x-www-form-urlencoded",         //发送到服务器的数据编码类型
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10],						//可供选择的每页的行数（*）
            height:700,
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
            showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,
            columns:[{
            	field: 'Number',
            	title: '序号',
            	width: '50',
            	align: 'center',
            	formatter: function (value, row, index) {
            		return index+1;
            	}
            },{
            	field:'logoPath',
        		title:'商品Logo',
        		width:'50',
        		align:'center',
        		formatter: function (value, row, index) {
        			if (isNull(value)){
        				return '<img alt="" src="../../assest/img/roleNoPic.png" class="dialogUserImg img-thumbnail" id = "packageLogo"/>';
        			}
        			return '<img alt="" src="'+ path + '/' + value+'" class="dialogUserImg img-thumbnail" id = "packageLogo"/>';
        		}
            },{
            	field:'productName',
            	title:'商品名称',
            	width:'50',
            	align:'center',
            },{
            	field:'productDesc',
            	title:'描述',
            	width:'100',
            	align:'center',
            	formatter: function (value, row, index) {
            		if (isNull(value)){
                		return "无";
                	}else if(value.length > 20){
    					return value.substring(0,20)+"...";
    				}else{
    					return value;
    				}
                }
            },{
            	field:'productPrice',
            	title:'商品价格',
            	width:'50',
            	align:'center',
            	formatter: function (value, row, index) {
            		return value+"元";
            	}
            },{
            	field:'productIntegral',
            	title:'积分',
            	width:'50',
            	align:'center',
            },{
            	field:'productPriority',
            	title:'当前菜单位置',
            	width:'50',
            	align:'center',
            },{
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
	oTableInit.queryParams = function (params) {
        var temp = {
        	limit: params.limit,   //页面大小
            offset: params.offset,  //页码
            productName: $('#search').val(),
            productType:productTypeId,
        };
        return temp;
    };
    return oTableInit;
}

//单击相应事件
window.EvenInit = {

}
function operateFormatter(value, row, index) {
	return [
			'<button type="button" class="RoleOfA btn btn-primary selectMaton">上移</button>',
			'<button type="button" class="RoleOfB btn btn-info">下移</button>',
			'<button type="button" class="RoleOfC btn btn-success">删除</button>',
			'<button type="button" class="RoleOfD btn btn-default">编辑</button>',
			'<button type="button" class="RoleOfE btn btn-success">详情</button>'
	    ].join('');
}