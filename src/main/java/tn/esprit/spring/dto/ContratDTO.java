package tn.esprit.spring.dto;

import java.util.Date;

public class ContratDTO {

    private int reference;
    private Date dateDebut;
    private String typeContrat;
    private float salaire;

    // Constructors
    public ContratDTO() {}

    public ContratDTO(int reference, Date dateDebut, String typeContrat, float salaire) {
        this.reference = reference;
        this.dateDebut = dateDebut;
        this.typeContrat = typeContrat;
        this.salaire = salaire;
    }

    // Getters and Setters
    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getTypeContrat() {
        return typeContrat;
    }

    public void setTypeContrat(String typeContrat) {
        this.typeContrat = typeContrat;
    }

    public float getSalaire() {
        return salaire;
    }

    public void setSalaire(float salaire) {
        this.salaire = salaire;
    }
}
