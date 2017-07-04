package systems.gray.www.services.common.exceptions;

public class InternalServerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InternalServerException(Throwable cause) {
        super(cause);
    }
}