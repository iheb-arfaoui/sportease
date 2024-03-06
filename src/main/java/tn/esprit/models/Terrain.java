/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.models;

import java.time.LocalDateTime;

public class Terrain {

    private int id;
    private int owner_id;
    private String name;
    private int capacity;
    private String sportType;
    private int rentPrice;
    private boolean disponibility;
    private String address;
    private String imageName;
    private LocalDateTime updatedAt;

    public Terrain() {

    }

    public Terrain(int id, int owner_id, String name, int capacity, String sportType, int rentPrice, boolean disponibility, String address, String imageName, LocalDateTime updatedAt) {
        this.id = id;
        this.owner_id = owner_id;
        this.name = name;
        this.capacity = capacity;
        this.sportType = sportType;
        this.rentPrice = rentPrice;
        this.disponibility = disponibility;

        this.address = address;

        this.imageName = imageName;
        this.updatedAt = updatedAt;
    }

    public Terrain(int owner_id, String name, int capacity, String sportType, int rentPrice, boolean disponibility, String address, String imageName, LocalDateTime updatedAt) {
        this.owner_id = owner_id;
        this.name = name;
        this.capacity = capacity;
        this.sportType = sportType;
        this.rentPrice = rentPrice;
        this.disponibility = disponibility;

        this.address = address;

        this.imageName = imageName;
        this.updatedAt = updatedAt;
    }

    public Terrain(Terrain other) {
        this.id = other.id;
        this.owner_id = other.owner_id;
        this.name = other.name;
        this.capacity = other.capacity;
        this.sportType = other.sportType;
        this.rentPrice = other.rentPrice;
        this.disponibility = other.disponibility;

        this.address = other.address;

        this.imageName = other.imageName;
        this.updatedAt = other.updatedAt;
    }

    @Override
    public String toString() {
        return "Terrain{" + "id=" + id + ", owner_id=" + owner_id + ", name=" + name + ", capacity=" + capacity + ", sportType=" + sportType + ", rentPrice=" + rentPrice + ", disponibility=" + disponibility + ", address=" + address + ", imageName=" + imageName + ", updatedAt=" + updatedAt + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getSportType() {
        return sportType;
    }

    public void setSportType(String sportType) {
        this.sportType = sportType;
    }

    public int getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(int rentPrice) {
        this.rentPrice = rentPrice;
    }

    public boolean isDisponibility() {
        return disponibility;
    }

    public void setDisponibility(boolean disponibility) {
        this.disponibility = disponibility;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
