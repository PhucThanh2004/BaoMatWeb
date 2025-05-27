package vn.iotstar.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.models.AccountModel;
import vn.iotstar.service.IAccountService;
import vn.iotstar.service.impl.AccountServiceImpl;

@WebServlet(urlPatterns = { "/resetPassword" })
public class ResetPasswordController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    IAccountService userService = new AccountServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            HttpSession session = req.getSession();
            AccountModel user = (AccountModel) session.getAttribute("resetAccount");

            if (user != null) {
                String newPassword = req.getParameter("newPassword");
                String confirmPassword = req.getParameter("confirmPassword");

                // Validate the new password and confirm password
                if (newPassword != null && newPassword.equals(confirmPassword)) {
                    // Update the password in the database
                    boolean isUpdated = userService.updatePassword(user.getEmail(), user.getCode(), newPassword);

                    if (isUpdated) {
                        out.println("<div class=\"container\"><br/>" +
                                "        <br/>" +
                                "        <br/>Mật khẩu đã được đặt lại thành công!<br/>" +
                                "        <br/>" +
                                "        <br/></div>");
                        session.removeAttribute("resetAccount");
                    } else {
                        out.println("<div class=\"container\"><br/>" +
                                "        <br/>" +
                                "        <br/>Đã xảy ra lỗi, vui lòng thử lại sau!<br/>" +
                                "        <br/>" +
                                "        <br/></div>");
                    }
                } else {
                    out.println("<div class=\"container\"><br/>" +
                            "        <br/>" +
                            "        <br/>Mật khẩu và xác nhận mật khẩu không khớp!<br/>" +
                            "        <br/>" +
                            "        <br/></div>");
                }
            } else {
                out.println("<div class=\"container\"><br/>" +
                        "        <br/>" +
                        "        <br/>Không tìm thấy thông tin tài khoản để đặt lại mật khẩu!<br/>" +
                        "        <br/>" +
                        "        <br/></div>");
            }
        } catch (Exception e) {
			e.printStackTrace();
		}
    }
}
