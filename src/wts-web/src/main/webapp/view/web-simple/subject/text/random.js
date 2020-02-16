// 打开评论窗口
function openCommentWin() {
	pAlert("loading...", 10000);
	$('#commentShowBoxId').load("websubject/comment.do", {
		'subjectid' : subjectId
	}, function() {
		pClose();
		$('#submitComments-win').modal({
			keyboard : false
		})
	});
}

function doPraise() {
	pAlert("loading...", 10000);
	$.post("websubject/praise.do", {
		'subjectId' : subjectId
	}, function(flag) {
		pClose();
		if (flag.STATE == 0) {
			$('#praiseNumId').text(flag.num);
		} else {
			pAlert(flag.MESSAGE);
		}
	}, 'json');
}

function doBook(isdo) {
	pAlert("loading...", 10000);
	$.post("websubject/book.do", {
		'subjectId' : subjectId,
		'isDo' : isdo
	}, function(flag) {
		pClose();
		if (flag.STATE == 0) {
			$('#bookNumId').text(flag.num);
			if (flag.isBook) {
				$('#book-y').show();
				$('#book-n').hide();
				$('#bookTitleId').text("已");

			} else {
				$('#book-n').show();
				$('#book-y').hide();
				$('#bookTitleId').text("未");
			}
		} else {
			pAlert(flag.MESSAGE);
		}
	}, 'json');
}

function submitSubjectVar() {
	pAlert("loading...", 10000);
	$.post("websubject/PubRunPoint.do", {
		'testid' : testid,
		'versionId' : versionId,
		'val' : enCodePaperForm()
	}, function(flag) {
		pClose();
		if (flag.STATE == 0) {
			$('#submitVar-win').modal({
				keyboard : false
			})
			$('#analysisBoxId').hide();
			$('#rightBoxId').hide();
			$('#winShowAna').show();
			$('#winNext').show();
			$('#myModalLabel').text("答案");
			if (flag.point == 0) {
				$('#result-n').show();
				$('#result-y').hide();
				$('#result-c').hide();
			}
			if (flag.point == 100) {
				$('#result-n').hide();
				$('#result-y').show();
				$('#result-c').hide();
			}
			if (flag.point < 100 && flag.point > 0) {
				$('#result-n').hide();
				$('#result-y').hide();
				$('#result-c').show();
				$('#point-Span').text(flag.point);
			}
		} else {
			pAlert(flag.MESSAGE);
		}
	}, 'json');
}
// 打开解析窗口（独立）
function openAnalysesWin() {
	pAlert("loading...", 10000);
	$.post("websubject/PubAnalysis.do", {
		'versionId' : versionId
	}, function(flag) {
		pClose();
		if (flag.STATE == 0) {
			$('#submitVar-win').modal({
				keyboard : false
			})
			$('#result-n').hide();
			$('#result-y').hide();
			$('#result-c').hide();
			$('#winShowAna').hide();
			$('#winNext').hide();
			$('#myModalLabel').text("解析");
			loadAnalysesInfo(flag);
		} else {
			pAlert(flag.MESSAGE);
		}
	}, 'json');
}
// 打开解析窗口(答案中)
function loadAnalysesWin() {
	pAlert("loading...", 10000);
	$.post("websubject/PubAnalysis.do", {
		'versionId' : versionId
	}, function(flag) {
		pClose();
		if (flag.STATE == 0) {
			$('#winShowAna').hide();
			loadAnalysesInfo(flag);
		} else {
			pAlert(flag.MESSAGE);
		}
	}, 'json');
}
function loadAnalysesInfo(flag) {
	$('#analysisBoxId .innerbox').html("");
	$(flag.analyses).each(
			function(i, obj) {
				$('#analysisBoxId').show();
				$('#analysisBoxId .innerbox').append(
						"<div class='analysisUnit'>" + obj.text + "</div>")
			});
	$('#rightBoxId .innerbox').html("");
	$(flag.answers).each(
			function(i, obj) {
				$('#rightBoxId').show();
				var weight = "";
				if (obj.answer.pointweight > 0) {
					var weight = "<span class='answerWeight'>(得分权重"
							+ obj.answer.pointweight + ")</span>";
				}
				$('#rightBoxId .innerbox').append(
						"<div class='answerUnit'>" + obj.answer.answer + weight
								+ "</div>");
			});
}

