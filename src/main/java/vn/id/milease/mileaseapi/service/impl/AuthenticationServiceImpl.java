package vn.id.milease.mileaseapi.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.id.milease.mileaseapi.model.entity.user.Traveler;
import vn.id.milease.mileaseapi.model.entity.user.TravelerStatus;
import vn.id.milease.mileaseapi.model.entity.user.User;
import vn.id.milease.mileaseapi.model.entity.user.UserRole;
import vn.id.milease.mileaseapi.model.entity.user.UserStatus;
import vn.id.milease.mileaseapi.model.exception.AccountLockedException;
import vn.id.milease.mileaseapi.model.exception.GoogleIdTokenVerificationFailedException;
import vn.id.milease.mileaseapi.repository.UserRepository;
import vn.id.milease.mileaseapi.service.AuthenticationService;
import vn.id.milease.mileaseapi.util.JwtTokenProvider;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.Collections;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final JwtTokenProvider tokenProvider;
    @Value("${app.google.client-id}")
    private String clientId;
    private GoogleIdTokenVerifier verifier;

    public AuthenticationServiceImpl(UserRepository userRepository, JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }

    @PostConstruct
    public void initVerifier() {
        verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList(clientId))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();
    }

    @Override
    public String verifyIdToken(String idTokenString) {
        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken == null) throw new GoogleIdTokenVerificationFailedException(idTokenString);

            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();

            // If email is not in database, create new user
            User userDetails = userRepository.findByEmail(email)
                    .orElseGet(() -> createUserFromGoogle(payload));

            // If account is not verify, enable it because email was verified by Google
            if (userDetails.getStatus() == UserStatus.UNVERIFIED) {
                userDetails.setStatus(UserStatus.ACTIVE);
                userRepository.save(userDetails);
            }
            // Check if account is not locked
            if (userDetails.getStatus() != UserStatus.LOCKED) {
                UsernamePasswordAuthenticationToken authentication
                        = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

                return tokenProvider.generateToken(userDetails);
            }

            // Account cannot be accessed
            throw new AccountLockedException(userDetails);
        } catch (GeneralSecurityException | IOException e) {
            throw new GoogleIdTokenVerificationFailedException(idTokenString, e);
        }
    }

    private User createUserFromGoogle(GoogleIdToken.Payload payload) {
        Traveler traveler = Traveler.builder()
                .firstName((String) payload.get("given_name"))
                .lastName((String) payload.get("family_name"))
                .status(TravelerStatus.NORMAL)
                .build();
        User user = User.builder()
                .traveler(traveler)
                .email(payload.getEmail())
                .imageUrl((String) payload.get("picture"))
                .role(UserRole.TRAVELLER)
                .status(UserStatus.ACTIVE)
                .createdTime(LocalDateTime.now())
                .build();
        traveler.setUser(user);
        return userRepository.save(user);
    }
}
