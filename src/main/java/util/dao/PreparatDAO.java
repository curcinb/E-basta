/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dao;

import beans.Porudzbina;
import beans.Preparat;
import beans.Sadnica;
import java.sql.Connection;
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
public class PreparatDAO {

    public static ArrayList<Preparat> dohvatiSvePreparate(String naziv) {
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;

        ArrayList<Preparat> preparati = new ArrayList<>();

        try {
            ps = conn.prepareStatement("select * from preparat where preduzece=?");
            ps.setString(1, naziv);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Preparat p = new Preparat();
                p.setIdP(rs.getInt("idP"));
                p.setNaziv(rs.getString("naziv"));
                p.setPreduzece(rs.getString("preduzece"));
                p.setCena(rs.getInt("cena"));
                p.setKolicina(rs.getInt("kolicina"));

                preparati.add(p);
            }
            return preparati;
        } catch (SQLException ex) {
            Logger.getLogger(PorudzbinaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(PorudzbinaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            util.DB.getInstance().putConnection(conn);
        }
        return null;
    }

    public static void izmeniPreparat(Preparat object) {
        Preparat p = object;

        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("update preparat set naziv=?, "
                    + "preduzece=?, cena=?, kolicina=? "
                    + "where preduzece=? and idP=?");
           
            ps.setString(1, p.getNaziv());
            ps.setString(2, p.getPreduzece());
            ps.setInt(3, p.getCena());
            ps.setInt(4, p.getKolicina());
            ps.setString(5, p.getPreduzece());
            ps.setInt(6, p.getIdP());  
            
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PorudzbinaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(PorudzbinaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            util.DB.getInstance().putConnection(conn);
        }
    }

    public static void obrisiPreparat(int idP) {
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("DELETE from preparat where idP=?");
            ps.setInt(1, idP);

            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PorudzbinaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(PorudzbinaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            util.DB.getInstance().putConnection(conn);
        }
    }

    public static void dodavanjePreparata(Preparat p) {
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("insert into preparat (naziv, preduzece, cena, kolicina) values(?, ?, ?, ?)");
            ps.setString(1, p.getNaziv());
            ps.setString(2, p.getPreduzece());
            ps.setInt(3, p.getCena());
            ps.setInt(4, p.getKolicina());

            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SadnicaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(SadnicaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            util.DB.getInstance().putConnection(conn);
        }
    }

    public static ArrayList<Preparat> dohvatiSvePreparateProdavnica() {
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;

        ArrayList<Preparat> preparati = new ArrayList<>();

        try {
            ps = conn.prepareStatement("select * from preparat");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Preparat p = new Preparat();
                p.setIdP(rs.getInt("idP"));
                p.setNaziv(rs.getString("naziv"));
                p.setPreduzece(rs.getString("preduzece"));
                p.setCena(rs.getInt("cena"));
                p.setKolicina(rs.getInt("kolicina"));

                preparati.add(p);
            }
            return preparati;
        } catch (SQLException ex) {
            Logger.getLogger(PorudzbinaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(PorudzbinaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            util.DB.getInstance().putConnection(conn);
        }
        return null;
    }

    public static void smanjiBrojPreparata(Preparat p) {
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("update preparat set kolicina=kolicina-1 where naziv=? and preduzece=?");
            ps.setString(1, p.getNaziv());
            ps.setString(2, p.getPreduzece());

            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SadnicaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(SadnicaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            util.DB.getInstance().putConnection(conn);
        }
    }
}
