package br.com.picpay.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_transference")
public class Transference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    private Long payer;
    private Long payee;
    private LocalDateTime dateTime;

    public Transference(BigDecimal amount, Long payer, Long payee) {
        this.amount = amount;
        this.payer = payer;
        this.payee = payee;
        this.dateTime = LocalDateTime.now();
    }
}
