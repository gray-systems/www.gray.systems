package systems.gray.www.services.common.exceptions;

public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String explanation;

    public BadRequestException(Throwable cause, String explanation) {
        super(cause);
        this.explanation = explanation;
    }

    public String getExplanation() {
        return explanation;
    }
}
