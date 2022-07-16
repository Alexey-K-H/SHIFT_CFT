package ru.cft.shift.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.cft.shift.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "select * from users where id = ?1 limit 1", nativeQuery = true)
    Optional<UserEntity> findById(Long id);

    @Query(value = "select * from users where users.email = ?1 limit 1;", nativeQuery = true)
    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteByEmail(String email);
}
