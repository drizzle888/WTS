$(function() {
	initSideBoxSize();
	//导航到元素下
	$('.wts-sidecard h1').click(function() {
		gotoTargetDom(this);
	});
	//导航到元素下
	$('.wts-sidecard h2').click(function() {
		gotoTargetDom(this);
	});
	//导航到元素下
	$('.wts-sidecard h3').click(function() {
		gotoTargetDom(this);
	});
	//导航到元素下
	$('.wts-sidecard li').click(function() {
		gotoTargetDom(this);
	});
	//鼠标移开时边框样式恢复
	$(".subjectUnitViewBox").mouseover(function() {
		$('.subjectUnitViewBox').removeClass("active");
	});
	//选择题或判断题选中一个选项
	$('.wts-select-unit').click(function() {
		checkUnitTipClick($('#' + $(this).attr('targetId')).val());
	});
	//答案控件改变事件
	$(".wts-paper-forms input[type='text']").change(function() {
		var json = enCodeFormInput(this);
		submitSubject(json, $(this).attr('name'));
	});
	//答案控件改变事件
	$(".wts-paper-forms input[type='radio']").change(function() {
		var json = enCodeFormInput(this);
		submitSubject(json, $(this).attr('name'));
		syncSelectOptionStyle(this);
	});
	//答案控件改变事件
	$(".wts-paper-forms input[type='checkbox']").change(function() {
		var json = enCodeFormInput(this);
		submitSubject(json, $(this).attr('name'));
		syncSelectOptionStyle(this);
	});
	//答案控件改变事件
	$(".wts-paper-forms textarea").change(function() {
		var json = enCodeFormInput(this);
		submitSubject(json, $(this).attr('name'));
	});
	// 刷新答題卡
	$('#sideCardRefreshButton').click(function() {
		location.reload();
	});
	// 保存答題卡
	$('#sideCardSaveButton').click(function() {
		var allJson = enCodePaperForm();
		submitSavePaper(allJson);
	});
	// 提交答題卡
	$('#sideCardSubmitButton').click(function() {
		$('#submitPaper-win').modal('hide');
		submitPaper();
		//var allJson = enCodePaperForm();
		//先保存再提交答題卡submitSavePaper(allJson, submitPaper);
	});
	// 跳转到检查试卷页面
	$('#sideCardCheckupButton').click(function() {
		$('#submitPaper-win').modal('hide');
		checkupPaper();
		//var allJson = enCodePaperForm();
		//先保存再進入檢查頁面submitSavePaper(allJson, checkupPaper);
	});
	// 返回答题页面
	$('#sideBackPaperButton').click(function() {
		answerPaper();
	});
	//用户确认提交试卷的回调事件（确认对话框弹出时执行）
	$('#submitPaper-win').on('shown.bs.modal', function (e) {
		//计算当前一共有多少道题，完成多少道题
		$("#card-finish-numinfo-all").text($('.wts-side-subjuct-unit').size());
		$("#card-finish-numinfo-compelet").text($('.wts-side-subjuct-unit.active').size());
	})
	//启动页面倒计时脚本
	CountDownStart();
	initSelectOptionStyle();
});
//初始化选择题判断题的选项样式
function initSelectOptionStyle(){
	$($('.subjectUnitViewBox').find("input")).each(function(i,obj){
		if($(obj).is(':checked')){
			syncSelectOptionStyle(obj);
		}
	});
}


//同步选择题判断题的选项样式
function syncSelectOptionStyle(domObj){
	var  inputType=$(domObj).attr("type");
	var  id=$(domObj).attr("id");
	//当前点击的选项
	var  titleUnitDom=$("div[targetid='"+id+"']");
	if(titleUnitDom&&inputType=='radio'){
		//清除所有同级选项的样式
		$(titleUnitDom).siblings("div").removeClass("selected");
		//单独加深选中元素
		$(titleUnitDom).addClass("selected");
	}
	if(titleUnitDom&&inputType=='checkbox'){
		//清除所有同级选项的样式
		//$(titleUnitDom).siblings("div").removeClass("selected");
		//加深所有选中选项的样式
		//alert($('#'+id).parents("ul").find('input').html());
		$($('#'+id).parents("ul").find('input')).each(function(i,obj){
			var siblingsInput=$(obj).attr("id");
			var siblingsTitle=$("div[targetid='"+siblingsInput+"']");
			if($(obj).is(':checked')){
				$(siblingsTitle).addClass("selected");
			}else{
				$(siblingsTitle).removeClass("selected");
			}
		});
	}
}

// 倒计时定时器,剩余时间
var countDownTimer, maxtime;
/**
 * 倒计时1/2:启动倒计时
 */
function CountDownStart() {
	var timerBoxId = "countDownTimerBoxId";
	if ($('#' + timerBoxId).length > 0) {
		maxtime = parseInt($('#' + timerBoxId).attr('title'));
		$('#' + timerBoxId).attr('title','以服务器时间为准');
		if(maxtime>1){
			countDownTimer = setInterval("CountDown('" + timerBoxId + "')", 1000);
		}
	}
}
/**
 * 倒计时2/2:计算时间单元
 * 
 * @param showDomId
 */
