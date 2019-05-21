package ru.antonov.smartfridge.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(nullable = false)
    String login;
    @Column(name = "full_name")
    String fullName;
    @Column(name = "pass", nullable = false)
    String password;
    @Column(name = "creation_date")
    LocalDate creationDate;

    //@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    //private List<Product> product;

    public User(String login, String fullName, String password){
        this.login = login;
        this.fullName = fullName;
        this.password = password;
        this.creationDate = LocalDate.now();
    }


    public User(String login, String password){
        this.login = login;

        this.password = password;
        this.creationDate = LocalDate.now();
    }
}
