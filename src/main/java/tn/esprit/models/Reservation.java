package tn.esprit.models;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class Reservation {
    private int id;
    private int terrainId;
    private int clientId;
    private Date dateRes;
    private LocalTime time;
    private String userName;
    private String phone;

    public Reservation(int id, int terrainId, int clientId, Date dateRes, LocalTime time, String userName, String phone) {
        this.id = id;
        this.terrainId = terrainId;
        this.clientId = clientId;
        this.dateRes = dateRes;
        this.time = time;
        this.userName = userName;
        this.phone = phone;
    }

    public Reservation(int terrainId, int clientId, Date dateRes, LocalTime time, String userName, String phone) {
        this.terrainId = terrainId;
        this.clientId = clientId;
        this.dateRes = dateRes;
        this.time = time;
        this.userName = userName;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", terrainId=" + terrainId +
                ", clientId=" + clientId +
                ", dateRes=" + dateRes +
                ", time='" + time + '\'' +
                ", userName='" + userName + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTerrainId() {
        return terrainId;
    }

    public void setTerrainId(int terrainId) {
        this.terrainId = terrainId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public Date getDateRes() {
        return dateRes;
    }

    public void setDateRes(Date dateRes) {
        this.dateRes = dateRes;
    }


    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Reservation() {
    }
}