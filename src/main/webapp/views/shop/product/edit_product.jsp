<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="card shadow mb-4">
    <div class="card-header py-3">
        <h4 class="m-0 font-weight-bold text-primary">Chỉnh sửa sản phẩm</h4>
    </div>
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/shop/product/edit" method="post" enctype="multipart/form-data">
            <!-- Input ID sản phẩm (ẩn) -->
            <input type="hidden" name="shopId" value="${shop}" />
            <input type="hidden" name="id" value="${product.id}" />

            <div class="form-group">
                <label for="name">Tên Sản Phẩm</label>
                <input type="text" id="name" name="name" class="form-control" value="${product.name}" required>
            </div>

            <div class="form-group">
                <label for="description">Mô Tả</label>
                <textarea id="description" name="description" class="form-control" rows="20" required>${product.description}</textarea>
            </div>

            <div class="form-group">
                <label for="price">Giá</label>
                <input type="number" id="price" name="price" class="form-control" value="${product.price}" step="0.01" required>
            </div>

            <div class="form-group">
                <label for="stock_quantity">Số Lượng Tồn</label>
                <input type="number" id="stock_quantity" name="stock_quantity" class="form-control" value="${product.stockQuantity}" required>
            </div>

            <div class="form-group">
                <label for="category_id">Danh Mục</label>
                <select id="category_id" name="category_id" class="form-control" required>
                    <c:forEach var="category" items="${categories}">
                        <option value="${category.id}" ${category.id == product.categoryId ? 'selected' : ''}>${category.name}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="product_images">Chọn Ảnh Sản Phẩm</label>
                <input type="file" id="product_images" name="product_images" class="form-control" multiple>
            </div>

            <button type="submit" class="btn btn-primary">Cập Nhật</button>
        </form>
    </div>
</div>
