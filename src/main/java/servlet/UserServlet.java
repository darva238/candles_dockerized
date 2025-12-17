package servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("userId") == null) {
            String redirect = servlet.utils.URLutils.getFullUrlForRedirect(req, "/login");
            resp.sendRedirect(redirect);
            return;
        }
        List<String> actions = List.of("создать позицию", "редактировать позицию", "удалить позицию");
        req.setAttribute("actions", actions);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/users/user.jsp");
        requestDispatcher.forward(req, resp);
    }
}
