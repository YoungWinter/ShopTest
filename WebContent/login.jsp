<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>会员登录</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="css/style.css" type="text/css" />

<style>
body {
	margin-top: 20px;
	margin: 0 auto;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}

.container .row div {
	/* position:relative;
				 float:left; */
	
}

font {
	color: #666;
	font-size: 22px;
	font-weight: normal;
	padding-right: 17px;
}
.valid {
		background: url('image/valid.png') no-repeat left center;
	}
</style>
<script type="text/javascript">

	//改变验证码图片
	function changeImg(obj) {
		obj.src = "${pageContext.request.contextPath }/checkImg?time="
				+ new Date().getTime();
	}
	
	//校验验证码
	function checkImage(){
		var value = $("#checkedImage").val();
		$.ajax({
			"async" : false,
			"url" : "${pageContext.request.contextPath}/user",
			"data" : {
				"checkCode" : value,
				"method" : "checkImgValidate"
			},
			"type" : "POST",
			"dataType" : "json",
			"success" : function(data) {
				if(data.isExist){
					$("#validLbel").html("");
					$("#validLbel").html("<img src='${pageContext.request.contextPath }/image/valid.png'>");
					$("#loginForm").removeAttr("disabled");
				}else{
					$("#validLbel").html("");
					$("#validLbel").html("验证码错误");
					$("#loginForm").attr("disabled","true");
				}
			}
		});		
	}
</script>
</head>
<body>

	<!-- 引入header.jsp -->
	<jsp:include page="/header.jsp"></jsp:include>


	<div class="container"
		style="width: 100%; height: 460px; background: #FF2C4C url('images/loginbg.jpg') no-repeat;">
		<div class="row">
			<div class="col-md-7">
				<!--<img src="./image/login.jpg" width="500" height="330" alt="会员登录" title="会员登录">-->
			</div>

			<div class="col-md-5">
				<div
					style="width: 440px; border: 1px solid #E7E7E7; padding: 20px 0 20px 30px; border-radius: 5px; margin-top: 60px; background: #fff;">
					<font>会员登录</font>USER LOGIN
					<div><span style="color: red">${loginInfo }</span></div>
					<form  class="form-horizontal" action="${pageContext.request.contextPath }/user" method="post">
						<input type="hidden" name="method" value="login">
						<div class="form-group">
							<label for="username" class="col-sm-2 control-label">用户名</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="username" name="username"
									placeholder="请输入用户名">
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">密码</label>
							<div class="col-sm-6">
								<input type="password" class="form-control" id="inputPassword3" name="password"
									placeholder="请输入密码">
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">验证码</label>
							<div class="col-sm-3">
								<input type="text" class="form-control" id="checkedImage" onblur="checkImage()"
									style="padding-left: 2px; padding-right: 2px;" placeholder="请输入验证码">
							</div>
							<div class="col-sm-4" style="padding-left: 0px; padding-right: 0px;">
								<img src="${pageContext.request.contextPath }/checkImg" onclick="changeImg(this)" />
							</div>
							<div class="col-sm-2" style="padding-left: 0px; padding-right: 0px;">
								<label id="validLbel"></label>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<div class="checkbox">
									<label> <input type="checkbox" name="autoLogin" value="autoLogin"> 自动登录
									</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <label> <input
										type="checkbox" name="isRember"> 记住用户名
									</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<input id="loginForm" type="submit" width="100" value="登录" name="submit"
									style="background: url('./images/login.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0); height: 35px; width: 100px; color: white;">
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- 引入footer.jsp -->
	<jsp:include page="/footer.jsp"></jsp:include>

</body>
</html>