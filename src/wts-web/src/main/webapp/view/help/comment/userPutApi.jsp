<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<h1>更新用户</h1>
<p class="protocol">${CURL}/put/user.do</p> 
<p class="lead">通过post方式提交</p>
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
			<th scope="row">id</th>
			<td>用户ID</td>
			<td class="demo">必填</td>
		</tr>
		<tr>
			<th scope="row">name</th>
			<td>用户姓名</td>
			<td class="demo">可以为空</td>
		</tr>
		<tr>
			<th scope="row">loginname</th>
			<td>登录名</td>
			<td class="demo">可以为空</td>
		</tr>
		<tr>
			<th scope="row">comments</th>
			<td>备注</td>
			<td class="demo">可以为空</td>
		</tr>
		<tr>
			<th scope="row">orgid</th>
			<td>组织机构id</td>
			<td class="demo">可以为空</td>
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
			<th scope="row">MESSAGE</th>
			<td>错误信息</td>
			<td class="demo"></td>
		</tr>
	</tbody>
</table>