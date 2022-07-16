package ru.cft.shift.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cft.shift.dto.UserDTO;
import ru.cft.shift.entity.BalanceEntity;
import ru.cft.shift.entity.PassportEntity;
import ru.cft.shift.entity.UserEntity;
import ru.cft.shift.exception.EmailAlreadyRegisteredException;
import ru.cft.shift.exception.IncorrectPassportException;
import ru.cft.shift.exception.PassportAlreadyRegisteredException;
import ru.cft.shift.exception.SmallAgeException;
import ru.cft.shift.repository.BalanceRepository;
import ru.cft.shift.repository.PassportRepository;
import ru.cft.shift.repository.UserRepository;
import ru.cft.shift.utils.PassportChecker;
import ru.cft.shift.utils.SecurityContextHelper;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PassportRepository passportRepository;

    @Transactional
    public UserDTO createUser(
            String email,
            String password,
            String surname,
            String name,
            String patronymic,
            String passportSeries,
            String passportNumber)
            throws
            EmailAlreadyRegisteredException,
            PassportAlreadyRegisteredException,
            SmallAgeException,
            IncorrectPassportException
    {
        checkEmailIsFree(email);
        UserEntity user = new UserEntity(email, password, surname, name, patronymic);
        userRepository.save(user);

        if(passportNumber != null && passportSeries != null){
            checkIsPassportDataFree(passportSeries, passportNumber);

            PassportChecker.checkUserAge(passportSeries, passportNumber);
            PassportChecker.checkPassportDataExist(passportSeries, passportNumber);

            PassportEntity passport = new PassportEntity()
                    .setId(user.getId())
                    .setUser(user)
                    .setSeries(passportSeries)
                    .setNumber(passportNumber);

            user.setPassport(passport);
        }

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

    private void checkIsPassportDataFree(String series, String number) throws PassportAlreadyRegisteredException {
        if(passportRepository.existsBySeriesAndNumber(series, number)){
            throw new PassportAlreadyRegisteredException();
        }
    }
}
