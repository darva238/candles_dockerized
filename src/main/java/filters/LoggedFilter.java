package filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/user/*")
public class LoggedFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession(false);

        if (session == null || session.getAttribute("login") == null) {
            ((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath() + "/login");
            return;
        }
        chain.doFilter(request, response);
    }
}
