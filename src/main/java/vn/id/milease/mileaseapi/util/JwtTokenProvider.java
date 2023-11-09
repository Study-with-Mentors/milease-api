package vn.id.milease.mileaseapi.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import vn.id.milease.mileaseapi.model.entity.user.Traveler;
import vn.id.milease.mileaseapi.model.entity.user.User;
import vn.id.milease.mileaseapi.model.exception.InvalidJwtException;
import vn.id.milease.mileaseapi.model.exception.JwtExpiredException;
import vn.id.milease.mileaseapi.repository.TravelerRepository;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtTokenProvider {
    private static final int MILLISECONDS_PER_MINUTE = 60000;

    private final TravelerRepository travelerRepository;
    @Value("${app.jwt.secret}")
    private String JWT_SECRET;
    @Value("${app.jwt.expiration:60}")
    private long JWT_EXPIRATION; // minutes
    private SecretKey JWT_SECRET_KEY;

    public JwtTokenProvider(TravelerRepository travelerRepository) {
        this.travelerRepository = travelerRepository;
    }

    @PostConstruct
    public void postConstruct() {
        JWT_SECRET_KEY = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION * MILLISECONDS_PER_MINUTE);

        var jwtBuilder = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("id", ((User) userDetails).getId())
                .claim("role", ((User) userDetails).getRole())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(JWT_SECRET_KEY);

        Optional<Traveler> travelerOpt = travelerRepository.findById(((User) userDetails).getId());
        String status = "NORMAL";
        if (travelerOpt.isPresent()) {
            Traveler traveler = travelerOpt.get();
            if (traveler.getPremiumExpiredDate() != null && traveler.getPremiumExpiredDate().isAfter(LocalDateTime.now())) {
                status = "PREMIUM";
            }
        }
        jwtBuilder.claim("traveler_status", status);

        return jwtBuilder.compact();
    }

    public String generateEmailVerificationToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(JWT_SECRET_KEY)
                .compact();
    }

    public String getEmailFromJwt(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(JWT_SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Validate the token and throw an exception if it is not valid.
     *
     * @param token the token to validate
     * @throws InvalidJwtException if the token is not valid
     * @throws JwtExpiredException if the token is expired
     */
    public void validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(JWT_SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException ex) {
            throw new JwtExpiredException(token, ex);
        } catch (JwtException | IllegalArgumentException ex) {
            throw new InvalidJwtException(token, ex);
        }
    }
}
