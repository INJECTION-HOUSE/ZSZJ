<%@ page pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
   <meta name="description" content="">
   <meta name="keywords" content="">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/pc/zszjmain.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
   <script type="text/javascript">
        $(function(){
		    $('.activity_top span').click(function(){
			     var index=$(this).index();
			     $(this).addClass('active4').siblings().removeClass('active4');
				 $('.activity .activity_list .activity_tab').eq(index).show().siblings().hide();
			});
		});
   </script>
</head>
<body>
    <div class="activity">
	      <div class="activity_top">
		        <span class="active4">最新活动</span>
				<span>全部活动</span>
		  </div>
          <div class="activity_list">
		        <div class="activity_tab">
				      <ul>
					        <li>
								  <span class="activity_title">活动家_专业商务会议活动网_中国领先的会议查询报名营销平台</span>
							      <span class="activity_date">2015-8-21 10:45<i><img src="images/closed.png" /></i></span>
					`		</li>
							<li style="border-bottom:none;">
								  <span class="activity_title">手机轻松做问卷，一天30元，换现金话费都可以</span>
							      <span class="activity_date">2015-8-21 10:45<i><img src="images/closed.png" /></i></span>
							</li>
					  </ul>
				</div>
				<div class="activity_tab" style="display:none;">
				      <ul>
					         <li>
								  <span class="activity_title">什么叫沙龙活动</span>
							      <span class="activity_date">2015-8-21 10:45<i><img src="images/closed.png" /></i></span>
					`		</li>
							<li>
								  <span class="activity_title">微信公众平台 一般做哪些互动营销活动</span>
							      <span class="activity_date">2015-8-21 10:45<i><img src="images/closed.png" /></i></span>
							</li>
					  </ul>
				</div>
	</div>
</div>
    
</body>



</html>