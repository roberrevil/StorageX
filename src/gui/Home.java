package gui;

import backend.HomeImpl;
import exceptions.MissingFileException;
import exceptions.StorageXException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Home extends JPanel {
    private final JFrame jFrame;
    private final JTextField jTextField1 = new JTextField();
    private final JComboBox<String> jComboBox1 = new JComboBox<>();
    private final JToggleButton jToggleButton1 = new JToggleButton("ON/OFF");
    private final JSpinner jSpinner1 = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MONTH));
    private final JToolBar jToolBar1 = new JToolBar();
    private final JButton jButton1 = new JButton("Add");
    private final JButton jButton2 = new JButton("Delete");
    private final JButton jButton3 = new JButton("Download");
    private final JButton jButton4 = new JButton("Update");
    private final JTable jTable1 = new JTable();

    public Home(JFrame jFrame) {
        this.jFrame = jFrame;

        initComponents();
        initFunctions();
    }

    public void initComponents() {
        JPanel jPanel1 = new JPanel();
        jPanel1.setBorder(BorderFactory.createTitledBorder("Search"));

        JLabel jLabel1 = new JLabel("Name");
        JLabel jLabel2 = new JLabel("Extension");
        JLabel jLabel3 = new JLabel("Date");

        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(jSpinner1, "");
        dateEditor.getTextField().setHorizontalAlignment(JTextField.LEFT);
        jSpinner1.setEditor(dateEditor);
        jSpinner1.setEnabled(false);

        JPanel jPanel2 = new JPanel();
        jPanel2.setBorder(BorderFactory.createTitledBorder("Results"));

        jToolBar1.setRollover(true);

        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(SwingConstants.BOTTOM);
        jToolBar1.add(jButton1);

        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(SwingConstants.BOTTOM);
        jToolBar1.add(jButton2);

        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(SwingConstants.BOTTOM);
        jToolBar1.add(jButton2);
        jToolBar1.add(new JSeparator());

        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(SwingConstants.BOTTOM);
        jToolBar1.add(jButton3);
        jToolBar1.add(new JSeparator());

        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(SwingConstants.BOTTOM);
        jToolBar1.add(jButton4);

        DefaultTableModel defaultTableModel = new DefaultTableModel();
        defaultTableModel.addColumn("NAME");
        defaultTableModel.addColumn("EXTENSION");
        defaultTableModel.addColumn("SIZE");
        defaultTableModel.addColumn("DATE");
        jTable1.setModel(defaultTableModel);
        JScrollPane jScrollPane1 = new JScrollPane();
        jScrollPane1.setViewportView(jTable1);

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextField1))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jComboBox1, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jSpinner1)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jToggleButton1)))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(jToggleButton1)
                                        .addComponent(jSpinner1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(jToolBar1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jToolBar1, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                                .addContainerGap())
        );

        GroupLayout jPanelLayout = new GroupLayout(this);
        this.setLayout(jPanelLayout);
        jPanelLayout.setHorizontalGroup(
                jPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        jPanelLayout.setVerticalGroup(
                jPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
    }

    public void initFunctions() {
        HomeImpl home = new HomeImpl(jFrame);

        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                home.selectCategories(jComboBox1);
                home.selectResults(jTextField1.getText(), "", new SimpleDateFormat("").format(jSpinner1.getValue()),
                        jTable1);
            }
        });
        jTextField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String PARAM2;

                if (jComboBox1.getSelectedIndex() == 0)
                    PARAM2 = "";
                else
                    PARAM2 = String.valueOf(jComboBox1.getSelectedItem());

                String PARAM3;

                if (jToggleButton1.isSelected())
                    PARAM3 = new SimpleDateFormat("yyyy-MM").format(jSpinner1.getValue());
                else
                    PARAM3 = "";

                home.selectResults(jTextField1.getText(), PARAM2, PARAM3, jTable1);
            }
        });
        jComboBox1.addItemListener(e -> {
            String PARAM2;

            if (jComboBox1.getSelectedIndex() == 0)
                PARAM2 = "";
            else
                PARAM2 = String.valueOf(jComboBox1.getSelectedItem());

            String PARAM3;

            if (jToggleButton1.isSelected())
                PARAM3 = new SimpleDateFormat("yyyy-MM").format(jSpinner1.getValue());
            else
                PARAM3 = "";

            home.selectResults(jTextField1.getText(), PARAM2, PARAM3, jTable1);
        });
        jSpinner1.addChangeListener(e -> {
            String PARAM2;

            if (jComboBox1.getSelectedIndex() == 0)
                PARAM2 = "";
            else
                PARAM2 = String.valueOf(jComboBox1.getSelectedItem());

            home.selectResults(jTextField1.getText(), PARAM2, new SimpleDateFormat("yyyy-MM").format(jSpinner1.getValue()),
                    jTable1);
        });
        jToggleButton1.addActionListener(e -> {
            String PARAM2;

            if (jComboBox1.getSelectedIndex() == 0)
                PARAM2 = "";
            else
                PARAM2 = String.valueOf(jComboBox1.getSelectedItem());

            String PARAM3;

            if (jToggleButton1.isSelected()) {
                JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(jSpinner1, "yyyy-MM");
                jSpinner1.setEditor(dateEditor);

                PARAM3 = new SimpleDateFormat("yyyy-MM").format(jSpinner1.getValue());
            } else {
                JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(jSpinner1, "");
                jSpinner1.setEditor(dateEditor);

                PARAM3 = "";
            }

            jSpinner1.setEnabled(jToggleButton1.isSelected());

            home.selectResults(jTextField1.getText(), PARAM2, PARAM3, jTable1);
        });
        jButton1.addActionListener(e -> {
            try {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                byte[] PARAM5 = new byte[0];

                if (e.getSource() == jButton1)
                    if (jFileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
                        try {
                            PARAM5 = Files.readAllBytes(new File(jFileChooser.getSelectedFile().getPath()).toPath());
                        } catch (OutOfMemoryError ex) {
                            throw new StorageXException(ex.getMessage());
                        }

                String PARAM2 = jFileChooser.getSelectedFile().getName();

                for (int i = 0; i < PARAM2.length(); i++)
                    if (PARAM2.charAt(i) == '.')
                        PARAM2 = PARAM2.substring(i);

                long SIZE = Files.size(jFileChooser.getSelectedFile().toPath());
                String PARAM3 = null;

                if (String.valueOf(SIZE).length() < 4)
                    PARAM3 = SIZE+" B";
                else if (String.valueOf(SIZE/1000).length() < 4)
                    PARAM3 = SIZE/1000+" KB";
                else if (String.valueOf(SIZE/1000000).length() < 4)
                    PARAM3 = SIZE/1000000+" MB";

                home.add(jFileChooser.getSelectedFile().getName(), PARAM2, PARAM3, new SimpleDateFormat("yyyy-MM-dd").format(new Date()),
                        PARAM5);
            } catch (IOException ex) {
                throw new StorageXException(ex.getMessage());
            }
        });
        jButton2.addActionListener(e -> {
            int row = jTable1.getSelectedRow();

            if (row == -1)
                throw new MissingFileException();
            else
                home.delete(String.valueOf(jTable1.getValueAt(row, 0)), String.valueOf(jTable1.getValueAt(row, 1)),
                        String.valueOf(jTable1.getValueAt(row, 2)), String.valueOf(jTable1.getValueAt(row, 3)));
        });
        jButton3.addActionListener(e -> {
            int row = jTable1.getSelectedRow();

            if (row == -1)
                throw new MissingFileException();
            else
                home.download(String.valueOf(jTable1.getValueAt(row, 0)), String.valueOf(jTable1.getValueAt(row, 1)),
                        String.valueOf(jTable1.getValueAt(row, 2)), String.valueOf(jTable1.getValueAt(row, 3)));
        });
        jButton4.addActionListener(e -> home.reload());
    }
}
