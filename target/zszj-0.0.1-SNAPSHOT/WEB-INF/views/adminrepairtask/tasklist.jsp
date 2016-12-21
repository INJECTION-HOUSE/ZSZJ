<%@ page pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="UTF-8"/>	
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>
	<title>维修任务列表</title>
	<!-- 加载jquery-easyui -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/icon.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
 	<style>
		td {
			padding-top: 5px;
			padding-bottom: 5px;
			padding-right: 5px;
		}
		
		td:first-child {
			padding-left: 5px;
			padding-right: 0;
		}
	</style>
	<script>
		var datagrid;  
		var addFlag;
		$(function() {
			datagrid = $('#taskListGrid').datagrid({
				url : "${pageContext.request.contextPath}/repairService/listTasks",//加载的URL  
				isField : "id",
				pagination : true,//显示分页  
				width : 1300,
				rownumbers:true,
				pageSize : 5,//分页大小   
				pageList:[5,10,15,20],//每页的个数
				title : "维修任务列表",
				columns : [ [ {
					field : 'orderNumber',
					title : '订单编号',
					width : 200
				} ,{
					field : 'taskTitle',
					title : '标题',
					width : 120
				} , {
					field : 'status',
					title : '状态',
					formatter: function(value, row, index) {
						if(value == 0) {
							return "竞标中";
						} else if(value == 1){
							return "选标中";
						} else if(value == 2){
							return "上面工作中";
						}else {
							return "确认验收中";
						}
					},
					width : 120
				},{
					field : 'enterpriser',
					title : '企业名称',
					width : 120
				} , {
					field : 'nickname',
					title : '昵称',
					width : 150
				} , {
					field : 'cellphone',
					title : '联系电话',
					width : 150
				}
				, {
					field : 'deadline',
					title : '投标截止日期',
					formatter : dateFormatter,
					width : 200
				}, {
					field : 'geoPosition',
					title : '所在城市',
					width : 120
				} 
				
				] ],
				toolbar : [{
					text : '编辑',
					iconCls : 'icon-edit',
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
	                    	window.location.href = "${pageContext.request.contextPath}/repairService/manageTasks?id="+rows[0].id;
	                    }
					}
				},  {
					text : '删除',
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
	                                    url : '${pageContext.request.contextPath}/repairService/deleteTask', 
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
			
			
			$("#search").click(function(){
				var params = {
					cellphone:$("#cellphone").val(),
					orderNumber:$("#ordernumber").val(),
				};
				datagrid.datagrid('load',params);
			});
			
		});
  
		function dateFormatter(value, row, index) {
			console.log("value = " + value);
			var formattedDate = new Date(parseInt(value));
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
<div class="container-fluid">
	<div class="row" style="padding:15px">
		<div class="panel panel-default">
			<div class="panel-heading">
		     	<h3 class="panel-title">维修任务查询</h3>
		    </div>
		    <div class="panel-body">
				<table>
					<tr>
						<td>订单号:</td>
						<td><input id="ordernumber" name="ordernumber" class="easyui-textbox" style="width:150px"></td>
						<td>联系电话:</td>
						<td><input id="cellphone" name="cellphone" class="easyui-textbox" style="width:150px"></td>
					</tr>
					<tr>
						<td colspan="4"><a id="search" href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
 	<div id="line_top_x"></div>
	<table id="taskListGrid">
	</table>
</div>	 
</body>
</html>
