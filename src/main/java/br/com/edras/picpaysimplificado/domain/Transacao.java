package br.com.edras.picpaysimplificado.domain;

import java.util.Date;

public class Transacao {

    private Double value;
    private Usuario payer;
    private Usuario payee;
    private Date date;

    public Transacao() {}

    public Transacao(Double value, Usuario payer, Usuario payee, Date date) {
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

    public Usuario getPayer() {
        return payer;
    }

    public void setPayer(Usuario payer) {
        this.payer = payer;
    }

    public Usuario getPayee() {
        return payee;
    }

    public void setPayee(Usuario payee) {
        this.payee = payee;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
