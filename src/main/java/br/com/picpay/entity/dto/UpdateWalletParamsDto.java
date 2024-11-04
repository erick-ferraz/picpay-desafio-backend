package br.com.picpay.entity.dto;

import br.com.picpay.exception.InputValidationException;
import com.mysql.cj.util.StringUtils;
import jakarta.ws.rs.QueryParam;

import java.lang.reflect.Field;
import java.util.*;

public record UpdateWalletParamsDto(@QueryParam("name") String name,
                                    @QueryParam("email") String email,
                                    @QueryParam("password") String password) {

    public void validateFields() {
        List<String> possibleFields = Arrays.stream(getClass().getDeclaredFields())
            .map(Field::getName)
            .toList();

        Map<String, String> nonProvidedFields = new HashMap<>();

        if(StringUtils.isNullOrEmpty(name)) {
            nonProvidedFields.put("name", "Name was not provided");
        }

        if(StringUtils.isNullOrEmpty(email)) {
            nonProvidedFields.put("email", "Email was not provided");
        }

        if(StringUtils.isNullOrEmpty(password)) {
            nonProvidedFields.put("password", "Password was not provided");
        }

        if(nonProvidedFields.size() == possibleFields.size()) {
            throw new InputValidationException("You must provide query param for at least ONE of the following fields",
                nonProvidedFields);
        }
    }
}
