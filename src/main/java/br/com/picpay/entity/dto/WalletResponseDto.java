package br.com.picpay.entity.dto;

import br.com.picpay.entity.OwnerType;
import br.com.picpay.entity.Wallet;
import lombok.Data;

@Data
public class WalletResponseDto {

    private String name;
    private String cpfCnpj;
    private String email;
    private OwnerType ownerType;

    public WalletResponseDto(Wallet wallet) {
        this.name = wallet.getName();
        this.cpfCnpj = wallet.getCpfCnpj();
        this.email = wallet.getEmail();
        this.ownerType = wallet.getOwnerType();
    }
}
