package servlet;

import jakarta.servlet.http.HttpServletRequest;

public class URLutils {
    public static String getFullUrlForRedirect(HttpServletRequest request, String baseUrl) {
        return request.getContextPath() + baseUrl;
    }
}
