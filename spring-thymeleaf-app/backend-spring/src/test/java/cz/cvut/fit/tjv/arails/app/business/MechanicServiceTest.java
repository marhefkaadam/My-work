package cz.cvut.fit.tjv.arails.app.business;

import cz.cvut.fit.tjv.arails.app.dao.MechanicJpaRepository;
import cz.cvut.fit.tjv.arails.app.domain.Manufacturer;
import cz.cvut.fit.tjv.arails.app.domain.Mechanic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MechanicServiceTest {
    @Autowired
    private MechanicService mechanicService;
    @MockBean
    private MechanicJpaRepository repository;

    private final Manufacturer manufacturer = new Manufacturer(50, "Tatravagónka", "tatra@gmail.com");
    private final Mechanic mechanicExists = new Mechanic(1, "name", "surname", "+420944873982", manufacturer);
    private final Mechanic mechanicNotExists = new Mechanic(2, "not name", "not surname", "+420944873982", manufacturer);


    @Test
    void exists() {
        Mockito.when(repository.existsById(mechanicExists.getId())).thenReturn(true);
        Mockito.when(repository.existsById(mechanicNotExists.getId())).thenReturn(false);

        Assertions.assertTrue(mechanicService.exists(mechanicExists));
        Mockito.verify(repository, Mockito.times(1)).existsById(mechanicExists.getId());

        Assertions.assertFalse(mechanicService.exists(mechanicNotExists));
        Mockito.verify(repository, Mockito.times(1)).existsById(mechanicNotExists.getId());
    }
}