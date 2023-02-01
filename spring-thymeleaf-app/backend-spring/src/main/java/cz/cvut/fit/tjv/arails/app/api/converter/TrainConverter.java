package cz.cvut.fit.tjv.arails.app.api.converter;

import cz.cvut.fit.tjv.arails.app.api.dto.TrainDto;
import cz.cvut.fit.tjv.arails.app.api.exception.NoEntityFoundException;
import cz.cvut.fit.tjv.arails.app.business.EntityStateException;
import cz.cvut.fit.tjv.arails.app.business.ManufacturerService;
import cz.cvut.fit.tjv.arails.app.domain.Manufacturer;
import cz.cvut.fit.tjv.arails.app.domain.Train;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class TrainConverter {
    public static ManufacturerService manufacturerService;

    TrainConverter(ManufacturerService manufacturerService) {
        TrainConverter.manufacturerService = manufacturerService;
    }

    public static ManufacturerService getManufacturerService() {
        return manufacturerService;
    }

    public static void setManufacturerService(ManufacturerService manufacturerService) {
        TrainConverter.manufacturerService = manufacturerService;
    }

    // DTO -> Domain
    public static Train toModel(TrainDto dtoData) {
        if(dtoData.getManufacturerId() == null) {
            throw new EntityStateException("Manufacturer id can not be null.");
        }

        Manufacturer manufacturer = manufacturerService.readById(dtoData.getManufacturerId()).orElseThrow(
                () -> new NoEntityFoundException("Manufacturer with given id not found.")
        );

        return new Train(null, dtoData.getMaxSpeed(), dtoData.getProductionYear(), dtoData.getRevisionValidity(), manufacturer);
    }

    // DTO -> Domain
    public static Train toModel(Integer id, TrainDto dtoData) {
        Train model = toModel(dtoData);

        return new Train(id, model.getMaxSpeed(), model.getProductionYear(), model.getRevisionValidity(), model.getManufacturer());
    }

    // Domain -> DTO
    public static TrainDto fromModel(Train data) {
        return new TrainDto(data.getId(), data.getMaxSpeed(), data.getProductionYear(), data.getRevisionValidity(), data.getManufacturer().getId());
    }

    // DTO -> Domain : many
    public static Collection<Train> toModelMany(Collection<TrainDto> dtoData) {
        Collection<Train> manufacturers = new ArrayList<>();
        dtoData.forEach((el) -> manufacturers.add(toModel(el)));
        return manufacturers;
    }

    // Domain -> DTO : many
    public static Collection<TrainDto> fromModelMany(Collection<Train> data) {
        Collection<TrainDto> manufacturersDto = new ArrayList<>();
        data.forEach((el) -> manufacturersDto.add(fromModel(el)));
        return manufacturersDto;
    }
}
