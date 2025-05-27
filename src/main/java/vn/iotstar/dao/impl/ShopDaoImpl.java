package vn.iotstar.dao.impl;

import vn.iotstar.configs.DBConnectSQL;
import vn.iotstar.dao.IShopDao;
import vn.iotstar.models.ProductModel;
import vn.iotstar.models.ShopModel;

import java.sql.*;

public class ShopDaoImpl implements IShopDao {
    private DBConnectSQL dbConnectSQL;

    public ShopDaoImpl() {
        dbConnectSQL = new DBConnectSQL();
    }

    @Override
    public void insert(ShopModel shop) throws Exception {
        String insertShopSql = "INSERT INTO shops (name, address, description, account_id, created_at) VALUES (?, ?, ?, ?, ?)";
        String updateAccountSql = "UPDATE accounts SET is_seller = 1 WHERE id = ?";

        Connection conn = dbConnectSQL.getConnection();

        try {
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(insertShopSql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, shop.getName());
                ps.setString(2, shop.getAddress());
                ps.setString(3, shop.getDescription());
                ps.setInt(4, shop.getAccountId());
                ps.setTimestamp(5, Timestamp.valueOf(shop.getCreatedAt()));
                ps.executeUpdate();

                try (PreparedStatement updatePs = conn.prepareStatement(updateAccountSql)) {
                    updatePs.setInt(1, shop.getAccountId());
                    updatePs.executeUpdate();
                }

                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                ex.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.setAutoCommit(true);
        }
    }

    @Override
    public ShopModel findByAccountId(int accountId) throws Exception {
        String sql = "SELECT * FROM shops WHERE account_id = ?";
        try (Connection connection = dbConnectSQL.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ShopModel shop = new ShopModel();
                shop.setId(rs.getInt("id"));
                shop.setName(rs.getString("name"));
                shop.setDescription(rs.getString("description"));
                shop.setAddress(rs.getString("address"));
                shop.setAccountId(rs.getInt("account_id"));
                return shop;
            }
            return null;
        }
    }

	@Override
	public ShopModel findByShopId(int shopId) throws Exception {
		 String sql = "SELECT * FROM shops WHERE id = ?";
	        try (Connection connection = dbConnectSQL.getConnection();
	             PreparedStatement stmt = connection.prepareStatement(sql)) {
	            stmt.setInt(1, shopId);
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	                ShopModel shop = new ShopModel();
	                shop.setId(rs.getInt("id"));
	                shop.setName(rs.getString("name"));
	                shop.setDescription(rs.getString("description"));
	                shop.setAddress(rs.getString("address"));
	                shop.setAccountId(rs.getInt("account_id"));
	                return shop;
	            }
	            return null;
	        }
	}
	@Override
	public void updateShopInfo(ShopModel shop) throws Exception {
		String sql = "UPDATE shops SET name = ?, address = ?, description = ? WHERE id = ?";
        try (Connection connection = dbConnectSQL.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, shop.getName());
            stmt.setString(2, shop.getAddress());
            stmt.setString(3, shop.getDescription());
            stmt.setInt(4, shop.getId());
            stmt.executeUpdate();
        }
       }
}
