package io.github.henriquejunqueira.service.impl;

import io.github.henriquejunqueira.domain.entity.Cliente;
import io.github.henriquejunqueira.domain.entity.ItemPedido;
import io.github.henriquejunqueira.domain.entity.Pedido;
import io.github.henriquejunqueira.domain.entity.Produto;
import io.github.henriquejunqueira.domain.enums.StatusPedido;
import io.github.henriquejunqueira.domain.repository.Clientes;
import io.github.henriquejunqueira.domain.repository.ItemsPedido;
import io.github.henriquejunqueira.domain.repository.Pedidos;
import io.github.henriquejunqueira.domain.repository.Produtos;
import io.github.henriquejunqueira.exception.PedidoNaoEncontradoException;
import io.github.henriquejunqueira.exception.RegraNegocioException;
import io.github.henriquejunqueira.rest.dto.ItemPedidoDTO;
import io.github.henriquejunqueira.rest.dto.PedidoDTO;
import io.github.henriquejunqueira.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // gera um construtor com todos os argumentos obrigatórios (necessário usar final)
public class PedidoServiceImpl implements PedidoService {

    private final Pedidos pedidosRepository;
    private final Clientes clientesRepository;
    private final Produtos produtosRepository;
    private final ItemsPedido itemsPedidoRepository;

    @Override
    @Transactional // garante que seja salvo no bd apenas se todos os dados passados forem válidos
    public Pedido salvar(PedidoDTO pedidoDTO) {
        Integer idCliente = pedidoDTO.getCliente();
        Cliente cliente = clientesRepository
            .findById(idCliente)
            .orElseThrow(
                () -> new RegraNegocioException("Código de cliente inválido!")
            );

        Pedido pedido = new Pedido();
        pedido.setTotal(pedidoDTO.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemsPedido = converterItems(pedido, pedidoDTO.getItems());
        pedidosRepository.save(pedido);
        itemsPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);

        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidosRepository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        pedidosRepository
            .findById(id)
            .map(pedido -> {
                pedido.setStatus(statusPedido);
                return pedidosRepository.save(pedido);
            }).orElseThrow(
                () -> new PedidoNaoEncontradoException()
            );
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items){
        if(items.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar um pedido sem items.");
        }

        return items
            .stream()
            .map(itemPedidoDTO -> {
                Integer idProduto = itemPedidoDTO.getProduto();
                Produto produto = produtosRepository
                    .findById(idProduto)
                    .orElseThrow(
                            () -> new RegraNegocioException("Código de produto inválido: " + idProduto)
                    );

                ItemPedido itemPedido = new ItemPedido();
                itemPedido.setQuantidade(itemPedidoDTO.getQuantidade());
                itemPedido.setPedido(pedido);
                itemPedido.setProduto(produto);

                return itemPedido;

            }).collect(Collectors.toList());
    }
}
