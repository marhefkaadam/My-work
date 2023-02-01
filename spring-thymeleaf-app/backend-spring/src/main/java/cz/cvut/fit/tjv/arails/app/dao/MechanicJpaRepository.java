package cz.cvut.fit.tjv.arails.app.dao;

import cz.cvut.fit.tjv.arails.app.domain.Mechanic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;


@Repository
public interface MechanicJpaRepository extends JpaRepository<Mechanic, Integer> {
}
