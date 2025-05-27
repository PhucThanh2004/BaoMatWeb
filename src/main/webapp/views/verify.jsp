<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Xác minh tài khoản</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <!-- Breadcrumb Section Begin -->
    <section class="breadcrumb-section">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <div class="breadcrumb__text">
                        <h2>Xác minh tài khoản</h2>
                        <div class="breadcrumb__option">
                            <a href="${pageContext.request.contextPath}/home">Trang chủ</a>
                            <span>Xác minh</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- Breadcrumb Section End -->

    <!-- Verify Section Begin -->
    <section class="verify-section spad">
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <!-- Hiển thị thông báo lỗi nếu có -->
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">
                            ${error}
                        </div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/VerifyCode" method="POST" class="form">
                        <div class="form-group">
                            <label for="authcode">Mã xác minh</label>
                            <input type="text" class="form-control" id="authcode" name="authcode" placeholder="Nhập mã xác minh" required>
                        </div>

                        <button type="submit" class="site-btn">Kích hoạt</button>
                    </form>
                </div>
            </div>
        </div>
    </section>
    <!-- Verify Section End -->

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.4.4/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
