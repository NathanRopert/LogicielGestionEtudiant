package fr.sae201.gestiongroupesetudiant.models.groupe;

public class Groupe {
    private String nom;
    private GroupeType type;

    public Groupe(String nom, GroupeType type) {
        this.nom = nom;
        this.type = type;
    }

    public String getNom() {
        return this.nom;
    }
    public GroupeType getType() { return this.type; }
}
