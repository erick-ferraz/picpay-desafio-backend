package br.com.picpay.entity;

import br.com.picpay.entity.enums.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_owner_type")
public class OwnerType {

    @Id
    private Long id;
    private String type;

}
