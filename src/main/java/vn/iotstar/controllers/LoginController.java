package vn.iotstar.controllers;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.models.AccountModel;
import vn.iotstar.service.IAccountService;
import vn.iotstar.service.impl.AccountServiceImpl;
import vn.iotstar.utils.Constant;


@WebServlet(urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	IAccountService accservice = new AccountServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Tạo token CSRF và lưu vào session
        String csrfToken = generateCsrfToken();
        req.getSession().setAttribute("csrf_token", csrfToken);
		
		req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		
		// Kiểm tra token CSRF
        String csrfToken = req.getParameter("csrf_token");
        String sessionToken = (String) req.getSession().getAttribute("csrf_token");
        if (csrfToken == null || !csrfToken.equals(sessionToken)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF token không hợp lệ!");
            return;
        }

		// lay tham so tu view
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String remember = req.getParameter("remember");

		// kiem tra tham so
		boolean isRememberMe = false;

		if ("on".equals(remember)) {
			isRememberMe = true;
		}
		String alertMsg = "";
		if (email.isEmpty() || password.isEmpty()) {
			alertMsg = "Tài khoản hoặc mật khẩu không được rỗng";
			req.setAttribute("alert", alertMsg);
			req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
			return;
		}

		// xu ly bai toan
		AccountModel acc = accservice.login(email, password);
		if (acc != null) {
			HttpSession session = req.getSession(true);
			System.out.println("Email: " + acc.getEmail());

			session.setAttribute("account", acc);
			session.setAttribute("email", acc.getEmail());
			
			if (isRememberMe) {
				saveRemeberMe(resp, email);
			}
			resp.sendRedirect(req.getContextPath() + "/waiting");
		} else {
			alertMsg = "Tài khoản hoặc mật khẩu không đúng";
			req.setAttribute("alert", alertMsg);
			req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
		}
	}
		private void saveRemeberMe(HttpServletResponse resp, String email) {
			Cookie cookie = new Cookie(Constant.COOKIE_REMEMBER, email);
			cookie.setMaxAge(30 * 60);
			resp.addCookie(cookie);
		}
		
	// Phương thức tạo token CSRF ngẫu nhiên
    private String generateCsrfToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[16];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
