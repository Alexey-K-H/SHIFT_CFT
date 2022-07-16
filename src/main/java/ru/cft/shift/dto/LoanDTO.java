package ru.cft.shift.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.cft.shift.entity.LoanEntity;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class LoanDTO {
    @NotNull
    private Long id;

    @NotNull
    private BigDecimal debt;

    @NotNull
    private String receiving;

    @NotNull
    private Long maturity;

    public static LoanDTO getFromEntity(LoanEntity loanEntity){
        if(loanEntity == null){
            return null;
        }

        Date receive = loanEntity.getDateOfReceive();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try{
            receive = sdf.parse(sdf.format(receive));
        }
        catch (Exception e){
            System.out.println("Error to convert date, use as it is");
        }

        return new LoanDTO()
                .setId(loanEntity.getId())
                .setDebt(loanEntity.getDebt())
                .setReceiving(sdf.format(receive))
                .setMaturity(loanEntity.getMaturity());
    }
}
