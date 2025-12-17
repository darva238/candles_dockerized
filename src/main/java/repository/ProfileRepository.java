package repository;

import entity.Profile;
import exception.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileRepository {
    private final String INSERT_BY_ID_TEMPLATE = "INSERT INTO profile(login, password_hash, salt) VALUES (?, ?, ?)";
    private final String GET_BY_LOGIN_TEMPLATE = "SELECT id_profile, login, password_hash, salt FROM profile WHERE login = ?";
    private static final String CHECK_LOGIN_EXIST_QUERY = "SELECT id_profile FROM profile WHERE login = ?";

    public Profile save(Profile profile) throws DBException {
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(INSERT_BY_ID_TEMPLATE);
            ps.setString(1, profile.getLogin());
            ps.setString(2, profile.getPassword());
            ps.setString(3, profile.getSalt());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        profile.setId(generatedKeys.getLong(1));
                    }
                }
            } else {
                throw new DBException("Ошибка. Профиль не создан.");
            }
            return profile;
        } catch (SQLException e) {
            throw new DBException("Ошибка в работе с базой данных", e);
        }
    }

    public Profile getByLogin(String login) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(GET_BY_LOGIN_TEMPLATE);
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Profile profile = new Profile();
                profile.setId(rs.getLong("id_profile"));
                profile.setLogin(rs.getString("login"));
                profile.setPassword(rs.getString("password_hash"));
                profile.setSalt(rs.getString("salt"));
                return profile;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean isLoginExist(String login) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(CHECK_LOGIN_EXIST_QUERY);
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}
