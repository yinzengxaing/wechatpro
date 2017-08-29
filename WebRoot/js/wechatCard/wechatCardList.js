

$(function(e){
	dataInit();
});

function dataInit(){
	
	eventInit();
}

function eventInit(){
	$('body').on('click', '#addCard', function(e){
		location.href = "addWechatCard.html";
	});	
}