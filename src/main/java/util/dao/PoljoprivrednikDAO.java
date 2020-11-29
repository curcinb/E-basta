/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dao;

import beans.Poljoprivrednik;
import beans.Preduzece;
import beans.Rasadnik;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bosko
 */
public class PoljoprivrednikDAO {

    public static Poljoprivrednik dohvatiPoljoprivrednika(String username) {
        Poljoprivrednik p = new Poljoprivrednik();

        //Dohvatam konekciju:
        Connection conn = util.DB.getInstance().getConnection();
        //Pravim iskaz:
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("select * from poljoprivrednik where username=?");
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                p.setIme(rs.getString("ime"));
                p.setPrezime(rs.getString("prezime"));
                p.setUsername(username);
                p.setPassword(rs.getString("password"));
                p.setDatumRodjenja(rs.getDate("datumRodjenja"));
                p.setMestoRodjenja(rs.getString("mestoRodjenja"));
                p.setMejl(rs.getString("mejl"));
                p.setPrihvacen(rs.getInt("prihvacen"));

                return p;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PoljoprivrednikDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(PoljoprivrednikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            util.DB.getInstance().putConnection(conn);
        }
        return null;
    }

    public static void dodajPoljoprivrednika(Poljoprivrednik p) {
        //Dohvatam konekciju:
        Connection conn = util.DB.getInstance().getConnection();
        //Pravim iskaz:
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("insert into "
                    + "poljoprivrednik(ime, prezime, username, password, datumRodjenja, "
                    + "mestoRodjenja, telefon, mejl, prihvacen) values(?,?,?,?,?,?,?,?,?)");
            ps.setString(1, p.getIme());
            ps.setString(2, p.getPrezime());
            ps.setString(3, p.getUsername());
            ps.setString(4, p.getPassword());
            ps.setDate(5, new Date(p.getDatumRodjenja().getTime()));
            ps.setString(6, p.getMestoRodjenja());
            ps.setString(7, p.getTelefon());
            ps.setString(8, p.getMejl());
            ps.setInt(9, 0);

            
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(PoljoprivrednikDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(PoljoprivrednikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            util.DB.getInstance().putConnection(conn);
        }
    }

    public static void promeniLozinku(String username, String pw2) {
        //Dohvatam konekciju:
        Connection conn = util.DB.getInstance().getConnection();
        //Pravim iskaz:
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("update poljoprivrednik set password = ? where username = ?");
            ps.setString(2, username);
            ps.setString(1, pw2);

            ps.executeUpdate();
            System.err.println("PROMENA POLJO");
        } catch (SQLException ex) {
            Logger.getLogger(PoljoprivrednikDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(PoljoprivrednikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            util.DB.getInstance().putConnection(conn);
        }
    }

    public static ArrayList<Rasadnik> dohvatiRasadnike(String username) {
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;
        ArrayList<Rasadnik> rasadnici = new ArrayList<>();

        try {
            ps = conn.prepareStatement("select * from rasadnik where vlasnik=?");
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Rasadnik r = new Rasadnik();
                r.setVlasnik(rs.getString("vlasnik"));
                r.setNaziv(rs.getString("naziv"));
                r.setMesto(rs.getString("mesto"));
                r.setBrojSadnica(rs.getInt("brojSadnica"));
                r.setKapacitet(rs.getInt("kapacitet"));
                r.setVoda(rs.getInt("voda"));
                r.setTemperatura(rs.getInt("temperatura"));
                r.setSlobodnoMesta(r.getKapacitet() - r.getBrojSadnica());
                r.setDuzina(rs.getInt("duzina"));
                r.setSirina(rs.getInt("sirina"));
                r.setIdR(rs.getInt("idR"));

                rasadnici.add(r);
            }
            return rasadnici;
        } catch (SQLException ex) {
            Logger.getLogger(PoljoprivrednikDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(PoljoprivrednikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            util.DB.getInstance().putConnection(conn);
        }
        return null;
    }

    public static ArrayList<Poljoprivrednik> dohvatiSvePoljoprivrednike() {
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;

        ArrayList<Poljoprivrednik> poljo = new ArrayList<>();

        try {
            ps = conn.prepareStatement("select * from poljoprivrednik");
            
            ResultSet rs = ps.executeQuery();
          
            while(rs.next()){
                Poljoprivrednik p = new Poljoprivrednik();
                p.setIme(rs.getString("ime"));
                p.setPrezime(rs.getString("prezime"));
                p.setUsername(rs.getString("username"));
                p.setPassword(rs.getString("password"));
                p.setDatumRodjenja(rs.getDate("datumRodjenja"));
                p.setMestoRodjenja(rs.getString("mestoRodjenja"));
                p.setMejl(rs.getString("mejl"));
                p.setTelefon(rs.getString("telefon"));
                p.setPrihvacen(rs.getInt("prihvacen"));
                poljo.add(p);
            }
            return poljo;
            
        } catch (SQLException ex) {
            Logger.getLogger(PoljoprivrednikDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(PoljoprivrednikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            util.DB.getInstance().putConnection(conn);
        }
        return null;
    }

    public static void obrisiPoljoprivrednika(String username) {
       //Dohvatam konekciju:
        Connection conn = util.DB.getInstance().getConnection();
        //Pravim iskaz:
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement("DELETE FROM poljoprivrednik WHERE username=?");
            ps.setString(1,username);
            
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PreduzeceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(PreduzeceDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            util.DB.getInstance().putConnection(conn);
        }
    }
}
