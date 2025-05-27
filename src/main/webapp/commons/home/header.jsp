<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:url value="/assets/user" var="URL"></c:url>
<c:url value="/" var="URL_PRIMARY"></c:url>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="description" content="Ogani Template">
<meta name="keywords" content="Ogani, unica, creative, html">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>UTE Shop</title>

<!-- Google Font -->
<link
	href="https://fonts.googleapis.com/css2?family=Cairo:wght@200;300;400;600;900&display=swap"
	rel="stylesheet">

<!-- Css Styles -->
<link rel="stylesheet" href="${URL}/css/bootstrap.min.css"
	type="text/css">
<link rel="stylesheet" href="${URL}/css/font-awesome.min.css"
	type="text/css">
<link rel="stylesheet" href="${URL}/css/elegant-icons.css"
	type="text/css">
<link rel="stylesheet" href="${URL}/css/nice-select.css" type="text/css">
<link rel="stylesheet" href="${URL}/css/jquery-ui.min.css"
	type="text/css">
<link rel="stylesheet" href="${URL}/css/owl.carousel.min.css"
	type="text/css">
<link rel="stylesheet" href="${URL}/css/slicknav.min.css"
	type="text/css">
<link rel="stylesheet" href="${URL}/css/style.css" type="text/css">
</head>

