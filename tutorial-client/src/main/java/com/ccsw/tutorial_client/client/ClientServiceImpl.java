package com.ccsw.tutorial_client.client;

import com.ccsw.tutorial_client.client.model.Client;
import com.ccsw.tutorial_client.client.model.ClientDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public Client get(Long id) {
        return this.clientRepository.findById(id).orElse(null);
    }

    @Override
    public List<Client> findAll() {
        return (List<Client>) this.clientRepository.findAll();
    }

    @Override
    public void save(Long id, ClientDto dto) {
        Client clientNameDetails = this.clientRepository.findByName(dto.getName());
        if (clientNameDetails != null) {
            throw new RuntimeException();
        }

        Client client;
        client = (id == null) ? new Client() : this.get(id);
        client.setName(dto.getName());
        this.clientRepository.save(client);
    }

    @Override
    public void delete(Long id) {
        this.clientRepository.deleteById(id);
    }
}
