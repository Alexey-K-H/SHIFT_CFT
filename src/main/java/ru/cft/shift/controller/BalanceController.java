package ru.cft.shift.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.cft.shift.exception.NotEnoughFundsException;
import ru.cft.shift.service.BalanceService;

import java.math.BigDecimal;

@Api
@RestController
@RequestMapping("/api/balance")
@RequiredArgsConstructor
public class BalanceController {
    private final BalanceService balanceService;

    @GetMapping("/user")
    public ResponseEntity<BigDecimal> getUserBalanceValue(){
        BigDecimal balance = balanceService.getUserBalanceValue();

        if(balance == null){
            return new ResponseEntity<>(BigDecimal.ZERO, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(balance);
    }

    @PutMapping("/user/change")
    public ResponseEntity<BigDecimal> changeUserBalance(
            @RequestParam(name = "sum") BigDecimal sum
    ) throws NotEnoughFundsException {
        return ResponseEntity.ok(balanceService.changeUserBalance(sum));
    }
}
