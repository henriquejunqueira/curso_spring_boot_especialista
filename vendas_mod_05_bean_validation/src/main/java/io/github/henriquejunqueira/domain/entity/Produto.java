package io.github.henriquejunqueira.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "produto")
//@Getter
//@Setter
//@ToString
//@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data // as anotações @Getter, @Setter, @ToString e @EqualsAndHashCode existem na @Data
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "descricao")
    @NotEmpty(message = "Campo descrição é obrigatório")
    private String descricao;

    @Column(name = "preco_unitario")
    @NotNull(message = "Campo preço é obrigatório.")
    private BigDecimal preco;

}