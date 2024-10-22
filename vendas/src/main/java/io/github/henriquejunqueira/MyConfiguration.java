package io.github.henriquejunqueira;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfiguration {

    @Bean(name = "applicationName")
    public String applicationName(){
        return "Sistema de Vendas";
    }

    @Bean(name = "outraConfiguracao")
    public String outraConfiguracao(){
        return "<h1>Esse é o meu título h1</h1>";
    }

}
