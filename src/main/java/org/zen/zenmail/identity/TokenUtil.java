package org.zen.zenmail.identity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.zen.zenmail.model.user.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@Service
public class TokenUtil {
    private static final long VALIDITY_TIME_MS = 2 * 60 * 60 * 1000;
    private static final String AUTH_HEADER_NAME = "Authorization";

    private String secret = "mrin";

    public Optional<Authentication> verifyToken(HttpServletRequest request) {
        final String token = request.getHeader(AUTH_HEADER_NAME);

        if (token != null && !token.isEmpty()) {
            final TokenUser user = parseUserFromToken(token.replace("Bearer", "").trim());
            if (user != null) {
                return Optional.of(new UserAuthentication(user.getUsername(), user.getPassword()));
            }
        }
        return Optional.empty();
    }


    public TokenUser parseUserFromToken(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        String userName = ((String) claims.get("username"));

        String password = (String) claims.get("pass");

        String roles = (String) (claims.get("role"));

        String name = (String) claims.get("name");

        return new TokenUser(userName, password, roles, name);
    }

    public String createTokenForUser(TokenUser user) {
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + VALIDITY_TIME_MS))
                .setSubject(user.getUsername())
                .claim("username", user.getUsername())
                .claim("pass", user.getPassword())
                .claim("role", user.getRoles())
                .claim("name", user.getName())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

}
