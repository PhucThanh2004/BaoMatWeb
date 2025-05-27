package vn.iotstar.configs;

import java.io.IOException;
import java.util.Map;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class InputValidationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        Map<String, String[]> params = request.getParameterMap();

        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            for (String value : entry.getValue()) {
                if (!isValidKeyword(value)) {
                    ((HttpServletResponse) response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input");
                    return;
                }
            }
        }

        chain.doFilter(request, response);
    }

    private boolean isValidKeyword(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            // Bỏ qua kiểm tra, cho phép null hoặc empty
            return true;
        }

        String trimmed = keyword.trim();
        String upperKeyword = trimmed.toUpperCase();

        // Chặn comment SQL kiểu -- ở cuối hoặc giữa chuỗi (case-insensitive)
        if (upperKeyword.contains("--")) {
            return false;
        }

        // Kiểm tra ký tự đặc biệt khác
        if (trimmed.matches(".*['\";/*\\\\%#=()\\[\\]].*")) {
            return false;
        }

        String[] dangerousPatterns = {
            "DROP TABLE", "ALTER TABLE", "INSERT INTO", "DELETE FROM", "UPDATE ",
            "EXEC ", "EXECUTE ", "UNION SELECT", "SELECT ", "WHERE ", " OR ", " AND ",
            "1=1", "1=0", "/*", "*/", "CAST(", "CONVERT(", "TRUNCATE", "DECLARE", "MERGE"
        };

        for (String pattern : dangerousPatterns) {
            if (upperKeyword.contains(pattern)) {
                return false;
            }
        }

        if (upperKeyword.matches(".*\\b(AND|OR)\\b\\s+\\d+=\\d+.*")) {
            return false;
        }

        return true;
    }
}