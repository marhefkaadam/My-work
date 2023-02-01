package cz.cvut.fit.tjv.arails.app.business;

import cz.cvut.fit.tjv.arails.app.dao.TrainJpaRepository;
import cz.cvut.fit.tjv.arails.app.domain.Manufacturer;
import cz.cvut.fit.tjv.arails.app.domain.Mechanic;
import cz.cvut.fit.tjv.arails.app.domain.Train;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrainServiceTest {
    @Autowired
    private TrainService trainService;
    @MockBean
    private TrainJpaRepository repository;
    @MockBean
    private MechanicService mechanicService;

    private Set<Mechanic> mechanics = new HashSet<>();

    private Manufacturer manufacturer = new Manufacturer(50, "Tatravagónka", "tatra@gmail.com");
    private Manufacturer employer = new Manufacturer(40, "Siemens", "siemens@gmail.com");
    private Mechanic mechanic1 = new Mechanic(1, "name", "surname", "+420944873982", employer);
    private Mechanic mechanic2 = new Mechanic(2, "not name", "not surname", "+420944873982", employer);
    private Train trainExists = new Train(1, 160, 2000, manufacturer, Set.of(mechanic1, mechanic2));
    private Train trainNotExists = new Train(2, 160, 2000, manufacturer, Set.of(mechanic1, mechanic2));
    private Train train3 = new Train(3, 200, 2010, Date.valueOf("2050-12-12"), manufacturer, mechanics);
    private Train train4old = new Train(4, 160, 2000, Date.valueOf("2030-12-12"), manufacturer, mechanics);
    private Train train4new = new Train(4, 200, 2000, Date.valueOf("2040-1-1"), manufacturer, mechanics);
    private Train train5old = new Train(5, 160, 2000, manufacturer, mechanics);
    private Train train5new = new Train(5, 200, 2000, Date.valueOf("2040-1-1"), manufacturer, mechanics);

    @BeforeEach
    void setUp() {
        train3.getRepairedBy().add(mechanic1);
        train4old.getRepairedBy().add(mechanic2);

        Mockito.when(repository.existsById(trainExists.getId())).thenReturn(true);
        Mockito.when(repository.existsById(trainNotExists.getId())).thenReturn(false);
        Mockito.when(repository.existsById(train3.getId())).thenReturn(true);
        Mockito.when(repository.existsById(train4new.getId())).thenReturn(true);
        Mockito.when(repository.existsById(train5new.getId())).thenReturn(true);

        Mockito.when(repository.findById(trainExists.getId())).thenReturn(Optional.of(trainExists));
        Mockito.when(repository.findById(train3.getId())).thenReturn(Optional.of(train3));
        Mockito.when(repository.findById(train4old.getId())).thenReturn(Optional.of(train4old));
        Mockito.when(repository.findById(train5old.getId())).thenReturn(Optional.of(train5old));

        Mockito.when(mechanicService.readById(mechanic1.getId())).thenReturn(Optional.of(mechanic1));
        Mockito.when(mechanicService.readById(mechanic2.getId())).thenReturn(Optional.of(mechanic2));
    }

    @Test
    void exists() {
        Assertions.assertTrue(trainService.exists(trainExists));
        Mockito.verify(repository, Mockito.times(1)).existsById(trainExists.getId());

        Assertions.assertFalse(trainService.exists(trainNotExists));
        Mockito.verify(repository, Mockito.times(1)).existsById(trainNotExists.getId());
    }

    @Test
    void updateTrain() {
        Assertions.assertThrows(EntityStateException.class, () -> trainService.updateTrain(train4new));
        Mockito.verify(repository, Mockito.times(2)).findById(train4new.getId());

        Assertions.assertEquals(train3, trainService.updateTrain(train3));
        Mockito.verify(repository, Mockito.times(3)).findById(train3.getId());

        Assertions.assertThrows(EntityStateException.class, () -> trainService.updateTrain(train5new));
        Mockito.verify(repository, Mockito.times(2)).findById(train5new.getId());
    }

    @Test
    void addMechanic() {
        trainService.addMechanic(train3.getId(), mechanic2.getId());

        Mockito.verify(repository, Mockito.times(1)).findById(train3.getId());
        Mockito.verify(mechanicService, Mockito.times(1)).readById(mechanic2.getId());
        train3.getRepairedBy().add(mechanic2);
        Mockito.verify(repository, Mockito.times(1)).save(train3);
    }

    @Test
    void deleteMechanic() {
        trainService.deleteMechanic(train3.getId(), mechanic2.getId());

        Mockito.verify(repository, Mockito.times(1)).findById(train3.getId());
        Mockito.verify(mechanicService, Mockito.times(1)).readById(mechanic2.getId());
        train3.getRepairedBy().remove(mechanic2);
        Mockito.verify(repository, Mockito.times(1)).save(train3);
    }

    @Test
    void getAllMechanics() {
        Collection<Mechanic> repairedBy = new ArrayList<>(trainExists.getRepairedBy());

        Assertions.assertEquals(repairedBy, trainService.getAllMechanics(trainExists.getId()));
        Mockito.verify(repository, Mockito.times(1)).findById(trainExists.getId());
    }
}