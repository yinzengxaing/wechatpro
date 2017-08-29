
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
		var city = $(this).html();
		location.href="restaurantList.html?city="+city;
	});
	
	$('body').on('click', '.letter a', function () {
		var s = $(this).html();
		$(window).scrollTop($('#' + s + '1').offset().top);
	});
}