package br.com.edras.picpaysimplificado.domain;

import java.util.Date;

public class Transaction {

    private Double value;
    private User payer;
    private User payee;
    private Date date;

    public Transaction() {}

    public Transaction(Double value, User payer, User payee, Date date) {
        this.value = value;
        this.payer = payer;
        this.payee = payee;
        this.date = date;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public User getPayer() {
        return payer;
    }

    public void setPayer(User payer) {
        this.payer = payer;
    }

    public User getPayee() {
        return payee;
    }

    public void setPayee(User payee) {
        this.payee = payee;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
