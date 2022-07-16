package ru.cft.shift.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cft.shift.dto.UserDTO;
import ru.cft.shift.entity.BalanceEntity;
import ru.cft.shift.entity.UserEntity;
import ru.cft.shift.exception.EmailAlreadyRegisteredException;
import ru.cft.shift.repository.UserRepository;
import ru.cft.shift.utils.SecurityContextHelper;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserDTO createUser(
            String email,
            String password,
            String surname,
            String name,
            String patronymic)
            throws
            EmailAlreadyRegisteredException
    {
        checkEmailIsFree(email);
        UserEntity user = new UserEntity(email, password, surname, name, patronymic);
        userRepository.save(user);

        BalanceEntity balance = new BalanceEntity()
                .setId(user.getId())
                .setUser(user)
                .setFunds(new BigDecimal("0.00"));

        user.setBalance(balance);

        return UserDTO.getFromEntity(user);
    }

    @Transactional
    public UserDTO getCurrentUser(){
        return UserDTO.getFromEntity(userRepository.findByEmail(SecurityContextHelper.email()).orElse(null));
    }

    @Transactional
    public UserEntity findUserByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }

    private void checkEmailIsFree(String email) throws EmailAlreadyRegisteredException {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyRegisteredException();
        }
    }

    @Transactional
    public Boolean deleteUser() {
        if (!userRepository.existsByEmail(SecurityContextHelper.email())) {
            return false;
        }

        userRepository.deleteByEmail(SecurityContextHelper.email());
        return true;
    }
}