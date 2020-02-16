var url_delActionSubject = "subject/del.do";// 删除URL
var url_formActionSubject = "subject/form.do";// 增加、修改、查看URL
var url_searchActionSubject = 'subject/query.do?funtype=' + funtype;// 查询URL
var title_windowSubject = "考题管理";// 功能名称
var gridSubject;// 数据表格对象
var searchSubject;// 条件查询组件对象
$(function() {
	$(".easyui-layout").show();
	// 初始化数据表格
	gridSubject = $('#dataSubjectGrid').datagrid({
		url : url_searchActionSubject,
		fit : true,
		fitColumns : true,
		'toolbar' : '#SubjectToolbar',
		pagination : true,
		closable : true,
		checkOnSelect : true,
		border : false,
		striped : true,
		rownumbers : true,
		ctrlSelect : true
	});
	// 初始化条件查询
	searchSubject = $('#searchSubjectForm').searchForm({
		gridObj : gridSubject
	});
	// funtype需要在引用界面定義（0分类管理展示全部分类1使用权、2维护权）
	$('#subjectTypeTree').tree({
		url : 'subjecttype/subjecttypeTree.do?funtype=' + funtype,
		onSelect : function(node) {
			$('#PARENTIDSUB_RULE').val(node.id);
			$('#PARENTTITLESUB_RULE').val(node.text);
			searchSubject.dosearch({
				'ruleText' : searchSubject.arrayStr()
			});
		}
	});
	$('#subjectTypeTreeReload').bind('click', function() {
		$('#subjectTypeTree').tree('reload');
	});
	$('#subjectTypeTreeOpenAll').bind('click', function() {
		$('#subjectTypeTree').tree('expandAll');
	});
	$('#buttonMaterialChoose').bindChooseWindow('chooseMaterialWin', {
		width : 600,
		height : 300,
		modal : true,
		url : 'admin/MaterialChooseGridPage.do',
		title : '选择材料',
		callback : function(rows) {
			// $('#NAME_like').val(rows[0].NAME);
		}
	});
});
// 试题预览
function viewDataSubject(flag) {
	var selectedArray = $(gridSubject).datagrid('getSelections');
	if (selectedArray.length == 1 || flag) {
		var subjectid;
		if (flag) {
			subjectid = flag;
		} else {
			subjectid = selectedArray[0].ID;
		}
		var url = url_formActionSubject + '?pageset.pageType=' + PAGETYPE.VIEW
				+ '&ids=' + subjectid;
		$.farm.openWindow({
			id : 'winSubjectView',
			width : 800,
			height : 400,
			modal : true,
			url : url,
			title : '试题预览'
		});
	} else {
		$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
				'info');
	}
}
// 新增
function addDataSubject(type) {
	// 1.填空，2.单选，3.多选，4判断，5问答，6附件
	if (!$('#PARENTIDSUB_RULE').val() || $('#PARENTIDSUB_RULE').val() == 'NONE') {
		$.messager.alert(MESSAGE_PLAT.PROMPT, "请选中一个分类后再添加试题!", 'info');
		return;
	}
	var url = url_formActionSubject + '?operateType=' + PAGETYPE.ADD
			+ "&tiptype=" + type + "&typeid=" + $('#PARENTIDSUB_RULE').val();
	$.farm.openWindow({
		id : 'winSubject',
		width : 900,
		height : 450,
		modal : true,
		url : url,
		title : '新增'
	});
}
// 修改
function editDataSubject() {
	var selectedArray = $(gridSubject).datagrid('getSelections');
	if (selectedArray.length == 1) {
		var url = url_formActionSubject + '?operateType=' + PAGETYPE.EDIT
				+ '&ids=' + selectedArray[0].ID;
		$.farm.openWindow({
			id : 'winSubject',
			width : 900,
			height : 450,
			modal : true,
			url : url,
			title : '修改'
		});
	} else {
		$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
				'info');
	}
}

// 题解析
function analysisMng() {
	var selectedArray = $(gridSubject).datagrid('getSelections');
	if (selectedArray.length == 1) {
		$.farm.openWindow({
			id : 'winSubject',
			width : 600,
			height : 350,
			modal : true,
			url : "subjectanalysis/list.do?subjectid=" + selectedArray[0].ID,
			title : '題解析'
		});
	} else {
		$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
				'info');
	}
}

// 清理废弃的题
function clearCancelSubjects() {
	// 有数据执行操作
	$.messager.confirm(MESSAGE_PLAT.PROMPT, "试题库中不可见的临时数据将被清理，请继续该操作",
			function(flag) {
				if (flag) {
					$(gridSubject).datagrid('loading');
					$.post("subject/clearSubject.do", {}, function(flag) {
						var jsonObject = JSON.parse(flag, null);
						$(gridSubject).datagrid('loaded');
						if (jsonObject.STATE == 0) {
							$(gridSubject).datagrid('reload');
							alert("已清理题数量:" + jsonObject.num);
						} else {
							var str = MESSAGE_PLAT.ERROR_SUBMIT
									+ jsonObject.MESSAGE;
							$.messager.alert(MESSAGE_PLAT.ERROR, str, 'error');
						}
					});
				}
			});
}

