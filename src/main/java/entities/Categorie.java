package entities;

public class Categorie {
    private int categorieId;
    private String categorieName;
    private String nombreJoueur;
    private String date;
    private String typeCategorie;
    private float gain;

    public Categorie(){

    }

    public Categorie(int categorieId, String categorieName, String nombreJoueur, String date, String typeCategorie, float gain) {
        this.categorieId = categorieId;
        this.categorieName = categorieName;
        this.nombreJoueur = nombreJoueur;
        this.date = date;
        this.typeCategorie = typeCategorie;
        this.gain = gain;
    }

    public Categorie(String categorieName, String nombreJoueur, String date, String typeCategorie, float gain) {
        this.categorieName = categorieName;
        this.nombreJoueur = nombreJoueur;
        this.date = date;
        this.typeCategorie = typeCategorie;
        this.gain = gain;
    }

    public int getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(int categorieId) {
        this.categorieId = categorieId;
    }

    public String getCategorieName() {
        return categorieName;
    }

    public void setCategorieName(String categorieName) {
        this.categorieName = categorieName;
    }

    public String getNombreJoueur() {
        return nombreJoueur;
    }

    public void setNombreJoueur(String nombreJoueur) {
        this.nombreJoueur = nombreJoueur;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "categorieId=" + categorieId +
                ", categorieName='" + categorieName + '\'' +
                ", nombreJoueur='" + nombreJoueur + '\'' +
                ", date='" + date + '\'' +
                ", typeCategorie='" + typeCategorie + '\'' +
                ", gain=" + gain +
                '}';
    }

    public String getTypeCategorie() {
        return typeCategorie;
    }

    public void setTypeCategorie(String typeCategorie) {
        this.typeCategorie = typeCategorie;
    }

    public float getGain() {
        return gain;
    }

    public void setGain(float gain) {
        this.gain = gain;
    }
}
