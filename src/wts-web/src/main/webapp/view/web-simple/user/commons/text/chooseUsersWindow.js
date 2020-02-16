var defOrgid, defName;
var choosUsers = new Array();
var choosOrgs = new Array();
var treeObj;
// 初始化组织机构树
function initTreeConntent(treeBoxId) {
	var json = '[{"text": "数据加载中...","id":"di1111"}]';
	treeObj = $(treeBoxId).treeview({
		data : json
	});
	$.post("webuser/loadOrgTreeData.do", {}, function(flag) {
		treeObj = $(treeBoxId).treeview({
			data : flag.data,
			levels : 1,
			onNodeSelected : function(event, data) {
				orgSearch(data.id)
			}
		});
	}, 'json');
}

// 组织机构查询
function orgSearch(orgid) {
	defName = null;
	dataLoadSearch(orgid, null, 1);
	$('#sendUserNameInput').val('');
}

// 翻页
function goSUPage(page) {
	dataLoadSearch(null, null, page);
}

// 清空选择的节点
function clearSelected() {
	var checkNode = $(treeObj).treeview('getSelected');
	if (checkNode) {
		$(treeObj).treeview('unselectNode', [ checkNode ]);
	}
}

// 查询远程数据
function dataLoadSearch(orgid, name, page) {
	if (!orgid) {
		orgid = defOrgid;
	} else {
		defOrgid = orgid;
	}
	if (!name) {
		name = defName;
	} else {
		defName = name;
	}
	if (!page) {
		page = 1;
	}
	$.post("webuser/loadSendUserData.do", {
		"orgid" : orgid,
		"name" : name,
		"page" : page
	}, function(flag) {
		initUsersDom(flag.data, flag.orgs);
		initPageDom(flag.data);
	}, 'json');
}
/**
 * 构造一个用户或机构的图标元素
 * 
 * @param id
 * @param name
 * @param url
 * @param type
 *            user|org
 * @param funcname
 * @returns {String}
 */
function creatNodeHtml(id, name, url, type, funcname) {
	var html;
	if (!funcname) {
		html = '<li id="' + id + '" class="' + type + '"> <div><img class="img-rounded" title="'
				+ name + '" src="' + url + '" /> <p>' + name
				+ '</p> </div></li>';
	} else {
		html = '<li onClick="' + funcname + '(\'' + id + '\',\'' + name
				+ '\',\'' + url + '\')"> <div><img class="img-rounded" title="' + name + '" src="'
				+ url + '" /> <p>' + name + '</p> </div></li>';
	}
	return html;
}

/**
 * 构造人员(機構)结果
 * 
 * @param data
 *            用户人员
 * @param orgs
 *            组织机构
 */
function initUsersDom(data, orgs) {
	$('#ableChooseNodes').html("");
	$(orgs.resultList).each(function(i, obj) {
		var name = obj.NAME;
		var id = obj.ID;
		var url = "text/img/orgnization.png";
		var html = creatNodeHtml(id, name, url, 'org', 'chooseSendOrg');
		$('#ableChooseNodes').append(html);
	});
	$(data.resultList).each(function(i, obj) {
		var name = obj.NAME;
		var id = obj.ID;
		var url = obj.URL;
		var html = creatNodeHtml(id, name, url, 'user', 'chooseSendUser');
		$('#ableChooseNodes').append(html);
	});

}
// 构造分页栏
function initPageDom(data) {
	$('.usernode-page').html("");
	for (var i = data.startPage; i <= data.endPage; i++) {
		var html = '';
		if (i == data.currentPage) {
			html = '<li class="active"><a onClick="goSUPage(' + i + ')">' + i
					+ '</a></li>';
		} else {
			html = '<li><a onClick="goSUPage(' + i + ')">' + i + '</a></li>';
		}
		$('.usernode-page').append(html);
	}
}

// 选择一个组织机构
function chooseSendOrg(id, name, url) {
	var org = new Object();
	org.id = id;
	org.name = name;
	org.url = url;
	addToArray(choosOrgs, org);
	showChooseNodeByChooseBox();
}
// 选择一个用户
function chooseSendUser(id, name, url) {
	var user = new Object();
	user.id = id;
	user.name = name;
	user.url = url;
	addToArray(choosUsers, user);
	showChooseNodeByChooseBox();
}
// 向数组中添加一个元素，过滤重复的
function addToArray(arrayObj, node) {
	var isHave = false;
	for (var i = 0; i < arrayObj.length; i++) {
		if (arrayObj[i].id == node.id) {
			isHave = true;
		}
	}
	if (!isHave) {
		arrayObj.push(node);
	}
	return arrayObj;
}
// 删除全部的选中用户和机构
function delAllSendNode() {
	$(choosOrgs).each(function(i, obj) {
		var name = obj.name;
		var id = obj.id;
		var url = "text/img/orgnization.png";
		delSendNode(id, name, url);
	});
	$(choosUsers).each(function(i, obj) {
		var name = obj.name;
		var id = obj.id;
		var url = obj.url;
		delSendNode(id, name, url);
	});
}

