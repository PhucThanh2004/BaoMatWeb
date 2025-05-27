package vn.iotstar.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.models.AccountModel;
import vn.iotstar.models.OrderWithDetails;
import vn.iotstar.service.IOrderService;
import vn.iotstar.service.impl.OrderServiceImpl;
import vn.iotstar.utils.Constant;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/orders"})
public class OrderController extends HttpServlet {
    private IOrderService orderService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	 AccountModel account = (AccountModel) session.getAttribute("account"); 
    	 int accountId = account.getId(); 
    	//int accountId = 1; // thay account vào khi làm login xong
        orderService = new OrderServiceImpl();
        try {
                List<OrderWithDetails> orders = orderService.getOrdersByAccountId(accountId);
                request.setAttribute("orders", orders);
                request.getRequestDispatcher(Constant.USER_ORDER).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
