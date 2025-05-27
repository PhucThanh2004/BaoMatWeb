package vn.iotstar.dao;

import vn.iotstar.models.CartDetailWithProduct;
import vn.iotstar.models.CartModel;

import java.util.List;

public interface ICartDao {
    CartModel findCartByAccountId(int accountId) throws Exception;
    int createCart(int accountId) throws Exception;
    void addProductToCart(int cartId, int productId, int quantity) throws Exception;
    void updateCartDetail(int cartId, int productId, int quantity) throws Exception;
    boolean isProductInCart(int cartId, int productId) throws Exception;
    List<CartDetailWithProduct> getCartDetailsWithProducts(int cartId) throws  Exception;
    void updateCartDetail(int cartDetailId, int quantity) throws Exception;
    void deleteCartDetail(int cartDetailId) throws Exception;
}
