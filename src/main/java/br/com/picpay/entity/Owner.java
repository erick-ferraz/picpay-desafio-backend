package br.com.picpay.entity;

import br.com.picpay.entity.enums.OwnerType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_owner")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private OwnerType type;
}
