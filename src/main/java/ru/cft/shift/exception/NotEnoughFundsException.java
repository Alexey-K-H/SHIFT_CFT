package ru.cft.shift.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "not enough funds on user balance to perform this operation")
public class NotEnoughFundsException extends Exception {
}
