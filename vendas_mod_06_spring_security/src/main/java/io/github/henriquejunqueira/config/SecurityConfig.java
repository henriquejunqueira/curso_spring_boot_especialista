package io.github.henriquejunqueira.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // essa interface server pra criptografar e descriptografar a senha de um usuário
    @Bean
    public PasswordEncoder passwordEncoder(){
//        PasswordEncoder passwordEncoder = new PasswordEncoder() {
//            // nesse metodo é criado o tipo de criptografia
//            @Override
//            public String encode(CharSequence charSequence) {
//                return charSequence + "321"; // aqui posso concatenar a senha do usuário com 321 por exemplo
//            }
//
//            // o metodo matches recebe a senha do usuário (charSequence) e a senha criptografada (s)
//            @Override
//            public boolean matches(CharSequence charSequence, String s) {
//                return (charSequence + "321").equals(s); // compara a senha do usuário com a senha criptografada
//            }
//        }

        return new BCryptPasswordEncoder(); // sempre que o bcrypt é chamado ele gera uma hash diferente
    }

    // metodo que trabalha a autenticação
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // configuração (temporário) de autenticação em memória
        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder()) // define o password encoder
                .withUser("fulano") // passa um usuário em memória
                .password(passwordEncoder().encode("123")) // senha do usuário em memória criptografada
                .roles("USER"); // define o perfil de usuário (nível de acesso)
    }

    // metodo que trabalha a autorização. Ele pega o que for autenticado e verifica o nível de autorização de acesso
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
    }
}
