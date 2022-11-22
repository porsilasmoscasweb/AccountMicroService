package reloadly.ms_01.jwt;

public class JWT {
    public static final long TTL = 86_400_000; // 1 day
    public static final String KEY = "1234";
    public static final String HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
}
