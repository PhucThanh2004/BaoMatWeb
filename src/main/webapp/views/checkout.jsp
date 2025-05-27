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
						<li><a href="#">Thịt tươi</a></li>
						<li><a href="#">Rau củ</a></li>
						<li><a href="#">Quà tặng trái cây & hạt</a></li>
						<li><a href="#">Quả tươi</a></li>
						<li><a href="#">Thủy sản</a></li>
						<li><a href="#">Bơ & Trứng</a></li>
						<li><a href="#">Fastfood</a></li>
						<li><a href="#">Hành tươi</a></li>
						<li><a href="#">Khoai tây & Bánh ngọt</a></li>
						<li><a href="#">Bột yến mạch</a></li>
						<li><a href="#">Chuối tươi</a></li>
					</ul>
				</div>
			</div>
			<div class="col-lg-9">
				<div class="hero__search">
					<div class="hero__search__form">
						<form action="#">
							<div class="hero__search__categories">
								Tất cả các danh mục <span class="arrow_carrot-down"></span>
							</div>
							<input type="text" placeholder="Bạn cần gì?">
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

<!-- Breadcrumb Section Begin -->
<section class="breadcrumb-section set-bg"
	data-setbg="${URL}/img/breadcrumb.jpg">
	<div class="container">
		<div class="row">
			<div class="col-lg-12 text-center">
				<div class="breadcrumb__text">
					<h2>Thanh toán</h2>
					<div class="breadcrumb__option">
						<a href="./index.html">Trang chủ</a> <span>Thanh toán</span>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<!-- Breadcrumb Section End -->

<!-- Checkout Section Begin -->
<section class="checkout spad">
	<div class="container">
		<div class="checkout__form">
			<h4>Thông tin thanh toán</h4>
			<form action="${pageContext.request.contextPath}/checkout"
				method="post">
				<div class="row">
					<div class="col-lg-8 col-md-6">
						<div class="row">
							<!-- Thông tin khách hàng -->
							<div class="checkout__input">
								<p>Chọn địa chỉ nhận hàng</p>
								<div class="address-list">
									<c:forEach items="${address}" var="addressItem">
										<div class="address-item">
											<div class="address-frame">
												<!-- Nút radio với kiểu đẹp hơn -->
												<input type="radio" name="selectedAddress"
													value="${addressItem.id}" id="address-${addressItem.id}"
													class="address-radio"> <label
													for="address-${addressItem.id}" class="address-label">
													<div class="address-content">
														<p>
															<strong>Địa chỉ:</strong> ${addressItem.province},
															${addressItem.district}, ${addressItem.wards},
															${addressItem.detail}
														</p>
														<p>
															<strong>Số điện thoại:</strong> ${addressItem.phone}
														</p>
													</div>
												</label>
											</div>
											<hr>
										</div>
									</c:forEach>
								</div>
							</div>

							<style>
.address-list {
	margin-top: 10px;
}

.address-item {
	padding: 10px;
	border-radius: 5px;
	background-color: #f9f9f9;
	margin-bottom: 15px;
	border: 1px solid #ddd;
	transition: background-color 0.3s ease;
}

.address-item:hover {
	background-color: #f1f1f1;
}

.address-frame {
	display: flex;
	align-items: center;
}

.address-radio {
	margin-right: 10px;
	width: 15px !important;
	height: 15px !important;
	border-radius: 50%;
	cursor: pointer;
	transition: all 0.2s ease;
}

.address-radio:checked {
	background-color: #007bff;
	border-color: #007bff;
}

.address-label {
	cursor: pointer;
	display: flex;
	flex-direction: column;
	font-size: 14px;
	color: #333;
}

.address-content p {
	margin: 5px 0;
}

.address-label:hover {
	color: #007bff;
}

.address-item hr {
	border-top: 1px solid #eee;
	margin: 10px 0;
}
</style>

						</div>
						<div class="checkout__input">
							<p>Ghi chú</p>
							<input type="text" placeholder="Lưu ý cho đơn hàng của bạn">
						</div>
						<div class="checkout__input">
							<p>Phương thức thanh toán: Thanh toán khi nhận hàng (COD)</p>
						</div>
					</div>
					<div class="col-lg-4 col-md-6">
						<div class="checkout__order">
							<h4>Đơn hàng</h4>
							<div class="checkout__order__products">
								Sản phẩm <span>Tổng</span>
							</div>
							<ul>
								<c:forEach var="cart" items="${carts}">
									<li>${cart.productName}<span>${cart.productPrice * cart.quantity}</span></li>
								</c:forEach>

							</ul>
							<div class="checkout__order__total">
								Tổng cộng <span>${totalAmount}</span>
							</div>

							<button type="submit" class="site-btn">HOÀN THÀNH ĐƠN
								HÀNG</button>
						</div>
					</div>
				</div>
			</form>
		</div>
		<div id="selectedAddressId"></div>

		<!-- JavaScript để hiển thị id của địa chỉ đã chọn -->
		<script>
			document.querySelectorAll('.address-radio').forEach(radio => {
				radio.addEventListener('change', function() {
					const selectedAddressId = this.value;
					document.getElementById('selectedAddressId').textContent = 'Địa chỉ được chọn: ' + selectedAddressId;
				});
			});
		</script>
	</div>
</section>
<!-- Checkout Section End -->