function CountDown(showDomId) {
	if (maxtime >= 0) {
		minutes = Math.floor(maxtime / 60);
		seconds = Math.floor(maxtime % 60);
		msg = minutes + "分" + seconds + "秒";
		$('#' + showDomId).text(msg);
		if (maxtime < 5 * 60) {
			// alert("还剩5分钟");
			$('#' + showDomId).addClass("alarm");
		}
		--maxtime;
	} else {
		clearInterval(countDownTimer);
		if (confirm("答题时间到,已禁止答题，是否立即交卷?")) {
			submitPaper();
		}
	}
}

/**
 * 提交试卷
 */
function submitPaper() {
	window.location = basePath + "webpaper/PubsubmitPaper.do?cardId="
			+ $('#cardId-Input').val();
}
/**
 * 答题页面
 */
function answerPaper() {
	window.location = basePath + "webpaper/card.do?paperid="
			+ $('#paperId-Input').val() + "&roomId=" + $('#roomId-Input').val();
}
/**
 * 跳转到检查试卷页面
 */
function checkupPaper() {
	window.location = basePath + "webpaper/checkUpPaper.do?cardId="
			+ $('#cardId-Input').val();
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
		if ($(obj).attr('name')) {
			if (json) {
				json = json + ',' + enCodeFormInput(obj);
			} else {
				json = enCodeFormInput(obj);
			}
		}
	});
	return "[" + json + "]";
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
// 提交保存一道題的答案
function submitSubject(jsonStr, versionId) {
	var timeout_post = setTimeout(function() {
		alert("数据提交异常,请及时检查网络或联系管理员[p1]!");
	}, 60000);
	$.post('webpaper/PubsaveSubjectVal.do', {
		'paperid' : $('#paperId-Input').val(),
		'roomid' : $('#roomId-Input').val(),
		'jsons' : jsonStr
	}, function(flag) {
		if (timeout_post) { // 清除定时器
			clearTimeout(timeout_post);
			timeout_post = null;
		}
		if (flag.STATE == '0') {
			// 成功 //alert('成功');
			if (flag.isAnswer) {
				// 已填答案
				$('#' + versionId + "-NAVIID").addClass("active");
			} else {
				// 為填答案
				$('#' + versionId + "-NAVIID").removeClass("active");
			}
		} else {
			if (flag.MESSAGE == 'OUTTIME') {
				// 答题时间超时
				alert("答题时间已超,试卷将自动提交(超时后的答案不会被保存)!");
				setTimeout(function() {
					submitPaper();
				}, 2000);
			} else {
				// 失敗 //alert('失敗');
				$('#' + versionId + "-NAVIID").removeClass("active");
				alert(flag.MESSAGE);
			}
		}
	}, 'json');
}
// 提交保存试卷的答案
function submitSavePaper(jsonStr, fun) {
	// 设置超时提醒用户检查网络
	var timeout_post = setTimeout(function() {
		alert("数据提交异常,请及时检查网络或联系管理员[p2]!");
	}, 60000);
	$('#sideCardSaveButton').attr('disabled', 'disabled');
	$('#savePaper-win').modal({
		backdrop : true,
		keyboard : false
	}).modal('show');
	openLoadingWindows = true;
	runSaveLoading();
	$.post('webpaper/PubsavePaperVal.do', {
		'paperid' : $('#paperId-Input').val(),
		'roomid' : $('#roomId-Input').val(),
		'jsons' : jsonStr
	}, function(flag) {
		if (timeout_post) { // 清除定时器
			clearTimeout(timeout_post);
			timeout_post = null;
		}
		openLoadingWindows = false;
		setTimeout(function() {
			$('#savePaper-win').modal('hide');
			$('#sideCardSaveButton').removeAttr("disabled");
		}, 1000);
		if (flag.STATE == '0') {
			// 成功 //alert('成功');
			if (fun) {
				fun();
			}
		} else {
			if (flag.MESSAGE == 'OUTTIME') {
				// 答题时间超时
				alert("答题时间已超,试卷将自动提交(超时后的答案不会被保存)!");
				setTimeout(function() {
					submitPaper();
				}, 2000);
			} else {
				// 失敗 //alert('失敗');
				alert(flag.MESSAGE);
			}
		}
	}, 'json');
}

// 加载进度条窗口是否展示（用来同步展示进度条动画）
var openLoadingWindows = false;
/**
 * 执行进度条
 * 
 * @param hav
 *            已经用掉的
 * @param les
 *            剩下的
 */
function runSaveLoading(hav, les) {
	if (!les) {
		les = 100;
	}
	if (!hav) {
		hav = 0;
	}
	hav = Math.round(hav + les / 8);
	les = Math.round(100 - hav);
	$('#savePaper-progress').attr('aria-valuenow', (100 - les));
	$('#savePaper-progress').css('width', (100 - les) + "%");
	if (hav < 90 && openLoadingWindows) {
		setTimeout(function() {
			runSaveLoading(hav, les);
		}, 500);
	} else {
		$('#savePaper-progress').attr('aria-valuenow', 99);
		$('#savePaper-progress').css('width', "99%");
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

// 窗口移动到指定ID的元素处
function gotoTargetDom(domObj) {
	$('html,body').animate({
		scrollTop : $('#' + $(domObj).attr('targetId')).offset().top - 70
	}, 500);
	$('.subjectUnitViewBox').removeClass("active");
	$('#' + $(domObj).attr('targetId') + ' .subjectUnitViewBox').addClass(
			"active");
}
