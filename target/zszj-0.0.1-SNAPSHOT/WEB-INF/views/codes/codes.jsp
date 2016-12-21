<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
    <meta charset="UTF-8"/>	
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>
	<title>代码表管理</title>
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
				url : "${pageContext.request.contextPath}/code/listItems",//加载的URL  
				isField : "id",
				pagination : true,//显示分页  
				width : 900,
				rownumbers:true,
				pageSize : 5,//分页大小   
				pageList:[5,10,15,20],//每页的个数
				title : "代码条目管理",
				columns : [ [ {
					field : 'name',
					title : '名称',
					width : 200
				}, {
					field : 'desc',
					title : '描述',
					width : 300
				}, {
					field : 'index',
					title : '索引号',
					width : 100
				}, {
					field : 'typeName',
					title : '代码类型',
					width : 150
				}] ],
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
	                                    url : '${pageContext.request.contextPath}/code/deleteItem', 
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
		    	url:'${pageContext.request.contextPath}/code/saveItem',
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
					typeName:$("#typeName").val(),
					codeName:$("#codeName").val(),
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
		
		function editCodeType(id) {
			addFlag=false;
			$("#fm").form('load','${pageContext.request.contextPath}/code/editItem?id='+id);
			$("#dlg").dialog("open");
			$('.window-header').find('.panel-title').text('编辑');
		}
		
		function saved(){
			$('#fm').submit();
		}
		
		//重加载
		function reload(closeFlag){
			if(addFlag){
				$("#fm").form('clear');
			}
			var params = {
					typeName:$("#typeName").val(),
					codeName:$("#codeName").val(),
				};
			datagrid.datagrid('load',params);
		}
		
	</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row" style="padding:15px">
		<div class="panel panel-default">
			<div class="panel-heading">
		     	<h3 class="panel-title">类别查询</h3>
		    </div>
		    <div class="panel-body">
		    	<table>
		    		<tr>
		    			<td>名称:</td>
		    			<td><input id="codeName" name="codeName" class="easyui-textbox" style="width:150px"></td>
		    			<td>类别:</td>
		    			<td><input id="typeName" name="typeName" class="easyui-textbox" style="width:150px"></td>
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
	
	<div id="dlg" class="easyui-dialog" style="width:500px;height:280px;padding:10px 20px" closed="true" buttons="#dlg-buttons" modal="true">
	<div class="ftitle"> </div>
	<form id="fm" method="post" class="form-horizontal" role="form">
		<input type="hidden" name="id">
		<div id="username" class="fitem form-group diaform">
			<label class="col-md-3 control-label">名称:</label>
			<div class="col-md-4" style="padding:0">
			<input name="name" class="easyui-validatebox easyui-textbox" style="width:200px" placeholder="请输入类型名称" required="true" validType="length[3,20]">
			</div>
		</div>
		<div class="fitem form-group diaform">
			<label class="col-md-3 control-label">描述:</label>
			<div class="col-md-4" style="padding:0">
			<input name="desc" class="easyui-validatebox easyui-textbox" style="width:200px" placeholder="请输入类型描述" required="true" validType="length[3,20]">
			</div>
		</div>
		<div class="fitem form-group diaform">
			<label class="col-md-3 control-label">索引编号:</label>
			<div class="col-md-4" style="padding:0">
			<input name="index" type="text" style="width:200px" class="easyui-textbox form-control" />
			</div>
		</div>
		<div class="fitem form-group">
			<label class="col-md-3 control-label">分类类型:</label>
			<div class="col-md-4" style="padding:0">
			<select id="helpType" name="codeTypeid" class="easyui-combobox" style="width:200px" mode="remote"
				url="${pageContext.request.contextPath}/code/listType"
				valueField="id" textField="typeName" method="get" editable="false"
				panelHeight="auto">
		</select>
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
