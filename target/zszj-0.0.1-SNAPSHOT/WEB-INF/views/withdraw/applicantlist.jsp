<%@ page pageEncoding="UTF-8"%>
<html>
<head>
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
				url : "${pageContext.request.contextPath}/withDraw/getWithDrawList?status=0",//加载的URL  
				isField : "id",
				pagination : true,//显示分页  
				width : 900,
				rownumbers:true,
				pageSize : 5,//分页大小   
				pageList:[5,10,15,20],//每页的个数
				title : "待审核",
				columns : [ [ {
					field : 'nickName',
					title : '会员',
					width : 120
				} ,  {
					field : 'realName',
					title : '真实姓名',
					width : 80
				} ,  {
					field : 'mobile',
					title : '手机号码',
					width : 100
				} , 
				
				{
					field : 'withdrawMoney',
					title : '提现金额',
					width : 120
				} 
				, {
					field : 'withdrawTime',
					title : '申请时间',
					formatter : dateFormatter,
					width : 200
				} 
				
				] ],
				toolbar : [  {
					text : '审核',
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
	                    	editWithDraw(rows[0].id);
	                    }
					}
				}  ]
			});
			
			
			$('#fm').form({
		    	url:'${pageContext.request.contextPath}/withDraw/editWithDrawStatus',
		    	onSubmit: function(){
				return $(this).form('validate');
		   	},
		    success:function(data){
		    	data = eval("("+data+")");
				if(data.result==1){
					$.messager.alert("成功", data.msg, "info", reload);
				}else{
					$.messager.alert("失败：",data.msg,"error");
				}
				$("#dlg").dialog('close');
		    }
			});
			
			$("#search").click(function(){
				var params = {
						cellphone:$("#cellphone").val(),
				 
				};
				alert("try to search with keys...");
				datagrid.datagrid('load',params);
			});
			
		});
		
		function editWithDraw(id) {
			addFlag=false;
			$("#fm").form('load','${pageContext.request.contextPath}/withDraw/editWithDraw?id='+id);
			$("#dlg").dialog("open");
			$('.window-header').find('.panel-title').text('审核');
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
		<div class="row" style="padding: 15px">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title"><b>提现管理</b>->待审核-搜索</h3>
				</div>
				<div class="panel-body">
					<table>
						<tr>
							<td>手机号码:</td>
							<td><input id="cellphone" name="cellphone" class="easyui-textbox" style="width:150px"></td>
							<td colspan="2"><a id="search" href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a></td>
						</tr>
					</table>
				</div>
			</div>
  		<div id="line_top_x"></div>
		<table id="demogrid">
		</table>
		</div>
	</div>
	 
	<div id="dlg" class="easyui-dialog" style="width:500px;height:320px;padding:10px 20px" closed="true" buttons="#dlg-buttons" modal="true">
	<div class="ftitle"> </div>
	<form id="fm" method="post" class="form-horizontal" role="form">
		<input type="hidden" name="id">
		<div id="username" class="fitem form-group diaform">
			<label class="col-md-3 control-label">审核人姓名:</label>
			<div class="col-md-4" style="padding:0">
			<input name="auditor" class="easyui-validatebox easyui-textbox" style="width:162px" placeholder="请输入类型名称" required="true" validType="length[3,20]">
			</div>
		</div>
		<div class="fitem form-group diaform">
			<label class="col-md-3 control-label">审核日期:</label>
			<div class="col-md-4" style="padding:0">
			<input name="checkDate" type="text" style="width:162px" class="easyui-datebox form-control" />
			</div>
		</div>
		<div class="fitem form-group diaform">
			<label class="col-md-3 control-label">状态:</label>
			<div class="col-md-4" style="padding:0">
				<select id="statusType" name="status" class="easyui-combobox" style="width:200px" mode="remote"
				url="${pageContext.request.contextPath}/code/listItems?typeName=withdrawType"
				valueField="index" textField="name" method="get" editable="false"
				panelHeight="auto">
				</select>
			</div>
		</div>
		<div class="fitem form-group diaform">
			<label class="col-md-3 control-label">审核意见:</label>
			<div class="col-md-4" style="padding:0">
				<textarea name="desc" rows="4" cols="40"></textarea>
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
