/**
 * farm-calendar.js
 * 
 * Copyright (c) 2009-2015 www.wcpdoc.com
 * 
 * wangdong 2015.07
 */
(function($, document) {
	$.fn.fcalendar = function(options) {
		// options.dayMenuClick=function(e,dayid)日头部右键击事件
		// options.taskMenuClick=function(e,taskid)任务右键击事件
		// options.year
		// options.month
		// options.onChageDate(currentYear, currentmonth)日期改变回调事件
		options.month--;
		var currentYear = options.year;
		var currentmonth = options.month;
		var thisObj = this;
		// 当前日
		var opday;
		// 当前任务
		var optask;

		loadDayDom(currentYear, currentmonth);
		// 加载组件css样式
		function loadDayCssAndEvent() {
			$('.fcalendar-day-head', thisObj).bind('contextmenu', function(e) {
				opday = this;
				var dayId = $(e.target).parents('.list-group').attr('id');
				options.dayMenuClick(e, dayId);
			});
			$('.fcalendar-day-head', thisObj).bind('click', function(e) {
				opday = this;
				$(this).toggleClass("active");
			});
			$('.fcalendar-day-head', thisObj).bind('dblclick', function(e) {
				opday = this;
				$('.fcalendar-day-head', thisObj).removeClass("active");
				$(this).toggleClass("active");
			});
		}
		// 加载日历dom
		function loadDayDom(year, month) {
			$(thisObj).html('');
			var dateC = new Date(year, month, 1);
			var dateNum = getLastDay(year, month + 1);
			var tableDom = "";
			tableDom = tableDom + "<tr>";
			for (var i = 0; i < dateNum; i++) {
				dateC.setDate(dateC.getDate() + (i == 0 ? 0 : 1));
				var dayNum = dateC.getDate();// 当天日期
				var weekNum = dateC.getDay();// 当天星期
				var backtdClass = "";
				if (weekNum == 0 || weekNum == 6) {
					backtdClass = "warning";
				}
				// 开始或周一的时候
				if (weekNum == 1 || i == 0) {
					tableDom = tableDom + "</tr><tr>";
				}
				if (i == 0) {
					for (var n = 1; n < (weekNum == 0 ? 7 : weekNum); n++) {
						tableDom = tableDom + "<td></td>";
					}
				}
				tableDom = tableDom + "<td class=\"" + backtdClass + "\">";
				tableDom = tableDom + buildDayDom(dateC);
				tableDom = tableDom + "</td>";
			}
			tableDom = tableDom + "</tr>";
			$(thisObj).append(tableDom);
			loadDayCssAndEvent();
			options.onChageDate(currentYear, currentmonth);
		}
		// 构造天的dom
		function buildDayDom(datec) {
			var dayNum = datec.getDate();// 当天日期
			var weekNum = datec.getDay();// 当天星期
			var milliseconds = datec.getTime();
			var weekkey = [ "日", "一", "二", "三", "四", "五", "六" ];
			var headTitleClass = "list-group-item-info";
			if (weekNum == 0 || weekNum == 6) {
				headTitleClass = "list-group-item-danger";
			}
			var arr = [
					"<ul class=\"list-group\" id=" + milliseconds + ">",
					"  <li class=\"list-group-item " + headTitleClass
							+ " fcalendar-day-head\">",
					"     " + dayNum + "日<span class=\"badge\">周"
							+ weekkey[weekNum] + "</span>", "  </li>", "</ul>" ];

			return arr.join("");
		}
		// 天加一个日常任务
		this.addDayTask = function(date, taskid, html) {
			var milliseconds = date.getTime();
			var classdom = [
					"  <li id=\""
							+ taskid
							+ "\" class=\"list-group-item info fcalendar-daytask\">",
					html, "  </li>" ];
			$('#' + milliseconds, thisObj).append(classdom.join(""));
			$('#' + taskid, thisObj).bind('contextmenu', function(e) {
				optask = this;
				options.taskMenuClick(e, taskid);
			});
			$('#' + taskid, thisObj).bind('click', function(e) {
				opday = this;
				$(this).toggleClass("active");
			});
			$('#' + taskid, thisObj).bind('dblclick', function(e) {
				opday = this;
				$('.fcalendar-daytask', thisObj).removeClass("active");
				$(this).toggleClass("active");
			});
			return;
		}

		// 上个月
		this.lastMonth = function() {
			goLastmonth();
			loadDayDom(currentYear, currentmonth);
			return;
		}
		// 下个月
		this.nextMonth = function() {
			goNextmonth();
			loadDayDom(currentYear, currentmonth);
			return;
		}
		// 删除一个任务
		this.delTask = function(id) {
			$('#' + id, thisObj).remove();
		}
		this.getYear = function() {
			return currentYear;
		}
		this.getMonth = function() {
			return currentmonth;
		}
		// 获得当前操作天
		this.getLiveDay = function() {
			return opday;
		}
		// 获得当前操作任务
		this.getLiveTask = function() {
			return optask;
		}
		// 选择所有天
		this.allDaycheck = function() {
			$('.fcalendar-day-head', thisObj).addClass("active");
			return;
		}
		// 取消选择所有天
		this.allDaycheckClear = function() {
			$('.fcalendar-day-head', thisObj).removeClass("active");
			return;
		}
		// 选择所有任务
		this.allTaskcheck = function() {
			$('.fcalendar-daytask', thisObj).addClass("active");
			return;
		}
		// 取消选择所有任务
		this.allTaskcheckClear = function() {
			$('.fcalendar-daytask', thisObj).removeClass("active");
			return;
		}

		// 获得所有选中日ids
		this.getCheckDayIds = function() {
			var arrayObj = new Array();
			$('.fcalendar-day-head.active').each(function(i, obj) {
				var curentDayId = $(obj).parents('.list-group').attr('id');
				arrayObj.push(curentDayId);
			});
			return arrayObj;
		}
		// 获得所有选中任务ids
		this.getCheckTaskIds = function() {
			var arrayObj = new Array();
			$('.fcalendar-daytask.active').each(function(i, obj) {
				arrayObj.push($(obj).attr('id'));
			});
			return arrayObj;
		}

		// 获得某月的最后一天
		function getLastDay(year, month) {
			var new_year = year; // 取当前的年份
			var new_month = month++;// 取下一个月的第一天，方便计算（最后一天不固定）
			if (month > 12) {
				new_month -= 12; // 月份减
				new_year++; // 年份增
			}
			var new_date = new Date(new_year, new_month, 1); // 取当年当月中的第一天
			return (new Date(new_date.getTime() - 1000 * 60 * 60 * 24))
					.getDate();// 获取当月最后一天日期
		}
		// 到下个月
		function goNextmonth(dt) {
			var m = (currentmonth == 11) ? 0 : currentmonth + 1;
			currentmonth = m;
			if (currentmonth == 11) {
				currentYear = currentYear + 1;
			}
			return m;
		}
		// 到上个月
		function goLastmonth(dt) {
			var m = (currentmonth == 0) ? 11 : currentmonth - 1;
			currentmonth = m;
			if (currentmonth == 0) {
				currentYear = currentYear - 1;
			}
			return m;
		}
		// 格式化日期
		function formateDate(d, formateIndex) {
			var o = {
				"M+" : d.getMonth() + 1, // month
				"d+" : d.getDate(), // day
				"h+" : d.getHours(), // hour
				"m+" : d.getMinutes(), // minute
				"s+" : d.getSeconds(), // second
				"q+" : Math.floor((d.getMonth() + 3) / 3), // quarter
				"S" : d.getMilliseconds()
			// millisecond
			}
			if (/(y+)/.test(formateIndex))
				formateIndex = formateIndex.replace(RegExp.$1,
						(d.getFullYear() + "").substr(4 - RegExp.$1.length));
			for ( var k in o)
				if (new RegExp("(" + k + ")").test(formateIndex))
					formateIndex = formateIndex.replace(RegExp.$1,
							RegExp.$1.length == 1 ? o[k] : ("00" + o[k])
									.substr(("" + o[k]).length));
			return formateIndex;
		}
		return thisObj;
	};
})(jQuery, document);