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
			datagrid = $('#demogrid').datagrid({
				url : "${pageContext.request.contextPath}/admin/listAdmin",//加载的URL  
				isField : "id",
				pagination : true,//显示分页  
				width : 900,
				rownumbers:true,
				pageSize : 5,//分页大小   
				pageList:[5,10,15,20],//每页的个数
				title : "管理员用户一览",
				columns : [ [ {
					field : 'userName',
					title : '姓名',
					width : 100
				}, {
					field : 'cellphone',
					title : '手机号码',
					width : 200
				}, {
					field : 'workCardNum',
					title : '工号',
					width : 100
				}, {
					field : 'gender',
					title : '性别',
					width : 50
				}, {
					field : 'valid',
					title : '合法性',
					width : 100
				},  {
					field : 'role',
					title : '角色',
					width : 100
				} ] ],
				toolbar : [{
					text : '添加',
					iconCls : 'icon-add',
					handler : function() {
						addCodeType();
					}
				}, {
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
	                    	editCodeType(rows[0].id);
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
	                                    url : '${pageContext.request.contextPath}/admin/deleteAdmin', 
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
		    	url:'${pageContext.request.contextPath}/admin/saveAdmin',
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
		
		function addCodeType() {
			addFlag=true;
			$("#fm").form('clear');
			$("#dlg").dialog("open");
			$('.window-header').find('.panel-title').text('添加用户');
		}
		
		function editCodeType(id) {
			addFlag=false;
			$("#fm").form('load','${pageContext.request.contextPath}/admin/editAdmin?id='+id);
			$("#dlg").dialog("open");
			$('.window-header').find('.panel-title').text('修改资料');
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
			<table id="demogrid">
			</table>
		</div>
	</div>
	
	<div id="dlg" class="easyui-dialog" style="width:500px;height:400px;padding:10px 20px" closed="true" buttons="#dlg-buttons" modal="true">
	<div class="ftitle"> </div>
	<form id="fm" method="post" class="form-horizontal" role="form">
		<input type="hidden" name="id">
		<div id="username" class="fitem form-group diaform">
			<label class="col-md-3 control-label">姓名:</label>
			<div class="col-md-4" style="padding:0">
			<input name="userName" class="easyui-validatebox easyui-textbox" style="width:162px" placeholder="请输入员工姓名" required="true" validType="length[2,16]">
			</div>
		</div>
		<div class="fitem form-group diaform">
			<label class="col-md-3 control-label">手机号码:</label>
			<div class="col-md-4" style="padding:0">
			<input name="cellphone" class="easyui-validatebox easyui-textbox" style="width:162px" placeholder="员工手机号码" required="true" validType="length[11,11]">
			</div>
		</div>
		<div class="fitem form-group diaform">
			<label class="col-md-3 control-label">工号:</label>
			<div class="col-md-4" style="padding:0">
			<input name="workCardNum" type="text" style="width:162px" class="easyui-textbox form-control" />
			</div>
		</div>
		<div class="fitem form-group diaform">
			<label class="col-md-3 control-label">密码:</label>
			<div class="col-md-4" style="padding:0">
			<input id="password" name="password" type="password" required="true" style="width:162px" class="easyui-validatebox easyui-textbox form-control" validType="length[8,12]"/>
			</div>
		</div>
		<div class="fitem form-group diaform">
			<label class="col-md-3 control-label">确认密码:</label>
			<div class="col-md-4" style="padding:0">
			<input name="repassword" type="password" style="width:162px" required="true" class="easyui-validatebox"  validType="equalTo['#password']" invalidMessage="两次输入密码不匹配"/>
			</div>
		</div>
		<div class="fitem form-group diaform">
			<label class="col-md-3 control-label">生日:</label>
			<div class="col-md-4" style="padding:0">
			<input name="birthday" type="text" style="width:162px" class="easyui-datebox form-control" />
			</div>
		</div>
		<div class="fitem form-group">
			<label class="col-md-3 control-label">角色:</label>
			<div class="col-md-4" style="padding:0">
			<select id="role" name="role" class="easyui-combobox" style="width:200px" mode="remote"
				url="${pageContext.request.contextPath}/admin/listRole"
				valueField="type" textField="type" method="get" editable="false"
				panelHeight="auto">
		</select>
		</div>
		</div>
		<div class="fitem form-group">
			<label class="col-md-3 control-label">是否有效:</label>
			<div class="col-md-4" style="padding:0">
				<input name="valid" type="radio" class="easyui-validatebox" checked="checked" required="true" value="true">有效
				<input name="valid" type="radio" class="easyui-validatebox" required="true" value="false">无效
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
