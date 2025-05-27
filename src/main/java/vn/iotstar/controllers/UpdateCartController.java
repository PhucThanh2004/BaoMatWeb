package vn.iotstar.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.models.AccountModel;
import vn.iotstar.models.CartModel;
import vn.iotstar.service.ICartService;
import vn.iotstar.service.impl.CartServiceImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = { "/cart/update" })
public class UpdateCartController extends HttpServlet {
	private ICartService cartService;

	@Override
	public void init() throws ServletException {
		cartService = new CartServiceImpl();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			HttpSession session = req.getSession();
			AccountModel account = (AccountModel) session.getAttribute("account");
			int accountId = account.getId();
			CartModel cart = cartService.findCartByAccountId(accountId);

			Map<Integer, Integer> updatedQuantities = new HashMap<>();
			for (String paramName : req.getParameterMap().keySet()) {
				if (paramName.startsWith("quantity_")) {
					int cartDetailId = Integer.parseInt(paramName.substring(9)); // Lấy cartDetailId từ tên parameter
					int quantity = Integer.parseInt(req.getParameter(paramName));
					updatedQuantities.put(cartDetailId, quantity);
				}
			}

			cartService.updateCart(cart.getId(), updatedQuantities);

			resp.sendRedirect(req.getContextPath() + "/cart/view");
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error adding to cart.");
		}
	}
}
