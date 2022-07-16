package ru.cft.shift.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.cft.shift.dto.LoanDTO;
import ru.cft.shift.exception.LoanNotFoundException;
import ru.cft.shift.exception.NotEnoughFundsException;
import ru.cft.shift.exception.UserNotFoundException;
import ru.cft.shift.service.LoanService;
import ru.cft.shift.service.UserService;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Api
@RestController
@RequestMapping("/api/loan")
@RequiredArgsConstructor
public class LoanController {
    private final LoanService loanService;
    private final UserService userService;

    @GetMapping("/user/all")
    public ResponseEntity<List<LoanDTO>> getUserLoans(
            ) throws UserNotFoundException
    {
            return ResponseEntity.ok(loanService.getUserLoans());
    }

    @PostMapping("/user/apply")
    public ResponseEntity<LoanDTO> applyForLoan(
            @RequestParam(name = "debt") BigDecimal debt,
            @RequestParam(name = "maturity")Long maturity
            ) throws UserNotFoundException {
            return ResponseEntity.ok(loanService.addLoan(debt, maturity));
    }

    @PutMapping("/pay/{id}")
    public ResponseEntity<LoanDTO> payTheDebtOff(
            @PathVariable(name = "id") Long loanId,
            @RequestParam(name = "sum") BigDecimal sum
    ) throws UserNotFoundException, LoanNotFoundException, NotEnoughFundsException {
            return ResponseEntity.ok(loanService.payDebtOff(loanId, sum));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanDTO> getCurrentLoan(
            @PathVariable(name = "id") Long loanId
    ){
        try {
            return ResponseEntity.ok(loanService.getUserLoanById(loanId, userService.getCurrentUser().getId()));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLoan(
            @PathVariable(name = "id") Long loanId
    ){
        if(!loanService.deleteLoanById(loanId)){
            return new ResponseEntity<>("Failed to delete loan", HttpStatus.BAD_REQUEST);
        }else {
            return ResponseEntity.ok("Deleted");
        }
    }

}
