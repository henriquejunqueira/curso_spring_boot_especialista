package io.github.henriquejunqueira.service;

import io.github.henriquejunqueira.domain.entity.Pedido;
import io.github.henriquejunqueira.domain.enums.StatusPedido;
import io.github.henriquejunqueira.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {

    Pedido salvar(PedidoDTO pedidoDTO);

    Optional<Pedido> obterPedidoCompleto(Integer id);

    void atualizaStatus(Integer id, StatusPedido statusPedido);

}
