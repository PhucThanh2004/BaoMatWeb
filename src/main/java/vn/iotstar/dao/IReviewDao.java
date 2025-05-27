package vn.iotstar.dao;

import java.util.List;

import vn.iotstar.models.ReviewModel;

public interface IReviewDao {
    List<ReviewModel> getReviewsWithAuthorByProductId(int productId) throws Exception ;
    void addReview(ReviewModel review) throws Exception;
    
    int countReview(int productId) throws Exception;

}
