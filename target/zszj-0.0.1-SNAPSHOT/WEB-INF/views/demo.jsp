<%@ page pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="UTF-8"/>	
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>
	<title>数据演示程序</title>
	<!-- 加载jquery-easyui -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/icon.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/fileupload/vendor/jquery.ui.widget.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/fileupload/load-image.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/fileupload/canvas-to-blob.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/fileupload/jquery.blueimp-gallery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/fileupload/jquery.iframe-transport.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/fileupload/jquery.fileupload.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/fileupload/cors/jquery.xdr-transport.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/fileupload/jquery.fileupload-process.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/fileupload/jquery.fileupload-validate.js"></script>
	<script>
		var datagrid;  
		$(function() {
			datagrid = $('#demogrid').datagrid({
				url : "${pageContext.request.contextPath}/demo/list",//加载的URL  
				isField : "id",
				pagination : true,//显示分页  
				width : 900,
				rownumbers:true,
				pageSize : 5,//分页大小   
				pageList:[5,10,15,20],//每页的个数
				title : "软件框架版本信息",
				columns : [ [ {
					field : 'name',
					title : '名称',
					width : 100
				}, {
					field : 'desc',
					title : '描述',
					width : 100
				}, {
					field : 'version',
					title : '版本',
					width : 200
				}, {
					field : 'creator',
					title : '创建者',
					width : 100
				}] ],
				toolbar : [ {
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
	                                    url : '${pageContext.request.contextPath}/demo/delete', 
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
			
			$("#file").fileupload({
				iframe:true,			
				url:'${pageContext.request.contextPath}/fileUpload/picture',
				maxFileSize:2097152, //(最大2M)字节
				acceptFileTypes:/(\.|\/)('|bmp|jpeg|jpg|png')$/i,
				maxNumberofFiles:1,
			}).on('fileuploadprocessalways',function (e,data){
				if (data.files.error) {
					if ('File is too large' == data.files[0].error) {
					alert("照片大小不能超过2M");
					}else if('File type not allowed' == data.files[0].error){
						alert("上传的图片格式必须为jpg/jpeg/png/bmp");
					}						
				}else{
					data.submit();	
				}	
				}).on('fileuploaddone',function(e,data){
					var resultImg = '<img style="width:200px;height:200px;cursor:pointer;" id="img1" src="${pageContext.request.contextPath}/upload/'+ data.result.path + '"/>';
					$("#demo_image_div").html(resultImg);
				
			});
			
		});
		
		function clickImageUpload(){
			$("#file").click();
		}
		
	</script>
</head>
<body>
	<div class="body">
		<table id="demogrid">
		</table>
	</div>
	<br>
	<div class="row">
		<input style="display: none;" type="file" id="file" name="imgs" />
		<button type="button" class="btn btn-primary btn-sm" style="width: 100px;" onclick="clickImageUpload()">上传图像文件</button>
	</div>
	<div id="demo_image_div"></div>
</body>
</html>
