<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="java.net.InetAddress"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div class="panel panel-default" style="background-color: #FCFCFA;min-height: 460px;">
	<div class="panel-body">
		<p>
			<span class="glyphicon glyphicon-question-sign">最热的已完成问答</span>
		</p>
		<table class="table" style="font-size: 12px;">
			<tr>
				<th width="60">排名</th>
				<th>标题</th>
				<th width="150">所有者</th>
			</tr>
			<c:forEach items="${finishQuestion.resultList}" varStatus="status"
				var="node">
				<tr>
					<td>${status.index+1}</td>
					<td style="overflow: hidden; max-width: 200px;"><a
						href="webquest/fqa/Pub${node.ID}.html"> ${node.TITLE}</a></td>
					<td><span class="glyphicon glyphicon-user">
							${node.CUSERNAME} </span></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>