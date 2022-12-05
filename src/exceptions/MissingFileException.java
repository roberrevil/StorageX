package exceptions;

public class MissingFileException extends StorageXException {
    public MissingFileException() {
        super("No file selected.");
    }
}
