package cz.cvut.fit.tjv.arails.app.dao;

import cz.cvut.fit.tjv.arails.app.domain.Manufacturer;
import cz.cvut.fit.tjv.arails.app.domain.Mechanic;
import cz.cvut.fit.tjv.arails.app.domain.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;


@Repository
public interface TrainJpaRepository extends JpaRepository<Train, Integer> {

}
