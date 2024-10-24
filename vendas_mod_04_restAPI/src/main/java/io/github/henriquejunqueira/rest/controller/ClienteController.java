package io.github.henriquejunqueira.rest.controller;

import io.github.henriquejunqueira.domain.entity.Cliente;
import io.github.henriquejunqueira.domain.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
//@RequestMapping("/api/clientes")
public class ClienteController {

//    @RequestMapping(value = "/hello/{nome}",method = RequestMethod.GET)
//    @RequestMapping(
//            value = {"/hello/{nome}", "/api/hello"}, // posso passar uma lista de url para acessar o mesmo metodo
//            method = RequestMethod.POST,
//            consumes = {"application/json", "application/xml"}, // no corpo da requisição pode vir um json ou um xml
//            produces = {"application/json", "application/xml"} // forma de retornar os objetos como json ou xml
//    )
//    @ResponseBody
//    public String helloCliente(@PathVariable("nome") String nomeCliente){
//    public String helloCliente(@PathVariable("nome") String nomeCliente, @RequestBody Cliente cliente){
//    public Cliente helloCliente(@PathVariable("nome") String nomeCliente, @RequestBody Cliente cliente){
//        return String.format("Hello %s ", nomeCliente);
//    }

    @Autowired
    private Clientes clientes;

    public ClienteController(Clientes clientes) {
        this.clientes = clientes;
    }

    @GetMapping("/api/clientes/{id}") // na GetMapping existe a utilização da RequestMapping
    @ResponseBody // retorna um objeto json no corpo da resposta
    public ResponseEntity getClienteById(@PathVariable Integer id){
        Optional<Cliente> cliente = clientes.findById(id);
        if(cliente.isPresent()){
//            HttpHeaders headers = new HttpHeaders();
//            headers.put("Authorization", "token");
//            ResponseEntity<Cliente> responseEntity = new ResponseEntity<>(cliente.get(), headers, HttpStatus.OK);

            return ResponseEntity.ok(cliente.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/api/clientes")
    @ResponseBody // ResponseBody é a resposta e RequestBody é o pedido por essa resposta
    public ResponseEntity save(@RequestBody Cliente cliente){
        Cliente clienteSalvo = clientes.save(cliente);
        return ResponseEntity.ok(clienteSalvo);
    }

    @DeleteMapping("/api/clientes/{id}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable Integer id){

        Optional<Cliente> cliente = clientes.findById(id);

        if(cliente.isPresent()){
            clientes.delete(cliente.get());
            return ResponseEntity.noContent().build(); // noContent é um status de sucesso que não retorna nada
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/api/clientes/{id}")
    @ResponseBody
    public ResponseEntity update(@PathVariable Integer id, @RequestBody Cliente cliente){
        return clientes
                .findById(id)
                .map(clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    clientes.save(cliente);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/api/clientes")
    public ResponseEntity find(Cliente filtro){
        // Jeito complexo
//        String sql = "SELECT * FROM cliente ";
//
//        if(filtro.getNome() != null){
//            sql += " where nome = :nome ";
//        }
//
//        if(filtro.getCpf() != null){
//            sql += " and cpf = :cpf ";
//        }

        // Jeito com métodos prontos
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); // pesquisa o cliente que contém a string
        Example example = Example.of(filtro, matcher);

        List<Cliente> lista = clientes.findAll(example);

        return ResponseEntity.ok(lista);
    }

}
