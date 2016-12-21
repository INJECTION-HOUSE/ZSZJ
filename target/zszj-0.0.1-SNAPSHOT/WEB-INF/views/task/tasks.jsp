<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="description" content="">
<meta name="keywords" content="">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/jquery.datetimepicker.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/pc/zszjmain.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/pccs.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.datetimepicker.full.js"></script>
<script type="text/javascript">
    //footer小于可视页面和大于可视页面
	$(function(){
	    var fHeight=$('.index_footer').height();
		 var iHeight=$(window).height();
		 var offsetTop=$('.index_footer').offset().top;
		 if(offsetTop<(iHeight-fHeight)){
			  alert(offsetTop+'offsetTop');
			  alert((iHeight-fHeight)+'iHeight-fHeight');
			  
		      $('.index_footer').css('bottom',0)
		 }else{
			  $('.index_footer').removeClass('position');
		 }
		 
	});

	$(function() {
		$('.con_card a').click(function() {
			var index = $(this).index();
			$(this).addClass('active').siblings().removeClass('active');
			$('.task .task_list').eq(index).show().siblings().hide();
			;
		});

		$.ajax({
			url : "${pageContext.request.contextPath}/messages/countInBox",
			cache : false,
			data : {
				userAccount : "xxxxxxxx",
				password : "xxxxxxxx"
			},
			success : function(data) {
				if (data.result == 1) {

					$("#mymsg_txt").html("消息(" + data.count + ")");
				} else {
					alert(data.count);
				}
			}
		});
	});
	
	
	var pageSize = 20;
	var paginationSize = 10;
	var taskData = [];
	$(function(){
		setup();
		$.datetimepicker.setLocale('zh');
		$("#start_date,#end_date").datetimepicker({
			startDate: new Date(),
			format:'Y-m-d',
			timepicker:false
		});
		
		$.ajax({
			url : "${pageContext.request.contextPath}/code/listItems",
			cache : false,
			data : {
				typeName : "issueType"
			},
			success : function(data) {
				if(data.length > 0){
					for(var i = 0; i < data.length; i++){
						var ele = '<option value="' + data[i].id + '">' + data[i].name + '</option>';
						$("#issue_type").append(ele);
					}
				}
			}
		});
		
		$("#search_btn").bind('click', function(){
			searchData();
		});
				
		loadData();
	});
	
	function searchData(){
		var type = $("#issue_type").val();
		var status = $("#task_status").val();
		var startDate = $("#start_date").val();
		var endDate = $("#end_date").val();
		var province = $("#province").val();
		var city = $("#city").val();
		var county = $("#county").val();
		if(startDate){
			startDate = new Date(startDate).getTime();
		}
		if(endDate){
			endDate = new Date(endDate).getTime();
		}
		if(startDate && endDate && startDate > endDate){
			alert("开始日期应小于结束日期");
			return;
		}
		province = province == "省份" ? "" : province;
		city = city == "地级市" ? "" : city;
		county = county == "市、县级市、县" ? "" : county;
		var pos = province + city + county;
		loadData(type, status, startDate, endDate, pos);
		
	}
	
	function loadData(type, status, startDate, endDate, position){
		var data = {};
		if(type){
			data.type = type;
		}
		if(status){
			data.status  = status;
		}
		if(start_date){
			data.startDate = startDate;
		}
		if(end_date){
			data.endDate = endDate;
		}
		if(position){
			data.position = position;
		}
		
		
		$.ajax({
			url : "${pageContext.request.contextPath}/task/getTaskList",
			cache : false,
			data : data,
			success : function(d) {
				taskData = d.data;
				initPagination(1, Math.ceil(taskData.length/pageSize));
			}
		});
	}
	
	function initPagination(page, length){
		loadPageData(null, (page - 1) * paginationSize + 1);
		
		$(".pagination ul").empty();
		var ele = '';
		if(page == 1){
			ele = '<li><a class="previous pagination_select"><<上一页</a></li>';
		}else{
			ele = '<li><a class="previous" onclick="initPagination(' + (page - 1) + ',' + length + ')"><<上一页</a></li>';
		}
		for(var i = (page - 1) * paginationSize + 1; i <= length; i++){
			if(i == (page - 1) * paginationSize + 1){
				ele += '<li><a class="page page_select" onclick="loadPageData(this, ' + i + ')">' + i + '</a></li>';
			}else{
				ele += '<li><a class="page" onclick="loadPageData(this, ' + i + ')">' + i + '</a></li>';
			}
			if(i == paginationSize * page){
				break;
			}
		}
		
		if(paginationSize * page < length){
			ele += '<li><a onclick="initPagination(' + (page + 1) + ',' + length + ')">下一页>></a></li>';
		}else{
			ele += '<li><a class="pagination_select">下一页>></a></li>';
		}
		$(".pagination ul").append(ele);
	}
	
	function loadPageData(ele, page){
		if(ele){
			$(".page.page_select").removeClass('page_select');
			$(ele).addClass('page_select');
		}
		buildTable(page, taskData);
	}
	
	function buildTable(page, data){
		$(".task_ul").empty();
		var ele = '';
		for(var i = (page - 1) * pageSize; i < data.length; i++){
			if(i == page * pageSize){
				break;
			};
			ele += '<li><div class="task_wrap"><div class="task_fir"><p class="title"><em>￥' + data[i].prepayFee + '</em><a onclick="goBidTask(' + data[i].id + ')">' 
						+ data[i].taskTitle + '</a></p></div><div class="task_sec">类别：' + data[i].category + '</div><div class="task_thir"><span class="tender">' 
						+ getStatus(data[i].status) + '</span><span class="num">已参与人数' + getJoiner(data[i].joiner) + '</span></div><div class="task_fou">' 
						+ getDeadline(data[i].deadline) + '</div><div class="clear"></div></div><div class="task_btn">'
						+ getBidBtn(data[i].joiner, data[i].id, data[i].status) + '</div><div class="clear"></div></li>';
		}
		$(".task_ul").append(ele);
	}
	
	function getJoiner(joiner){
		if(joiner === null){
			return 0;
		}else{
			return joiner.split(',').length;
		}
	}
	
	function getStatus(status){
		var result = '';
		switch(status){
		case 0:
			result = '投标中';
			break;
		case 1:
			result = '选标中';
			break;
		case 2:
			result = '维修中';
			break;
		case 3:
			result = '已完工';
			break;
		case 4:
			result = '已付款';
			break;
		case 5:
			result = '已结束';
			break;
		case 6:
			result = '纠纷';
			break;
		default:
			result = '投标中';
			break;
		}
		
		return result;
	}
	
	function getDeadline(msc){
		var result = "";
		var sub = parseInt(msc) - (new Date().getTime());
		if(sub <= 0){
			result = "已截止";
		}else if(sub < 86400000){
			if(sub < 3600000){
				result = Math.round(sub/60000) + "分钟后截止";
			}else{
				result = Math.floor(sub/3600000) + "小时后截止";
			}
		}else{
			result = Math.floor(sub/86400000) + "天后截止";
		}
		return result;
	}
	
	function getBidBtn(joiner, id, status){
		var isBid = false;
		if(joiner !== null){
			var userid = ${pageContext.getSession().getAttribute("LOGININFO").getLoginUser().getId()};
			if(joiner.indexOf(userid) > -1){
				isBid = true;
			}
		}
		
		if(isBid){
			return '<input type="button" value="已投标" onclick="goBidTask(' + id + ')"/>';
		}else if(status > 1){
			return '<input type="button" value="' + getStatus(status) + '" onclick="goBidTask(' + id + ')"/>'	;
		}else{
			return '<input type="button" value="投标" class="toubiao" onclick="goBidTask(' + id + ')"/>';
		}
	}
	
	function goBidTask(id) {
		window.location.href = "${pageContext.request.contextPath}/task/goBidTask?taskId=" + id;
	}
