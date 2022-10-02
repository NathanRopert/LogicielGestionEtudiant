package fr.sae201.gestiongroupesetudiant.models.etudiant;

import fr.sae201.gestiongroupesetudiant.models.DAO;
import fr.sae201.gestiongroupesetudiant.models.groupe.Groupe;
import fr.sae201.gestiongroupesetudiant.models.groupe.GroupeDAO;
import fr.sae201.gestiongroupesetudiant.utils.EtudiantTableRow;
import fr.sae201.gestiongroupesetudiant.utils.Utils;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class EtudiantDAO implements DAO {
    private List<Etudiant> listeEtudiants = new ArrayList<Etudiant>();

    public EtudiantDAO() {
        super();
    }

    public void chargerEtudiants() {
        this.listeEtudiants.clear();
        String sql = "SELECT * FROM ETUDIANT INNER JOIN GROUPE ON GROUPE.id_groupe = ETUDIANT.id_groupe";

        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Etudiant etudiant = new Etudiant(
                        rs.getInt("num_etu"),
                        rs.getString("nom_etu"),
                        rs.getString("prenom_etu"),
                        rs.getInt("age_etu"),
                        rs.getString("dateN_etu"),
                        rs.getString("statut_etu"),
                        rs.getString("promo_etu"),
                        rs.getString("tp_groupe"),
                        rs.getInt("id_td_groupe"),
                        rs.getString("url_photo_etu"),
                        rs.getString("desc_am_etu")
                );
                listeEtudiants.add(etudiant);
            }
            stmt.close();
            rs.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public int getNbEtudiants() {
        int nbEtudiants = 0;
        String sql = "SELECT MAX(id_etu) AS 'Nb' FROM ETUDIANT;";

        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                nbEtudiants = rs.getInt("Nb") + 1;
            }
            stmt.close();
            rs.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return nbEtudiants;
    }

    public void insertEtudiant(int numEtu, String nom, String prenom, LocalDate dateN, String statut, String desc_am, String url_photo, String promo, int id_groupe, String motDePasse) {
        int age = Period.between(dateN, LocalDate.now()).getYears();
        String motDePasseChiffre = Utils.hashPassword(motDePasse, Utils.cleDeChiffrement).get();
        String sql = "INSERT INTO" +
                "`ETUDIANT`(`id_etu`, `num_etu`, `nom_etu`, `prenom_etu`, `age_etu`, `dateN_etu`, `statut_etu`, `desc_am_etu`, `url_photo_etu`, `promo_etu`, `id_groupe`, `mdp_etu`)" +
                "VALUES('"
                + this.getNbEtudiants() + "', '"
                + numEtu + "', '"
                + nom + "', '"
                + prenom + "', '"
                + age + "', '"
                + dateN + "', '"
                + statut + "', '"
                + desc_am + "', '"
                + url_photo + "', '"
                + promo + "', '"
                + id_groupe + "', '"
                + motDePasseChiffre + "');";

        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = con.createStatement();

            stmt.executeUpdate(sql);

            stmt.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void ajouterEtudiantsDansGroupe(ObservableList<EtudiantTableRow> listeEtudiants, String nomGroupe) {
        GroupeDAO groupeDAO = new GroupeDAO();

        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = con.createStatement();

            String sql;
            for (EtudiantTableRow etu : listeEtudiants) {
                sql = "UPDATE `ETUDIANT` SET `id_groupe`= " + groupeDAO.getId(nomGroupe) + " WHERE num_etu = '" + etu.getNumEtu() + "';";
                stmt.executeUpdate(sql);
            }

            stmt.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Etudiant getEtudiant(int numEtu) {
        Etudiant etudiantRecupere = null;
        String sql = "SELECT * FROM ETUDIANT INNER JOIN GROUPE ON GROUPE.id_groupe = ETUDIANT.id_groupe WHERE num_etu = " + numEtu + ";";

        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                etudiantRecupere = new Etudiant(
                        rs.getInt("num_etu"),
                        rs.getString("nom_etu"),
                        rs.getString("prenom_etu"),
                        rs.getInt("age_etu"),
                        rs.getString("dateN_etu"),
                        rs.getString("statut_etu"),
                        rs.getString("promo_etu"),
                        rs.getString("tp_groupe"),
                        rs.getInt("id_td_groupe"),
                        rs.getString("url_photo_etu"),
                        rs.getString("desc_am_etu")
                );
            }
            stmt.close();
            rs.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return etudiantRecupere;
    }

    public String getPassword(String numEtu) {
        String mdp = null;
        String sql = "SELECT mdp_etu FROM ETUDIANT WHERE num_etu = " + numEtu + ";";

        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                mdp = rs.getString("mdp_etu");
            }
            stmt.close();
            rs.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return mdp;
    }

    public String getImage(int numEtu) {
        String nomFichier = null;
        String sql = "SELECT url_photo_etu FROM ETUDIANT WHERE num_etu = " + numEtu + ";";

        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                nomFichier = rs.getString("url_photo_etu");
            }
            stmt.close();
            rs.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return nomFichier;
    }

    public List<Etudiant> getEtudiantsParRecherche(String recherche) {
        List<Etudiant> etudiantsRecuperes = new ArrayList<Etudiant>();
        String sql = "SELECT * FROM ETUDIANT INNER JOIN GROUPE ON GROUPE.id_groupe = ETUDIANT.id_groupe WHERE nom_etu LIKE '%" + recherche + "%' OR prenom_etu LIKE '%" + recherche + "%' OR num_etu LIKE '%" + recherche + "%' OR age_etu LIKE '%" + recherche + "%';";

        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                etudiantsRecuperes.add(new Etudiant(
                        rs.getInt("num_etu"),
                        rs.getString("nom_etu"),
                        rs.getString("prenom_etu"),
                        rs.getInt("age_etu"),
                        rs.getString("dateN_etu"),
                        rs.getString("statut_etu"),
                        rs.getString("promo_etu"),
                        rs.getString("tp_groupe"),
                        rs.getInt("id_td_groupe"),
                        rs.getString("url_photo_etu"),
                        rs.getString("desc_am_etu")
                ));
            }
            stmt.close();
            rs.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return etudiantsRecuperes;
    }

    public List<Etudiant> getEtudiantsParPromo(String nomPromo) {
        List<Etudiant> etudiantsRecuperes = new ArrayList<Etudiant>();
        String sql = "SELECT * FROM ETUDIANT INNER JOIN GROUPE ON GROUPE.id_groupe = ETUDIANT.id_groupe WHERE promo_etu = '" + nomPromo + "';";

        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Etudiant etudiantRecupere = new Etudiant(
                        rs.getInt("num_etu"),
                        rs.getString("nom_etu"),
                        rs.getString("prenom_etu"),
                        rs.getInt("age_etu"),
                        rs.getString("dateN_etu"),
                        rs.getString("statut_etu"),
                        rs.getString("promo_etu"),
                        rs.getString("tp_groupe"),
                        rs.getInt("id_td_groupe"),
                        rs.getString("url_photo_etu"),
                        rs.getString("desc_am_etu")
                );

                etudiantsRecuperes.add(etudiantRecupere);
            }
            stmt.close();
            rs.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return etudiantsRecuperes;
    }

    public void supprimerEtudiant(int num_etu) {
        String sql = "DELETE FROM ETUDIANT WHERE num_etu = '" + num_etu + "'";

        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = con.createStatement();

            stmt.executeUpdate(sql);

            stmt.close();
            con.close();

            System.out.println("Étudiant supprimé !");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Etudiant> getEtudiantsParGroupe(String nomGroupe) {
        List<Etudiant> etudiantsRecuperes = new ArrayList<Etudiant>();
        String sql = "SELECT * FROM ETUDIANT INNER JOIN GROUPE ON GROUPE.id_groupe = ETUDIANT.id_groupe WHERE tp_groupe = '" + nomGroupe + "';";

        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Etudiant etudiantRecupere = new Etudiant(
                        rs.getInt("num_etu"),
                        rs.getString("nom_etu"),
                        rs.getString("prenom_etu"),
                        rs.getInt("age_etu"),
                        rs.getString("dateN_etu"),
                        rs.getString("statut_etu"),
                        rs.getString("promo_etu"),
                        rs.getString("tp_groupe"),
                        rs.getInt("id_td_groupe"),
                        rs.getString("url_photo_etu"),
                        rs.getString("desc_am_etu")
                );

                etudiantsRecuperes.add(etudiantRecupere);
            }
            stmt.close();
            rs.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return etudiantsRecuperes;
    }

    public Etudiant logEtudiant(int numEtu, String motDePasse) {
        Etudiant etudiantRecupere = null;
        String sql = "SELECT * FROM ETUDIANT INNER JOIN GROUPE ON GROUPE.id_groupe = ETUDIANT.id_groupe WHERE num_etu = '" + numEtu + "' AND mdp_etu = '" + motDePasse + "';";

        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                etudiantRecupere = new Etudiant(
                        rs.getInt("num_etu"),
                        rs.getString("nom_etu"),
                        rs.getString("prenom_etu"),
                        rs.getInt("age_etu"),
                        rs.getString("dateN_etu"),
                        rs.getString("statut_etu"),
                        rs.getString("promo_etu"),
                        rs.getString("tp_groupe"),
                        rs.getInt("id_td_groupe"),
                        rs.getString("url_photo_etu"),
                        rs.getString("desc_am_etu")
                );
            }
            stmt.close();
            rs.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return etudiantRecupere;
    }

    public void updateEtudiant(int numEtu, String nom, String prenom, String statut, String desc_am, String url_photo, String promo, int id_groupe, String motDePasse) {
        String motDePasseChiffre = Utils.hashPassword(motDePasse, Utils.cleDeChiffrement).get();
        String sql = "UPDATE `ETUDIANT` SET `nom_etu` = '"
                + nom + "', `prenom_etu` = '"
                + prenom + "', `statut_etu` = '"
                + statut + "', `desc_am_etu` = '"
                + desc_am + "', `url_photo_etu` = '"
                + url_photo + "', `promo_etu` = '"
                + promo + "', `id_groupe` = '"
                + id_groupe + "', `mdp_etu` = '"
                + motDePasseChiffre + "' WHERE num_etu = " + numEtu + ";";

        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = con.createStatement();

            stmt.executeUpdate(sql);

            stmt.close();
            con.close();

            System.out.println("Étudiant mis-à-jour !");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Etudiant> getEtudiants() {
        return this.listeEtudiants;
    }
}
