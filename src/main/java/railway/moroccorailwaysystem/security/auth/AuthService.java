package railway.moroccorailwaysystem.security.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import railway.moroccorailwaysystem.dto.payload.*;
import railway.moroccorailwaysystem.exception.DuplicatedResourceException;
import railway.moroccorailwaysystem.exception.ForgotPasswordException;
import railway.moroccorailwaysystem.exception.ResourceNotFoundException;
import railway.moroccorailwaysystem.exception.TokenInvalidException;
import railway.moroccorailwaysystem.model.User;
import railway.moroccorailwaysystem.model.UserType;
import railway.moroccorailwaysystem.repo.UserRepository;
import railway.moroccorailwaysystem.utils.EmailService;

import java.util.Optional;

import static railway.moroccorailwaysystem.utils.JWTUtils.*;
import static railway.moroccorailwaysystem.utils.JWTUtils.generateToken;

@Service
public class AuthService {

    public final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final CustomAuthProvider customAuthProvider;

    public AuthService(
            PasswordEncoder passwordEncoder,
            UserRepository userRepository,
            EmailService emailService,
            CustomAuthProvider customAuthProvider
    ) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.customAuthProvider = customAuthProvider;
    }

    public ApiResponse registerUser(RegisterRequest registerRequest) {
        checkIfUserAlreadyExists(registerRequest.email());
        LOGGER.info("Registering a new user with email %s".formatted(registerRequest.email()));
        User user = new User(
                registerRequest.firstName(),
                registerRequest.lastName(),
                registerRequest.age(),
                registerRequest.email(),
                passwordEncoder.encode(registerRequest.password()),
                registerRequest.userType() == null ? UserType.CUSTOMER : UserType.ADMIN
        );
        user.setAccountConfirmed(false);
        userRepository.save(user);
        String token = generateToken(user.getEmail());
        LOGGER.info("the generated token for the registering process %s".formatted(token));
        emailService.sendConfirmationMail(user.getEmail(), token);

        return new ApiResponse(
                registerRequest.email(),
                "We sent you an e-mail to confirm your account registration",
                HttpStatus.OK
        );
    }

    public ApiResponse confirmAccountRegistration(String token) {
        String email = extractEmailFromToken(token);
        Optional<User> user = userRepository.findUserByEmail(email);
        if(user.isEmpty()) {
            throw new ResourceNotFoundException(
                    "User with email %s not found".formatted(email)
            );
        }
        if(isTokenExpired(token)) {
            throw new TokenInvalidException(
                    "It seems that the token sent to this email %s is now expired"
                            .formatted(email)
            );
        }
        user.get().setAccountConfirmed(true);
        userRepository.save(user.get());

        return new ApiResponse(
                email,
                "Your account is now activated",
                HttpStatus.OK
        );
    }

    public AuthResponse authenticateUser(AuthRequest authRequest) {
        LOGGER.info("Processing the login of user with email %s".formatted(authRequest.email()));
        customAuthProvider.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authRequest.email(),
                    authRequest.password()
                )
        );
        String token = generateToken(authRequest.email());

        return new AuthResponse(
                authRequest.email(),
                token
        );
    }

    public void checkIfUserAlreadyExists(String email) {
        boolean userExists = userRepository.existsByEmail(email);
        if(userExists) {
            throw new DuplicatedResourceException(
                    "user with email %s already exists!"
                            .formatted(email)
            );
        }
    }

    public ApiResponse forgotPassword(String email) {
        checkIfUserAlreadyExists(email);
        String token = generateToken(email);
        emailService.sendForgotPasswordMail(email, token);

        return new ApiResponse(
                email,
                "We sent you an e-mail to reset your password",
                HttpStatus.OK
        );
    }

    public ApiResponse confirmNewPassword(ChangePasswordRequest changePasswordRequest) {
        User user = userRepository.findUserByEmail(changePasswordRequest.email())
                .orElseThrow(() -> new ResourceNotFoundException(
                                "User with email %s not found"
                                        .formatted(changePasswordRequest.email())
                        )
                );

        boolean updated = false;
        if(customAuthProvider.isSamePassword(changePasswordRequest.newPassword(), user.getPassword())) {
            throw new ForgotPasswordException(
                    "You've typed the same password for email %s"
                            .formatted(changePasswordRequest.email())
            );
        }
        user.setPassword(passwordEncoder.encode(changePasswordRequest.newPassword()));
        updated = true;
        userRepository.save(user);

        return new ApiResponse(
                changePasswordRequest.email(),
                "Your password is updated successfully",
                HttpStatus.OK
        );
    }

    public ApiResponse logout(HttpServletRequest req, HttpServletResponse res) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) {
            new SecurityContextLogoutHandler().logout(req, res, auth);
            return new ApiResponse(
                    null,
                    "You're logout was successfully",
                    HttpStatus.ACCEPTED
            );
        }
        return null;
    }
}
