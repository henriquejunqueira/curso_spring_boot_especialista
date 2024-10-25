package io.github.henriquejunqueira.service;

import io.github.henriquejunqueira.domain.entity.Pedido;
import io.github.henriquejunqueira.rest.dto.PedidoDTO;

public interface PedidoService {

    Pedido salvar(PedidoDTO pedidoDTO);

}
