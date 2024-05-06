package railway.moroccorailwaysystem.security.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import railway.moroccorailwaysystem.model.User;
import railway.moroccorailwaysystem.repo.UserRepository;

import java.util.Optional;

@Component
public class CustomAuthProvider implements AuthenticationManager {

    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthProvider(
            UserRepository userRepository,
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        Optional<User> user = userRepository.findUserByEmail(authentication.getName());
        if(!user.isPresent()) {
            throw new UsernameNotFoundException("No user was found with these credentials");
        }
        if(!isSamePassword(authentication.getCredentials().toString(), user.get().getPassword())) {
            throw new BadCredentialsException("Authentication failed!");
        }
        userDetailsService.loadUserByUsername(user.get().getEmail());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new UsernamePasswordAuthenticationToken(
                authentication.getName(),
                authentication.getCredentials()
        );
    }

    public boolean isSamePassword(String pass, String encodedPass) {
        return passwordEncoder.matches(pass, encodedPass);
    }
}
