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
			datagrid = $('#withdrawGrid').datagrid({
				url : "${pageContext.request.contextPath}/withDraw/getWithDrawList?status=2",//加载的URL  
				isField : "id",
				pagination : true,//显示分页  
				width : 1200,
				rownumbers:true,
				pageSize : 5,//分页大小   
				pageList:[5,10,15,20],//每页的个数
				title : "打款记录",
				columns : [ [ {
					field : 'nickName',
					title : '会员昵称',
					width : 120
				} ,  {
					field : 'realName',
					title : '会员姓名',
					width : 80
				} ,  {
					field : 'mobile',
					title : '手机号码',
					width : 100
				} ,  {
					field : 'withdrawMoney',
					title : '金额',
					width : 80
				} 
				, {
					field : 'withdrawTime',
					formatter : dateFormatter,
					title : '申请时间',
					width : 160
				} , {
					field : 'auditor',
					title : '审核人',
					width : 80
				}  , {
					field : 'desc',
					title : '审核备注',
					width : 80
				}  , {
					field : 'checkDate',
					title : '审核时间',
					formatter : dateFormatter,
					width : 160
				}   , {
					field : 'payor',
					title : '打款人',
					width : 80
				}   , {
					field : 'payTime',
					title : '打款时间',
					formatter : dateFormatter,
					width : 160
				} 
				
				] ]
			});
			
			$("#search").click(function(){
				var params = {
					cellphone:$("#cellphone").val(),
				};
				alert("try to search with keys...");
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
		<div class="row" style="padding: 15px">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title"><b>提现管理</b>->已打款记录</h3>
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
		<table id="withdrawGrid">
		</table>
		</div>
	</div>
</body>
</html>
