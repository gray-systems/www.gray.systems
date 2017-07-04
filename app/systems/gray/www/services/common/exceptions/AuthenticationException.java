package systems.gray.www.services.common.exceptions;

/**
 *
 */
public class AuthenticationException extends BadRequestException {

    private static final long serialVersionUID = 1L;

    /**
     * @param cause
     * @param explanation
     */
    public AuthenticationException(Throwable cause, String explanation) {
        super(cause, explanation);
    }


    public static AuthenticationException authenticationFailed() {
        return new AuthenticationException(new RuntimeException(),
                "Username or password incorrect");
    }
}
