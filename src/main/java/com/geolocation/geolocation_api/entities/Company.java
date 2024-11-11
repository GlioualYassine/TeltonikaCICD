package com.geolocation.geolocation_api.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;

    @Column(unique = true , nullable = false)
    private String name ;

    // Relation one-to-one avec l'admin de la compagnie (Company Admin)
    @OneToOne
    @JoinColumn(name = "admin_id" , nullable = true)
    private User admin;

    // Liste des utilisateurs de la compagnie
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users ;

    //information additionnelle
    private String address;
    private String phone;

    public Company(String nom, String address, String phone) {
        this.name = nom;
        this.address = address;
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company company)) return false;
        return Objects.equals(id, company.id) && Objects.equals(name, company.name) && Objects.equals(address, company.address) && Objects.equals(phone, company.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, phone);
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
