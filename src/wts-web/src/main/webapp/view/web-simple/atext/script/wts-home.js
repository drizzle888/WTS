$(function(){
	$('.wts-hometype-ul li').click(function(){
		$(".wts-hometype-ul li").removeClass("wts-hometype-active");
		$(this).addClass("wts-hometype-active");
		$('.wts-subjects li').hide(); 
		$('#'+$(this).attr("targetId")).show();
	});
});