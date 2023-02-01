package cz.cvut.fit.tjv.arails.app.api.dto;

import java.lang.Integer;

public class ManufacturerDto {
    private Integer id;
    private String companyName;
    private String email;
    private String phoneNumber;

    ManufacturerDto() {
    }

    public ManufacturerDto(Integer id, String companyName, String email, String phoneNumber) {
        this.id = id;
        this.companyName = companyName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
