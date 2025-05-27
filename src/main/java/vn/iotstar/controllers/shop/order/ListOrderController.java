package vn.iotstar.controllers.shop.order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.models.OrderWithDetails;
import vn.iotstar.models.ProductModel;
import vn.iotstar.service.IOrderService;
import vn.iotstar.service.IProductService;
import vn.iotstar.service.impl.OrderServiceImpl;
import vn.iotstar.service.impl.ProductServiceImpl;
import vn.iotstar.utils.Constant;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/shop/orders"})
public class ListOrderController extends HttpServlet {
    private IOrderService orderService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        orderService = new OrderServiceImpl();
        int shopId;
        try {
            shopId = Integer.parseInt(request.getParameter("shopId"));
        } catch (NumberFormatException e) {
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


