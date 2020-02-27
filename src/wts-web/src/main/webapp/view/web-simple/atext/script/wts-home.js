$(function(){
	//pc端
	$('.wts-hometype-ul li').click(function(){
		$(".wts-hometype-ul li").removeClass("wts-hometype-active");
		$(this).addClass("wts-hometype-active");
		$('.wts-subjects li').hide(); 
		$('#'+$(this).attr("targetId")).show();
	});
	//移动端
	$('.wts-mobile-hometype-ul li').click(function(){
		$('.wts-subjects li').hide(); 
		$('#'+$(this).attr("targetId")).show();
		$(".wts-mobile-hometype-ul li").removeClass("active");
		$(this).addClass("active");
	});
});