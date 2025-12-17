package servlet;

import entity.Profile;
import exception.ServiceException;
import exception.ValidationException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import repository.ProfileRepository;
import service.PasswordService;
import service.ProfileService;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    private final ProfileService profileService = new ProfileService(new ProfileRepository(), new PasswordService());


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/users/registration.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try {
            Profile newProfile = profileService.createProfile(login, password);
            HttpSession session = req.getSession();
            session.setAttribute("login", newProfile.getLogin());
            session.setAttribute("userId", newProfile.getId());
            resp.sendRedirect(servlet.utils.URLutils.getFullUrlForRedirect(req, "/user"));
        } catch (ValidationException e) {
            req.setAttribute("error", "Пользователь с таким логином уже существует.");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/users/registration.jsp");
            requestDispatcher.forward(req, resp);
        } catch (NoSuchAlgorithmException | ServiceException e) {
            throw new ServletException(e);
        }
    }
}
