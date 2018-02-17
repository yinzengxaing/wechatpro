$(function(e){
	dataInit();
});

function dataInit(){
	var oTable = new TableInit();
	oTable.Init();
	eventInit();
}
function eventInit(){
	//添加按钮点击事件
	$('body').on('click', '#addProductType', function(e){
		location.href="addProductType.html";
	});
	//查询按钮点击事件
	$('body').on('click', '#queryButton',function(e){
		$('#massage').bootstrapTable('refresh',{url:path+'/post/WechatProductTypeController/getProductTypeList'});
	});
}
var TableInit = function (){
	var oTableInit = new Object();
	//初始化Table
	oTableInit.Init = function () {
		$('#massage').bootstrapTable({
			url: path+'/post/WechatProductTypeController/getProductTypeList',             //请求后台的URL（*） 
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
			}, {
				field: 'typeName',
				title: '类别名称',
				align: 'center',
				width: '150',
				formatter: function (value, row, index) {
					return '<a style="word-wrap:break-word;">'+value+'</a>';
				}
			},{
				field: 'typeDesc',
				title: '类别描述',
				align: 'center',
				width: '200',
				formatter: function (value, row, index) {
					if (isNull(value)){
						return '<a style="word-wrap:break-word;">'+"无"+'</a>';
					}else{
						if (value.length>30){
							//截取字符串前30个字
							return '<a style="word-wrap:break-word;">'+value.substring(0,20)+"..."+'</a>';	
						}else{
							return '<a style="word-wrap:break-word;">'+value+'</a>';	
						}
					}  
				}
			},{
				field: 'typeState',
				title: '状态',
				align: 'center',
				width: '100',
				formatter: function (value, row, index) {
					if (value == 0)
						return  '<a style="word-wrap:break-word;">'+"创建"+'</a>';
					else if (value == 1)
						return '<a style="word-wrap:break-word;">'+"审核中"+'</a>';
					else if (value == 2)
						return '<a style="word-wrap:break-word;">'+"上线"+'</a>';
					else if (value == 3)
						return '<a style="word-wrap:break-word;">'+"审核不通过"+'</a>';
				}
			},{
				field :'createTime',
				title: '创建时间',
				align: 'center',
        	  	width: '100',
        	  	formatter: function (value, row, index){
        	  		return '<a style="word-wrap:break-word;">'+value.substring(0,10)+'</a>';
        	  	}
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
	  //得到查询的参数
	oTableInit.queryParams = function (params) {
		var temp={
				limit: params.limit,   // 页面大小
				offset: params.offset,  // 页码
				typeName:$('#queryTypeName').val(),
				typeState: $('#typeStateItem').val()
		};
		return temp;
	};
	return oTableInit;
};

//操作按钮点击事件
window.EvenInit = {
		'click .RoleOfA': function (e, value, row, index) { // 删除一个商品类别
				qiao.bs.confirm("确定删除该商品类别吗？",function(){
					var params = {
							id : row.id,
					};
					AjaxPostUtil.request({url:path+"/post/WechatProductTypeController/deleteProductType",params:params,type:'json',callback:function(json){
					if (json.returnCode == 0){
						qiao.bs.msg({msg:"删除成功！",type:'success'});
						setTimeout(refreshTable,1000);//一秒后刷新页面
					}else{
						qiao.bs.msg({msg:"删除失败！",type:'danger'});
					}
					}
					});
				},function(){});
			},
			'click .RoleOfB': function (e, value, row, index) { //编辑一个商品类别
				location.href = "updateProductType.html?id="+row.id;
			},
			'click .RoleOfC': function (e, value, row, index) { //查看一个商品类别
				$('#myModal2').modal('show');
				var params = {
						id:row.id
				};
				AjaxPostUtil.request({url:path+"/post/WechatProductTypeController/getProductTypeById",params:params,type:'json',callback:function(json){
					if (json.returnCode == 0){
						$('#typeName').html(json.bean.typeName);
						$('#typeDesc').html(json.bean.typeDesc);
						$('#createId').html(json.bean.adminNo);
						$('#createTime').html(json.bean.createTime);
						$("#imgPath").attr("src",path+"/"+json.bean.optionPath);
						var typeState = json.bean.typeState;
						if (typeState == 0)
							$('#typeState').html("创建");
						else if(typeState == 1)
							$('#typeState').html("审核中");
						else if (typeState == 2)
							$('#typeState').html("上线");
						else if (typeState == 3)
							$('#typeState').html("审核不通过");
					}else{
						qiao.bs.msg({msg:json.returnMessage,type:'danger'});
					}
				}});
			},
			'click .RoleOfD': function (e, value, row, index) { //提审一个商品类别
				qiao.bs.confirm("确定提审该商品类别吗？",function(){
					var params = {
							id:row.id
					};
					AjaxPostUtil.request({url:path+"/post/WechatProductTypeController/updateStateSubmit",params:params,type:'json',callback:function(json){
							if (json.returnCode == 0){
								//刷新表格
								qiao.bs.msg({msg:"正在进行提审，请耐心等待···",type:'success'});
								setTimeout(refreshTable,1000);//一秒后刷新页面
							}else{
								qiao.bs.msg({msg:"提交审核失败！",type:'danger'});
							}
					}});	
				},function(){});
			}
		};
function operateFormatter(value, row, index) {
	if (row.typeState == 0){
		return [
		        '<button type="button" class="RoleOfC btn btn-default  btn-sm" style="margin-right:15px;">详情</button>',
		        '<button type="button" class="RoleOfB btn btn-default  btn-sm" style="margin-right:15px;">编辑</button>',
				'<button type="button" class="RoleOfD btn btn-default  btn-sm" style="margin-right:15px;">提审</button>',
		        '<button type="button" class="RoleOfA btn btn-default  btn-sm" style="margin-right:15px;">删除</button>'
		        ].join('');
	}else if(row.typeState == 1) {
		return [
		        '<button type="button" class="RoleOfC btn btn-default  btn-sm" style="margin-right:15px;">详情</button>'
		        ].join('');
	}else {
		return [
		        '<button type="button" class="RoleOfC btn btn-default  btn-sm" style="margin-right:15px;">详情</button>',
		        '<button type="button" class="RoleOfB btn btn-default  btn-sm" style="margin-right:15px;">编辑</button>',
		        '<button type="button" class="RoleOfA btn btn-default  btn-sm" style="margin-right:15px;">删除</button>'
		        ].join('');
	}
};
//刷新表格
function refreshTable(){
	$('#massage').bootstrapTable('refresh',{url:path+'/post/WechatProductTypeController/getProductTypeList'});
}



