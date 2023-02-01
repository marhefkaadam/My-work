package cz.cvut.fit.tjv.arails.app.business;

import cz.cvut.fit.tjv.arails.app.api.dto.MechanicDto;
import cz.cvut.fit.tjv.arails.app.dao.TrainJpaRepository;
import cz.cvut.fit.tjv.arails.app.domain.Manufacturer;
import cz.cvut.fit.tjv.arails.app.domain.Mechanic;
import cz.cvut.fit.tjv.arails.app.domain.Train;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;


import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class TrainService extends AbstractCrudService<Integer, Train, TrainJpaRepository> {
    protected MechanicService mechanicService;

    TrainService(TrainJpaRepository repository, MechanicService mechanicService) {
        super(repository);
        this.mechanicService = mechanicService;
    }

    public boolean exists(Train entity) {
        return repository.existsById(entity.getId());
    }

    public Train updateTrain(Train train) {
        if( train.getRevisionValidity() != null &&
                readById(train.getId()).orElseThrow().getRevisionValidity() != null &&
                ! readById(train.getId()).orElseThrow().getRevisionValidity().equals(train.getRevisionValidity()) ) {
            if(train.getProductionYear() <= LocalDateTime.now().getYear() - 20) {
                throw new EntityStateException("The production year of the train is too old (>20), updating the revision validity is forbidden.");
            }
        } else {
            if(readById(train.getId()).orElseThrow().getRevisionValidity() == null &&
                    train.getRevisionValidity() != null && train.getProductionYear() <= LocalDateTime.now().getYear() - 20) {
                throw new EntityStateException("The production year of the train is too old (>20), updating the revision validity is forbidden.");
            }
        }

        update(train);

        return train;
    }

    public void addMechanic(Integer id_train, Integer id_mechanic) {
        Optional<Train> train = readById(id_train);

        Optional<Mechanic> mechanic = mechanicService.readById(id_mechanic);

        train.orElseThrow().getRepairedBy().add(mechanic.orElseThrow());
        update(train.get());
    }

    public void deleteMechanic(Integer id_train, Integer id_mechanic) {
        Optional<Train> train = readById(id_train);

        Optional<Mechanic> mechanic = mechanicService.readById(id_mechanic);

        train.orElseThrow().getRepairedBy().remove(mechanic.orElseThrow());
        update(train.get());
    }

    public Collection<Mechanic> getAllMechanics(Integer id) {
        Collection<Mechanic> repairedBy = new ArrayList<>();

        Optional<Train> train = readById(id);

        repairedBy.addAll(train.orElseThrow().getRepairedBy());

        return repairedBy;
    }
}
