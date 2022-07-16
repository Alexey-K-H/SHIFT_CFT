package ru.cft.shift.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "user with that passport data already registered")
public class PassportAlreadyRegisteredException extends Exception{
}
