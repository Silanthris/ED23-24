package ed.Utils.Exceptions;

public class FullNodeException extends RuntimeException {
    public FullNodeException(String collection) {
        super("The selected node already has two parents");
    }
}
