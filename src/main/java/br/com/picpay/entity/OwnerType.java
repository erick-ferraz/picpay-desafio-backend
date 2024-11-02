package br.com.picpay.entity;

import br.com.picpay.entity.enums.Type;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_owner_type")
public class OwnerType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Type type;
}
