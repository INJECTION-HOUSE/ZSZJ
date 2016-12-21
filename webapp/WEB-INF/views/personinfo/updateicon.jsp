<%@ page pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
   <meta name="description" content="">
   <meta name="keywords" content="">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/pc/zszjmain.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
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
	$(function(){
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
				$("#avatarImg").attr("src","${pageContext.request.contextPath}/upload/"+data.result.path);
				$("#avatarPathId").val(data.result.path);
		});
		
		$("#uploadBtn").bind("click", function () {
        	console.log("upload new profile image...");
        	$("#file").click();
        });
		
		$("#saveAvatarBtn").bind("click", function () {
        	console.log("save as new avatar icon...");
        	//调用后台
			$.ajax({
				url:"${pageContext.request.contextPath}/profile/saveAvatar",
				cache:false,
				data:{avatarImage:$("#avatarPathId").val()},
				success:function(data){
					if(data.result==1){
						alert("头像更新成功!");
						return;
					}else{
						alert(data.msg);
						return;
					}
				}
			});		
        });
		
	});
	</script>
</head>
<body>
	<div class="icon">
		<div class="icon_title">
			<h3>修改头像</h3>
		</div>
		<div class="icon_content">
		    <div class="icon_wrap">
			    <div class="icon_wrap_left">
				     <div class="icon_subtitle">
					     <h3>当前头像</h3>
					     <input style="display: none;" type="file" id="file" name="imgs" />
				     </div>
					 <div class="icon_pic">
					     <img id="avatarImg" src="${pageContext.request.contextPath}/upload/${myiconpath}"/>
				     </div>
					 <div class="icon_setting">
					     <input id="uploadBtn" type="button" value="设置新头像" />
				     </div>
				</div>
				<div id="avatarPathId" style="display:none"></div>
				<div class="clear"></div>
			</div>
			<div class="submit_icon">
					<input id="saveAvatarBtn" value="立即提交" type="button" class="submit_btn"/>
					<input value="取消" type="button" class="back_btn"/>
				</div>
		</div>
	</div>
</body>


</html>