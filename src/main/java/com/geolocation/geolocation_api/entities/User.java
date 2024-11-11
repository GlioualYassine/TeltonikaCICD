package com.geolocation.geolocation_api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geolocation.geolocation_api.entities.enums.ERole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column(unique = true)
    private String email;
    @Column
    private String password;
    @Column
    @Enumerated(EnumType.STRING)
    private ERole role;

    // Relation avec la compagnie (nullable si l'utilisateur n'a pas de compagnie)
    @ManyToOne
    @JoinColumn(name = "company_id" , nullable = true)
    private Company company;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private  List<GpsDevice> voitures ;

    public User(String fn, String ls, String mail, String s) {
        this.firstName = fn;
        this.lastName = ls;
        this.email = mail;
        this.password = s;
        this.role = ERole.ROLE_USER;
    }
    public User(String fn, String ls, String mail, String s,ERole role) {
        this.firstName = fn;
        this.lastName = ls;
        this.email = mail;
        this.password = s;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
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
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email)  && role == user.role && Objects.equals(company, user.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, role, company);
    }
}
