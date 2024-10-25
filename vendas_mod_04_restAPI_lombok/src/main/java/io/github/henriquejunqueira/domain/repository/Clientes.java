package io.github.henriquejunqueira.domain.repository;

import io.github.henriquejunqueira.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Clientes extends JpaRepository<Cliente, Integer> {

    // SELECT c FROM Cliente c WHERE c.nome LIKE :nome
    // List<Cliente> findByNomeLike(String nome);

    // @Query(value = "SELECT c FROM Cliente c WHERE c.nome LIKE :nome") // consulta com hql
    @Query(value = "SELECT * FROM cliente c WHERE c.nome LIKE '%:nome%' ", nativeQuery = true) // consulta com sql
    List<Cliente> encontrarPorNome( @Param("nome") String nome);

    @Query(" DELETE FROM Cliente c WHERE c.nome = :nome ")
    @Modifying
    void deleteByNome(String nome);

    // List<Cliente> findByNomeOrId(String nome, Integer id);
    // List<Cliente> findByNomeLikeOrId(String nome, Integer id); // Busca por nome ou id
    // List<Cliente> findByNomeOrIdOrderById(String nome, Integer id); // Busca por nome ou id e ordena por id

    // Cliente findOneByNome(); // Busca por um nome único, caso ele não possa repetir na tabela

    boolean existsByNome(String nome);

    @Query(" select c from Cliente c left join fetch c.pedidos where c.id = :id ")
    Cliente findClienteFetchPedidos(@Param("id") Integer id);

}
