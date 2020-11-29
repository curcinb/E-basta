/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Poljoprivrednik;
import beans.Preduzece;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Bosko
 */
@ManagedBean
@SessionScoped
public class RegisterController{

    private Poljoprivrednik p = new Poljoprivrednik();
    private Preduzece pr = new Preduzece();
    private String porukaPoljo = "";
    private String porukaPredu = "";

    public Poljoprivrednik getP() {
        return p;
    }

    public void setP(Poljoprivrednik p) {
        this.p = p;
    }

    public Preduzece getPr() {
        return pr;
    }

    public void setPr(Preduzece pr) {
        this.pr = pr;
    }

    public String getPorukaPoljo() {
        return porukaPoljo;
    }

    public void setPorukaPoljo(String porukaPoljo) {
        this.porukaPoljo = porukaPoljo;
    }

    public String getPorukaPredu() {
        return porukaPredu;
    }

    public void setPorukaPredu(String porukaPredu) {
        this.porukaPredu = porukaPredu;
    }

    public String registracija(boolean poljoprivrednik, boolean admin) {
        porukaPoljo = "";
        porukaPredu = "";
        //Korisnik dodavanje:
        if (poljoprivrednik) {
            if (!p.getPassword().equals(p.getPassword2())) {
                porukaPoljo = "Lozinke se ne poklapaju!";
            } else {
                util.dao.KorisnikDAO.dodajKorisnika(p.getUsername(), p.getPassword(), "poljoprivrednik");
                util.dao.PoljoprivrednikDAO.dodajPoljoprivrednika(p);
                if(admin){
                    util.dao.KorisnikDAO.odobriPoljoprivrednika(p.getUsername());
                    return "admin?faces-redirect=true";
                }
                return "index?faces-redirect=true";
            }

        } else {
            if (!pr.getPassword().equals(pr.getPassword2())) {
                porukaPredu = "Lozinke se ne poklapaju!";
            } else {
                util.dao.KorisnikDAO.dodajKorisnika(pr.getUsername(), pr.getPassword(), "preduzece");
                util.dao.PreduzeceDAO.dodajPreduzece(pr);
                if(admin){
                    util.dao.KorisnikDAO.odobriPreduzece(pr.getUsername());
                    return "admin?faces-redirect=true";
                }
                return "index?faces-redirect=true";
            }
        }
        return null;
    }

    public String loginRedirect() {
        return "index?faces-redirect=true";
    }

}
