<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="java.net.InetAddress"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div class="panel panel-default" style="background-color: #FCFCFA;">
	<div class="panel-body">
		<p>
			<span class="glyphicon glyphicon-stats  ">系统使用统计</span>
		</p>
		<hr />
		<div class="panel panel-default" style="background-color: white;">
			<div class="panel-body">
				<div id="container">
					<div id="main1" style="width: 100%; height: 200px;"></div>
					<div id="main2" style="width: 100%; height: 200px;"></div>
					<div id="main3" style="width: 100%; height: 200px;"></div>
					<div id="main4" style="width: 100%; height: 200px;"></div> 
				</div>
			</div>
		</div>
	</div>
</div>
<script>
	// 第二个参数可以指定前面引入的主题
	function graphLine(domid,lable, titles, vals) {
		var chart = echarts.init(document.getElementById(domid));
		option = {
			tooltip : {
				trigger : 'axis',
				axisPointer : {
					type : 'cross',
					label : {
						backgroundColor : '#6a7985'
					}
				}
			},
			legend : {
				data : [ lable ]
			},
			toolbox : {
				feature : {
					saveAsImage : {}
				}
			},
			grid : {
				left : '10%', 
				right : '10%',
				bottom : '1%',
				containLabel : true
			},
			xAxis : [ {
				type : 'category',
				boundaryGap : false,
				data : titles
			} ],
			yAxis : [ {
				type : 'value'
			} ],
			series : [ {
				name : lable,
				type : 'line',
				stack : '总量',
				areaStyle : {
					normal : {}
				},
				data : vals
			} ]
		};
		chart.setOption(option);
	}

	$(function() {
		$.getJSON('webstat/PubStatAll.html', function(data) {
			var titles = new Array();
			var vals = new Array();
			$(data.nums.TotalNum).each(function(i, obj) {
				titles[i] = obj[0];
				vals[i] = obj[1];
			});
			graphLine('main1','知识总量', titles, vals);
			titles = new Array();
			vals = new Array();
			$(data.nums.GoodNum).each(function(i, obj) {
				titles[i] = obj[0];
				vals[i] = obj[1];
			});
			graphLine('main2','好评知识', titles, vals);
			titles = new Array();
			vals = new Array();
			$(data.nums.BadNum).each(function(i, obj) {
				titles[i] = obj[0];
				vals[i] = obj[1];
			});
			graphLine('main3','差评知识', titles, vals);
			titles = new Array();
			vals = new Array();
			$(data.nums.DayNum).each(function(i, obj) {
				titles[i] = obj[0];
				vals[i] = obj[1];
			});
			graphLine('main4','当日提交', titles, vals);
		});
	});
</script>