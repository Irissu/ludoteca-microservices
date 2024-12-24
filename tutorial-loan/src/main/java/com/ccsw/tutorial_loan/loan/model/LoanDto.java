package com.ccsw.tutorial_loan.loan.model;

import com.ccsw.tutorial_loan.client.model.ClientDto;
import com.ccsw.tutorial_loan.game.model.GameDto;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) para la entidad Loan.
 */
public class LoanDto {
    /**
     * Identificador único del préstamo.
     */
    private Long id;

    /**
     * Juego asociado al préstamo.
     * Este campo es obligatorio.
     */
    @NotNull(message = "Game is required")
    private GameDto game;

    /**
     * Cliente asociado al préstamo.
     * Este campo es obligatorio.
     */
    @NotNull(message = "Client is required")
    private ClientDto client;

    /**
     * Fecha de inicio del préstamo.
     * Este campo es obligatorio.
     */
    @NotNull(message = "Loan date is required")
    private LocalDate loanDate;

    /**
     * Fecha de devolución del préstamo.
     * Este campo es obligatorio.
     */
    @NotNull(message = "Return date is required")
    private LocalDate returnDate;

    /**
     * Obtiene el identificador del préstamo.
     *
     * @return el identificador del préstamo.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador del préstamo.
     *
     * @param id el identificador del préstamo.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el juego asociado al préstamo.
     *
     * @return el juego asociado al préstamo.
     */
    public GameDto getGame() {
        return game;
    }

    /**
     * Establece el juego asociado al préstamo.
     *
     * @param game el juego asociado al préstamo.
     */
    public void setGame(GameDto game) {
        this.game = game;
    }

    /**
     * Obtiene el cliente asociado al préstamo.
     *
     * @return el cliente asociado al préstamo.
     */
    public ClientDto getClient() {
        return client;
    }

    /**
     * Establece el cliente asociado al préstamo.
     *
     * @param client el cliente asociado al préstamo.
     */
    public void setClient(ClientDto client) {
        this.client = client;
    }

    /**
     * Obtiene la fecha de inicio del préstamo.
     *
     * @return la fecha de inicio del préstamo.
     */
    public LocalDate getLoanDate() {
        return loanDate;
    }

    /**
     * Establece la fecha de inicio del préstamo.
     *
     * @param loanDate la fecha de inicio del préstamo.
     */
    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    /**
     * Obtiene la fecha de devolución del préstamo.
     *
     * @return la fecha de devolución del préstamo.
     */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /**
     * Establece la fecha de devolución del préstamo.
     *
     * @param returnDate la fecha de devolución del préstamo.
     */
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}