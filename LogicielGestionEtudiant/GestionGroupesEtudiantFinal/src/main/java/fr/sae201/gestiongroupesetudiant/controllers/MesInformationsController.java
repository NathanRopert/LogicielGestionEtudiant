package fr.sae201.gestiongroupesetudiant.controllers;

import fr.sae201.gestiongroupesetudiant.models.Enseignant;
import fr.sae201.gestiongroupesetudiant.models.etudiant.Etudiant;
import fr.sae201.gestiongroupesetudiant.models.Humain;
import fr.sae201.gestiongroupesetudiant.models.Secretaire;
import fr.sae201.gestiongroupesetudiant.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MesInformationsController implements Initializable {
    public static Humain humainActuel;

    @FXML
    private Label ageLabel, roleLabel, dateNLabel, nomLabel, numLabel, promoLabel, statutLabel, tdLabel, tpLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nomLabel.setText(humainActuel.getNom() + " " + humainActuel.getPrenom());

        if (humainActuel instanceof Etudiant) {
            Etudiant etudiantActuel = (Etudiant) humainActuel;

            // Chargement des informations
            numLabel.setText("Numéro : " + etudiantActuel.getNumEtu());
            ageLabel.setText(etudiantActuel.getAge() + " ans");
            dateNLabel.setText("Né le " + etudiantActuel.getDateN());
            statutLabel.setText("Statut : " + etudiantActuel.getStatut());
            promoLabel.setText("Promotion : " + etudiantActuel.getPromo());
            tdLabel.setText("Groupe TD : TD" + etudiantActuel.getTdGroupe());
            tpLabel.setText("Groupe TP : " + etudiantActuel.getTpGroupe());
            roleLabel.setText("Vous êtes : Étudiant");
        }
        else if (humainActuel instanceof Enseignant) {
            Enseignant enseignantActuel = (Enseignant) humainActuel;

            numLabel.setText("Numéro Harpège : " + enseignantActuel.getNumHarpege());

            roleLabel.setText("Vous êtes : Enseignant");
        }
        else if (humainActuel instanceof Secretaire) {
            Secretaire secretaireActuelle = (Secretaire) humainActuel;

            numLabel.setText("Numéro Harpège : " + secretaireActuelle.getNumHarpege());

            roleLabel.setText("Vous êtes : Secrétaire");
        }
    }

    @FXML
    void groupesOnClick(ActionEvent event) throws IOException {
        Utils.chargerGroupes(event);
    }

    @FXML
    void infosOnClick(ActionEvent event) {
        return;
    }

    @FXML
    void listeEtuOnClick(ActionEvent event) throws IOException {
        Utils.chargerListeEtudiants(event);
    }

    @FXML
    void trombiOnClick(ActionEvent e) throws IOException {
        Utils.chargerTrombinoscope(e);
    }

    @FXML
    void disconnectOnClick(ActionEvent e) throws IOException {
        Utils.disconnect(e);
    }
}
