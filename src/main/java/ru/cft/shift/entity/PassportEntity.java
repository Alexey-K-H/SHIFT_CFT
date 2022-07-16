package ru.cft.shift.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "passport")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class PassportEntity {

    @Id
    @Column(name = "user_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "series")
    private String series;

    @Column(name = "number")
    private String number;
}
