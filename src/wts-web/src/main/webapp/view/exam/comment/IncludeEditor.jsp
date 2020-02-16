<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet" type="text/css"
	href="text/lib/kindeditor/editInner.css">
<style>
.MediaEditorButtons {
	height: 20px;
	background-color: #90cef4;
	text-align: center;
	cursor: pointer;
	color: #ffffff;
	padding-top: 1px;
	margin-bottom: 4px;
}

.MediaEditorButtons:HOVER {
	background-color: #3b9ad5;
}

.MediaEditorButtonsBox {
	border: dashed 1px #eeeeee;
	background-color: #ffffff;
}

.MediaEditorButtonsBox:HOVER {
	border: dashed 1px #90cef4;
}
</style>
<div class="MediaEditorButtonsBox">
	<c:if test="${param.type!='0' }">
		<div class="MediaEditorButtons" id="${param.fieldId}_editor_button">点击编辑&nbsp;${param.fieldTitle}</div>
	</c:if>
	<div class="ke-content" id="${param.fieldId}_view"></div>
</div>
<script type="text/javascript">
	var setEditorHtmlFunction;
	var getEditorHtmlFunction;
	var showEditorHtmlFunction;
	$(function() {
		$('#${param.fieldId}_editor_button').click(function() {
			$.farm.openWindow({
				id : 'mediaEditorWin',
				width : 800,
				height : 500,
				modal : true,
				url : "utils/editor.do",
				title : '${param.fieldTitle}'
			});
			setEditorHtmlFunction = function(html) {
				$('#${param.fieldId}').val(html);
			};
			showEditorHtmlFunction = function(html) {
				initTextHtml(html);
			};
			getEditorHtmlFunction = function() {
				return $('#${param.fieldId}').val();
			};
		});
		initTextHtml($('#${param.fieldId}').val());
	});
	function initTextHtml(html) {
		$('#${param.fieldId}_view').html(html);
		$.post("utils/formatHtmlTag.do", {
			'html' : html
		}, function(flag) {
			$('#${param.fieldId}_view').html(flag.html);
		}, 'json');
	}
</script>