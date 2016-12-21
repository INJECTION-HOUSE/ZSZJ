<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>注塑之家后台管理系统</title>
   <meta name="viewport" content="width=device-width,height=device-height, initial-scale=1, maximum-scale=1, user-scalable=no">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/index.css" id="css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
	<script type="text/javascript">
        function navList(id) {
		    var $obj = $("#juheweb"), $item = $("#J_nav_" + id);
		    $item.addClass("on").parent().removeClass("none").parent().addClass("selected");
		    $obj.find(".list").click(function () {
		        var $div = $(this).siblings(".list-item");
		        if ($(this).parent().hasClass("selected")) {
		            $div.slideUp(600);
		            $(this).parent().removeClass("selected");
		        }
		        if ($div.is(":hidden")) {
		            $("#juheweb li").find(".list-item").slideUp(600);
		            $("#juheweb li").removeClass("selected");
		            $(this).parent().addClass("selected");
		            $div.slideDown(600);
		
		        } else {
		            $div.slideUp(600);
		        }
		    });
		}     
        
   		function loadPage(e, src){
    		$("#iframe").attr("src", src);
    		$($(e).parent()).addClass("list_a").siblings().removeClass("list_a");    
		}
   
       	function hide(){
	       	var id1=document.getElementById("nav1");
	       	var id2=document.getElementById("nav2");
	       	var r=document.getElementById("right");
	       	id1.style.display="none";
	       	id2.style.display="block";
	       	r.style.width="97%";
	       		r.style.left="3%";
       	}

        function show(){
	       	var id1=document.getElementById("nav1");
	       	var id2=document.getElementById("nav2");
	       	var r=document.getElementById("right");
	       	id1.style.display="block";
	       	id2.style.display="none";
	       	r.style.width="84.2%";
	       	r.style.left="216px";
       	}
        
        function updatePassword() {
			window.location.href="${pageContext.request.contextPath}/login/passwordUpdateInit";
		}
        
        function signout() {
        	console.log("admin sign out...");
        	$.ajax({
				url:"${pageContext.request.contextPath}/login/signout",
				cache:false,
				data:{userAccount:"xxxxxxxx",password:"xxxxxxxx"},
				success:function(data){
					if(data.result==1){
						window.location.href="${pageContext.request.contextPath}/login/admin";
					}else{
						alert(data.msg);
					}
				}
			});
        }
        
    </script>
