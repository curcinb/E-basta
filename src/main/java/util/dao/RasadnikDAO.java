/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dao;

import beans.Poljoprivrednik;
import beans.Preparat;
import beans.Rasadnik;
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
public class RasadnikDAO {

    public static void dodajRasadnik(Poljoprivrednik ja, Rasadnik r) {
        //Dohvatam konekciju:
        Connection conn = util.DB.getInstance().getConnection();
        //Pravim iskaz:
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("insert into "
                    + "rasadnik(vlasnik, naziv, mesto, brojSadnica, kapacitet, voda, temperatura, duzina, sirina)"
                    + " values(?,?,?,?,?,?,?,?,?)");
            ps.setString(1, ja.getUsername());
            ps.setString(2, r.getNaziv());
            ps.setString(3, r.getMesto());
            ps.setInt(4, 0);
            ps.setInt(5, r.getDuzina() * r.getSirina());
            ps.setInt(6, 200);
            ps.setInt(7, 18);
            ps.setInt(8, r.getDuzina());
            ps.setInt(9, r.getSirina());

            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(RasadnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(RasadnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            util.DB.getInstance().putConnection(conn);
        }
    }

    public static ArrayList<Sadnica> dohvatiSadnice(Rasadnik rr) {
        //Dohvatam konekciju:
        Connection conn = util.DB.getInstance().getConnection();
        //Pravim iskaz:
        PreparedStatement ps = null;

        int idR = rr.getIdR();

        try {
            ps = conn.prepareStatement("select * from sadnica where idR=?");
            ps.setInt(1, idR);
            ArrayList<Sadnica> sadnice = new ArrayList<>();

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Sadnica s = new Sadnica();
                s.setIdS(rs.getInt("idS"));
                s.setNaziv(rs.getString("naziv"));
                s.setPreduzece(rs.getString("preduzece"));
                s.setNapredak(rs.getInt("napredak"));
                sadnice.add(s);
            }
            return sadnice;
        } catch (SQLException ex) {
            Logger.getLogger(RasadnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(RasadnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            util.DB.getInstance().putConnection(conn);
        }
        return null;
    }

    public static void promenaVode(int val, Rasadnik r) {
        //Dohvatam konekciju:
        Connection conn = util.DB.getInstance().getConnection();
        //Pravim iskaz:
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("update rasadnik set voda=? where idR=?");
            ps.setInt(1, r.getVoda() + val);
            ps.setInt(2, r.getIdR());

            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RasadnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(RasadnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            util.DB.getInstance().putConnection(conn);
        }

    }

    public static void promenaTemperature(double val, Rasadnik r) {
        //Dohvatam konekciju:
        Connection conn = util.DB.getInstance().getConnection();
        //Pravim iskaz:
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("update rasadnik set temperatura=? where idR=?");
            ps.setDouble(1, r.getTemperatura() + val);
            ps.setInt(2, r.getIdR());

            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RasadnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(RasadnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            util.DB.getInstance().putConnection(conn);
        }
    }

    public static void apdejtujRasadnike(double voda, double temperatura) {
        //Dohvatam konekciju:
        Connection conn = util.DB.getInstance().getConnection();
        //Pravim iskaz:
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("update rasadnik set temperatura=temperatura-0.5, voda=voda-1 where idR>=0 and idR<=5000 and kolicina>0");

            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RasadnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(RasadnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            util.DB.getInstance().putConnection(conn);
        }
    }

    public static ArrayList<Sadnica> dohvatiSadniceMagacin(Rasadnik rr) {
        //Dohvatam konekciju:
        Connection conn = util.DB.getInstance().getConnection();
        //Pravim iskaz:
        PreparedStatement ps = null;

        int idR = rr.getIdR();

        try {
            ps = conn.prepareStatement("select * from magacin m, sadnica s where m.idR=? and m.tipProizvoda=? and s.idM=? and m.nazivProizvoda=s.naziv");
            ps.setInt(1, idR);
            ps.setString(2, "sadnica");
            ps.setInt(3, idR);
            ArrayList<Sadnica> sadnice = new ArrayList<>();

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Sadnica s = new Sadnica();
                s.setNaziv(rs.getString("m.nazivProizvoda"));
                s.setPreduzece(rs.getString("m.nazivProizvodjaca"));
                s.setKolicina(rs.getInt("m.kolicina"));
                s.setIdS(rs.getInt("s.idS"));
                sadnice.add(s);
            }
            return sadnice;
        } catch (SQLException ex) {
            Logger.getLogger(RasadnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(RasadnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            util.DB.getInstance().putConnection(conn);
        }
        return null;
    }

    public static ArrayList<Preparat> dohvatiPreparateMagacin(Rasadnik rr) {
        //Dohvatam konekciju:
        Connection conn = util.DB.getInstance().getConnection();
        //Pravim iskaz:
        PreparedStatement ps = null;

        int idR = rr.getIdR();

        try {
            ps = conn.prepareStatement("select * from magacin  where idR=? and tipProizvoda=? and kolicina>0");
            ps.setInt(1, idR);
            ps.setString(2, "preparat");
            ArrayList<Preparat> preparati = new ArrayList<>();

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Preparat p = new Preparat();
                p.setNaziv(rs.getString("nazivProizvoda"));
                p.setPreduzece(rs.getString("nazivProizvodjaca"));
                p.setKolicina(rs.getInt("kolicina"));
                preparati.add(p);
            }
            return preparati;
        } catch (SQLException ex) {
            Logger.getLogger(RasadnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(RasadnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            util.DB.getInstance().putConnection(conn);
        }
        return null;
    }

    public static void ukloniSadnicu(int idS) {
        //Dohvatam konekciju:
        Connection conn = util.DB.getInstance().getConnection();
        //Pravim iskaz:
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("delete from sadnica where idS=?");
            ps.setInt(1, idS);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RasadnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(RasadnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            util.DB.getInstance().putConnection(conn);
        }
    }

    public static void smanjiBrojSadnica(Rasadnik rr) {
        //Dohvatam konekciju:
        Connection conn = util.DB.getInstance().getConnection();
        //Pravim iskaz:
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("update rasadnik set brojSadnica=? where idR=?");
            ps.setInt(1, rr.getBrojSadnica() - 1);
            ps.setInt(2, rr.getIdR());

            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RasadnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(RasadnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            util.DB.getInstance().putConnection(conn);
        }
    }

    public static void smanjiBrojSadnicaMagacin(String nazivSadnice) {
        //Dohvatam konekciju:
        Connection conn = util.DB.getInstance().getConnection();
        //Pravim iskaz:
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("update magacin set kolicina=kolicina-1 where nazivProizvoda=? and tipProizvoda=?");
            ps.setString(1, nazivSadnice);
            ps.setString(2, "sadnica");

            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RasadnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(RasadnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            util.DB.getInstance().putConnection(conn);
        }
    }

    public static void povecajBrojSadnicaRasadnik(int idR) {
        //Dohvatam konekciju:
        Connection conn = util.DB.getInstance().getConnection();
        //Pravim iskaz:
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("update rasadnik set brojSadnica=brojSadnica+1 where idR=?");
            ps.setInt(1, idR);

            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RasadnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(RasadnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            util.DB.getInstance().putConnection(conn);
        }
    }

    public static void apdejtujSveRasadnike() {
         // Dohvatam konekciju:
        Connection 
        conn = util.DB.getInstance().getConnection();
        //Pravim iskaz:
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("update rasadnik set voda=voda-1, temperatura=temperatura-0.5 where idR<1000");

            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RasadnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(RasadnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            util.DB.getInstance().putConnection(conn);
        }
    }
}
