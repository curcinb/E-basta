/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Poljoprivrednik;
import beans.Porudzbina;
import beans.Preduzece;
import beans.Preparat;
import beans.Sadnica;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.event.RowEditEvent;
import util.dao.KorisnikDAO;
import util.dao.PoljoprivrednikDAO;
import util.dao.PreduzeceDAO;
import util.dao.RasadnikDAO;

/**
 *
 * @author Bosko
 */
@ManagedBean
@SessionScoped
public class PreduController {

    Preduzece pr = new Preduzece();
    private String pw1;
    private String pw2;
    private String pw3;
    private String poruka = "";

    public PreduController() {
        HttpSession session = util.SessionUtils.getSession();
        pr = (Preduzece) session.getAttribute("preduzece");
        kuriri[0] = "Pera";
        kuriri[1] = "Zika";
        kuriri[2] = "Mika";
        kuriri[3] = "Joca";
        kuriri[4] = "Paja";
        brojac = 5;
        for (int i = 0; i < 5; i++) {
            radi[i] = true;
        }
    }

    public Preduzece getPr() {
        return pr;
    }

    public void setPr(Preduzece pr) {
        this.pr = pr;
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

    public String lozinkaRedirect() {
        return "promenaLozinkePredu?faces-redirect=true";
    }

    public String pocetnaRedirect() {
        return "preduzece?faces-redirect=true";
    }

    public String proizvodRedirect() {
        return "dodavanjeProizvoda?-faces-redirect=true";
    }

    public String promeniLozinku() {
        poruka = "";
        HttpSession session = util.SessionUtils.getSession();
        Preduzece pr = new Preduzece();
        pr = (Preduzece) session.getAttribute("preduzece");
        String pw = pr.getPassword();
        String username = pr.getUsername();

        if (pw.equals(pw1)) {
            if (pw.equals(pw2) && pw.equals(pw3)) {
                poruka = "Nova lozinka mora da se razlikuje od stare!";
            } else if (!pw2.equals(pw3)) {
                poruka = "Lozinke se ne poklapaju!";
            } else {
                PreduzeceDAO.promeniLozinku(username, pw2);
                KorisnikDAO.promeniLozinku(username, pw2);
                session.invalidate();
                return ("index");
            }
        } else {
            poruka = "Uneli ste pogresnu lozinku!";
        }
        return null;
    }

    ArrayList<Porudzbina> porudzbine = new ArrayList<>();

    public ArrayList<Porudzbina> getPorudzbine() {
        return porudzbine;
    }

    public void setPorudzbine(ArrayList<Porudzbina> porudzbine) {
        this.porudzbine = porudzbine;
    }

    ArrayList<Porudzbina> porudzbine2 = new ArrayList<>();

    public ArrayList<Porudzbina> getPorudzbine2() {
        return porudzbine2;
    }

    public void setPorudzbine2(ArrayList<Porudzbina> porudzbine) {
        this.porudzbine2 = porudzbine;
    }

    public void dohvatiSve() {
        porudzbine = util.dao.PorudzbinaDAO.dohvatiSvePorudzbine(pr.getUsername());
        porudzbine2 = util.dao.PorudzbinaDAO.dohvatiSvePorudzbine30(pr.getUsername());
        sadnice = util.dao.SadnicaDAO.dohvatiSveSadnice(pr.getUsername());
        preparati = util.dao.PreparatDAO.dohvatiSvePreparate(pr.getUsername());
    }

    public void onRowEdit1(RowEditEvent<Porudzbina> event) {
        FacesMessage msg = new FacesMessage("Promenjena porudzbina:", Integer.toString(event.getObject().getIdP()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        util.dao.PorudzbinaDAO.izmeniPorudzbinu((Porudzbina)event.getObject());
        dohvatiSve();
    }

    public void onRowCancel1(RowEditEvent<Porudzbina> event) {
        int brojPorudzbine = event.getObject().getIdP();
        String brPorudzbine = Integer.toString(brojPorudzbine);
        FacesMessage msg = new FacesMessage("Otkazana promena porudzbine:", brPorudzbine);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowEdit2(RowEditEvent<Sadnica> event) {
        FacesMessage msg = new FacesMessage("Promenjena sadnica:", event.getObject().getNaziv());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        util.dao.SadnicaDAO.izmeniSadnicu(event.getObject());
        dohvatiSve();
    }

    public void onRowCancel2(RowEditEvent<Sadnica> event) {
        FacesMessage msg = new FacesMessage("Otkazana promena sadnice:", event.getObject().getNaziv());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowEdit3(RowEditEvent<Preparat> event) {
        FacesMessage msg = new FacesMessage("Promenjen preparat:", event.getObject().getNaziv());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        util.dao.PreparatDAO.izmeniPreparat(event.getObject());
        dohvatiSve();
    }

    public void onRowCancel3(RowEditEvent<Preparat> event) {
        FacesMessage msg = new FacesMessage("Otkazana promena preparata:", event.getObject().getNaziv());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void obrisiPorudzbinu(Porudzbina p) {
        int idP = p.getIdP();
        util.dao.PorudzbinaDAO.obrisiPorudzbinu(idP);
        dohvatiSve();
    }

    public void prihvatiPorudzbinu(Porudzbina p) {
        int idP = p.getIdP();
        util.dao.PorudzbinaDAO.prihvatiPorudzbinu(idP);
        dohvatiSve();
    }

    public void obrisiSadnicu(Sadnica s) {
        int idS = s.getIdS();
        util.dao.SadnicaDAO.obrisiSadnicu(idS);
        dohvatiSve();
    }

    public void obrisiPreparat(Preparat p) {
        int idP = p.getIdP();
        util.dao.PreparatDAO.obrisiPreparat(idP);
        dohvatiSve();
    }

    ArrayList<Preparat> preparati = new ArrayList<>();
    ArrayList<Sadnica> sadnice = new ArrayList<>();

    public ArrayList<Preparat> getPreparati() {
        return preparati;
    }

    public void setPreparati(ArrayList<Preparat> preparati) {
        this.preparati = preparati;
    }

    public ArrayList<Sadnica> getSadnice() {
        return sadnice;
    }

    public void setSadnice(ArrayList<Sadnica> sadnice) {
        this.sadnice = sadnice;
    }

    Preparat p = new Preparat();
    Sadnica s = new Sadnica();

    public Preparat getP() {
        return p;
    }

    public void setP(Preparat p) {
        this.p = p;
    }

    public Sadnica getS() {
        return s;
    }

    public void setS(Sadnica s) {
        this.s = s;
    }

    public String cenaFejk;
    public String kolicinaFejk;

    public String getCenaFejk() {
        return cenaFejk;
    }

    public void setCenaFejk(String cenaFejk) {
        this.cenaFejk = cenaFejk;
    }

    public String getKolicinaFejk() {
        return kolicinaFejk;
    }

    public void setKolicinaFejk(String kolicinaFejk) {
        this.kolicinaFejk = kolicinaFejk;
    }

    public String dodavanjeSadnice() {
        int cena = Integer.parseInt(cenaFejk);
        s.setCena(cena);
        int kolicina = Integer.parseInt(kolicinaFejk);
        s.setKolicina(kolicina);
        s.setPreduzece(pr.getUsername());
        util.dao.SadnicaDAO.dodavanjeSadnice(s);
        return "preduzece?faces-redirect=true";
    }

    public String dodavanjePreparata() {
        int cena = Integer.parseInt(cenaFejk);
        p.setCena(cena);
        int kolicina = Integer.parseInt(kolicinaFejk);
        p.setKolicina(kolicina);
        p.setPreduzece(pr.getUsername());
        util.dao.PreparatDAO.dodavanjePreparata(p);
        return "preduzece?faces-redirect=true";
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

        Sadnica sad = (Sadnica) value;
        return sad.getNaziv().toLowerCase().contains(filterText)
                || sad.getPreduzece().toLowerCase().contains(filterText)
                || sad.getCena() < filterInt
                || sad.getKolicina() < filterInt;
    }

    private int getInteger(String string) {
        try {
            return Integer.valueOf(string);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private String postar;
    private String kuriri[] = new String[5];

    public String getPostar() {
        return postar;
    }

    public void setPostar(String postar) {
        this.postar = postar;
    }

    public String[] getKuriri() {
        return kuriri;
    }

    public void setKuriri(String[] kuriri) {
        this.kuriri = kuriri;
    }

    private boolean radi[] = new boolean[5];

    public boolean[] getRadi() {
        return radi;
    }

    public void setRadi(boolean[] radi) {
        this.radi = radi;
    }

    private int brojac;

    public int getBrojac() {
        return brojac;
    }

    public void setBrojac(int brojac) {
        this.brojac = brojac;
    }

    public boolean proveriKurira(String kurir) {
        for (int i = 0; i < 5; i++) {
            if (kuriri[i] == kurir) {
                if (radi[i] == false) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public void angazujKurira(String kurir){
        
    }
}
