package com.example.task3.DAO;

import com.example.task3.Model.Address;
import com.example.task3.Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO implements DAOUser {
    private String jdbcURL = "jdbc:postgresql://localhost:5432/User";
    private String jdbcUser = "postgres";
    private String jdbcPassword = "123";

    private static final String INSERT_USERS_SQL = "INSERT INTO users" + "  (name, surname, age) VALUES "
            + " (?, ?, ?);";


    private static final String SELECT_USER_BY_ID = "select user_id, name, surname, age from users where user_id =?";
    private static final String SELECT_ALL_USERS = "select * from users";
    private static final String DELETE_USERS_SQL = "delete from users where user_id = ?;";
    private static final String UPDATE_USERS_SQL = "update users set name = ?,surname= ?, age =? where user_id = ?;";

    public UserDAO() {
    }

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void insertTransaction() throws SQLException {
        Connection connection = getConnection();
        connection.setAutoCommit(false);
        PreparedStatement updateUser = connection.prepareStatement("INSERT INTO users(name, surname, age) VALUES ('VLAD', 'ANISIM', 123)");
        updateUser.executeUpdate();
        PreparedStatement updateAddress = connection.prepareStatement("INSERT INTO user_address (user_id, street, house) values (4, 'Frunz', 13)");
        updateAddress.executeUpdate();
        connection.commit();
        connection.setAutoCommit(true);
    }


    /* public void insertTransaction() {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL_TRANSACTION)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    */


        @Override
        public User find (Long id){
            User user = null;
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);) {
                preparedStatement.setLong(1, id);
                System.out.println(preparedStatement);
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    int age = rs.getInt("age");
                    user = new User(id, name, surname, age);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return user;
        }

        @Override
        public List<User> findAll () {
            List<User> users = new ArrayList<>();
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {
                System.out.println(preparedStatement);
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    Long user_id = rs.getLong("user_id");
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    int age = rs.getInt("age");
                    users.add(new User(user_id, name, surname, age));
                    System.out.println(users);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return users;
        }

        @Override
        public void insert (User user){
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getSurname());
                preparedStatement.setInt(3, user.getAge());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        @Override
        public boolean update (User user){
            boolean updated = true;
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USERS_SQL)) {
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getSurname());
                preparedStatement.setInt(3, user.getAge());
                preparedStatement.setLong(4, user.getId());
                preparedStatement.executeUpdate();

                updated = preparedStatement.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return updated;
        }

        @Override
        public boolean delete (Long id){
            boolean rowDeleted = true;
            try (Connection connection = getConnection();
                 PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);) {
                statement.setLong(1, id);
                rowDeleted = statement.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return rowDeleted;
        }
    }
