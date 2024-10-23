package io.github.henriquejunqueira;

import io.github.henriquejunqueira.domain.entity.Cliente;
import io.github.henriquejunqueira.domain.entity.Pedido;
import io.github.henriquejunqueira.domain.repository.Clientes;
import io.github.henriquejunqueira.domain.repository.Pedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class VendasApplication {

    @Bean
    public CommandLineRunner init(@Autowired Clientes clientes, @Autowired Pedidos pedidos){
        return args -> {
//            System.out.println("Salvando clientes");
//            clientes.save(new Cliente("Henrique"));
//            clientes.save(new Cliente("João"));
//            clientes.save(new Cliente("Maria"));

//            List<Cliente> todosClientes = clientes.findAll();
//            todosClientes.forEach(System.out::println);

//            System.out.println("Atualizando clientes");
//            todosClientes.forEach(c -> {
//                c.setNome(c.getNome() + " atualizado.");
//                clientes.save(c);
//            });
//
//            todosClientes = clientes.findAll();
//            todosClientes.forEach(System.out::println);
//
//            System.out.println("Buscando clientes");
//            clientes.findByNomeLike("Mar").forEach(System.out::println);
//
//            System.out.println("Deletando clientes");
//            clientes.findAll().forEach(c -> {
//                clientes.delete(c);
//            });
//
//            todosClientes = clientes.findAll();
//            if(todosClientes.isEmpty()){
//                System.out.println("Nenhum cliente encontrado!");
//            }else{
//                todosClientes.forEach(System.out::println);
//            }

//            boolean existe = clientes.existsByNome("Henrique");
//            System.out.println("Existe um cliente com o nome 'Henrique'? " + existe);

//            List<Cliente> result = clientes.encontrarPorNome("Henrique");
//            result.forEach(System.out::println);

            System.out.println("Salvando clientes");
            Cliente fulano = new Cliente("Fulano");
            clientes.save(fulano);

            Pedido p = new Pedido();
            p.setCliente(fulano);
            p.setDataPedido(LocalDate.now());
            p.setTotal(BigDecimal.valueOf(100));

            pedidos.save(p);

//            Cliente cliente = clientes.findClienteFetchPedidos(fulano.getId());
//            System.out.println(cliente);
//            System.out.println(cliente.getPedidos());

            pedidos.findByCliente(fulano).forEach(System.out::println);

        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
