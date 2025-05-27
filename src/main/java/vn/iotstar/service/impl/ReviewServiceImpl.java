package vn.iotstar.service.impl;

import java.util.List;

import vn.iotstar.dao.IReviewDao;
import vn.iotstar.dao.impl.ReviewDaoImpl;
import vn.iotstar.models.ReviewModel;
import vn.iotstar.service.IReviewService;

public class ReviewServiceImpl implements IReviewService{

	IReviewDao revDao = new ReviewDaoImpl();

	@Override
	public List<ReviewModel> getReviewsWithAuthorByProductId(int productId) throws Exception {
		return revDao.getReviewsWithAuthorByProductId(productId);
	}

	@Override
	public void addReview(ReviewModel review) throws Exception {
		revDao.addReview(review);
	}

	@Override
	public int countReview(int productId) throws Exception {
		return revDao.countReview(productId);
	}
	

}
