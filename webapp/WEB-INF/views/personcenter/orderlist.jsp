<%@ page pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
   <meta name="description" content="">
   <meta name="keywords" content="">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/pc/zszjmain.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
	<script type="text/javascript">
		var pageNum = 1;
		var totalSize = 0;
		$(function(){
			$("#winbiddingchartBtn").bind("click", function () {
		    	console.log("generate highchart image...");
		    });
			
			loadWinBiddingTasks();
		})
		
			
	function loadWinBiddingTasks() {
		$.ajax({
			url: "${pageContext.request.contextPath}/personCenter/listWinBiddingTask",
			type: "GET",
			data: {pageIndex: (pageNum-1)},
			dataType: "json",     
			contentType: "application/json; charset=utf-8",
			success: function (data) {
            	var size = data.model.length;
            	totalSize = data.total;
            	pageNum = pageNum;
            	console.log("number of winbidding tasks : " + size);
            	loadPaginationView();
				if(size > 0) {
					$("#winbiddingTaskGrid").html('');
	            	$.each(data.model, function (index, item) {  
	                    //循环获取数据    
	                    var price = data.model[index].totalFee;  
	                    var taskTitle = data.model[index].taskTitle;  
	                    var orderNumber = data.model[index].orderNumber;  
	                    var status = data.model[index].status; 
	                    var address = data.model[index].geoPosition + " " + data.model[index].addressDetail;
	                    var cellphone = data.model[index].cellphone;
	                    var statusDesc = "上门维修中";
	                    if(status == 3) {
	                    	statusDesc = "完工";
	                    }
	                    else if(status == 4) {
	                    	statusDesc = "确认付款（评价）";
	                    }
	                    else if(status == 5) {
	                    	statusDesc = "结束";
	                    }
	                    
	                    var item = "<li><div class='order_wrap'><div class='order_fir'><p class='title'>报价:<em>￥" + price + "</em><a href='${pageContext.request.contextPath}/personCenter/getWinBiddingTask?taskId=" + data.model[index].id + "'>" + taskTitle + "</a></p>";
	                    item += "<p class='order_skills'>上门地址 :<span>" + address + "</span></p></div>"
	                    item += "<div class='order_sec'>状态：" + statusDesc   + "</div>";
	                    item += "<div class='order_thir'><span class='tender'>订单编号 </span><span class='num'>" + orderNumber + "</span></div>";
	                    item += "<div class='order_fou'>联系电话：" + cellphone + "</div></div></li>";
	                    $("#winbiddingTaskGrid").append(item);  
	                    
	                }); 
				}
            }
		});
	}
	
	function dateFormatter(value) {
		console.log("value = " + value);
		var formattedDate = new Date(parseInt(value));
		var d = formattedDate.getDate();
		var m =  formattedDate.getMonth();
		m += 1;  // JavaScript months are 0-11
		var y = formattedDate.getFullYear();
		// var hhmmss = formattedDate.getHours()+":"+formattedDate.getMinutes()+":"+formattedDate.getSeconds();
		return y + "-" + m + "-" + d;
	}
	
	function loadPaginationView() {
		$("#paginationViewId").html('');
		var prePage = "<li><a class='previous pagination_select'>上一页</a></li>"
		var nextPage = "<li><a>下一页</a></li>"
		$("#paginationViewId").append(prePage);
		var rows = Math.ceil(totalSize / 10);
		for(var i=0; i<rows; i++) {
			var item = ""
			if(i == (pageNum - 1)) {
				item += "<li><a class='page page_select'>" + (i+1) + "</a></li>"
			}
			else {
				item += "<li><a class='page' onclick='loadPageData(this," + i + ")'>" + (i+1) + "</a></li>"
			}
			$("#paginationViewId").append(item);
		}
		$("#paginationViewId").append(nextPage);
	}
	
	function loadPageData(ele, page) {
		if(ele){
			$(".page.page_select").removeClass('page_select');
			$(ele).addClass('page_select');
			pageNum = (page + 1);
		}
		loadBidRecord();
	}
	</script>
</head>
<body>
    <div class="main">
		<div class="order">
				 <div class="order_right">
					  <div class="order_title">
						   <h3>中标订单列表</h3>
						   <input type="button" value="月订单走势图" class="records_btn" id="winbiddingchartBtn"/>
					  </div>
					  <div class="clear"></div>
					  <div class="order_content">
						<ul class="order_ul" id="winbiddingTaskGrid">
						</ul>
						<div class="pagination">
							  <ul id="paginationViewId">
							  </ul>
							  <div class="clear"></div>
						</div>
					  </div>
				 </div>
				 <div class="clear"></div>
			<div class="clear"></div>
		</div>
	</div>
</body>
</html>