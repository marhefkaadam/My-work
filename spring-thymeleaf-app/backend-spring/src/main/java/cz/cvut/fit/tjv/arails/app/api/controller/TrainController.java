package cz.cvut.fit.tjv.arails.app.api.controller;

import cz.cvut.fit.tjv.arails.app.api.converter.MechanicConverter;
import cz.cvut.fit.tjv.arails.app.api.converter.TrainConverter;
import cz.cvut.fit.tjv.arails.app.api.dto.MechanicDto;
import cz.cvut.fit.tjv.arails.app.api.dto.TrainDto;
import cz.cvut.fit.tjv.arails.app.api.exception.NoEntityFoundException;
import cz.cvut.fit.tjv.arails.app.business.EntityStateException;
import cz.cvut.fit.tjv.arails.app.business.MechanicService;
import cz.cvut.fit.tjv.arails.app.business.TrainService;
import cz.cvut.fit.tjv.arails.app.domain.Mechanic;
import cz.cvut.fit.tjv.arails.app.domain.Train;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.Collection;
import java.util.Map;

@RestController
public class TrainController {
    private final TrainService trainService;
    private final MechanicService mechanicService;

    public TrainController(TrainService trainService, MechanicService mechanicService) {
        this.trainService = trainService;
        this.mechanicService = mechanicService;
    }

    @GetMapping("/api/trains")
    Collection<TrainDto> getAllTrains() {
        return TrainConverter.fromModelMany(trainService.readAll());
    }

    @GetMapping("/api/trains/{id}")
    TrainDto getTrain(@PathVariable Integer id) {
        return TrainConverter.fromModel(trainService.readById(id).orElseThrow(NoEntityFoundException::new));
    }

    @PostMapping("/api/trains")
    TrainDto addTrain(@RequestBody TrainDto newTrain) {
        Train train = TrainConverter.toModel(newTrain);
        trainService.create(train);

        return TrainConverter.fromModel(train);
    }

    @PutMapping("/api/trains/{id}")
    TrainDto updateTrain(@PathVariable Integer id, @RequestBody TrainDto updateTrain) {
        trainService.readById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Train with specified id not found." )
        );

        Train train = TrainConverter.toModel(id, updateTrain);

        return TrainConverter.fromModel(trainService.updateTrain(train));
    }

    @DeleteMapping("/api/trains/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTrain(@PathVariable Integer id) {
        trainService.deleteById(id);
    }

    @PostMapping("/api/trains/{id_train}/mechanics/{id_mechanic}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void addMechanic(@PathVariable Integer id_train, @PathVariable Integer id_mechanic) {
        trainService.readById(id_train).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Train with specified id not found." )
        );

        mechanicService.readById(id_mechanic).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Mechanic with specified id not found." )
        );


        trainService.addMechanic(id_train, id_mechanic);
    }

    @DeleteMapping("/api/trains/{id_train}/mechanics/{id_mechanic}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteMechanic(@PathVariable Integer id_train, @PathVariable Integer id_mechanic) {
        trainService.readById(id_train).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Train with specified id not found." )
        );

        mechanicService.readById(id_mechanic).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Mechanic with specified id not found." )
        );

        trainService.deleteMechanic(id_train, id_mechanic);
    }

    @GetMapping("/api/trains/{id}/mechanics")
    Collection<MechanicDto> getAllMechanics(@PathVariable Integer id) {
        return MechanicConverter.fromModelMany(trainService.getAllMechanics(id));
    }
}