$(function() {
	// 鼠标移开时边框样式恢复
	$(".subjectUnitViewBox").mouseover(function() {
		$('.subjectUnitViewBox').removeClass("active");
	});
	// 选择题或判断题选中一个选项
	$('.wts-select-unit').click(function() {
		checkUnitTipClick($('#' + $(this).attr('targetId')).val());
	});
	// 答案控件改变事件
	$(".wts-paper-forms input[type='text']").change(function() {
		// cval = enCodeFormInput(this);
	});
	// 答案控件改变事件
	$(".wts-paper-forms input[type='radio']").change(function() {
		// cval = enCodeFormInput(this);
		syncSelectOptionStyle(this);
	});
	// 答案控件改变事件
	$(".wts-paper-forms input[type='checkbox']").change(function() {
		// cval = enCodeFormInput(this);
		syncSelectOptionStyle(this);
	});
	// 答案控件改变事件
	$(".wts-paper-forms textarea").change(function() {
		// cval = enCodeFormInput(this);
	});
	// 刷新答題卡
	$('#sideCardRefreshButton').click(function() {
		location.reload();
	});
	// 启动页面倒计时脚本
	initSelectOptionStyle();
});
// 初始化选择题判断题的选项样式
function initSelectOptionStyle() {
	$($('.subjectUnitViewBox').find("input")).each(function(i, obj) {
		if ($(obj).is(':checked')) {
			syncSelectOptionStyle(obj);
		}
	});
}
// 同步选择题判断题的选项样式
function syncSelectOptionStyle(domObj) {
	var inputType = $(domObj).attr("type");
	var id = $(domObj).attr("id");
	// 当前点击的选项
	var titleUnitDom = $("div[targetid='" + id + "']");
	if (titleUnitDom && inputType == 'radio') {
		// 清除所有同级选项的样式
		$(titleUnitDom).siblings("div").removeClass("selected");
		// 单独加深选中元素
		$(titleUnitDom).addClass("selected");
	}
	if (titleUnitDom && inputType == 'checkbox') {
		// 清除所有同级选项的样式
		// $(titleUnitDom).siblings("div").removeClass("selected");
		// 加深所有选中选项的样式
		// alert($('#'+id).parents("ul").find('input').html());
		$($('#' + id).parents("ul").find('input')).each(function(i, obj) {
			var siblingsInput = $(obj).attr("id");
			var siblingsTitle = $("div[targetid='" + siblingsInput + "']");
			if ($(obj).is(':checked')) {
				$(siblingsTitle).addClass("selected");
			} else {
				$(siblingsTitle).removeClass("selected");
			}
		});
	}
}
// 编码一个表单
function enCodeFormInput(docObj) {
	var subjectAnswer = new Object();
	subjectAnswer.versionid = $(docObj).attr('name');
	var answerid = $(docObj).attr('id');
	if (answerid) {
		subjectAnswer.answerid = answerid.replace("-INPUT", "");
	} else {
		subjectAnswer.answerid = "NONE";
	}
	if ($(docObj).attr('type') == 'radio'
			|| $(docObj).attr('type') == 'checkbox') {
		var val = $("input[value='" + $(docObj).val() + "']").is(':checked') == true;
		subjectAnswer.value = val;
	} else {
		subjectAnswer.value = $(docObj).val();
	}
	var jsonStr = JSON.stringify(subjectAnswer);
	return jsonStr;
}

// 编码所有表单
function enCodePaperForm() {
	var json;
	$(".wts-paper-forms input[type='text']").each(function(i, obj) {
		if (json) {
			json = json + ',' + enCodeFormInput(obj);
		} else {
			json = enCodeFormInput(obj);
		}
	});
	$(".wts-paper-forms input[type='radio']").each(function(i, obj) {
		if (json) {
			json = json + ',' + enCodeFormInput(obj);
		} else {
			json = enCodeFormInput(obj);
		}
	});
	$(".wts-paper-forms input[type='checkbox']").each(function(i, obj) {
		if (json) {
			json = json + ',' + enCodeFormInput(obj);
		} else {
			json = enCodeFormInput(obj);
		}
	});
	$(".wts-paper-forms textarea").each(function(i, obj) {
		if (json) {
			json = json + ',' + enCodeFormInput(obj);
		} else {
			json = enCodeFormInput(obj);
		}
	});
	return "[" + json + "]";
}
// 题的选项描述被点击时，选项被选中(val:选项对应的表单元素，一般未radio或checkbox)
function checkUnitTipClick(val) {
	// 选项被选中且，选项是复选框的选项
	var checkState = ($("input[value='" + val + "']").is(':checked') == true)
			&& ($("input[value='" + val + "']").attr('type') == 'checkbox');
	if (checkState) {
		$("input[value='" + val + "']").attr("checked", false);
	} else {
		$("input[value='" + val + "']").attr("checked", true);
	}
	$("input[value='" + val + "']").trigger("change");
}
// 询问框
function pConfirm(tip, okFunc, noFunc) {
	swal({
		text : tip,
		buttons : true,
		buttons : {
			'確定' : true,
			cancel : "取消"
		},
	}).then(function(willDelete) {
		if (willDelete) {
			okFunc();
		} else {
			if (noFunc) {
				noFunc();
			}
		}
	});
}

// pAlert("loading...", 10000);
// pClose();
// 消息框
function pAlert(tip, timenum) {
	if (timenum) {
		swal({
			text : tip,
			timer : timenum,
			buttons : false
		});
	} else {
		swal(tip);
	}
}
function pClose() {
	setTimeout(function() {
		swal.close();
	}, 200);
}