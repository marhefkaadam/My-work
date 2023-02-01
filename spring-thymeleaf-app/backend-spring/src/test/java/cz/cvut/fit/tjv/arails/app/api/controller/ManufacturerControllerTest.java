package cz.cvut.fit.tjv.arails.app.api.controller;

import cz.cvut.fit.tjv.arails.app.api.converter.ManufacturerConverter;
import cz.cvut.fit.tjv.arails.app.api.converter.MechanicConverter;
import cz.cvut.fit.tjv.arails.app.business.ManufacturerService;
import cz.cvut.fit.tjv.arails.app.business.MechanicService;
import cz.cvut.fit.tjv.arails.app.domain.Manufacturer;
import cz.cvut.fit.tjv.arails.app.domain.Mechanic;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ManufacturerController.class)
class ManufacturerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ManufacturerService manufacturerService;

    private final Manufacturer manufacturer1 = new Manufacturer(1, "Tatravagónka", "tatra@gmail.com");
    private final Manufacturer manufacturer2 = new Manufacturer(2, "Vlaky Poprad", "vlakypoprad@gmail.com");
    private final Manufacturer manufacturerProdYear = new Manufacturer(3, "Siemens", "siemens@gmail.com");

    @BeforeEach
    void setUp() {
        Collection<Manufacturer> manufacturers = new ArrayList<>();
        manufacturers.add(manufacturer1);
        manufacturers.add(manufacturer2);

        Mockito.when(manufacturerService.readAll()).thenReturn(manufacturers);

        Mockito.when(manufacturerService.readById(1)).thenReturn(Optional.of(manufacturer1));
        Mockito.when(manufacturerService.readById(1000)).thenReturn(Optional.empty());

        Collection<Manufacturer> manufacturersByYear = new ArrayList<>();
        manufacturersByYear.add(manufacturerProdYear);
        Mockito.when(manufacturerService.getManufacturersByProductionYear(2000)).thenReturn(manufacturersByYear);
        Mockito.when(manufacturerService.getManufacturersByProductionYear(1900)).thenReturn(Collections.emptyList());
    }

    @Test
    void getAllManufacturers() throws Exception {
        mockMvc.perform(get("/api/manufacturers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[1].id", Matchers.is(2)));
        Mockito.verify(manufacturerService, Mockito.times(1)).readAll();
    }

    @Test
    void getManufacturer() throws Exception {
        mockMvc.perform(get("/api/manufacturers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.companyName", Matchers.is("Tatravagónka")))
                .andExpect(jsonPath("$.email", Matchers.is("tatra@gmail.com")));
        Mockito.verify(manufacturerService, Mockito.times(1)).readById(1);

        mockMvc.perform(get("/api/manufacturers/1000"))
                .andExpect(status().isNotFound());
        Mockito.verify(manufacturerService, Mockito.times(1)).readById(1000);
    }

    @Test
    void addManufacturer() throws Exception {
        mockMvc.perform(post("/api/manufacturers")
                        .contentType("application/json")
                        .accept("application/json")
                        .content("{\"companyName\":\"Vlaky Poprad\",\"email\":\"vlakypoprad@gmail.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyName", Matchers.is("Vlaky Poprad")))
                .andExpect(jsonPath("$.email", Matchers.is("vlakypoprad@gmail.com")));

        // because we don't know which id number would be generated
        manufacturer2.setId(null);
        Mockito.verify(manufacturerService, Mockito.times(1)).create(manufacturer2);

        mockMvc.perform(post("/api/manufacturers")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateManufacturer() throws Exception {
        mockMvc.perform(put("/api/manufacturers/2")
                        .contentType("application/json")
                        .accept("application/json")
                        .content("{\"companyName\":\"Vlaky Praha\",\"email\":\"vlakypraha@gmail.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyName", Matchers.is("Vlaky Praha")))
                .andExpect(jsonPath("$.email", Matchers.is("vlakypraha@gmail.com")));

        manufacturer2.setCompanyName("Vlaky Praha");
        manufacturer2.setEmail("vlakypraha@gmail.com");
        Mockito.verify(manufacturerService, Mockito.times(1)).update(manufacturer2);

        mockMvc.perform(put("/api/manufacturers/2")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteManufacturer() throws Exception {
        mockMvc.perform(delete("/api/manufacturers/1"))
                .andExpect(status().isNoContent());
        Mockito.verify(manufacturerService, Mockito.times(1)).deleteById(1);
    }

    @Test
    void getManufacturersByProductionYear() throws Exception {
        mockMvc.perform(get("/api/manufacturers/trains/2000").accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(3)))
                .andExpect(jsonPath("$[0].companyName", Matchers.is("Siemens")))
                .andExpect(jsonPath("$[0].email", Matchers.is("siemens@gmail.com")));
        Mockito.verify(manufacturerService, Mockito.times(1)).getManufacturersByProductionYear(2000);

        mockMvc.perform(get("/api/manufacturers/trains/1900").accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(0)));
        Mockito.verify(manufacturerService, Mockito.times(1)).getManufacturersByProductionYear(1900);
    }
}