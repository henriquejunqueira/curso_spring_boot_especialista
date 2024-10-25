package io.github.henriquejunqueira.rest.controller;

import io.github.henriquejunqueira.domain.entity.ItemPedido;
import io.github.henriquejunqueira.domain.entity.Pedido;
import io.github.henriquejunqueira.domain.enums.StatusPedido;
import io.github.henriquejunqueira.rest.dto.AtualizacaoStatusPedidoDTO;
import io.github.henriquejunqueira.rest.dto.InformacaoItemPedidoDTO;
import io.github.henriquejunqueira.rest.dto.InformacoesPedidoDTO;
import io.github.henriquejunqueira.rest.dto.PedidoDTO;
import io.github.henriquejunqueira.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody PedidoDTO pedidoDTO){
        Pedido pedido = pedidoService.salvar(pedidoDTO);
        return pedido.getId();
    }

    @GetMapping("/{id}")
    public InformacoesPedidoDTO getById(@PathVariable Integer id){
        return pedidoService
            .obterPedidoCompleto(id)
            .map(p -> converter(p))
            .orElseThrow(
                () -> new ResponseStatusException(
                    NOT_FOUND,
                    "Pedido não encontrado!")
            );
    }

    @PatchMapping("/{id}") // atualiza parcialmente
    @ResponseStatus(NO_CONTENT)
    public void updateStatus(@PathVariable Integer id, @RequestBody AtualizacaoStatusPedidoDTO atualizacaoStatusPedidoDTO){
        String novoStatus = atualizacaoStatusPedidoDTO.getNovoStatus();
        pedidoService.atualizaStatus(id, StatusPedido.valueOf(novoStatus));
    }

    private InformacoesPedidoDTO converter(Pedido pedido){
        return InformacoesPedidoDTO
            .builder()
            .codigo(pedido.getId())
            .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
            .cpf(pedido.getCliente().getCpf())
            .nomeCliente(pedido.getCliente().getNome())
            .total(pedido.getTotal())
            .status(pedido.getStatus().name())
            .items(converter(pedido.getItens()))
            .build();
    }

    private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens){
        if(CollectionUtils.isEmpty(itens)){
            return Collections.emptyList();
        }

        return itens.stream().map(
            item -> InformacaoItemPedidoDTO
                .builder().descricaoProduto(item.getProduto().getDescricao())
                .precoUnitario(item.getProduto().getPreco())
                .quantidade(item.getQuantidade())
                .build()
        ).collect(Collectors.toList());
    }

}
