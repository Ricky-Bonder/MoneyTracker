package it.uninsubria.pdm.money;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class Movimento {

    private String date;
    private double amount;
    private String u;

    private String id_movimento;
    private String causale;
    private String macroarea;

    private String realDate;

    public Movimento() {
    }

    Movimento(String idMov , String dateMovimento, String realDate, double amountMoney, String user, String caus, String area) {
        this.id_movimento = idMov ;
        this.date = dateMovimento;
        this.realDate = realDate;
        this.amount = amountMoney;
        this.u = user;
        this.causale = caus;
        this.macroarea = area;

    }

    // GETTER E SETTER

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRealDate() { return realDate; }

    public void setRealDate(String realDate) {
        this.realDate = realDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getU() {
        return u;
    }

    public void setU(String u) { this.u = u; }

    public void setId_movimento(String id_movimento) {
        this.id_movimento = id_movimento;
    }

    public String getMacroarea() {
        return macroarea;
    }

    public void setMacroarea(String macroarea) {
        this.macroarea = macroarea;
    }

    public String getId_movimento() {
        return id_movimento;
    }

    public String getCausale() {
        return causale;
    }

    public void setCausale(String causale) {
        this.causale = causale;
    }
}
