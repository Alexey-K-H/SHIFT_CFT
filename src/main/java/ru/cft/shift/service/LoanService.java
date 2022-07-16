package ru.cft.shift.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cft.shift.dto.LoanDTO;
import ru.cft.shift.entity.LoanEntity;
import ru.cft.shift.entity.UserEntity;
import ru.cft.shift.exception.LoanNotFoundException;
import ru.cft.shift.exception.NotEnoughFundsException;
import ru.cft.shift.exception.UserNotFoundException;
import ru.cft.shift.repository.BalanceRepository;
import ru.cft.shift.repository.LoanRepository;
import ru.cft.shift.repository.UserRepository;
import ru.cft.shift.utils.SecurityContextHelper;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;

    private final UserRepository userRepository;

    @Transactional
    public List<LoanDTO> getUserLoans() throws UserNotFoundException {
        UserEntity user = userRepository.findByEmail(SecurityContextHelper.email()).orElse(null);

        if(user == null){
            throw new UserNotFoundException();
        }

        return loanRepository.findAllByUserId(user.getId()).stream().map(LoanDTO::getFromEntity).collect(Collectors.toList());
    }

    @Transactional
    public LoanDTO addLoan(BigDecimal debt, Long maturity) throws UserNotFoundException {

        UserEntity user = userRepository.findByEmail(SecurityContextHelper.email()).orElse(null);

        if(user == null){
            throw new UserNotFoundException();
        }

        LoanEntity loan = new LoanEntity()
                .setId(user.getId())
                .setUser(user)
                .setDebt(debt)
                .setMaturity(maturity)
                .setDateOfReceive(Date.from(Instant.now()));

        user.getLoans().add(loan);
        loanRepository.save(loan);

        return LoanDTO.getFromEntity(loan);
    }

    @Transactional
    public LoanDTO payDebtOff(Long loanId, BigDecimal sum) throws UserNotFoundException, LoanNotFoundException, NotEnoughFundsException {
        UserEntity user = userRepository.findByEmail(SecurityContextHelper.email()).orElse(null);

        if(user == null){
            throw new UserNotFoundException();
        }

        LoanEntity loan = loanRepository.getUserLoanById(loanId, user.getId()).orElse(null);
        if(loan == null){
            throw new LoanNotFoundException();
        }


        if (sum.abs().compareTo(user.getBalance().getFunds()) > 0) {
            throw new NotEnoughFundsException();
        }

        user.getBalance().setFunds(user.getBalance().getFunds().subtract(sum));

        loanRepository.setDebtSumByUserId(loanId, user.getId(), sum);
        loan.setDebt(loan.getDebt().subtract(sum));

        return LoanDTO.getFromEntity(loan);
    }

    @Transactional
    public LoanDTO getUserLoanById(Long loanId, Long userId){
        return LoanDTO.getFromEntity(loanRepository.getUserLoanById(loanId, userId).orElse(null));
    }

    @Transactional
    public Boolean deleteLoanById(Long loanId){
        if(!loanRepository.existsById(loanId)){
            return false;
        }

        loanRepository.deleteById(loanId);
        return true;
    }

}
