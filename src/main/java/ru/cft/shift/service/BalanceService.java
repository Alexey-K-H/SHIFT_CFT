package ru.cft.shift.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cft.shift.entity.BalanceEntity;
import ru.cft.shift.exception.NotEnoughFundsException;
import ru.cft.shift.repository.BalanceRepository;
import ru.cft.shift.utils.SecurityContextHelper;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BalanceService {
    private final BalanceRepository balanceRepository;

    @Transactional
    public BigDecimal getUserBalanceValue(){
        BalanceEntity balance = balanceRepository.findByUserEmail(SecurityContextHelper.email()).orElse(null);
        if(balance == null){
            return null;
        }

        return balance.getFunds();
    }

    @Transactional
    public BigDecimal changeUserBalance(BigDecimal sum) throws NotEnoughFundsException {
        BalanceEntity balance = balanceRepository.findByUserEmail(SecurityContextHelper.email()).orElse(null);
        if(balance == null){
            return null;
        }

        if(sum.compareTo(BigDecimal.ZERO) < 0){
            if(sum.abs().compareTo(balance.getFunds()) > 0){
                throw new NotEnoughFundsException();
            }
        }

        balance.setFunds(balance.getFunds().add(sum));
        return balance.getFunds();
    }
}
