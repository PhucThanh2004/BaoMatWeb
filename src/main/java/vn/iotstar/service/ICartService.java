package vn.iotstar.service;

import vn.iotstar.models.CartDetailWithProduct;
import vn.iotstar.models.CartModel;

import java.util.List;
import java.util.Map;

public interface ICartService {
    void addToCart(int accountId, int productId, int quantity) throws Exception;
    List<CartDetailWithProduct> getCartDetailsWithProducts(int cartId) throws Exception;
    CartModel findCartByAccountId(int accountId) throws Exception;
    void updateCart(int cartId, Map<Integer, Integer> updatedQuantities) throws Exception;
    void deleteCartDetail(int cartDetailId) throws Exception;
}
