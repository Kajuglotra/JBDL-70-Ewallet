package org.gfg.UserServiceApplication.model;

import jakarta.persistence.*;
import lombok.*;
import org.gfg.Utilities.UserIdentifier;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@Entity
//@RequiredArgsConstructor
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;

    @Column(unique = true, nullable = false)
    private String contact;

    @Column(unique = true, nullable = false)
    private String email;

    private String authorities;

    private String password;

    private boolean accountNonExpired;

    private boolean enabled;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private String name;

    private String address;

    private String dob;

    private UserType userType;

    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;

    @Enumerated
    private UserIdentifier identifier;

    private String userIdentifierValue;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return  Arrays.stream(authorities.split(",")).map(a -> new SimpleGrantedAuthority(a)).toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return contact;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
