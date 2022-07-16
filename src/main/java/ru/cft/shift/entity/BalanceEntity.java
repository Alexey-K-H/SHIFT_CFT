package ru.cft.shift.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "balance")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class BalanceEntity {
    @Id
    @Column(name = "user_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "funds", precision = 10, scale = 2, nullable = false)
    private BigDecimal funds;


}
