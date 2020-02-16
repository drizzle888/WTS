<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--答题卡表单-->
<div class="easyui-layout" data-options="fit:true">
	<div class="TableTitle" data-options="region:'north',border:false">
		<span class="label label-primary">错误提示 </span>
	</div>
	<div data-options="region:'center'">
		<div
			style="border: 1px dashed red; width: 300px; color: red; text-align: center; padding: 20px; margin: auto; margin-top: 100px;">
			${MESSAGE}</div>
	</div>
</div>
