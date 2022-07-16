package ru.cft.shift.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cft.shift.entity.PassportEntity;
import ru.cft.shift.entity.UserEntity;
import ru.cft.shift.exception.IncorrectPassportException;
import ru.cft.shift.exception.PassportAlreadyRegisteredException;
import ru.cft.shift.exception.SmallAgeException;
import ru.cft.shift.exception.UserNotFoundException;
import ru.cft.shift.repository.PassportRepository;
import ru.cft.shift.repository.UserRepository;
import ru.cft.shift.utils.SecurityContextHelper;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PassportService {
    private final UserRepository userRepository;
    private final PassportRepository passportRepository;

    @Transactional
    public void updatePassportData(String series, String number)
            throws PassportAlreadyRegisteredException, SmallAgeException, IncorrectPassportException, UserNotFoundException {
        checkIsPassportDataFree(series, number);

        checkUserAge(series, number);
        checkPassportDataExist(series, number);

        UserEntity user = userRepository.findByEmail(SecurityContextHelper.email()).orElse(null);

        if(user == null){
            throw new UserNotFoundException();
        }

        PassportEntity passport = new PassportEntity()
                .setSeries(series)
                .setNumber(number)
                .setId(user.getId())
                .setUser(user);

        user.setPassport(passport);

    }

    @Transactional
    public boolean isUserHasPassportData(){
        return passportRepository.existsByUserEmail(SecurityContextHelper.email());
    }

    private void checkIsPassportDataFree(String series, String number) throws PassportAlreadyRegisteredException {
        if(passportRepository.existsBySeriesAndNumber(series, number)){
            throw new PassportAlreadyRegisteredException();
        }
    }

    private void checkUserAge(String series, String number) throws SmallAgeException {
        //TODO:how exactly we should check this?
    }

    private void checkPassportDataExist(String series, String number) throws IncorrectPassportException {
        //TODO:how exactly we should check k this?
    }
}
