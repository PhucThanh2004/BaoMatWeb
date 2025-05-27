package vn.iotstar.service.impl;

import vn.iotstar.configs.DBConnectSQL;
import vn.iotstar.dao.ICartDao;
import vn.iotstar.dao.IOrderDao;
import vn.iotstar.dao.impl.CartDaoImpl;
import vn.iotstar.dao.impl.OrderDaoImpl;
import vn.iotstar.models.CartDetailWithProduct;
import vn.iotstar.models.CartModel;
import vn.iotstar.models.OrderWithDetails;
import vn.iotstar.service.IOrderService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderServiceImpl extends DBConnectSQL implements IOrderService {
    private ICartDao cartDao;
    private IOrderDao orderDao;

    public OrderServiceImpl() {
        cartDao = new CartDaoImpl();
        orderDao = new OrderDaoImpl();
    }

    @Override
    public List<Integer> checkoutCart(int accountId, String paymentMethod, String note) throws Exception {

        CartModel cartOfUser = cartDao.findCartByAccountId(accountId);
        // Lấy chi tiết giỏ hàng của user
        List<CartDetailWithProduct> cartDetails = cartDao.getCartDetailsWithProducts(cartOfUser.getId());

        if (cartDetails.isEmpty()) {
            throw new Exception("Giỏ hàng trống!");
        }

        Map<Integer, List<CartDetailWithProduct>> productsByShop = new HashMap<>();
        for (CartDetailWithProduct detail : cartDetails) {
            productsByShop.computeIfAbsent(detail.getShopId(), k -> new ArrayList<>()).add(detail);
        }

        List<Integer> orderIds = new ArrayList<>();
        for (Map.Entry<Integer, List<CartDetailWithProduct>> entry : productsByShop.entrySet()) {
            int shopId = entry.getKey();
            List<CartDetailWithProduct> shopProducts = entry.getValue();

            double totalAmount = shopProducts.stream()
                    .mapToDouble(product -> product.getProductPrice() * product.getQuantity())
                    .sum();

            int orderId = orderDao.createOrder(accountId, shopId, totalAmount, paymentMethod, note);

            for (CartDetailWithProduct product : shopProducts) {
                orderDao.createOrderDetail(orderId, product.getProductId(), product.getQuantity(), product.getProductPrice());
                cartDao.deleteCartDetail(product.getCartDetailId());
            }

            orderIds.add(orderId);
        }

        return orderIds;
    }

    @Override
    public List<OrderWithDetails> getOrdersByShopId(int shopId) throws Exception {
        return orderDao.getOrdersByShopId(shopId);
    }

    @Override
    public void updateOrderStatus(int orderId, String status) throws Exception {
        orderDao.updateOrderStatus(orderId, status);
    }

    @Override
    public List<OrderWithDetails> getOrdersByAccountId(int accountId) throws Exception {
        return orderDao.getOrdersByAccountId(accountId);
    }

    @Override
    public boolean cancelOrder(int orderId) throws Exception {
        return orderDao.cancelOrder(orderId);
    }

	@Override
	public void updateAddress(int orderId, int addressId) throws Exception {
		orderDao.updateAddress(orderId, addressId);
		
	}

   
		
	
}
