package com.example.Unit_2_Project.security;

import com.example.Unit_2_Project.model.User; // Adjust based on where your User entity lives
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Getter //  Use @Getter from Lombok to auto-generate getters for fields
public class UserPrincipal implements UserDetails {

    private final User user; //  Final since it's set in the constructor and shouldn't change

    private final int id;
    private final String username;
    private final String password;
    private final List<GrantedAuthority> authorities;

    public UserPrincipal(User user) {
        this.user = user; //  Initialize the 'user' field or getEmail() will break

        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + user.getRole()) //  Construct role from your user entity
        );


    }

    //  Optional custom getter—not part of UserDetails, so no @Override
    public String getEmail() {
        return user.getEmail(); //  Will now compile fine since 'user' is initialized
    }



    //  Spring Security interface methods
    @Override public String getUsername() { return username; }
    @Override public String getPassword() { return password; }
    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}