</script>
</head>
<body style="background: #EFEFEF;">
	<div class="main">
		<div class="head">
			<div class="index_top">
				<div class="wrap">
					<p>
						<%
							Object username = request.getSession().getAttribute("USERNAME");
							if (username == null) {
						%>
						<a href="${pageContext.request.contextPath}/login/index">登陆|注册</a>
						<%
							} else {
						%>
						<a><%=pageContext.getSession().getAttribute("USERNAME")%></a> <a
							href="#" onclick="signout()" class="free_register">退出</a>
						<%
							}
						%>
					</p>
					<div class="right_a">
						<ul>
							<li><a id="mymsg_txt"
								href="${pageContext.request.contextPath}/mymessage/home">消息(0)</a></li>
							<li><a
								href="${pageContext.request.contextPath}/profile/home">个人中心</a></li>
							<li>设置</li>
							<li>帮助</li>
						</ul>
					</div>
				</div>
			</div>
			<div class="wrap">
				<div class="task_logo float">
					<a href="${pageContext.request.contextPath}/index/main"><img src="${pageContext.request.contextPath}/static/images/logo.png" /></a>
				</div>
				<div class="head_right">
					<div class="head_right_input">
						<input type="text" placeholder="输入任务名称" class="search_text" /> 
						<input type="button" value="搜索" class="search_btn" />
					</div>
					
				</div>
			</div>
		</div>
		<div class="head_fixed">
			<div class="wrap">
				<div class="task_logo float">
					<a><img
						src="${pageContext.request.contextPath}/static/images/logo.png" /></a>
				</div>
				<div class="head_right">
					<div class="head_right_input">
						<input type="text" placeholder="输入任务名称" class="search_text" /> 
						<input type="button" value="搜索" class="search_btn" />
					</div>
					<div class="head_right_middle">或</div>
					<div class="head_right_right">
						<input type="button" value="发布新需求" class="release_btn" />
					</div>
				</div>
			</div>
		</div>
		<div class="content">
			<div class="info">
				当前所在位置：<a class="small_title">维修任务</a>
			</div>
			<div class="container condition">
				<div class="row condition_top">
					<div class="col-md-6"> 
						<label>问题分类</label> 
						<select id="issue_type">
							<option value="">全部</option>
						</select>
					</div>
					<div class="col-md-6"> 
						<label>任务状态</label> 
						<select id="task_status">
							<option value="">全部</option>
							<option value="0">投标中</option>
							<option value="1">选标中</option>
							<option value="2">维修中</option>
							<option value="3">已完工</option>
							<option value="4">已付款</option>
							<option value="5">已结束</option>
							<option value="5">纠纷</option>
						</select>
					</div>
					
				</div>
				<div class="row condition_top">
					<div class="col-md-6"> 
						<label>日期选择</label> 
						
						<input id="start_date" type="text" />
						<span class="line">—</span> 
						<input id="end_date" type="text" />
					</div>
					
				</div>
				<div class="row condition_top">
					<div class="col-md-10"> 
						<label>地区</label> 
						<select name="province" id="province"></select> 
						<select name="city" id="city"> </select>
						<select name="county" id="county"></select>
					</div> 
					<div class="col-md-2"> 
						<input id="search_btn" type="button" value="搜索" />
					</div>
				</div>
			</div>
			<div class="task">
				<div class="con_card">
					<a class="active">进行中</a>
				</div>
				<div class="tab">
					<div class="task_list">
						<ul class="task_ul">
						</ul>
						<div class="pagination">
							<ul>
							</ul>
							<div class="clear"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="index_footer position">
			<div class="footer_nav">
				<a>首页</a><span>|</span> <a>关于我们</a><span>|</span> <a>维修任务</a><span>|</span>
				<a>个人中心</a><span>|</span> <a>联系我们</a>
			</div>
			<p class="info">
				宁波市三体网络科技有限公司<span><a href="http://www.miitbeian.gov.cn/">浙ICP备15044527号-1</a></span>
			</p>
		</div>
	</div>

</body>


</html>