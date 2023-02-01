package cz.cvut.fit.tjv.arails.app.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.lang.Integer;
import java.sql.Date;

public class TrainDto {
    private Integer id;
    private Integer maxSpeed;
    private Integer productionYear;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+1")
    private Date revisionValidity;
    private Integer manufacturerId;

    TrainDto() {
    }

    public TrainDto(Integer id, Integer maxSpeed, Integer productionYear, Date revisionValidity, Integer manufacturerId) {
        this.id = id;
        this.maxSpeed = maxSpeed;
        this.productionYear = productionYear;
        this.revisionValidity = revisionValidity;
        this.manufacturerId = manufacturerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Integer maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Integer getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
    }

    public Date getRevisionValidity() {
        return revisionValidity;
    }

    public void setRevisionValidity(Date revisionValidity) {
        this.revisionValidity = revisionValidity;
    }

    public Integer getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

}
