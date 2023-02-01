package cz.cvut.fit.tjv.arails.app.api.dto;

import java.lang.Integer;

public class MechanicDto {
    private Integer id;
    private String name;
    private String surname;
    private String phoneNumber;
    private Integer manufacturerId;

    MechanicDto() {
    }

    public MechanicDto(Integer id, String name, String surname, String phoneNumber, Integer manufacturerId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.manufacturerId = manufacturerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Integer manufacturerId) { this.manufacturerId = manufacturerId; }
}
