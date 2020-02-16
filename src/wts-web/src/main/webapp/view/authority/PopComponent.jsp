<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<script type="text/javascript">
	//下载权限
	function openPopWindow(targetIds,targetType,windowTitle) {
		//ids,'设置显示权限'
		var url = 'popcom/setingPage.do?targetIds='+ targetIds+'&targetType='+targetType;
		$.farm.openWindow({
			id : 'winPopWindow',
			width : 600,
			height : 500,
			modal : true,
			url : url,
			title : windowTitle
		});
	}
</script>