package ru.cft.shift.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.cft.shift.exception.IncorrectPassportException;
import ru.cft.shift.exception.PassportAlreadyRegisteredException;
import ru.cft.shift.exception.SmallAgeException;
import ru.cft.shift.exception.UserNotFoundException;
import ru.cft.shift.service.PassportService;

@Api
@RestController
@RequestMapping("/api/passport")
@RequiredArgsConstructor
public class PassportController {
    private final PassportService passportService;

    @PutMapping("/user")
    public ResponseEntity<String> setUserPassportData(
            @RequestParam(name = "series") String series,
            @RequestParam(name = "number") String number
    ) throws IncorrectPassportException, PassportAlreadyRegisteredException, SmallAgeException, UserNotFoundException {
        passportService.updatePassportData(series, number);
        return ResponseEntity.ok("Successfully added passport data");
    }

    @GetMapping("/user/exist")
    public ResponseEntity<Boolean> isUserHasPassportData(){
        return ResponseEntity.ok(passportService.isUserHasPassportData());
    }

}
