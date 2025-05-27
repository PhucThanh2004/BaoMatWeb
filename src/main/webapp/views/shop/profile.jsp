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
					<h2>Thông Tin Shop</h2>
					<div class="breadcrumb__option">
						
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<!-- Breadcrumb Section End -->

<!-- Shop Profile Section Begin -->
<section class="shop-profile spad">
	<div class="container">
		<div class="row">
			<div class="col-lg-12">
				<!-- Hiển thị thông báo lỗi hoặc thành công -->
				<c:if test="${not empty errorMessage}">
					<div class="alert alert-danger">${errorMessage}</div>
				</c:if>
				<c:if test="${not empty successMessage}">
					<div class="alert alert-success">${successMessage}</div>
				</c:if>

				<!-- Kiểm tra nếu shop tồn tại -->
				<c:choose>
					<c:when test="${not empty shop}">
						<!-- Hiển thị thông tin shop -->
						<div class="shop-info">
							
							<ul class="list-group">
								<li class="list-group-item"><strong>Tên Shop:</strong> ${shop.name}</li>
								<li class="list-group-item"><strong>Địa Chỉ:</strong> ${shop.address}</li>
								<li class="list-group-item"><strong>Mô Tả:</strong> ${shop.description}</li>
								
							</ul>
						</div>

						<!-- Nút chỉnh sửa thông tin -->
						<div class="mt-4">
							<a href="${pageContext.request.contextPath}/shop/edit?shopId=${shop.id}" class="site-btn">
								Chỉnh Sửa Thông Tin
							</a>
						</div>
					</c:when>
					<c:otherwise>
						<!-- Hiển thị thông báo nếu shop không tồn tại -->
						<div class="alert alert-warning">
							Shop chưa được đăng ký. Vui lòng đăng ký shop trước!
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
</section>
<!-- Shop Profile Section End -->
