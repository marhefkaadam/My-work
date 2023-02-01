package cz.cvut.fit.tjv.arails.app.api.controller;

import cz.cvut.fit.tjv.arails.app.api.converter.MechanicConverter;
import cz.cvut.fit.tjv.arails.app.api.dto.MechanicDto;
import cz.cvut.fit.tjv.arails.app.api.exception.NoEntityFoundException;
import cz.cvut.fit.tjv.arails.app.business.ManufacturerService;
import cz.cvut.fit.tjv.arails.app.business.MechanicService;
import cz.cvut.fit.tjv.arails.app.domain.Mechanic;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class MechanicController {
    private final MechanicService mechanicService;

    public MechanicController(MechanicService mechanicService) {
        this.mechanicService = mechanicService;
    }

    @GetMapping("/api/mechanics")
    Collection<MechanicDto> getAllMechanics() {
        return MechanicConverter.fromModelMany(mechanicService.readAll());
    }

    @GetMapping("/api/mechanics/{id}")
    MechanicDto getMechanic(@PathVariable Integer id) {
        return MechanicConverter.fromModel(mechanicService.readById(id).orElseThrow(NoEntityFoundException::new));
    }

    @PostMapping("/api/mechanics")
    MechanicDto addMechanic(@RequestBody MechanicDto newMechanic) {
        Mechanic mechanic = MechanicConverter.toModel(newMechanic);
        mechanicService.create(mechanic);

        return MechanicConverter.fromModel(mechanic);
    }

    @PutMapping("/api/mechanics/{id}")
    MechanicDto updateMechanic(@PathVariable Integer id, @RequestBody MechanicDto updateMechanic) {
        Mechanic mechanic = MechanicConverter.toModel(id, updateMechanic);
        mechanicService.update(mechanic);

        return MechanicConverter.fromModel(mechanic);
    }

    @DeleteMapping("/api/mechanics/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteMechanic(@PathVariable Integer id) {
        mechanicService.deleteById(id);
    }

}
