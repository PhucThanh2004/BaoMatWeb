package vn.iotstar.controllers.shop.product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.models.ProductModel;
import vn.iotstar.models.ShopModel;
import vn.iotstar.service.IProductService;
import vn.iotstar.service.IShopService;
import vn.iotstar.service.impl.ProductServiceImpl;
import vn.iotstar.service.impl.ShopServiceImpl;
import vn.iotstar.utils.Constant;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = {"/shop/product/list-product"})
public class ListProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private IProductService productService;
    private IShopService shopService;

    // Compile the regex pattern once for efficiency for numbers up to 8 digits
    private static final Pattern ID_PATTERN = Pattern.compile("^\\d{1,8}$");
    private static final Pattern NUMBER_PATTERN = Pattern.compile("^\\d+$"); // For general numbers like page

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        productService = new ProductServiceImpl();
        shopService = new ShopServiceImpl();

        int page = 1;
        String pageStr = req.getParameter("page");
        if (pageStr != null && !pageStr.isEmpty()) {
            if (!NUMBER_PATTERN.matcher(pageStr).matches()) {
                System.err.println("Invalid page format (non-numeric characters detected): " + pageStr);
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Định dạng số trang không hợp lệ.");
                return;
            }
            try {
                page = Integer.parseInt(pageStr);
                if (page < 1) {
                    page = 1;
                }
            } catch (NumberFormatException e) {
                // This block is mostly for defensive programming, as the regex check should prevent it.
                System.err.println("Invalid page format (parsing error): " + pageStr);
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Định dạng số trang không hợp lệ.");
                return;
            }
        }
        int pageSize = 10;

        String idParam = req.getParameter("id");
        int accountId;
        int shopId;

        if (idParam == null || idParam.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu tham số 'id'.");
            return;
        }

        if (!ID_PATTERN.matcher(idParam).matches()) {
            System.err.println("Invalid 'id' parameter format (non-numeric characters or too long detected): " + idParam);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Định dạng ID không hợp lệ.");
            return;
        }

        try {
            accountId = Integer.parseInt(idParam);
            System.out.println("Account ID: " + accountId);

            ShopModel shop = null;
            try {
                shop = shopService.findByAccountId(accountId);
            } catch (Exception e) {
                e.printStackTrace();
                // Log the error but don't stop the process yet, as shopId might be accountId
            }

            if (shop == null) {
                shopId = accountId; // If no shop linked to account, assume accountId is shopId
                // Potentially, if shopId is 0 or negative after this, it's an invalid ID
                if (shopId <= 0) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Shop không tồn tại hoặc không hợp lệ.");
                    return;
                }
            } else {
                shopId = shop.getId(); // Get shopId from the found ShopModel
            }

            System.out.println("Shop ID: " + shopId);

            List<ProductModel> products = productService.getProductsByShop(shopId, page, pageSize);
            int totalRecords = productService.getProductCountByShop(shopId);
            int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

            // Ensure current page is within valid range
            page = Math.min(page, totalPages > 0 ? totalPages : 1);
            
            int begin = Math.max(page - 2, 1);
            int end = Math.min(page + 2, totalPages);

            req.setAttribute("shop", shopId);
            req.setAttribute("products", products);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("begin", begin);
            req.setAttribute("end", end);
            
            if (products == null || products.isEmpty()) {
                req.setAttribute("message", "Không có sản phẩm nào.");
            }

            req.getRequestDispatcher(Constant.SHOP_LIST_PRODUCT).forward(req, resp);

        } catch (NumberFormatException e) {
            System.err.println("Invalid 'id' parameter format (parsing error): " + idParam);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Định dạng ID không hợp lệ.");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Đã xảy ra lỗi không mong muốn khi tải danh sách sản phẩm.");
        }
    }
}