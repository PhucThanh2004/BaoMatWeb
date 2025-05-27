package vn.iotstar.dao.impl;

import vn.iotstar.configs.DBConnectSQL;


import vn.iotstar.dao.IOrderDao;
import vn.iotstar.models.AddressModel;
import vn.iotstar.models.OrderDetailWithProduct;
import vn.iotstar.models.OrderWithDetails;
import vn.iotstar.models.ProductModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;





public class OrderDaoImpl implements IOrderDao {
	
	
	public static void main(String[] args) {
        // Khởi tạo đối tượng OrderDaoImpl để gọi phương thức updateAddress
        OrderDaoImpl orderDao = new OrderDaoImpl();

        // Các tham số cần thiết cho phương thức updateAddress
        int orderId = 25;      // ID của đơn hàng cần cập nhật
        int newAddressId = 5; // ID của địa chỉ mới cần cập nhật cho đơn hàng

        try {
            // Gọi phương thức updateAddress để cập nhật địa chỉ cho đơn hàng
            orderDao.updateAddress(orderId, newAddressId);

            // In ra thông báo thành công
            System.out.println("Order " + orderId + " has been successfully updated with new address ID: " + newAddressId);
        } catch (Exception e) {
            // Xử lý lỗi nếu có
            e.printStackTrace();
        }
    }
	
    private DBConnectSQL dbConnectSQL;

    public OrderDaoImpl() {
        dbConnectSQL = new DBConnectSQL();
    }

