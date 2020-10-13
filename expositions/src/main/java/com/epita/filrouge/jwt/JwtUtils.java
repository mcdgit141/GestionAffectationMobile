package com.epita.filrouge.jwt;

import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {

//    On défini la durée de validité du token: Heure * minutes * secondes
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

//    @Value("${jwt.secret}")
    private String secret="private_key";


    // retrieve username from jwt token
    public String getUsernameFromToken(final String token, HttpServletRequest request) {
        String userName = null;
        try {
            userName = getClaimFromToken(token, Claims::getSubject);
        } catch (ExpiredJwtException e) {
            request.setAttribute("Expired","token expire");
        } catch (MalformedJwtException e) {
            System.out.println("Token Absent ou non conforme");
        }
        return userName;
    }

    // retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(final String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(final String token) {
        return Jwts.parser() //
                .setSigningKey(secret) //
                .parseClaimsJws(token) //
                .getBody();
    }

    // check if the token has expired
    private Boolean isTokenExpired(final String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // generate token for user
    public String generateToken(final UserDetails userDetails) {
        final Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    // while creating the token -
    // 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
    // 2. Sign the JWT using the HS512 algorithm and secret key.
    // 3. According to JWS Compact
    // Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    // compaction of the JWT to a URL-safe string
    private String doGenerateToken(final Map<String, Object> claims, final String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject) //
                .setIssuedAt(new Date(System.currentTimeMillis())) //
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret) //
                .compact();
    }

    // validate token
    public Boolean validateToken(final String token, final UserDetails userDetails, HttpServletRequest request) {
        final String username = getUsernameFromToken(token, request);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}
