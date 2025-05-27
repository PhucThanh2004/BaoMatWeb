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
						<i class="fa fa-bars"></i> <span>Danh mục</span>
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

<!-- Product Details Section Begin -->
<section class="product-details spad">
	<div class="container">
		<div class="row">
			<div class="col-lg-6 col-md-6">
				<div class="product__details__pic">
					<div class="product__details__pic__item">

						<img class="product__details__pic__item--large"
							src="${pageContext.request.contextPath}/image?fname=${product.images[0]}"
							alt="">
					</div>
					<div class="product__details__pic__slider owl-carousel">
						<c:forEach var="image" items="${product.images}">
							<img
								data-imgbigurl="${pageContext.request.contextPath}/image?fname=${image}"
								src="${pageContext.request.contextPath}/image?fname=${image}"
								alt="">
						</c:forEach>
					</div>
				</div>
			</div>
			<div class="col-lg-6 col-md-6">
				<div class="product__details__text">
					<h3>${product.name}</h3>
					<div class="product__details__rating">
						<i class="fa fa-star"></i> <i class="fa fa-star"></i> <i
							class="fa fa-star"></i> <i class="fa fa-star"></i> <i
							class="fa fa-star-half-o"></i> <span>(${countReview} đánh
							giá)</span>
					</div>

					<div class="product__details__price">${product.price}VNĐ</div>
					<form action="${pageContext.request.contextPath}/cart/add"
						method="post">
						<input type="hidden" name="productId" value="${product.id}">
						<div class="product__details__quantity">
							<div class="quantity">
								<div class="pro-qty">
									<input name="quantity" type="text" value="1">
								</div>
							</div>
						</div>
						<button type="submit" class="primary-btn">THÊM VÀO GIỎ
							HÀNG</button>
					</form>
					<a href="#" class="heart-icon"><span class="icon_heart_alt"></span></a>
					<ul>
						<li><b>Sẵn có</b> <span>${product.stockQuantity}</span></li>
						<li><b>Thời gian vận chuyển</b> <span>01 ngày kể từ
								lúc xác nhận. <br> <samp>Nhận hàng miễn phí hôm nay</samp>
						</span></li>
						<li><b>Cân nặng</b> <span>0.5 kg</span></li>
						<li><b>Chia sẻ</b>
							<div class="share">
								<a href="#"><i class="fa fa-facebook"></i></a> <a href="#"><i
									class="fa fa-twitter"></i></a> <a href="#"><i
									class="fa fa-instagram"></i></a> <a href="#"><i
									class="fa fa-pinterest"></i></a>
							</div></li>
					</ul>
				</div>
			</div>
			<div class="col-lg-12">
				<div class="product__details__tab">
					<ul class="nav nav-tabs" role="tablist">
						<li class="nav-item"><a class="nav-link active"
							data-toggle="tab" href="#tabs-1" role="tab" aria-selected="true">Mô
								tả</a></li>
						<li class="nav-item"><a class="nav-link" data-toggle="tab"
							href="#tabs-2" role="tab" aria-selected="false">Thông tin
								shop</a></li>
						<li class="nav-item"><a class="nav-link" data-toggle="tab"
							href="#tabs-3" role="tab" aria-selected="false">Đánh giá</a></li>
					</ul>
					<div class="tab-content">
						<!-- Tab 1: Thông tin sản phẩm -->
						<div class="tab-pane fade show active" id="tabs-1" role="tabpanel">
							<div class="product__details__tab__desc">
								<h6>Thông tin sản phẩm</h6>
								<p>${product.description}</p>
							</div>
						</div>

						<!-- Tab 2: Products Information -->
						<div class="tab-pane fade" id="tabs-2" role="tabpanel">
							<div class="product__details__tab__desc">
								
								<p>
									<strong>Tên Shop:</strong> ${shop.name}
								</p>
								<p>
									<strong>Địa Chỉ:</strong> ${shop.address}
								</p>
								<p>
									<strong>Mô Tả:</strong> ${shop.description}
								</p>
							</div>
						</div>

						<!-- Tab 3: Đánh giá sản phẩm -->
						<div class="tab-pane fade" id="tabs-3" role="tabpanel">
							<div class="product__details__tab__desc">
								<c:forEach var="review" items="${reviews}">
									<div class="review-box"
										style="border: 1px solid #ddd; padding: 15px; margin-bottom: 15px; border-radius: 5px;">
										<div class="review-header" style="margin-bottom: 10px;">
											<h5 class="author-name"
												style="margin: 0; font-size: 16px; font-weight: bold;">
												${review.authorName} <span class="review-date"
													style="font-size: 12px; color: #888;">
													(${review.createdAt}) </span>
											</h5>
										</div>
										<div class="review-content"
											style="font-size: 14px; color: #333;">
											<p>${review.content}</p>
										</div>
									</div>
								</c:forEach>

								<!-- Hiển thị thông báo nếu chưa có đánh giá -->
								<c:if test="${empty reviews}">
									<p style="text-align: center; color: #888; font-size: 14px;">Chưa
										có đánh giá nào cho sản phẩm này.</p>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
</section>
<!-- Product Details Section End -->
