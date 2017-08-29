
$(function(e){
	dataInit();
});

function dataInit(){
	$('.container').show();
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
		var type = $('.container').data('type');
		AjaxPostUtil.request({url:path+"/gateway/EmpowerWebpageController/updateSession",params:{Location:$(this).html()},type:'json',callback:function(json){
			if(json.returnCode==0){
				location.href="myMation.html";
			}else{
				location.href = 'sessionNull.html';
			}
		}});
	});
	$('body').on('click', '.letter a', function () {
		var s = $(this).html();
		$(window).scrollTop($('#' + s + '1').offset().top);
	});
	
	$('body').on('click', '#imgback', function(){
		location.href = "myMation.html";
	});
}