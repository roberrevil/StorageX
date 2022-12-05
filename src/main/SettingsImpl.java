package backend;

import exceptions.StorageXException;
import gui.GUI;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SettingsImpl {
    private final JFrame jFrame;
    private final DB db = new DB();
    private final String OS_NAME = System.getProperty("os.name").toLowerCase();
    private final String USER_HOME = System.getProperty("user.home");

    public SettingsImpl(JFrame jFrame) {
        this.jFrame = jFrame;
    }

    public void selectResults(JList<String> jList) {
        try {
            DefaultListModel<String> defaultListModel = new DefaultListModel<>();
            defaultListModel.removeAllElements();

            Statement statement = db.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    """
                    SELECT NAME FROM settings;
                    """
            );

            while (resultSet.next())
                defaultListModel.addElement(resultSet.getString("NAME"));

            jList.setModel(defaultListModel);

            statement.close();
        } catch (SQLException e) {
            throw new StorageXException(e.getMessage());
        }
    }

    public void downloadData() {
        try {
            String path;

            if (OS_NAME.contains("win"))
                path = USER_HOME+"\\Downloads\\StorageX";
            else
                path = USER_HOME+"/Downloads/StorageX";

            //noinspection ResultOfMethodCallIgnored
            new File(path).mkdir();

            PreparedStatement preparedStatement1 = db.getConnection().prepareStatement(
                    """
                    SELECT ID FROM storage;
                    """
            );
            ResultSet resultSet = preparedStatement1.executeQuery();

            while (resultSet.next()) {
                PreparedStatement preparedStatement2 = db.getConnection().prepareStatement(
                        """
                        SELECT NAME, FILE from storage
                        WHERE ID LIKE ?;
                        """
                );
                preparedStatement2.setInt(1, resultSet.getInt("ID"));
                ResultSet resultSet2 = preparedStatement2.executeQuery();

                while (resultSet2.next()) {
                    String NAME = resultSet2.getString("NAME");

                    if (OS_NAME.contains("win"))
                        path = USER_HOME+"\\Downloads\\StorageX\\"+NAME;
                    else
                        path = USER_HOME+"/Downloads/StorageX/"+NAME;

                    FileUtils.writeByteArrayToFile(new File(path), resultSet2.getBytes("FILE"));
                }

                preparedStatement2.clearParameters();
                preparedStatement2.close();
            }

            if (OS_NAME.contains("win"))
                path = USER_HOME+"\\Downloads";
            else
                path = USER_HOME+"/Downloads";

            Desktop.getDesktop().open(new File(path));

            preparedStatement1.clearParameters();
            preparedStatement1.close();
        } catch (IOException | SQLException e) {
            throw new StorageXException(e.getMessage());
        }
    }

    public void truncateData() {
        try {
            String input = JOptionPane.showInputDialog(null, "This operation might corrupt the database. Type TRUNCATE to execute:",
                    getClass().toString(), JOptionPane.WARNING_MESSAGE);

            if (input.equals("TRUNCATE")) {
                Statement statement = db.getConnection().createStatement();
                statement.executeUpdate(
                        """
                        -- noinspection SqlWithoutWhereForFile
                        DELETE FROM extensions;
                        DELETE FROM storage;
                        """
                );
                statement.close();

                reload();
            }
        } catch (SQLException e) {
            throw new StorageXException(e.getMessage());
        }
    }

    public void changeScreenMode() {
        try {
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(
                    """
                    SELECT PROPERTY FROM settings
                    WHERE NAME LIKE 'Change Screen Mode';
                    """
            );
            ResultSet resultSet = preparedStatement.executeQuery();

            String results = null;

            while (resultSet.next())
                results = resultSet.getString("PROPERTY");

            assert results != null;
            if (results.equals("LIGHT_MODE"))
                preparedStatement = db.getConnection().prepareStatement(
                        """
                        UPDATE settings
                        SET PROPERTY = 'DARK_MODE'
                        WHERE NAME LIKE 'Change Screen Mode';
                        """
                );
            else
                preparedStatement = db.getConnection().prepareStatement(
                        """
                        UPDATE settings
                        SET PROPERTY = 'LIGHT_MODE'
                        WHERE NAME LIKE 'Change Screen Mode';
                        """
                );

            preparedStatement.executeUpdate();
            preparedStatement.clearParameters();
            preparedStatement.close();

            reload();
        } catch (SQLException e) {
            throw new StorageXException(e.getMessage());
        }
    }

    public void getSupport() {
        try {
            Desktop.getDesktop().browse(URI.create("https://www.robertovicario.com"));
        } catch (IOException e) {
            throw new StorageXException(e.getMessage());
        }
    }

    public void getSystemProperties() {
        String message =
                System.getProperty("java.vendor")+"\n"+
                        System.getProperty("java.vendor.url")+"\n"+
                        System.getProperty("java.version")+"\n"+
                        System.getProperty("os.arch")+"\n"+
                        System.getProperty("os.name")+"\n"+
                        System.getProperty("os.version");

        JOptionPane.showMessageDialog(null, message, getClass().toString(), JOptionPane.INFORMATION_MESSAGE);
    }

    public void backupDB() {
        try {
            String path1;
            String path2;

            if (OS_NAME.contains("win"))
                path1 = USER_HOME+"\\StorageX\\storagex.sqlite";
            else
                path1 = USER_HOME+"/Library/StorageX/storagex.sqlite";

            if (OS_NAME.contains("win"))
                path2 = USER_HOME+"\\Downloads";
            else
                path2 = USER_HOME+"/Downloads";

            FileUtils.copyFileToDirectory(new File(path1), new File(path2));

            Desktop.getDesktop().open(new File(path2));
        } catch (IOException e) {
            throw new StorageXException(e.getMessage());
        }
    }

    public void reload() {
        jFrame.dispose();
        new GUI("Home");
    }
}
