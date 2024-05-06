package railway.moroccorailwaysystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JWTAuthFilter jwtAuthFilter;

    public SecurityConfig (JWTAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
           .csrf(AbstractHttpConfigurer::disable)
           .authorizeHttpRequests(authorize -> {
              authorize.requestMatchers(
               "/api/v1/auth/**", "/api/v1/users/**"
             )
             .permitAll()
             .anyRequest()
             .authenticated();
           })
           .formLogin(withDefaults())
           .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
           .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> {
                    logout.logoutUrl("/api/v1/auth/logout");
                    logout.logoutSuccessUrl("/api/v1/auth/signin");
                });

        return http.build();
    }
}