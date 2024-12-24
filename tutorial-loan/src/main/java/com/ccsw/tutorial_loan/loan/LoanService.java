package com.ccsw.tutorial_loan.loan;

import com.ccsw.tutorial_loan.loan.model.Loan;
import com.ccsw.tutorial_loan.loan.model.LoanDto;
import com.ccsw.tutorial_loan.loan.model.LoanSearchDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LoanService {
    /**
     * Recupera un préstamo a través de su ID.
     *
     * @param id PK de la entidad
     * @return el préstamo correspondiente al ID
     */
    Loan get(Long id);

    /**
     * Encuentra una página de préstamos basada en los criterios de búsqueda.
     *
     * @param dto objeto que contiene los criterios de búsqueda y paginación
     * @return una página de préstamos que coinciden con los criterios de búsqueda
     */

    Page<Loan> findPage(LoanSearchDto dto);

    /**
     * Guarda o actualiza un préstamo.
     *
     * @param id  PK de la entidad, puede ser null para crear un nuevo préstamo
     * @param dto objeto que contiene los datos del préstamo a guardar
     */
    void save(Long id, LoanDto dto);

    /**
     * Elimina un préstamo a través de su ID.
     *
     * @param id PK de la entidad
     */
    void delete(Long id);

    /**
     * Recupera todos los préstamos.
     *
     * @return una lista de todos los préstamos
     */
    List<Loan> findAll();
}
