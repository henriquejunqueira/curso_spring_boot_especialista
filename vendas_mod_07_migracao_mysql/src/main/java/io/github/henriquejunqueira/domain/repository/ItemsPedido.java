package io.github.henriquejunqueira.domain.repository;

import io.github.henriquejunqueira.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsPedido extends JpaRepository<ItemPedido, Integer> {
}
