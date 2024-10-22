package io.github.henriquejunqueira;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
//@Profile("development") // define que essa classe de configuração será acessível apenas no perfil development
@Development // anotação criada por mim e que faz o mesmo que a anotação @Profile
public class MyConfiguration {

//    @Bean(name = "applicationName")
//    public String applicationName(){
//        return "Sistema de Vendas";
//    }

//    @Bean(name = "outraConfiguracao")
//    public String outraConfiguracao(){
//        return "<h1>Esse é o meu título h1</h1>";
//    }

    @Bean
    public CommandLineRunner executar(){
        return args -> {
            System.out.println("RODANDO A CONFIGURAÇÃO DE DESENVOLVIMENTO!!!");
        };
    }

}
