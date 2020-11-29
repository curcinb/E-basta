/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dao;

import beans.Poljoprivrednik;
import beans.Preduzece;
import beans.Preparat;
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
public class PreduzeceDAO {

    public static Preduzece dohvatiPreduzece(String username) {
        Preduzece pr = new Preduzece();

        //Dohvatam konekciju:
        Connection conn = util.DB.getInstance().getConnection();
        //Pravim iskaz:
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("select * from preduzece where username=?");
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                pr.setNaziv(rs.getString("naziv"));
                pr.setUsername(username);
                pr.setPassword(rs.getString("password"));
                pr.setDatumOsnivanja(rs.getDate("datumOsnivanja"));
                pr.setMesto(rs.getString("mesto"));
                pr.setMejl(rs.getString("mejl"));
                pr.setPrihvacen(rs.getInt("prihvacen"));

                return pr;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PreduzeceDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(PreduzeceDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            util.DB.getInstance().putConnection(conn);
        }
        return null;
    }

    public static void dodajPreduzece(Preduzece pr) {
        //Dohvatam konekciju:
        Connection conn = util.DB.getInstance().getConnection();
        //Pravim iskaz:
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("insert into preduzece(naziv, username, "
                    + "password, datumOsnivanja, mesto, mejl, prihvacen) "
                    + "values(?,?,?,?,?,?,?)");
            ps.setString(1, pr.getNaziv());
            ps.setString(2, pr.getUsername());
            ps.setString(3, pr.getPassword());
            ps.setDate(4, new Date(pr.getDatumOsnivanja().getTime()));
            ps.setString(5, pr.getMesto());
            ps.setString(6, pr.getMejl());
            ps.setInt(7, 0);

            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PreduzeceDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(PreduzeceDAO.class.getName()).log(Level.SEVERE, null, ex);
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
            ps = conn.prepareStatement("update preduzece set password = ? where username = ?");
            ps.setString(1, pw2);
            ps.setString(2, username);
            

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

    public static ArrayList<Preduzece> dohvatiSvaPreduzeca() {
        //Dohvatam konekciju:
        Connection conn = util.DB.getInstance().getConnection();
        //Pravim iskaz:
        PreparedStatement ps = null;
        
        ArrayList<Preduzece> predu = new ArrayList<>();
        try {
            ps = conn.prepareStatement("select * from preduzece");
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                Preduzece p = new Preduzece();
                p.setNaziv(rs.getString("naziv"));
                p.setUsername(rs.getString("username"));
                p.setPassword(rs.getString("password"));
                p.setDatumOsnivanja(rs.getDate("datumOsnivanja"));
                p.setMesto(rs.getString("mesto"));
                p.setMejl(rs.getString("mejl"));
                p.setPrihvacen(rs.getInt("prihvacen"));
                predu.add(p);
            }
            return predu;
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
        return null;
    }

    public static void obrisiPreduzece(String username) {
       //Dohvatam konekciju:
        Connection conn = util.DB.getInstance().getConnection();
        //Pravim iskaz:
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement("DELETE FROM preduzece WHERE username=?");
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
