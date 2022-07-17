package ru.cft.shift.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfo {
    private String email;
    private String password;
    private String surname;
    private String name;
    private String patronymic;
}
