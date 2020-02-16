<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html>
<head>
<title><PF:ParameterValue key="config.sys.title" /></title>
<jsp:include page="/view/conf/include.jsp"></jsp:include>
</head>
<body class="easyui-layout">
	<div id="loadInitFrameIndex"
		style="vertical-align: middle; text-align: center; padding-top: 25px; height: 2000px;">
		<img style="margin-top: 50px;" alt="" src="text/img/loading.gif">
		<!-- <img align="middle" src="WEB-FACE/img/style/loading.gif" />
			<span style="font-size: 14px; color: #102947;">&nbsp;加载中...</span> -->
	</div>
	<div data-options="region:'north',border:false"
		style="height: 40px; border: 0px; padding: 0px; overflow: hidden; background: #2d2d2d;">
		<jsp:include page="head.jsp"></jsp:include>
	</div>
	<div data-options="region:'west',split:true,title:'功能菜单'"
		style="width: 200px;">
		<jsp:include page="left.jsp"></jsp:include>
	</div>
	<PF:IfParameterEquals key="config.sys.mode.debug" val="true">
		<div data-options="region:'east',collapsed:true,split:true,title:'服务'"
			style="width: 250px; padding: 10px;">
			<jsp:include page="server.jsp"></jsp:include>
		</div>
	</PF:IfParameterEquals>
	<div data-options="region:'south',border:false"
		style="height: 25px; background-color: #F3F3F3;">
		<jsp:include page="foot.jsp"></jsp:include>
	</div>
	<div data-options="region:'center'">
		<div class="easyui-tabs" id="frame_tabs" style="overflow: hidden;"
			data-options="fit:true,border:false,tabHeight:33">
			<div title="首页" style="overflow: hidden;">
				<iframe scrolling="auto" frameborder="0" src="frame/home.do"
					style="width: 100%; height: 100%;"></iframe>
			</div>
		</div>
	</div>
	<div id="tabsShortMenu" class="easyui-menu"
		style="width: 150px; display: none;">
		<div data-options="iconCls:'icon-close-current'" id="closeCtabs">
			关闭当前</div>
		<div data-options="iconCls:'icon-close-others'" id="closeOtabs">
			关闭其他</div>
		<div data-options="iconCls:'icon-close-all'" id="closeAtabs">
			关闭全部</div>
		<div data-options="iconCls:'icon-reload'" id="reloadtabs">刷新选项卡
		</div>
		<div data-options="iconCls:'icon-graphic-design'" id="openByNewWindow">
			选项卡新窗口显示</div>
	</div>
</body>
<script type="text/javascript">
	$.parser.onComplete = function() {
		$('#loadInitFrameIndex').hide();
	};
	//解决chrome对easyui中tab高度的自适应问题
	function updateIframe() {
		$('iframe').css('height', $('#frame_tabs').height() - 35);
	}
	$(function() {
		//加载选项卡上的右键菜单（用于关闭多个选项卡）
		$('#frame_tabs').bind('contextmenu', function(e) {
			e.preventDefault();
			$('#tabsShortMenu').menu('show', {
				left : e.pageX,
				top : e.pageY
			});
		});
		//关闭当前选项卡
		$('#closeCtabs', '#tabsShortMenu').bind('click', function() {
			var tab = $('#frame_tabs').tabs('getSelected');
			var index = $('#frame_tabs').tabs('getTabIndex', tab);
			if (index != 0) {
				$('#frame_tabs').tabs('close', index);
			}
		});
		//刷新当前选项卡
		$('#reloadtabs', '#tabsShortMenu').bind('click', function() {
			var tab = $('#frame_tabs').tabs('getSelected');
			var index = $('#frame_tabs').tabs('getTabIndex', tab);
			openAndRefreshTab(index);
		});
		//新窗口中打开当前选项卡
		$('#openByNewWindow', '#tabsShortMenu').bind('click', function() {
			var tab = $('#frame_tabs').tabs('getSelected');
			var url = $('#frame' + tab[0].id);
			if (url.attr('src')) {
				window.open(url.attr('src'));
			}
		});
		//关闭其他选项卡
		$('#closeOtabs', '#tabsShortMenu').bind('click', function() {
			var tab = $('#frame_tabs').tabs('getSelected');
			var index = $('#frame_tabs').tabs('getTabIndex', tab);
			var delIndex = 1;
			$($('#frame_tabs').tabs('tabs')).each(function(i) {
				if (i == index) {
					delIndex = 2;
					//如果当前页是首页就从第一个开始删
					if (index == 0) {
						delIndex = 1;
					}
				}
				if (i != 0) {
					if ($('#frame_tabs').tabs('exists', delIndex)) {
						$('#frame_tabs').tabs('close', delIndex);
					}
				}
			});
			if ($('#frame_tabs').tabs('exists', 1)) {
				$('#frame_tabs').tabs('select', 1);
			}
		});
		//关闭所有选项卡
		$('#closeAtabs', '#tabsShortMenu').bind('click', function() {
			$($('#frame_tabs').tabs('tabs')).each(function(i) {
				if ($('#frame_tabs').tabs('exists', 1)) {
					$('#frame_tabs').tabs('close', 1);
				}
			});
		});
	});
	//刷新和打开一个选项卡
	function openAndRefreshTab(index) {
		$('#frame_tabs').tabs('select', index);
		if (index != 0) {
			var tab = $('#frame_tabs').tabs('getTab', index);
			if (tab[0].id) {
				var url = document.getElementById('frame' + tab[0].id).src;
				document.getElementById('frame' + tab[0].id).src = url;
			}
		}
	}
//-->
</script>
</html>