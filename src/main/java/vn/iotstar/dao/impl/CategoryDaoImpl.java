package vn.iotstar.dao.impl;

import vn.iotstar.configs.DBConnectSQL;
import vn.iotstar.dao.ICategoryDao;
import vn.iotstar.models.CategoryModel;
import vn.iotstar.models.ProductModel;
import vn.iotstar.models.ShopModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl implements ICategoryDao {

    private DBConnectSQL dbConnectSQL;

    public CategoryDaoImpl() {
        dbConnectSQL = new DBConnectSQL();
    }

    @Override
    public List<CategoryModel> findAll() throws Exception {
        List<CategoryModel> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories";
        Connection connection = dbConnectSQL.getConnection();

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                CategoryModel category = new CategoryModel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("thumbnail")
                );
                categories.add(category);
            }
        }
        return categories;
    }

    @Override
    public CategoryModel findById(int id) throws Exception {
        String sql = "SELECT * FROM categories WHERE id = ?";
        try (Connection connection = dbConnectSQL.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                CategoryModel category = new CategoryModel();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                category.setThumbnail((rs.getString("thumbnail")));
                return category;
            }
            return null;
        }
    }
}
