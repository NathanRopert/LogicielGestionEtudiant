package fr.sae201.gestiongroupesetudiant.models.personnel;

import fr.sae201.gestiongroupesetudiant.models.Humain;

public class Personnel extends Humain {
    private int numHarp;
    private String role;

    public Personnel(int numHarp, String nom, String prenom, String role) {
        super(nom, prenom);
        this.numHarp = numHarp;
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }

    public int getNumHarpege() {
        return this.numHarp;
    }
}
