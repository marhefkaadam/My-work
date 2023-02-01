package cz.cvut.fit.tjv.arails.app.domain;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "vyrobca")
public class Manufacturer {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "vyrobca_id_vyrobca_seq")
    @SequenceGenerator(name = "vyrobca_id_vyrobca_seq", allocationSize = 1)
    @Column(name = "id_vyrobca")
    private Integer id;

    @Column(name = "nazov", nullable = false)
    private String companyName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "telefon")
    private String phoneNumber;

    @OneToMany(mappedBy = "manufacturer")
    private List<Train> trains;

    public Manufacturer() {
    }

    public Manufacturer(Integer id, String companyName, String email) {
        this.id = id;
        this.companyName = companyName;
        this.email = email;
        this.phoneNumber = null;
    }

    public Manufacturer(Integer id, String companyName, String email, String phoneNumber) {
        this.id = id;
        this.companyName = companyName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public List<Train> getTrains() {
        return trains;
    }

    public void setTrains(List<Train> trains) {
        this.trains = trains;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Manufacturer)) return false;
        Manufacturer that = (Manufacturer) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Manufacturer{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
