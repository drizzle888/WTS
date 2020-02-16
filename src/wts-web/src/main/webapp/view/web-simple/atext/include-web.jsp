<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<base href="<PF:basePath/>">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1,user-scalable=no" />
<link rel="icon" href="favicon.ico" mce_href="favicon.ico"
	type="image/x-icon">
<link rel="shortcut icon" href="favicon.ico" mce_href="favicon.ico"
	type="image/x-icon">
<script type="text/javascript" src="text/javascript/jquery-1.8.0.min.js"></script>
<link href="text/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="text/lib/bootstrap/css/bootstrap-theme.min.css"
	rel="stylesheet">
<script src="text/lib/bootstrap/js/bootstrap.min.js"></script>
<script src="text/lib/bootstrap/respond.min.js"></script>
<link href="view/web-simple/atext/style/web-base.css" rel="stylesheet">
<link href="view/web-simple/atext/style/web-black.css" rel="stylesheet">
<link href="view/web-simple/atext/style/wts-app.css" rel="stylesheet">
<link href="text/lib/kindeditor/editInner.css" rel="stylesheet">
<script type="text/javascript">
	var basePath = '<PF:basePath/>';
	$(function() {
		$.ajaxSetup({
			cache : false
		});
	})
	//ajax执行远程命令
	function loadRemoteFunction(url, domid) {
		$('#' + domid).attr('disabled', 'disabled');
		$.post(url, {}, function(flag) {
			if (flag.STATE == '0') {
				alert("操作执行完班!");
			} else {
				alert(flag.MESSAGE);
			}
			$('#' + domid).removeAttr("disabled");
		}, 'json');
	}
	//ajax执行远程命令
	function loadRemoteFunctionAndReload(url, domid,mgs) {
		if (mgs) {
			if (confirm(mgs)) {
				doRemoteFunctionAndReload(url, domid, mgs);
			}
		} else {
			doRemoteFunctionAndReload(url, domid, mgs);
		}
	}
	//ajax执行远程命令-do
	function doRemoteFunctionAndReload(url, domid,mgs){
		$('#' + domid).attr('disabled', 'disabled');
		$.post(url, {}, function(flag) {
			if (flag.STATE == '0') {
				location.reload();
			} else {
				alert(flag.MESSAGE);
			}
			$('#' + domid).removeAttr("disabled");
		}, 'json');
	}
	
	
	//confirm访问后台服务
	function confirmRemoteFunction(url,mgs) {
		if (confirm(mgs)) {
			window.location = basePath + url;
		}
	}
</script>