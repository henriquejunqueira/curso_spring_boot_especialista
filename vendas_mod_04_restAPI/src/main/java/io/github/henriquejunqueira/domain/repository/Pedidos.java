package io.github.henriquejunqueira.domain.repository;

import io.github.henriquejunqueira.domain.entity.Cliente;
import io.github.henriquejunqueira.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface Pedidos extends JpaRepository<Pedido, Integer> {

//    Set<Pedido> findByCliente(); // Posso utilizar Set ou List nesse caso
    List<Pedido> findByCliente(Cliente cliente);

}
