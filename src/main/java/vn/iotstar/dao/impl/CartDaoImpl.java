package vn.iotstar.dao.impl;

import vn.iotstar.configs.DBConnectSQL;
import vn.iotstar.dao.ICartDao;
import vn.iotstar.models.CartDetailWithProduct;
import vn.iotstar.models.CartModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDaoImpl implements ICartDao {
    private DBConnectSQL dbConnectSQL;

    public CartDaoImpl() {
        dbConnectSQL = new DBConnectSQL();
    }

    @Override
    public CartModel findCartByAccountId(int accountId) throws Exception {
        String sql = "SELECT * FROM carts WHERE account_id = ?";
        try (Connection conn = dbConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                CartModel cart = new CartModel();
                cart.setId(rs.getInt("id"));
                cart.setAccountId(rs.getInt("account_id"));
                return cart;
            }
        }
        return null; // Giỏ hàng chưa tồn tại
    }

    @Override
    public int createCart(int accountId) throws Exception {
        String sql = "INSERT INTO carts (account_id) VALUES (?)";
        try (Connection conn = dbConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, accountId);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1; // Thất bại
    }

    @Override
    public void addProductToCart(int cartId, int productId, int quantity) throws Exception {
        String sql = "INSERT INTO cart_details (cart_id, product_id, quantity) VALUES (?, ?, ?)";
        try (Connection conn = dbConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);
            stmt.executeUpdate();
        }
    }

    @Override
    public void updateCartDetail(int cartId, int productId, int quantity) throws Exception {
        String sql = "UPDATE cart_details SET quantity = quantity + ? WHERE cart_id = ? AND product_id = ?";
        try (Connection conn = dbConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, cartId);
            stmt.setInt(3, productId);
            stmt.executeUpdate();
        }
    }

    @Override
    public boolean isProductInCart(int cartId, int productId) throws Exception {
        String sql = "SELECT COUNT(*) FROM cart_details WHERE cart_id = ? AND product_id = ?";
        try (Connection conn = dbConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            stmt.setInt(2, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    @Override
    public List<CartDetailWithProduct> getCartDetailsWithProducts(int cartId) throws Exception {
        List<CartDetailWithProduct> cartDetailsWithProducts = new ArrayList<>();

        String sql = "SELECT cd.id AS cart_detail_id, cd.product_id, cd.quantity, " +
                "p.name AS product_name, p.price AS product_price, p.description AS product_description, " +
                "p.shop_id AS shop_id," +
                "(SELECT TOP 1 pi.product_image FROM product_images pi WHERE pi.product_id = p.id ORDER BY pi.id ASC) AS product_image " +
                "FROM cart_details cd " +
                "JOIN products p ON cd.product_id = p.id " +
                "WHERE cd.cart_id = ? " +
                "ORDER BY cd.id";

        try (Connection conn = dbConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cartId); // Set the cartId

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CartDetailWithProduct detail = new CartDetailWithProduct();
                    detail.setCartDetailId(rs.getInt("cart_detail_id"));
                    detail.setProductId(rs.getInt("product_id"));
                    detail.setQuantity(rs.getInt("quantity"));
                    detail.setProductName(rs.getString("product_name"));
                    detail.setProductPrice(rs.getDouble("product_price"));
                    detail.setProductDescription(rs.getString("product_description"));
                    detail.setShopId(rs.getInt("shop_id"));
                    detail.setProductImage(rs.getString("product_image"));

                    cartDetailsWithProducts.add(detail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cartDetailsWithProducts;
    }

    @Override
    public void updateCartDetail(int cartDetailId, int quantity) throws Exception {
        if (quantity == 0) {
            deleteCartDetail(cartDetailId);
        } else {
            String sql = "UPDATE cart_details SET quantity = ? WHERE id = ?";

            try (Connection conn = dbConnectSQL.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, quantity);
                stmt.setInt(2, cartDetailId);
                stmt.executeUpdate();
            }
        }

    }

    @Override
    public void deleteCartDetail(int cartDetailId) throws SQLException {
        String sql = "DELETE FROM cart_details WHERE id = ?";

        try (Connection conn = dbConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartDetailId);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
