package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import jm.task.core.jdbc.util.exception.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = """
                    CREATE TABLE IF NOT EXISTS users (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255),
                        last_name VARCHAR(255),
                        age TINYINT);
                    """;
        try (Connection connection = Util.open();
             Statement statement = connection.createStatement()) {
            var execute = statement.execute(sql);
        } catch (SQLException e) {
            throw new DatabaseException("Create users table error", e);
//            Насчет рантайма: я смотрел видосы/гуглил/спрашивал у гпт, тут пробрасывается кастомное анчекд исключение,
//            чтобы не связывать слой sql логики со слоем логики сервисов, иначе везде бы пришлось указывать throw и это
//            бед практис, нужно лоу лвл чекед экзепшнс оборачивать в кастомные анчекед исключения, но я поленился и
//            просто указал рантайм экзепшн. Если что-то не так написал, то оставь коммент, пж
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE if exists USERS;";
        try (Connection connection = Util.open();
        Statement statement = connection.createStatement()) {
            var execute = statement.execute(sql);
        } catch (SQLException e) {
            throw new DatabaseException("Drop users table error", e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = """
                INSERT INTO users (name, last_name, age) 
                values (?, ?, ?);
                """;
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Save user in table users error", e);
        }
    }

    public void removeUserById(long id) {
        String sql = "delete from users where id = ?";
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Delete user in table users error", e);
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "Select * from users";
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                list.add(new User(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("last_name"),
                        resultSet.getByte("age"))
                );
            }
            return list;
        } catch (SQLException e) {
            throw new DatabaseException("Select all users in table users error", e);
        }
    }

    public void cleanUsersTable() {
        String sql = "truncate table users;";
        try (Connection connection = Util.open();
             Statement statement = connection.createStatement()) {
            var execute = statement.execute(sql);
        } catch (SQLException e) {
            throw new DatabaseException("Clean users table error", e);
        }
    }
}
