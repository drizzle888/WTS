<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<h1>分类查询</h1>
<p class="protocol">${CURL}/get/category.do</p>
<p class="lead">URL描述</p>
<h3>参数</h3>
<table class="table table-striped">
	<thead>
		<tr>
			<th>属性</th> 
			<th>描述</th>
			<th>备注</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<th scope="row">id</th>
			<td>主键</td>
			<td class="demo">可以为空</td>
		</tr>
		<tr>
			<th scope="row">name</th>
			<td>分类名称</td>
			<td class="demo">可以为空</td>
		</tr>
		<tr>
			<th scope="row">type</th>
			<td>类型</td>
			<td class="demo">可以为空，3知识，1结构</td>
		</tr>
		<tr>
			<th scope="row">state</th>
			<td>状态</td>
			<td class="demo">可以为空，0:禁用,1:可用</td>
		</tr>
		<tr>
			<th scope="row">parentid</th>
			<td>上层分类Id</td>
			<td class="demo">可以为空,一级分类的arentid为NONE</td>
		</tr>
		<tr>
			<th scope="row">secret</th>
			<td>权限码</td>
			<td class="demo">必填,通过知识库配置文件预先配置</td>
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
			<th scope="row">STATE</th>
			<td>状态</td>
			<td class="demo">0成功,1失败</td>
		</tr>
		<tr>
			<th scope="row">DATA.list</th>
			<td>数据</td>
			<td class="demo"></td>
		</tr>
		<tr>
			<th scope="row">DATA.totalsize</th>
			<td>记录总数</td>
			<td class="demo"></td>
		</tr>
		<tr>
			<th scope="row">MESSAGE</th>
			<td>错误信息</td>
			<td class="demo"></td>
		</tr>
	</tbody>
</table>
<h3>字段说明</h3>
<table class="table table-bordered table-striped">
	<caption>字段说明</caption>
	<thead>
		<tr>
			<th>字段名</th>
			<th>字段含义</th>
			<th>备注</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<th scope="row">ID</th>
			<td>主键</td>
			<td>32位UUID</td>
		</tr>
		<tr>
			<th scope="row">NAME</th>
			<td>分类名称</td>
			<td></td>
		</tr>
		<tr>
			<th scope="row">TYPE</th>
			<td>类型</td>
			<td>3知识,1结构</td>
		</tr>
		<tr>
			<th scope="row">STATE</th>
			<td>状态</td>
			<td>1可用，0禁用</td>
		</tr>
		<tr>
			<th scope="row">PARENTID</th>
			<td>上层分类id</td>
			<td>一级分类的上层节点为NONE</td>
		</tr>
	</tbody>
</table>