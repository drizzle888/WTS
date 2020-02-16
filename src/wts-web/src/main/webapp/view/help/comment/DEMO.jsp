<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<h1>功能名称</h1>
<p class="protocol">${CURL}/[URL].do</p> 
<p class="lead">URL描述</p>
<h3>参数</h3>
<table class="table table-striped">
	<caption></caption>
	<thead>
		<tr>
			<th>属性</th>
			<th>描述</th>
			<th>例子</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<th scope="row">[名称]</th>
			<td>[描述]</td>
			<td class="demo">[例子]</td>
		</tr>
	</tbody>
</table>
<h3>返回值</h3>
<table class="table table-striped">
	<caption></caption>
	<thead>
		<tr>
			<th>参数</th>
			<th>值</th>
			<th>例子</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<th scope="row">[名称]</th>
			<td>[描述]</td>
			<td class="demo">[例子]</td>
		</tr>
	</tbody>
</table>
<h3>字段说明</h3>
<table class="table table-bordered table-striped">
	<caption></caption>
	<thead>
		<tr>
			<th>字段名</th>
			<th>字段含义</th>
			<th>备注</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<th scope="row">[名称]</th>
			<td>[描述]</td>
			<td>[备注]</td>
		</tr>
	</tbody>
</table>