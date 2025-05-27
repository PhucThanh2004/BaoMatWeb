<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:url value="/assets/user" var="URL"></c:url>
<!-- Footer Section Begin -->
<footer class="footer spad">
	<div class="container">
		<div class="row">
			<div class="col-lg-3 col-md-6 col-sm-6">
				<div class="footer__about">
					<div class="footer__about__logo">
						<a href="./index.html"><img src="img/logo.png" alt=""></a>
					</div>
					<ul>
						<li>Địa chỉ: 1 Võ Văn Ngân, Phường Linh Chiểu, Thành phố Thủ
							Đức, Thành phố Hồ Chí Minh.</li>
						<li>Điện thoại: (+84-028)38968641</li>
						<li>Email: lathanhbatri2020@gmail.com</li>
					</ul>
				</div>
			</div>
			<div class="col-lg-4 col-md-6 col-sm-6 offset-lg-1">
				<div class="footer__widget">
					<h6>Liên kết hữu ích</h6>
					<ul>
						<li><a href="#">Về chúng tôi</a></li>
						<li><a href="#">Giới thiệu cửa hàng</a></li>
						<li><a href="#">Mua sắm an toàn</a></li>
						<li><a href="#">Thông tin giao hàng</a></li>
						<li><a href="#">Chính sách bảo mật</a></li>
						<li><a href="#">Sơ đồ trang web</a></li>
					</ul>
					<ul>
						<li><a href="#">Chúng tôi là ai</a></li>
						<li><a href="#">Dịch vụ của chúng tôi</a></li>
						<li><a href="#">Dự án</a></li>
						<li><a href="#">Liên hệ</a></li>
						<li><a href="#">Đổi mới</a></li>
						<li><a href="#">Đánh giá</a></li>
					</ul>
				</div>
			</div>
			<div class="col-lg-4 col-md-12">
				<div class="footer__widget">
					<h6>Đăng ký nhận bản tin của chúng tôi</h6>
					<p>Nhận cập nhật qua email về cửa hàng và các ưu đãi đặc biệt
						của chúng tôi.</p>
					<form action="#">
						<input type="text" placeholder="Nhập email của bạn">
						<button type="submit" class="site-btn">Đăng ký</button>
					</form>
					<div class="footer__widget__social">
						<a href="#"><i class="fa fa-facebook"></i></a> <a href="#"><i
							class="fa fa-instagram"></i></a> <a href="#"><i
							class="fa fa-twitter"></i></a> <a href="#"><i
							class="fa fa-pinterest"></i></a>
					</div>
				</div>
			</div>
		</div>
	</div>
</footer>
<!-- Footer Section End -->

<!-- Js Plugins -->
<script src="${URL}/js/jquery-3.3.1.min.js"></script>
<script src="${URL}/js/bootstrap.min.js"></script>
<script src="${URL}/js/jquery.nice-select.min.js"></script>
<script src="${URL}/js/jquery-ui.min.js"></script>
<script src="${URL}/js/jquery.slicknav.js"></script>
<script src="${URL}/js/mixitup.min.js"></script>
<script src="${URL}/js/owl.carousel.min.js"></script>
<script src="${URL}/js/main.js"></script>

</body>

</html>