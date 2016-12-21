<%@ page pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="UTF-8"/>	
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>
	<title>会员列表</title>
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
			datagrid = $('#demogrid').datagrid({
				url : "${pageContext.request.contextPath}/member/getMemberList",//加载的URL  
				isField : "id",
				pagination : true,//显示分页  
				width : 900,
				rownumbers:true,
				pageSize : 5,//分页大小   
				pageList:[5,10,15,20],//每页的个数
				title : "会员列表",
				columns : [ [ {
					field : 'cellphone',
					title : '手机号码',
					width : 120
				} , {
					field : 'role',
					title : '角色',
					formatter: function(value, row, index) {
						if(value === 'unknown') {
							return "未认证";
						} else if(value === 'person'){
							return "个人会员";
						} else {
							return "企业会员";
						}
					},
					width : 120
				},{
					field : 'realName',
					title : '真实姓名',
					width : 120
				} , {
					field : 'nickName',
					title : '会员',
					width : 150
				}
				, {
					field : 'totalCash',
					title : '账户余额',
					width : 120
				}, {
					field : 'level',
					title : '信誉等级',
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
	                    	window.location.href = "${pageContext.request.contextPath}/member/goMemberDetail?id="+rows[0].id;
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
	                                    url : '${pageContext.request.contextPath}/member/deleteMember', 
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
		    	url:'${pageContext.request.contextPath}/code/saveType',
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
			
			$("#search").click(function(){
				var params = {
					nickName:$("#nickName").val(),
					realName:$("#realName").val(),
				};
				// alert("try to search with keys...");
				datagrid.datagrid('load',params);
			});
			
		});
		
		function addCodeType() {
			addFlag=true;
			$("#fm").form('clear');
			$("#dlg").dialog("open");
			$('.window-header').find('.panel-title').text('新增');
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
	<div class="row" style="padding:15px">
		<div class="panel panel-default">
			<div class="panel-heading">
		     	<h3 class="panel-title">会员查询</h3>
		    </div>
		    <div class="panel-body">
				<table>
					<tr>
						<td>昵称:</td>
						<td><input id="nickName" name="nickName" class="easyui-textbox" style="width:150px"></td>
						<td>姓名:</td>
						<td><input id="realName" name="realName" class="easyui-textbox" style="width:150px"></td>
					</tr>
					<tr>
						<td colspan="4"><a id="search" href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
 	<div id="line_top_x"></div>
	<table id="demogrid">
	</table>
</div>	 
</body>
</html>
