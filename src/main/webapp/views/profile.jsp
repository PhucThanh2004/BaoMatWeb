<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<c:url value="/assets/user" var="URL"></c:url>

<!-- Breadcrumb Section Begin -->
<section class="breadcrumb-section"
	style="background-image: url('<c:url value="/image?fname=${account.cover_image}" />'); height: 250px; background-size: cover; background-position: center; width: 100%; position: relative;">
	<div class="profile-header">

		<div class="profile-avatar"
			style="margin-top: 175px; position: relative;">

			<c:url value="/image?fname=${account.avatar}" var="imageUrl"></c:url>
			<img src="${imageUrl}" alt="Avatar" class="img-thumbnail mt-3"
				style="width: 150px;">
			<!-- Form Upload Avatar -->
			<form
				action="${pageContext.request.contextPath}/account/updateAvatar"
				method="POST" enctype="multipart/form-data">
				<!-- fix -->
				<input type="hidden" name="csrf_token" value="${sessionScope.csrf_token}">
				
				<!-- Ẩn input file mặc định -->
				<input type="file" name="avatar" id="avatarUpload"
					class="avatar-upload-input" style="display: none;">

				<!-- Thêm input ẩn để truyền email -->
				<input type="hidden" name="email" value="${account.email}">


				<!-- Nút có hình biểu tượng sửa avatar -->
				<label for="avatarUpload" class="edit-avatar-btn"
					style="cursor: pointer; position: absolute; bottom: 0; right: 0; transform: translate(50%, 50%);">
					<img src="${URL}/img/camera.png" alt="Edit Avatar"
					style="width: 24px; height: 24px;">
				</label>
			</form>

			<script>
				// Lắng nghe sự kiện khi người dùng chọn file avatar
				document.getElementById("avatarUpload").addEventListener(
						"change", function() {
							// Tự động submit form ngay khi người dùng chọn file
							this.form.submit();
						});
			</script>

		</div>

		<!-- Form cập nhật ảnh bìa -->
		<form
			action="${pageContext.request.contextPath}/account/updateCoverImage"
			method="POST" enctype="multipart/form-data">
			<!-- fix -->
			<input type="hidden" name="csrf_token" value="${sessionScope.csrf_token}">
			
			<!-- Ẩn input file mặc định -->
			<input type="file" name="cover_image" id="coverImageUpload"
				class="cover-upload-input" style="display: none;">

			<!-- Thêm input ẩn để truyền email -->
			<input type="hidden" name="email" value="${account.email}">

			<!-- Nút sửa ảnh bìa -->
			<label for="coverImageUpload" class="edit-cover-btn"
				style="cursor: pointer; position: absolute; bottom: 10px; right: 10px; background-color: #fff; color: #000; padding: 8px 12px; border-radius: 5px; display: flex; align-items: center; gap: 5px; box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2); font-weight: bold;">
				<img src="${URL}/img/camera.png" alt="Edit Cover"
				style="width: 24px; height: 24px;">Cập nhật ảnh bìa
			</label>

		</form>

		<script>
			// Lắng nghe sự kiện khi người dùng chọn file cover image
			document.getElementById("coverImageUpload").addEventListener(
					"change", function() {
						// Tự động submit form ngay khi người dùng chọn file
						this.form.submit();
					});
		</script>


	</div>
</section>
<!-- Breadcrumb Section End -->



<!-- Profile Section Begin -->
<section class="profile-section spad">
	<div class="container">
		<div class="row">
			<!-- User Information Section -->
			<div class="col-lg-6">
				<h4>Thông tin cá nhân</h4>
				<form
					action="${pageContext.request.contextPath}/account/updateAccount"
					" method="POST" enctype="multipart/form-data">
					
					<!-- fix -->
					<input type="hidden" name="csrf_token" value="${sessionScope.csrf_token}">	

					<!-- Name -->
					<div class="form-group">
						<label for="fullName">Họ và tên</label> <input type="text"
							class="form-control" id="fullName" name="fullName"
							value="${account.name}" required>
					</div>

					<!-- Email -->
					<div class="form-group">
						<label for="email">Email</label> <input type="email"
							class="form-control" id="email" name="email"
							value="${account.email}" readonly>
					</div>

					<!-- Phone -->
					<div class="form-group">
						<label for="phone">Số điện thoại</label> <input type="text"
							class="form-control" id="phone" name="phone"
							value="${account.phone}">
					</div>

					<!-- Submit -->
					<button type="submit" class="site-btn">Cập nhật thông tin</button>
				</form>
			</div>

			<!-- Shipping Address Management -->
			<div class="col-lg-6">
				<h4>Quản lý địa chỉ nhận hàng</h4>
				<div class="address-list">
					<c:forEach items="${address}" var="addressItem">
						<div class="address-item">
							<div class="address-frame">

								<p>
									<strong>Địa chỉ:</strong> ${addressItem.province},
									${addressItem.district}, ${addressItem.wards},
									${addressItem.detail}
								</p>
								<p>
									<strong>Số điện thoại:</strong> ${addressItem.phone}
								</p>
								<form
									action="${pageContext.request.contextPath}/account/deleteAddress"
									method="POST">
									<!-- fix -->
									<input type="hidden" name="csrf_token" value="${sessionScope.csrf_token}">	
									
									<input type="hidden" name="email" value="${account.email}">
									<input type="hidden" name="id" value="${addressItem.id}">
									<button type="submit" class="btn btn-danger btn-sm">Xóa</button>
								</form>



							</div>
						</div>
						<hr>
					</c:forEach>
				</div>

				<form action="${pageContext.request.contextPath}/account/addAddress"
					method="POST">
					<!-- fix -->
					<input type="hidden" name="csrf_token" value="${sessionScope.csrf_token}">	
					
					<h4>Thêm địa chỉ nhận hàng</h4>
					<div class="form-group">
						<label for="addressCity">Tỉnh/Thành phố</label> <input type="text"
							class="form-control" id="addressCity" name="province" required>
					</div>
					<div class="form-group">
						<label for="addressDistrict">Quận/Huyện</label> <input type="text"
							class="form-control" id="addressDistrict" name="district"
							required>
					</div>
					<div class="form-group">
						<label for="addressWard">Phường/Xã</label> <input type="text"
							class="form-control" id="addressWard" name="wards" required>
					</div>
					<div class="form-group">
						<label for="addressDetail">Địa chỉ chi tiết</label> <input
							type="text" class="form-control" id="addressDetail" name="detail"
							required>
					</div>
					<div class="form-group">
						<label for="addressPhone">Số điện thoại nhận hàng</label> <input
							type="text" class="form-control" id="addressPhone" name="phone"
							required>

					</div>
					<input type="hidden" name="email" value="${account.email}">
					<button type="submit" class="site-btn">Thêm địa chỉ</button>
				</form>
			</div>

		</div>
	</div>
</section>
<!-- Profile Section End -->
