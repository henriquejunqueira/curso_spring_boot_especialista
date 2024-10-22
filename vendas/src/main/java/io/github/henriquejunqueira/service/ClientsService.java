package io.github.henriquejunqueira.service;

import io.github.henriquejunqueira.model.Client;
import io.github.henriquejunqueira.repository.ClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientsService { // essa classe faz as operações de serviço, porém através do repository

    private ClientsRepository repository;

    @Autowired
    public ClientsService(ClientsRepository repository){
        this.repository = repository;
    }

    public void salvarCliente(Client cliente){

        validarCliente(cliente);

        // ClientsRepository clientsRepository = new ClientsRepository();
        // clientsRepository.salvar(cliente);

        this.repository.salvar(cliente);

    }

    public void validarCliente(Client cliente){

        // aplica validações

    }

}
