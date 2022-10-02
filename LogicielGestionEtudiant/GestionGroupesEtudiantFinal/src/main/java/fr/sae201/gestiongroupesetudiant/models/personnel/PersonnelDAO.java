package fr.sae201.gestiongroupesetudiant.models.personnel;

import fr.sae201.gestiongroupesetudiant.models.DAO;
import fr.sae201.gestiongroupesetudiant.models.Enseignant;
import fr.sae201.gestiongroupesetudiant.models.Secretaire;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonnelDAO implements DAO {
    private List<Enseignant> listeEnseignants = new ArrayList<Enseignant>();
    private List<Secretaire> listeSecretaires = new ArrayList<Secretaire>();

    public PersonnelDAO() {
        super();
    }

    public void chargerPersonnel() {
        this.listeEnseignants.clear();
        String sql = "SELECT id_pers_harp, nom_pers, prenom_pers, nom_role FROM PERSONNEL INNER JOIN ROLE ON ROLE.id_role = PERSONNEL.id_role WHERE nom_role = 'Enseignant';";

        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if (rs.getString("nom_role").equals("Enseignant")) {
                    Enseignant enseignant = new Enseignant(
                            rs.getInt("id_pers_harp"),
                            rs.getString("nom_pers"),
                            rs.getString("prenom_pers")
                    );
                    listeEnseignants.add(enseignant);
                }
                else if (rs.getString("nom_role").equals("Secrétaire")) {
                    Secretaire secretaire = new Secretaire(
                            rs.getInt("id_pers_harp"),
                            rs.getString("nom_pers"),
                            rs.getString("prenom_pers")
                    );
                    listeSecretaires.add(secretaire);
                }
            }
            stmt.close();
            rs.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Personnel getPersonnel(int id_pers_harp) {
        Personnel personnelRecupere = null;
        String sql = "SELECT nom_pers, prenom_pers, nom_role FROM PERSONNEL INNER JOIN ROLE ON ROLE.id_role = PERSONNEL.id_role WHERE id_pers_harp = " + id_pers_harp + ";";

        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                if (rs.getString("nom_role").equals("Secrétaire"))
                    personnelRecupere = new Secretaire(
                            id_pers_harp,
                            rs.getString("nom_pers"),
                            rs.getString("prenom_pers")
                    );
                else if (rs.getString("nom_role").equals("Enseignant"))
                    personnelRecupere = new Enseignant(
                            id_pers_harp,
                            rs.getString("nom_pers"),
                            rs.getString("prenom_pers")
                    );
            }

            stmt.close();
            rs.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return personnelRecupere;
    }

    public Personnel logPersonnel(int id_pers_harp, String motDePasse) {
        Personnel personnelRecupere = null;
        String sql = "SELECT * FROM PERSONNEL INNER JOIN ROLE ON ROLE.id_role = PERSONNEL.id_role WHERE id_pers_harp = " + id_pers_harp + " AND mdp_pers = '" + motDePasse + "';";

        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                if (rs.getString("nom_role").equals("Secrétaire"))
                    personnelRecupere = new Secretaire(
                            id_pers_harp,
                            rs.getString("nom_pers"),
                            rs.getString("prenom_pers")
                    );
                else if (rs.getString("nom_role").equals("Enseignant"))
                    personnelRecupere = new Enseignant(
                            id_pers_harp,
                            rs.getString("nom_pers"),
                            rs.getString("prenom_pers")
                    );
            }

            stmt.close();
            rs.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return personnelRecupere;
    }

    public String getPassword(String idPersHarp) {
        String mdp = null;
        String sql = "SELECT mdp_pers FROM PERSONNEL WHERE id_pers_harp = " + idPersHarp + ";";

        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                mdp = rs.getString("mdp_pers");
            }
            stmt.close();
            rs.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return mdp;
    }

    public List<Enseignant> getEnseignants() {
        return this.listeEnseignants;
    }

    public List<Secretaire> getSecretaires() {
        return this.listeSecretaires;
    }
}
