package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static Util util = Util.getInstance();
    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try {
            Session session = util.getSessionFactory().openSession();
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS user " +
                    "(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(20) NOT NULL, lastName VARCHAR(30) NOT NULL, " +
                    "age TINYINT NOT NULL)").executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException  e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            Session session = util.getSessionFactory().openSession();
            session.beginTransaction();
            session.createSQLQuery("Drop table if exists User").executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            Session session = util.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException  e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            Session session = util.getSessionFactory().openSession();
            session.beginTransaction();
            User user = (User) session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException  e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List <User>list = new ArrayList<>();
        try {
            Session session = util.getSessionFactory().openSession();
            Query query = session.createQuery("FROM User");
            list = (List<User>)query.list();
            session.close();
            return list;
        } catch (HibernateException  e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try {
            Session session = util.getSessionFactory().openSession();
            session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException  e) {
            e.printStackTrace();
        }
    }
}
