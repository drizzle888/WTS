<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.core.time.TimeTool"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<%
	String year = TimeTool.getFormatTimeDate12(
			TimeTool.getTimeDate12(), "yyyy");
	int yearInt = Integer.valueOf(year);
	int cyear = yearInt - 10;
%>
<select id="yearid">
	<option value="*">每</option>
	<%
		while (cyear < yearInt + 10) {
	%>
	<option value="<%=cyear%>">
		<%=cyear%>
	</option>
	<%
		cyear++;
		}
	%>
</select>
年&nbsp;&nbsp;
<select id="monthid">
	<option value="*">每</option>
	<%
		int cmonth = 1;
		while (cmonth <= 12) {
	%>
	<option value="<%=cmonth%>">
		<%=cmonth%>
	</option>
	<%
		cmonth++;
		}
	%>
</select>
月&nbsp;&nbsp;
<select id="dayid">
	<option value="*">每</option>
	<%
		int cday = 1;
		while (cday <= 30) {
	%>
	<option value="<%=cday%>">
		<%=cday%>
	</option>
	<%
		cday++;
		}
	%>
	<option value="L">最后一日</option>
</select>
日&nbsp;&nbsp;
<select id="hourid">
	<%
		int chour = 0;
		while (chour <= 23) {
	%>
	<option value="<%=chour%>">
		<%=chour%>
	</option>
	<%
		chour++;
		}
	%>
	<option value="*">每</option>
</select>
时&nbsp;&nbsp;
<select id="minuteid">
	<%
		int cminute = 0;
		while (cminute <= 59) {
	%>
	<option value="<%=cminute%>">
		<%=cminute%>
	</option>
	<%
		cminute++;
		}
	%>
	<option value="*">每</option>
</select>
分&nbsp;&nbsp;
<select id="secondid">
	<%
		int csecond = 0;
		while (csecond <= 59) {
	%>
	<option value="<%=csecond%>">
		<%=csecond%>
	</option>
	<%
		csecond++;
		}
	%>
	<option value="*">每</option>
</select>
秒
