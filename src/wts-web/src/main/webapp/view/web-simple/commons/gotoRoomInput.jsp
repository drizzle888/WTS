<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div class="row" style="margin: 20px;">
	<div class="col-lg-4"></div>
	<div class="col-lg-4">
		<div class="input-group">
			<input type="text" class="form-control" id="gotoRoomInputId"
				style="background-color: #f9f9f9" placeholder="请录入答题室ID...">
			<div class="input-group-btn">
				<button type="button" onclick="gotoRoom()" class="btn btn-default">进入答题室</button>
				<button type="button" class="btn btn-default dropdown-toggle"
					data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					<span class="caret"></span> <span class="sr-only"> 开始阅卷</span>
				</button>
				<ul class="dropdown-menu dropdown-menu-right">
					<li><a href="javascript:gotoAdjudge()">进入阅卷</a></li>
					<!-- <li role="separator" class="divider"></li>
					<li><a href="#">Separated link</a></li> -->
				</ul>
			</div>
		</div>
	</div>
	<div class="col-lg-4"></div>
</div>
<script type="text/javascript">
	function gotoRoom() {
		if ($('#gotoRoomInputId').val().trim()) {
			window.location = "<PF:basePath/>exam/roompage.do?roomid="
					+ $('#gotoRoomInputId').val().trim();
		}
	}
	function gotoAdjudge() {
		if ($('#gotoRoomInputId').val().trim()) {
			window.location = "<PF:basePath/>adjudge/roompage.do?roomid="
					+ $('#gotoRoomInputId').val().trim();
		}
	}
</script>