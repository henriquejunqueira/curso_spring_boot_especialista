package io.github.henriquejunqueira;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ComponentScan(basePackages = {
        "io.github.henriquejunqueira.repository",
        "io.github.henriquejunqueira.service",
        "com.umaoutrabiblioteca.projeto"
})
@RestController
public class VendasApplication {

    @Autowired
    @Qualifier("applicationName")
    private String applicationName;

    @Autowired
    @Qualifier("outraConfiguracao")
    private String outraConfiguracao;

    @GetMapping("/hello")
    public String helloWorld(){
        return applicationName;
    }

    @GetMapping("/teste")
    public String teste(){
        return outraConfiguracao;
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
