/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Preparat;
import beans.Rasadnik;
import beans.Sadnica;
import java.util.ArrayList;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;
import util.dao.RasadnikDAO;
import util.dao.SadnicaDAO;

/**
 *
 * @author Bosko
 */
@ManagedBean
@SessionScoped
public class RasadnikController {

    private Rasadnik rr = new Rasadnik();

    public RasadnikController() {
        HttpSession session = util.SessionUtils.getSession();
        rr = (Rasadnik) session.getAttribute("rasadnik");

    }

    public Rasadnik getRr() {
        return rr;
    }

    public void setRr(Rasadnik rr) {
        this.rr = rr;
    }

    private ArrayList<Sadnica> sadnice = new ArrayList<>();

    public ArrayList<Sadnica> getSadnice() {
        return sadnice;
    }

    public void setSadnice(ArrayList<Sadnica> sadnice) {
        this.sadnice = sadnice;
    }

    public void dohvatiRasadnik() {
        HttpSession session = util.SessionUtils.getSession();
        rr = (Rasadnik) session.getAttribute("rasadnik");
        sadnice = util.dao.RasadnikDAO.dohvatiSadnice(rr);
        System.out.println("Kapacitet:" + rr.getKapacitet());
        System.out.println("Broj sadnica:" + rr.getBrojSadnica());
        int prazno = rr.getKapacitet() - rr.getBrojSadnica();

        for (int i = 0; i < prazno; i++) {
            Sadnica s = new Sadnica();
            s.setNaziv("Prazno mesto");
            s.setPreduzece("");
            s.setNapredak(0);
            s.setIdR(rr.getIdR());
            sadnice.add(s);
        }
    }

    private ArrayList<Preparat> preparatiMagacin = new ArrayList<>();

    public ArrayList<Preparat> getPreparatiMagacin() {
        return preparatiMagacin;
    }

    public void setPreparatiMagacin(ArrayList<Preparat> preparatiMagacin) {
        this.preparatiMagacin = preparatiMagacin;
    }

    private ArrayList<Sadnica> sadniceMagacin = new ArrayList<>();

    public ArrayList<Sadnica> getSadniceMagacin() {
        return sadniceMagacin;
    }

    public void setSadniceMagacin(ArrayList<Sadnica> sadniceMagacin) {
        this.sadniceMagacin = sadniceMagacin;
    }

    public void dohvatiSadniceMagacin() {
        HttpSession session = util.SessionUtils.getSession();
        rr = (Rasadnik) session.getAttribute("rasadnik");
        sadniceMagacin = util.dao.RasadnikDAO.dohvatiSadniceMagacin(rr);
    }

    public void dohvatiPreparateMagacin() {
        HttpSession session = util.SessionUtils.getSession();
        rr = (Rasadnik) session.getAttribute("rasadnik");
        preparatiMagacin = util.dao.RasadnikDAO.dohvatiPreparateMagacin(rr);
    }

    public String pocetnaRedirect() {
        return "poljoprivrednik?faces-redirect=true";
    }

    public void promenaVode(int val) {
        util.dao.RasadnikDAO.promenaVode(val, rr);
        rr.setVoda(rr.getVoda() + val);
    }

    public void promenaTemperature(double val) {
        util.dao.RasadnikDAO.promenaTemperature(val, rr);
        rr.setTemperatura((rr.getTemperatura() + val));
    }

    public void ukloniSadnicu(Sadnica s) {
        HttpSession session = util.SessionUtils.getSession();
        rr = (Rasadnik) session.getAttribute("rasadnik");
        sadnice.remove(s);
        util.dao.RasadnikDAO.ukloniSadnicu(s.getIdS());
        util.dao.RasadnikDAO.smanjiBrojSadnica(rr);
        dohvatiRasadnik();
        rr.setBrojSadnica(rr.getBrojSadnica() - 1);
        session.setAttribute("rasadnik", rr);
    }

    public String dodajSadnicu(String sadnicaNaziv) {
        RasadnikDAO.smanjiBrojSadnicaMagacin(sadnicaNaziv);
        RasadnikDAO.povecajBrojSadnicaRasadnik(rr.getIdR());
        dohvatiSadniceMagacin();
        SadnicaDAO.dodajSadnicuURasadnik(sadnicaNaziv, rr.getIdR());
        dohvatiRasadnik();
        return "prikazRasadnika?faces-redirect=true";
    }

    private String nazivDodavanje;

    public String getNazivDodavanje() {
        return nazivDodavanje;
    }

    public void setNazivDodavanje(String nazivDodavanje) {
        this.nazivDodavanje = nazivDodavanje;
    }

    private ArrayList<Sadnica> sveSadniceFiltrirano = new ArrayList<>();

    public ArrayList<Sadnica> getSveSadniceFiltrirano() {
        return sveSadniceFiltrirano;
    }

    public void setSveSadniceFiltrirano(ArrayList<Sadnica> sveSadniceFiltrirano) {
        this.sveSadniceFiltrirano = sveSadniceFiltrirano;
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }
        int filterInt = getInteger(filterText);

        if (value instanceof Sadnica) {
            Sadnica sad = (Sadnica) value;
            return sad.getNaziv().toLowerCase().contains(filterText)
                    || sad.getPreduzece().toLowerCase().contains(filterText)
                    || sad.getCena() < filterInt
                    || sad.getKolicina() < filterInt;
        } else {
            Preparat prep = (Preparat) value;
            return prep.getNaziv().toLowerCase().contains(filterText)
                    || prep.getPreduzece().toLowerCase().contains(filterText)
                    || prep.getCena() < filterInt
                    || prep.getKolicina() < filterInt;
        }
    }

    private int getInteger(String string) {
        try {
            return Integer.valueOf(string);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private ArrayList<Preparat> sviPreparatiFiltrirano = new ArrayList<>();

    public ArrayList<Preparat> getSviPreparatiFiltrirano() {
        return sviPreparatiFiltrirano;
    }

    public void setSviPreparatiFiltrirano(ArrayList<Preparat> sviPreparatiFiltrirano) {
        this.sviPreparatiFiltrirano = sviPreparatiFiltrirano;
    }

    public void dekrementiraj() {
        RasadnikDAO.apdejtujSveRasadnike();
        rr.setTemperatura((rr.getTemperatura() - 0.5));
        rr.setVoda(rr.getVoda() - 1);
    }

    public void dekrementirajSve() {
        RasadnikDAO.apdejtujSveRasadnike();
    }

}