// 检查更新题的答案状态
function checkAnswered() {
	// 有数据执行操作
	var selectedArray = $(gridSubject).datagrid('getSelections');
	if (selectedArray.length > 0) {
		// 有数据执行操作
		$.messager.confirm(MESSAGE_PLAT.PROMPT, "将更新" + selectedArray.length
				+ "道题的正确答案设置状态，完成后将自动刷新列表，请继续！", function(flag) {
			if (flag) {
				$(gridSubject).datagrid('loading');
				$.post("subject/updateAnswered.do" + '?ids='
						+ $.farm.getCheckedIds(gridSubject, 'ID'), {},
						function(flag) {
							var jsonObject = JSON.parse(flag, null);
							$(gridSubject).datagrid('loaded');
							if (jsonObject.STATE == 0) {
								$(gridSubject).datagrid('reload');
							} else {
								var str = MESSAGE_PLAT.ERROR_SUBMIT
										+ jsonObject.MESSAGE;
								$.messager.alert(MESSAGE_PLAT.ERROR, str,
										'error');
							}
						});
			}
		});
	} else {
		$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE, 'info');
	}
}

// 删除
function delDataSubject() {
	var selectedArray = $(gridSubject).datagrid('getSelections');
	if (selectedArray.length > 0) {
		// 有数据执行操作
		var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
		$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
			if (flag) {
				$(gridSubject).datagrid('loading');
				$.post(url_delActionSubject + '?ids='
						+ $.farm.getCheckedIds(gridSubject, 'ID'), {},
						function(flag) {
							var jsonObject = JSON.parse(flag, null);
							$(gridSubject).datagrid('loaded');
							if (jsonObject.STATE == 0) {
								$(gridSubject).datagrid('reload');
							} else {
								var str = MESSAGE_PLAT.ERROR_SUBMIT
										+ jsonObject.MESSAGE;
								$.messager.alert(MESSAGE_PLAT.ERROR, str,
										'error');
							}
						});
			}
		});
	} else {
		$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE, 'info');
	}
}
// 移动分类
function moveSubjectTypetree() {
	var selectedArray = $(gridSubject).datagrid('getSelections');
	if (selectedArray.length > 0) {
		$.farm.openWindow({
			id : 'subjectTypeNodeWin',
			width : 250,
			height : 300,
			modal : true,
			url : "subjecttype/subjectTypeTreeView.do?funtype=2&ids="
					+ $.farm.getCheckedIds(gridSubject, 'ID'),
			title : '移动分类'
		});
	} else {
		$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE, 'info');
	}
}
// 移动分类回调函数
function chooseSubjectNodeBackFunc(node, subjectid) {
	$.messager.confirm(MESSAGE_PLAT.PROMPT, "是否移分类到目标分类下？", function(flag) {
		if (flag) {
			$.post("subject/subjectTypeSetting.do", {
				ids : subjectid,
				typeId : node.id
			}, function(flag) {
				var jsonObject = JSON.parse(flag, null);
				if (jsonObject.STATE == 0) {
					$(gridSubject).datagrid('reload');
					$('#subjectTypeNodeWin').window('close');
				} else {
					$.messager.alert(MESSAGE_PLAT.ERROR, jsonObject.MESSAGE,
							'error');
				}
			});
		}
	});
}
// 批量创建题
function addSubjects() {
	if (!$('#PARENTIDSUB_RULE').val() || $('#PARENTIDSUB_RULE').val() == 'NONE') {
		$.messager.alert(MESSAGE_PLAT.PROMPT, "请选中一个分类后再添加试题!", 'info');
		return;
	}
	var url = 'subject/addSubjects.do?typeid=' + $('#PARENTIDSUB_RULE').val();
	$.farm.openWindow({
		id : 'winSubjects',
		width : 800,
		height : 450,
		modal : true,
		url : url,
		title : '批量创建'
	});
}
function clearMaterial() {
	var selectedArray = $(gridSubject).datagrid('getSelections');
	if (selectedArray.length > 0) {
		$.post("subject/clearMaterial.do", {
			subjectIds : $.farm.getCheckedIds(gridSubject, 'ID')
		}, function(flag) {
			if (flag.STATE == 0) {
				$(gridSubject).datagrid('reload');
			} else {
				$.messager.alert(MESSAGE_PLAT.ERROR, flag.MESSAGE, 'error');
			}
		}, 'json');
	} else {
		$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE, 'info');
	}
}
function bindMaterial() {
	var selectedArray = $(gridSubject).datagrid('getSelections');
	if (selectedArray.length > 0) {
		chooseWindowCallBackHandle = function(row) {
			$.post("subject/bindMaterial.do", {
				subjectIds : $.farm.getCheckedIds(gridSubject, 'ID'),
				materialId : row.ID
			},
					function(flag) {
						if (flag.STATE == 0) {
							$("#chooseMaterialWin").window('close');
							$(gridSubject).datagrid('reload');
						} else {
							$.messager.alert(MESSAGE_PLAT.ERROR, flag.MESSAGE,
									'error');
						}
					}, 'json');
		};
		$.farm.openWindow({
			id : 'chooseMaterialWin',
			width : 600,
			height : 400,
			modal : true,
			url : 'material/MaterialChooseGridPage.do',
			title : '选择材料'
		});
	} else {
		$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE, 'info');
	}
}