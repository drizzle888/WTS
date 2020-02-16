<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div id="frame_accordion" class="easyui-accordion"
	data-options="fit:true,border:false,animate:false">
	<c:forEach items="${menus}" var="node">
		<c:if test="${node.parentid=='NONE'}">
			<div title="${node.name}" data-options="iconCls:'${node.icon}'"
				style="overflow: auto; padding: 4px;">
				<div class="well well-sm">
					<ul class="easyui-tree" id="${node.id}"
						data-options="url:'actiontree/cuserMenus.do',lines:false,onBeforeLoad:loadLeftMenuTree,onClick:clickLeftMenuTree"></ul>
				</div>
			</div>
		</c:if>
	</c:forEach>
	
</div>
<script type="text/javascript">
	function loadLeftMenuTree(node, param) {
		param.domain = "alone";
		if (!param.id) {
			param.id = $(this).attr('id');
		}
	}
	function clickLeftMenuTree(node) {
		if (node) {
			if ($('#tt').tree('isLeaf', node.target)) {
				if (node.url) {
					if (node.para) {
						openUrlAtIfram(
								"admin/" + node.url + ".do?" + node.para,
								node.id, node.text, true);
					} else {
						openUrlAtIfram(node.url, node.id, node.text);
					}
				}
			}
		}
	}
</script>