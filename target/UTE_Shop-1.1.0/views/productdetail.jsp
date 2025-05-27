<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ include file="/commons/taglib.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product Detail</title>
</head>
<body>

    <h2>Product Detail</h2>

    <div>
       <c:choose>
					
					<c:when
						test="${product.imageLink != null && product.imageLink.startsWith('https')}">
						<c:url value="${product.imageLink}" var="imageUrl"></c:url>
					</c:when>
				
					<c:otherwise>
						<c:url value="/image?fname=${product.imageLink}" var="imageUrl"></c:url>
					</c:otherwise>
				</c:choose> <!-- Hiển thị ảnh sản phẩm --> <img height="150" width="200"
				src="${imageUrl}" alt="${product.productName}" />
    </div>

    <div>
        <!-- Hiển thị tên sản phẩm -->
        <strong>Product Name:</strong> ${product.productName}
    </div>
    <div>
        <!-- Hiển thị mã sản phẩm -->
        <strong>Product ID:</strong> ${product.productID}
    </div>
    
    <div>
        <!-- Hiển thị tên danh mục -->
        <strong>Category:</strong> 
        <c:forEach var="category" items="${listcate}">
            <c:if test="${category.categoryID == product.categoryID}">
                ${category.categoryName}
            </c:if>
        </c:forEach>
    </div>

    <div>
        <!-- Hiển thị giá sản phẩm -->
        <strong>Price:</strong> ${product.price} VND
    </div>
    <div>
        <!-- Hiển thị số lượng sản phẩm -->
        <strong>Amount:</strong> ${count}
    </div>
    <div>
        <!-- Hiển thị mô tả sản phẩm -->
        <strong>Description:</strong> ${product.description}
    </div>

    <br><br>
    <a href="<c:url value='/admin/products'/>">Back to Product List</a>

</body>
</html>
