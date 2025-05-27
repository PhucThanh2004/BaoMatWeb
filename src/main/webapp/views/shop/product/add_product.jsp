
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<div class="card shadow mb-4">
	<div class="card-header py-3 d-flex justify-content-between">
		<h4 class="m-0 font-weight-bold text-primary">Thêm mới sản phẩm</h4>
	</div>
	<div class="card-body">
		<form
			action="${pageContext.request.contextPath}/shop/product/add-product"
			method="post" enctype="multipart/form-data">
			<input type="hidden" name="shop" value="${shop}">
			<div class="form-group">
				<label for="name">Tên Sản Phẩm</label> <input type="text" id="name"
					name="name" class="form-control" required>
			</div>

			<div class="form-group">
				<label for="description">Mô Tả</label>
				<textarea id="description" name="description" class="form-control"
					rows="20" required></textarea>
			</div>

			<div class="form-group">
				<label for="price">Giá</label> <input type="number" id="price"
					name="price" class="form-control" step="0.01" required>
			</div>

			<div class="form-group">
				<label for="stock_quantity">Số Lượng Tồn</label> <input
					type="number" id="stock_quantity" name="stock_quantity"
					class="form-control" required>
			</div>

			<div class="form-group">
				<label for="category_id">Danh Mục</label> <select id="category_id"
					name="category_id" class="form-control" required>
					<c:forEach var="category" items="${categories}">
						<option value="${category.id}">${category.name}</option>
					</c:forEach>
				</select>
			</div>
			<!-- Chọn hình ảnh -->
			<div class="form-group">
				<label for="product_images">Chọn Ảnh Sản Phẩm</label> <input
					type="file" id="product_images" name="product_images"
					class="form-control" multiple required>
			</div>

			<button type="submit" class="btn btn-primary">Thêm Sản Phẩm</button>
		</form>
	</div>
</div>

