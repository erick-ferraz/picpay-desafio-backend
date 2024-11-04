package br.com.picpay.entity.dto;

import br.com.picpay.entity.Wallet;
import br.com.picpay.entity.enums.Type;
import br.com.picpay.exception.InputValidationException;
import com.mysql.cj.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public record WalletRequestDto(String name,
                               String cpfCnpj,
                               String email,
                               String password,
                               Type type) {

    public Wallet toEntity() {
        return new Wallet(name, cpfCnpj, email, password, type);
    }

    public void validateFields() {
        Map<String, String> errors = new HashMap<>();

        if(StringUtils.isNullOrEmpty(name)) {
            errors.put("name", "Name is required");
        }

        if(StringUtils.isNullOrEmpty(cpfCnpj)) {
            errors.put("cpfCnpj", "CPF or CNPJ is required");
        }

        if(StringUtils.isNullOrEmpty(email)) {
            errors.put("email", "Email is required");
        }

        if(StringUtils.isNullOrEmpty(password)) {
            errors.put("password", "Password is required");
        }

        if(Objects.isNull(type)) {
            errors.put("type", "Type is required");
        } else {
            try {
                Type.valueOf(type.toString());
            } catch (IllegalArgumentException e) {
                errors.put("type", "Type is not valid");
            }
        }

        if(!errors.isEmpty()) {
            throw new InputValidationException("Your input is not valid", errors);
        }
    }
}
