package unlar.edu.ar.Tp3.repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public abstract class GenericInMemoryRepository<T, ID> implements IGenericRepository<T, ID> {

    protected final ConcurrentHashMap<ID, T> dataStore = new ConcurrentHashMap<>();

    protected final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public List<T> findAll() {
        return new ArrayList<>(dataStore.values());
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(dataStore.get(id));
    }

    @Override
    @SuppressWarnings("unchecked")
    public T save(T entity) {
        ID id = extractId(entity);
        if (id == null) {
            Long newId = idGenerator.getAndIncrement();
            entity = assignId(entity, newId);
            id = (ID) newId;
        }
        dataStore.put(id, entity);
        return entity;
    }

    @Override
    public void deleteById(ID id) {
        if (!dataStore.containsKey(id)) {
            throw new ResourceNotFoundException("Entidad con id " + id + " no encontrada");
        }
        dataStore.remove(id);
    }

    @Override
    public boolean existsById(ID id) {
        return dataStore.containsKey(id);
    }

    @Override
    public long count() {
        return dataStore.size();
    }

    protected abstract ID extractId(T entity);

    protected abstract T assignId(T entity, Long newId);
}
