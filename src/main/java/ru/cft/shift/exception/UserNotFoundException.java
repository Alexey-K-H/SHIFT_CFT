package ru.cft.shift.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "user not found to perform this operation")
public class UserNotFoundException extends Exception{
}
