package com.ccsw.tutorial_loan.loan;

import com.ccsw.tutorial_loan.client.ClientClient;
import com.ccsw.tutorial_loan.client.model.ClientDto;
import com.ccsw.tutorial_loan.game.GameClient;
import com.ccsw.tutorial_loan.game.model.GameDto;
import com.ccsw.tutorial_loan.loan.model.Loan;
import com.ccsw.tutorial_loan.loan.model.LoanDto;
import com.ccsw.tutorial_loan.loan.model.LoanSearchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Loan", description = "API for Loan")
@RequestMapping(value = "/loan")
@RestController
@CrossOrigin(origins = "*")
public class LoanController {

    @Autowired
    LoanService loanService;

    @Autowired
    GameClient gameClient;

    @Autowired
    ClientClient clientClient;

    @Operation(summary = "Find", description = "Method that return a list of Loans")
    @GetMapping("")
    public List<LoanDto> findAll() {
        List<Loan> loans = this.loanService.findAll();
        List<GameDto> games = gameClient.findAll();
        List<ClientDto> clients = clientClient.findAll();

        return loans.stream().map(loan -> {
            LoanDto loandto = new LoanDto();
            BeanUtils.copyProperties(loan, loandto);
            loandto.setGame(games.stream().filter(game -> game.getId().equals(loan.getIdGame())).findFirst().orElse(null));
            loandto.setClient(clients.stream().filter(client -> client.getId().equals(loan.getIdClient())).findFirst().orElse(null));
            return loandto;
        }).collect(Collectors.toList());
    }

    @Operation(summary = "Find Page", description = "Method that return a Page of Loans")
    @PostMapping

    public Page<LoanDto> findPage(@RequestBody LoanSearchDto dto) {
        List<GameDto> games = gameClient.findAll();
        List<ClientDto> clients = clientClient.findAll();
        Page<Loan> page = this.loanService.findPage(dto);

        return new PageImpl<>(page.getContent().stream().map(loan -> {
            LoanDto loandto = new LoanDto();
            BeanUtils.copyProperties(loan, loandto);
            loandto.setGame(games.stream().filter(game -> game.getId().equals(loan.getIdGame())).findFirst().orElse(null));
            loandto.setClient(clients.stream().filter(client -> client.getId().equals(loan.getIdClient())).findFirst().orElse(null));
            return loandto;
        }).collect(Collectors.toList()), page.getPageable(), page.getTotalElements());
    }

    @Operation(summary = "Save", description = "Method that saves or updates a Loan")
    @PutMapping
    public void save(@PathVariable(name = "id", required = false) Long id, @RequestBody LoanDto dto) {
        this.loanService.save(id, dto);
    }

    @Operation(summary = "Delete", description = "Method that deletes a Loan")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        this.loanService.delete(id);
    }

}
