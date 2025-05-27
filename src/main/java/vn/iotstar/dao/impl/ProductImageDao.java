package vn.iotstar.dao.impl;

import vn.iotstar.configs.DBConnectSQL;
import vn.iotstar.dao.IProductImageDao;
import vn.iotstar.models.ProductImageModel;
import vn.iotstar.models.ProductModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductImageDao implements IProductImageDao {
    private DBConnectSQL dbConnectSQL;

    public ProductImageDao() {
        dbConnectSQL = new DBConnectSQL();
    }

    @Override
    public void addProductImage(ProductImageModel productImageModel) throws Exception {
        String sql = "INSERT INTO product_images (product_id, product_image) VALUES (?, ?)";
        Connection conn = dbConnectSQL.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productImageModel.getProductId());
            ps.setString(2, productImageModel.getProductImage());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void loadProductImages(List<ProductModel> products) throws Exception {
        if (products.isEmpty()) return;

        String sql = "SELECT product_id, product_image FROM product_images WHERE product_id IN (%s)";

        List<Integer> productIds = products.stream()
                .map(ProductModel::getId)
                .toList();
        String inClause = productIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        sql = String.format(sql, inClause);

        Connection connection = dbConnectSQL.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            // Tạo map để lưu danh sách ảnh theo product_id
            Map<Integer, List<String>> productImagesMap = new HashMap<>();

            while (rs.next()) {
                int productId = rs.getInt("product_id");
                String productImage = rs.getString("product_image");

                // Gắn ảnh vào danh sách tương ứng với product_id
                productImagesMap
                        .computeIfAbsent(productId, k -> new ArrayList<>())
                        .add(productImage);
            }
            for (ProductModel product : products) {
                List<String> images = productImagesMap.get(product.getId());
                if (images != null) {
                    product.setImages(images);
                }
            }
        }
    }

}
