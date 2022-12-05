package exceptions;

public class MissingOpsException extends StorageXException {
    public MissingOpsException() {
        super("No operation selected.");
    }
}
