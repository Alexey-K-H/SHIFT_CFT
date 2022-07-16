package ru.cft.shift.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cft.shift.entity.PromoCodeEntity;
import ru.cft.shift.exception.PromoCodeInvalidException;
import ru.cft.shift.repository.PromoCodeRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PromoCodeService {
    private final PromoCodeRepository promoCodeRepository;

    @Transactional
    public void registerCode(String codeValue, BigDecimal sum){
        promoCodeRepository.registerCode(codeValue, sum);
    }

    @Transactional
    public void deleteCodeByValue(String codeValue){
        promoCodeRepository.deleteByValue(codeValue);
    }

    @Transactional
    public BigDecimal activateCode(String codeValue) throws PromoCodeInvalidException {
        PromoCodeEntity promoCode = promoCodeRepository.getPromoCode(codeValue).orElse(null);

        if(promoCode == null){
            throw new PromoCodeInvalidException();
        }

        return promoCode.getSum();
    }
}
