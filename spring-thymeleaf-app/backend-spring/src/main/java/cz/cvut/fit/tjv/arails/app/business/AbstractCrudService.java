package cz.cvut.fit.tjv.arails.app.business;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * Business superclass for CRUD operations.
 * @param <K> is a type of a primary key.
 * @param <E> is a type of entity.
 * @param <R> is a type of JpaRepository with entity type E and key type K.
 */
public abstract class AbstractCrudService<K, E, R extends JpaRepository<E, K>> {

    protected final R repository;

    protected AbstractCrudService(R repository) {
        this.repository = repository;
    }


    public Collection<E> readAll() {
        return repository.findAll();
    }

    public Optional<E> readById(K id) {
        return repository.findById(id);
    }

    public abstract boolean exists(E entity);

    public void create(E entity) {
        repository.save(entity);
    }

    public void update(E entity) {
        if(exists(entity))
            repository.save(entity);
        else
            throw new EntityStateException(entity);
    }

    public void deleteById(K id) {
        repository.deleteById(id);
    }

}
