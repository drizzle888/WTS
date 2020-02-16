<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<h1>组织机构查询</h1>
<p class="protocol">${CURL}/get/organization.do</p>
<p class="lead">URL描述</p>
<h3>参数</h3>
<table class="table table-striped">
	<caption>如：/GET/organization.do?id=402888a84fcb6d88014fcb6f3e90000a</caption>
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
			<td>组织机构名称</td>
			<td class="demo">可以为空</td>
		</tr>
		<tr>
			<th scope="row">parentid</th>
			<td>上级机构ID</td>
			<td class="demo">可以为空</td>
		</tr>
		<tr>
			<th scope="row">appid</th>
			<td>业务关键字</td>
			<td class="demo">可以为空</td>
		</tr>
		<tr>
			<th scope="row">secret</th>
			<td>权限码</td>
			<td class="demo">必填，通过知识库配置文件预先配置</td>
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
			<th scope="row">STATE</th>
			<td>状态</td>
			<td>1可用，0禁用</td>
		</tr>
		<tr>
			<th scope="row">SORT</th>
			<td>排序号</td>
			<td>数字</td>
		</tr>
		<tr>
			<th scope="row">NAME</th>
			<td>机构名称</td>
			<td></td>
		</tr>
		<tr>
			<th scope="row">PARENTID</th>
			<td>上级机构ID</td>
			<td>32位UUID</td>
		</tr>
		<tr>
			<th scope="row">APPID</th>
			<td>业务关键字</td>
			<td></td>
		</tr>
	</tbody>
</table>