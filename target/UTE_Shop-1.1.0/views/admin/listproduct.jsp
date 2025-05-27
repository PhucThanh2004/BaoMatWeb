<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ include file="/commons/taglib.jsp"%>

<!-- Link to add a new product -->
<a href="${pageContext.request.contextPath}/admin/product/add">Add
	Product</a>

<table border="1" width="100%">
	<tr>
		<th>STT</th>
		<th>ProductID</th>
		<th>Product Name</th>
		<th>Description</th>
		<th>Price</th>
		<th>Image</th>
		<th>Category</th>
		<th>Seller</th>
		<th>Amount</th>
		<th>Actions</th>
	</tr>

	<c:forEach items="${listproduct}" var="product" varStatus="STT">
		<tr>
			<td>${STT.index + 1}</td>
			<td>${product.productID}</td>
			<td>${product.productName}</td>

			<td>${product.description}</td>

			<td>${product.price}</td>

			<td><c:choose>
					
					<c:when
						test="${product.imageLink != null && product.imageLink.startsWith('https')}">
						<c:url value="${product.imageLink}" var="imageUrl"></c:url>
					</c:when>
				
					<c:otherwise>
						<c:url value="/image?fname=${product.imageLink}" var="imageUrl"></c:url>
					</c:otherwise>
				</c:choose> <!-- Hiển thị ảnh sản phẩm --> <img height="150" width="200"
				src="${imageUrl}" alt="${product.productName}" /></td>

			<td><!-- Assuming you have a list of categories, this will display the category name -->
				<c:forEach items="${listcate}" var="category">
                    <c:if test="${category.categoryID == product.categoryID}">
                        ${category.categoryName}
                    </c:if>
                </c:forEach>
			</td>
			<!-- Displaying seller -->
			<td>${product.sellerID}</td>
			<!-- Displaying product amount -->
			<td>${product.amount}</td>
			<!-- Action links: Edit, Delete, View Details -->
			<td><a
				href="<c:url value='/admin/product/edit?ProductID=${product.productID}'/>">Edit</a>
				| <a
				href="<c:url value='/admin/product/delete?ProductID=${product.productID}'/>">Delete</a>
				| <a
				href="<c:url value='/product/detail?ProductID=${product.productID}'/>">View
					Details</a></td>
		</tr>
	</c:forEach>
</table>
