package vn.iotstar.service;

import vn.iotstar.models.OrderWithDetails;

import java.util.List;

public interface IOrderService {
    List<Integer> checkoutCart(int accountId, String paymentMethod, String note) throws Exception;
    List<OrderWithDetails> getOrdersByShopId(int shopId) throws Exception;

    void updateOrderStatus(int orderId, String status) throws Exception;

    List<OrderWithDetails> getOrdersByAccountId(int accountId) throws Exception;

    boolean cancelOrder(int orderId) throws Exception;
    
    void updateAddress(int orderId, int addressId) throws Exception;
}