// 删除一个选中的用户或机构
function delSendNode(id, name, url) {
	var orgindex = -1;
	var userindex = -1;
	for (var i = 0; i < choosUsers.length; i++) {
		if (choosUsers[i].id == id) {
			userindex = i;
		}
	}
	for (var i = 0; i < choosOrgs.length; i++) {
		if (choosOrgs[i].id == id) {
			orgindex = i;
		}
	}
	if (orgindex >= 0) {
		choosOrgs.splice(orgindex, 1);
	}
	if (userindex >= 0) {
		choosUsers.splice(userindex, 1);
	}
	showChooseNodeByChooseBox();
}
// 在选中区中回显选中人员和机构
function showChooseNodeByChooseBox() {
	if ((choosUsers.length + choosOrgs.length) > 0) {
		$('#chooseUserAndOrgConfirm #chooseNodeShowbox')
				.html(
						'<div class="chooseuser-tip">已选<b>'
								+ (choosUsers.length)
								+ '</b>用户和<b>'
								+ (choosOrgs.length)
								+ '</b>机构(点击可删除选中状态)</div><ul id="chooseNodeShowboxUl" class="usernode-box"></ul>');
		$(choosOrgs).each(
				function(i, obj) {
					var html = creatNodeHtml(obj.id, obj.name, obj.url, 'org',
							'delSendNode');
					$('#chooseUserAndOrgConfirm #chooseNodeShowboxUl').append(
							html);
				});
		$(choosUsers).each(
				function(i, obj) {
					var html = creatNodeHtml(obj.id, obj.name, obj.url, 'user',
							'delSendNode');
					$('#chooseUserAndOrgConfirm #chooseNodeShowboxUl').append(
							html);
				});
	} else {
		$('#chooseUserAndOrgConfirm #chooseNodeShowbox')
				.html(
						'<div class="center-block text-center nochoose-title" style="cursor: auto;">'
								+ ' <i class="glyphicon glyphicon-user"></i> 请在上面用户机构列表中，选择接收者 </div>');
	}
}
// 在某個地方回显已选人员和机构
function showChooseNodeByOutBox(boxId) {
	var nodeNum = 0;
	var showBox = $('#' + boxId);
	if ((choosUsers.length + choosOrgs.length) > 0) {
		$(showBox)
				.html(
						'<div class="chooseuser-tip">已选<b>'
								+ (choosUsers.length)
								+ '</b>用户和<b>'
								+ (choosOrgs.length)
								+ '</b>机构</div><ul id="chooseNodeShowboxUl" class="usernode-box"></ul>');
		$(choosOrgs).each(function(i, obj) {
			var html = creatNodeHtml(obj.id, obj.name, obj.url, 'org');
			$('#' + boxId + ' #chooseNodeShowboxUl').append(html);
			nodeNum++;
		});
		$(choosUsers).each(function(i, obj) {
			var html = creatNodeHtml(obj.id, obj.name, obj.url, 'user');
			$('#' + boxId + ' #chooseNodeShowboxUl').append(html);
			nodeNum++;
		});
		$('li', showBox).css("cursor", 'auto');
	} else {
		$(showBox).html('');
	}
	return nodeNum;
}

function initAlert() {
	var content = '<div class="modal-header" style="padding:8px;">'
			+ ' <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>'
			+ '  <h4 class="modal-title" id="mySmallModalLabel">提示 </h4>'
			+ ' </div>'
			+ ' <div class="modal-body">'
			+ '   ...'
			+ ' </div>'
			+ '<div class="modal-footer" style="padding:8px;text-align:center;">'
			+ '  <button type="button" class="btn btn-default" data-dismiss="modal">确定</button>'
			+ '</div>';

	var html = '<div id="tatleAlertWin" class="modal fade bs-example-modal-sm in"  aria-hidden="true" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" >'
			+ '  <div class="modal-dialog modal-sm" role="document" style="width:300px;">'
			+ ' <div class="modal-content">'
			+ content
			+ ' </div><!-- /.modal-content -->'
			+ ' </div><!-- /.modal-dialog -->' + '</div>';

	if ($('#tatleAlertWin').length <= 0) {
		$('body').append(html);
	}
}
function alert(message) {
	initAlert();
	$('body #tatleAlertWin .modal-body').html(message);
	$('#tatleAlertWin').modal('show');
}