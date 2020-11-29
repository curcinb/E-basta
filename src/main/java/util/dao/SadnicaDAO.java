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
public class SadnicaDAO {

    public static ArrayList<Sadnica> dohvatiSveSadnice(String naziv) {
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;

        ArrayList<Sadnica> sadnice = new ArrayList<>();

        try {
            ps = conn.prepareStatement("select * from sadnica where preduzece=?");
            ps.setString(1, naziv);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Sadnica s = new Sadnica();
                s.setIdS(rs.getInt("idS"));
                s.setNaziv(rs.getString("naziv"));
                s.setPreduzece(rs.getString("preduzece"));
                s.setNapredak(0);
                s.setIdR(0);
                s.setCena(rs.getInt("cena"));
                s.setKolicina(rs.getInt("kolicina"));

                sadnice.add(s);
            }
            return sadnice;
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

    public static void izmeniSadnicu(Sadnica object) {
        Sadnica s = object;

        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("update sadnica set naziv=?, "
                    + "preduzece=?, napredak=?, idR=?, cena=?, kolicina=? "
                    + "where preduzece=? and idS=?");
            
            ps.setString(1, s.getNaziv());
            ps.setString(2, s.getPreduzece());
            ps.setInt(3, 0);
            ps.setInt(4, 0);
            ps.setInt(5, s.getCena());
            ps.setInt(6, s.getKolicina());
            ps.setString(7, s.getPreduzece());
            ps.setInt(8, s.getIdS());

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

    public static void obrisiSadnicu(int idS) {
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("Delete from sadnica where idS=?");
            ps.setInt(1, idS);

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

    public static void dodavanjeSadnice(Sadnica s) {
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("insert into sadnica (naziv, preduzece, cena, kolicina) values(?, ?, ?, ?)");
            ps.setString(1, s.getNaziv());
            ps.setString(2, s.getPreduzece());
            ps.setInt(3, s.getCena());
            ps.setInt(4, s.getKolicina());

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

    public static ArrayList<Sadnica> dohvatiSveSadniceProdavnica() {
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;

        ArrayList<Sadnica> sadnice = new ArrayList<>();

        try {
            ps = conn.prepareStatement("select * from sadnica where idR=? and napredak=? and idM=?");
            ps.setInt(1, 0);
            ps.setInt(2, 0);
            ps.setInt(3,0);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Sadnica s = new Sadnica();
                s.setIdS(rs.getInt("idS"));
                s.setNaziv(rs.getString("naziv"));
                s.setPreduzece(rs.getString("preduzece"));
                s.setNapredak(0);
                s.setIdR(0);
                s.setCena(rs.getInt("cena"));
                s.setKolicina(rs.getInt("kolicina"));

                sadnice.add(s);
            }
            return sadnice;
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

    public static void smanjiBrojSadnica(Sadnica s) {
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("update sadnica set kolicina=kolicina-1 where naziv=? and preduzece=?");
            ps.setString(1, s.getNaziv());
            ps.setString(2, s.getPreduzece());

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

    public static void dodajSadnicuURasadnik(String naziv, int idR) {
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;

        try {
            ps=conn.prepareStatement("update sadnica set idR=?, idM=? where idM=? and naziv=?");
            ps.setInt(1, idR);
            ps.setInt(2, 0);
            ps.setInt(3, idR);
            ps.setString(4,naziv);
            
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
