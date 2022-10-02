module fr.sae201.gestiongroupesetudiant {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens fr.sae201.gestiongroupesetudiant to javafx.fxml;
    exports fr.sae201.gestiongroupesetudiant;
    exports fr.sae201.gestiongroupesetudiant.controllers;
    opens fr.sae201.gestiongroupesetudiant.controllers to javafx.fxml;
    opens fr.sae201.gestiongroupesetudiant.models to javafx.base;
    opens fr.sae201.gestiongroupesetudiant.utils to javafx.base;
    opens fr.sae201.gestiongroupesetudiant.models.etudiant to javafx.base;
    opens fr.sae201.gestiongroupesetudiant.models.groupe to javafx.base;
    opens fr.sae201.gestiongroupesetudiant.models.personnel to javafx.base;
}