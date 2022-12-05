package exceptions;

import javax.swing.*;

public class StorageXException extends RuntimeException {
    public StorageXException(String e) {
        JOptionPane.showMessageDialog(null, e, getClass().toString(), JOptionPane.ERROR_MESSAGE);
    }
}
