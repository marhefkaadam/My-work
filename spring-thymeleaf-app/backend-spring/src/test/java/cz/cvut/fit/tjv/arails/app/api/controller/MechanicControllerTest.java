package cz.cvut.fit.tjv.arails.app.api.controller;

import cz.cvut.fit.tjv.arails.app.api.converter.MechanicConverter;
import cz.cvut.fit.tjv.arails.app.business.EntityStateException;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MechanicController.class)
class MechanicControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MechanicService mechanicService;
    @MockBean
    private ManufacturerService manufacturerService;

    @MockBean
    private MechanicConverter mechanicConverter;

    private final Manufacturer manufacturer = new Manufacturer(50, "Tatravagónka", "tatra@gmail.com");
    private final Mechanic mechanic1 = new Mechanic(1, "name", "surname", "+420944873982", manufacturer);
    private final Mechanic mechanic2 = new Mechanic(2, "not name", "not surname", "+420944873982", manufacturer);

    @BeforeEach
    void setUp() {
        Collection<Mechanic> mechanics = new ArrayList<>();
        mechanics.add(mechanic1);
        mechanics.add(mechanic2);

        Mockito.when(mechanicService.readAll()).thenReturn(mechanics);

        Mockito.when(mechanicService.readById(1)).thenReturn(Optional.of(mechanic1));
        Mockito.when(mechanicService.readById(1000)).thenReturn(Optional.empty());

        mechanicConverter.setManufacturerService(manufacturerService);
        Mockito.when(manufacturerService.readById(manufacturer.getId())).thenReturn(Optional.of(manufacturer));
    }

    @Test
    void getAllMechanics() throws Exception {
        mockMvc.perform(get("/api/mechanics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[1].id", Matchers.is(2)));
        Mockito.verify(mechanicService, Mockito.times(1)).readAll();
    }

    @Test
    void getMechanic() throws Exception {
        mockMvc.perform(get("/api/mechanics/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.name", Matchers.is("name")))
                .andExpect(jsonPath("$.surname", Matchers.is("surname")))
                .andExpect(jsonPath("$.phoneNumber", Matchers.is("+420944873982")))
                .andExpect(jsonPath("$.manufacturerId", Matchers.is(50)));
        Mockito.verify(mechanicService, Mockito.times(1)).readById(1);


        mockMvc.perform(get("/api/mechanics/1000"))
                .andExpect(status().isNotFound());
        Mockito.verify(mechanicService, Mockito.times(1)).readById(1000);
    }

    @Test
    void addMechanic() throws Exception {
        mockMvc.perform(post("/api/mechanics")
                        .contentType("application/json")
                        .accept("application/json")
                        .content("{\"name\":\"not name\",\"surname\":\"not surname\",\"phoneNumber\":\"+420944873982\",\"manufacturerId\":\"50\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("not name")))
                .andExpect(jsonPath("$.surname", Matchers.is("not surname")))
                .andExpect(jsonPath("$.phoneNumber", Matchers.is("+420944873982")))
                .andExpect(jsonPath("$.manufacturerId", Matchers.is(50)));

        // because we don't know which id number would be generated
        mechanic2.setId(null);
        Mockito.verify(mechanicService, Mockito.times(1)).create(mechanic2);

        mockMvc.perform(post("/api/mechanics")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateMechanic() throws Exception {
        mockMvc.perform(put("/api/mechanics/2")
                        .contentType("application/json")
                        .accept("application/json")
                        .content("{\"name\":\"Kali\",\"surname\":\"Janko\",\"phoneNumber\":\"+420944873982\",\"manufacturerId\":\"50\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("Kali")))
                .andExpect(jsonPath("$.surname", Matchers.is("Janko")))
                .andExpect(jsonPath("$.phoneNumber", Matchers.is("+420944873982")))
                .andExpect(jsonPath("$.manufacturerId", Matchers.is(50)));

        mechanic2.setName("Kali");
        mechanic2.setSurname("Janko");
        Mockito.verify(mechanicService, Mockito.times(1)).update(mechanic2);

        mockMvc.perform(put("/api/mechanics/2")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteMechanic() throws Exception {
        mockMvc.perform(delete("/api/mechanics/1"))
                .andExpect(status().isNoContent());
        Mockito.verify(mechanicService, Mockito.times(1)).deleteById(1);
    }
}