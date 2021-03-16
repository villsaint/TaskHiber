package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Util util = Util.getInstance();
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Statement statement = util.getConnection().createStatement()) {
            statement.execute("create table if not exists  user (`id` int not null AUTO_INCREMENT,`name` varchar(45) not null,`lastname` varchar(45) not null,`age` int not null,primary key (`id`)) ");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = util.getConnection().createStatement()) {
            statement.execute("drop table if  exists  user");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement = util.getConnection().prepareStatement("insert into user(name,lastName,age) values (?,?,?)")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement statement = util.getConnection().prepareStatement("delete from user where id =" + (int)id)){
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (PreparedStatement statement = util.getConnection().prepareStatement("slect * from user")) {
            ResultSet resultSet = statement.executeQuery("select*from user");
            while (resultSet.next()) {
                long id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                int age = resultSet.getInt("age");
                User user = new User(name, lastName, (byte) age);
                user.setId(id);
                list.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        try (Statement statement = util.getConnection().createStatement()) {
            statement.execute("TRUNCATE table user");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
