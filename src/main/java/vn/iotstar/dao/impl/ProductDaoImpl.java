package vn.iotstar.dao.impl;

import vn.iotstar.configs.DBConnectSQL;
import vn.iotstar.dao.IProductDao;
import vn.iotstar.dao.IProductImageDao;
import vn.iotstar.models.ProductModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl  implements IProductDao {

    private IProductImageDao productImageDao;
    private DBConnectSQL dbConnectSQL;

    public ProductDaoImpl() {
        dbConnectSQL = new DBConnectSQL();
    }

    @Override
    public List<ProductModel> getAllProductOfShop(int shopId, int page, int pageSize) throws Exception {
        List<ProductModel> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE shop_id = ? ORDER BY created_at DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        Connection connection = dbConnectSQL.getConnection();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            int offset = (page - 1) * pageSize;
            stmt.setInt(1, shopId);
            stmt.setInt(2, offset);
            stmt.setInt(3, pageSize);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ProductModel product = new ProductModel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock_quantity"),
                        rs.getInt("category_id"),
                        rs.getInt("shop_id")
                );
                products.add(product);
            }
            productImageDao = new ProductImageDao();
            productImageDao.loadProductImages(products);
        }
        return products;
    }

    public int getTotalProductOfShop(int shopId) {
        try {
            Connection connection = dbConnectSQL.getConnection();
            String sql = "SELECT COUNT(*) AS total FROM products WHERE shop_id = ?";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, shopId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
            return 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addProduct(ProductModel productModel) throws Exception {
        String sql = "INSERT INTO products (name, description, price, stock_quantity, category_id, shop_id, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = dbConnectSQL.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, productModel.getName());
            ps.setString(2, productModel.getDescription());
            ps.setDouble(3, productModel.getPrice());
            ps.setInt(4, productModel.getStockQuantity());
            ps.setInt(5, productModel.getCategoryId());
            ps.setInt(6, productModel.getShopId());
            ps.setTimestamp(7, Timestamp.valueOf(productModel.getCreatedAt()));
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        productModel.setId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean deleteProduct(int productId) throws Exception {
        String deleteImagesSQL = "DELETE FROM product_images WHERE product_id = ?";
        String deleteProductSQL = "DELETE FROM products WHERE id = ?";
        Connection connection = dbConnectSQL.getConnection();

        try {
            connection.setAutoCommit(false);

            // Xóa các ảnh liên quan
            try (PreparedStatement stmtImages = connection.prepareStatement(deleteImagesSQL)) {
                stmtImages.setInt(1, productId);
                stmtImages.executeUpdate();
            }

            // Xóa sản phẩm
            try (PreparedStatement stmtProduct = connection.prepareStatement(deleteProductSQL)) {
                stmtProduct.setInt(1, productId);
                stmtProduct.executeUpdate();
            }

            connection.commit();
            return true;
        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    @Override
    public ProductModel getProductById(int id) throws Exception {
        String sqlProduct = "SELECT * FROM products WHERE id = ?";
        String sqlImages = "SELECT product_image FROM product_images WHERE product_id = ?";

        try (Connection connection = dbConnectSQL.getConnection();
             PreparedStatement stmtProduct = connection.prepareStatement(sqlProduct);
             PreparedStatement stmtImages = connection.prepareStatement(sqlImages)) {

            stmtProduct.setInt(1, id);
            ResultSet rsProduct = stmtProduct.executeQuery();
            if (rsProduct.next()) {
                ProductModel product = new ProductModel();
                product.setId(rsProduct.getInt("id"));
                product.setName(rsProduct.getString("name"));
                product.setDescription(rsProduct.getString("description"));
                product.setPrice(rsProduct.getDouble("price"));
                product.setStockQuantity(rsProduct.getInt("stock_quantity"));
                product.setCategoryId(rsProduct.getInt("category_id"));
                product.setShopId(rsProduct.getInt("shop_id"));

                stmtImages.setInt(1, id);
                ResultSet rsImages = stmtImages.executeQuery();
                List<String> images = new ArrayList<>();
                while (rsImages.next()) {
                    images.add(rsImages.getString("product_image"));
                }
                product.setImages(images);

                return product;
            }
            return null; // Nếu không tìm thấy sản phẩm
        }
    }

    @Override
    public void updateProduct(ProductModel product) throws Exception {
        String sql = "UPDATE products SET name = ?, description = ?, price = ?, stock_quantity = ?, category_id = ? WHERE id = ?";
        try (Connection connection = dbConnectSQL.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getStockQuantity());
            stmt.setInt(5, product.getCategoryId());
            stmt.setInt(6, product.getId());

            stmt.executeUpdate();
        }
    }

    @Override
    public List<ProductModel> getProductsByCategory(int categoryId, int limit) throws Exception {
        List<ProductModel> products = new ArrayList<>();
        String sqlProducts = "SELECT TOP (?) * FROM products WHERE category_id = ? ORDER BY created_at DESC";
        String sqlImages = "SELECT product_id, product_image FROM product_images WHERE product_id = ?";

        Connection connection = dbConnectSQL.getConnection();

        try (PreparedStatement psProducts = connection.prepareStatement(sqlProducts)) {
            psProducts.setInt(1, limit);
            psProducts.setInt(2, categoryId);

            ResultSet rsProducts = psProducts.executeQuery();
            while (rsProducts.next()) {
                // Lấy thông tin sản phẩm
                ProductModel product = new ProductModel(
                        rsProducts.getInt("id"),
                        rsProducts.getString("name"),
                        rsProducts.getString("description"),
                        rsProducts.getDouble("price"),
                        rsProducts.getInt("stock_quantity"),
                        rsProducts.getInt("category_id"),
                        rsProducts.getInt("shop_id")
                );

                // Lấy danh sách ảnh cho sản phẩm này
                try (PreparedStatement psImages = connection.prepareStatement(sqlImages)) {
                    psImages.setInt(1, product.getId());
                    ResultSet rsImages = psImages.executeQuery();

                    List<String> images = new ArrayList<>();
                    while (rsImages.next()) {
                        images.add(rsImages.getString("product_image"));
                    }
                    product.setImages(images);
                }

                products.add(product);
            }
        }
        return products;
    }

    @Override
    public List<ProductModel> getProductsNew(int limit) throws Exception {
        List<ProductModel> products = new ArrayList<>();
        String sqlProducts = "SELECT TOP (?) * FROM products ORDER BY created_at DESC";
        String sqlImages = "SELECT product_id, product_image FROM product_images WHERE product_id = ?";

        Connection connection = dbConnectSQL.getConnection();

        try (PreparedStatement psProducts = connection.prepareStatement(sqlProducts)) {
            psProducts.setInt(1, limit);
            ResultSet rsProducts = psProducts.executeQuery();
            while (rsProducts.next()) {
                // Lấy thông tin sản phẩm
                ProductModel product = new ProductModel(
                        rsProducts.getInt("id"),
                        rsProducts.getString("name"),
                        rsProducts.getString("description"),
                        rsProducts.getDouble("price"),
                        rsProducts.getInt("stock_quantity"),
                        rsProducts.getInt("category_id"),
                        rsProducts.getInt("shop_id")
                );

                // Lấy danh sách ảnh cho sản phẩm này
                try (PreparedStatement psImages = connection.prepareStatement(sqlImages)) {
                    psImages.setInt(1, product.getId());
                    ResultSet rsImages = psImages.executeQuery();

                    List<String> images = new ArrayList<>();
                    while (rsImages.next()) {
                        images.add(rsImages.getString("product_image"));
                    }
                    product.setImages(images);
                }

                products.add(product);
            }
        }
        return products;
    }
    
    @Override
	public List<ProductModel> searchProductsByName(String name) throws Exception {
		List<ProductModel> products = new ArrayList<>();
		String sql = "SELECT * FROM products WHERE name LIKE ? ORDER BY created_at DESC";

		try (Connection connection = dbConnectSQL.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {
			// Sử dụng wildcard để tìm kiếm tên chứa từ khóa
			stmt.setString(1, "%" + name + "%");

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				ProductModel product = new ProductModel(rs.getInt("id"), rs.getString("name"),
						rs.getString("description"), rs.getDouble("price"), rs.getInt("stock_quantity"),
						rs.getInt("category_id"), rs.getInt("shop_id"));
				products.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Error while searching products by name", e);
		}
		return products;
	}


}
