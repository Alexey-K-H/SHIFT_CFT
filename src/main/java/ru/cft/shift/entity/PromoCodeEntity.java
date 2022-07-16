package ru.cft.shift.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "promo_code")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class PromoCodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "value", nullable = false, unique = true)
    String value;

    @Column(name = "sum", precision = 10, scale = 2, nullable = false)
    BigDecimal sum;
}
