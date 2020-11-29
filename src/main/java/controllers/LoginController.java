/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Admin;
import beans.Korisnik;
import beans.Poljoprivrednik;
import beans.Preduzece;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Bosko
 */
@ManagedBean
@SessionScoped
public class LoginController {

    private String poruka = "";
    private String username;
    private String password;
    

    public LoginController() {
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login() {

        Korisnik k = util.dao.KorisnikDAO.login(username, password);

        if (k != null) {
            if (k.getTip().equals("admin")) {
                HttpSession session = util.SessionUtils.getSession();
                Admin a = util.dao.KorisnikDAO.dohvatiAdmina(username);
                session.setAttribute("admin", a);
                return "admin?faces-redirect=true";
            } else if (k.getTip().equals("poljoprivrednik")) {
                Poljoprivrednik p = util.dao.PoljoprivrednikDAO.dohvatiPoljoprivrednika(username);
                if (p.getPrihvacen() == 1) {
                    HttpSession session = util.SessionUtils.getSession();
                    session.setAttribute("poljoprivrednik", p);
                    return "poljoprivrednik?faces-redirect=true";
                } else {
                    poruka = "Administrator jos nije odobrio vas nalog!";
                    return null;
                }
            } else {
                Preduzece pr = util.dao.PreduzeceDAO.dohvatiPreduzece(username);
                if (pr.getPrihvacen() == 1) {
                    HttpSession session = util.SessionUtils.getSession();
                    session.setAttribute("preduzece", pr);
                    return "preduzece?faces-redirect=true";
                } else {
                    poruka = "Administrator jos nije odobrio vas nalog!";
                    return null;
                }
            }
        } else {
            poruka = "Nema takvog korisnika!";
            return null;
        }
    }

    public String logout() {
        HttpSession session = util.SessionUtils.getSession();
        session.invalidate();
        return ("index?faces-redirect=true");
    }

    public String registerRedirect(boolean poljoprivrednik) {
        if (poljoprivrednik) {
            return "newPoljoprivrednik?faces-redirect=true";
        } else {
            return "newPreduzece?faces-redirect=true";
        }
    }
}
