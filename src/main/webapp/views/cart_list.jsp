<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:url value="/assets/user" var="URL"></c:url>

<!-- Hero Section Begin -->
<section class="hero hero-normal">
    <div class="container">
        <div class="row">
            <div class="col-lg-3">
                <div class="hero__categories">
                    <div class="hero__categories__all">
                        <i class="fa fa-bars"></i>
                        <span>Danh mục sản phẩm</span>
                    </div>
                    <ul>
                        <c:forEach var="category" items="${categories}">
                            <li><a href="${pageContext.request.contextPath}/category?id=${category.id}">${category.name}</a></li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
            <div class="col-lg-9">
                <div class="hero__search">
                    <div class="hero__search__form">
                        <form action="${pageContext.request.contextPath}/search" method="get">
                            <div class="hero__search__categories">
                                Tất cả danh mục <span class="arrow_carrot-down"></span>
                            </div>
                            <input type="text" name="keyword" placeholder="Nhập từ khóa tìm kiếm...">
                            <button type="submit" class="site-btn">TÌM KIẾM</button>
                        </form>
                    </div>
                    <div class="hero__search__phone">
                        <div class="hero__search__phone__icon">
                            <i class="fa fa-phone"></i>
                        </div>
                        <div class="hero__search__phone__text">
                            <h5>+65 11.188.888</h5>
                            <span>Hỗ trợ 24/7</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Hero Section End -->

<!-- Breadcrumb Section Begin -->
<section class="breadcrumb-section set-bg" data-setbg="${URL}/img/breadcrumb.jpg">
    <div class="container">
        <div class="row">
            <div class="col-lg-12 text-center">
                <div class="breadcrumb__text">
                    <h2>Giỏ hàng của bạn</h2>
                    <div class="breadcrumb__option">
                        <a href="${pageContext.request.contextPath}/home">Trang chủ</a>
                        <span>Giỏ hàng</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Breadcrumb Section End -->

<!-- Shopping Cart Section Begin -->
<section class="shoping-cart spad">
    <div class="container">
        <form action="${pageContext.request.contextPath}/cart/update" method="post">
            <div class="row">
                <div class="col-lg-12">
                    <div class="shoping__cart__table">
                        <table>
                            <thead>
                                <tr>
                                    <th class="shoping__product">Sản phẩm</th>
                                    <th>Giá</th>
                                    <th>Số lượng</th>
                                    <th>Tổng tiền</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="cart" items="${carts}">
                                    <tr>
                                   
                                        <td class="shoping__cart__item">
                                            <img width="50px" src="${pageContext.request.contextPath}/image?fname=${cart.productImage}" alt="">
                                            <h5>${cart.productName}</h5>
                                            <input type="hidden" name="cartDetailId" value="${cart.cartDetailId}" >
                                        </td>
                                        <td class="shoping__cart__price">${cart.productPrice}</td>
                                        <td class="shoping__cart__quantity">
                                            <div class="quantity">
                                                <div class="pro-qty">
                                                    <input type="number" name="quantity_${cart.cartDetailId}" value="${cart.quantity}" min="1">
                                                </div>
                                            </div>
                                        </td>
                                        <td class="shoping__cart__total">${cart.productPrice * cart.quantity}</td>
                                        <td class="shoping__cart__item__close">
                                            <a href="${pageContext.request.contextPath}/cart/delete?cartDetailId=${cart.cartDetailId}" class="icon_close"></a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="shoping__cart__btns">
                        <a href="${pageContext.request.contextPath}/home" class="primary-btn cart-btn">TIẾP TỤC MUA SẮM</a>
                        <button type="submit" class="primary-btn cart-btn cart-btn-right">
                            <span class="icon_loading"></span> Cập nhật giỏ hàng
                        </button>
                    </div>
                </div>
                <div class="col-lg-6"></div>
                <div class="col-lg-6">
                    <div class="shoping__checkout">
                        <h5>Thành tiền</h5>
                        <ul>
                            <li>Tổng cộng: <span>${totalAmount}</span></li>
                        </ul>
                        <a href="${pageContext.request.contextPath}/checkout" class="primary-btn">TIẾP TỤC THANH TOÁN</a>
                    </div>
                </div>
            </div>
        </form>
    </div>
</section>
<!-- Shopping Cart Section End -->
