<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html >
<html>
<head>
<base href="<PF:basePath/>">
<title><PF:ParameterValue key="config.sys.title" /></title>
<jsp:include page="/view/conf/include.jsp"></jsp:include>
<script type="text/javascript"
	src="WEB-FACE/model/easy_ui_1_3_6/jquery.portal.js"></script>
<link href='WEB-FACE/model/fullcalendar/fullcalendar.min.css'
	rel='stylesheet' />
<script src='WEB-FACE/model/fullcalendar/moment.min.js'></script>
<script src='WEB-FACE/model/fullcalendar/fullcalendar.min.js'></script>
<script src='WEB-FACE/model/fullcalendar/zh-cn.js'></script>
<script type="text/javascript" src="PLUGIN/plan/js/TaskResult.js"></script>
<style>
<!--
.portal {
	padding: 0;
	margin: 0;
	border: 1px solid #99BBE8;
	overflow: auto;
}

.portal-noborder {
	border: 0;
}

.portal-panel {
	margin-bottom: 10px;
}

.portal-column-td {
	vertical-align: top;
}

.portal-column {
	padding: 10px 0 10px 10px;
	overflow: hidden;
}

.portal-column-left {
	padding-left: 10px;
}

.portal-column-right {
	padding-right: 10px;
}

.portal-proxy {
	opacity: 0.6;
	filter: alpha(opacity =              
		                                                         
		                60);
}

.portal-spacer {
	border: 3px dashed #eee;
	margin-bottom: 10px;
}
-->
</style>
</head>
<body class="easyui-layout">
	<div style="display: none;">
		<div id="win" style="padding: 8px; text-align: center;">
			<c:forEach items="${portal.componentdefs}" var="node">
				<a id="${node.id}" class="easyui-linkbutton compButton"
					style="margin: 4px;"
					data-options="iconCls:'${node.component.iconclass}',toggle:true,selected:${node.openable=='1'?'true':'false'}">${node.node.name}</a>
				<br />
			</c:forEach>
		</div>
	</div>
	<div region="center" border="false"
		style="background-image: url('WEB-FACE/img/style/macback.png')">
		<div id="pp">
			<c:forEach items="${portal.widths}" var="node">
				<div style="width: ${node};"></div>
			</c:forEach>
		</div>
	</div>
</body>
<!-- 初始化门户 -->
<script type="text/javascript">
	setTimeout("$.parser.parse();",500);
	$('#pp').portal( {
		fit : true,
		border : false,
		onStateChange : function(flag) {
			var panals = $(this).portal('getPanels', 1);
		}
	});
	function addComponent(id,titlestr,hetghtInt,closableStr,collapsibleStr,iconclsStr,url,index){
		var comp = $('<div id="'+id+'"></div>').appendTo('body');
		comp.panel( {
			title : titlestr,
			height : hetghtInt,
			closable : closableStr,
			collapsible : collapsibleStr,
			iconCls:iconclsStr
		});
		$('#pp').portal('disableDragging',comp);
		$(comp).text('loading...');
		$(comp).load(url,{});
		$('#pp').portal('add', {
			panel : comp,
			columnIndex : index
		});
	}
	function removeComponent(id){
		var panel=$('#'+id).panel();
		$('#pp').portal('remove', panel);
	}
</script>
<c:forEach items="${portal.componentdefs}" var="node">
	<c:if test="${node.openable=='1'}">
		<!-- 加载门户组件 -->
		<script type="text/javascript">
	addComponent('${node.id}','${node.node.name}',${node.heitgh},${node.closable=='1'?'true':'false'},${node.collapsible=='1'?'true':'false'},'${node.component.iconclass}','${node.component.url}',<fmt:formatNumber type="number" value="${(node.node.sort)/10}" maxFractionDigits="0"/>);
	</script>
	</c:if>
</c:forEach>
<!-- 初始化设置按钮 -->
<script type="text/javascript">
	var setingBut= $('<div style="background-color: transparent"><a id="settingbtn" href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-settings\'"></a>  </div>').appendTo('body');
	setingBut.panel( {
		height : 30,
		border:false
	});
	$('#settingbtn').bind('click',function(){
		$('#win').window({    
		    width:200,    
		    height:400,   
		    border:false, 
		    title:'组件设置',
		    modal:true,
		    resizable:false,
		    draggable:false,
		    closable:true,
		    maximizable:false,
		    minimizable:false,
		    collapsible:false,
		    iconCls:'icon-settings'   
		});  
	});
	$('.compButton').bind('click',function(){
		$.messager.progress({interval:100,text:'loading...'}); 
		if($(this).linkbutton('options').selected){
		//取消选中
			$.post('admin/FarmConsoleComponentSet.do',{id:$(this).attr('id'),'componentExcute.closeis':'0'},function(flag){
				removeComponent(flag.id);
				$.messager.progress('close');
			});
		}else{
		//选中
			$.post('admin/FarmConsoleComponentSet.do',{id:$(this).attr('id'),'componentExcute.closeis':'1'},function(flag){
				addComponent(flag.id,flag.node.name,flag.height,flag.closable=='0'?false:true,flag.collapsible=='0'?false:true,flag.component.iconclass,flag.component.url,parseInt(flag.node.sort/10));
				$.messager.progress('close');
			});
		}
	});
</script>
</html>