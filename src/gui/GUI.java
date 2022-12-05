package gui;

import backend.DB;
import com.formdev.flatlaf.intellijthemes.FlatMaterialDesignDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialLighterIJTheme;
import exceptions.StorageXException;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GUI extends JFrame {
    private final String tab;

    public GUI(String tab) {
        this.tab = tab;

        setupGUI();
        initComponents();
    }

    public static void setupGUI() {
        try {
            System.setProperty("apple.awt.application.name", "StorageX");
            System.setProperty("apple.laf.useScreenMenuBar", "true");

            Statement statement = new DB().getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    """
                    SELECT PROPERTY FROM settings
                    WHERE NAME LIKE 'Change Screen Mode';
                    """
            );

            while (resultSet.next()) {
                String result = resultSet.getString("PROPERTY");

                if (result.equals("LIGHT_MODE"))
                    UIManager.setLookAndFeel(new FlatMaterialLighterIJTheme());
                else
                    UIManager.setLookAndFeel(new FlatMaterialDesignDarkIJTheme());
            }

            statement.close();
        } catch (SQLException | UnsupportedLookAndFeelException e) {
            throw new StorageXException(e.getMessage());
        }
    }

    public void initComponents() {
        JFrame jFrame = new JFrame();
        JTabbedPane jTabbedPane1 = new JTabbedPane();

        jTabbedPane1.addTab("Home", new Home(jFrame));
        jTabbedPane1.addTab("Settings", new Settings(jFrame));

        switch (tab) {
            case "Home" -> jTabbedPane1.setSelectedIndex(0);
            case "Settings" -> jTabbedPane1.setSelectedIndex(1);
        }

        GroupLayout layout = new GroupLayout(jFrame.getContentPane());
        jFrame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jTabbedPane1, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jTabbedPane1, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                                .addContainerGap())
        );

        jFrame.setTitle("StorageX");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setMinimumSize(new Dimension(500, 500));
        jFrame.setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
}
