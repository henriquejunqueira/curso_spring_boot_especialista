package io.github.henriquejunqueira.security.jwt;

// Importa a implementação do serviço de usuário
import io.github.henriquejunqueira.service.impl.UsuarioServiceImpl;

// Importa classes necessárias para autenticação e filtragem
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

// Importa classes para manipulação de requisições e respostas HTTP
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Classe que implementa um filtro para autenticação via JWT
public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtService jwtService; // Serviço para manipulação de JWT
    private UsuarioServiceImpl usuarioService; // Serviço para manipulação de usuários

    // Construtor que recebe os serviços necessários para a autenticação
    public JwtAuthFilter(JwtService jwtService, UsuarioServiceImpl usuarioService) {
        this.jwtService = jwtService; // Inicializa o serviço de JWT
        this.usuarioService = usuarioService; // Inicializa o serviço de usuários
    }

    // Metodo que é chamado para processar cada requisição HTTP
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // Obtém o cabeçalho de autorização da requisição
        String authorization = httpServletRequest.getHeader("Authorization");

        // Verifica se o cabeçalho contém um token Bearer
        if(authorization != null && authorization.startsWith("Bearer")){
            String token = authorization.split(" ")[1]; // Extrai o token do cabeçalho
            boolean isValid = jwtService.tokenValido(token); // Verifica se o token é válido

            // Se o token for válido, continua o processo de autenticação
            if(isValid){
                String loginUsuario = jwtService.obterLoginUsuario(token); // Obtém o login do usuário a partir do token
                UserDetails usuario = usuarioService.loadUserByUsername(loginUsuario); // Carrega os detalhes do usuário

                // Cria um objeto de autenticação com as informações do usuário
                UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

                // Adiciona detalhes adicionais da autenticação, como informações da requisição
                user.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                // Armazena a autenticação no contexto de segurança
                SecurityContextHolder.getContext().setAuthentication(user);
            }
        }

        // Continua o processamento da cadeia de filtros
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
