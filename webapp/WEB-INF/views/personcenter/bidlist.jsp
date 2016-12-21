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
		loadBidRecord();
	});
	var pageNum = 1;
	var totalSize = 0;
	function loadBidRecord() {
		$.ajax({
			url: "${pageContext.request.contextPath}/personCenter/listMyBidRecords",
			type: "GET",
			data: {pageIndex: (pageNum-1)},
			dataType: "json",     
			contentType: "application/json; charset=utf-8",
			success: function (data) {
            	var size = data.model.length;
            	totalSize = data.total;
            	pageNum = pageNum;
            	console.log("number of bid records : " + size);
            	loadPaginationView();
				if(size > 0) {
					$("#bidRecordList").html('');
	            	$.each(data.model, function (index, item) {  
	                    //循环获取数据    
	                    var bidNumber = data.model[index].bidNumber;  
	                    var tasktitle = data.model[index].taskTitle;  
	                    var price = data.model[index].price;  
	                    var bidstatus = "";
	                    if(!data.model[index].bingBid) {
	                    	if(data.model[index].taskstatus == 0) {
	                    		bidstatus = "雇主选标中";
	                    	} else {
		                    	bidstatus = "竞标失败";
	                    	}
	                    }
	                    else {
	                    	bidstatus = "<b style='color:red'>中标</b>";
	                    }
	                    
	                    var item = "<li><div class='toubiao_fir'><p class='title'><a>任务标题:" + tasktitle + "</a></p>";
	                    item += "<p class='toubiao_skills'>投标时间:<span>"+ dateFormatter(data.model[index].bidTime) + "</span></p></div>";
	                    item += "<div class='toubiao_sec'>报价:" + price + "</div>"
	                    item += "<div class='toubiao_thir'><span class='tender'>投标编号</span><span class='num'>" + bidNumber + "</span></div>";
	                    item += "<div class='toubiao_fou'>状态:"+ bidstatus + "</div>";
	                    item += "<div class='clear'></div></li>";
	                    $("#bidRecordList").append(item);  
	                    
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
		var hhmmss = formattedDate.getHours()+":"+formattedDate.getMinutes()+":"+formattedDate.getSeconds();
		return y + "-" + m + "-" + d + " " + hhmmss;
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
		<div class="toubiao">
					  <div class="toubiao_title">
						   <h3>我的投标记录</h3>
					  </div>
					  <div class="clear"></div>
					  <div class="toubiao_content">
								<ul id="bidRecordList" class="toubiao_ul">
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
</body>


</html>