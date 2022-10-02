package fr.sae201.gestiongroupesetudiant.controllers;

import fr.sae201.gestiongroupesetudiant.HelloApplication;
import fr.sae201.gestiongroupesetudiant.models.Secretaire;
import fr.sae201.gestiongroupesetudiant.models.etudiant.Etudiant;
import fr.sae201.gestiongroupesetudiant.models.etudiant.EtudiantDAO;
import fr.sae201.gestiongroupesetudiant.models.groupe.GroupeDAO;
import fr.sae201.gestiongroupesetudiant.utils.EtudiantTableRow;
import fr.sae201.gestiongroupesetudiant.utils.Utils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.print.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static fr.sae201.gestiongroupesetudiant.HelloApplication.getResource;

public class TrombinoscopeController implements Initializable {
    private List<Etudiant> listeEtudiants = new ArrayList<Etudiant>();
    private EtudiantDAO etudiantDAO;
    private GroupeDAO groupeDAO;
    private String statutChoisi = "Tous statuts",
                   promoChoisie = "Toutes promos",
                   tdChoisi = "Tous TD",
                   tpChoisi = "Tous TP";

    @FXML
    private MenuButton statutMenu, promoMenu, tdMenu, tpMenu;

    @FXML
    private Button imprimerAppelBtn;

    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (MesInformationsController.humainActuel instanceof Etudiant) {
            promoMenu.setVisible(false);
            statutMenu.setVisible(false);

        }
        if (!(MesInformationsController.humainActuel instanceof Secretaire)) {
            imprimerAppelBtn.setVisible(false);
        }

        listeEtudiants.clear();
        etudiantDAO = new EtudiantDAO();
        etudiantDAO.chargerEtudiants();

        groupeDAO = new GroupeDAO();
        groupeDAO.chargerGroupes();

