<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<div class="card shadow mb-4">
	<div class="card-header py-3 d-flex justify-content-between">
		<h4 class="m-0 font-weight-bold text-primary">Danh sách đơn hàng
			của shop</h4>
	</div>
	<div class="card-body">
		<div class="table-responsive">
			<!-- Thông báo thành công -->
			<c:if test="${not empty sessionScope.message}">
				<div class="alert alert-success" role="alert">
					${sessionScope.message}</div>
				<c:remove var="message" scope="session" />
			</c:if>

			<!-- Thông báo lỗi -->
			<c:if test="${not empty sessionScope.error}">
				<div class="alert alert-danger" role="alert">
					${sessionScope.error}</div>
				<c:remove var="error" scope="session" />
			</c:if>

			<table class="table table-bordered" id="dataTable" width="100%"
				cellspacing="0">
				<thead>
					<tr>
						<th>Mã đơn hàng</th>
						<th>Người mua</th>
						<th>Email</th>
						<th>Tổng tiền</th>
						<th>Địa chỉ</th>
						<th>Trạng thái</th>
						<th>Phương thức thanh toán</th>
						<th>Ngày đặt hàng</th>
						<th>Hành động</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="order" items="${orders}">
						<!-- Hàng chính của đơn hàng -->
						<tr>
							<td>${order.orderId}</td>
							<td>${order.accountName}</td>
							<td>${order.accountEmail}</td>
							<td>${order.totalAmount}</td>
							<td>${order.address.province} - ${order.address.district} -
								${order.address.wards} <br /> ${order.address.detail} <br />
								SDT: ${order.address.phone}
							</td>
							<td><c:set var="shopId" value="${param.shopId}" />

								<form
									action="${pageContext.request.contextPath}/shop/orders/update-status"
									method="post">
									<input type="hidden" name="orderId" value="${order.orderId}">
									<input type="hidden" name="shopId" value="${shopId}"> <select
										name="status" class="form-control"
										onchange="this.form.submit()">
										<option value="PENDING"
											${order.status == 'PENDING' ? 'selected' : ''}>Chờ
											xác nhận</option>
										<option value="ACCEPT"
											${order.status == 'ACCEPT' ? 'selected' : ''}>Đã xác
											nhận</option>
										<option value="DELIVERING"
											${order.status == 'DELIVERING' ? 'selected' : ''}>Đang
											vận chuyển</option>
										<option value="DONE"
											${order.status == 'DONE' ? 'selected' : ''}>Đã giao
											hàng</option>
										<option value="CANCEL"
											${order.status == 'CANCEL' ? 'selected' : ''}>Huỷ
											đơn hàng</option>
									</select>
								</form></td>
							<td>${order.paymentMethod}</td>
							<td>${order.createdAt}</td>
							<td>
								<!-- Nút xem chi tiết -->
								<button class="btn btn-primary btn-sm" type="button"
									data-toggle="collapse"
									data-target="#orderDetails${order.orderId}"
									aria-expanded="false"
									aria-controls="orderDetails${order.orderId}">Xem chi
									tiết</button>
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
												</tr>
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
