<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<c:url value="/assets/user" var="URL"></c:url>
<!-- Hero Section Begin -->
<section class="hero hero-normal">
	<div class="container">
		<div class="row">
			<div class="col-lg-3">
				<div class="hero__categories">
					<div class="hero__categories__all">
						<i class="fa fa-bars"></i> <span>Danh mục sản phẩm</span>
					</div>
					<ul>
						<c:forEach var="category" items="${categories}">
							<li><a
								href="${pageContext.request.contextPath}/category?id=${category.id}">${category.name}</a></li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div class="col-lg-9">
				<div class="hero__search">
					<div class="hero__search__form">
						<form action="#">
							<div class="hero__search__categories">
								Tất cả danh mục <span class="arrow_carrot-down"></span>
							</div>
							<input type="text" placeholder="Bạn cần tìm gì?">
							<button type="submit" class="site-btn">TÌM KIẾM</button>
						</form>
					</div>
					<div class="hero__search__phone">
						<div class="hero__search__phone__icon">
							<i class="fa fa-phone"></i>
						</div>
						<div class="hero__search__phone__text">
							<h5>+65 11.188.888</h5>
							<span>Hỗ trợ 24/7</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<!-- Hero Section End -->

<!-- Categories Section Begin -->
<section class="categories">
	<div class="container">
		<div class="row">
			<div class="categories__slider owl-carousel">
				<c:forEach var="category" items="${categories}">
					<div class="col-lg-3">
						<div class="categories__item set-bg"
							data-setbg="${category.thumbnail}">
							<h5>
								<a
									href="${pageContext.request.contextPath}/category?id=${category.id}">${category.name}</a>
							</h5>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</section>
<!-- Categories Section End -->

<!-- Featured Section Begin -->
<section class="featured spad">
	<div class="container">
		<div class="row">
			<div class="col-lg-12">
				<div class="section-title">
					<h2>${category.name}</h2>
				</div>
			</div>
		</div>
		<div class="row featured__filter">
			<c:forEach var="product" items="${products}">
				<div class="col-lg-3 col-md-4 col-sm-6 mix">
					<div class="featured__item">
						<div class="featured__item__pic set-bg"
							data-setbg="${pageContext.request.contextPath}/image?fname=${product.images[0]}">

						</div>
						<div class="featured__item__text">
							<h6>
								<a
									href="${pageContext.request.contextPath}/product/detail?id=${product.id}">${product.name}</a>
							</h6>
							<h5>${product.price}VNĐ</h5>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</section>
<!-- Featured Section End -->
