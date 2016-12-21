<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
   <meta name="description" content="">
   <meta name="keywords" content="">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/pc/zszjmain.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
   <script type="text/javascript">
		var select = {bid: 0, mid: 0, price: 0};
		var selectedTaskID;
		var selectedBidID;
		var taskOwnerId;
		$(function(){
			var taskId = ${taskId};
			selectedTaskID = taskId;
			loadData(taskId);
		})
		
		function loadData(taskId){
			$.ajax({
				url : "${pageContext.request.contextPath}/task/getBidTask",
				cache : false,
				data : {
					taskId : taskId
				},
				success : function(data) {
					initContent(data.data, taskId);
				}
			});
		}
		
		function initContent(data, taskId){
			taskOwnerId = data.taskOwner;
			$("#task_title").text(data.taskTitle);
			$("#member_count").text(data.members.length);
			$("#task_deadline").text(getDeadline(data.deadline));
			$("#task_time").text('发布时间：' + formatDate(data.createTime));
			$("#task_status").text(getStatus(data.status));
			$("#finshTaskDiv").empty();
			if(data.status === 3) {
	            var buttons = "<input type='button' value='完工评价' onclick='startPayment()' class='zb_comment'/>" + 
		            "<input type='button' value='返工' onclick='reworkTask()' class='zb_work' />";
		       $("#finshTaskDiv").append(buttons);   
			}
			if(data.status > 1){
				$(".choose_bid").removeClass("active");
				$(".choose_bid").attr("disabled", "disabled");
			}else{
				$(".choose_bid").bind("click", function(){
					if(select.bid == 0){
						alert("请选择一个投标");
						return;
					}
					if(confirm("选择此投标后，您将无法更改选择。确定选择？")){
						doSelect(taskId, select.bid, select.mid, select.price + data.prepayFee);
					}
				});
			}
			$("#task_desc").text(data.taskDesc);
			$("#task_images").empty();
			if(data.imageFiles !== ''){
				var images = data.imageFiles.split(";");
				var ele = '';
				for(var i = 0; i < images.length; i++){
					ele += '<li><img src="${pageContext.request.contextPath}/upload/' + images[i] + '"/></li>'
				}
				$("#task_images").append(ele);
			}
			
			
			$(".tender_member_ul").empty();
			var memEle = '';
			var activeEle = '';
			for(var j = 0; j < data.members.length; j++){
				var mem = data.members[j];
				if(mem.bindBid == 1){
					selectedBidID = mem.bid;
					activeEle = '<li class="active"><div class="pic"><img src="${pageContext.request.contextPath}/upload/' + mem.avatar + '" /></div>'
					+ '<div class="pic_info"><p><span class="title">' + mem.nickName + '</span><span class="renzheng">' + getVerify(mem.verify)
					+ '</span><span class="title title-tag">报价：</span><span class="title_date">￥' + mem.price 
					+ '</span><span class="title title-tag">上门时间：</span><span class="title_date">' + getArriveTime(mem.arriveTime)
					+ '</span></p><div class="tender_content">' + mem.content + '</div></div><div class="clear"><div class="zb"><img src="${pageContext.request.contextPath}/static/images/zb.png" /></div></li>';
				}else if(data.status > 1){
					memEle += '<li><div class="pic"><img src="${pageContext.request.contextPath}/upload/' + mem.avatar + '" /></div>'
							+ '<div class="pic_info"><p><span class="title">' + mem.nickName + '</span><span class="renzheng">' + getVerify(mem.verify)
							+ '</span><span class="title title-tag">报价：</span><span class="title_date">￥' + mem.price 
							+ '</span><span class="title title-tag">上门时间：</span><span class="title_date">' + getArriveTime(mem.arriveTime)
							+ '</span></p><div class="tender_content">' + mem.content + '</div></div></li>';
				}else{
					memEle += '<li onclick="selectMember(this, ' + mem.bid + ',' + mem.id + ',' + mem.price + ')"><div class="pic"><img src="${pageContext.request.contextPath}/upload/' + mem.avatar + '" /></div>'
					+ '<div class="pic_info"><p><span class="title">' + mem.nickName + '</span><span class="renzheng">' + getVerify(mem.verify)
					+ '</span><span class="title title-tag">报价：</span><span class="title_date">￥' + mem.price 
					+ '</span><span class="title title-tag">上门时间：</span><span class="title_date">' + getArriveTime(mem.arriveTime)
					+ '</span></p><div class="tender_content">' + mem.content + '</div></div></li>';
				}
			}
			
			$(".tender_member_ul").append(activeEle + memEle);
		}
		
		function doSelect(taskId, bid, mid, price){
			$.ajax({
				url : "${pageContext.request.contextPath}/personCenter/selectBid",
				cache : false,
				method : "post",
				data : {
					id : bid,
					taskId : taskId,
					memberId : mid,
					price : price
				},
				success : function(data) {
					loadData(taskId);
				}
			});
		}
		
		function startPayment() {
			$('.screen').show();
			$('.tanchu').show();
		}
		
		function reworkTask() {
			var r = confirm("雇主你好，确定要返工重修吗？");
			if(r == true) {
				$.ajax({
					url : "${pageContext.request.contextPath}/personCenter/reworktask",
					cache : false,
					method : "post",
					data : {
						taskId : selectedTaskID,
						bidId : selectedBidID,
						comments : $("#bidCommentsId").val(),
						taskOwnerId : taskOwnerId,
						level : getSatisfactionLevel()
					},
					success : function(data) {
						loadData(selectedTaskID);
					}
				});
			}
		}
		
		function selectMember(ele, bid, mid, price){
			$(".tender_member_ul li.active").removeClass("active");
			$(ele).addClass("active");
			select.bid = bid;
			select.mid = mid;
			select.price = price;
		}
		
		function getVerify(verify){
			return verify ? "已认证" : "未认证";
		}
		
		function getDeadline(msc){
			var result = "";
			var sub = parseInt(msc) - (new Date().getTime());
			if(sub <= 0){
				result = "已截止";
			}else if(sub < 86400000){
				if(sub < 3600000){
					result = Math.round(sub/60000) + "分钟";
				}else{
					result = Math.floor(sub/3600000) + "小时";
				}
			}else{
				result = Math.floor(sub/86400000) + "天";
			}
			return result;
		}
		
		function formatDate(msc){
			var date = new Date(parseInt(msc));
			return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes();
		}
		
		function finishTask() {
			$.ajax({
				url : "${pageContext.request.contextPath}/personCenter/updateBidComments",
				cache : false,
				method : "post",
				data : {
					taskId : selectedTaskID,
					bidId : selectedBidID,
					comments : $("#bidCommentsId").val(),
					taskOwnerId : taskOwnerId,
					level : getSatisfactionLevel()
				},
				success : function(data) {
				    $('.screen').hide();
					$('.tanchu').hide();
					   $(document.body).css({
						   "overflow-y":"auto"
					   });
					loadData(selectedTaskID);
				}
			});
		}
		
		function getSatisfactionLevel() {
			var level = $("input[name='satisfaction_degree']:checked").val()
			// alert(level);
			console.log("level : " + level);
			return level;
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
		
		function getArriveTime(type){
			var result = "";
			switch(type){
			case 1:
				result = "15分钟";
				break;
			case 2:
				result = "半小时";
				break;
			case 3:
				result = "1小时";
				break;
			case 4:
				result = "2小时";
				break;
			case 5:
				result = "4小时";
				break;
			case 6:
				result = "当天到";
				break;
			}
			
			return result;
		}
		$(function(){
			$('.zb_comment').click(function(){
				$('.screen').show();
				$('.tanchu').show();
			});
			$('.cancel').click(function(){
			    $('.screen').hide();
				$('.tanchu').hide();
				   $(document.body).css({
					   "overflow-y":"auto"
				   });
			 });
		});
   </script>
</head>
<body style="background:#EFEFEF;">
    <div class="main">
		<div class="content" style="width: 100%;">
			 <div class="detail">
				  <div class="detail_info">
				        <h3 id="task_title" style="margin-top: 0;"></h3>
				        <div id="finshTaskDiv" class="zb_status">
				        </div>
						<p class="zb_status_info">
							<span>已收到 <em id="member_count">14</em> 个投标</span>
							<span>投标截止还有<em id="task_deadline"></em></span>
							<span id="task_time"></span>
							<span>状态：<em id="task_status">投标中</em></span>
						</p>
						<div class="detail_line" style="margin: 20px auto;"></div>
						<div class="task_info">
						     <h3>任务详情</h3>
							 <p id="task_desc"></p>
							 <ul id="task_images" class="pic_load"></ul>
						</div>
						<div class="clear"></div>
				  </div>
				  <!-- 分享按钮 -->
				  <div class="share"></div>
			 </div>
			 
			 <div class="tender_member" style="padding: 10px 30px;">
			 	<div style="line-height: 40px;">
			     	<h3 style="float: left; margin-left:0;padding-botton:0;">已投标会员</h3>
			     	<input type="button" class="choose_bid active" value="选择"/>
			 	</div>
			 	<div class="clear"></div>
			 	<div style="margin: 5px auto;height: 1px;background: #efefef;"></div>
			 	<ul class="tender_member_ul"></ul>
			 </div>
		</div>
	</div>
	<div class="screen"></div>
	<div class="tanchu">
	     <h3>完工评价</h3>
		 <div class="tanchu_content">
		      <div class="text_box">
			       <span>评价内容</span>
				   <span>
				        <textarea id="bidCommentsId"></textarea>
				   </span>
			  </div>
			  <div class="input_box3">
			       <span class="input_box3_title">选择满意程度</span><br>
			       <span class="manyi">
			             <input type="radio" value="3" name="satisfaction_degree"/>
			             <label>非常满意</label>
			       </span>
			       <span class="manyi">
			             <input type="radio" value="2" name="satisfaction_degree"/>
			             <label>满意</label>
			       </span>
			       <span class="manyi">
			             <input type="radio" value="1" name="satisfaction_degree"/>
			             <label>不满意</label>
			       </span>
			       
			  </div>
		 </div>
		 <div class="tanchu_btn">
		      <input type="button" value="取消" class="cancel" />
			  <input type="button" value="确定" onclick="finishTask()" class="toub"/>
		 </div>
	</div>
</body>


</html>