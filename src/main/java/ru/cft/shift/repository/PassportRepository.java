package ru.cft.shift.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cft.shift.entity.PassportEntity;

import java.util.Optional;

public interface PassportRepository extends JpaRepository<PassportEntity, Long> {
    boolean existsBySeriesAndNumber(String series, String number);

    boolean existsByUserEmail(String email);
}
