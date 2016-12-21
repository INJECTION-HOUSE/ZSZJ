<%@ page pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
   <meta name="description" content="">
   <meta name="keywords" content="">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/pc/zszjmain.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.easyui.min.js"></script>
   <script type="text/javascript">
   		var numOfInbox = 0;
   		var numOfOutbox = 0;
        $(function(){
		    $('.per_top span').click(function(){
			     var index=$(this).index();
			     if(index == 0) {
			    	 $(".per_middle_right").show();
			    	 $("#clearInboxId").show();
			     } else {
			    	 $(".per_middle_right").hide();
			    	 $("#clearInboxId").hide();
			     }
			     $(this).addClass('active4').siblings().removeClass('active4');
				 $('.per_letter .per_letter_list .per_tab').eq(index).show().siblings().hide();
			});
		    loadInboxItems();
		    loadOutboxItems();
		    
		     var screen= $(".screen" , parent.document);
			 var tanchu= $(".tanchu" , parent.document);
			 var sHeight=screen.height();
			 var sWidth=screen.width();
			 screen.css('height',sHeight);
			 screen.css('width',sWidth);
			 
		     var iHeight=$("html" , parent.document).height();
			 var tanchuHeight=tanchu.height();
			 var iWidth=$("html" , parent.document).width();
			 
			 var tanchuWidth=tanchu.outerWidth();
			 var halfTop=(iHeight-tanchuHeight)/2;
			 var halfLeft=(iWidth-tanchuWidth)/2;
			
			 tanchu.css('top',halfTop+'px');
			 tanchu.css('left',halfLeft+'px');
		    
			 var pdocument = parent.document;
			 $('.per_input input').click(function(){
			       screen.show();
				   tanchu.show();
				   $(document.body).css({
					   "overflow-y":"hidden"
				   });
			 });
			 screen.click(function(){
				   $("#usernameId", pdocument).attr("value","");
			       $("#messagetitleId", pdocument).attr("value","");
			       $("#messagecontentId", pdocument).attr("value","");
			       screen.hide();
				   tanchu.hide();
				   $(document.body).css({
					   "overflow-y":"auto"
					});
				   
			 });
			 
			 $('.cancel',parent.document).click(function(){
				 	$("#usernameId", pdocument).attr("value","");
			       	$("#messagetitleId", pdocument).attr("value","");
			       	$("#messagecontentId", pdocument).attr("value","");
			       screen.hide();
				   tanchu.hide();
				   $(document.body).css({
					   "overflow-y":"auto"
					});
			 });
			 
			 
			 $('#sendPrivacymsgBtn',parent.document).click(function(){
					console.log('send the privacy message to other user by nickname or cellphone...');
					$('#fm', pdocument).submit();		   
			});
				 
			$('#fm', parent.document).form({
			   	url:'${pageContext.request.contextPath}/messages/savePrivacyMsg',
			   	onSubmit: function(){
					return $(this).form('validate');
			  	},
			   success:function(data){
				   	data = eval("("+data+")");
					if(data.result==1){
						screen.hide();
					   	tanchu.hide();
					   	$(document.body).css({
						   "overflow-y":"auto"
						});
					   	loadInboxItems();
					    loadOutboxItems();
					}
			   }
			});

		});
        
        function loadOutboxItems() {
		    // request inbox items
			$.ajax({
				url: "${pageContext.request.contextPath}/mymessage/listOutbox",
				type: "GET",
				dataType: "json",     
				contentType: "application/json; charset=utf-8",
				success: function (data) {
			    	var size = data.length;
			    	console.log("number of privacy message : " + size);
					$("#outboxItems").html('');
					if(size > 0) {
			        	$.each(data, function (index, item) {  
			                //循环获取数据    
							var title = data[index].title;  
			                var content = data[index].content;  
			                var type = data[index].type; 
			                var sendDate = data[index].sendDate
			                var item = "<li><span class='per_label'><input type='checkbox' name='outboxchk' id='" + data[index].id + "'><b>" + title + "</b></span>";
			                item += "<span class='per_title'>" + content + "</span><span class='per_date'>" +  dateFormatter(sendDate);
			                item += "</span></li>";
			                $("#outboxItems").append(item);  			           
			            });
			        	
					}
			    }
			});
        }
        
        function loadInboxItems() {
		    // request inbox items
			$.ajax({
				url: "${pageContext.request.contextPath}/mymessage/listInbox",
				type: "GET",
				dataType: "json",     
				contentType: "application/json; charset=utf-8",
				success: function (data) {
			    	var size = data.length;
			    	numOfInbox = size;
			    	console.log("number of privacy message : " + size);
					$("#inboxItems").html('');
					if(size > 0) {
			        	$.each(data, function (index, item) {  
			                //循环获取数据
			                var sender = data[index].sender + "&nbsp;&nbsp;";
							var title = data[index].title;  
			                var content = data[index].content;  
			                var type = data[index].type; 
			                var sendDate = data[index].sendDate
			                var item = "<li><span class='per_label'><input type='checkbox' name='inboxchk' id='" + data[index].id + "'><b>" + sender + "</b></span>";
			                item += "<span class='per_title'>" + content + "</span><span class='per_date'>" +  dateFormatter(sendDate) + "<i>";
			                item += "<img id='inbox-" + data[index].id +"'+ src='${pageContext.request.contextPath}/static/images/closed.png'/></i></span></li>";
			                $("#inboxItems").append(item);  
			               
			             	// bind click event
		                    $("#inbox-" + data[index].id).bind("click", function () {
		                    	removePrivacyMessages(data[index]);
		                    });
			            });
					}
			    }
			});
		    
		    $("#deleteMailItemsId").bind("click", function() {
		    	var outboxtxt = $('.per_top .active4').text();
		    	var selectedId = 0;
		    	var count = 0;
		    	if(outboxtxt === '发件箱') {
			    	var selecteditems = document.getElementsByName("outboxchk");
			    	for(var i=0; i<selecteditems.length; i++) {
			    		var selected = selecteditems[i].checked;
			    		if(selected) {
			    			selectedId =  selecteditems[i].id;
			    			count++;
			    		}
			    	}
		    	}
		    	else {
		    		var selecteditems = document.getElementsByName("inboxchk");
			    	for(var i=0; i<selecteditems.length; i++) {
			    		var selected = selecteditems[i].checked;
			    		if(selected) {
			    			selectedId =  selecteditems[i].id;
			    			count++;
			    		}
			    	}
		    	}
		    	
		    	if(count == 1) {
		    		var retVal = confirm("你确定要删除吗?");
			    	if(retVal == true) {
			    		var item = {id:selectedId};
			    		removePrivacyMessages(item);
			    	}
		    	}
		    	else if(count > 1) {
		    		alert("不支持多选...");
		    	}
		    	else {
		    		alert("请选择以后再删除...");
		    	}
		    });
		    
		    $("#clearInboxId").bind("click", function() {
		    	if(numOfInbox == 0) {
		    		return;
		    	}
		    	var retVal = confirm("你确定要清空收件箱吗?");
		    	if(retVal == true) {
					$.ajax({  
			            url : '${pageContext.request.contextPath}/mymessage/clearInbox', 
			            type: 'DELETE', 
			            dataType : 'json',  
			            contentType: "application/json",
			            success : function(r) {  
			                if (r.result) { 
			                	loadInboxItems();   
			                } else {  
			                }  
			            }  
			        });
		    	}  
		    });
        }
        
		function removePrivacyMessages(item) {
			$.ajax({  
	            url : '${pageContext.request.contextPath}/mymessage/deletePrivacy', 
	            type: 'DELETE', 
	            data:JSON.stringify(item),
	            dataType : 'json',  
	            contentType: "application/json",
	            success : function(r) {  
	                if (r.result) { 
			    		loadOutboxItems();
			    		loadInboxItems();
	                } else {  
	                }  
	            }  
	        });  
		}
        
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
    <div class="per_letter">
	      <div class="per_top">
		        <span class="active4">收件箱</span>
				<span>发件箱</span>
				<span class="per_input">
					  <input type="button" value="发私信" />
				</span>
		  </div>
		  <div class="per_middle">
		        <div class="per_middle_left">
						<select>
							  <option>下拉勾选</option>
					   </select>
					   <select>
							  <option>标记为</option>
					   </select>
					   <input id="deleteMailItemsId" type="button" value="删除" />
					   <input id="clearInboxId" type="button" value="清空收件箱" />
				</div>
				<div class="clear"></div>
		  </div>
          <div class="per_letter_list">
		        <div class="per_tab">
				      <ul id="inboxItems">
					  </ul>
				</div>
				<div class="per_tab" id="" style="display:none;">
				      <ul id="outboxItems">
					  </ul>
				</div>
				</div>
	</div>
</body>
</html>