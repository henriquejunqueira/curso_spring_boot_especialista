package io.github.henriquejunqueira.domain.repository;

import io.github.henriquejunqueira.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos extends JpaRepository<Produto, Integer> {
}
