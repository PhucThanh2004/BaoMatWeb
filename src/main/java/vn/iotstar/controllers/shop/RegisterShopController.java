package vn.iotstar.controllers.shop;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.models.ShopModel;
import vn.iotstar.service.IShopService;
import vn.iotstar.service.impl.ShopServiceImpl;
import vn.iotstar.utils.Constant;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(urlPatterns = {"/register-shop"})
public class RegisterShopController extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IShopService shopService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(Constant.SHOP_REGISTER).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            shopService = new ShopServiceImpl();

            int accountId = Integer.parseInt(req.getParameter("accountId"));
            System.out.println(accountId);

            ShopModel shop = shopService.findByAccountId(accountId);
           

            if(shop != null){
                req.setAttribute("errorMessage", "Tài khoản của bạn đã đăng ký shop. Vui lòng vào truy cập vào phần cửa hàng của bạn.");
                req.getRequestDispatcher(Constant.SHOP_REGISTER).forward(req, resp);
                return;
            }

            String name = req.getParameter("name");
            String address = req.getParameter("address");
            String description = req.getParameter("description");

            ShopModel newShop = new ShopModel();
            newShop.setName(name);
            newShop.setAddress(address);
            newShop.setDescription(description);
            newShop.setAccountId(accountId);
            newShop.setCreatedAt(LocalDateTime.now());

            shopService.registerShop(newShop);

            resp.sendRedirect(req.getContextPath() + "/shop/product/list-product?id="+accountId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
