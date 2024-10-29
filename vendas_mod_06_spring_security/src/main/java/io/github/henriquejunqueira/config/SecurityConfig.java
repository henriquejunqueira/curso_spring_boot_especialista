package io.github.henriquejunqueira.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // metodo que trabalha a autenticação
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    // metodo que trabalha a autorização. Ele pega o que for autenticado e verifica o nível de autorização de acesso
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
    }
}
