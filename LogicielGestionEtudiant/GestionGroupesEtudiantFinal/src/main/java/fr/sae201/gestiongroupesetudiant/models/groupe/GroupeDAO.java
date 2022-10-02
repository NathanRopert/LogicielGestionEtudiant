package fr.sae201.gestiongroupesetudiant.models.groupe;

import fr.sae201.gestiongroupesetudiant.models.DAO;
import fr.sae201.gestiongroupesetudiant.models.etudiant.Etudiant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupeDAO implements DAO {
    private List<Groupe> tdGroupes = new ArrayList<Groupe>();
    private List<Groupe> tpGroupes = new ArrayList<Groupe>();

    public GroupeDAO() {
        super();
    }

    public void chargerGroupes() {
        this.tdGroupes.clear();
        this.tpGroupes.clear();

        String sqlTd = "SELECT DISTINCT id_td_groupe FROM GROUPE;";
        String sqlTp = "SELECT DISTINCT tp_groupe FROM GROUPE;";

        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(sqlTd);
            while (rs.next()) {
                this.tdGroupes.add(new Groupe("TD" + rs.getInt("id_td_groupe"), GroupeType.TD));
            }

            rs = stmt.executeQuery(sqlTp);
            while (rs.next()) {
                this.tpGroupes.add(new Groupe(rs.getString("tp_groupe"), GroupeType.TP));
            }

            stmt.close();
            rs.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public int getId(String nomGroupe) {
        int idGroupe = -1;
        String sql = "SELECT id_groupe FROM GROUPE WHERE tp_groupe = '" + nomGroupe + "';";

        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                idGroupe = rs.getInt("id_groupe");
            }

            stmt.close();
            rs.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return idGroupe;
    }

    public List<Groupe> getTpParTd(String nomTd) {
        List<Groupe> groupesRecuperes = new ArrayList<Groupe>();
        String sql = "SELECT tp_groupe FROM GROUPE WHERE id_td_groupe = '" + nomTd + "';";

        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                groupesRecuperes.add(new Groupe(rs.getString("tp_groupe"), GroupeType.TP));
            }

            stmt.close();
            rs.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return groupesRecuperes;
    }

    public int getNbGroupes(){
        String sql = "SELECT MAX(id_groupe) AS 'nb' FROM GROUPE";
        int nbGroupes = -1;

        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) nbGroupes = rs.getInt("nb") + 1;

            stmt.close();
            rs.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return nbGroupes;
    }

    public void insertGroupeTp(String nomGroupe, int tdParent) {
        String sql = "INSERT INTO GROUPE VALUES(" + getNbGroupes() + ", 'TD', " + tdParent + ", '" + nomGroupe + "');";

        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = con.createStatement();

            stmt.executeUpdate(sql);

            stmt.close();
            con.close();

            System.out.println("Groupe TP créé !");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void insertGroupeTd(String nomGroupe, String nomGroupeTp) {
        String sql = "INSERT INTO GROUPE VALUES(" + getNbGroupes() + ", 'TD', '" + nomGroupe + "', '" + nomGroupeTp + "');";

        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = con.createStatement();

            stmt.executeUpdate(sql);

            stmt.close();
            con.close();

            System.out.println("Groupe TD créé !");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void updateGroupeTp(String nouveauNom, String nomGroupe){
        String sql = "UPDATE GROUPE SET tp_groupe = '" + nouveauNom + "' WHERE tp_groupe = '" + nomGroupe + "'";
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = con.createStatement();

            stmt.executeUpdate(sql);

            stmt.close();
            con.close();

            System.out.println("Groupe TP modifié !");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void deleteGroupe(String nomGroupe) {
        String sql = "DELETE FROM GROUPE WHERE tp_groupe = '" + nomGroupe + "';";
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = con.createStatement();

            stmt.executeUpdate(sql);

            stmt.close();
            con.close();

            System.out.println("Groupe " + nomGroupe + " supprimé !");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Groupe> getTdGroupes() {
        return this.tdGroupes;
    }

    public List<Groupe> getTpGroupes() {
        return this.tpGroupes;
    }
}
