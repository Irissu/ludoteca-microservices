package com.ccsw.tutorial_loan.loan.model;

import com.ccsw.tutorial_loan.common.pagination.PageableRequest;

public class LoanSearchDto {
    private PageableRequest pageable;
    private Long idGame;
    private Long idClient;
    private String date;
    private String[] dates;

    public PageableRequest getPageable() {
        return pageable;
    }

    public void setPageable(PageableRequest pageable) {
        this.pageable = pageable;
    }

    public Long getIdGame() {
        return idGame;
    }

    public void setIdGame(Long idGame) {
        this.idGame = idGame;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String[] getDates() {
        return dates;
    }

    public void setDates(String[] dates) {
        this.dates = dates;
    }

}
