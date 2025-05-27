package vn.iotstar.controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.models.ReviewModel;
import vn.iotstar.service.impl.ReviewServiceImpl;
import vn.iotstar.utils.Constant;

@WebServlet("/review")
public class ReviewController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ReviewServiceImpl reviewService;

    @Override
    public void init() throws ServletException {
        reviewService = new ReviewServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String content = req.getParameter("content");
            String productIdStr = req.getParameter("productId");
            String accountIdStr = req.getParameter("accountId");

            // Kiểm tra nếu các tham số đầu vào hợp lệ
            if (content == null || content.trim().isEmpty() || productIdStr == null || accountIdStr == null) {
                req.setAttribute("errorMessage", "Thông tin không hợp lệ.");
                req.getRequestDispatcher(Constant.USER_PRODUCT_DETAIL).forward(req, resp);
                return;
            }

            int productId = Integer.parseInt(productIdStr);
            int accountId = Integer.parseInt(accountIdStr);

            // Tạo đối tượng ReviewModel và thiết lập thông tin
            ReviewModel review = new ReviewModel();
            review.setContent(content);
            review.setProduct_id(productId);
            review.setAccount_id(accountId);

            // Gọi service để thêm đánh giá
            reviewService.addReview(review);

            // Chuyển hướng về trang chi tiết sản phẩm sau khi thêm đánh giá thành công
            resp.sendRedirect(req.getContextPath() + "/product/detail?id=" + productId);
        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "Dữ liệu không hợp lệ. Vui lòng thử lại.");
            req.getRequestDispatcher(Constant.USER_PRODUCT_DETAIL).forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Có lỗi xảy ra khi thêm đánh giá. Vui lòng thử lại sau.");
            req.getRequestDispatcher(Constant.USER_PRODUCT_DETAIL).forward(req, resp);
        }
    }
}
