package vn.iotstar.configs;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class CSPFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No initialization required
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (response instanceof HttpServletResponse) {
            HttpServletResponse httpResp = (HttpServletResponse) response;
            httpResp.setHeader("Content-Security-Policy",
                "default-src 'self'; " +
                "style-src 'self' https://fonts.googleapis.com https://cdnjs.cloudflare.com https://maxcdn.bootstrapcdn.com; " +
                "script-src 'self' https://code.jquery.com https://cdn.jsdelivr.net https://cdnjs.cloudflare.com https://maxcdn.bootstrapcdn.com; " +
                "img-src 'self' data: blob: https://maxcdn.bootstrapcdn.com https://cdn.jsdelivr.net https://cdnjs.cloudflare.com " +
                "https://themewagon.github.io https://source.unsplash.com; " +
                "font-src 'self' https://fonts.gstatic.com https://cdnjs.cloudflare.com https://maxcdn.bootstrapcdn.com; " +
                "connect-src 'self'; " +
                "frame-src 'self'; " +
                "object-src 'none'; " +
                "form-action 'self'; " +
                "base-uri 'self';" +
                "frame-ancestors 'self'; " 
            );
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // No cleanup required
    }
}
