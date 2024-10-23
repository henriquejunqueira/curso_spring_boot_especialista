package io.github.henriquejunqueira;

import io.github.henriquejunqueira.domain.entity.Cliente;
import io.github.henriquejunqueira.domain.repositorio.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class VendasApplication {

    @Bean
    public CommandLineRunner init(@Autowired Clientes clientes){
        return args -> {
            System.out.println("Salvando clientes");
            // clientes.save(new Cliente("Henrique"));
            clientes.save(new Cliente("João"));
            clientes.save(new Cliente("Maria"));

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

            boolean existe = clientes.existsByNome("Henrique");
            System.out.println("Existe um cliente com o nome 'Henrique'? " + existe);

        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
