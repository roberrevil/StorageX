package main;

import exceptions.StorageXException;

import javax.swing.*;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Installer {
    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
    private static final String USER_HOME = System.getProperty("user.home");
    private static String DB_PATH;

    public static void setupEnvironment() {
        if (OS_NAME.contains("win")) {
            if (!new File(USER_HOME+"\\StorageX").exists())
                //noinspection ResultOfMethodCallIgnored
                new File(USER_HOME+"\\StorageX").mkdir();

            DB_PATH = USER_HOME+"\\StorageX\\storagex.sqlite";
        } else {
            if (!new File(USER_HOME+"/Library/StorageX").exists())
                //noinspection ResultOfMethodCallIgnored
                new File(USER_HOME+"/Library/StorageX").mkdir();

            DB_PATH = USER_HOME+"/Library/StorageX/storagex.sqlite";
        }
    }

    public static void setupDB() {
        try {
            Class.forName("org.sqlite.JDBC");

            Connection connection = DriverManager.getConnection("jdbc:sqlite:"+DB_PATH);
            Statement statement = connection.createStatement();
            statement.executeUpdate(
                    """
                    -- DDL setup
                    CREATE TABLE extensions (
                        NAME TEXT,
                        PRIMARY KEY (NAME)
                    );
                    CREATE TABLE storage (
                        ID INTEGER PRIMARY KEY AUTOINCREMENT,
                        NAME TEXT NOT NULL,
                        EXTENSION TEXT NOT NULL,
                        SIZE TEXT NOT NULL,
                        DATE DATE NOT NULL,
                        FILE LONGBLOB NOT NULL UNIQUE,
                        FOREIGN KEY (EXTENSION) REFERENCES extensions (NAME)
                    );
                    CREATE TABLE settings (
                        NAME TEXT,
                        PROPERTY TEXT,
                        PRIMARY KEY (NAME)
                    );
                    
                    -- DML setup
                    INSERT INTO settings (NAME, PROPERTY)
                    VALUES ('Download Data', NULL);
                    INSERT INTO settings (NAME, PROPERTY)
                    VALUES ('Truncate Data', NULL);
                    INSERT INTO settings (NAME, PROPERTY)
                    VALUES ('Change Screen Mode', 'LIGHT_MODE');
                    INSERT INTO settings (NAME, PROPERTY)
                    VALUES ('Get System Properties', NULL);
                    INSERT INTO settings (NAME, PROPERTY)
                    VALUES ('Get Support', NULL);
                    INSERT INTO settings (NAME, PROPERTY)
                    VALUES ('Backup DB', NULL);
                    INSERT INTO settings (NAME, PROPERTY)
                    VALUES ('Reload', NULL);
                    """
            );
            statement.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new StorageXException(e.getMessage());
        }
    }

    public static void main(String[] args) {
        setupEnvironment();
        setupDB();

        JOptionPane.showMessageDialog(null, "Installation successful.", "Installer",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
