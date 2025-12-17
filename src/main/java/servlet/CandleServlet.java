package servlet;

import entity.Candle;
import exception.DBException;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.CandleRepository;
import service.CandleService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/candles")
public class CandleServlet extends HttpServlet {

    private CandleService candleService;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            CandleRepository candleRepository = new CandleRepository();
            this.candleService = new CandleService(candleRepository);
        } catch (DBException e) {
            throw new ServletException("Ошибка подключения к базе данных", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userIdLong = (Long) req.getSession().getAttribute("userId");
        if (userIdLong == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int userId = userIdLong.intValue();
        String search = req.getParameter("search");
        String costParam = req.getParameter("cost");

        if (search != null && search.isBlank()) {
            search = null;
        }

        if (costParam != null && costParam.isBlank()) {
            costParam = null;
        }

        List<Candle> candles;

        try {
            if (search == null && costParam == null) {
                candles = candleService.getByCandle(userId);
            } else if (search != null) {
                candles = candleService.getCandlesByNameAndCost(userId, search);
            } else {
                candles = candleService.getCandlesByNameAndCost(userId, "");
            }
            req.setAttribute("candles", candles);
            req.getRequestDispatcher("/WEB-INF/users/candles.jsp").forward(req, resp);
        } catch (DBException e) {
            req.setAttribute("error", "Произошла ошибка при загрузке свечей.");
            req.getRequestDispatcher("/WEB-INF/users/candles.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        Long userIdLong = (Long) req.getSession().getAttribute("userId");

        if (userIdLong == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        int userId = userIdLong.intValue();

        try {
            switch (action) {
                case "add": {
                    String candleName = req.getParameter("candle");
                    double cost = Double.parseDouble(req.getParameter("cost"));
                    Candle candle = new Candle();
                    candle.setCandle(candleName);
                    candle.setCost(cost);
                    candle.setIdProfile(userId);
                    candleService.saveCandle(candleName, cost, userId);
                    String redirect = servlet.utils.URLutils.getFullUrlForRedirect(req, "/candles");
                    resp.sendRedirect(redirect);
                    break;
                }
                case "update": {
                    int id = Integer.parseInt(req.getParameter("id"));
                    String updatedCandleName = req.getParameter("candle");
                    double updatedCost = Double.parseDouble(req.getParameter("cost"));
                    candleService.updateCandle(id, updatedCandleName, updatedCost, userId);
                    String redirect = servlet.utils.URLutils.getFullUrlForRedirect(req, "/candles");
                    resp.sendRedirect(redirect);
                    break;
                }
                case "delete": {
                    int deleteId = Integer.parseInt(req.getParameter("id"));
                    candleService.deleteCandle(deleteId, userId);
                    String redirect = servlet.utils.URLutils.getFullUrlForRedirect(req, "/candles");
                    resp.sendRedirect(redirect);
                    break;
                }
                default:
                    break;
            }
        } catch (NumberFormatException e) {
            req.setAttribute("error", "Неверный формат числа.");
            req.getRequestDispatcher("/WEB-INF/users/candles.jsp").forward(req, resp);
        } catch (DBException e) {
            req.setAttribute("error", "Ошибка при работе с базой данных.");
            req.getRequestDispatcher("/WEB-INF/users/candles.jsp").forward(req, resp);
        }
    }
}

