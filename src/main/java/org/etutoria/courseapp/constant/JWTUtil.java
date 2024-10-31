// JWTUtil.java
package org.etutoria.courseapp.constant;

public class JWTUtil {

    //public static final long EXPIRE_ACCESS_TOKEN = 1 * 60 * 1000; // 1 minute
    public static final long EXPIRE_ACCESS_TOKEN = 24 * 60 * 60 * 1000; // 24 hours
    public static final long EXPIRE_REFRESH_TOKEN = 120 * 60 * 1000;
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String ISSUER = "springBootApp"; // le issuer represente l'application qui a généré le token
    public static final String SECRET = "myPrivateSecret";
    public static final String AUTH_HEADER = "Authorization";
}