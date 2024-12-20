package br.com.picpay.entity;

import br.com.picpay.entity.enums.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_wallet")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "cpf_cnpj")
    private String cpfCnpj;
    private String email;
    private BigDecimal balance = BigDecimal.ZERO;
    private String password;
    @ManyToOne
    @JoinColumn(name = "owner_type_id")
    private OwnerType ownerType;

    public Wallet(String name, String cpfCnpj, String email, String password, Type type) {
        this.name = name;
        this.cpfCnpj = cpfCnpj;
        this.email = email;
        this.password = password;
        this.ownerType = type.toOwnerType();
    }

    public boolean isUser() {
        return Objects.equals(ownerType.getType(), Type.USER.toString());
    }

    public boolean hasBalance() {
        return this.balance.compareTo(BigDecimal.ZERO) > 0;
    }

    public void debit(BigDecimal amount) {
        this.balance = balance.subtract(amount);
    }

    public void credit(BigDecimal amount) {
        this.balance = balance.add(amount);
    }
}
