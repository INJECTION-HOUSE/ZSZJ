<%@ page pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
   <meta name="description" content="">
   <meta name="keywords" content="">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/pc/zszjmain.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<script>
	$(function() {
		$.ajax({
		url: "${pageContext.request.contextPath}/mymessage/listMyNotices",
		type: "GET",
		dataType: "json",     
		contentType: "application/json; charset=utf-8",
		success: function (data) {
	    	var size = data.length;
	    	console.log("number of notify : " + size);
			if(size > 0) {
				$("#notifylist").html('');
	        	$.each(data, function (index, item) {  
	                //循环获取数据    
	                var title = data[index].title;  
	                var content = data[index].content;  
	                var type = data[index].type; 
	                var generateTiem = data[index].generateDate
	                var item = "<div class='message_list'><p class='maessage_date'>" + dateFormatter(generateTiem) + "</p>";
	                item += "<div class='message_line'><p>" + title + "</p>";
	                item += "<p>"+ content+ "</p></div></div>";
	                $("#notifylist").append(item);  	                
	            }); 
			}
	    }
		});
	});
	
	function dateFormatter(ntime) {
		console.log("value = " + ntime);
		var formattedDate = new Date(parseInt(ntime));
		var d = formattedDate.getDate();
		var m =  formattedDate.getMonth();
		m += 1;  // JavaScript months are 0-11
		var y = formattedDate.getFullYear();
		var hhmmss = formattedDate.getHours()+":"+formattedDate.getMinutes()+":"+formattedDate.getSeconds();
		return y + "-" + m + "-" + d + " " + hhmmss;
	}
</script>
</head>
<body>
    <div class="message" id="notifylist">
	</div>
</body>
</html>