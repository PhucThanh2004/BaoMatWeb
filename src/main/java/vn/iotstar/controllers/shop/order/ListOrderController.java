package vn.iotstar.controllers.shop.order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.models.OrderWithDetails;
import vn.iotstar.service.IOrderService;
import vn.iotstar.service.impl.OrderServiceImpl;
import vn.iotstar.utils.Constant;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern; // Import this

@WebServlet(urlPatterns = {"/shop/orders"})
public class ListOrderController extends HttpServlet {
    private IOrderService orderService;
    // Compile the regex pattern once for efficiency
    private static final Pattern NUMBER_PATTERN = Pattern.compile("^\\d+$"); 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        orderService = new OrderServiceImpl();
        String shopIdParam = request.getParameter("shopId");
        int shopId;

        // Add a check to ensure shopIdParam only contains digits before parsing
        if (shopIdParam == null || !NUMBER_PATTERN.matcher(shopIdParam).matches()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid shop ID format");
            return;
        }

        try {
            shopId = Integer.parseInt(shopIdParam);
        } catch (NumberFormatException e) {
            // This catch block might be redundant if the regex check is strict enough,
            // but keeping it adds an extra layer of safety.
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid shop ID");
            return;
        }

        try {
            List<OrderWithDetails> orders = orderService.getOrdersByShopId(shopId);
            request.setAttribute("orders", orders);
            request.getRequestDispatcher(Constant.SHOP_LIST_ORDER).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching orders");
        }
    }
}