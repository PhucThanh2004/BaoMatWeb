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
					<h2>Đăng ký shop</h2>
					<div class="breadcrumb__option">
						<a href="/home">Trang chủ</a> <span>Đăng ký shop</span>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<!-- Breadcrumb Section End -->

<!-- Product Details Section Begin -->
<section class="product-details spad">
	<div class="container">
		<div class="row">
			<div class="col-lg-12">
				<c:if test="${not empty errorMessage}">
					<div class="alert alert-danger">${errorMessage}</div>
				</c:if>

				<form action="${pageContext.request.contextPath}/register-shop"
					method="POST">
					<!-- Truyền accountId dưới dạng input ẩn -->
					<c:choose>
						<c:when test="${not empty sessionScope.account}">
							<input type="hidden" name="accountId"
								value="${sessionScope.account.id}" />
						</c:when>
						<c:otherwise>
							<!-- Nếu chưa có tài khoản trong session, sẽ không gửi accountId -->
							<input type="hidden" name="accountId" value="0" />
						</c:otherwise>
					</c:choose>
					<div class="form-group">
						<label for="name">Tên Shop</label> <input type="text"
							class="form-control" id="name" name="name" required>
					</div>

					<div class="form-group">
						<label for="address">Địa Chỉ</label> <input type="text"
							class="form-control" id="address" name="address" required>
					</div>

					<div class="form-group">
						<label for="description">Mô Tả</label>
						<textarea class="form-control" id="description" name="description"
							rows="4" required></textarea>
					</div>

					<button type="submit" class="site-btn">Đăng Ký Shop</button>
				</form>
			</div>
		</div>
	</div>
</section>
<!-- Product Details Section End -->

