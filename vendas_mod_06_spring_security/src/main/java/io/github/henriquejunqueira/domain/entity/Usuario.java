package io.github.henriquejunqueira.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    @NotEmpty(message = "{campo.login.obrigatorio}")
    private String login;

    @Column
    @NotEmpty(message = "{campo.senha.obrigatorio}")
    private String senha;

    @Column
    private boolean admin;

}
