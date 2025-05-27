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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
			        String cartDetailIdParam = paramName.substring(9);
			        String quantityParam = req.getParameter(paramName);

			        try {
			            int cartDetailId = Integer.parseInt(cartDetailIdParam);
			            int quantity = Integer.parseInt(quantityParam);

			            if (cartDetailId <= 0) {
			                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid cartDetailId.");
			                return;
			            }
			            if (quantity <= 0) {
			                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Quantity must be greater than zero.");
			                return;
			            }
			            if (!cartService.isCartDetailValid(cart.getId(), cartDetailId)) {
			                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid cart detail ID.");
			                return;
			            }

			            updatedQuantities.put(cartDetailId, quantity);
			        } catch (NumberFormatException e) {
			            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input format for cartDetailId or quantity.");
			            return;
			        }
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
