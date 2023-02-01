package cz.cvut.fit.tjv.arails.app.api.controller;

import cz.cvut.fit.tjv.arails.app.api.converter.TrainConverter;
import cz.cvut.fit.tjv.arails.app.business.EntityStateException;
import cz.cvut.fit.tjv.arails.app.business.ManufacturerService;
import cz.cvut.fit.tjv.arails.app.business.MechanicService;
import cz.cvut.fit.tjv.arails.app.business.TrainService;
import cz.cvut.fit.tjv.arails.app.domain.Manufacturer;
import cz.cvut.fit.tjv.arails.app.domain.Mechanic;
import cz.cvut.fit.tjv.arails.app.domain.Train;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TrainController.class)
class TrainControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TrainService trainService;
    @MockBean
    private MechanicService mechanicService;
    @MockBean
    private ManufacturerService manufacturerService;
    @MockBean
    private TrainConverter trainConverter;

    private Set<Mechanic> mechanics = new HashSet<>();

    private Manufacturer manufacturer = new Manufacturer(50, "Tatravagónka", "tatra@gmail.com");
    private Manufacturer employer = new Manufacturer(40, "Siemens", "siemens@gmail.com");
    private Mechanic mechanic1 = new Mechanic(1, "name", "surname", "+420944873982", employer);
    private Mechanic mechanic2 = new Mechanic(2, "not name", "not surname", "+420944873982", employer);
    private Train train1 = new Train(1, 160, 2000, manufacturer, Set.of(mechanic1));
    private Train train2 = new Train(2, 160, 2000, manufacturer, Set.of(mechanic1, mechanic2));
    private Train train3 = new Train(3, 200, 2010, Date.valueOf("2050-12-12"), manufacturer, mechanics);
    private Train train3new = new Train(3, 300, 2010, Date.valueOf("2050-12-12"), manufacturer, mechanics);
    private Train train4 = new Train(4, 160, 2000, Date.valueOf("2030-12-12"), manufacturer, mechanics);
    private Train train4new = new Train(4, 160, 2000, Date.valueOf("2070-12-12"), manufacturer, mechanics);

    @BeforeEach
    void setUp() {

        Collection<Train> trains = new ArrayList<>();
        trains.add(train1);
        trains.add(train2);

        Mockito.when(trainService.readAll()).thenReturn(trains);

        Mockito.when(trainService.readById(1)).thenReturn(Optional.of(train1));
        Mockito.when(trainService.readById(2)).thenReturn(Optional.of(train2));
        Mockito.when(trainService.readById(3)).thenReturn(Optional.of(train3));
        Mockito.when(trainService.readById(4)).thenReturn(Optional.of(train4));
        Mockito.when(trainService.readById(1000)).thenReturn(Optional.empty());

        Mockito.when(mechanicService.readById(1)).thenReturn(Optional.of(mechanic1));
        Mockito.when(mechanicService.readById(2)).thenReturn(Optional.of(mechanic2));
        Mockito.when(mechanicService.readById(1000)).thenReturn(Optional.empty());

        trainConverter.setManufacturerService(manufacturerService);
        Mockito.when(manufacturerService.readById(manufacturer.getId())).thenReturn(Optional.of(manufacturer));
        Mockito.when(manufacturerService.readById(employer.getId())).thenReturn(Optional.of(employer));

        Mockito.when(trainService.updateTrain(train3new)).thenReturn(train3new);
        Mockito.when(trainService.updateTrain(train4new)).thenThrow(EntityStateException.class);

        Set<Mechanic> mechanics = new HashSet<>();
        mechanics.add(mechanic1);
        mechanics.add(mechanic2);
        Mockito.when(trainService.getAllMechanics(2)).thenReturn(mechanics);
        Mockito.when(trainService.getAllMechanics(1000)).thenThrow(EntityStateException.class);
    }

    @Test
    void getAllTrains() throws Exception {
        mockMvc.perform(get("/api/trains"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[1].id", Matchers.is(2)));
        Mockito.verify(trainService, Mockito.times(1)).readAll();
    }

    @Test
    void getTrain() throws Exception {
        mockMvc.perform(get("/api/trains/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.maxSpeed", Matchers.is(160)))
                .andExpect(jsonPath("$.productionYear", Matchers.is(2000)))
                .andExpect(jsonPath("$.manufacturerId", Matchers.is(50)));
        Mockito.verify(trainService, Mockito.times(1)).readById(1);

        mockMvc.perform(get("/api/trains/1000"))
                .andExpect(status().isNotFound());
        Mockito.verify(trainService, Mockito.times(1)).readById(1000);
    }

    @Test
    void addTrain() throws Exception {
        mockMvc.perform(post("/api/trains")
                        .contentType("application/json")
                        .accept("application/json")
                        .content("{\"maxSpeed\":\"200\",\"productionYear\":\"2010\",\"revisionValidity\":\"2050-12-12\",\"manufacturerId\":\"50\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maxSpeed", Matchers.is(200)))
                .andExpect(jsonPath("$.productionYear", Matchers.is(2010)))
                .andExpect(jsonPath("$.revisionValidity", Matchers.is("2050-12-12")))
                .andExpect(jsonPath("$.manufacturerId", Matchers.is(50)));

        // because we don't know which id number would be generated
        train3.setId(null);
        Mockito.verify(trainService, Mockito.times(1)).create(train3);

        mockMvc.perform(post("/api/trains")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateTrain() throws Exception {
        mockMvc.perform(put("/api/trains/3")
                        .contentType("application/json")
                        .accept("application/json")
                        .content("{\"maxSpeed\":\"300\",\"productionYear\":\"2010\",\"revisionValidity\":\"2050-12-12\",\"manufacturerId\":\"50\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maxSpeed", Matchers.is(300)))
                .andExpect(jsonPath("$.productionYear", Matchers.is(2010)))
                .andExpect(jsonPath("$.revisionValidity", Matchers.is("2050-12-12")))
                .andExpect(jsonPath("$.manufacturerId", Matchers.is(50)));
        Mockito.verify(trainService, Mockito.times(1)).updateTrain(train3new);

        mockMvc.perform(put("/api/trains/4")
                        .contentType("application/json")
                        .accept("application/json")
                        .content("{\"maxSpeed\":\"160\",\"productionYear\":\"2000\",\"revisionValidity\":\"2070-12-12\",\"manufacturerId\":\"50\"}"))
                .andExpect(status().isBadRequest());
        Assertions.assertThrows(EntityStateException.class, () -> trainService.updateTrain(train4new));

        mockMvc.perform(put("/api/trains/2")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(""))
                .andExpect(status().isBadRequest());

        mockMvc.perform(put("/api/trains/1000")
                        .contentType("application/json")
                        .accept("application/json")
                        .content("{\"maxSpeed\":\"160\",\"productionYear\":\"2000\",\"revisionValidity\":\"2070-12-12\",\"manufacturerId\":\"50\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteTrain() throws Exception {
        mockMvc.perform(delete("/api/trains/1"))
                .andExpect(status().isNoContent());
        Mockito.verify(trainService, Mockito.times(1)).deleteById(1);
    }

    @Test
    void addMechanic() throws Exception {
        mockMvc.perform(post("/api/trains/1/mechanics/2"))
                .andExpect(status().isNoContent());
        Mockito.verify(trainService, Mockito.times(1)).addMechanic(1, 2);

        mockMvc.perform(post("/api/trains/1000/mechanics/2"))
                .andExpect(status().isNotFound());
        Mockito.verify(trainService, Mockito.times(0)).addMechanic(1000, 2);

        mockMvc.perform(post("/api/trains/1/mechanics/1000"))
                .andExpect(status().isNotFound());
        Mockito.verify(trainService, Mockito.times(0)).addMechanic(1, 1000);
    }

    @Test
    void deleteMechanic() throws Exception {
        mockMvc.perform(delete("/api/trains/1/mechanics/1"))
                .andExpect(status().isNoContent());
        Mockito.verify(trainService, Mockito.times(1)).deleteMechanic(1, 1);

        mockMvc.perform(delete("/api/trains/1000/mechanics/2"))
                .andExpect(status().isNotFound());
        Mockito.verify(trainService, Mockito.times(0)).deleteMechanic(1000, 2);

        mockMvc.perform(delete("/api/trains/1/mechanics/1000"))
                .andExpect(status().isNotFound());
        Mockito.verify(trainService, Mockito.times(0)).deleteMechanic(1, 1000);
    }

    @Test
    void getAllMechanics() throws Exception {
        mockMvc.perform(get("/api/trains/2/mechanics")
                .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].name", Matchers.is("name")))
                .andExpect(jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(jsonPath("$[1].name", Matchers.is("not name")));
        Mockito.verify(trainService, Mockito.times(1)).getAllMechanics(2);

        mockMvc.perform(get("/api/trains/1000/mechanics"))
                .andExpect(status().isBadRequest());
        Mockito.verify(trainService, Mockito.times(1)).getAllMechanics(1000);
        Assertions.assertThrows(EntityStateException.class, () -> trainService.getAllMechanics(1000));
    }
}