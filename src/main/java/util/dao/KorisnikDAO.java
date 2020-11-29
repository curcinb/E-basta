/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dao;

import beans.Admin;
import beans.Korisnik;
import beans.Poljoprivrednik;
import beans.Preduzece;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bosko
 */
public class KorisnikDAO {

    public static Korisnik login(String username, String password) {
        //Dohvatam konekciju:
        Connection conn = util.DB.getInstance().getConnection();
        //Pravim iskaz:
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("select * from korisnik where username=? and password=?");
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Korisnik kk = new Korisnik();
                kk.setUsername(username);
                kk.setPassword(password);
                kk.setTip(rs.getString("tip"));

                return kk;
            }

        } catch (SQLException ex) {
            Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            util.DB.getInstance().putConnection(conn);
        }
        return null;
    }

    public static void dodajKorisnika(String username, String password, String tip) {
        //Dohvatam konekciju:
        Connection conn = util.DB.getInstance().getConnection();
        //Pravim iskaz:
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("insert into korisnik(username,password,tip) values(?,?,?)");
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, tip);
            
            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
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
            ps = conn.prepareStatement("update korisnik set password = ? where username = ?");
            ps.setString(2, username);
            ps.setString(1, pw2);

            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            util.DB.getInstance().putConnection(conn);
        }
    }

    public static void odobriPoljoprivrednika(String username) {
        //Dohvatam konekciju:
        Connection conn = util.DB.getInstance().getConnection();
        //Pravim iskaz:
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("update poljoprivrednik set prihvacen = ? where username= ? ");
            ps.setInt(1, 1);
            ps.setString(2, username);

            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            util.DB.getInstance().putConnection(conn);
        }
    }

    public static void odobriPreduzece(String username) {
        //Dohvatam konekciju:
        Connection conn = util.DB.getInstance().getConnection();
        //Pravim iskaz:
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("update preduzece set prihvacen = ? where username= ? ");
            ps.setInt(1, 1);
            ps.setString(2, username);

            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            util.DB.getInstance().putConnection(conn);
        }
    }

    public static void obrisiKorisnika(String username) {
        //Dohvatam konekciju:
        Connection conn = util.DB.getInstance().getConnection();
        //Pravim iskaz:
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("DELETE FROM korisnik WHERE username=?");
            ps.setString(1, username);

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

    public static Admin dohvatiAdmina(String username) {
        Admin a = new Admin();

        //Dohvatam konekciju:
        Connection conn = util.DB.getInstance().getConnection();
        //Pravim iskaz:
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("select * from korisnik where username=?");
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                a.setUsername(rs.getString("username"));
                a.setPassword(rs.getString("password"));

                return a;
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

    public static void izmeniPoljoprivrednika(Poljoprivrednik object) {
       Poljoprivrednik p = object;
       
       //Dohvatam konekciju:
        Connection conn = util.DB.getInstance().getConnection();
        //Pravim iskaz:
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement("update poljoprivrednik set ime=?, "
                    + "prezime=?, username=?, password=?,"
                    + "datumRodjenja=?, mestoRodjenja=?, telefon=?, mejl=?, prihvacen=? "
                    + "where username=?");
            
            ps.setString(1, p.getIme());
            ps.setString(2, p.getPrezime());
            ps.setString(3, p.getUsername());
            ps.setString(4, p.getPassword());
            ps.setDate(5, new Date(p.getDatumRodjenja().getTime()));
            ps.setString(6, p.getMestoRodjenja());
            ps.setString(7, p.getTelefon());
            ps.setString(8, p.getMejl());
            ps.setInt(9, p.getPrihvacen());
            ps.setString(10, p.getUsername());
            
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
           try {
               ps.close();
           } catch (SQLException ex) {
               Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
           }
           util.DB.getInstance().putConnection(conn);
        }
    }

    public static void izmeniPreduzece(Preduzece object) {
        Preduzece p  = object;
        
        //Dohvatam konekciju:
        Connection conn = util.DB.getInstance().getConnection();
        //Pravim iskaz:
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement("update preduzece set naziv=?,"
                    + "username=?, password=?, datumOsnivanja=?, mesto=?,"
                    + "mejl=?, prihvacen=? where username=?");
            ps.setString(1, p.getNaziv());
            ps.setString(2, p.getUsername());
            ps.setString(3, p.getPassword());
            ps.setDate(4, new Date(p.getDatumOsnivanja().getTime()));
            ps.setString(5, p.getMesto());
            ps.setString(6, p.getMejl());
            ps.setInt(7, p.getPrihvacen());
            ps.setString(8, p.getUsername());
            
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            util.DB.getInstance().putConnection(conn);
        }
    }
}
