package entities;

import java.util.Date;

public class Evenement {
    private int evenementId;
    private String eventName;
    private String eventAbr;
    private String eventDate;
    private String status;
    private String eventAdress;
    private int categorieId;

    public Evenement(){

    }

    public Evenement(int evenementId, String eventName, String eventAbr, String eventDate, String status, String eventAdress, int categorieId) {
        this.evenementId = evenementId;
        this.eventName = eventName;
        this.eventAbr = eventAbr;
        this.eventDate = eventDate;
        this.status = status;
        this.eventAdress = eventAdress;
        this.categorieId = categorieId;
    }

    public Evenement(String eventName, String eventAbr, String eventDate, String status, String eventAdress, int categorieId) {
        this.eventName = eventName;
        this.eventAbr = eventAbr;
        this.eventDate = eventDate;
        this.status = status;
        this.eventAdress = eventAdress;
        this.categorieId = categorieId;
    }

    public int getEvenementId() {
        return evenementId;
    }

    public void setEvenementId(int evenementId) {
        this.evenementId = evenementId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventAbr() {
        return eventAbr;
    }

    public void setEventAbr(String eventAbr) {
        this.eventAbr = eventAbr;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEventAdress() {
        return eventAdress;
    }

    public void setEventAdress(String eventAdress) {
        this.eventAdress = eventAdress;
    }

    public int getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(int categorieId) {
        this.categorieId = categorieId;
    }
}
