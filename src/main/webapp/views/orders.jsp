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
						<li><a href="#">Fresh Meat</a></li>
						<li><a href="#">Vegetables</a></li>
						<li><a href="#">Fruit & Nut Gifts</a></li>
						<li><a href="#">Fresh Berries</a></li>
						<li><a href="#">Ocean Foods</a></li>
						<li><a href="#">Butter & Eggs</a></li>
						<li><a href="#">Fastfood</a></li>
						<li><a href="#">Fresh Onion</a></li>
						<li><a href="#">Papayaya & Crisps</a></li>
						<li><a href="#">Oatmeal</a></li>
						<li><a href="#">Fresh Bananas</a></li>
					</ul>
				</div>
			</div>
			<div class="col-lg-9">
				<div class="hero__search">
					<div class="hero__search__form">
						<form action="#">
							<div class="hero__search__categories">
								All Categories <span class="arrow_carrot-down"></span>
							</div>
							<input type="text" placeholder="What do yo u need?">
							<button type="submit" class="site-btn">SEARCH</button>
						</form>
					</div>
					<div class="hero__search__phone">
						<div class="hero__search__phone__icon">
							<i class="fa fa-phone"></i>
						</div>
						<div class="hero__search__phone__text">
							<h5>+65 11.188.888</h5>
							<span>support 24/7 time</span>
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
					<h2>Đơn hàng</h2>
					<div class="breadcrumb__option">
						<a href="/home">Trang chủ</a> <span>Đơn hàng của bạn</span>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<!-- Breadcrumb Section End -->

<!-- Shoping Cart Section Begin -->
<section class="shoping-cart spad">
	<div class="container">
		<div class="row">
			<div class="col-lg-12">
				<div class="shoping__cart__table">
					<!-- Thông báo thành công -->
					<c:if test="${not empty sessionScope.message}">
						<div class="alert alert-success" role="alert">
							${sessionScope.message}</div>
						<c:remove var="message" scope="session" />
					</c:if>

					<table class="table table-bordered" id="dataTable" width="100%"
						cellspacing="0">
						<thead>
							<tr>
								<th>Mã đơn hàng</th>
								<th>Tổng tiền</th>
								<th>Trạng thái</th>
								<th>Phương thức thanh toán</th>
								<th>Ngày đặt hàng</th>
								<th>Hành động</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="order" items="${orders}">
								<tr>
									<td>${order.orderId}</td>
									<td>${order.totalAmount}</td>
									<td><c:choose>
											<c:when test="${order.status == 'PENDING'}">
												<span class="badge badge-warning">Chờ xác nhận</span>
											</c:when>
											<c:when test="${order.status == 'ACCEPT'}">
												<span class="badge badge-primary">Đã xác nhận</span>
											</c:when>
											<c:when test="${order.status == 'DELIVERING'}">
												<span class="badge badge-info">Đang vận chuyển</span>
											</c:when>
											<c:when test="${order.status == 'DONE'}">
												<span class="badge badge-success">Đã giao hàng</span>
											</c:when>
											<c:when test="${order.status == 'CANCLE'}">
												<span class="badge badge-danger">Huỷ đơn hàng</span>
											</c:when>
											<c:otherwise>
												<span class="badge badge-secondary">Không xác định</span>
											</c:otherwise>
										</c:choose></td>
									<td>${order.paymentMethod}</td>
									<td>${order.createdAt}</td>
									<td>
										<button class="btn btn-primary btn-sm" type="button"
											data-toggle="collapse"
											data-target="#orderDetails${order.orderId}"
											aria-expanded="false"
											aria-controls="orderDetails${order.orderId}">Xem chi
											tiết</button> <!-- Nút huỷ đơn hàng nếu trạng thái là PENDING --> <c:if
											test="${order.status == 'PENDING'}">
											<form
												action="${pageContext.request.contextPath}/orders/cancel"
												method="post" style="display: inline;">
												<input type="hidden" name="orderId" value="${order.orderId}">
												<button type="submit" class="btn btn-danger btn-sm">Huỷ
													đơn hàng</button>
											</form>
										</c:if>
									</td>
								</tr>

								<!-- Phần mở rộng để hiển thị chi tiết đơn hàng -->
								<tr class="collapse-row">
									<td colspan="8" class="p-0">
										<div id="orderDetails${order.orderId}" class="collapse">
											<table class="table table-sm mb-0">
												<thead>
													<tr class="bg-gray-100">
														<th>Tên sản phẩm</th>
														<th>Số lượng</th>
														<th>Giá</th>
														<th>Thành tiền</th>
														<th>Hình ảnh</th>
														<th>Đánh giá</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach var="detail" items="${order.orderDetails}">
														<tr>
															<td>${detail.product.name}</td>
															<td>${detail.quantity}</td>
															<td>${detail.price}</td>
															<td>${detail.price * detail.quantity}</td>
															<td><c:if test="${not empty detail.product.images}">
																	<img
																		src="${pageContext.request.contextPath}/image?fname=${detail.product.images[0]}"
																		alt="${detail.product.name}" width="50">
																</c:if></td>
															<td>
																<!-- Hiển thị nút đánh giá nếu trạng thái đơn hàng là "Đã giao hàng" -->
																<c:if test="${order.status == 'DONE'}">
																	<!-- Nút mở Modal -->
																	<button type="button" class="btn btn-sm btn-success"
																		data-toggle="modal"
																		data-target="#reviewModal${detail.product.id}">
																		Đánh giá</button>
																</c:if>
																
															</td>
														</tr>
														<div class="modal fade"
															id="reviewModal${detail.product.id}" tabindex="-1"
															role="dialog"
															aria-labelledby="reviewModalLabel${detail.product.id}"
															aria-hidden="true">
															<div class="modal-dialog" role="document">
																<div class="modal-content">
																	<div class="modal-header">
																		<h5 class="modal-title"
																			id="reviewModalLabel${detail.product.id}">Đánh
																			giá sản phẩm: ${detail.product.name}</h5>
																		<button type="button" class="close"
																			data-dismiss="modal" aria-label="Close">
																			<span aria-hidden="true">&times;</span>
																		</button>
																	</div>
																	<div class="modal-body">
																		<form
																			action="${pageContext.request.contextPath}/review"
																			method="post">
																			<input type="hidden" name="productId"
																				value="${detail.product.id}"> <input
																				type="hidden" name="accountId" value="${account.id}">
																			<div class="form-group">
																				<label for="reviewContent${detail.product.id}">Nội
																					dung đánh giá</label>
																				<textarea class="form-control"
																					id="reviewContent${detail.product.id}"
																					name="content" rows="3" required></textarea>
																			</div>
																			<button type="submit" class="btn btn-primary">Gửi
																				đánh giá</button>
																		</form>
																	</div>
																</div>
															</div>
														</div>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</section>
