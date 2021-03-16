package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/firstbd?useSSL=false";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "drakedog";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static Connection connection;
    private static SessionFactory sessionFactory;

    private Util() {
    }
    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            Properties properties = new Properties();
            properties.put(Environment.DRIVER, DRIVER);
            properties.put(Environment.URL, URL);
            properties.put(Environment.USER, LOGIN);
            properties.put(Environment.PASS, PASSWORD);
            properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
            properties.put(Environment.SHOW_SQL, "false");

            Configuration configuration = new Configuration();
            configuration.setProperties(properties);
            configuration.addAnnotatedClass(User.class);

            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
            builder.applySettings(configuration.getProperties());
            ServiceRegistry serviceRegistry = builder.build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            return sessionFactory;
        }
        return sessionFactory;
    }

    public static Connection getConnection(){
        if(connection == null) {
            try {
                Class.forName(DRIVER);
                connection = DriverManager.getConnection(URL,LOGIN,PASSWORD);
                return connection;
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }
        return connection;
    }
}
