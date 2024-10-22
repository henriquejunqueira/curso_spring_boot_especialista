package io.github.henriquejunqueira.repository;

import io.github.henriquejunqueira.model.Client;
import org.springframework.stereotype.Repository;

@Repository
public class ClientsRepository { // essa classe acessa diretamente o bd
    public void salvar(Client cliente) {
        // acessa a base e salva o cliente
    }

}
