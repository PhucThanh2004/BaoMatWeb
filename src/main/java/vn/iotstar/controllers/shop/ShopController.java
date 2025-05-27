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

@WebServlet(urlPatterns = { "/shop/home", "/shop/profile", "/shop/edit", "/shop/updateInfo" })
public class ShopController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IShopService shopService = new ShopServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// req.getRequestDispatcher(Constant.SHOP_LIST_PRODUCT).forward(req, resp);
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String url = req.getRequestURI();

		if (url.contains("/shop/profile")) {
			int shopId = Integer.parseInt(req.getParameter("shopId"));
			ShopModel shop = null;
			try {
				shop = shopService.findByShopId(shopId);
			} catch (Exception e) {

				e.printStackTrace();
			}

			req.setAttribute("shop", shop);
			req.getRequestDispatcher("/views/shop/profile.jsp").forward(req, resp);
		} else if (url.contains("/shop/edit")) {
			int shopId = Integer.parseInt(req.getParameter("shopId"));
			ShopModel shop = null;
			try {
				shop = shopService.findByShopId(shopId);
			} catch (Exception e) {
				e.printStackTrace();
			}

			req.setAttribute("shop", shop);
			req.getRequestDispatcher("/views/shop/profile_edit.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String url = req.getRequestURI();

		if (url.contains("/shop/updateInfo")) {
			try {
				int shopId = Integer.parseInt(req.getParameter("shopId"));
				String name = req.getParameter("name");
				String address = req.getParameter("address");
				String description = req.getParameter("description");

				ShopModel shop = new ShopModel();
				shop.setId(shopId);
				shop.setName(name);
				shop.setAddress(address);
				shop.setDescription(description);

				shopService.updateShopInfo(shop);
				resp.sendRedirect(req.getContextPath() + "/shop/profile?shopId=" + shopId);
			} catch (Exception e) {
				e.printStackTrace();
				resp.sendRedirect(req.getContextPath() + "/error-page");
			}
		}
	}
}