
$(function(e){
	dataInit();
});

function dataInit(){
	$('.container').show();
	
	AjaxPostUtil.request({url:path+"/gateway/WechatCityController/queryAllCity",params:{},type:'json',callback:function(json){
		if(json.returnCode==0){
			console.log(json);
			Handlebars.registerHelper("compare1", function(v1,v2,options){
				if(v1==null||v1==""){
					return options.fn(this);
				}else{
					return options.inverse(this);
				}
			});
			var source = $("#selectCity").html();  
		    var template = Handlebars.compile(source);
		    $("#cityDiv").html($("#cityDiv").html()+template(json));
//		    $.sidebarMenu($('#cityDiv'));
		}else{
			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
		}
	}});
	eventInit();
}

function eventInit(){
	//加载城市事件
	$('body').on('click', '#zone_ids,#gr_zone_ids', function () {
		var zid = $(this).attr('id');
		$('.container').show();
	});
	//选择城市 start
	$('body').on('click', '.city-list p', function () {
		var city = $(this).html();
		location.href="restaurantList.html?city="+city;
	});
	
	$('body').on('click', '.letter a', function () {
		var s = $(this).html();
		$(window).scrollTop($('#' + s + '1').offset().top);
	});
}