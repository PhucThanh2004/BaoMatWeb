package vn.iotstar.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.Base64;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.models.AccountModel;
import vn.iotstar.service.IAccountService;
import vn.iotstar.service.impl.AccountServiceImpl;

@WebServlet(urlPatterns = { "/forgotPassword", "/verifyResetCode" })
public class ForgotPwdController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    IAccountService userService = new AccountServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURL().toString();
        
        // Tạo token CSRF và lưu vào session
        String csrfToken = generateCsrfToken();
        req.getSession().setAttribute("csrf_token", csrfToken);
        
        if (url.contains("forgotPassword")) {
            req.getRequestDispatcher("/views/forgotpwd.jsp").forward(req, resp);
        } else if (url.contains("verifyResetCode")) {
            req.getRequestDispatcher("/views/verifyResetCode.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURL().toString();
        if (url.contains("forgotPassword")) {
            try {
				postForgotPassword(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
        } else if (url.contains("verifyResetCode")) {
            postVerifyResetCode(req, resp);
        }
    }

    private void postForgotPassword(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        resp.setContentType("text/html; charset=UTF-8");
        
        // Kiểm tra token CSRF
        String csrfToken = req.getParameter("csrf_token");
        String sessionToken = (String) req.getSession().getAttribute("csrf_token");
        if (csrfToken == null || !csrfToken.equals(sessionToken)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF token không hợp lệ!");
            return;
        }
        
        try (PrintWriter out = resp.getWriter()) {
            String email = req.getParameter("email");
            
            // Kiểm tra định dạng email
            if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                req.setAttribute("errorMessage", "Email không hợp lệ!");
                req.getRequestDispatcher("/views/forgotpwd.jsp").forward(req, resp);
                return;
            }
            
            AccountModel user = userService.findByUserName(email);

            if (user != null) {
                // Generate a random 6-digit verification code
                String code = String.format("%06d", (int) (Math.random() * 1000000));
                user.setCode(code);

                // Update the verification code in the database
                userService.updateCode(email, code);

                // Store user information in session for verification
                HttpSession session = req.getSession();
                session.setAttribute("resetAccount", user);

                // Redirect to the page to verify the reset code
                resp.sendRedirect(req.getContextPath() + "/verifyResetCode");
            } else {
            	// Mã hóa email trước khi hiển thị trong thông báo lỗi
                String safeEmail = htmlEscape(email);
                req.setAttribute("errorMessage", "Email " + safeEmail + " không tồn tại trong hệ thống!");
                req.getRequestDispatcher("/views/forgotpwd.jsp").forward(req, resp);
            }
        }
    }

    private void postVerifyResetCode(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("text/html; charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            HttpSession session = req.getSession();
            AccountModel user = (AccountModel) session.getAttribute("resetAccount");

            String code = req.getParameter("authcode");

            // Check the verification code from the session
            if (code != null && code.equals(user.getCode())) {
                // If the code is correct, display the form to reset the password
                req.getRequestDispatcher("/views/resetPassword.jsp").forward(req, resp);
            } else {
                out.println("<div class=\"container\"><br/>" +
                        "        <br/>" +
                        "        <br/>Sai mã xác nhận, vui lòng kiểm tra lại!<br/>" +
                        "        <br/>" +
                        "        <br/></div>");
            }
        }
    }
    
    // Phương thức tạo token CSRF ngẫu nhiên
    private String generateCsrfToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[16];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
    
    // Phương thức mã hóa HTML để ngăn chặn XSS
    private String htmlEscape(String input) {
        if (input == null) return null;
        return input.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#x27;");
    }
}
