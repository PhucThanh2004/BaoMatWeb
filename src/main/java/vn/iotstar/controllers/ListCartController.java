package vn.iotstar.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.models.AccountModel;
import vn.iotstar.models.CartDetailWithProduct;
import vn.iotstar.models.CartModel;
import vn.iotstar.service.ICartService;
import vn.iotstar.service.impl.CartServiceImpl;
import vn.iotstar.utils.Constant;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/cart/view", "/cart/delete"})
public class ListCartController extends HttpServlet {
    private ICartService cartService;

    @Override
    public void init() throws ServletException {
        cartService = new CartServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String url = req.getRequestURI();

        if (url.contains("/cart/view")) {
            try {
                HttpSession session = req.getSession();
                AccountModel account = (AccountModel) session.getAttribute("account");
                if (account == null) {
                    // Xử lý trường hợp người dùng chưa đăng nhập, chuyển hướng hoặc hiển thị lỗi
                    resp.sendRedirect(req.getContextPath() + "/login"); // Ví dụ chuyển hướng về trang đăng nhập
                    return;
                }
                int accountId = account.getId();
                CartModel cart = cartService.findCartByAccountId(accountId);
                if (cart == null) {
                    // Nếu không tìm thấy giỏ hàng, có thể tạo mới hoặc hiển thị trang giỏ hàng trống
                    // Hiện tại bạn đang gửi lỗi 404, có thể giữ nguyên hoặc thay đổi tùy luồng nghiệp vụ
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Not found cart");
                    return;
                }
                List<CartDetailWithProduct> carts = cartService.getCartDetailsWithProducts(cart.getId());
                double totalAmount = 0;

                for (CartDetailWithProduct cartDetail : carts) {
                    double productPrice = cartDetail.getProductPrice();
                    int quantity = cartDetail.getQuantity();
                    totalAmount += productPrice * quantity;
                }

                req.setAttribute("carts", carts);
                req.setAttribute("totalAmount", totalAmount);
                req.getRequestDispatcher(Constant.USER_CART_LIST).forward(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error displaying cart.");
            }
        } else if (url.contains("/cart/delete")) { // Sử dụng else if để đảm bảo chỉ một nhánh được thực thi
        	
            String cartDetailIdStr = req.getParameter("cartDetailId");
            if (cartDetailIdStr == null || cartDetailIdStr.isEmpty()) {
                // Xử lý trường hợp tham số không tồn tại hoặc rỗng
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing cartDetailId parameter.");
                return;
            }

            try {
                // 1. Ép kiểu: Chuyển đổi chuỗi thành số nguyên
                // Nếu chuỗi không phải là số nguyên, nó sẽ ném ra ngoại lệ NumberFormatException.
                int cartDetailId = Integer.parseInt(cartDetailIdStr);

                // 2. Thực hiện xóa chi tiết giỏ hàng
                cartService.deleteCartDetail(cartDetailId);
                
                // Chuyển hướng về trang xem giỏ hàng sau khi xóa thành công
                resp.sendRedirect(req.getContextPath() + "/cart/view");

            } catch (NumberFormatException e) {
                // Xử lý lỗi khi cartDetailId không phải là số hợp lệ
                System.err.println("Invalid cartDetailId format: " + cartDetailIdStr);
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid cart item ID format.");

                // resp.sendRedirect(req.getContextPath() + "/error?message=Invalid ID");
            } catch (Exception e) {
                // Bắt các ngoại lệ khác (ví dụ: lỗi từ service/DAO)
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting cart item.");
            }
        }
    }
}