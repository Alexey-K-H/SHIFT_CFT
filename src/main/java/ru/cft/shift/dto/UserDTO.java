package ru.cft.shift.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.cft.shift.entity.UserEntity;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotNull
    private Long id;

    @NotNull
    private String email;

    @NotNull
    private String surname;

    @NotNull
    private String name;

    @NotNull
    private String patronymic;


    public static UserDTO getFromEntity(UserEntity user){
        if(user == null){
            return null;
        }

        return new UserDTO()
                .setId(user.getId())
                .setSurname(user.getSurname())
                .setName(user.getName())
                .setPatronymic(user.getPatronymic())
                .setEmail(user.getEmail());
    }
}
