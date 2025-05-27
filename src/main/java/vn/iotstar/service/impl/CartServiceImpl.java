package vn.iotstar.service.impl;

import vn.iotstar.dao.ICartDao;
import vn.iotstar.dao.ICategoryDao;
import vn.iotstar.dao.impl.CartDaoImpl;
import vn.iotstar.dao.impl.CategoryDaoImpl;
import vn.iotstar.models.CartDetailWithProduct;
import vn.iotstar.models.CartModel;
import vn.iotstar.service.ICartService;

import java.util.List;
import java.util.Map;

public class CartServiceImpl implements ICartService {

    private ICartDao cartDao;

    public CartServiceImpl() {
        cartDao = new CartDaoImpl();
    }

    @Override
    public void addToCart(int accountId, int productId, int quantity) throws Exception {
        CartModel cart = cartDao.findCartByAccountId(accountId);
        int cartId;

        if (cart == null) {
            cartId = cartDao.createCart(accountId);
        } else {
            cartId = cart.getId();
        }

        if (cartDao.isProductInCart(cartId, productId)) {
            cartDao.updateCartDetail(cartId, productId, quantity);
        } else {
            cartDao.addProductToCart(cartId, productId, quantity);
        }
    }

    @Override
    public List<CartDetailWithProduct> getCartDetailsWithProducts(int cartId) throws Exception {
        return cartDao.getCartDetailsWithProducts(cartId);
    }

    @Override
    public CartModel findCartByAccountId(int accountId) throws Exception {
        return cartDao.findCartByAccountId(accountId);
    }

    @Override
    public void updateCart(int cartId, Map<Integer, Integer> updatedQuantities) throws Exception {
        for (Map.Entry<Integer, Integer> entry : updatedQuantities.entrySet()) {
            int cartDetailId = entry.getKey();
            int quantity = entry.getValue();

            cartDao.updateCartDetail(cartDetailId, quantity);
        }
    }

	@Override
	public void deleteCartDetail(int cartDetailId) throws Exception {
		cartDao.deleteCartDetail(cartDetailId);
		
	}
}
