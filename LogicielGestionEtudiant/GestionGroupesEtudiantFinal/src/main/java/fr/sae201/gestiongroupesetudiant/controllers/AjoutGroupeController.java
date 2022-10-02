package fr.sae201.gestiongroupesetudiant.controllers;

import fr.sae201.gestiongroupesetudiant.models.groupe.Groupe;
import fr.sae201.gestiongroupesetudiant.models.groupe.GroupeDAO;
import fr.sae201.gestiongroupesetudiant.models.groupe.GroupeType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AjoutGroupeController implements Initializable {
    private GroupesController groupesController;
    public static GroupeType groupeTypeACreer;
    private GroupeDAO groupeDAO;

    @FXML
    private TextField nomGroupeTextField, nomGroupeTpTextField;

    @FXML
    private Label titreLabel, titre2Label;

    @FXML
    private MenuButton tdParentMenu;

    private String tdChoisi;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (groupeTypeACreer == GroupeType.TD) titreLabel.setText("Nom du groupe TD :");
        else titreLabel.setText("Nom du groupe TP :");

        groupeDAO = new GroupeDAO();
        groupeDAO.chargerGroupes();

        // Si le groupe à créer est un TP
        if (groupeTypeACreer == GroupeType.TP) {
            groupeDAO.getTdGroupes().forEach((td) -> {
                tdParentMenu.getItems().add(new MenuItem(td.getNom()));
            });
            tdChoisi = groupeDAO.getTdGroupes().get(0).getNom().substring(2);
            tdParentMenu.setText(tdChoisi);
            tdParentMenu.getItems().forEach(menuButton -> {
                menuButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String selected = ((MenuItem) event.getSource()).getText();

                        tdParentMenu.setText(selected);
                        tdChoisi = selected.substring(2);
                    }
                });
            });
        // Si c'est un TD
        } else {
            tdParentMenu.setVisible(false);
            titre2Label.setVisible(true);
            nomGroupeTpTextField.setVisible(true);
        }
    }

    @FXML
    void ajouterGroupeOnClick(ActionEvent event) {
        if (groupeTypeACreer == GroupeType.TP)
            groupeDAO.insertGroupeTp(nomGroupeTextField.getText(), Integer.valueOf(tdChoisi));
        else
            groupeDAO.insertGroupeTd(nomGroupeTextField.getText().replace("TD", ""), nomGroupeTpTextField.getText());

        ((Node) event.getSource()).getScene().getWindow().hide();

        groupesController.rechargerVue();
    }

    public void setController(GroupesController groupesController) {
        this.groupesController = groupesController;
    }
}
