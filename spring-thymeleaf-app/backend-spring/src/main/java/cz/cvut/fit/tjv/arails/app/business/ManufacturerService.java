package cz.cvut.fit.tjv.arails.app.business;

import cz.cvut.fit.tjv.arails.app.dao.ManufacturerJpaRepository;
import cz.cvut.fit.tjv.arails.app.domain.Manufacturer;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class ManufacturerService extends AbstractCrudService<Integer, Manufacturer, ManufacturerJpaRepository> {
    public ManufacturerService(ManufacturerJpaRepository repository) {
        super(repository);
    }

    public boolean exists(Manufacturer entity) {
        return repository.existsById(entity.getId());
    }

    public Collection<Manufacturer> getManufacturersByProductionYear(Integer production_year) {
        return repository.findManufacturersByProductionYear(production_year);
    }
}
