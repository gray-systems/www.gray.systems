package systems.gray.www.services.auth.client;

import java.nio.charset.Charset;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.hash.Hashing;

import systems.gray.www.services.common.exceptions.AuthenticationException;

/**
 *
 */
public class AuthServiceClient {

    private static final Charset UTF8 = Charsets.UTF_8;
    private final String userNameHash;
    private final String passwordHash;

    private AuthServiceClient(String username, String password) {
        userNameHash = hashString(username);
        passwordHash = hashString(password);
    }
    
    private static String hashString(String input) {
        return Hashing.sha512().hashString(input, UTF8).toString();
    }

    public static AuthServiceClient create(AuthServiceClientConfig config) {
        return new AuthServiceClient("username", "password");
    }
    
    public void authenticate(String username, String password) {
        String safeUsername = Strings.nullToEmpty(username);
        String safePassword = Strings.nullToEmpty(password);
        String userNameHash = hashString(safeUsername);
        String passwordHash = hashString(safePassword);

        boolean userNameOk = userNameHash.equals(this.userNameHash);
        boolean passwordHashOk = passwordHash.equals(this.passwordHash);
        if ((!userNameOk) || (!passwordHashOk)) {
            throw AuthenticationException.authenticationFailed();
        }
    }

}
