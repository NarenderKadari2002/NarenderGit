import io.jsonwebtoken.JwtException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
public class JwtGenerator {

    // Generate a signing key (HS256)
    private static final String SECRET_KEY = "8f4c2a9d7e1b6f3a5c8d0e2f4b7a9c1d6e3f8a2b5c7d9f1a4e6b8c0d2f5a7b9";

    // Method to create a JWT
    public static String generateToken(String username, String role) {
        long expirationTime = 1000 * 60 * 10; // 10 mins

        return Jwts.builder()
                .setSubject(username) // main identifier
                .setIssuer("YESBANK")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .claim("role", role) // custom claim for role
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
    public static String validateToken(String token) {
        try {

            String subject = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            return "Valid token for user: " + subject;
        } catch (JwtException e) {
            return "Invalid: " + e.getMessage();
        }
    }
}
