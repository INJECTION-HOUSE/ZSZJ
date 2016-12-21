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
			url : "${pageContext.request.contextPath}/member/getMemberCheckList",//加载的URL  
			isField : "id",
			pagination : true,//显示分页  
			width : 900,
			rownumbers:true,
			pageSize : 5,//分页大小   
			pageList:[5,10,15,20],//每页的个数
			title : "会员认证管理",
			columns : [ [ {
				field : 'applicant',
				title : '申请人',
				width : 120
			} , {
				field : 'memberNickName',
				title : '昵称',
				width : 120
			} 
			, {
				field : 'memberCellPhone',
				title : '手机号码',
				width : 120
			} 
			, {
				field : 'auditor',
				title : '审核人',
				width : 120
			}, {
				field : 'auditDate',
				title : '审核日期',
				width : 120
			} ,{
				field : 'auditStatus',
				title : '审核状态',
				width : 120,
				formatter: function(value, row, index) {
					if(value == 0) {
						return "未审核";
					} else if(value == 1){
						return "通过审核";
					} else if(value == 2){
						return "审核失败";
					}
				},
			} ,{
				field : 'certType',
				title : '认证类型',
				width : 120,
				formatter: function(value, row, index) {
					if(value == 0) {
						return "个人会员";
					} else if(value == 1){
						return "企业会员";
					} else {
						return "unknown";
					}
				},
			}]],
			toolbar : [{
				text : '信息审核',
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
                    	goAuditMemberPage(rows[0].id);
                    }
				}
			}]
		});
		
		$("#search").click(function(){
			var params = {
				nickName:$("#nickName").val(),
				realName:$("#realName").val(),
			};
			datagrid.datagrid('load',params);
		});
		
	});
	
	function goAuditMemberPage(id) {	 
		var rows=datagrid.datagrid('getSelections');  
		if(rows[0].auditStatus == 0 || rows[0].auditStatus == 0){
			window.location.href = "${pageContext.request.contextPath}/member/goMemberCheckDetail?id="+id;
		}
		else{
			$.messager.alert('警告','该用户当前不需要审核','error');  
		}
	}

</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row" style="padding:15px">
			<div class="panel panel-default">
				<div class="panel-heading">
			     	<h3 class="panel-title">会员认证</h3>
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
