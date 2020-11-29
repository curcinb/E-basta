/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dao;

import beans.Porudzbina;
import beans.Preparat;
import beans.Rasadnik;
import beans.Sadnica;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bosko
 */
public class PorudzbinaDAO {

    public static ArrayList<Porudzbina> dohvatiSvePorudzbine(String nazivPreduzeca) {
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;

        ArrayList<Porudzbina> porudzbine = new ArrayList<>();

        try {
            ps = conn.prepareStatement("select * from porudzbina where nazivProizvodjaca=?");
            ps.setString(1, nazivPreduzeca);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Porudzbina p = new Porudzbina();
                p.setIdP(rs.getInt("idP"));
                p.setNazivProivodjaca(rs.getString("nazivProizvodjaca"));
                p.setNazivProizvoda(rs.getString("nazivProizvoda"));
                p.setKolicina(rs.getInt("kolicina"));
                p.setDatumPorudzbine(rs.getDate("datumPorudzbine"));
                p.setStatus(rs.getString("status"));
                p.setImeKupca(rs.getString("imeKupca"));
                p.setIznos(rs.getInt("iznos"));

                porudzbine.add(p);
            }
            return porudzbine;
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

    public static void izmeniPorudzbinu(Porudzbina object) {
        Porudzbina p = object;

        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("update porudzbina set nazivProizvodjaca=?, "
                    + "nazivProizvoda=?, kolicina=?, datumPorudzbine=?, status=?, "
                    + "imeKupca=?, iznos=? where idP=?");

            ps.setString(1, p.getNazivProivodjaca());
            ps.setString(2, p.getNazivProizvoda());
            ps.setInt(3, p.getKolicina());
            ps.setDate(4, new Date(p.getDatumPorudzbine().getTime()));
            ps.setString(5, p.getStatus());
            ps.setString(6, p.getImeKupca());
            ps.setInt(7, p.getIznos());
            ps.setInt(8, p.getIdP());
            
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

    public static void obrisiPorudzbinu(int idP) {
        System.out.println("-------------------------------------------------------------> " + idP);
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("DELETE from porudzbina where idP=?");
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

    public static ArrayList<Porudzbina> dohvatiSvePorudzbine30(String nazivPreduzeca) {
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;

        ArrayList<Porudzbina> porudzbine = new ArrayList<>();

        try {
            //ps = conn.prepareStatement("select * from porudzbina where datediff(CURDATE(), datumPorudzbine) < 31 and nazivProizvodjaca=?");
            ps = conn.prepareStatement("select datumPorudzbine,sum(kolicina), sum(iznos) from porudzbina where datediff(CURDATE(), datumPorudzbine) < 31 and nazivProizvodjaca=? group by datumPorudzbine;");
            ps.setString(1, nazivPreduzeca);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Porudzbina p = new Porudzbina();
                p.setDatumPorudzbine(rs.getDate("datumPorudzbine"));
                p.setKolicina(rs.getInt("sum(kolicina)"));
                p.setIznos(rs.getInt("sum(iznos)"));

                porudzbine.add(p);
            }
            return porudzbine;
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

    public static void novaPorudzbinaPreparat(Preparat p, String kupac) {
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;

        try {
            //ps = conn.prepareStatement("select * from porudzbina where datediff(CURDATE(), datumPorudzbine) < 31 and nazivProizvodjaca=?");
            ps = conn.prepareStatement("insert into porudzbina (nazivProizvodjaca, nazivProizvoda, kolicina, datumPorudzbine, status, imeKupca, iznos) values(?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, p.getPreduzece());
            ps.setString(2, p.getNaziv());
            ps.setInt(3, 1);
            ps.setDate(4, new Date(System.currentTimeMillis()));
            ps.setString(5, "NA CEKANJU");
            ps.setString(6, kupac);
            ps.setInt(7, p.getCena());

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

    public static void novaPorudzbinaSadnica(Sadnica s, String kupac) {
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;

        try {
            //ps = conn.prepareStatement("select * from porudzbina where datediff(CURDATE(), datumPorudzbine) < 31 and nazivProizvodjaca=?");
            ps = conn.prepareStatement("insert into porudzbina (nazivProizvodjaca, nazivProizvoda, kolicina, datumPorudzbine, status, imeKupca, iznos) "
                    + "values(?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, s.getPreduzece());
            ps.setString(2, s.getNaziv());
            ps.setInt(3, 1);
            ps.setDate(4, new Date(System.currentTimeMillis()));
            ps.setString(5, "NA CEKANJU");
            ps.setString(6, kupac);
            ps.setInt(7, s.getCena());

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

    public static void prihvatiPorudzbinu(int idP) {
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("update porudzbina set status='PRIHVACENA' where idP=?");
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

    public static ArrayList<Porudzbina> dohvatiMojePorudzbine(String username) {
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;

        ArrayList<Porudzbina> porudzbine = new ArrayList<>();

        try {
            ps = conn.prepareStatement("select * from porudzbina where imeKupca=?");
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Porudzbina p = new Porudzbina();
                p.setIdP(rs.getInt("idP"));
                p.setNazivProivodjaca(rs.getString("nazivProizvodjaca"));
                p.setNazivProizvoda(rs.getString("nazivProizvoda"));
                p.setKolicina(rs.getInt("kolicina"));
                p.setDatumPorudzbine(rs.getDate("datumPorudzbine"));
                p.setStatus(rs.getString("status"));
                p.setImeKupca(rs.getString("imeKupca"));
                p.setIznos(rs.getInt("iznos"));

                porudzbine.add(p);
            }
            return porudzbine;
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

}
