package cz.cvut.fit.tjv.arails.app.api.converter;

import cz.cvut.fit.tjv.arails.app.api.dto.ManufacturerDto;
import cz.cvut.fit.tjv.arails.app.domain.Manufacturer;

import java.util.ArrayList;
import java.util.Collection;

public class ManufacturerConverter {
    // DTO -> Domain
    public static Manufacturer toModel(ManufacturerDto dtoData) {
        return new Manufacturer(null, dtoData.getCompanyName(), dtoData.getEmail(), dtoData.getPhoneNumber());
    }

    public static Manufacturer toModel(Integer id, ManufacturerDto dtoData) {
        return new Manufacturer(id, dtoData.getCompanyName(), dtoData.getEmail(), dtoData.getPhoneNumber());
    }

    // Domain -> DTO
    public static ManufacturerDto fromModel(Manufacturer data) {
        return new ManufacturerDto(data.getId(), data.getCompanyName(), data.getEmail(), data.getPhoneNumber());
    }

    // DTO -> Domain : many
    public static Collection<Manufacturer> toModelMany(Collection<ManufacturerDto> dtoData) {
        Collection<Manufacturer> manufacturers = new ArrayList<>();
        dtoData.forEach((el) -> manufacturers.add(toModel(el)));
        return manufacturers;
    }

    // Domain -> DTO : many
    public static Collection<ManufacturerDto> fromModelMany(Collection<Manufacturer> data) {
        Collection<ManufacturerDto> manufacturersDto = new ArrayList<>();
        data.forEach((el) -> manufacturersDto.add(fromModel(el)));
        return manufacturersDto;
    }
}