    @Override
    public int createOrder(int accountId, int shopId, double totalAmount, String paymentMethod, String note) throws Exception {
        String sql = "INSERT INTO orders (account_id, shop_id, total_amount, payment_method, status, note, created_at) " +
                "VALUES (?, ?, ?, ?, 'PENDING', ?, GETDATE())";

        try (Connection conn = dbConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, accountId);
            stmt.setInt(2, shopId);
            stmt.setDouble(3, totalAmount);
            stmt.setString(4, paymentMethod);
            stmt.setString(5, note);
            //stmt.setInt(6, addressId);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Trả về ID của order vừa tạo
            }
        }

        throw new Exception("Không thể tạo order!");
    }

    @Override
    public void createOrderDetail(int orderId, int productId, int quantity, double price) throws Exception {
        String sql = "INSERT INTO order_details (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = dbConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);
            stmt.setDouble(4, price);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<OrderWithDetails> getOrdersByShopId(int shopId) throws Exception {
    	String sql = "SELECT o.id AS order_id, o.account_id, o.shop_id, o.total_amount, o.payment_method, " +
                "o.status, o.note, o.created_at, o.address_id, " +
                "od.id AS order_detail_id, od.product_id, od.quantity, od.price, " +
                "p.name AS product_name, p.description AS product_description, p.price AS product_price, " +
                "pi.product_image AS product_image, " +
                "a.name AS account_name, a.email AS account_email, " +
                "ad.email AS address_email, ad.province AS address_province, ad.district AS address_district, " +
                "ad.wards AS address_wards, ad.detail AS address_detail, ad.phone AS address_phone " +
                "FROM orders o " +
                "JOIN accounts a ON o.account_id = a.id " +
                "JOIN order_details od ON o.id = od.order_id " +
                "JOIN products p ON od.product_id = p.id " +
                "LEFT JOIN (SELECT product_id, MIN(product_image) AS product_image FROM product_images GROUP BY product_id) pi ON p.id = pi.product_id " +
                "LEFT JOIN address ad ON o.address_id = ad.id " + // Thêm phần này để lấy địa chỉ
                "WHERE o.shop_id = ? " +
                "ORDER BY o.created_at DESC";


        List<OrderWithDetails> orders = new ArrayList<>();
        try (Connection conn = dbConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, shopId);
            ResultSet rs = stmt.executeQuery();

            Map<Integer, OrderWithDetails> orderMap = new HashMap<>();

            while (rs.next()) {
                int orderId = rs.getInt("order_id");

                OrderWithDetails order = orderMap.get(orderId);
                if (order == null) {
                    order = new OrderWithDetails();
                    order.setOrderId(orderId);
                    order.setAccountId(rs.getInt("account_id"));
                    order.setShopId(rs.getInt("shop_id"));
                    order.setTotalAmount(rs.getDouble("total_amount"));
                    order.setPaymentMethod(rs.getString("payment_method"));
                    order.setStatus(rs.getString("status"));
                    order.setNote(rs.getString("note"));
                    order.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    order.setAddress_id(rs.getInt("address_id"));
                    order.setAccountName(rs.getString("account_name"));
                    order.setAccountEmail(rs.getString("account_email"));
                    
                    AddressModel address = new AddressModel();
                    address.setEmail(rs.getString("address_email"));
                    address.setProvince(rs.getString("address_province"));
                    address.setDistrict(rs.getString("address_district"));
                    address.setWards(rs.getString("address_wards"));
                    address.setDetail(rs.getString("address_detail"));
                    address.setPhone(rs.getString("address_phone"));
                    order.setAddress(address); 
                    
                    order.setOrderDetails(new ArrayList<>());

                    // Thêm đơn hàng vào Map
                    orderMap.put(orderId, order);
                }

                OrderDetailWithProduct detail = new OrderDetailWithProduct();
                detail.setOrderDetailId(rs.getInt("order_detail_id"));
                detail.setProductId(rs.getInt("product_id"));
                detail.setQuantity(rs.getInt("quantity"));
                detail.setPrice(rs.getDouble("price"));

                ProductModel product = new ProductModel();
                product.setId(rs.getInt("product_id"));
                product.setName(rs.getString("product_name"));
                product.setDescription(rs.getString("product_description"));
                product.setPrice(rs.getDouble("product_price"));

                String productImage = rs.getString("product_image");
                if (productImage != null) {
                    product.setImages(List.of(productImage));  // Nếu có ảnh, gán ảnh cho sản phẩm
                }

                // Thêm sản phẩm vào chi tiết đơn hàng
                detail.setProduct(product);

                // Thêm chi tiết đơn hàng vào đơn hàng
                order.getOrderDetails().add(detail);
            }

            // Thêm tất cả các đơn hàng vào danh sách trả về
            orders.addAll(orderMap.values());
        }

        return orders;
    }

    @Override
    public void updateOrderStatus(int orderId, String status) throws Exception {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";

        try (Connection conn = dbConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, orderId);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<OrderWithDetails> getOrdersByAccountId(int accountId) throws Exception {
        String sql = "SELECT o.id AS order_id, o.account_id, o.shop_id, o.total_amount, o.payment_method, " +
                "o.status, o.note, o.created_at, " +
                "od.id AS order_detail_id, od.product_id, od.quantity, od.price, " +
                "p.name AS product_name, p.description AS product_description, p.price AS product_price, " +
                "pi.product_image AS product_image, " +
                "a.name AS account_name, a.email AS account_email " +
                "FROM orders o " +
                "JOIN accounts a ON o.account_id = a.id " +
                "JOIN order_details od ON o.id = od.order_id " +
                "JOIN products p ON od.product_id = p.id " +
                "LEFT JOIN (SELECT product_id, MIN(product_image) AS product_image FROM product_images GROUP BY product_id) pi ON p.id = pi.product_id " +
                "WHERE o.account_id = ? " +
                "ORDER BY o.created_at DESC";

        List<OrderWithDetails> orders = new ArrayList<>();
        try (Connection conn = dbConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            Map<Integer, OrderWithDetails> orderMap = new HashMap<>();

            while (rs.next()) {
                int orderId = rs.getInt("order_id");

                OrderWithDetails order = orderMap.get(orderId);
                if (order == null) {
                    order = new OrderWithDetails();
                    order.setOrderId(orderId);
                    order.setAccountId(rs.getInt("account_id"));
                    order.setShopId(rs.getInt("shop_id"));
                    order.setTotalAmount(rs.getDouble("total_amount"));
                    order.setPaymentMethod(rs.getString("payment_method"));
                    order.setStatus(rs.getString("status"));
                    order.setNote(rs.getString("note"));
                    order.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    order.setAccountName(rs.getString("account_name"));
                    order.setAccountEmail(rs.getString("account_email"));
                    order.setOrderDetails(new ArrayList<>());

                    orderMap.put(orderId, order);
                }

                OrderDetailWithProduct detail = new OrderDetailWithProduct();
                detail.setOrderDetailId(rs.getInt("order_detail_id"));
                detail.setProductId(rs.getInt("product_id"));
                detail.setQuantity(rs.getInt("quantity"));
                detail.setPrice(rs.getDouble("price"));

                // Thêm thông tin sản phẩm vào chi tiết đơn hàng
                ProductModel product = new ProductModel();
                product.setId(rs.getInt("product_id"));
                product.setName(rs.getString("product_name"));
                product.setDescription(rs.getString("product_description"));
                product.setPrice(rs.getDouble("product_price"));

                // Lấy hình ảnh sản phẩm (giả sử chỉ lấy 1 ảnh nếu có)
                String productImage = rs.getString("product_image");
                if (productImage != null) {
                    product.setImages(List.of(productImage));  // Nếu có ảnh, gán ảnh cho sản phẩm
                }

                // Thêm sản phẩm vào chi tiết đơn hàng
                detail.setProduct(product);

                // Thêm chi tiết đơn hàng vào đơn hàng
                order.getOrderDetails().add(detail);
            }

            // Thêm tất cả các đơn hàng vào danh sách trả về
            orders.addAll(orderMap.values());
        }

        return orders;
    }

    @Override
    public boolean cancelOrder(int orderId) throws Exception {
        String sql = "UPDATE orders SET status = 'CANCLE' WHERE id = ?";
        try (Connection conn = dbConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

	
		
    @Override
    public void updateAddress(int orderId, int addressId) throws Exception {
        String sql = "UPDATE orders SET address_id = ? WHERE id = ?";
        System.out.println("SQL: " + sql + ", address_id: " + addressId + ", orderId: " + orderId);
        try (Connection conn = dbConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, addressId);
            stmt.setInt(2, orderId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi nếu có
        }
    }

    
   

   	
}

