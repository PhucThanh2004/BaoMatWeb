<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<!DOCTYPE html>
<html lang="vi">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap CSS -->
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	rel="stylesheet">
</head>

<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<a class="navbar-brand" href="#">Trang Web</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarNav" aria-controls="navbarNav"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="navbar-nav ml-auto">
				<!-- Trang Chủ -->
				<li class="nav-item"><a class="nav-link" href="#">Trang Chủ</a>
				</li>

				<li class="nav-item"><a class="nav-link"
					href="${pageContext.request.contextPath}/admin/products">Sản phẩm</a></li>



				<li class="nav-item"><a class="nav-link" href="#">Trang
						quản trị</a></li>

				<li class="nav-item"><c:choose>
						<c:when test="${sessionScope.account == null}">
							<a class="nav-link"
								href="${pageContext.request.contextPath}/login">Đăng nhập</a>
						</c:when>
						<c:otherwise>
							<!-- Nếu đã đăng nhập, có thể hiển thị tên người dùng hoặc một nút khác -->
							<a class="nav-link" href="#">Chào,
								${sessionScope.account.username}</a>
			| <a href="${pageContext.request.contextPath }/logout">Logout</a>

						</c:otherwise>
					</c:choose></li>


			</ul>
		</div>
	</nav>


	<!-- Bootstrap JS, Popper.js -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>

</html>