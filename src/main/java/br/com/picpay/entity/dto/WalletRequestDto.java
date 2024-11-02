package br.com.picpay.entity.dto;

import br.com.picpay.entity.Wallet;
import br.com.picpay.entity.enums.Type;
import com.mysql.cj.util.StringUtils;

public record WalletRequestDto(String name,
                               String cpfCnpj,
                               String email,
                               String password,
                               Type type) {

    public Wallet toEntity() {
        return new Wallet(name, cpfCnpj, email, password, type);
    }

    public void validateFields() {

    }
}
