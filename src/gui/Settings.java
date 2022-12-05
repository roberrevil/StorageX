package gui;

import backend.SettingsImpl;
import exceptions.MissingOpsException;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Settings extends JPanel {
    private final JFrame jFrame;
    private final JToolBar jToolBar1 = new JToolBar();
    private final JList<String> jList1 = new JList<>();
    private final JButton jButton1 = new JButton("Execute");

    public Settings(JFrame jFrame) {
        this.jFrame = jFrame;

        initComponents();
        initFunctions();
    }

    public void initComponents() {
        JPanel jPanel1 = new JPanel();
        jPanel1.setBorder(BorderFactory.createTitledBorder("Ops"));

            jToolBar1.setRollover(true);

            jButton1.setFocusable(false);
            jButton1.setHorizontalTextPosition(SwingConstants.CENTER);
            jButton1.setVerticalTextPosition(SwingConstants.BOTTOM);
            jToolBar1.add(jButton1);

            JScrollPane jScrollPane1 = new JScrollPane();
            jScrollPane1.setViewportView(jList1);

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jToolBar1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jToolBar1, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                                .addContainerGap())
        );

        GroupLayout jPanelLayout = new GroupLayout(this);
        this.setLayout(jPanelLayout);
        jPanelLayout.setHorizontalGroup(
                jPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        jPanelLayout.setVerticalGroup(
                jPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
    }

    public void initFunctions() {
        SettingsImpl settings = new SettingsImpl(jFrame);

        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                settings.selectResults(jList1);
            }
        });
        jButton1.addActionListener(e -> {
            switch (String.valueOf(jList1.getSelectedValue())) {
                case "Download Data" -> settings.downloadData();
                case "Truncate Data" -> settings.truncateData();
                case "Change Screen Mode" -> settings.changeScreenMode();
                case "Get Support" -> settings.getSupport();
                case "Get System Properties" -> settings.getSystemProperties();
                case "Backup DB" -> settings.backupDB();
                case "Reload" -> settings.reload();
                default -> throw new MissingOpsException();
            }
        });
    }
}
