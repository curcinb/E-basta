/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Admin;
import beans.Poljoprivrednik;
import beans.Preduzece;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.event.RowEditEvent;
import util.dao.KorisnikDAO;
import util.dao.PoljoprivrednikDAO;

/**
 *
 * @author Bosko
 */
@ManagedBean
@SessionScoped
public class AdminController {

    Admin ja = new Admin();
    ArrayList<Poljoprivrednik> poljo = new ArrayList<>();
    ArrayList<Preduzece> predu = new ArrayList<>();

    public AdminController() {
        //HttpSession session = util.SessionUtils.getSession();
        //ja = (Admin) session.getAttribute("admin");
    }

    public Admin getJa() {
        return ja;
    }

    public void setJa(Admin ja) {
        this.ja = ja;
    }

    public ArrayList<Poljoprivrednik> getPoljo() {
        return poljo;
    }

    public void setPoljo(ArrayList<Poljoprivrednik> poljo) {
        this.poljo = poljo;
    }

    public ArrayList<Preduzece> getPredu() {
        return predu;
    }

    public void setPredu(ArrayList<Preduzece> predu) {
        this.predu = predu;
    }

    private String poruka;

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    private String pw1;
    private String pw2;
    private String pw3;

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

    public void dohvatiSve() {
        poljo = util.dao.PoljoprivrednikDAO.dohvatiSvePoljoprivrednike();
        predu = util.dao.PreduzeceDAO.dohvatiSvaPreduzeca();
    }

    public void onRowEdit1(RowEditEvent<Poljoprivrednik> event) {
        FacesMessage msg = new FacesMessage("Promenjen korisnik:", event.getObject().getUsername());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        util.dao.KorisnikDAO.izmeniPoljoprivrednika(event.getObject());
        dohvatiSve();
    }

    public void onRowCancel1(RowEditEvent<Poljoprivrednik> event) {
        FacesMessage msg = new FacesMessage("Otkazana promena korisnika:", event.getObject().getUsername());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowEdit2(RowEditEvent<Preduzece> event) {
        FacesMessage msg = new FacesMessage("Promenjeno preduzece:", event.getObject().getNaziv());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        util.dao.KorisnikDAO.izmeniPreduzece(event.getObject());
        dohvatiSve();
    }

    public void onRowCancel2(RowEditEvent<Preduzece> event) {
        FacesMessage msg = new FacesMessage("Otkazana promena preduzeca:", event.getObject().getNaziv());;
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void obrisiPreduzece(Preduzece pr) {
        String username = pr.getUsername();
        util.dao.PreduzeceDAO.obrisiPreduzece(username);
        util.dao.KorisnikDAO.obrisiKorisnika(username);
        dohvatiSve();
    }

    public void obrisiPoljoprivrednika(Poljoprivrednik po) {
        String username = po.getUsername();
        util.dao.PoljoprivrednikDAO.obrisiPoljoprivrednika(username);
        util.dao.KorisnikDAO.obrisiKorisnika(username);
        dohvatiSve();
    }

    public String promeniLozinku() {
        poruka = "";
        HttpSession session = util.SessionUtils.getSession();
        Admin a = new Admin();
        a = (Admin) session.getAttribute("admin");
        String pw = a.getPassword();
        String username = a.getUsername();

        if (pw.equals(pw1)) {
            if (pw.equals(pw2) && pw.equals(pw3)) {
                poruka = "Nova lozinka mora da se razlikuje od stare!";
            } else if (!pw2.equals(pw3)) {
                poruka = "Lozinke se ne poklapaju!";
            } else {
                KorisnikDAO.promeniLozinku(username, pw2);
                session.invalidate();
                return ("index?faces-redirect=true");
            }
        } else {
            poruka = "Uneli ste pogresnu lozinku!";
        }
        return null;
    }

    public String pocetnaRedirect() {
        return "admin?faces-redirect=true";
    }

    public String lozinkaRedirect() {
        return "promenaLozinkeAdmin?faces-redirect=true";
    }

    public String poljoprivrednikRedirect() {
        return "newPoljoprivrednikA?faces-redirect=true";
    }

    public String preduzeceRedirect() {
        return "newPreduzeceA?faces-redirect=true";
    }
}
