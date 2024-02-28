package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {

    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Connection connection = getConnection(); Statement st = connection.createStatement()) {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS USER (ID BIGINT AUTO_INCREMENT PRIMARY KEY," +
                    "NAME VARCHAR(100), LAST_NAME VARCHAR(100), AGE TINYINT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Connection connection = getConnection(); Statement st = connection.createStatement()) {
            st.executeUpdate("DROP TABLE IF EXISTS USER");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement("INSERT " +
                "INTO USER (NAME, LAST_NAME, AGE) VALUES (?, ?, ?)")) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement("DELETE " +
                "FROM USER WHERE ID = ?")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = getConnection(); Statement st = connection.createStatement(); ResultSet rs =
                st.executeQuery("SELECT * FROM USER")) {
            while(rs.next()) {
                User user = new User();
                user.setId(rs.getLong("ID"));
                user.setName(rs.getString("NAME"));
                user.setLastName(rs.getString("LAST_NAME"));
                user.setAge(rs.getByte("AGE"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Connection connection = getConnection(); Statement st = connection.createStatement()) {
            st.executeUpdate("DELETE FROM USER");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
