
var typeId = null;

$(function(e){
	receiveData();
	phoneOneDataInit();
});

function phoneOneDataInit(){
	typeId = $.req("typeId");
	$(".tab-bottom").load("common/buttomMenu.html",function(e){
		if(typeId==null){
			$(".tab-button").removeClass("active").addClass("cached");
			$(".tab-button:eq(0)").removeClass("cached").addClass("active");
		}else{
			if(typeId=="1"){
				$(".tab-button").removeClass("active").addClass("cached");
				$(".tab-button:eq(0)").removeClass("cached").addClass("active");
			}else if(typeId=="2"){
				$(".tab-button").removeClass("active").addClass("cached");
				$(".tab-button:eq(1)").removeClass("cached").addClass("active");
			}else if(typeId=="3"){
				$(".tab-button").removeClass("active").addClass("cached");
				$(".tab-button:eq(2)").removeClass("cached").addClass("active");
			}else if(typeId=="5"){
				$(".tab-button").removeClass("active").addClass("cached");
				$(".tab-button:eq(4)").removeClass("cached").addClass("active");
			}
		}
		phoneOneEventInit();
	});
}

function phoneOneEventInit(){
	
	$('body').on('click', '.tab-button', function(e){
		$(".tab-button").removeClass("active").addClass("cached");
		$(this).removeClass("cached").addClass("active");
		var url = $(this).attr("rowHref") + "?typeId=" + $(this).attr("typeId");
		location.href = url;
	});
	
}
