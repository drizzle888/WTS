<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<select id='demoCatex'
	style="width: 100%; margin-bottom: 4px; background-color: #eeeeee;">
	<option value="">~选择公式模板~</option>
	<c:forEach items="${types }" var="node">
		<option value="${node.key}">${node.value}</option>
	</c:forEach>
</select>
<textarea id="codeId"
	style="width: 100%; background-color: #fffbd6; border: solid 1px #715100; padding: 4px;"></textarea>
<button style="width: 100%;" onclick="loadLatexImg()">解析公式</button>
<div id="imgDiv" style="width: 100%; min-height: 115px;">
	<div style="margin: auto;" id="latexloadTitleId">请输入公式脚本...</div>
	<img id='loadLatexImgId' style="max-width: 100%; max-height: 100px;"
		src="" id="" />
</div>
<button style="width: 100%;" id="insertLatexImg">插入公式</button>
<script>
	$('#demoCatex').on("change", function() {
		$('#codeId').val($('#demoCatex').val());
		loadLatexImg();
	})
	function loadLatexImg() {
		if ($('#codeId').val()) {
			$('#latexloadTitleId').text('公式解析中......');
			$.post('latex/valid.do', {
				latex : $('#codeId').val()
			}, function(flag) {
				if (flag.error == 0) {
					$('#latexloadTitleId').text('');
					$('#loadLatexImgId').attr(
							'src',
							"latex/view.do?latex="
									+ encodeURIComponent(encodeURIComponent($('#codeId').val())));
				} else {
					$('#latexloadTitleId').text(flag.message);
				}
			}, 'json');
		} else {
			$('#latexloadTitleId').text('请输入公式脚本...');
		}
	}
</script>