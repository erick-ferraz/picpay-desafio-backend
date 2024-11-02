package br.com.picpay.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "tb_wallet")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String cpfCnpj;
    private String email;
    private BigDecimal balance;
    private String password;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;
}
