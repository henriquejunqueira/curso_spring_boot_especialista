package io.github.henriquejunqueira.rest.controller;

import io.github.henriquejunqueira.domain.entity.Cliente;
import io.github.henriquejunqueira.domain.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

}