</head>
<body>
	<div class="header" > 
		<div class="header_title"><img src="${pageContext.request.contextPath}/static/images/logo.png"/>注塑之家后台管理系统</div>
		<div class="login">
		     <span class="head"><img src="${pageContext.request.contextPath}/static/images/head_white.png"></span>
		     <span class="username"><%=pageContext.getSession().getAttribute("USERNAME")%></span>
		     <button onfocus=blur() onclick="signout()">退出</button>
		     <button onfocus=blur() onclick="updatePassword()">修改密码</button>
		</div>
	</div>
	<div class="content">
		<div class="left">
			<div class="nav1" style="display:black;" id="nav1">
                 <div class="operate">
                      <ul id="juheweb">
                           <li><div class="nav_bg"><a ></a></div>
                             <div class="list">
                              <span class="xiangmu_icon icon"></span>
                              <sapn class="nav_title">帐号管理</span>
                             </div>
                                <div class="list-item none">
                                    <p><a onclick="loadPage(this, '${pageContext.request.contextPath}/demo')">演示页面</a></p>
                              </div> 
                              <div class="list-item none">
                                    <p><a onclick="loadPage(this, '${pageContext.request.contextPath}/admin/goAdminConsole')">帐号管理</a></p>
                              </div> 
                           </li>
                            <li><div class="nav_bg"><a ></a></div>
                             <div class="list">
                              <span class="xiangmu_icon icon"></span>
                              <sapn class="nav_title">维修服务</span>
                             </div>
                                <div class="list-item none">
                                    <p><a onclick="loadPage(this, '${pageContext.request.contextPath}/repairService/goCreate')">订单生成</a></p>
                              </div>
                              <div class="list-item none">
                                    <p><a onclick="loadPage(this, '${pageContext.request.contextPath}/repairService/queryList')">订单查询</a></p>
                              </div>
                              <div class="list-item none">
                                    <p><a onclick="loadPage(this, '${pageContext.request.contextPath}/repairService/manageTasks')">订单管理</a></p>
                              </div>  
                           </li>
                           <li><div class="nav_bg"><a ></a></div>
                             <div class="list">
                              <span class="xiangmu_icon icon"></span>
                              <sapn class="nav_title">会员管理</span>
                             </div>
                             	<div class="list-item none">
                                    <p><a onclick="loadPage(this, '${pageContext.request.contextPath}/member/goList')">会员查询</a></p>
                                </div>
                                <div class="list-item none">
                                    <p><a onclick="loadPage(this, '${pageContext.request.contextPath}/member/checkList')">会员认证</a></p>
                                </div>
                                <div class="list-item none">
                                    <p><a onclick="loadPage(this, '${pageContext.request.contextPath}/accountsetting/setting')">会员黑名单</a></p>
                                </div>
                           </li>
                           
                           <li><div class="nav_bg"><a></a></div>
                             <div class="list">
                              <span class="xiangmu_icon icon"></span>
                              <sapn class="nav_title">提现管理</span>
                             </div>
 								<div class="list-item none">
                                    <p><a onclick="loadPage(this, '${pageContext.request.contextPath}/withDraw/goApplicants')">待审核</a></p>
                              	</div>
                              	<div class="list-item none">
                                    <p><a onclick="loadPage(this, '${pageContext.request.contextPath}/withDraw/goAuditPay')">待打款</a></p>
                              	</div>
                              	<div class="list-item none">
                                    <p><a onclick="loadPage(this, '${pageContext.request.contextPath}/withDraw/goPayment')">已打款</a></p>
                              	</div>
                           </li>
                           <li><div class="nav_bg"><a></a></div>
                            <div class="list">
                              <span class="xiangmu_icon icon"></span>
                              <sapn class="nav_title">消息管理</span>
                             </div>
                              	<div class="list-item none">
                                    <p><a onclick="loadPage(this, '${pageContext.request.contextPath}/messages')">消息列表</a></p>
                              	</div>
                           </li>
                           <li><div class="nav_bg"><a></a></div>
                            <div class="list">
                              <span class="xiangmu_icon icon"></span>
                              <sapn class="nav_title">代码表管理</span>
                             </div>
                              	<div class="list-item none">
                                    <p><a onclick="loadPage(this, '${pageContext.request.contextPath}/code/tview')">代码类型管理</a></p>
                              	</div>
                              	<div class="list-item none">
                                    <p><a onclick="loadPage(this, '${pageContext.request.contextPath}/code/cview')">代码管理</a></p>
                              	</div>
                           </li>
                       </ul>
                  </div>
                       <script type="text/javascript" language="javascript">navList(12);</script>
			           <div class="shensuo_big"> <img src="${pageContext.request.contextPath}/static/images/shensuo_big.png" onclick="hide();" >
			           </div>
			</div>
                                     <!-- nav1 ç»æ-->
                                    <!-- nav2 å¼å§-->
			<div class="nav2" style="display:none;" id="nav2">
            <span class="xiangmu_icon icon2 "></span><br/>
              <span class="xiangmu_icon icon2 "></span><br/>
                <span class="xiangmu_icon icon2 "></span><br/>
                  <span class="xiangmu_icon icon2 "></span><br/>
                               <span class="xiangmu_icon icon2 "></span><br/><br/>
               <div class="shensuo_small"><img src="${pageContext.request.contextPath}/static/images/shensuo_small.png" onclick="show();" ></div>
        </div>
		                            <!-- nav2 ç»æ-->
		</div>
		<div class="right" id="right">     
			<iframe id="iframe" src="${pageContext.request.contextPath}/demo"></iframe>
		</div>
	</div>
</body>
</html>