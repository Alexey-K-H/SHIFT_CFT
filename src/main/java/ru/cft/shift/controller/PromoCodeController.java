package ru.cft.shift.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.cft.shift.exception.PromoCodeInvalidException;
import ru.cft.shift.service.PromoCodeService;

import java.math.BigDecimal;

@Api
@RestController
@RequestMapping("/api/promo_code")
@RequiredArgsConstructor
public class PromoCodeController {
    private final PromoCodeService promoCodeService;

    @PostMapping("/register")
    public ResponseEntity<String> registerCode(
            @RequestParam(name = "code_value") String value,
            @RequestParam(name = "sum") BigDecimal sum
            ){
        promoCodeService.registerCode(value, sum);
        return ResponseEntity.ok("Code registered");
    }

    @DeleteMapping
    public ResponseEntity<String> deletePromoCode(
            @RequestParam(name = "code_value") String value
    ){
        promoCodeService.deleteCodeByValue(value);
        return ResponseEntity.ok("Code deleted");
    }

    @PostMapping("/activate")
    public ResponseEntity<BigDecimal> activateCode(
            @RequestParam(name = "value") String value
    ) throws PromoCodeInvalidException {
        return ResponseEntity.ok(promoCodeService.activateCode(value));
    }
}
