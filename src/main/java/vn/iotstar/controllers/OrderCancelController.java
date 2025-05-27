package vn.iotstar.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.service.IOrderService;
import vn.iotstar.service.impl.OrderServiceImpl;

import java.io.IOException;

@WebServlet("/orders/cancel")
public class OrderCancelController extends HttpServlet {
    private IOrderService orderService;

    @Override
    public void init() {
        orderService = new OrderServiceImpl();  // Khởi tạo service
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        try {
            // Gọi phương thức trong service để huỷ đơn hàng
            boolean success = orderService.cancelOrder(orderId);
            if (success) {
                request.getSession().setAttribute("message", "Đơn hàng đã được huỷ thành công.");
            } else {
                request.getSession().setAttribute("error", "Không thể huỷ đơn hàng.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Lỗi khi huỷ đơn hàng.");
        }
        response.sendRedirect(request.getContextPath() + "/orders");
    }
}
