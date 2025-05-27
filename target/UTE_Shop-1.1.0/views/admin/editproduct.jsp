<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ include file="/commons/taglib.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Product</title>
</head>
<body>

    <h2>Edit Product</h2>

    <form action="<c:url value='/admin/product/update'/>" method="post" enctype="multipart/form-data">
        <input type="hidden" name="ProductID" value="${product.productID}" />

        <label for="ProductName">Product Name:</label>
        <input type="text" id="ProductName" name="ProductName" value="${product.productName}" required />
        <br><br>

        <label for="Description">Description:</label>
        <textarea id="Description" name="Description" rows="4" cols="50" required>${product.description}</textarea>
        <br><br>

        <label for="Price">Price:</label>
        <input type="number" id="Price" name="Price" value="${product.price}" required />
        <br><br>

        <label for="Amount">Amount:</label>
        <input type="number" id="Amount" name="Amount" value="${product.amount}" required />
        <br><br>

        <label for="CategoryID">Category:</label>
        <select name="CategoryID" required>
            <option value="">-- Select Category --</option>
            <c:forEach var="category" items="${listcate}">
                <option value="${category.categoryID}" <c:if test="${product.categoryID == category.categoryID}">selected</c:if>>
                    ${category.categoryName}
                </option>
            </c:forEach>
        </select>
        <br><br>

        <label for="SellerID">Seller:</label>
        <input type="number" id="SellerID" name="SellerID" value="${product.sellerID}" required />
        <br><br>

        <label for="ImageLink">Image URL (optional):</label>
        <input type="text" name="ImageLink" value="${product.imageLink}" />
        <br><br>

        <label for="images1">Upload New Image (optional):</label>
        <input type="file" name="images1" />
        <br><br>

        <button type="submit">Save</button>
    </form>

    <br><br>
    <a href="<c:url value='/admin/products'/>">Back to Product List</a>

</body>
</html>
