package cz.cvut.fit.tjv.arails.app.dao;

import cz.cvut.fit.tjv.arails.app.domain.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


@Repository
public interface ManufacturerJpaRepository extends JpaRepository<Manufacturer, Integer> {
    /**
     * Gets all manufacturers which have manufactured a train in selected year.
     * @param production_year -
     * @return
     */
    @Query("SELECT DISTINCT t.manufacturer FROM Train t WHERE t.productionYear = :production_year")
    Collection<Manufacturer> findManufacturersByProductionYear(Integer production_year);

}
