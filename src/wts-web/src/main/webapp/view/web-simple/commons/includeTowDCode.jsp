<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="java.net.InetAddress"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<style>
/* tooltip */
#tooltip {
	position: absolute;
	border: 1px solid #fff;
	background: #fff;
	padding: 3px 3px 3px 3px;
	color: #333;
	display: none;
}
</style>
<div class="row">
	<div class="col-sm-12  visible-lg visible-md"
		style="text-align: center;">
		<div>
			<a class="towcodelink"> <img class="towcodeTooltipfalgOpposite"
				style="background-color: #ffffff;" src="text/img/towcode24.png" />
			</a>
		</div>
	</div>
</div>
<script>
	$(function() {
		var towcodelinkurl = location.href;
		$('.towcodelink').attr('href', towcodelinkurl);
		initTowCodeTooltip('.towcodeTooltipfalg',
				'<PF:basePath/>home/PubQRCode.do', -170, 20);
		initTowCodeTooltip('.towcodeTooltipfalgOpposite',
				'<PF:basePath/>home/PubQRCode.do', 0, -175);
	});
	function initTowCodeTooltip(objKey, imgurl, x, y) {
		$(objKey)
				.live(
						'mouseover',
						function(e) {
							this.imgTitle = this.title;
							this.title = "";
							var imgTitle = this.imgTitle ? "<br/>"
									+ this.imgTitle : "";
							var tooltip = "<div id='tooltip'><img style='max-width: 150px;' src='"+ imgurl +"' alt='预览图'/>"
									+ imgTitle + "<\/div>"; //创建 div 元素
							$("body").append(tooltip);
							$("#tooltip").css({
								"top" : (e.pageY + y) + "px",
								"left" : (e.pageX + x) + "px"
							}).show("fast"); //设置x坐标和y坐标，并且显示
						});

		$(objKey).live('mouseout', function(e) {
			this.title = this.imgTitle;
			$("#tooltip").remove();
		});
		$(objKey).live('mousemove', function(e) {
			$("#tooltip").css({
				"top" : (e.pageY + y) + "px",
				"left" : (e.pageX + x) + "px"
			});
		});
	}
</script>