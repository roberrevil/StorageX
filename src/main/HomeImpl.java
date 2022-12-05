package backend;

import exceptions.StorageXException;
import gui.GUI;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class HomeImpl {
    private final JFrame jFrame;
    private final DB db = new DB();
    private final String OS_NAME = System.getProperty("os.name").toLowerCase();
    private final String USER_HOME = System.getProperty("user.home");

    public HomeImpl(JFrame jFrame) {
        this.jFrame = jFrame;
    }

    public void selectCategories(JComboBox<String> jComboBox) {
        try {
            DefaultComboBoxModel<String> defaultComboBoxModel = new DefaultComboBoxModel<>();
            defaultComboBoxModel.removeAllElements();

            Statement statement = db.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    """
                    SELECT NAME FROM extensions;
                    """
            );

            defaultComboBoxModel.addElement("All");

            while (resultSet.next())
                defaultComboBoxModel.addElement(resultSet.getString("NAME"));

            jComboBox.setModel(defaultComboBoxModel);

            statement.close();
        } catch (SQLException e) {
            throw new StorageXException(e.getMessage());
        }
    }

    public void selectResults(String PARAM1, String PARAM2, String PARAM3, JTable jTable) {
        try {
            DefaultTableModel defaultTableModel = (DefaultTableModel) jTable.getModel();
            defaultTableModel.setRowCount(0);

            PreparedStatement preparedStatement = db.getConnection().prepareStatement(
                    """
                    SELECT NAME, EXTENSION, SIZE, DATE FROM storage
                    WHERE NAME LIKE ? AND EXTENSION LIKE ? AND DATE LIKE ?
                    ORDER BY DATE DESC;
                    """
            );
            preparedStatement.setString(1, "%"+PARAM1+"%");
            preparedStatement.setString(2, "%"+PARAM2+"%");
            preparedStatement.setString(3, "%"+PARAM3+"%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
                defaultTableModel.addRow(
                        new String[] {
                                resultSet.getString("NAME"),
                                resultSet.getString("EXTENSION"),
                                resultSet.getString("SIZE"),
                                resultSet.getString("DATE")
                        }
                );

            jTable.setModel(defaultTableModel);

            preparedStatement.clearParameters();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new StorageXException(e.getMessage());
        }
    }

    public void add(String PARAM1, String PARAM2, String PARAM3, String PARAM4, byte[] PARAM5) {
        if (PARAM1.equals("") || PARAM2.equals("") || PARAM5 == null)
            JOptionPane.showMessageDialog(null, "Registration failed.", getClass().toString(),
                    JOptionPane.ERROR_MESSAGE);
        else
            try {
                PreparedStatement preparedStatement = db.getConnection().prepareStatement(
                        """
                        SELECT COUNT(NAME) FROM extensions
                        WHERE NAME LIKE ?;
                        """
                );
                preparedStatement.setString(1, PARAM2);
                ResultSet resultSet = preparedStatement.executeQuery();

                int results = 0;

                while (resultSet.next())
                    results = resultSet.getInt(1);

                preparedStatement.clearParameters();

                if (results == 0) {
                    preparedStatement = db.getConnection().prepareStatement(
                            """
                            INSERT INTO extensions (NAME)
                            VALUES (?);
                            """
                    );
                    preparedStatement.setString(1, PARAM2);
                    preparedStatement.executeUpdate();
                    preparedStatement.clearParameters();
                }

                preparedStatement = db.getConnection().prepareStatement(
                        """
                        INSERT INTO storage (NAME, EXTENSION, SIZE, DATE, FILE)
                        VALUES (?, ?, ?, ?, ?);
                        """
                );
                preparedStatement.setString(1, PARAM1);
                preparedStatement.setString(2, PARAM2);
                preparedStatement.setString(3, PARAM3);
                preparedStatement.setString(4, PARAM4);
                preparedStatement.setBytes(5, PARAM5);
                preparedStatement.executeUpdate();
                preparedStatement.clearParameters();
                preparedStatement.close();

                JOptionPane.showMessageDialog(null, "Registration successful.", getClass().toString(),
                        JOptionPane.INFORMATION_MESSAGE);

                reload();
            } catch (SQLException e) {
                throw new StorageXException(e.getMessage());
            }
    }

    public void delete(String PARAM1, String PARAM2, String PARAM3, String PARAM4) {
        try {
            String input = JOptionPane.showInputDialog(null, "Type DELETE to execute:",
                    getClass().toString(), JOptionPane.WARNING_MESSAGE);

            if (input.equals("DELETE")) {
                PreparedStatement preparedStatement = db.getConnection().prepareStatement(
                        """
                        SELECT ID FROM storage
                        WHERE NAME LIKE ? AND EXTENSION LIKE ? AND SIZE LIKE ? AND DATE LIKE ?;
                        """
                );
                preparedStatement.setString(1, PARAM1);
                preparedStatement.setString(2, PARAM2);
                preparedStatement.setString(3, PARAM3);
                preparedStatement.setString(4, PARAM4);
                ResultSet resultSet = preparedStatement.executeQuery();

                int results = 0;

                while (resultSet.next())
                    results = resultSet.getInt("ID");

                preparedStatement.clearParameters();

                preparedStatement = db.getConnection().prepareStatement(
                        """
                        DELETE FROM storage
                        WHERE ID LIKE ?;
                        """
                );
                preparedStatement.setInt(1, results);
                preparedStatement.executeUpdate();
                preparedStatement.clearParameters();
                preparedStatement.close();

                reload();
            }
        } catch (SQLException e) {
            throw new StorageXException(e.getMessage());
        }
    }

    public void download(String PARAM1, String PARAM2, String PARAM3, String PARAM4) {
        try {
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(
                    """
                    SELECT ID FROM storage
                    WHERE NAME LIKE ? AND EXTENSION LIKE ? AND SIZE LIKE ? AND DATE LIKE ?;
                    """
            );
            preparedStatement.setString(1, PARAM1);
            preparedStatement.setString(2, PARAM2);
            preparedStatement.setString(3, PARAM3);
            preparedStatement.setString(4, PARAM4);
            ResultSet resultSet = preparedStatement.executeQuery();

            int results = 0;

            while (resultSet.next())
                results = resultSet.getInt("ID");

            preparedStatement.clearParameters();

            preparedStatement = db.getConnection().prepareStatement(
                    """
                    SELECT FILE FROM storage
                    WHERE ID LIKE ?;
                    """
            );
            preparedStatement.setInt(1, results);
            resultSet = preparedStatement.executeQuery();

            String path;

            while (resultSet.next()) {
                if (OS_NAME.contains("win"))
                    path = USER_HOME+"\\Downloads\\"+PARAM1;
                else
                    path = USER_HOME+"/Downloads/"+PARAM1;

                FileUtils.writeByteArrayToFile(new File(path), resultSet.getBytes("FILE"));
            }

            if (OS_NAME.contains("win"))
                path = USER_HOME+"\\Downloads";
            else
                path = USER_HOME+"/Downloads";

            Desktop.getDesktop().open(new File(path));

            preparedStatement.clearParameters();
            preparedStatement.close();
        } catch (IOException | SQLException e) {
            throw new StorageXException(e.getMessage());
        }
    }

    public void reload() {
        jFrame.dispose();
        new GUI("Home");
    }
}
