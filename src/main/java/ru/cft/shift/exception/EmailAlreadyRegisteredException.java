package ru.cft.shift.exception;

import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "email already registered")
public class EmailAlreadyRegisteredException extends Exception{
}
