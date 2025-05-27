<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:url value="/assets/user" var="URL"></c:url>

<!-- Breadcrumb Section Begin -->
<section class="breadcrumb-section set-bg" data-setbg="${URL}/img/breadcrumb.jpg">
    <div class="container">
        <div class="row">
            <div class="col-lg-12 text-center">
                <div class="breadcrumb__text">
                    <h2>Chỉnh Sửa Thông Tin Shop</h2>
                    <div class="breadcrumb__option">
                        <!-- You can add navigation links here -->
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Breadcrumb Section End -->

<!-- Shop Edit Section Begin -->
<section class="shop-edit spad">
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

                <form action="${pageContext.request.contextPath}/shop/updateInfo" method="post">
                    <input type="hidden" name="shopId" value="${shop.id}">
                    <div class="form-group">
                        <label for="name">Tên Shop</label>
                        <input type="text" id="name" name="name" class="form-control" value="${shop.name}" required>
                    </div>
                    <div class="form-group">
                        <label for="address">Địa Chỉ</label>
                        <input type="text" id="address" name="address" class="form-control" value="${shop.address}" required>
                    </div>
                    <div class="form-group">
                        <label for="description">Mô Tả</label>
                        <textarea id="description" name="description" class="form-control" rows="4" required>${shop.description}</textarea>
                    </div>
                    <button type="submit" class="site-btn">Cập Nhật Thông Tin</button>
                </form>
            </div>
        </div>
    </div>
</section>
<!-- Shop Edit Section End -->