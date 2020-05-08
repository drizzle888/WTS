<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!-- 判卷时通过点击正确答案弹出 题解析 -->
<script type="text/javascript">
	$(function() {
		//綁定查看題解析的事件
		initAnswerRightViewBox();
	});
	//綁定查看題解析的事件
	function initAnswerRightViewBox() {
		$('.answerRightViewBox').css("cursor", "pointer");
		$(".answerRightViewBox").mouseover(function() {
			$(this).addClass("answerRightHover");
		}).mouseout(function() {
			$(this).removeClass("answerRightHover");
		})
		$('.answerRightViewBox').attr("title", "点击查看题解析");
		$('.answerRightViewBox').click(function() {
			$('#subjectInfo-win').modal({
				keyboard : false
			})
			$('#subjectInfoLoader').html('<center>loading...</center>');
			$('#subjectInfoLoader').load('adjudge/loadSubjectinfo.do', {
				'subjectId' : $(this).attr('subjectId')
			}, function() {

			});
		});
	}
</script>
<!-- 提交阅卷結果確認窗口  -->
<div class="modal fade" id="subjectInfo-win" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">题解析</h4>
			</div>
			<div class="modal-body" style="padding: 0px;">
				<div id="subjectInfoLoader" style="margin: -24px;font-size: 12px;"></div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>