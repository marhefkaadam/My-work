package cz.cvut.fit.tjv.arails.app.api.controller;

import cz.cvut.fit.tjv.arails.app.api.converter.ManufacturerConverter;
import cz.cvut.fit.tjv.arails.app.api.converter.MechanicConverter;
import cz.cvut.fit.tjv.arails.app.api.dto.ManufacturerDto;
import cz.cvut.fit.tjv.arails.app.api.dto.MechanicDto;
import cz.cvut.fit.tjv.arails.app.api.exception.NoEntityFoundException;
import cz.cvut.fit.tjv.arails.app.business.ManufacturerService;
import cz.cvut.fit.tjv.arails.app.domain.Manufacturer;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class ManufacturerController {
    private final ManufacturerService manufacturerService;

    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping("/api/manufacturers")
    Collection<ManufacturerDto> getAllManufacturers() {
        return ManufacturerConverter.fromModelMany(manufacturerService.readAll());
    }

    @GetMapping("/api/manufacturers/{id}")
    ManufacturerDto getManufacturer(@PathVariable Integer id) {
        return ManufacturerConverter.fromModel(manufacturerService.readById(id).orElseThrow(NoEntityFoundException::new));
    }

    @PostMapping("/api/manufacturers")
    ManufacturerDto addManufacturer(@RequestBody ManufacturerDto newManufacturer) {
        Manufacturer manufacturer = ManufacturerConverter.toModel(newManufacturer);
        manufacturerService.create(manufacturer);

        return ManufacturerConverter.fromModel(manufacturer);
    }

    @PutMapping("/api/manufacturers/{id}")
    ManufacturerDto updateManufacturer(@PathVariable Integer id, @RequestBody ManufacturerDto updateManufacturer) {
        Manufacturer manufacturer = ManufacturerConverter.toModel(id, updateManufacturer);
        manufacturerService.update(manufacturer);

        return ManufacturerConverter.fromModel(manufacturer);
    }

    @DeleteMapping("/api/manufacturers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteManufacturer(@PathVariable Integer id) {
        manufacturerService.deleteById(id);
    }

    @GetMapping("/api/manufacturers/trains/{production_year}")
    Collection<ManufacturerDto> getManufacturersByProductionYear(@PathVariable Integer production_year) {
        return ManufacturerConverter.fromModelMany(manufacturerService.getManufacturersByProductionYear(production_year));
    }
}
