package cz.cvut.fit.tjv.arails.app.business;

import cz.cvut.fit.tjv.arails.app.dao.MechanicJpaRepository;
import cz.cvut.fit.tjv.arails.app.domain.Mechanic;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class MechanicService extends AbstractCrudService<Integer, Mechanic, MechanicJpaRepository> {
    MechanicService(MechanicJpaRepository repository) {
        super(repository);
    }

    public boolean exists(Mechanic entity) {
        return repository.existsById(entity.getId());
    }
}
