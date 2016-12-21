<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
   <meta name="description" content="">
   <meta name="keywords" content="">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/pc/zszjmain.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
   <script type="text/javascript">
        
		$(function(){
		    $('.con_card a').click(function(){
			     var index=$(this).index();
			     $(this).addClass('active').siblings().removeClass('active');
				 $('.task .task_list').eq(index).show().siblings().hide();;
			});
		});
		//弹出框居中
		$(function(){
		     var iHeight=$(window).height();
			 var tanchuHeight=$('.tanchu').height();
			 var iWidth=$(window).width();
			 var tanchuWidth=$('.tanchu').width();
			 var halfTop=(iHeight-tanchuHeight)/2;
			 var halfLeft=(iWidth-tanchuWidth)/2;
			 $('.tanchu').css('top',halfTop+'px');
			 $('.tanchu').css('left',halfLeft+'px');
			 
			 $('.tender_btn').click(function(){
				 	if(isOwner){
						alert("您无法对自己发布的任务进行投标");
						return;
					}
			       $('.screen').show();
				   $('.tanchu').show();
				   $(document.body).css({
					   "overflow-y":"hidden"
				   });
			 });
			 $('.screen').click(function(){
			       $('.screen').hide();
				   $('.tanchu').hide();
				    $(document.body).css({
					   "overflow-y":"auto"
					});
			 });
			 $('.cancel').click(function(){
			       $('.screen').hide();
				   $('.tanchu').hide();
				    $(document.body).css({
					   "overflow-y":"auto"
					});
			 });
			 
		});
		
		var isOwner = false;
		$(function(){
			var taskId = ${taskId};
			loadData(taskId);
			$('.toub').click(function(){
				var bidContent = $("#bidContent").val();
				if(bidContent == ''){
					alert("请输入投标原因");
					return;
				}
				var price = $("#price").val();
				var ex = /^\d+$/;
				if(!ex.test(price) || price ==0){
					alert("请输入正确的报价，需为大于0的整数");
					return;
				}
				var arriveTime = $("#arriveTime").val();
				$.ajax({
					url : "${pageContext.request.contextPath}/task/saveTaskBid",
					method: 'post',
					cache : false,
					data : {
						taskId : taskId,
						bidContent : bidContent,
						price : price,
						arriveTime : arriveTime
					},
					success : function(data) {
						$('.tanchu').hide();
						$('.screen').hide();
						loadData(taskId);
						$('.tanchu').hide();
					}
				});
			});
		})
		
		function loadData(taskId){
			$.ajax({
				url : "${pageContext.request.contextPath}/task/getBidTask",
				cache : false,
				data : {
					taskId : taskId
				},
				success : function(data) {
					initContent(data.data);
				}
			});
		}
		
		function initContent(data){
			var feeRange = 0;
			if(data.minFee !== data.maxFee){
				feeRange = data.minFee + '-' + data.maxFee;
			}else if(data.minFee == data.maxFee && data.minFee == 0){
				feeRange = data.prepayFee;
			}else{
				feeRange = data.minFee;
			}
			$(".tender_price").text('￥' + feeRange);
			
			if(data.hasBid){
				$("#bidButton").attr("disabled", "disabled");
				$("#bidButton").removeClass("active");
				$("#bidButton").val("已投标");
			}else if(data.status > 1){
				$("#bidButton").attr("disabled", "disabled");
				$("#bidButton").removeClass("active");
				$("#bidButton").val("已截止");
			}
			
			$("#task_title").text(data.taskTitle);
			$("#member_count").text(data.members.length);
			$("#task_deadline").text(getDeadline(data.deadline));
			$("#task_time").text('发布时间：' + formatDate(data.createTime));
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
			
			$('.state li.active').removeClass('active');
			$(".task_status_" + data.status).addClass('active');
			
			$(".tender_member_ul").empty();
			var memEle = '';
			for(var j = 0; j < data.members.length; j++){
				var mem = data.members[j];
				memEle += '<li><div class="pic"><img src="${pageContext.request.contextPath}/upload/' + mem.avatar + '" /></div>'
						+ '<div class="pic_info"><p><span class="title">' + mem.nickName + '</span><span class="renzheng">' + getVerify(mem.verify)
						+ '</span></p><p>投标编号：<span class="title_info">' + mem.bidNumber + '</span></p><p class="p_date">提交时间：<span class="title_date">'
						+ formatDate(mem.time) + '</span></p></div></li>';
			}
			$(".tender_member_ul").append(memEle);
			
			var userid = ${pageContext.getSession().getAttribute("LOGININFO").getLoginUser().getId()};
			isOwner = userid == data.memberId;
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
		
   </script>
</head>
<body style="background:#EFEFEF;">
    <div class="main">
		<div class="head">
		      <div class="index_top">
				   <div class="wrap">
					     <p>
					   		<%Object username = request.getSession().getAttribute("USERNAME"); if(username == null) {%>
						   		<a href="${pageContext.request.contextPath}/login/index">登陆|注册</a>
						    <% } else {%>
						    	<a><%=pageContext.getSession().getAttribute("USERNAME")%></a>
							    <a href="#" onclick="signout()" class="free_register">退出</a>
						    <% }%>
					    </p>
					    <div class="right_a">
							  <ul>
								    <li id="mymsg_txt">消息(0)</li>
								    <li>个人中心</li>
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
						        <input type="text" placeholder="输入任务名称" class="search_text"/>
								<input type="button" value="搜索" class="search_btn"/>
						  </div>
						  
				   </div>
			  </div>
		</div>
		<div class="head_fixed">
		      <div class="index_top">
				   <div class="wrap">
					     <p>
							  <a>请登录</a>
							  <a class="free_register">免费注册</a>
					    </p>
					    <div class="right_a">
							  <ul>
								    <li>我的消息</li>
								    <li>我的订单</li>
								    <li>设置</li>
								    <li>帮助</li>
							  </ul>
					    </div>
				   </div>
			  </div>
		      <div class="wrap">
			       <div class="task_logo float">
						  <a><img src="${pageContext.request.contextPath}/static/images/logo.png" /></a>
				   </div>
				   <div class="head_right">
				          <div class="head_right_input">
						        <input type="text" placeholder="输入任务名称" class="search_text"/>
								<input type="button" value="搜索" class="search_btn"/>
						  </div>
						 
				   </div>
			  </div>
		</div>
		<div class="content">
		     <div class="info">当前所在位置：<a href="${pageContext.request.contextPath}/task/goTaskList" style="text-decoration: underline;">维修任务</a> / <a class="small_title">维修任务详情</a></div>
			 <div class="detail">
			      <div class="tender_info">
				        <div class="tender_info_div">
						     <ul>
								  <li class="tender_task">招标任务</li>
								  <li class="tender_price"></li>
							 </ul>
						</div>
						<div class="tender_btn"><input class="active" id="bidButton" type="button" value="我要投标"/></div>
						<div class="clear"></div>
				  </div>
				  <div class="detail_info">
				        <h3 id="task_title"></h3>
						<p><span>已收到 <em id="member_count">0</em> 个投标</span><span>投标截止还有<em id="task_deadline"></em></span><span id="task_time"></span></p>
				        <ul class="state">
						     <li class="task_status_0"><span class="circle"></span><span class="circle_info">竞标</span></li>
							 <li class="task_status_1"><span class="circle"></span><span class="circle_info">选标</span></li>
							 <li class="task_status_2"><span class="circle"></span><span class="circle_info">开始工作</span></li>
							 <li class="task_status_3"><span class="circle"></span><span class="circle_info">验收</span></li>
							 <li class="task_status_4"><span class="circle"></span><span class="circle_info">付款</span></li>
							 <li class="task_status_5"><span class="circle"></span><span class="circle_info">结束</span></li>
						</ul>
						<div class="clear"></div>
						<div class="detail_line"></div>
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
			 
			 <div class="tender_member">
			      <h3>已投标会员</h3>
				  <ul class="tender_member_ul"></ul>
				  <!-- <div class="pagination">
				  	<ul>
					   <li>
							<a class="previous pagination_select">上一页</a>
					   </li>
					   <li>
							<a class="page page_select">1</a>
					   </li>
					   <li>
							<a class="page">2</a>
					   </li>
					   <li>
							<a class="page">3</a>
					   </li>
					   <li>
							<a class="dot page">. . .</a>
					   </li>
					   <li>
							<a>下一页>></a>
					   </li>
				  	</ul>
				  <div class="clear"></div>
				</div> -->
			 </div>
		</div>
        <div class="index_footer">
		     <div class="footer_nav">
			     <a>首页</a><span>|</span>
				 <a>关于我们</a><span>|</span>
				 <a>维修任务</a><span>|</span>
				 <a>个人中心</a><span>|</span>
				 <a>联系我们</a>
			 </div>
			 <p class="info">宁波市三体网络科技有限公司<span><a href="http://www.miitbeian.gov.cn/">浙ICP备15044527号-1</a></span></p>
		</div>
	</div>
	<div class="screen"></div>
	<div class="tanchu">
	     <h3>我要竞标</h3>
		 <div class="tanchu_content">
		      <div class="text_box">
			       <span>维修原因</span>
				   <span>
				        <textarea id="bidContent"></textarea>
				   </span>
			  </div>
			  <div class="input_box1">
			       <span>维修金额</span>
				   <span>
				        <input type="number" placeholder="输入金额" id="price"/>
				   </span>
			  </div>
			  <div class="input_box1">
			       <span>上门时间</span>
				   <span>
					   <select id="arriveTime">
					       <option value="1">15分钟</option>
					       <option value="2">半小时</option>
					       <option value="3">1小时</option>
					       <option value="4">2小时</option>
					       <option value="5">4小时</option>
					   	   <option value="6">当天到</option>
				       </select>
				   </span>
			  </div> 
			  <!-- <div class="input_box2">
			       <span>保证金(选填)</span>
				   <span>
				        <input type="text" placeholder="输入金额"></textarea>
				   </span>
			  </div> -->
		 </div>
		 <div class="tanchu_btn">
		      <input type="button" value="取消" class="cancel" />
			  <input type="button" value="我要投标" class="toub"/>
		 </div>
	</div>
</body>
</html>