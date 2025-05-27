package vn.iotstar.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.models.AccountModel;
import vn.iotstar.models.CartDetailWithProduct;
import vn.iotstar.models.CartModel;
import vn.iotstar.service.ICartService;
import vn.iotstar.service.impl.CartServiceImpl;
import vn.iotstar.utils.Constant;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/cart/view", "/cart/delete"})
public class ListCartController extends HttpServlet {
    private ICartService cartService;

    @Override
    public void init() throws ServletException {
        cartService = new CartServiceImpl();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String url = req.getRequestURI();
		if (url.contains("/cart/view")) {
    	try {
        	HttpSession session = req.getSession();
       	 AccountModel account = (AccountModel) session.getAttribute("account"); 
       	 int accountId = account.getId(); 
            CartModel cart = cartService.findCartByAccountId(accountId);
            if(cart == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Not found cart");
                return;
            }
            List<CartDetailWithProduct> carts = cartService.getCartDetailsWithProducts(cart.getId());
            double totalAmount = 0;

            for (CartDetailWithProduct cartDetail : carts) {
                double productPrice = cartDetail.getProductPrice();
                int quantity = cartDetail.getQuantity();
                totalAmount += productPrice * quantity;
            }

            req.setAttribute("carts", carts);
            req.setAttribute("totalAmount", totalAmount);
            req.getRequestDispatcher(Constant.USER_CART_LIST).forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error adding to cart.");
        }
		}if (url.contains("/cart/delete"))
		{
			int cartDetailId = Integer.parseInt(req.getParameter("cartDetailId"));
			try {
				cartService.deleteCartDetail(cartDetailId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resp.sendRedirect(req.getContextPath()+"/cart/view");
			
		}
		
        
        
    }
}
