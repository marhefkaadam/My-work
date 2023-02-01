package cz.cvut.fit.tjv.arails.app.domain;

import cz.cvut.fit.tjv.arails.app.business.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "opravar")
public class Mechanic {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "opravar_id_opravar_seq")
    @SequenceGenerator(name = "opravar_id_opravar_seq", allocationSize = 1)
    @Column(name = "id_opravar")
    private Integer id;

    @Column(name = "meno", nullable = false)
    private String name;

    @Column(name = "priezvisko", nullable = false)
    private String surname;

    @Column(name = "telefon")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "id_vyrobca", nullable = false)
    private Manufacturer manufacturer;


    public Mechanic() {
    }

    public Mechanic(Integer id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = null;
        this.manufacturer = null;
    }

    public Mechanic(Integer id, String name, String surname, String phoneNumber, Manufacturer manufacturer) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.manufacturer = manufacturer;
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

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer employer) {
        this.manufacturer = employer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mechanic)) return false;
        Mechanic mechanic = (Mechanic) o;
        return getId() == mechanic.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Mechanic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
