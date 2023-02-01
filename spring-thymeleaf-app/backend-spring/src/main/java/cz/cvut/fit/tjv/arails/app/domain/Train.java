package cz.cvut.fit.tjv.arails.app.domain;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "zeleznicne_vozidlo")
public class Train {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "zeleznicne_vozidlo_id_zeleznicne_vozidlo_seq")
    @SequenceGenerator(name = "zeleznicne_vozidlo_id_zeleznicne_vozidlo_seq", allocationSize = 1)
    @Column(name = "id_zeleznicne_vozidlo")
    private Integer id;

    @Column(name = "max_rychlost", nullable = false)
    private Integer maxSpeed;

    @Column(name = "rok_vyroby", nullable = false)
    private Integer productionYear;

    @Column(name = "platnost_revizie")
    private Date revisionValidity;

    @ManyToOne
    @JoinColumn(name = "id_vyrobca", nullable = false)
    private Manufacturer manufacturer;

    @ManyToMany
    @JoinTable(name = "oprava",
               joinColumns = @JoinColumn(name = "id_zeleznicne_vozidlo"),
               inverseJoinColumns = @JoinColumn(name = "id_opravar"))
    private Set<Mechanic> repairedBy = new HashSet<>();

    public Train() {
    }

    public Train(Integer id, Integer maxSpeed, Integer productionYear, Date revisionValidity, Manufacturer manufacturer) {
        this.id = id;
        this.maxSpeed = maxSpeed;
        this.productionYear = productionYear;
        this.revisionValidity = revisionValidity;
        this.manufacturer = manufacturer;
    }

    public Train(Integer id, Integer maxSpeed, Integer productionYear, Manufacturer manufacturer, Set<Mechanic> repairedBy) {
        this.id = id;
        this.maxSpeed = maxSpeed;
        this.productionYear = productionYear;
        this.revisionValidity = null;
        this.manufacturer = manufacturer;
        this.repairedBy = repairedBy;
    }

    public Train(Integer id, Integer maxSpeed, Integer productionYear, Date revisionValidity, Manufacturer manufacturer, Set<Mechanic> repairedBy) {
        this.id = id;
        this.maxSpeed = maxSpeed;
        this.productionYear = productionYear;
        this.revisionValidity = revisionValidity;
        this.manufacturer = manufacturer;
        this.repairedBy = repairedBy;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMaxSpeed(Integer maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
    }

    public void setRevisionValidity(Date revisionValidity) {
        this.revisionValidity = revisionValidity;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setRepairedBy(Set<Mechanic> repairedBy) {
        this.repairedBy = repairedBy;
    }

    public Integer getId() {
        return id;
    }

    public Integer getMaxSpeed() {
        return maxSpeed;
    }

    public Integer getProductionYear() {
        return productionYear;
    }

    public Date getRevisionValidity() {
        return revisionValidity;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public Set<Mechanic> getRepairedBy() {
        return repairedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Train)) return false;
        Train train = (Train) o;
        return Objects.equals(getId(), train.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Train{" +
                "id=" + id +
                ", maxSpeed=" + maxSpeed +
                ", productionYear=" + productionYear +
                ", revisionValidity=" + revisionValidity +
                ", manufacturer=" + manufacturer +
                '}';
    }
}
