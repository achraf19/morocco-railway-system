package railway.moroccorailwaysystem.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JWTUtils {

    @Value("${spring.config.jwt.secret}")
    public String secretKey;
    public static String JWT_SECRET_KEY;

    public static void setJwtSecretKey(String secretKey) {
        JWT_SECRET_KEY = secretKey;
    }

    @PostConstruct
    public void init() {
        setJwtSecretKey(secretKey);
    }

    private static Key getSignInKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET_KEY));
    }

    public static String generateToken(String email) {
        return Jwts
                .builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000 * 60))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractClaims(token);
        return claimsResolver.apply(claims);
    }

    public static Claims extractClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(JWT_SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static String extractEmailFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public static Date extractExpirationDateFromToken(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public static boolean isTokenValid(String token, String email, UserDetails u) {
        return email.equals(u.getUsername()) && !isTokenExpired(token);
    }

    public static boolean isTokenExpired(String token) {
        return JWTUtils.extractExpirationDateFromToken(token).before(new Date());
    }
}
