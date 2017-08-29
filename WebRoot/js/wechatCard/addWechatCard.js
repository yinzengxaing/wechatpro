

$(function(e){
	dataInit();
});

function dataInit(){
	
	eventInit();
}

function eventInit(){
	$('body').on('click', '#yesBean', function(e){
		location.href = "addWechatCardMessage.html";
	});	
}