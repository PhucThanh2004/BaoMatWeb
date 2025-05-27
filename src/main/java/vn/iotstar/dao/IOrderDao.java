package vn.iotstar.dao;

import vn.iotstar.models.OrderWithDetails;

import java.util.List;

public interface IOrderDao {
    int createOrder(int accountId, int shopId, double totalAmount, String paymentMethod, String note) throws Exception;
    void createOrderDetail(int orderId, int productId, int quantity, double price) throws Exception;
    List<OrderWithDetails> getOrdersByShopId(int shopId) throws Exception;

    void updateOrderStatus(int orderId, String status) throws Exception;

    List<OrderWithDetails> getOrdersByAccountId(int accountId) throws Exception;

    boolean cancelOrder(int orderId) throws Exception;
    void updateAddress(int orderId, int addressId) throws Exception ;
}
