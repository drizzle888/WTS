<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--答题表单-->
<div class="easyui-layout" data-options="fit:true">
	<div class="TableTitle" data-options="region:'north',border:false">
		<span class="label label-primary">房间权限</span>
	</div>
	<div data-options="region:'center'">
		<div class="easyui-tabs" data-options="fit:true,border:false">
			<div title="答题人员" style="padding: 20px;">
				<table class="easyui-datagrid">
					<thead>
						<tr>
							<th data-options="field:'code'" width="33%">姓名</th>
							<th data-options="field:'name'" width="33%">组织机构</th>
							<th data-options="field:'room'" width="33%">答题室</th>
						</tr>
					</thead>
					<tbody>
						<!-- USERID,USERNAME,ORGID,ORGNAME,ROOMID,ROOMNAME -->
						<c:forEach items="${examUsers}" var="node">
							<tr>
								<td>${node.USERNAME }</td>
								<td>${node.ORGNAME }</td>
								<td>${node.ROOMNAME }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div title="阅卷人员" style="padding: 20px;">
				<table class="easyui-datagrid">
					<thead>
						<tr>
							<th data-options="field:'code'" width="33%">姓名</th>
							<th data-options="field:'name'" width="33%">组织机构</th>
							<th data-options="field:'type'" width="33%">业务分类</th>
						</tr>
					</thead>
					<tbody>
						<!-- USERID,USERNAME,ORGID,ORGNAME,ROOMID,ROOMNAME -->
						<c:forEach items="${adjudgeUsers}" var="node">
							<tr>
								<td>${node.USERNAME }</td>
								<td>${node.ORGNAME }</td>
								<td>${node.TYPENAME }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div title="管理人员" style="padding: 20px;">
				<table class="easyui-datagrid">
					<thead>
						<tr>
							<th data-options="field:'code'" width="33%">姓名</th>
							<th data-options="field:'name'" width="33%">组织机构</th>
							<th data-options="field:'type'" width="33%">业务分类</th>
						</tr>
					</thead>
					<tbody>
						<!-- USERID,USERNAME,ORGID,ORGNAME,ROOMID,ROOMNAME -->
						<c:forEach items="${mngUsers}" var="node">
							<tr>
								<td>${node.USERNAME }</td>
								<td>${node.ORGNAME }</td>
								<td>${node.TYPENAME }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<a id="dom_cancle_formpop" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function() {
		//关闭窗口
		$('#dom_cancle_formpop').bind('click', function() {
			$('#winPopRoom').window('close');
		});
	});
//-->
</script>