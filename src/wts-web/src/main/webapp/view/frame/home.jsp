<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html>
<head>
<title><PF:ParameterValue key="config.sys.title" /></title>
<jsp:include page="/view/conf/include.jsp"></jsp:include>
<script type="text/javascript" src="text/javascript/echarts.min.js"></script>
</head>
<body class="easyui-layout">
	<div
		style="text-align: right; color: #000000; background: #fff; padding: 20px; height: 100%;">
		<table style="width: 100%;">
			<tr>
				<td style="text-align: center;"><img alt=""
					style="max-width: 100%; max-height: 500px; margin: auto;"
					src="text/img/flow.png"></td>
			</tr>
		</table>
	</div>
</body>
<script>
	// 第二个参数可以指定前面引入的主题
	//var chart = echarts.init(document.getElementById('main'));

	var option = {
		title : {
			text : '知识库用量统计'
		},
		tooltip : {
			trigger : 'axis',
			axisPointer : { // 坐标轴指示器，坐标轴触发有效
				type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
			},
			formatter : function(params) {
				var tar = params[0];
				return tar.name + '<br/>' + tar.seriesName + ' : ' + tar.value;
			}
		},
		grid : {
			left : '3%',
			right : '4%',
			bottom : '3%',
			containLabel : true
		},
		xAxis : {
			type : 'category',

			splitLine : {
				show : true
			},
			data : [ '类别数', '总知识数', '总人数', '好评文档数', '差评文档数', '全部问题', '完成问题',
					'待答回答' ]
		},
		yAxis : {
			type : 'value'
		},
		series : [ {
			name : '数量',
			type : 'bar',
			stack : '总量',
			label : {
				normal : {
					show : true,
					position : 'top'
				}
			},
			itemStyle : {
				normal : {
					color : '#1c6d9f'
				},
				emphasis : {
					color : '#1c6d9f'
				}
			},
			data : [ '${STAT.TYPESNUM}', '${STAT.KNOWNUM}', '${STAT.USERNUM}',
					'${STAT.GOODKNOWNUM}', '${STAT.BADKNOWNUM}',
					'${STAT.ALLQUESTION}', '${STAT.FINISHQUESTION}',
					'${STAT.WAITINGQUESTION}' ]
		} ]
	};
	//chart.setOption(option);
</script>
</html>