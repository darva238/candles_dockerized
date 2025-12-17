package repository;

import entity.Candle;
import exception.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CandleRepository {

    private final String INSERT_QUERY = "INSERT INTO candles(candle, cost, id_profile) VALUES (?, ?, ?)";
    private final String GET_BY_CANDLE_QUERY = "SELECT * FROM candles WHERE id_profile = ?";
    private final String GET_BY_CANDLE_AND_COST_QUERY = "SELECT * FROM candles WHERE  id_profile = ? AND candle = ?";
    private final String UPDATE_QUERY = "UPDATE candles SET candle = ?, cost = ? WHERE id_profile = ? AND id = ?";
    private final String DELETE_QUERY = "DELETE FROM candles WHERE id = ? AND id_profile = ?";


    public boolean saveCandle(Candle candle) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY);
            ps.setString(1, candle.getCandle());
            ps.setDouble(2, candle.getCost());
            ps.setInt(3, candle.getIdProfile());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Candle> getByCandle(int idProfile) {
        List<Candle> candles = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_BY_CANDLE_QUERY)) {
            ps.setInt(1, idProfile);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Candle c = new Candle();
                    c.setId(rs.getInt("id"));
                    c.setCandle(rs.getString("candle"));
                    c.setCost(rs.getDouble("cost"));
                    c.setIdProfile(rs.getInt("id_profile"));
                    candles.add(c);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return candles;
    }

    public List<Candle> getCandlesByNameAndCost(int idProfile, String candleName) {
        List<Candle> candles = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_CANDLE_AND_COST_QUERY)) {

            preparedStatement.setInt(1, idProfile);
            preparedStatement.setString(2, candleName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Candle candle = new Candle();
                    candle.setId(resultSet.getInt("id"));
                    candle.setCandle(resultSet.getString("candle"));
                    candle.setCost(resultSet.getDouble("cost"));
                    candle.setIdProfile(resultSet.getInt("id_profile"));
                    candles.add(candle);
                }
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return candles;
    }

    public boolean updateCandle(Candle candle) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY)) {
            ps.setString(1, candle.getCandle());
            ps.setDouble(2, candle.getCost());
            ps.setInt(3, candle.getIdProfile());
            ps.setInt(4, candle.getId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public boolean deleteCandle(int candleId, int profileId) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_QUERY)) {
            ps.setInt(1, candleId);
            ps.setInt(2, profileId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

}
