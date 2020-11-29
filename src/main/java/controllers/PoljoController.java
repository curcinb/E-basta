/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Poljoprivrednik;
import beans.Porudzbina;
import beans.Preparat;
import beans.Rasadnik;
import beans.Sadnica;
import java.util.ArrayList;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;
import util.dao.KorisnikDAO;
import util.dao.PoljoprivrednikDAO;
import util.dao.PorudzbinaDAO;
import util.dao.PreparatDAO;
import util.dao.SadnicaDAO;

/**
 *
 * @author Bosko
 */
@ManagedBean
@SessionScoped
public class PoljoController {

    private Poljoprivrednik ja = new Poljoprivrednik();
    private String pw1;
    private String pw2;
    private String pw3;
    private String poruka = "";

    public PoljoController() {
        HttpSession session = util.SessionUtils.getSession();
        ja = (Poljoprivrednik) session.getAttribute("poljoprivrednik");
    }

    public Poljoprivrednik getJa() {
        return ja;
    }

    public void setJa(Poljoprivrednik ja) {
        this.ja = ja;
    }

    public String getPw1() {
        return pw1;
    }

    public void setPw1(String pw1) {
        this.pw1 = pw1;
    }

    public String getPw2() {
        return pw2;
    }

    public void setPw2(String pw2) {
        this.pw2 = pw2;
    }

    public String getPw3() {
        return pw3;
    }

    public void setPw3(String pw3) {
        this.pw3 = pw3;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    public String promeniLozinku() {
        poruka = "";
        HttpSession session = util.SessionUtils.getSession();
        Poljoprivrednik p = new Poljoprivrednik();
        p = (Poljoprivrednik) session.getAttribute("poljoprivrednik");
        String pw = p.getPassword();
        String username = p.getUsername();

        if (pw.equals(pw1)) {
            if (pw.equals(pw2) && pw.equals(pw3)) {
                poruka = "Nova lozinka mora da se razlikuje od stare!";
            } else if (!pw2.equals(pw3)) {
                poruka = "Lozinke se ne poklapaju!";
            } else {
                PoljoprivrednikDAO.promeniLozinku(username, pw2);
                KorisnikDAO.promeniLozinku(username, pw2);
                session.invalidate();
                return ("index?faces-redirect=true");
            }
        } else {
            poruka = "Uneli ste pogresnu lozinku!";
        }
        return null;
    }

    public String dodajRasadnik() {
        porukaRasadnik = "";
        String tmpDuz = r.getDuzinaFejk();
        String tmpSir = r.getSirinaFejk();
        r.setDuzina(Integer.parseInt(tmpDuz));
        r.setSirina(Integer.parseInt(tmpSir));

        util.dao.RasadnikDAO.dodajRasadnik(ja, r);

        return "poljoprivrednik?faces-redirect=true";
    }

    private ArrayList<Rasadnik> rasadnici = new ArrayList<>();

    public ArrayList<Rasadnik> getRasadnici() {
        return rasadnici;
    }

    public void setRasadnici(ArrayList<Rasadnik> rasadnici) {
        this.rasadnici = rasadnici;
    }

    public void dohvatiRasadnike() {
        rasadnici = util.dao.PoljoprivrednikDAO.dohvatiRasadnike(ja.getUsername());
    }

    Rasadnik r = new Rasadnik();

    public Rasadnik getR() {
        return r;
    }

    public void setR(Rasadnik r) {
        this.r = r;
    }

    private String porukaRasadnik = "";

    public String getPorukaRasadnik() {
        return porukaRasadnik;
    }

    public void setPorukaRasadnik(String porukaRasadnik) {
        this.porukaRasadnik = porukaRasadnik;
    }

    public String prikazRasadnika(Rasadnik rasadnik) {
        HttpSession session = util.SessionUtils.getSession();
        session.setAttribute("rasadnik", rasadnik);
        return "prikazRasadnika?faces-redirect=true";
    }

    private ArrayList<Sadnica> sveSadnice = new ArrayList<>();

    public ArrayList<Sadnica> getSveSadnice() {
        return sveSadnice;
    }

    public void setSveSadnice(ArrayList<Sadnica> sveSadnice) {
        this.sveSadnice = sveSadnice;
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

    private ArrayList<Preparat> sviPreparati = new ArrayList<>();

    public ArrayList<Preparat> getSviPreparati() {
        return sviPreparati;
    }

    public void setSviPreparati(ArrayList<Preparat> sviPreparati) {
        this.sviPreparati = sviPreparati;
    }

    private ArrayList<Preparat> sviPreparatiFiltrirano = new ArrayList<>();

    public ArrayList<Preparat> getSviPreparatiFiltrirano() {
        return sviPreparatiFiltrirano;
    }

    public void setSviPreparatiFiltrirano(ArrayList<Preparat> sviPreparatiFiltrirano) {
        this.sviPreparatiFiltrirano = sviPreparatiFiltrirano;
    }

    public void dohvatiZaRadnju() {
        sveSadnice = SadnicaDAO.dohvatiSveSadniceProdavnica();
        sviPreparati = PreparatDAO.dohvatiSvePreparateProdavnica();
    }

    public String poruciPreparat(Preparat p) {
        PorudzbinaDAO.novaPorudzbinaPreparat(p, ja.getUsername());
        PreparatDAO.smanjiBrojPreparata(p);
        sviPreparati = PreparatDAO.dohvatiSvePreparateProdavnica();
        dohvatiMojePorudzbine();
        return "prodavnica?faces-redirect=true";
    }

    public String poruciSadnicu(Sadnica s) {
        PorudzbinaDAO.novaPorudzbinaSadnica(s, ja.getUsername());
        SadnicaDAO.smanjiBrojSadnica(s);
        sveSadnice = SadnicaDAO.dohvatiSveSadniceProdavnica();
        dohvatiMojePorudzbine();
        return "prodavnica?faces-redirect=true";
    }

    private ArrayList<Porudzbina> mojePorudzbine = new ArrayList<>();

    public ArrayList<Porudzbina> getMojePorudzbine() {
        return mojePorudzbine;
    }

    public void setMojePorudzbine(ArrayList<Porudzbina> mojePorudzbine) {
        this.mojePorudzbine = mojePorudzbine;
    }

    public void dohvatiMojePorudzbine() {
        mojePorudzbine = util.dao.PorudzbinaDAO.dohvatiMojePorudzbine(ja.getUsername());
    }

    public void otkaziPorudzbinu(int idP) {
        PorudzbinaDAO.obrisiPorudzbinu(idP);
        dohvatiMojePorudzbine();
    }

    public String lozinkaRedirect() {
        return "promenaLozinkePoljo?faces-redirect=true";
    }

    public String pocetnaRedirect() {
        return "poljoprivrednik?faces-redirect=true";
    }

    public String rasadnikRedirect() {
        return "noviRasadnik?faces-redirect=true";
    }

    public String prodavnicaRedirect() {
        return "prodavnica?faces-redirect=true";
    }
    
    public String mojePorudzbineRedirect(){
        return "mojePorudzbine?faces-redirect=true";
    }
}
