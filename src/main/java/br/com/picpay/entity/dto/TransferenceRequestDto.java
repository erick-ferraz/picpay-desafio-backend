package br.com.picpay.entity.dto;

import br.com.picpay.entity.Transference;
import br.com.picpay.exception.InputValidationException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public record TransferenceRequestDto(BigDecimal amount,
                                     Long payer,
                                     Long payee) {

    public void validateFields() {
        Map<String, String> errors = new HashMap<>();

        if (Objects.isNull(amount) || amount.compareTo(BigDecimal.ZERO) < 0) {
            errors.put("amount", "Amount must be not null and a positive number");
        }

        if(Objects.isNull(payer)) {
            errors.put("payer", "Payer must be not null");
        }

        if(Objects.isNull(payee)) {
            errors.put("payee", "Payee must be not null");
        }

        if(Objects.equals(payer, payee)) {
            errors.put("payee", "Payee is the same as payer");
        }

        if(!errors.isEmpty()) {
            throw new InputValidationException("Your input is not valid", errors);
        }
    }

    public Transference toEntity() {
        return new Transference(amount, payer, payee);
    }
}
