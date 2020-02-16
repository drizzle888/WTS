<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<base href="<PF:basePath/>">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="Cache-Control" content="no-store,must-revalidate">
<jsp:include page="settings.jsp"></jsp:include>
<!-- black,bootstrap,default,gray,metro  -->
<link rel="stylesheet" type="text/css"
	href="text/lib/easyui/themes/gray/base.css">
<link rel="stylesheet" type="text/css"
	href="text/lib/easyui/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css"
	href="text/lib/easyui/themes/icon.css">
<link rel="icon" href="favicon.ico" mce_href="favicon.ico"
	type="image/x-icon">
<link rel="shortcut icon" href="favicon.ico" mce_href="favicon.ico"
	type="image/x-icon">
<script type="text/javascript"
	src="text/javascript/jquery-1.11.3.min.js"></script>
<script type="text/javascript"
	src="text/lib/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="text/lib/easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="text/lib/easyui/easyui.farm.js"></script>
<script type="text/javascript"
	src="text/lib/easyui/jquery.validate.exp.js"></script>
<script type="text/javascript">
	var basePath = '<PF:basePath/>';
	$(function() {
		$.ajaxSetup({
			cache : false
		});
		setTimeout(function() {
			try {
				parent.window.updateIframe();
			} catch (e) {
			}
		}, 100);
	})
//-->
</script>