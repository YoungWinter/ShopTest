<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<script type="text/javascript">

	var searchProducts = "";

	function searchProduct(){
		var name = $("#searchInput").val();
		var id = "";
		for(var i = 0; i < searchProducts.length; i++){
			if(name == searchProducts[i].pname){
				id = searchProducts[i].pid;
			}
		}
		if(id != ""){
			$("#hiddenMethod").val("productInfo");
			$("#hiddenPid").val(id);
			$("#searchForm").submit();
		}
	}

	function searchByWord(obj){
		var word = $(obj).val();
		var content = "";
		$.post(
			"${pageContext.request.contextPath}/product?method=searchProductListByWord",
			{"word":word},
			function(data){
				if(data.length>0){
					searchProducts = data;
					for(var i=0;i<data.length;i++){
						content += "<option>"+data[i].pname+"</option>";
					}
					$("#searchList").html(content);
				}
				
			},
			"json"
		);
	}

	$(function(){
		var content = "";
		$.post(
			"${pageContext.request.contextPath }/product?method=categoryList",
			function(data){
				for(var i=0;i<data.length;i++){
					content += "<li><a href='${pageContext.request.contextPath }/product?method=productListByCategory&cid=" + data[i].cid + "'>" + data[i].cname + "</a></li>";
				}
				//将数据放置到页面中
				$("#categoryUl").html(content);
			},
			"json"
		);
	});
</script>
<!-- 登录 注册 购物车... -->
<div class="container-fluid">
	<div class="col-md-4">
		<img src="img/logo2.png" />
	</div>
	<div class="col-md-5">
		<img src="img/header.png" />
	</div>
	<div class="col-md-3" style="padding-top:20px">
		<ol class="list-inline">
			<c:if test="${empty user }">
				<li><a href="login.jsp">登录</a></li>
				<li><a href="register.jsp">注册</a></li>
			</c:if>
			<c:if test="${!empty user }">
				<li style="font-size: 15px;color: orange;">亲爱的<strong>${user.username }</strong>,您好</li>
				<li><a href="register.jsp">退出</a></li>
			</c:if>
			<li><a href="${pageContext.request.contextPath }/cart.jsp">购物车</a></li>
			<li><a href="order_list.jsp">我的订单</a></li>
		</ol>
	</div>
</div>

<!-- 导航条 -->
<div class="container-fluid">
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="${pageContext.request.contextPath }/product?method=index">首页</a>
			</div>

			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav" id="categoryUl">
				<%-- class="active" --%>
					<!--  <li><a href="#">手机数码<span class="sr-only">(current)</span></a></li> -->
				</ul>
				<form id="searchForm" action="${pageContext.request.contextPath }/product" method="post" class="navbar-form navbar-right" role="search">
					<div class="form-group">
						<input id="searchInput" type="search" class="form-control" list="searchList" placeholder="找到真爱" onkeyup="searchByWord(this)">
						<datalist id="searchList">
							
						</datalist>
					</div>
					<input id="hiddenMethod" name="method" type="hidden"/>
					<input id="hiddenPid" name="pid" type="hidden"/>
					<button type="button" onclick="searchProduct()" class="btn btn-default">搜索</button>
				</form>
			</div>
		</div>
	</nav>
</div>