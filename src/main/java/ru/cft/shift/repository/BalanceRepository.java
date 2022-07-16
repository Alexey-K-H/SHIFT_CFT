package ru.cft.shift.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.cft.shift.entity.BalanceEntity;

import java.util.Optional;

public interface BalanceRepository extends JpaRepository<BalanceEntity, Long> {

    @Query(value = "select balance.user_id, balance.funds from balance inner join users u on u.id = balance.user_id where u.email = ?1 limit 1;", nativeQuery = true)
    Optional<BalanceEntity> findByUserEmail(String email);

}
