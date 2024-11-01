package io.github.henriquejunqueira.service.impl;

import io.github.henriquejunqueira.domain.entity.Usuario;
import io.github.henriquejunqueira.domain.repository.UsuarioRepository;
import io.github.henriquejunqueira.exception.SenhaInvalidaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.StringJoiner;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public UserDetails autenticar(Usuario usuario){
        UserDetails userDetails = loadUserByUsername(usuario.getLogin());
        boolean senhasBatem = passwordEncoder.matches(usuario.getSenha(), userDetails.getPassword());

        if(senhasBatem){
            return userDetails;
        }

        throw new SenhaInvalidaException();

    }

    // carrega os usuários do bd
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        if(!username.equals("cicrano")){
//            throw new UsernameNotFoundException("Usuário não encontrado na base de dados.");
//        }
//
//        return User
//                .builder()
//                .username("cicrano")
//                .password(passwordEncoder.encode("123"))
//                .roles("USER", "ADMIN")
//                .build();

        Usuario usuario = usuarioRepository
                .findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados."));

        String[] roles = usuario.isAdmin() ? new String[]{"ADMIN", "USER"} : new String[]{"USER"};

        return User
                .builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(roles)
                .build();
    }
}
