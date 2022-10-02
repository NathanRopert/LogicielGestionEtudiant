package fr.sae201.gestiongroupesetudiant.controllers;

import fr.sae201.gestiongroupesetudiant.models.groupe.GroupeDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmerSuppressionController implements Initializable {
    public static String groupeASupprimer;
    private GroupesController groupesController;

    @FXML
    private Label titreLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        titreLabel.setText("Êtes-vous sûr de vouloir supprimer le groupe " + groupeASupprimer + " ?");
    }

    @FXML
    void confirmerOnClick(ActionEvent e) {
        // Suppression du groupe
        GroupeDAO groupeDAO = new GroupeDAO();
        groupeDAO.deleteGroupe(groupeASupprimer);

        ((Node) e.getSource()).getScene().getWindow().hide();
        groupesController.rechargerVue();
    }

    @FXML
    void annulerOnClick(ActionEvent e) {
        ((Node) e.getSource()).getScene().getWindow().hide();
    }

    public void setController(GroupesController groupesController) {
        this.groupesController = groupesController;
    }
}
