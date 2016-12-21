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
		var datagrid;  
		var addFlag;
		$(function() {
			datagrid = $('#inboxgrid').datagrid({
				url : "${pageContext.request.contextPath}/messages/listInbox",//加载的URL  
				isField : "id",
				pagination : true,//显示分页  
				width : 900,
				rownumbers:true,
				pageSize : 5,//分页大小   
				pageList:[5,10,15,20],//每页的个数
				title : "我的收件箱",
				columns : [ [ {
					field : 'title',
					title : '标题',
					width : 100
				}, {
					field : 'content',
					title : '内容',
					width : 300
				}, {
					field : 'receiver',
					title : '接受者',
					width : 100
				}, {
					field : 'read',
					title : '状态',
					formatter: function(value, row, index) {
						if(value) {
							return "已读";
						} else {
							return "未读";
						}
					},
					width : 50
				}] ],
				toolbar : [{
					text : '新私信',
					iconCls : 'icon-add',
					handler : function() {
						addMessages();
					}
				}, {
					text : '阅读',
					iconCls : 'icon-add',
					handler : function() {
						var rows=datagrid.datagrid('getSelections');  
	                    if(rows.length<=0)  
	                    {  
	                        $.messager.alert('警告','您没有选择','error');  
	                    }  
	                    else if(rows.length>1)  
	                    {  
	                        $.messager.alert('警告','不支持批量选择','error');  
	                    }  
	                    else  
	                    {  
	                    	readMyMessages(rows[0].id);
	                    }
					}
				}, {
					text : '标记已读',
					iconCls : 'icon-edit',
					handler : function() {
						var rows=datagrid.datagrid('getSelections');  
	                    if(rows.length<=0)  
	                    {  
	                        $.messager.alert('警告','您没有选择','error');  
	                    }  
	                    else if(rows.length>1)  
	                    {  
	                        $.messager.alert('警告','不支持批量选择','error');  
	                    }  
	                    else  
	                    {  
	                    	$.ajax({  
                                url : '${pageContext.request.contextPath}/messages/markAsReadMsg', 
                                type: 'POST', 
                                data:JSON.stringify(rows[0]),
                                dataType : 'json',  
                                contentType: "application/json",
                                success : function(r) {  
                                    if (r.success) {  
                                        datagrid.datagrid('acceptChanges');  
                                        $.messager.show({  
                                            msg : r.msg,  
                                            title : '成功'  
                                        });   
                                        datagrid.datagrid('reload');  
                                    } else {  
                                        $.messager.alert('错误', r.msg, 'error');  
                                    }  
                                    datagrid.datagrid('unselectAll');  
                                }  
                            });  
	                    }
					}
				},  {
					text : '删除私信',
					iconCls : 'icon-remove',
					handler : function() {
						var rows=datagrid.datagrid('getSelections');  
	                    if(rows.length<=0)  
	                    {  
	                        $.messager.alert('警告','您没有选择','error');  
	                    }  
	                    else if(rows.length>1)  
	                    {  
	                        $.messager.alert('警告','不支持批量删除','error');  
	                    }  
	                    else  
	                    {  
	                        $.messager.confirm('确定','您确定要删除吗',function(t){  
	                            if(t)  
	                            {  		                                  
	                                $.ajax({  
	                                    url : '${pageContext.request.contextPath}/messages/deletePrivacy', 
	                                    type: 'DELETE', 
	                                    data:JSON.stringify(rows[0]),
	                                    dataType : 'json',  
	                                    contentType: "application/json",
	                                    success : function(r) {  
	                                        if (r.success) {  
	                                            datagrid.datagrid('acceptChanges');  
	                                            $.messager.show({  
	                                                msg : r.msg,  
	                                                title : '成功'  
	                                            });   
	                                            datagrid.datagrid('reload');  
	                                        } else {  
	                                            $.messager.alert('错误', r.msg, 'error');  
	                                        }  
	                                        datagrid.datagrid('unselectAll');  
	                                    }  
	                                });  
	                              
	                            }  
	                        })  
	                    }  
					}
				} ]
			});
			
			
			$('#fm').form({
		    	url:'${pageContext.request.contextPath}/messages/savePrivacyMsg',
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
		
		function addMessages() {
			addFlag=true;
			$("#fm").form('clear');
			$("#dlg").dialog("open");
			$('.window-header').find('.panel-title').text('新建私信');
		}
		
		function readMyMessages(id) {
			addFlag=false;
			$("#fm").form('load','${pageContext.request.contextPath}/messages/readMessage?id='+id);
			$("#dlg").dialog("open");
			$('.window-header').find('.panel-title').text('阅读私信');
		}
		
		function saved(){
			$('#fm').submit();
		}
		
		//重加载
		function reload(closeFlag){
			if(addFlag){
				$("#fm").form('clear');
			}
			datagrid.datagrid('reload'); 
		}
		
	</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row" style="padding:10px 20px">
			<table id="inboxgrid">
			</table>
		</div>
	</div>
	<div id="dlg" class="easyui-dialog" style="width:500px;height:280px;padding:10px 20px" closed="true" buttons="#dlg-buttons" modal="true">
	<div class="ftitle"> </div>
	<form id="fm" method="post" class="form-horizontal" role="form">
		<input type="hidden" name="id">
		<div id="title" class="fitem form-group diaform">
			<label class="col-md-3 control-label">标题:</label>
			<div class="col-md-4" style="padding:0">
			<input name="title" class="easyui-validatebox easyui-textbox" style="width:162px" placeholder="请输入标题" required="true" validType="length[2,20]">
			</div>
		</div>
		<div class="fitem form-group diaform">
			<label class="col-md-3 control-label">收件人:</label>
			<div class="col-md-4" style="padding:0">
			<input name="receiver" class="easyui-validatebox easyui-textbox" style="width:162px" placeholder="请输入收件人" required="true" validType="length[2,20]">
			</div>
		</div>
		<div class="fitem form-group diaform">
			<label class="col-md-3 control-label">内容:</label>
			<div class="col-md-4" style="padding:0">
				<textarea name="content" rows="4" cols="50"></textarea>
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
