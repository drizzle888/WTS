<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<style>
<!--
.demo-info {
	background: #FFFEE6;
	color: #8F5700;
	padding: 12px;
	border: 1px dotted #ccc;
}

.demo-tip {
	width: 16px;
	height: 16px;
	margin-right: 8px;
	float: left;
}
-->
</style>
<div class="demo-info">
	<div class="demo-tip icon-tip"></div>
	<div>系统中的可用服务,可以在debug模式下对系统进行调试</div>
</div>
<ul class="easyui-tree" id="serverUrl_tree_id" style="margin: 8px;"
	data-options="url:'frame/service.do'"></ul>
<script type="text/javascript">
	$('#serverUrl_tree_id').tree(
			{
				onClick : function(node) {
					if ($('#serverUrl_tree_id').tree('isLeaf', node.target)) {
						openUrlAtIfram(node.text, node.text.replace("/", "-"),
								node.text);
					}
				},
				onLoadSuccess : function(node) {
					$('#serverUrl_tree_id').tree('collapseAll');
				}
			});
//-->
</script>