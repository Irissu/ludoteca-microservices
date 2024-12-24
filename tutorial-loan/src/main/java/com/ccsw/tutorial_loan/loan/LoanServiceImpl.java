package com.ccsw.tutorial_loan.loan;

import com.ccsw.tutorial_loan.common.criteria.SearchCriteria;
import com.ccsw.tutorial_loan.exceptions.InvalidReturnDateException;
import com.ccsw.tutorial_loan.exceptions.LoanNotValidException;
import com.ccsw.tutorial_loan.loan.model.Loan;
import com.ccsw.tutorial_loan.loan.model.LoanDto;
import com.ccsw.tutorial_loan.loan.model.LoanSearchDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Override
    public Loan get(Long id) {
        return this.loanRepository.findById(id).orElse(null);
    }

    @Override
    public List<Loan> findAll() {
        return (List<Loan>) this.loanRepository.findAll();
    }

    @Override
    public Page<Loan> findPage(LoanSearchDto dto) {
        LocalDate date = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (dto.getDate() != null) {
            date = LocalDate.parse(dto.getDate(), formatter);
        }
        // Especificaciones para filtros
        Specification<Loan> spec = Specification.where(null);

        if (dto.getIdGame() != null) {
            LoanSpecification gameSpec = new LoanSpecification(new SearchCriteria("idGame", ":", dto.getIdGame()));
            spec = spec.and(gameSpec);
        }

        if (dto.getIdClient() != null) {
            LoanSpecification clientSpec = new LoanSpecification(new SearchCriteria("idClient", ":", dto.getIdClient()));
            spec = spec.and(clientSpec);
        }

        if (date != null) {
            LoanSpecification dateGreaterThanSpec = new LoanSpecification(new SearchCriteria("returnDate", ">=", date));
            LoanSpecification dateLessThanSpec = new LoanSpecification(new SearchCriteria("loanDate", "<=", date));
            spec = spec.and(dateGreaterThanSpec).and(dateLessThanSpec);
        }

        return this.loanRepository.findAll(spec, dto.getPageable().getPageable());
    }

    @Override
    public void save(Long id, LoanDto dto) {

        if (dto.getLoanDate() != null && dto.getReturnDate() != null) {
            if (dto.getLoanDate().isAfter(dto.getReturnDate())) {
                throw new InvalidReturnDateException("Loan date must be before return date");
            }
        }

        if (dto.getLoanDate() != null && dto.getReturnDate() != null) {
            if (dto.getLoanDate().plusDays(14).isBefore(dto.getReturnDate())) {
                throw new InvalidReturnDateException("Loan can't be more than 14 days");
            }
        }

        LoanSpecification clientSpec = new LoanSpecification(new SearchCriteria("idClient", ":", dto.getClient().getId()));
        LoanSpecification activeLoanSpec = new LoanSpecification(new SearchCriteria("loanDate", "<=", dto.getReturnDate()));
        LoanSpecification returnLoanSpec = new LoanSpecification(new SearchCriteria("returnDate", ">=", dto.getLoanDate()));

        Specification<Loan> clientLoansSpec = Specification.where(clientSpec).and(activeLoanSpec).and(returnLoanSpec);

        long activeLoans = this.loanRepository.count(clientLoansSpec);
        if (activeLoans >= 2) {
            throw new LoanNotValidException("Client can't loan more than 2 games in this date range");
        }

        LoanSpecification gameSpec = new LoanSpecification(new SearchCriteria("idGame", ":", dto.getGame().getId()));
        Specification<Loan> gameReservationSpec = Specification.where(gameSpec).and(activeLoanSpec).and(returnLoanSpec);

        boolean gameAlreadyReserved = this.loanRepository.exists(gameReservationSpec);
        if (gameAlreadyReserved) {
            throw new LoanNotValidException("Game already loaned in this date range");
        }

        Loan loan;

        loan = (id == null) ? new Loan() : this.get(id);

        BeanUtils.copyProperties(dto, loan, "id", "client", "game");

        if (dto.getGame() != null && dto.getGame().getId() != null) {
            loan.setIdGame(dto.getGame().getId());
        } else {
            throw new RuntimeException("Game is required");
        }

        if (dto.getClient() != null && dto.getClient().getId() != null) {
            loan.setIdClient(dto.getClient().getId());
        } else {
            throw new RuntimeException("Client is required");
        }

        this.loanRepository.save(loan);
    }

    @Override
    public void delete(Long id) {
        this.loanRepository.deleteById(id);

    }

}
