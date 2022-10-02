package fr.sae201.gestiongroupesetudiant.controllers;

import fr.sae201.gestiongroupesetudiant.models.groupe.GroupeDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifierGroupeController implements Initializable {
    private GroupesController groupesController;
    public static String groupeAModifier;
    private String nouveauNomGroupe;

    @FXML
    private TextField nouveauNomTextField;

    @FXML
    private Label nomTpLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nomTpLabel.setText("Nouveau nom du groupe " + groupeAModifier + " :");
    }

    @FXML
    void modifierGroupeOnClick(ActionEvent event) {
        // Modification du groupe
        GroupeDAO groupeDAO = new GroupeDAO();
        nouveauNomGroupe = nouveauNomTextField.getText();
        groupeDAO.updateGroupeTp(nouveauNomGroupe, groupeAModifier);

        ((Node) event.getSource()).getScene().getWindow().hide();
        groupesController.rechargerVue();
    }

    public void setController(GroupesController groupesController) {
        this.groupesController = groupesController;
    }
}
