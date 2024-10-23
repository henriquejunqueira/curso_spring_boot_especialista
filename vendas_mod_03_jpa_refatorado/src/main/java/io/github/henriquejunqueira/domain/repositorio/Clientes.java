package io.github.henriquejunqueira.domain.repositorio;

import io.github.henriquejunqueira.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Clientes extends JpaRepository<Cliente, Integer> {

    // SELECT c FROM Cliente c WHERE c.nome LIKE :nome
    List<Cliente> findByNomeLike(String nome);

    // List<Cliente> findByNomeOrId(String nome, Integer id);
    // List<Cliente> findByNomeLikeOrId(String nome, Integer id); // Busca por nome ou id
    // List<Cliente> findByNomeOrIdOrderById(String nome, Integer id); // Busca por nome ou id e ordena por id

    // Cliente findOneByNome(); // Busca por um nome único, caso ele não possa repetir na tabela

    boolean existsByNome(String nome);

}
