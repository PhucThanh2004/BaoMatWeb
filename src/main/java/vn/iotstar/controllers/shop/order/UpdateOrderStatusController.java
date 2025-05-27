package vn.iotstar.controllers.shop.order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.service.IOrderService;
import vn.iotstar.service.impl.OrderServiceImpl;

import java.io.IOException;

@WebServlet(urlPatterns = {"/shop/orders/update-status"})
public class UpdateOrderStatusController extends HttpServlet {
    private IOrderService orderService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        orderService = new OrderServiceImpl();
        int shopId = Integer.parseInt(request.getParameter("shopId"));
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String status = request.getParameter("status");

        try {
            orderService.updateOrderStatus(orderId, status);
            request.getSession().setAttribute("message", "Cập nhật trạng thái đơn hàng thành công!");

            response.sendRedirect(request.getContextPath() + "/shop/orders?shopId="+shopId);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Cập nhật trạng thái không thành công.");
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }
}
