package io.github.henriquejunqueira.rest.controller;

import io.github.henriquejunqueira.domain.entity.Produto;
import io.github.henriquejunqueira.domain.repository.Produtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private Produtos produtosRepository;

    public ProdutoController(Produtos produtosRepository) {
        this.produtosRepository = produtosRepository;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Produto save(@RequestBody Produto produto){
        return produtosRepository.save(produto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody Produto produto){
        produtosRepository
            .findById(id)
            .map(produtoExistente -> {
                produto.setId(produtoExistente.getId());
                produtosRepository.save(produto);
                return produtoExistente;
            }).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Produto não encontrado"
            ));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Integer id){
        produtosRepository
                .findById(id)
                .map(produto -> {
                    produtosRepository.delete(produto);
                    return produto;
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Produto não encontrado"
                ));
    }

    @GetMapping("/{id}")
    public Produto getProdutoById(@PathVariable Integer id){
        return produtosRepository
            .findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Produto não encontrado"
            ));
    }

    @GetMapping
    public List<Produto> find(Produto filtro){
        ExampleMatcher matcher = ExampleMatcher
            .matching()
            .withIgnoreCase()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);

        return produtosRepository.findAll(example);
    }

}
