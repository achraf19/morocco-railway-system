package railway.moroccorailwaysystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import railway.moroccorailwaysystem.dto.payload.*;
import railway.moroccorailwaysystem.security.auth.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ApiResponse registerCustomer(
            @RequestBody RegisterRequest registerRequest
    ) {
        return authService.registerUser(registerRequest);
    }

    @GetMapping("/confirm-email")
    public ApiResponse confirmAccountRegistration(
            @RequestParam(value = "token") String token
    ) {
        return authService.confirmAccountRegistration(token);
    }

    @PostMapping("/signin")
    public AuthResponse authenticateCustomer(@RequestBody @Valid AuthRequest authRequest) {
        return authService.authenticateUser(authRequest);
    }

    @PostMapping("/forgot-password")
    public ApiResponse forgotPassword(@RequestBody String email) {
        return authService.forgotPassword(email);
    }

    @PostMapping("/confirm-new-password")
    public ApiResponse confirmNewPassword(
            @RequestBody ChangePasswordRequest changePasswordRequest
    ) {
        return authService.confirmNewPassword(changePasswordRequest);
    }

    @GetMapping("/logout")
    public ApiResponse logout(
            HttpServletRequest req,
            HttpServletResponse res
    ) {
        return authService.logout(req, res);
    }
}
