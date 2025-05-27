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
					<h2>Xác nhận mã</h2>
					<div class="breadcrumb__option">
						<a href="/home">Trang chủ</a> <span>Xác nhận mã</span>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<!-- Breadcrumb Section End -->

<!-- Verify Reset Code Section Begin -->
<section class="product-details spad">
	<div class="container">
		<div class="row justify-content-center">
			<div class="col-lg-6">
				<c:if test="${not empty errorMessage}">
					<div class="alert alert-danger">${errorMessage}</div>
				</c:if>

				<div class="card shadow-sm">
					<div class="card-body">
						<h4 class="card-title text-center mb-4">Nhập mã xác nhận</h4>
						<form action="${pageContext.request.contextPath}/verifyResetCode"
							method="POST">
							<div class="mb-3">
								<label for="authcode" class="form-label">Mã xác nhận</label> <input
									type="text" class="form-control form-control-sm" id="authcode"
									name="authcode" placeholder="Nhập mã xác nhận" required>
							</div>
							<button type="submit" class="site-btn w-100">Xác nhận</button>
						</form>
						<div class="mt-3 text-center">
							<a href="${pageContext.request.contextPath}/forgotPassword"
								class="btn btn-link">Quay lại quên mật khẩu</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<!-- Verify Reset Code Section End -->