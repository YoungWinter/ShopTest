<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head></head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>会员注册</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入表单校验jquery插件 -->
<script src="js/jquery.validate.min.js" type="text/javascript"></script>

<!-- 引入日期选择插件 -->
<script src="js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
<script src="js/bootstrap-datetimepicker.zh-CN.js"
	type="text/javascript"></script>
<!-- 引入日期选择插件css文件 datetimepicker.css -->
<link rel="stylesheet" href="css/bootstrap-datetimepicker.min.css"
	type="text/css" />

<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="css/style.css" type="text/css" />
<style type="text/css">
	label.valid {
		background: url('image/valid.png') no-repeat left center;
	}
</style>

<script type="text/javascript">
	//改变验证码图片
	function changeImg(obj) {
		obj.src = "${pageContext.request.contextPath }/checkImg?time="
				+ new Date().getTime();
	}

	$(function() {

		//日期控件
		$("#datetimepicker1").datetimepicker({
			language : 'zh-CN',
			format : "yyyy-mm-dd",
			minView : "month",
			autoclose : true,
			todayBtn : true,
			pickerPosition : "bottom-left"
		});

		//自定义校验规则(用户名是否为空)
		$.validator.addMethod(
						"checkUsername",
						function(value, element, params) {
							//value--输入的内容,element--被校验的元素对象,params--规则对应的参数
							var isExist = false;
							$.ajax({
										"async" : false,
										"url" : "${pageContext.request.contextPath}/user",
										"data" : {
											"username" : value,
											"method" : "checkUsername"
										},
										"type" : "POST",
										"dataType" : "json",
										"success" : function(data) {
											isExist = data.isExist;
										}
									});
							return !isExist;
						});
		
		//自定义校验规则(验证码是否正确)
		$.validator.addMethod(
			"checkCodeValidate",
			function(value, element, params){
				var isTrue = false;
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
						isTrue = data.isExist;
					}
				});
				return isTrue;
			}
		);

		//表单校验
		$("#formCheck").validate({
			//失去焦点时验证
			onfocusout : function(element) {$(element).valid();},
			//成功是显示的信息
			success : function(label) {
				/*label的默认正确样式为valid，需要通过validClass来重置，否则这里添加的其他样式不能被清除*/
				label.text('').addClass('valid');
			},
			//校验规则
			rules : {
				"username" : {
					"required" : true,
					"checkUsername" : true
				},
				"password" : {
					"required" : true,
					"rangelength" : [ 6, 12 ]
				},
				"repassword" : {
					"required" : true,
					"rangelength" : [ 6, 12 ],
					"equalTo" : "#confirmpwd"
				},
				"email" : {
					"required" : true,
					"email" : true
				},
				"sex" : {
					"required" : true
				},
				"checkCode":{
					"checkCodeValidate" : true
				}
			},
			messages : {
				"username" : {
					"required" : "用户名不能为空",
					"checkUsername" : "用户名已存在"
				},
				"password" : {
					"required" : "密码不能为空",
					"rangelength" : "密码长度6-12位"
				},
				"repassword" : {
					"required" : "密码不能为空",
					"rangelength" : "密码长度6-12位",
					"equalTo" : "两次密码不一致"
				},
				"email" : {
					"required" : "邮箱不能为空",
					"email" : "邮箱格式不正确"
				},
				"sex" : {
					"required" : "性别不能为空"
				},
				"checkCode":{
					"checkCodeValidate" : "验证码不正确"
				}
			}
		});
	});
</script>
<style>
body {
	margin-top: 20px;
	margin: 0 auto;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}

font {
	color: #3164af;
	font-size: 18px;
	font-weight: normal;
	padding: 0 10px;
}

.error {
	color: red;
}
</style>
</head>
<body>

	<!-- 引入header.jsp -->
	<jsp:include page="/header.jsp"></jsp:include>

	<div class="container"
		style="width: 100%; background: url('image/regist_bg.jpg');">
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8"
				style="background: #fff; padding: 40px 80px; margin: 30px; border: 7px solid #ccc;">
				<font>会员注册</font>USER REGISTER
				<form id="formCheck" class="form-horizontal"
					style="margin-top: 5px;"
					action="${pageContext.request.contextPath }/user" method="post">
					<input type="hidden" name="method" value="register"/>
					<div class="form-group">
						<label for="username" class="col-sm-2 control-label">用户名</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" id="username"
								name="username" placeholder="请输入用户名">
						</div>
						<label for="username" generated="true"
							class="col-sm-2 control-label error"
							style="text-align: left; height: 30px; padding-top: 0px; width: 120px;"></label>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-sm-2 control-label">密码</label>
						<div class="col-sm-4">
							<input type="password" class="form-control" id="inputPassword3"
								name="password" placeholder="请输入密码">
						</div>
						<label for="inputPassword3" generated="true"
							class="col-sm-2 control-label error"
							style="text-align: left; height: 30px; padding-top: 0px; width: 120px;"></label>
					</div>
					<div class="form-group">
						<label for="confirmpwd" class="col-sm-2 control-label">确认密码</label>
						<div class="col-sm-4">
							<input type="password" class="form-control" id="confirmpwd"
								name="repassword" placeholder="请输入确认密码">
						</div>
						<label for="confirmpwd" generated="true"
							class="col-sm-2 control-label error"
							style="text-align: left; height: 30px; padding-top: 0px; width: 120px;"></label>
					</div>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">Email</label>
						<div class="col-sm-4">
							<input type="email" class="form-control" id="inputEmail3"
								name="email" placeholder="Email">
						</div>
						<label for="inputEmail3" generated="true"
							class="col-sm-2 control-label error"
							style="text-align: left; height: 30px; padding-top: 0px; width: 120px;"></label>
					</div>
					<div class="form-group">
						<label for="usercaption" class="col-sm-2 control-label">姓名</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" id="usercaption"
								name="name" placeholder="请输入姓名">
						</div>
					</div>
					<div class="form-group opt">
						<label for="inlineRadio1" class="col-sm-2 control-label">性别</label>
						<div class="col-sm-4">
							<label class="radio-inline"> <input type="radio"
								name="sex" id="inlineRadio1" value="male"> 男
							</label> <label class="radio-inline"> <input type="radio"
								name="sex" id="inlineRadio2" value="female"> 女
							</label>
						</div>
						<label for="sex" class="col-sm-2 control-label error"
							style="text-align: left; display: none">性别不能为空</label>
					</div>
					<div class="form-group">
						<label for="date" class="col-sm-2 control-label">出生日期</label>
						<div class="col-sm-3 input-group date" id="datetimepicker1"
							style="padding-left: 15px; padding-right: 15px;">
							<input type="text" class="form-control" name="birthday">
							<span class="input-group-addon"> <span
								class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
					</div>

					<div class="form-group">
						<label for="date" class="col-sm-2 control-label">验证码</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" name="checkCode">
						</div>
						<div class="col-sm-2">
							<img src="${pageContext.request.contextPath }/checkImg"
								onclick="changeImg(this)" />
						</div>
						<label for="checkCode" generated="true"
							class="col-sm-2 control-label error"
							style="text-align: left; height: 30px; padding-top: 0px; width: 120px;"></label>

					</div>

					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<input type="submit" width="100" value="注册" name="submit"
								style="background: url('./images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0); height: 35px; width: 100px; color: white;">
						</div>
					</div>
				</form>
			</div>

			<div class="col-md-2"></div>

		</div>
	</div>

	<!-- 引入footer.jsp -->
	<jsp:include page="/footer.jsp"></jsp:include>

</body>
</html>




