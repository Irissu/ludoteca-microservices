package com.ccsw.tutorial_client.client;

import com.ccsw.tutorial_client.client.model.Client;
import com.ccsw.tutorial_client.client.model.ClientDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "client", description = "API for Client")
@RequestMapping(value = "/client")
@RestController
@CrossOrigin(origins = "*")

public class ClientController {
    @Autowired
    ClientService clientService;

    @Autowired
    ModelMapper mapper;

    @Operation(summary = "Find", description = "Method that return all Clients")
    @GetMapping(path = "")
    public List<ClientDto> findAll() {
        List<Client> clients = this.clientService.findAll();
        return clients.stream().map(e -> mapper.map(e, ClientDto.class)).collect(Collectors.toList());
    }

    @Operation(summary = "Save or Update", description = "Method that saves or updates a client")
    @PutMapping(path = { "", "/{id}" })
    public void save(@PathVariable(name = "id", required = false) Long id, @RequestBody ClientDto dto) {
        this.clientService.save(id, dto);
    }

    @Operation(summary = "Delete", description = "Method that deletes a client")
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        this.clientService.delete(id);
    }
}
