<%@ page pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
   <meta name="description" content="">
   <meta name="keywords" content="">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/pc/zszjmain.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
	<script type="text/javascript">
		function createNewTask() {
			window.location.href="${pageContext.request.contextPath}/personCenter/newtask";
		}
		
		
		var pageSize = 20;
		var paginationSize = 10;
		var taskData = [];
		$(function(){
			var userid = ${pageContext.getSession().getAttribute("LOGININFO").getLoginUser().getId()};
			$.ajax({
				url : "${pageContext.request.contextPath}/task/getTaskList",
				cache : false,
				data : {memberId: userid},
				success : function(d) {
					taskData = d.data;
					initPagination(1, Math.ceil(taskData.length/pageSize));
				}
			});
		});
		
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
			$(".publish_ul").empty();
			var ele = '';
			for(var i = (page - 1) * pageSize; i < data.length; i++){
				if(i == page * pageSize){
					break;
				};
				ele += '<li><div class="publish_wrap"><div class="publish_fir"><p class="title"><em>￥' + data[i].prepayFee + '</em><a onclick="goTaskDetail(' + data[i].id + ')">' 
							+ data[i].taskTitle + '</a></p></div><div class="publish_sec">类别：' + data[i].category + '</div><div class="publish_thir"><span class="publish_span">' 
							+ getStatus(data[i].status) + '</span><span class="num">已参与人数' + getJoiner(data[i].joiner) + '</span></div><div class="publish_fou">' 
							+ getDeadline(data[i].deadline) + '</div><div class="clear"></div></div><div class="clear"></div></li>';
			}
			$(".publish_ul").append(ele);
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
		
		function goTaskDetail(id) {
			window.location.href = "${pageContext.request.contextPath}/personCenter/taskdetail?taskId=" + id;
		}
	
	</script>
</head>
<body>
    <div class="main">
			 <div class="publish">
					  <div class="publish_title">
						   <h3>发布任务列表</h3>
						   <input type="button" id="createnewTaskBtn" value="发布维修任务" class="records_btn" onclick="createNewTask()" />
					  </div>
					  <div class="clear"></div>
					  <div class="publish_content">
						   <div class="publish_list">
								<ul class="publish_ul">
								</ul>
								<div class="pagination">
									  <ul>
									  </ul>
									  <div class="clear"></div>
								</div>
						   </div>
					  </div>
				 <div class="clear"></div>
		    </div>
			<div class="clear"></div>
	</div>
    
</body>


</html>