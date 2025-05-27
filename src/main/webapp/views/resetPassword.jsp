<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<c:url value="/assets/user" var="URL"></c:url>

<!-- Breadcrumb Section Begin -->
<section class="breadcrumb-section set-bg"
	data-setbg="${URL}/img/breadcrumb.jpg">
	<div class="container">
		<div class="row">
			<div class="col-lg-12 text-center">
				<div class="breadcrumb__text">
					<h2>Đặt lại mật khẩu</h2>
					<div class="breadcrumb__option">
						<a href="/home">Trang chủ</a> <span>Đặt lại mật khẩu</span>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<!-- Breadcrumb Section End -->

<!-- Reset Password Section Begin -->
<section class="product-details spad">
	<div class="container">
		<div class="row justify-content-center">
			<div class="col-lg-6">
				<c:if test="${not empty errorMessage}">
					<div class="alert alert-danger">${errorMessage}</div>
				</c:if>

				<div class="card shadow-sm">
					<div class="card-body">
						<h4 class="card-title text-center mb-4">Đặt lại mật khẩu</h4>
						<form action="${pageContext.request.contextPath}/resetPassword"
							method="POST" id="resetPasswordForm">
							<div class="mb-3">
								<label for="newPassword" class="form-label">Mật khẩu mới</label>
								<input type="password" class="form-control" id="newPassword"
									name="newPassword" placeholder="Nhập mật khẩu mới" required>
							</div>

							<div class="mb-3">
								<label for="confirmPassword" class="form-label">Xác nhận
									mật khẩu</label> <input type="password" class="form-control"
									id="confirmPassword" name="confirmPassword"
									placeholder="Nhập lại mật khẩu" required>
							</div>

							<button type="submit" class="site-btn w-100">Đặt lại mật
								khẩu</button>
						</form>
						<div class="mt-3 text-center">
							<a href="${pageContext.request.contextPath}/login"
								class="btn btn-link">Quay lại đăng nhập</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<!-- Reset Password Section End -->

<!-- Optional JavaScript for password match -->
<script>
	document.getElementById('resetPasswordForm').addEventListener(
			'submit',
			function(event) {
				var password = document.getElementById('newPassword').value;
				var confirmPassword = document
						.getElementById('confirmPassword').value;

				if (password !== confirmPassword) {
					alert('Mật khẩu không khớp. Vui lòng kiểm tra lại.');
					event.preventDefault(); // Ngừng gửi biểu mẫu nếu mật khẩu không khớp
				}
			});
</script>