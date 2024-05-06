package railway.moroccorailwaysystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private int age;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String password;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    private boolean isAccountConfirmed;

    public User(
            String firstName,
            String lastName,
            int age,
            String email,
            String password,
            UserType userType
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ADMIN_ROLE"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
