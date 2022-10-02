package fr.sae201.gestiongroupesetudiant.controllers;

import fr.sae201.gestiongroupesetudiant.HelloApplication;
import fr.sae201.gestiongroupesetudiant.models.etudiant.Etudiant;
import fr.sae201.gestiongroupesetudiant.models.etudiant.EtudiantDAO;
import fr.sae201.gestiongroupesetudiant.models.personnel.Personnel;
import fr.sae201.gestiongroupesetudiant.models.personnel.PersonnelDAO;
import fr.sae201.gestiongroupesetudiant.utils.Utils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConnexionController implements Initializable {

    private boolean isEtudiant = true;

    @FXML
    private RadioButton etudiantRadio, personnelRadio;

    @FXML
    private TextField nomTextField, motDePasseTextField;

    @FXML
    public Label erreurLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup radioGroup = new ToggleGroup();

        etudiantRadio.setToggleGroup(radioGroup);
        personnelRadio.setToggleGroup(radioGroup);

        radioGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                isEtudiant = ((RadioButton) radioGroup.getSelectedToggle()).getText().equals("ÉTUDIANT");
                System.out.println(isEtudiant);
            }
        });
    }

    @FXML
    void connectOnClick(ActionEvent e) throws IOException {
        String nomUtilisateur = nomTextField.getText();
        String motDePasse = motDePasseTextField.getText();
        String motDePasseChiffre = null;

        // Si c'est un étudiant qui se connecte
        if (isEtudiant) {
            EtudiantDAO etudiantDAO = new EtudiantDAO();
            Etudiant etudiantConnecte = null;

            try {
                // Bon mot de passe ou non
                motDePasseChiffre = etudiantDAO.getPassword(nomTextField.getText());
                if (Utils.verifyPassword(motDePasse, motDePasseChiffre, Utils.cleDeChiffrement)) {
                    etudiantConnecte = etudiantDAO.logEtudiant(Integer.parseInt(nomUtilisateur), motDePasseChiffre);
                }
            } catch(NumberFormatException ex) {
                erreurLabel.setText("L'identifiant doit être un nombre !");
                erreurLabel.setVisible(true);
                return;
            }

            if (etudiantConnecte == null) {
                erreurLabel.setText("Les identifiants sont incorrects, réessayez !");
                erreurLabel.setVisible(true);
                return;
            }

            MesInformationsController.humainActuel = etudiantConnecte;
        }
        // Si c'est un personnel ou un enseignant
        else {
            PersonnelDAO personnelDAO = new PersonnelDAO();
            Personnel personnelConnecte = null;

            try {
                // Bon mot de passe ou non
                motDePasseChiffre = personnelDAO.getPassword(nomTextField.getText());
                if (Utils.verifyPassword(motDePasse, motDePasseChiffre, Utils.cleDeChiffrement)) {
                    personnelConnecte = personnelDAO.logPersonnel(Integer.parseInt(nomUtilisateur), motDePasseChiffre);
                }
            } catch(NumberFormatException ex) {
                erreurLabel.setText("L'identifiant doit être un nombre !");
                erreurLabel.setVisible(true);
                return;
            }

            if (personnelConnecte == null) {
                erreurLabel.setText("Les identifiants sont incorrects, réessayez !");
                erreurLabel.setVisible(true);
                return;
            }

            MesInformationsController.humainActuel = personnelConnecte;
        }

        chargerFenetre(e);
    }

    private void chargerFenetre(ActionEvent e) throws IOException {
        // Chargement de la fenêtre de liste des étudiants
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ListeEtudiantView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setTitle("Liste des étudiants");
        stage.setScene(scene);
        stage.show();
    }
}
