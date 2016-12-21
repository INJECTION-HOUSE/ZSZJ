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
		$("#applyBtn").bind("click", function () {
	    	console.log("upload new profile image...");
	    	$.ajax({
				url:"${pageContext.request.contextPath}/personCenter/applyWithdraw",
				cache:false,
				success:function(data){
					if(data.result==1){
						$("#withdrawtxtId").html("0元");
						alert(data.msg);
					}else{
						alert(data.msg);
					}
				}
			});
	    });
		
		loadIncomeRecords();
	})
	
	function loadIncomeRecords() {
		$.ajax({
			url: "${pageContext.request.contextPath}/personCenter/listMyIncomeRecords",
			type: "GET",
			data: {pageIndex: (pageNum-1)},
			dataType: "json",     
			contentType: "application/json; charset=utf-8",
			success: function (data) {
            	var size = data.model.length;
            	totalSize = data.total;
            	pageNum = pageNum;
            	console.log("number of income records : " + size);
            	loadPaginationView();
				if(size > 0) {
					$("#incomeGridList").html('');
	            	$.each(data.model, function (index, item) {  
	                    //循环获取数据    
	                    var orderNumber = data.model[index].orderNumber;  
	                    var incomeTime = data.model[index].incomeTime;  
	                    var income = data.model[index].income;  
	                    var tradefee = data.model[index].tradeAmount; 
	                    var withDraw = data.model[index].withDraw;
	                    var vendor = data.model[index].vendor;
	                    var sourceFrom = data.model[index].sourceFrom;
	                    var sf = "PC端";
	                    if(sourceFrom == 1) {
	                    	sf = "微信端";
	                    }
	                    
	                    var item = "<li><h3>" + data.model[index].orderTitle + "</h3>";
	                    item += "<span class='biaohao'>订单编号 : </span><span>" + orderNumber + "</span>";
	                    item += "<span class='money1'>成交金额 : <em>￥" + tradefee + "</em><span class='money2'>实际收益 : <em>￥" + income + "</em></span></span>"
	                    item += "<div class='clear'></div>";
	                    item += "<span class='earnings_sort'>收入：" + (withDraw ? "已提现" : "现金收入") + "</span><span>发布商：" + vendor + "</span>";
	                    item += "<span class='earnings_origin'>订单来源：" + sf + "<span class='earnings_date'>收益时间：" + dateFormatter(incomeTime) + "</span></span></li>";
	                    $("#incomeGridList").append(item);  
	                    
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
		<div class="earnings">
			 <div class="earnings_rihgt">
					  <div class="earnings_title">
						   <h3>可提现收益：<span id="withdrawtxtId" class="withdraw">${availcash}元</span></h3>
						   <input id="applyBtn" type="button" value="申请提现" class="apply" />
					  </div>
					  <div class="clear"></div>
					  <div class="earnings_content">
								<ul class="earnings_ul" id="incomeGridList">
								</ul>
								<div class="pagination">
 									<ul id="paginationViewId">
									</ul>
								<div class="clear"></div>
						   </div>
				 </div>
				 <div class="clear"></div>
		    </div>
		</div>
	</div>
</body>
</html>