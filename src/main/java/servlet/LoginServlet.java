package servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import entity.Profile;
import jakarta.servlet.http.HttpSession;
import repository.ProfileRepository;
import service.PasswordService;
import service.ProfileService;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final ProfileService profileService;

    public LoginServlet() {
        this.profileService = new ProfileService(new ProfileRepository(), new PasswordService());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/users/login.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String action = req.getParameter("action");

        try {
            Long userId = profileService.login(login, password);
            if (userId != null) {
                HttpSession session = req.getSession();
                session.setAttribute("userId", userId);
                session.setAttribute("login", login);
                String redirect = servlet.utils.URLutils.getFullUrlForRedirect(req, "/user");
                resp.sendRedirect(redirect);
            } else {
                req.setAttribute("error", "Неверный логин или пароль. Зарегестрируйтесь");
                RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/users/login.jsp");
                dispatcher.forward(req, resp);
            }

        } catch (NoSuchAlgorithmException e) {
            throw new ServletException("Ошибка при проверке пароля", e);
        }
    }
}
