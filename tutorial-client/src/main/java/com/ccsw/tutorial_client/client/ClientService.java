package com.ccsw.tutorial_client.client;

import com.ccsw.tutorial_client.client.model.Client;
import com.ccsw.tutorial_client.client.model.ClientDto;

import java.util.List;

public interface ClientService {

    Client get(Long id);

    List<Client> findAll();

    void save(Long id, ClientDto dto);

    void delete(Long id);
}
