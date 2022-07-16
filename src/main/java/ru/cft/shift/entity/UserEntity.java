package ru.cft.shift.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, precision = 20)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "surname", nullable = false, precision = 50)
    private String surname;

    @Column(name = "name", nullable = false, precision = 30)
    private String name;

    @Column(name = "patronymic", nullable = false, precision = 70)
    private String patronymic;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private PassportEntity passport;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private BalanceEntity balance;

    @OneToMany(mappedBy = "user")
    private Set<LoanEntity> loans;

    public UserEntity(String email, String password, String surname, String name, String patronymic) {
        this.email = email;
        this.password = password;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
    }
}
