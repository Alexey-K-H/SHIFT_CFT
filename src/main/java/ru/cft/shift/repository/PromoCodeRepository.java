package ru.cft.shift.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.cft.shift.entity.PromoCodeEntity;

import java.math.BigDecimal;
import java.util.Optional;

public interface PromoCodeRepository extends JpaRepository<PromoCodeEntity, Long> {
    boolean existsByValue(String value);

    @Modifying
    @Query(value = "insert into promo_code (value, sum)  values (?1, ?2)", nativeQuery = true)
    void registerCode(String value, BigDecimal sum);

    void deleteByValue(String value);

    @Query(value = "select * from promo_code where value = ?1 limit 1", nativeQuery = true)
    Optional<PromoCodeEntity> getPromoCode(String value);
}
