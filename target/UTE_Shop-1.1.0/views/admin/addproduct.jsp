<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ include file="/commons/taglib.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Product</title>
</head>
<body>

    <h2>Add New Product</h2>

    <form action="<c:url value='/admin/product/insert'/>" method="post" enctype="multipart/form-data">
        
        <label for="ProductName">Product Name:</label>
        <input type="text" id="ProductName" name="ProductName" required />
        <br><br>

        <label for="Description">Description:</label>
        <textarea id="Description" name="Description" rows="4" cols="50" required></textarea>
        <br><br>

        <label for="Price">Price:</label>
        <input type="number" id="Price" name="Price" required />
        <br><br>

        <label for="Amount">Amount:</label>
        <input type="number" id="Amount" name="Amount" required />
        <br><br>

        <label for="CategoryID">Category:</label>
        <select name="CategoryID" required>
            <option value="">-- Select Category --</option>
            <c:forEach var="category" items="${listcate}">
                <option value="${category.categoryID}">
                    ${category.categoryName}
                </option>
            </c:forEach>
        </select>
        <br><br>

        <label for="SellerID">Seller ID:</label>
        <input type="number" id="SellerID" name="SellerID" required />
        <br><br>

        <label for="ImageLink">Image URL (optional):</label>
        <input type="text" name="ImageLink" />
        <br><br>

        <label for="images1">Upload Image:</label>
        <input type="file" name="images1" />
        <br><br>

        <button type="submit">Save</button>
    </form>

    <br><br>
    <a href="<c:url value='/admin/products'/>">Back to Product List</a>

</body>
</html>
