package cz.cvut.fit.tjv.arails.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class TrainModel {
    private Integer id;
    private Integer maxSpeed;
    private Integer productionYear;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+1")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate revisionValidity;
    private Integer manufacturerId;
    private boolean error;

    public boolean isError() {
        return error;
    }

    public TrainModel() {
    }

    public TrainModel(TrainModel train, boolean error) {
        this.id = train.id;
        this.maxSpeed = train.maxSpeed;
        this.productionYear = train.productionYear;
        this.revisionValidity = train.revisionValidity;
        this.manufacturerId = train.manufacturerId;
        this.error = error;
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

    public LocalDate getRevisionValidity() {
        return revisionValidity;
    }

    public void setRevisionValidity(LocalDate revisionValidity) {
        this.revisionValidity = revisionValidity;
    }

    public Integer getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }
}
