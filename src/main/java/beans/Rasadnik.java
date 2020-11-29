/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author Bosko
 */
public class Rasadnik {

    private int idR;
    private String vlasnik;
    private String naziv;
    private String mesto;
    private int brojSadnica;
    private int kapacitet;
    private int slobodnoMesta;
    private int voda;
    private double temperatura;
    private int duzina;
    private int sirina;

    public int getIdR() {
        return idR;
    }

    public void setIdR(int idR) {
        this.idR = idR;
    }

    public String getVlasnik() {
        return vlasnik;
    }

    public void setVlasnik(String vlasnik) {
        this.vlasnik = vlasnik;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public int getBrojSadnica() {
        return brojSadnica;
    }

    public void setBrojSadnica(int brojSadnica) {
        this.brojSadnica = brojSadnica;
    }

    public int getKapacitet() {
        return kapacitet;
    }

    public void setKapacitet(int kapacitet) {
        this.kapacitet = kapacitet;
    }

    public int getSlobodnoMesta() {
        return slobodnoMesta;
    }

    public void setSlobodnoMesta(int slobodnoMesta) {
        this.slobodnoMesta = slobodnoMesta;
    }

    public int getVoda() {
        return voda;
    }

    public void setVoda(int voda) {
        this.voda = voda;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public int getDuzina() {
        return duzina;
    }

    public void setDuzina(int duzina) {
        this.duzina = duzina;
    }

    public int getSirina() {
        return sirina;
    }

    public void setSirina(int sirina) {
        this.sirina = sirina;
    }

    private String sirinaFejk;
    private String duzinaFejk;

    public String getSirinaFejk() {
        return sirinaFejk;
    }

    public void setSirinaFejk(String sirinaFejk) {
        this.sirinaFejk = sirinaFejk;
    }

    public String getDuzinaFejk() {
        return duzinaFejk;
    }

    public void setDuzinaFejk(String duzinaFejk) {
        this.duzinaFejk = duzinaFejk;
    }

}
