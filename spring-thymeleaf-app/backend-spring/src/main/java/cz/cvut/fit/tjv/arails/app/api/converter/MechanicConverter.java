package cz.cvut.fit.tjv.arails.app.api.converter;

import cz.cvut.fit.tjv.arails.app.api.dto.MechanicDto;
import cz.cvut.fit.tjv.arails.app.api.exception.NoEntityFoundException;
import cz.cvut.fit.tjv.arails.app.business.EntityStateException;
import cz.cvut.fit.tjv.arails.app.business.ManufacturerService;
import cz.cvut.fit.tjv.arails.app.domain.Manufacturer;
import cz.cvut.fit.tjv.arails.app.domain.Mechanic;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class MechanicConverter {
    public static ManufacturerService manufacturerService;

    MechanicConverter(ManufacturerService manufacturerService) {
        MechanicConverter.manufacturerService = manufacturerService;
    }

    public static ManufacturerService getManufacturerService() {
        return manufacturerService;
    }

    public static void setManufacturerService(ManufacturerService manufacturerService) {
        MechanicConverter.manufacturerService = manufacturerService;
    }

    // DTO -> Domain
    public static Mechanic toModel(MechanicDto dtoData) {
        if(dtoData.getManufacturerId() == null) {
            throw new EntityStateException("Manufacturer id can not be null.");
        }

        Manufacturer manufacturer = manufacturerService.readById(dtoData.getManufacturerId()).orElseThrow(
                () -> new NoEntityFoundException("Manufacturer with given id not found.")
        );

        return new Mechanic(null, dtoData.getName(), dtoData.getSurname(), dtoData.getPhoneNumber(), manufacturer);
    }

    public static Mechanic toModel(Integer id, MechanicDto dtoData) {
        Mechanic model = toModel(dtoData);

        return new Mechanic(id, model.getName(), model.getSurname(), model.getPhoneNumber(), model.getManufacturer());
    }

    // Domain -> DTO
    public static MechanicDto fromModel(Mechanic data) {
        return new MechanicDto(data.getId(), data.getName(), data.getSurname(), data.getPhoneNumber(), data.getManufacturer().getId());
    }

    // DTO -> Domain : many
    public static Collection<Mechanic> toModelMany(Collection<MechanicDto> dtoData) {
        Collection<Mechanic> mechanics = new ArrayList<>();
        dtoData.forEach((el) -> mechanics.add(toModel(el)));
        return mechanics;
    }

    // Domain -> DTO : many
    public static Collection<MechanicDto> fromModelMany(Collection<Mechanic> data) {
        Collection<MechanicDto> manufacturersDto = new ArrayList<>();
        data.forEach((el) -> manufacturersDto.add(fromModel(el)));
        return manufacturersDto;
    }
}
