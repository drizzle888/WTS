<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun"%>
<div class="row">
	<div class="col-md-12">
		<hr class="hr_split" />
		<form role="form" id="knowSubmitFormId" class="wcp_noEnterSubmit"
			method="post">
			<input type="hidden" name="subjectid" value="${subjectu.subject.id}" />
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<c:if test="${USEROBJ==null}">
							<textarea class="form-control" id="mesTextId" name="text"
								disabled="disabled" placeholder="请登陆后发表评论"></textarea>
						</c:if>
						<c:if test="${USEROBJ!=null}">
							<textarea class="form-control" id="mesTextId" name="text"
								style="border-color: #377bb5;" placeholder="请输入评论内容"></textarea>
						</c:if>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12 text-center">
					<c:if test="${USEROBJ==null}">
						<a href="login/webPage.html" class="btn btn-primary">登陆后发表评论</a>
					</c:if>
					<c:if test="${USEROBJ!=null}">
						<button type="button" id="msgSubmitButtonId"
							class="btn btn-primary btn-lg">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发表评论&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</button>
					</c:if>
				</div>
			</div>
		</form>
	</div>
</div>
<script type="text/javascript">
	function delComment(commentId) {
		if (confirm("是否刪除此评论?")) {
			$.post('websubject/delComment.do', {
				'id' : commentId
			}, function(flag) {
				if (flag.STATE == 0) {
					openCommentWin();
					$('#commentNumId').text(flag.num);
				} else {
					alert(flag.MESSAGE);
				}
			}, 'json');
		}
	}
	$(function() {
		$('#msgSubmitButtonId').bind('click', function() {
			if (validate('knowSubmitFormId')) {
				if (confirm("是否提交评论?")) {
					$.post('websubject/addComment.do', {
						'subjectid' : '${subjectu.subject.id}',
						'text' : $('#mesTextId').val()
					}, function(flag) {
						if (flag.STATE == 0) {
							openCommentWin();
							$('#commentNumId').text(flag.num);
						} else {
							alert(flag.MESSAGE);
						}
					}, 'json');
				}
			}
		});
		validateInput('mesTextId', function(id, val, obj) {
			// 留言
			if (valid_isNull(val)) {
				return {
					valid : false,
					msg : '评论内容不能为空'
				};
			}
			if (valid_maxLength(val, 1024)) {
				return {
					valid : false,
					msg : '长度不能大于' + 1024
				};
			}
			return {
				valid : true,
				msg : '正确'
			};
		});
	});
</script>