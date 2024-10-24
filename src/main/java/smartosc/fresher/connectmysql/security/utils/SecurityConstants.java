package smartosc.fresher.connectmysql.security.utils;

public class SecurityConstants {

    /**
     * Token Prefix
     * We will use this prefix when parsing JWT Token
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * Authorization Prefix in HttpServletRequest
     * Authorization: <type> <credentials>
     * For Example : Authorization: Bearer YWxhZGxa1qea32GVuc2VzYW1l
     */
    public static final String HEADER_STRING = "Authorization";

    public static final String AUTHENTICATION_BASE_PATH = "/accounts";

    public static final String FORBIDDEN_MESSAGE = "403 forbidden — you don't have permission to access this resource";

    private SecurityConstants() {
        throw new UnsupportedOperationException();
    }
}
