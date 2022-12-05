package main;

import exceptions.StorageXException;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Uninstaller {
    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
    private static final String USER_HOME = System.getProperty("user.home");

    public static void breakupEnvironment() {
        try {
            String path;

            if (OS_NAME.contains("win"))
                path = USER_HOME+"\\StorageX";
            else
                path = USER_HOME+"/Library/StorageX";

            FileUtils.deleteDirectory(new File(path));
        } catch (IOException e) {
            throw new StorageXException(e.getMessage());
        }
    }

    public static void main(String[] args) {
        breakupEnvironment();

        JOptionPane.showMessageDialog(null, "Uninstallation successful.", "Uninstaller",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
