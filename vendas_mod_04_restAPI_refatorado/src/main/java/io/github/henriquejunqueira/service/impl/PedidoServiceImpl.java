package io.github.henriquejunqueira.service.impl;

import io.github.henriquejunqueira.domain.repository.Pedidos;
import io.github.henriquejunqueira.service.PedidoService;
import org.springframework.stereotype.Service;

@Service
public class PedidoServiceImpl implements PedidoService {

    private Pedidos pedidosRepository;

    public PedidoServiceImpl(Pedidos pedidosRepository) {
        this.pedidosRepository = pedidosRepository;
    }


}
