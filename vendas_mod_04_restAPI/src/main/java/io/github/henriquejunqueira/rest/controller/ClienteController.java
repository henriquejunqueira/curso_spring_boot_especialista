package io.github.henriquejunqueira.rest.controller;

import io.github.henriquejunqueira.domain.entity.Cliente;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/clientes")
public class ClienteController {

//    @RequestMapping(value = "/hello/{nome}",method = RequestMethod.GET)
    @RequestMapping(
            value = {"/hello/{nome}", "/api/hello"}, // posso passar uma lista de url para acessar o mesmo metodo
            method = RequestMethod.POST,
            consumes = {"application/json", "application/xml"}, // no corpo da requisição pode vir um json ou um xml
            produces = {"application/json", "application/xml"} // forma de retornar os objetos como json ou xml
    )
    @ResponseBody
//    public String helloCliente(@PathVariable("nome") String nomeCliente){
//    public String helloCliente(@PathVariable("nome") String nomeCliente, @RequestBody Cliente cliente){
    public Cliente helloCliente(@PathVariable("nome") String nomeCliente, @RequestBody Cliente cliente){
        return String.format("Hello %s ", nomeCliente);
    }

}
