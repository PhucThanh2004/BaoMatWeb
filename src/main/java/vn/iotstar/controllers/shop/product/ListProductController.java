package vn.iotstar.controllers.shop.product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.models.AccountModel;
import vn.iotstar.models.ProductModel;
import vn.iotstar.models.ShopModel;
import vn.iotstar.service.IProductService;
import vn.iotstar.service.IShopService;
import vn.iotstar.service.impl.ProductServiceImpl;
import vn.iotstar.service.impl.ShopServiceImpl;
import vn.iotstar.utils.Constant;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/shop/product/list-product"})
public class ListProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private IProductService productService;
    private IShopService shopService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        productService = new ProductServiceImpl();
        shopService = new ShopServiceImpl();
        
        // Lấy tài khoản từ session
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        AccountModel currentUser = (AccountModel) session.getAttribute("account");
        
        // Kiểm tra vai trò người bán
        if (!currentUser.getIsSeller()) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền truy cập trang này!");
            return;
        }

        // Lấy tham số id và kiểm tra quyền sở hữu
        int accountId = Integer.parseInt(req.getParameter("id"));
        if (currentUser.getId() != accountId) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền truy cập dữ liệu của cửa hàng này!");
            return;
        }
        
        int page = Integer.parseInt(req.getParameter("page") != null ? req.getParameter("page") : "1");
        int pageSize = 10;
                
        System.out.print(accountId);
       
        ShopModel shop = null;
        
		try {
			shop = shopService.findByAccountId(accountId);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
        
        
        int shopId = 0;
        
        if(shop == null)
        {
        	shopId = Integer.parseInt(req.getParameter("id"));
        }
        else
        {
        	shopId = shop.getId(); //co the lay theo account login
        }
        
        System.out.print(shopId);

        try {
            List<ProductModel> products = productService.getProductsByShop(shopId, page, pageSize);

            int totalRecords  = productService.getProductCountByShop(shopId);
            int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
            int begin = Math.max(page - 2, 1);
            int end = Math.min(page + 2, totalPages);

            req.setAttribute("shop", shopId);
            req.setAttribute("products", products);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("begin", begin);
            req.setAttribute("end", end);
            req.getRequestDispatcher(Constant.SHOP_LIST_PRODUCT).forward(req, resp);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}



