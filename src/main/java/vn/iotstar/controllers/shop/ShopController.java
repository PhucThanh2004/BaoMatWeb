package vn.iotstar.controllers.shop;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.models.ShopModel;
import vn.iotstar.service.IShopService;
import vn.iotstar.service.impl.ShopServiceImpl;

import java.io.IOException;
import java.util.regex.Pattern; // Import this

@WebServlet(urlPatterns = { "/shop/home", "/shop/profile", "/shop/edit", "/shop/updateInfo" })
public class ShopController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IShopService shopService = new ShopServiceImpl();

    // Compile the regex pattern once for efficiency
    private static final Pattern NUMBER_PATTERN = Pattern.compile("^\\d+$");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String url = req.getRequestURI();

        if (url.contains("/shop/profile") || url.contains("/shop/edit")) {
            String shopIdParam = req.getParameter("shopId");
            int shopId;

            // Input validation for shopId
            if (shopIdParam == null || !NUMBER_PATTERN.matcher(shopIdParam).matches()) {
                System.err.println("Invalid 'shopId' parameter format (non-numeric characters detected): " + shopIdParam);
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Mã shop không hợp lệ.");
                return;
            }

            try {
                shopId = Integer.parseInt(shopIdParam);
            } catch (NumberFormatException e) {
                // This catch is mostly a fallback as regex should catch non-numeric,
                // but it's good for robustness.
                System.err.println("Invalid 'shopId' parameter format (parsing error): " + shopIdParam);
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Mã shop không hợp lệ.");
                return;
            }

            if (shopId <= 0) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Mã shop phải lớn hơn 0.");
                return;
            }

            ShopModel shop = null;
            try {
                shop = shopService.findByShopId(shopId);
            } catch (Exception e) {
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi truy xuất thông tin cửa hàng.");
                return;
            }

            if (shop == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy cửa hàng.");
                return;
            }

            req.setAttribute("shop", shop);
            if (url.contains("/shop/profile")) {
                req.getRequestDispatcher("/views/shop/profile.jsp").forward(req, resp);
            } else { // url.contains("/shop/edit")
                req.getRequestDispatcher("/views/shop/profile_edit.jsp").forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String url = req.getRequestURI();

        if (url.contains("/shop/updateInfo")) {
            String shopIdParam = req.getParameter("shopId");
            int shopId;

            // Input validation for shopId
            if (shopIdParam == null || !NUMBER_PATTERN.matcher(shopIdParam).matches()) {
                System.err.println("Invalid 'shopId' parameter format (non-numeric characters detected) for update: " + shopIdParam);
                resp.sendRedirect(req.getContextPath() + "/error-page?message=Mã shop không hợp lệ cho cập nhật.");
                return;
            }

            try {
                shopId = Integer.parseInt(shopIdParam);
            } catch (NumberFormatException e) {
                System.err.println("Invalid 'shopId' parameter format (parsing error) for update: " + shopIdParam);
                resp.sendRedirect(req.getContextPath() + "/error-page?message=Mã shop không hợp lệ cho cập nhật.");
                return;
            }

            if (shopId <= 0) {
                resp.sendRedirect(req.getContextPath() + "/error-page?message=Mã shop phải lớn hơn 0.");
                return;
            }

            // Other parameters for update (name, address, description)
            String name = req.getParameter("name");
            String address = req.getParameter("address");
            String description = req.getParameter("description");

            try {
                ShopModel shop = new ShopModel();
                shop.setId(shopId);
                // Assigning potentially null values for name, address, description.
                // It's assumed the service layer handles nulls or enforces NOT NULL constraints.
                shop.setName(name);
                shop.setAddress(address);
                shop.setDescription(description);

                shopService.updateShopInfo(shop);
                resp.sendRedirect(req.getContextPath() + "/shop/profile?shopId=" + shopId);
            } catch (Exception e) {
                e.printStackTrace();
                resp.sendRedirect(req.getContextPath() + "/error-page?message=Lỗi khi cập nhật thông tin cửa hàng.");
            }
        }
    }
}