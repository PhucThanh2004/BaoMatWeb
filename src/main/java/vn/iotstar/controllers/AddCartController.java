package vn.iotstar.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.models.AccountModel;
import vn.iotstar.service.ICartService;
import vn.iotstar.service.impl.CartServiceImpl;

import java.io.IOException;

@WebServlet(urlPatterns = { "/cart/add" })
public class AddCartController extends HttpServlet {
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
			int productId = Integer.parseInt(req.getParameter("productId"));
			int quantity = Integer.parseInt(req.getParameter("quantity"));

			cartService.addToCart(accountId, productId, quantity);
			resp.sendRedirect(req.getContextPath() + "/cart/view");
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error adding to cart.");
		}
	}
}