        // Menu promos
        promoMenu.getItems().addAll(
                new MenuItem("Toutes promos"),
                new MenuItem("BUT1"),
                new MenuItem("BUT2"),
                new MenuItem("BUT3"),
                new MenuItem("DUT")
        );
        promoMenu.getItems().forEach(menuButton -> {
            menuButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String selected = ((MenuItem) event.getSource()).getText();

                    promoMenu.setText(selected);
                    promoChoisie = selected;
                }
            });
        });

        // Menu TD
        tdMenu.getItems().add(new MenuItem("Tous TD"));
        groupeDAO.getTdGroupes().forEach((td) -> {
            tdMenu.getItems().add(new MenuItem(td.getNom()));
        });
        tdMenu.getItems().forEach(menuButton -> {
            menuButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String selected = ((MenuItem) event.getSource()).getText();

                    tdMenu.setText(selected);
                    tdChoisi = selected;
                }
            });
        });

        // Menu TP
        tpMenu.getItems().add(new MenuItem("Tous TP"));
        groupeDAO.getTpGroupes().forEach((tp) -> {
            tpMenu.getItems().add(new MenuItem(tp.getNom()));
        });
        tpMenu.getItems().forEach(menuButton -> {
            menuButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String selected = ((MenuItem) event.getSource()).getText();

                    tpMenu.setText(selected);
                    tpChoisi = selected;
                }
            });
        });

        // Si élève Etudiant -> Chargement des étudiants de sa promo
        // Sinon Chargement de tous les étudiants
        if (MesInformationsController.humainActuel instanceof Etudiant) {
            etudiantDAO.getEtudiantsParPromo(((Etudiant)MesInformationsController.humainActuel).getPromo()).forEach((etudiant) -> {
                listeEtudiants.add(etudiant);
            });
        } else {
            etudiantDAO.getEtudiants().forEach((etudiant) -> {
                listeEtudiants.add(etudiant);
            });
        }

        rechargerVue();
    }

    private void rechargerVue() {
        // Rechargement de la vue des étudiants
        scrollPane.setContent(null);
        VBox list = new VBox();
        int nbEtudiants = 0;
        HBox ligne = new HBox();
        ligne.setSpacing(10);
        ligne.setPadding(new Insets(10));
        for (Etudiant etu : listeEtudiants) {
            if (nbEtudiants < 4) {

                VBox profil = new VBox();

                ImageView imageProfil = new ImageView(new Image(getResource((etu.getUrlPhoto() == null || etu.getUrlPhoto().equals("")) ? "images/profile-photo.jpeg" : "images/" + etu.getUrlPhoto())));
                imageProfil.fitWidthProperty().set(150);
                imageProfil.fitHeightProperty().set(150);

                Button modifierInfos = new Button("Modifier");
                modifierInfos.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            ouvrirModifierEtudiantFenetre(etu);
                        } catch (IOException e) {
                            System.out.println(e);
                        }
                    }
                });

                profil.getChildren().addAll(
                        imageProfil,
                        new Label(etu.getNom() + " " + etu.getPrenom())
                );
                if (MesInformationsController.humainActuel instanceof Secretaire) {
                    profil.getChildren().add(modifierInfos);
                }

                ligne.getChildren().add(profil);
                nbEtudiants++;
            } else {
                nbEtudiants = 0;
                list.getChildren().add(ligne);
                ligne = new HBox();
                ligne.setSpacing(10);
                ligne.setPadding(new Insets(10));
            }
        }
        list.getChildren().add(ligne);
        scrollPane.setContent(list);
    }

    @FXML
    void filtrerOnClick() {
        // Filtrer les étudiants
        if (statutChoisi != "Tous statuts") this.listeEtudiants.removeIf((etu) -> !etu.getStatut().equalsIgnoreCase(statutChoisi));
        if (promoChoisie != "Toutes promos") this.listeEtudiants.removeIf((etu) -> !etu.getPromo().equals(promoChoisie));
        if (tdChoisi != "Tous TD") this.listeEtudiants.removeIf((etu) -> etu.getTdGroupe() != Integer.parseInt(tdChoisi.substring(2)));
        if (tpChoisi != "Tous TP") this.listeEtudiants.removeIf((etu) -> !etu.getTpGroupe().equals(tpChoisi));
        rechargerVue();
    }

    @FXML
    void rechargerDonneesOnClick() {
        // Rechargement des étudiants
        listeEtudiants.clear();
        etudiantDAO.chargerEtudiants();

        statutMenu.setText("Tous statuts");
        promoMenu.setText("Toutes promos");
        tdMenu.setText("Tous TD");
        tpMenu.setText("Tous TP");

        if (MesInformationsController.humainActuel instanceof Etudiant) {
            etudiantDAO.getEtudiantsParPromo(((Etudiant)MesInformationsController.humainActuel).getPromo()).forEach((etudiant) -> {
                listeEtudiants.add(etudiant);
            });
        } else {
            etudiantDAO.getEtudiants().forEach((etudiant) -> {
                listeEtudiants.add(etudiant);
            });
        }

        rechargerVue();
    }

    @FXML
    void listeEtuOnClick(ActionEvent e) throws IOException {
        Utils.chargerListeEtudiants(e);
    }

    @FXML
    void groupesOnClick(ActionEvent e) throws IOException {
        Utils.chargerGroupes(e);
    }

    @FXML
    void infosOnClick(ActionEvent e) throws IOException {
        Utils.chargerMesInformations(e);
    }

    @FXML
    void trombiOnClick(ActionEvent e) throws IOException {
        return;
    }

    @FXML
    void disconnectOnClick(ActionEvent e) throws IOException {
        Utils.disconnect(e);
    }

    private void ouvrirModifierEtudiantFenetre(Etudiant etu) throws IOException {
        // Ouverture de la fenêtre pour modifier un étudiant
        ModifierEtudiantController.etudiantAModifier = etu;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ModifierEtudiantView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle(ModifierEtudiantController.etudiantAModifier.getNom() + " " + ModifierEtudiantController.etudiantAModifier.getPrenom());
        stage.setScene(scene);
        stage.show();

        ((ModifierEtudiantController) fxmlLoader.getController()).setTrombiController(this);
    }

    @FXML
    void imprimerAppelOnClick() {
        // Imprimer la feuille d'appel
        ChoiceDialog dialog = new ChoiceDialog(Printer.getDefaultPrinter(), Printer.getAllPrinters());
        dialog.setHeaderText("Choisissez une imprimante");
        dialog.setContentText("Choisissez une imprimante dans la liste");
        dialog.setTitle("Choix disponibles");
        Optional<Printer> opt = dialog.showAndWait();
        if (opt.isPresent()) {
            Printer printer = opt.get();
            PrinterJob job = PrinterJob.createPrinterJob();
            if (job != null) {
                System.out.println(job.jobStatusProperty().asString());

                JobSettings jobSettings = job.getJobSettings();
                PageLayout pageLayout = job.getPrinter().createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.EQUAL);
                jobSettings.setPageLayout(pageLayout);

                VBox list = new VBox();

                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                Label titre = new Label("Feuille d'appel - " + format.format(LocalDateTime.now()));
                titre.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));

                ListView listView = new ListView();
                etudiantDAO.getEtudiants().forEach((etu) -> {
                    listView.getItems().add(etu.getNom() + " " + etu.getPrenom());
                });
                listView.setCellFactory(cell -> {
                    return new ListCell<String>() {
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item != null) {
                                setText(item);
                                setFont(Font.font(16));
                            }
                        }
                    };
                });

                list.getChildren().addAll(
                        titre,
                        listView
                );

                boolean printed = job.printPage(list);
                if (printed) {
                    job.endJob();
                } else {
                    System.out.println("Impression échouée.");
                }
            } else {
                System.out.println("Impossible d'imprimer.");
            }
        }
    }
}