<body>
	<!-- Page Preloder -->
	<div id="preloder">
		<div class="loader"></div>
	</div>

	<!-- Humberger Begin -->
	<div class="humberger__menu__overlay"></div>
	<div class="humberger__menu__wrapper">
		<div class="humberger__menu__logo">
			<a href="#"><img src="${URL}/img/logo.png" alt=""></a>
		</div>
		<div class="humberger__menu__cart">
			<ul>
				<li><a href="#"><i class="fa fa-heart"></i> <span>1</span></a></li>
				<div class="col-lg-3">
					<c:choose>
						<c:when test="${not empty sessionScope.account}">
							<div class="header__cart">
								<ul>
									<li><a href="${URL_PRIMARY}cart/view"><i
											class="fa fa-shopping-bag"></i></a></li>
								</ul>
							</div>
						</c:when>
					</c:choose>
				</div>
			</ul>
			<div class="header__cart__price">
				item: <span>$150.00</span>
			</div>
		</div>
		<div class="humberger__menu__widget">

			<!-- Thay đổi phần hiển thị Login/Logout và giữ lại Đăng ký shop -->
			<div class="header__top__right__auth">

				<c:choose>
					<c:when test="${empty sessionScope.account}">
						<!-- Hiển thị nút Đăng ký nếu người dùng chưa đăng nhập -->
						<a href="${pageContext.request.contextPath}/register"
							class="btn-register">Đăng ký</a>
					</c:when>
					<c:otherwise>
						<!-- Không hiển thị gì nếu đã đăng nhập -->
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${not empty sessionScope.account}">
						<a href="${pageContext.request.contextPath}/logout"><i
							class="fa fa-user"></i> Đăng xuất</a>
					</c:when>
					<c:otherwise>
						<a href="${pageContext.request.contextPath}/login"><i
							class="fa fa-user"></i> Đăng nhập</a>
					</c:otherwise>
				</c:choose>
				<a href="${URL_PRIMARY}register-shop" class="ml-2"><i
					class="fa fa-shopping-bag"></i> Đăng ký shop</a>
			</div>

		</div>
		<nav class="humberger__menu__nav mobile-menu">
			<ul>
				<li class="active"><a
					href="${pageContext.request.contextPath}/home">Home</a></li>
				<li><a href="${pageContext.request.contextPath}/home">Shop</a></li>
				
				</li>
				<c:choose>
					<c:when test="${not empty sessionScope.account}">
						<li><a href="${pageContext.request.contextPath}/orders">Đơn
								hàng của tôi</a></li>
					</c:when>
				</c:choose>
				<li>
			</ul>
		</nav>
		<div id="mobile-menu-wrap"></div>
		<div class="header__top__right__social">
			<a href="#"><i class="fa fa-facebook"></i></a> <a href="#"><i
				class="fa fa-twitter"></i></a> <a href="#"><i class="fa fa-linkedin"></i></a>
			<a href="#"><i class="fa fa-pinterest-p"></i></a>
		</div>
		<div class="humberger__menu__contact">
			<ul>
				<c:choose>
					<c:when test="${not empty sessionScope.account}">
						<li><i class="fa fa-envelope"></i>
							${sessionScope.account.email}</li>
					</c:when>
					<c:otherwise>
						<li><i class="fa fa-envelope"></i> Xin chào!</li>
					</c:otherwise>
				</c:choose>


				<li>Miễn phí ship cho đơn hàng từ 150k</li>
			</ul>
		</div>
	</div>

	<!-- Humberger End -->

	<!-- Header Section Begin -->
	<header class="header">
		<div class="header__top">
			<div class="container">
				<div class="row">
					<div class="col-lg-6 col-md-6">
						<div class="header__top__left">
							<ul>
								<c:choose>
									<c:when test="${not empty sessionScope.account}">
										<li><i class="fa fa-envelope"></i> <a
											href="${pageContext.request.contextPath}/account?email=${account.email}">${sessionScope.account.email}</a></li>
									</c:when>
									<c:otherwise>
										<li><i class="fa fa-envelope"></i> Xin chào!</li>
									</c:otherwise>
								</c:choose>


								<li>Miễn phí ship cho đơn hàng từ 150k</li>
							</ul>
						</div>
					</div>
					<div class="col-lg-6 col-md-6">
						<div class="header__top__right d-flex justify-content-end">
							<div class="header__top__right__social">
								<a href="#"><i class="fa fa-facebook"></i></a> <a href="#"><i
									class="fa fa-twitter"></i></a> <a href="#"><i
									class="fa fa-linkedin"></i></a> <a href="#"><i
									class="fa fa-pinterest-p"></i></a>
							</div>
							<div class="header__top__right__auth">
								<c:choose>
									<c:when test="${empty sessionScope.account}">
										<!-- Hiển thị nút Đăng ký nếu người dùng chưa đăng nhập -->
										<a href="${pageContext.request.contextPath}/register"
											class="btn-register">Đăng ký</a>
									</c:when>
									<c:otherwise>
										<!-- Không hiển thị gì nếu đã đăng nhập -->
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${not empty sessionScope.account}">
										<!-- Hiển thị nút Logout nếu đã đăng nhập -->
										<a href="${pageContext.request.contextPath}/logout"><i
											class="fa fa-user"></i> Đăng xuất</a>
									</c:when>
									<c:otherwise>
										<!-- Hiển thị nút Login nếu chưa đăng nhập -->
										<a href="${pageContext.request.contextPath}/login"><i
											class="fa fa-user"></i> Đăng nhập</a>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when
										test="${not empty sessionScope.account and sessionScope.account.isSeller == false}">
										<a href="${URL_PRIMARY}register-shop" class="ml-2"><i
											class="fa fa-shopping-bag"></i> Đăng ký shop</a>
									</c:when>
								</c:choose>

							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="container">
			<div class="row">
				<div class="col-lg-3">
					<div class="header__logo">
						<a href="${URL_PRIMARY}/home"><img src="${URL}/img/logo.png"
							alt=""></a>
					</div>
				</div>
				<div class="col-lg-6">
					<nav class="header__menu">
						<ul>
							<li class="active"><a
								href="${pageContext.request.contextPath}/home">Home</a></li>


							
							</li>
							<c:choose>
								<c:when test="${not empty sessionScope.account}">
									<li><a href="${pageContext.request.contextPath}/orders">Đơn
											hàng của tôi</a></li>
								</c:when>
							</c:choose>
							<li>
							<li><c:choose>
									<c:when
										test="${not empty sessionScope.account and sessionScope.account.isSeller == true}">
										<a
											href="${pageContext.request.contextPath}/shop/product/list-product?id=${sessionScope.account.id}">Trang Shop
									</a>
									</c:when>
								</c:choose></li>
						</ul>
					</nav>
				</div>
				<div class="col-lg-3">
					<c:choose>
						<c:when test="${not empty sessionScope.account}">
							<div class="header__cart">
								<ul>
									<li><a href="${URL_PRIMARY}cart/view"><i
											class="fa fa-shopping-bag"></i></a></li>
								</ul>
							</div>
						</c:when>
					</c:choose>
				</div>

			</div>
			<div class="humberger__open">
				<i class="fa fa-bars"></i>
			</div>
		</div>
	</header>
	<!-- Header Section End -->