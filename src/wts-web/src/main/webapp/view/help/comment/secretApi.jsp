<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<h1>权限码secret</h1>
<p class="lead">secret权限码，用于接口调用时验明权限。secret的计算方法如下：</p>
<p class="lead" style="color: red;">
	<span style="color: gray; font-size: 14px;"> UTC时间的毫秒数除以一万拼接上安全码，再做MD5编码 </span>
	<br />
	secret=MD5( (UTC / 10000) + SECRET_KEY);
</p> 
<p class="lead" style="color: gray; font-size: 14px;"><b>SECRET_KEY:</b> 在配置文件中配置的一组随机数密码，本系统中为 <PF:ParameterValue key="config.restful.secret.key"/></p>
<p class="lead" style="color: gray; font-size: 14px;"><b>UTC:</b> 为协调世界时，又称世界统一时间、世界标准时间、国际协调时间。可以通过下方接口URL获取</p>
<hr/>
<h2>获得服务器UTC时间</h2>
<p class="protocol">${CURL}/secret/utc.do</p>
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
			<th scope="row">UTC</th>
			<td>UTC时间</td>
			<td class="demo"></td>
		</tr>
		<tr>
			<th scope="row">MESSAGE</th>
			<td>错误信息</td>
			<td class="demo"></td>
		</tr>
	</tbody>
</table>