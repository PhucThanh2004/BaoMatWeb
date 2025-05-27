package vn.iotstar.dao.impl;

import vn.iotstar.configs.DBConnectSQL;
import vn.iotstar.dao.IReviewDao;
import vn.iotstar.models.ReviewModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDaoImpl implements IReviewDao {

    @Override
    public List<ReviewModel> getReviewsWithAuthorByProductId(int productId) throws Exception {
        List<ReviewModel> reviews = new ArrayList<>();
        String query = "SELECT r.review_id, r.content, r.image, r.video, r.product_id, r.account_id, r.created_at, a.name AS author_name, p.name AS product_name " +
                       "FROM [ute-shop].[dbo].[reviews] r " +
                       "JOIN [ute-shop].[dbo].[accounts] a ON r.account_id = a.id " +
                       "JOIN [ute-shop].[dbo].[products] p ON r.product_id = p.id " +
                       "WHERE r.product_id = ?";

        // Khởi tạo đối tượng DBConnectSQL
        DBConnectSQL dBConnectSQL = new DBConnectSQL();
        try (Connection connection = dBConnectSQL.getConnection(); // Gọi phương thức từ đối tượng
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, productId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ReviewModel review = new ReviewModel();
                    review.setReview_id(resultSet.getInt("review_id"));
                    review.setContent(resultSet.getString("content"));
                    review.setImage(resultSet.getString("image"));
                    review.setVideo(resultSet.getString("video"));
                    review.setProduct_id(resultSet.getInt("product_id"));
                    review.setAccount_id(resultSet.getInt("account_id"));
                    review.setAuthorName(resultSet.getString("author_name"));
                    review.setProductName(resultSet.getString("product_name"));
                    Timestamp timestamp = resultSet.getTimestamp("created_at");
                    if (timestamp != null) {
                        review.setCreatedAt(timestamp.toLocalDateTime());
                    }

                    //review.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                    reviews.add(review);
                }
            }
        } catch (SQLException e) {
            // Ghi log chi tiết hoặc ném lại ngoại lệ để xử lý ở lớp gọi
            System.err.println("Error while fetching reviews: " + e.getMessage());
            e.printStackTrace(); // Log exception cho việc debug
            throw new Exception("Error while fetching reviews", e);
        }
        return reviews;
    }

    @Override
    public void addReview(ReviewModel review) throws Exception {
        String checkProductQuery = "SELECT COUNT(*) FROM [ute-shop].[dbo].[products] WHERE [id] = ?";
        String checkAccountQuery = "SELECT COUNT(*) FROM [ute-shop].[dbo].[accounts] WHERE [id] = ?";
        String insertQuery = "INSERT INTO [ute-shop].[dbo].[reviews] ([content], [product_id], [account_id], [created_at]) VALUES (?, ?, ?, GETDATE())";

        try (Connection connection = new DBConnectSQL().getConnection()) {
            // Kiểm tra tồn tại của product_id
            try (PreparedStatement checkProductStmt = connection.prepareStatement(checkProductQuery)) {
                checkProductStmt.setInt(1, review.getProduct_id());
                ResultSet rsProduct = checkProductStmt.executeQuery();
                if (rsProduct.next() && rsProduct.getInt(1) == 0) {
                    throw new Exception("Product ID does not exist in the products table.");
                }
            }

            // Kiểm tra tồn tại của account_id
            try (PreparedStatement checkAccountStmt = connection.prepareStatement(checkAccountQuery)) {
                checkAccountStmt.setInt(1, review.getAccount_id());
                ResultSet rsAccount = checkAccountStmt.executeQuery();
                if (rsAccount.next() && rsAccount.getInt(1) == 0) {
                    throw new Exception("Account ID does not exist in the accounts table.");
                }
            }

            // Thêm đánh giá vào bảng reviews
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setString(1, review.getContent());
                statement.setInt(2, review.getProduct_id());
                statement.setInt(3, review.getAccount_id());
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted == 0) {
                    throw new Exception("Failed to insert the review.");
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error while adding review", e);
        }
    }

	@Override
	public int countReview(int productId) throws Exception{
		 int count = 0;
		    String query = "SELECT COUNT(*) FROM reviews WHERE product_id = ?";

		    DBConnectSQL dBConnectSQL = new DBConnectSQL();
		    try (Connection connection = dBConnectSQL.getConnection(); 
		         PreparedStatement statement = connection.prepareStatement(query)) {

		        statement.setInt(1, productId);

		        try (ResultSet resultSet = statement.executeQuery()) {
		            if (resultSet.next()) {
		                count = resultSet.getInt(1); 
		            }
		        }
		    } catch (SQLException e) {
	
		        System.err.println("Error while counting reviews: " + e.getMessage());
		        e.printStackTrace(); 
		    }
		    return count;
	}

}
