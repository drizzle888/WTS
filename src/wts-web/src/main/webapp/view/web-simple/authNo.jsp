<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<title>权限受限-<PF:ParameterValue key="config.sys.title" />
</title>
<meta name="description"
	content='<PF:ParameterValue key="config.sys.mate.description"/>'>
<meta name="keywords"
	content='<PF:ParameterValue key="config.sys.mate.keywords"/>'>
<meta name="author"
	content='<PF:ParameterValue key="config.sys.mate.author"/>'>
<meta name="robots" content="noindex,nofllow">
<jsp:include page="atext/include-web.jsp"></jsp:include>
</head>
<body>
	<div class="modal fade bs-example-modal-lg" tabindex="-1"
		data-backdrop="true" data-show="true" data-keyboard="false"
		id="authNoModalId" role="dialog" aria-labelledby="myLargeModalLabel"
		aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content" style="padding: 20px;">
				<div class="alert alert-danger" role="alert">
					${pageset.message}</div>
				<p style="text-align: center;">
					<a href="<PF:basePath/>" class="btn btn-primary btn-xs">
						返回首页... </a>
				</p>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function() {
		$('#authNoModalId').modal('show');
		$('#authNoModalId').on('hidden.bs.modal', function(e) {
			window.location = "<PF:basePath/>";
		})
	});
//-->
</script>
</html>