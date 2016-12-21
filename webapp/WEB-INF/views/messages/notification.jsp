<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
    <meta charset="UTF-8"/>	
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>
	<title>数据演示程序</title>
	<!-- 加载jquery-easyui -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/icon.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
	<script>
		$(function() {
			$.ajax({
			url: "${pageContext.request.contextPath}/messages/listNotices",
			type: "GET",
			dataType: "json",     
			contentType: "application/json; charset=utf-8",
			success: function (data) {
				// http://blog.csdn.net/smartsmile2012/article/details/17220413
            	var size = data.length;
            	console.log("number of notify : " + size);
				if(size > 0) {
					$("#notifylist").html('');
	            	$.each(data, function (index, item) {  
	                    //循环获取数据    
	                    var title = data[index].title;  
	                    var content = data[index].content;  
	                    var type = data[index].type;  
	                    var item = "<div class='row'><div class='col-md-3'><h4>" + title + "</h4></div>";
	                    item += "<div class='col-md-7 hidden-xs'><p>" + content + "</p></div>";
	                    item += "<div class='col-md-2'><button id='sysny-"+ data[index].id+ "' type='button' class='btn btn-primary'>编辑</button>";
	                    item += "&nbsp;&nbsp;<button id='sysdel-"+ data[index].id+ "' type='button' class='btn btn-danger'>删除</button></div>";
	                    $("#notifylist").append(item);  
	                    
	                    // bind click event
	                    $("#sysny-" + data[index].id).bind("click", function () {
	                    	editNotice(data[index].id);
	                    });
	                    
	                    $("#sysdel-" + data[index].id).bind("click", function () {
	                    	deleteNotice(data[index]);
	                    });
	                    
	                }); 
				}
            }
			});
			
			$("#createNoticeBtn").bind("click", function () {
            	console.log("create system notice...");
    			$("#fm").form('clear');
    			$("#dlg").dialog("open");
    			$('.window-header').find('.panel-title').text('新增');
            });
			
			
			$('#fm').form({
		    	url:'${pageContext.request.contextPath}/messages/saveNotice',
		    	onSubmit: function(){
				return $(this).form('validate');
		   	},
		    success:function(data){
		    	data = eval("("+data+")");
				if(data.result==1){
					$.messager.alert("成功","成功","info",reload);
				}else{
					$.messager.alert("失败：",data.msg,"error");
				}
				$("#dlg").dialog('close');
		    }
			});
			
		});
		
		function editNotice(id) {
			addFlag=false;
			$("#fm").form('load','${pageContext.request.contextPath}/messages/editNotice?id='+id);
			$("#dlg").dialog("open");
			$('.window-header').find('.panel-title').text('编辑');
		}
		
		function saved(){
			$('#fm').submit();
		}
		
		function deleteNotice(item) {
			console.log(item.title);
			$.ajax({  
                url : '${pageContext.request.contextPath}/messages/deleteNotice', 
                type: 'DELETE', 
                data:JSON.stringify(item),
                dataType : 'json',  
                contentType: "application/json",
                success : function(r) {  
                    if (r.success) {  
                        $.messager.show({  
                            msg : r.msg,  
                            title : '成功'  
                        });   
                        reload(); 
                    } else {  
                        $.messager.alert('错误', r.msg, 'error');  
                    }  
                }  
            });  
		}
		
		//重加载
		function reload(){
			$.ajax({
				url: "${pageContext.request.contextPath}/messages/listNotices",
				type: "GET",
				dataType: "json",     
				contentType: "application/json; charset=utf-8",
				success: function (data) {
					// http://blog.csdn.net/smartsmile2012/article/details/17220413
	            	var size = data.length;
	            	console.log("number of notify : " + size);
					if(size > 0) {
						$("#notifylist").html('');
		            	$.each(data, function (index, item) {  
		                    //循环获取数据    
		                    var title = data[index].title;  
		                    var content = data[index].content;  
		                    var type = data[index].type;  
		                    var item = "<div class='row'><div class='col-md-3'><h4>" + title + "</h4></div>";
		                    item += "<div class='col-md-7 hidden-xs'><p>" + content + "</p></div>";
		                    item += "<div class='col-md-2'><button id='sysny-"+ data[index].id+ "' type='button' class='btn btn-primary'>编辑</button>";
		                    item += "&nbsp;&nbsp;<button id='sysdel-"+ data[index].id+ "' type='button' class='btn btn-danger'>删除</button></div>";
		                    $("#notifylist").append(item);  
		                    
		                    // bind click event
		                    $("#sysny-" + data[index].id).bind("click", function () {
		                    	editNotice(data[index].id);
		                    });
		                    
		                    $("#sysdel-" + data[index].id).bind("click", function () {
		                    	deleteNotice(data[index]);
		                    });
		                    
		                }); 
					}
	            }
			});
		}
		
	</script>
</head>
<body>
<div class="container-fluid">
	<div class="row">
		<div class="col-md-3">
			<h4>标题</h4>
		</div>
		<div class="col-md-7">
			<h4>内容</h4>
		</div>
		<div class="col-md-2">
			<h4><button id="createNoticeBtn" type="button" class="btn btn-default">创建通知</button></h4>
		</div>
	</div>
	<div id="notifylist">
		<div class="row">
			<div class="col-md-3">
			   <h4>系统通知 - 网站周年庆活动</h4>
			</div>
			<div class="col-md-7 hidden-xs">
			    <p>11111111111111111111111111111111111111111111111ggggggggggggggggggggggggggggggggggggggggggg</p>
			</div>
			<div class="col-md-2">
				<button type="button" class="btn btn-primary">编辑</button>
				<button type="button" class="btn btn-danger">删除</button>
			</div>
		</div>
	</div>
</div>	
	<div id="dlg" class="easyui-dialog" style="width:500px;height:350px;padding:10px 20px" closed="true" buttons="#dlg-buttons" modal="true">
	<div class="ftitle"> </div>
	<form id="fm" method="post" class="form-horizontal" role="form">
		<input type="hidden" name="id">
		<div id="username" class="fitem form-group diaform">
			<label class="col-md-3 control-label">通知标题:</label>
			<div class="col-md-4" style="padding:0">
			<input name="title" class="easyui-validatebox easyui-textbox" style="width:162px" placeholder="请输入类型名称" required="true" validType="length[6,20]">
			</div>
		</div>
		<div class="fitem form-group diaform">
			<label class="col-md-3 control-label">通知类型:</label>
			<div class="col-md-4" style="padding:0">
				<select id="noticeType" name="type" class="easyui-combobox" style="width:200px" mode="remote"
					url="${pageContext.request.contextPath}/code/listItems?typeName=noticeType"
					valueField="id" textField="name" method="get" editable="false"
					panelHeight="auto">
				</select>
			</div>
		</div>
		<div class="fitem form-group diaform">
			<label class="col-md-3 control-label">通知内容:</label>
			<div class="col-md-4" style="padding:0">
				<textarea name="content" rows="7" cols="50"></textarea>
			</div>
		</div>
	</form>
</div>
<div id="dlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:saved()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:reload(true);">取消</a>
</div>
</body>
</html>
