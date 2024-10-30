package io.github.henriquejunqueira.security.jwt;

import io.github.henriquejunqueira.VendasApplication;
import io.github.henriquejunqueira.domain.entity.Usuario;

// Importa classes necessárias para manipulação de JWT
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

// Importa classes do Spring
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime; // Importa classe para manipulação de data e hora
import java.time.ZoneId; // Importa classe para manipulação de fusos horários
import java.util.Date; // Importa classe para manipulação de datas


@Service // Indica que esta classe é um serviço gerenciado pelo Spring
public class JwtService {

    @Value("${security.jwt.expiracao}")
    private String expiracao; // Tempo de expiração do token em minutos

    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura; // Chave secreta usada para assinar o token

    // Metodo para gerar um token JWT a partir de um usuário
    public String gerarToken(Usuario usuario){
        long expString = Long.valueOf(expiracao); // Converte a expiração para um número long
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString); // Calcula a data de expiração
        Date data = Date.from(dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant()); // Converte para Date

        // Passa mais informações na geração do token
//        HashMap<String, Object> claims = new HashMap<>();
//        claims.put("emaildousuario", "usuario@teste.com");
//        claims.put("roles", "admin");

        // Gera o token JWT com o login do usuário e a data de expiração
        return Jwts
                .builder()
                .setSubject(usuario.getLogin()) // Define o assunto do token como o login do usuário
                .setExpiration(data) // Define a data de expiração do token
//                .setClaims(claims) // Define os dados adicionais
                .signWith(SignatureAlgorithm.HS512, chaveAssinatura) // Assina o token com o algoritmo HS512
                .compact(); // Compacta e retorna o token gerado
    }

    // Metodo privado para obter os claims (dados) a partir do token
    private Claims obterClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parser() // Cria um parser para o JWT
                .setSigningKey(chaveAssinatura) // Define a chave de assinatura
                .parseClaimsJws(token) // Analisa o token e obtém os claims
                .getBody(); // Retorna o corpo dos claims
    }

    // Metodo para verificar se o token é válido
    public boolean tokenValido(String token){
        try{
            Claims claims = obterClaims(token); // Obtém os claims do token
            Date dataExpiracao = claims.getExpiration(); // Obtém a data de expiração
            // Converte para LocalDateTime
            LocalDateTime data = dataExpiracao
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            // Retorna verdadeiro se a data atual não ultrapassa a data de expiração
            return !LocalDateTime.now().isAfter(data);
        }catch(Exception e){
            return false; // Retorna falso se ocorrer qualquer exceção ao validar o token
        }
    }

    // Metodo para obter o login do usuário a partir do token
    public String obterLoginUsuario(String token) throws ExpiredJwtException{
        return (String) obterClaims(token).getSubject(); // Retorna o login armazenado no token
    }

    // Metodo principal para testar a geração e validação do token
    public static void main(String[] args) {
        ConfigurableApplicationContext contexto = SpringApplication.run(VendasApplication.class); // Inicia a aplicação Spring
        JwtService service = contexto.getBean(JwtService.class); // Obtém o bean JwtService do contexto
        Usuario usuario = Usuario.builder().login("fulano").build(); // Cria um usuário com login "fulano"
        String token = service.gerarToken(usuario); // Gera o token para o usuário
        System.out.println(token); // Exibe o token gerado

        boolean isTokenValido = service.tokenValido(token); // Verifica se o token é válido
        System.out.println("O token está válido? " + isTokenValido); // Exibe o resultado da validação

        System.out.println(service.obterLoginUsuario(token)); // Obtém e exibe o login do usuário a partir do token
    }

}
