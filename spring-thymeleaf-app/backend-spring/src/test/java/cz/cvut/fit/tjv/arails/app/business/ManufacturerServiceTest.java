package cz.cvut.fit.tjv.arails.app.business;

import cz.cvut.fit.tjv.arails.app.dao.ManufacturerJpaRepository;
import cz.cvut.fit.tjv.arails.app.domain.Manufacturer;

import java.util.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ManufacturerServiceTest {
    @Autowired
    private ManufacturerService manufacturerService;
    @MockBean
    private ManufacturerJpaRepository repository;

    private final Manufacturer manufacturerExists = new Manufacturer(50, "Tatravagónka", "tatra@gmail.com");
    private final Manufacturer manufacturerNotExists = new Manufacturer(100, "Tatravagónka", "tatra@gmail.com");
    private final Manufacturer manufacturerProdYear = new Manufacturer(99, "Siemens", "siemens@gmail.com");

    @Test
    void exists() {
        Mockito.when(repository.existsById(manufacturerExists.getId())).thenReturn(true);
        Mockito.when(repository.existsById(manufacturerNotExists.getId())).thenReturn(false);

        Assertions.assertTrue(manufacturerService.exists(manufacturerExists));
        Mockito.verify(repository, Mockito.times(1)).existsById(manufacturerExists.getId());

        Assertions.assertFalse(manufacturerService.exists(manufacturerNotExists));
        Mockito.verify(repository, Mockito.times(1)).existsById(manufacturerNotExists.getId());
    }

    @Test
    void getManufacturersByProductionYear() {
        Collection<Manufacturer> collection = new ArrayList<>();
        collection.add(manufacturerExists);
        collection.add(manufacturerProdYear);

        Mockito.when(repository.findManufacturersByProductionYear(2000)).thenReturn(collection);
        Mockito.when(repository.findManufacturersByProductionYear(1900)).thenReturn(Collections.emptyList());

        Assertions.assertEquals(manufacturerService.getManufacturersByProductionYear(2000), collection);
        Mockito.verify(repository, Mockito.times(1)).findManufacturersByProductionYear(2000);

        Assertions.assertEquals(manufacturerService.getManufacturersByProductionYear(1900), Collections.emptyList());
        Mockito.verify(repository, Mockito.times(1)).findManufacturersByProductionYear(1900);
    }
}