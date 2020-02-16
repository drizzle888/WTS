$(function() {
	initSideBoxSize();
	$('.wts-sidecard h1').click(function() {
		gotoTargetDom(this);
	});
	$('.wts-sidecard h2').click(function() {
		gotoTargetDom(this);
	});
	$('.wts-sidecard h3').click(function() {
		gotoTargetDom(this);
	});
	$('.wts-sidecard li').click(function() {
		gotoTargetDom(this);
	});
	$(".subjectUnitViewBox").mouseover(function() {
		$('.subjectUnitViewBox').removeClass("active");
	});
	// 答案改變事件
	$(".wts-paper-forms input[type='text']").change(function() {
		initSumPoint();
	});
	// 点击满分，就填充到表单中
	$(".wts-adjudge-maxpoint").click(function() {
		setPOINTtoINPUT($(this).attr('forInput'));
		initSumPoint();
	});
	initSumPoint();
});

/**
 * 提交判卷數據到後臺
 */
function submitAdjudgePaper() {
	$('#adjudgeHidenFormJsonId').val(enCodePaperForm());
	$('#adjudgeHidenFormId').submit();
}

// 编码所有表单
function enCodePaperForm() {
	var json;
	$(".wts-adjudge-point").each(function(i, obj) {
		if (json) {
			json = json + ',' + enCodeFormInput(obj);
		} else {
			json = enCodeFormInput(obj);
		}
	});
	return "[" + json + "]";
}
// 编码一个表单
function enCodeFormInput(docObj) {
	var subjectAnswer = new Object();
	var versionid = $(docObj).attr('id');
	if (versionid) {
		subjectAnswer.versionid = versionid.replace("POINT", "");
	}
	subjectAnswer.value = $(docObj).val();
	var jsonStr = JSON.stringify(subjectAnswer);
	return jsonStr;
}

/**
 * 点击满分，直接设置满分
 * 
 * @param pointInputId
 */
function setPOINTtoINPUT(pointInputId) {
	$('#' + pointInputId).val($('#' + pointInputId).attr('alt'));
}

// 计算并填充总分
function initSumPoint() {
	var pint = 0;
	$('.wts-adjudge-point').each(function(i, obj) {
		if ($(obj).val()) {
			try {
				// 校验整数
				if (!isNumber($(obj).val())) {
					alert('分数必须为整数：' + $(obj).val());
					$(obj).val(0);
				}
				// 校验满分（小于满分大于0）
				if ($(obj).val() < 0) {
					alert('分数必须大于0!');
					$(obj).val(0);
				}
				var maxPoint = parseInt($(obj).attr('alt'));
				if ($(obj).val() > maxPoint) {
					alert('分数' + $(obj).val() + '不能大于' + maxPoint + '!');
					$(obj).val(0);
				}
				pint = pint + parseInt($(obj).val());
			} catch (e) {
				alert(e);
			}
		}
	});
	$('#paper-allpoint').text(pint);
}

// 验证是否是数字
function isNumber(val) {
	var regPos = /^\d+(\.\d+)?$/; // 非负浮点数
	var regNeg = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/; // 负浮点数
	if (regPos.test(val) || regNeg.test(val)) {
		return true;
	} else {
		return false;
	}
}
// 适配右边答题卡的组件高度
function initSideBoxSize() {
	$(window).resize(
			function() {
				$('.wts-sidecard-subjects').css('max-height',
						$(window).height() - 300);
			});
	$('.wts-sidecard-subjects').css('max-height', $(window).height() - 300);
}

// 窗口移动到指定ID的元素处
function gotoTargetDom(domObj) {
	$('html,body').animate({
		scrollTop : $('#' + $(domObj).attr('targetId')).offset().top - 70
	}, 500);
	$('.subjectUnitViewBox').removeClass("active");
	$('#' + $(domObj).attr('targetId') + ' .subjectUnitViewBox').addClass(
			"active");
}
