<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<div class="card shadow mb-4">
	<div class="card-header py-3 d-flex justify-content-between">
		<h4 class="m-0 font-weight-bold text-primary">Danh sách sản phẩm</h4>
		<a
			href="${pageContext.request.contextPath}/shop/product/add-product?id=${shop}"
			class="btn btn-primary">Thêm mới</a>
	</div>
	<div class="card-body">
		<div class="table-responsive">
			<c:if test="${not empty sessionScope.message}">
				<div class="alert alert-success" role="alert">
					${sessionScope.message}</div>
				<c:remove var="message" scope="session" />
			</c:if>

			<c:if test="${not empty sessionScope.error}">
				<div class="alert alert-danger" role="alert">
					${sessionScope.error}</div>
				<c:remove var="error" scope="session" />
			</c:if>
			<table class="table table-bordered" id="dataTable" width="100%"
				cellspacing="0">
				<thead>
					<tr>
						<th>ID</th>
						<th>Ảnh SP</th>
						<th>Sản phẩm</th>
						<th>Giá</th>
						<th>Số lượng tồn</th>
						<th>Danh mục</th>
						<th>Hành động</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="product" items="${products}">
						<tr>
							<td>${product.id}</td>
							<td><c:choose>
									<c:when
										test="${not empty product.images and not empty product.images[0]}">
										<img
											src="${pageContext.request.contextPath}/image?fname=${product.images[0]}"
											alt="Product Image" style="width: 70px; height: 50px;" />
									</c:when>
								</c:choose></td>
							<td>${product.name}</td>
							<td>${product.price}</td>
							<td>${product.stockQuantity}</td>
							<td>${product.categoryId}</td>
							<td><a
								href="${pageContext.request.contextPath}/shop/product/edit?id=${product.id}&shopId=${shop}"
								class="btn btn-info">Sửa</a> <a
								href="${pageContext.request.contextPath}/shop/product/delete?id=${product.id}&shopId=${shop}"
								class="btn btn-danger"
								onclick="return confirm('Bạn có chắc chắn muốn xóa sản phẩm này?');">
									Xóa </a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="d-flex justify-content-end">
				<nav aria-label="Pagination">
					<ul class="pagination">
						<!-- Nút Previous -->
						<li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
							<a class="page-link" href="?page=${currentPage - 1}&id=${shop}">Previous</a>
						</li>

						<!-- Nút Page Numbers -->
						<c:forEach var="i" begin="${begin}" end="${end}" step="1">
							<li class="page-item ${i == currentPage ? 'active' : ''}"><c:choose>
									<c:when test="${i == currentPage}">
										<span class="page-link">${i} <span class="sr-only">(current)</span></span>
									</c:when>
									<c:otherwise>
										<a class="page-link" href="?page=${i}&id=${shop}">${i}</a>
									</c:otherwise>
								</c:choose></li>
						</c:forEach>

						<!-- Nút Next -->
						<li
							class="page-item ${currentPage >= totalPages ? 'disabled' : ''}">
							<a class="page-link" href="?page=${currentPage + 1}&id=${shop}">Next</a>
						</li>

					</ul>
				</nav>
			</div>

		</div>
	</div>
</div>
