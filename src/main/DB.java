package backend;

import exceptions.StorageXException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private final String OS_NAME = System.getProperty("os.name").toLowerCase();
    private final String USER_HOME = System.getProperty("user.home");
    public Connection getConnection() {
        try {
            String path;

            if (OS_NAME.contains("win"))
                path = USER_HOME+"\\StorageX\\storagex.sqlite";
            else
                path = USER_HOME+"/Library/StorageX/storagex.sqlite";

            Class.forName("org.sqlite.JDBC");

            return DriverManager.getConnection("jdbc:sqlite:"+path);
        } catch (ClassNotFoundException | SQLException e) {
            throw new StorageXException(e.getMessage());
        }
    }
}
